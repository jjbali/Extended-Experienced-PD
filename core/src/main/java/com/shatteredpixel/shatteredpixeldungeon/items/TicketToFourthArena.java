/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2020 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2020 Trashbox Bobylev
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.levels.FourthArenaLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class TicketToFourthArena extends TicketToArena {

    {
        image = ItemSpriteSheet.MAGIC_PORTER;
        stackable = true;
    }
    private static final ItemSprite.Glowing SUNNY = new ItemSprite.Glowing( 0xFFFFFF );
    private static final ItemSprite.Glowing RANDOM = new ItemSprite.Glowing( 0x000000 );

    @Override
    public ItemSprite.Glowing glowing() {
        int flash = Dungeon.Int(2);
        return flash == 0 ? SUNNY : RANDOM;
    }
    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    private static final String AC_USE = "USE";


    public int depth;
    public int branch;
    public int pos;

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_USE)){
            if (Dungeon.branch != Dungeon.BRANCH_FOURTH){
                depth = Dungeon.depth;
                branch = Dungeon.branch;
                pos = hero.pos;
                try {
                    Dungeon.saveLevel(GamesInProgress.curSlot);
                } catch (Exception e){
                    Game.reportException(e);
                }
                InterlevelScene.curTransition = new LevelTransition(Dungeon.level, -1, LevelTransition.Type.BRANCH_EXIT, 106, Dungeon.BRANCH_FOURTH, LevelTransition.Type.BRANCH_ENTRANCE);
                InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                Game.switchScene( InterlevelScene.class );
                Buff.affect(hero, FourthArenaLevel.FourthArenaCounter.class);
            } else {
//                InterlevelScene.curTransition = new LevelTransition(Dungeon.level, -1, LevelTransition.Type.BRANCH_ENTRANCE, depth, branch, LevelTransition.Type.BRANCH_EXIT);
                InterlevelScene.mode = InterlevelScene.Mode.RETURN;
                InterlevelScene.returnDepth = depth;
                InterlevelScene.returnBranch = branch;
                InterlevelScene.returnPos = pos;
                Game.switchScene( InterlevelScene.class );
                detach(hero.belongings.backpack);
                Buff.detach(hero, FourthArenaLevel.FourthArenaCounter.class);
            }
        }
    }
    private static final String DEPTH	= "depth";
    private static final String BRANCH	= "branch";
    private static final String POS		= "pos";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( DEPTH, depth );
        bundle.put( BRANCH, branch );
        if (depth != -1) {
            bundle.put( POS, pos);
        }
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        depth	= bundle.getInt( DEPTH );
        if (bundle.contains(BRANCH))
            branch	= bundle.getInt( BRANCH );
        else
            branch = Dungeon.BRANCH_NORMAL;
        pos	= bundle.getInt( POS );
    }

    @Override
    public String name() {
        return "Ticket To 4th Arena";
    }

    @Override
    public String desc() {
        return "Teleports you into the newly created arena!\n\n"
             + "_EVERY CREATURE HERE WILL DROP EVERY TWO OF SOME ITEMS IN EVERY CATEGORY, MAYBE?_"
             + "\n\nGOODLUCK GRINDING :)";
    }

    @Override
    public long value() {
        return 15000000;
    }

    @Override
    public Item random() {
        quantity = (Dungeon.IntRange( 30 + Dungeon.escalatingDepth() * 300, 60 + Dungeon.escalatingDepth() * 300 ));
        return this;
    }
}

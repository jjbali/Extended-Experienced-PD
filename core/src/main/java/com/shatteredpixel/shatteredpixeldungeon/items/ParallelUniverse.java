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
import com.shatteredpixel.shatteredpixeldungeon.Rankings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.levels.ArenaLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class ParallelUniverse extends Item{

    {
        image = ItemSpriteSheet.HEALING_BOOK;
    }

    private int requirements = 100;
    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    private static final String AC_USE = "USE";
    private static final String AC_TICKET = "TICKET";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions(hero);
        if (Dungeon.hero.lvl >= requirements) actions.add(AC_USE);
        actions.remove(AC_DROP);
        actions.remove(AC_THROW);
        actions.remove(AC_RENAME);
        actions.add(AC_TICKET);
        return actions;
    }

    public int depth;
    public int branch;
    public int pos;

    private static int[] generateLotteryNumbers() {
        int[] numbers = new int[6];

        for (int i = 0; i < 6; i++) {
            numbers[i] = Random.Int(49) + 1; // Generating random numbers between 1 and 49
        }

        return numbers;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_USE)){
            if (Dungeon.branch != Dungeon.DIMENSIONAL){
                depth = Dungeon.depth;
                branch = Dungeon.branch;
                pos = hero.pos;
                try {
                    Dungeon.saveLevel(GamesInProgress.curSlot);
                } catch (Exception e){
                    Game.reportException(e);
                }
                InterlevelScene.curTransition = new LevelTransition(Dungeon.level, -1, LevelTransition.Type.BRANCH_EXIT, 1, Dungeon.DIMENSIONAL, LevelTransition.Type.BRANCH_ENTRANCE);
                InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                Game.switchScene( InterlevelScene.class );
            } else {
                InterlevelScene.mode = InterlevelScene.Mode.RETURN;
                InterlevelScene.returnDepth = depth;
                InterlevelScene.returnBranch = branch;
                InterlevelScene.returnPos = pos;
                Game.switchScene( InterlevelScene.class );
            }
        } else if (action.equals(AC_TICKET)) {
            GLog.p("Generated Ticket: " + Arrays.toString(generateLotteryNumbers()));
        }
    }

    private static final String DEPTH	= "depth";
    private static final String BRANCH	= "branch";
    private static final String POS		= "pos";

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0.2f);
    }

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
        if (Dungeon.hero.lvl >= requirements) {
            return "Parallel Universe: Glitched";
        } else {
            String rand = "abcdefghijklmnopqrstuvwxyz1234567890";
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 8; i++){
                builder.append(rand.charAt(Random.Int(rand.length())));
            }
            return "???: " + builder;
        }
    }

    @Override
    public String desc() {
        String desc_1 = "\n\nTurns elapsed: " + Game.elapsed;
        if (Dungeon.hero.lvl >= requirements) {
            return "This legendary item will teleport you to the most powerful branch that exist." + desc_1;
        } else {
            return "You are not ready for this, but grind more if you can!" + desc_1;
        }
    }
}

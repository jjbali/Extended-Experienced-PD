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

import static com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene.ready;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blacksmith;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.shatteredpixel.shatteredpixeldungeon.levels.DimensionalLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.SecretLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndStorage;
import com.watabou.noosa.Game;

import java.util.ArrayList;
import java.util.Objects;

public class PsycheChest extends Item {
    {
        image = ItemSpriteSheet.PSYCHE_CHEST;
        unique = true;
        identify();
    }

    private static final String AC_ACTIVATE = "ACTIVATE";
    private static final String AC_DEACTIVATE = "DEACTIVATE";
    private static final String AC_RESET = "RESET";
    private static final String AC_STORAGE = "STORAGE";

    private static final ItemSprite.Glowing BLOODY = new ItemSprite.Glowing( 0x550000 );

    public static int questDepth;

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        if (!hero.grinding) actions.add( AC_ACTIVATE );
        else actions.add( AC_DEACTIVATE );
        if (!(Dungeon.branch == 7)) actions.add(AC_RESET);
        actions.remove(AC_DROP);
        actions.remove(AC_THROW);
        actions.add(AC_STORAGE);
        return actions;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals(AC_ACTIVATE) && Dungeon.branch != 7) {
            hero.grinding = true;
            GLog.w( Messages.get(this, "activated") );
        }
        if (action.contains(AC_DEACTIVATE) && Dungeon.branch != 7){
            hero.grinding = false;
            GLog.w( Messages.get(this, "deactivated") );
        }
        for (Heap heap: Dungeon.level.heaps.valueList()) {
            if (heap.type.forSale()) {
                if (heap.peek().wereOofed && !Dungeon.oofedItems.contains(heap.peek())){
                    Dungeon.oofedItems.add(heap.items.removeFirst());
                }
            }
        }
        if (action.contains(AC_RESET)){
            if (Objects.equals(Dungeon.level, new SecretLevel())) {
                GLog.p("Are you serious men? You escaped the (reality) glitch.");
                InterlevelScene.curTransition = new LevelTransition(Dungeon.level, -1, LevelTransition.Type.REGULAR_EXIT, 1, Dungeon.branch, null);
                InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
            }
            for (Mob m: Dungeon.level.mobs) {
                if (m instanceof Ghost) {
                    questDepth = Dungeon.depth;
                    Ghost.Quest.reset();
                } else if (m instanceof Wandmaker) {
                    questDepth = Dungeon.depth;
                    Wandmaker.Quest.reset();
                } else if (m instanceof Blacksmith) {
                    questDepth = Dungeon.depth;
                    Blacksmith.Quest.reset();
                } else if (m instanceof Imp) {
                    questDepth = Dungeon.depth;
                    Imp.Quest.reset();
                }
            }
            for (Heap heap: Dungeon.level.heaps.valueList()) {
                if (heap.type.forSale()) {
                    if (heap.peek().wereOofed && !Dungeon.oofedItems.contains(heap.peek())){
                        Dungeon.oofedItems.add(heap.items.removeFirst());
                    }
                }
            }
            InterlevelScene.mode = InterlevelScene.Mode.RESET;
            Dungeon.resetDamage *= 1.16d;
            Game.switchScene(InterlevelScene.class);
            Dungeon.level.reset();
        }
        if (action.contains(AC_STORAGE)) {
            GameScene.show( new WndStorage(Dungeon.hero.storage, null, WndStorage.Mode.ALL, "Storage" ) );
            ready();
        }
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return Dungeon.hero.grinding ? BLOODY : null;
    }

}

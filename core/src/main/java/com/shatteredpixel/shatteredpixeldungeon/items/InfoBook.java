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
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Spell;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.Runestone;
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

public class InfoBook extends Item{

    {
        image = ItemSpriteSheet.PAGE_2;
        unique = true;
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
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions(hero);
        actions.remove(AC_DROP);
        actions.remove(AC_THROW);
        actions.remove(AC_RENAME);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(0.1f);
    }

    private static final String SCROLL	= "scroll_";
    private static final String POTION	= "potion_";
    private static final String SPELL	= "spell_";
    private static final String STONE	= "stone_";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( SCROLL, Scroll.scroll_uses );
        bundle.put( POTION, Potion.potion_uses );
        bundle.put( SPELL, Spell.spell_uses );
        bundle.put( STONE, Runestone.stone_uses );

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        Scroll.scroll_uses = bundle.getInt( SCROLL );
        Potion.potion_uses = bundle.getInt( POTION );
        Spell.spell_uses = bundle.getInt( SPELL );
        Runestone.stone_uses = bundle.getInt( STONE );
    }

    @Override
    public String name() {
       return "Info Page";
    }

    @Override
    public String desc() {
        String scroll_counter = "\nUsed Scroll(s): " + Scroll.scroll_uses;
        String spell_counter = "\nUsed Spell(s): " + Spell.spell_uses;
        String potion_counter = "\nUsed Potion(s): " + Potion.potion_uses;
        String stone_counter = "\nUsed Stone(s): " + Runestone.stone_uses;
        return "This piece of page portrays counters on how you use some of the consumable items.\n"
                + scroll_counter
                + spell_counter
                + potion_counter
                + stone_counter;
    }
}

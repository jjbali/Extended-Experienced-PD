/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.tieredcards;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.DivisionItem;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TieredCard extends Item {

    private static final String AC_DRINK	= "DRINK";
    public static long lvl = 1;

    {
        image = ItemSpriteSheet.RANDOM_ITEM_GIVER;

        defaultAction = AC_DRINK;

        stackable = false;
        unique = true;
    }

    @Override
    public ArrayList<String> actions( Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add( AC_DRINK );
        return actions;
    }

    @Override
    public void execute(final Hero hero, String action ) {

        super.execute(hero, action);

        if (action.equals(AC_DRINK)) {
            long rolls = 2 * lvl;
            ArrayList<Item> bonus = RingOfWealth.tryForBonusDrop((int) rolls);
            if (!bonus.isEmpty()) {
                for (Item b : bonus) Dungeon.level.drop(b, hero.pos).sprite.drop();
                RingOfWealth.showFlareForBonusDrop(hero.sprite);
            }
            detach(hero.belongings.backpack);
        }
    }

    @Override
    public long level() {
        return lvl;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public void setLvl(int level){
        lvl = level;
    }

    @Override
    public long value() {
        return 100 * quantity * lvl;
    }

    private static ItemSprite.Glowing GLITCHED = new ItemSprite.Glowing( 0.3f );

    @Override
    public String name() {
        return "Tiered Card";
    }

    @Override
    public String desc() {
        return "Gives random item based on ring of wealth and they glow in specific level";
    }

    @Override
    public ItemSprite.Glowing glowing() {
       return GLITCHED;
    }
}
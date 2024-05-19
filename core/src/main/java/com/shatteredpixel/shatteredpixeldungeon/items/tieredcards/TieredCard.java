/*
 *
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2024 Trashbox Bobylev
 *
 * Extended Experienced Pixel Dungeon
 * Copyright (C) 2023-2024 John Nollas
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
 *
 */

package com.shatteredpixel.shatteredpixeldungeon.items.tieredcards;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class TieredCard extends Item {

    private static final String AC_DRINK	= "DRINK";

    {
        image = ItemSpriteSheet.TIERED;
        defaultAction = AC_DRINK;
        identify();
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
            long rolls = 9 * level();
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
        return super.level();
    }

    @Override
    public boolean isUpgradable() {
        return true;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public long value() {
        return 500 * level();
    }

    private static ItemSprite.Glowing GLITCHED = new ItemSprite.Glowing( 0.3f );
    private static ItemSprite.Glowing r = new ItemSprite.Glowing(0xff0000, 0.3f );
    private static ItemSprite.Glowing y = new ItemSprite.Glowing(0xFFFF00,0.3f );
    private static ItemSprite.Glowing g = new ItemSprite.Glowing(0x008000, 0.3f );
    private static ItemSprite.Glowing o = new ItemSprite.Glowing(0xFFA500, 0.3f);

    @Override
    public String name() {
        return "Tiered Card";
    }

    @Override
    public String desc() {
        return "Gives random item based on ring of wealth, it is upgradable until it reaches 100.";
    }

    @Override
    public ItemSprite.Glowing glowing() {
        if (level() >= 0 && level() <= 25) {
            return g;
        } else if (level() >= 26 && level() <= 50) {
            return y;
        } else if (level() >= 51 && level() <= 75) {
            return r;
        } else if (level() >= 75 && level() <= 90) {
            return o;
        } else if (level() >= 91) {
            return GLITCHED;
        } else {
            return null;
        }
    }
}
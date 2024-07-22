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

package com.shatteredpixel.shatteredpixeldungeon.items.modules;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Module extends Item {

    {
        stackable = true;
        image = ItemSpriteSheet.MODULE_ITEM;

        defaultAction = AC_USE;
    }


    public static int level = 0;

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0xFFFFFF );
    }

    public static final String AC_USE = "USE";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_USE );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
    }

    @Override
    public long level(){
        return level;
    }


    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public long value() {
        return 75000 * quantity;
    }

    @Override
    public Item random() {
        quantity = (Dungeon.IntRange( 1 + Dungeon.escalatingDepth() * 2, 2 + Dungeon.escalatingDepth() * 2 ));
        return this;
    }

    @Override
    public String name() {
        return "Module";
    }

    @Override
    public String desc() {
        return "This is the new item from Extended Experienced Pixel Dungeon, The Module. Provides a specific buff based on the description below.";
    }

    public Item random(long x){
        switch (Random.Int(1, 12)) {
            case 0: default:
                return new ChampionModule().quantity(x);
            case 1:
                return new DimensionalRiftModule().quantity(x);
            case 2:
                return new GoldModule().quantity(x);
            case 3:
                return new HasteModule().quantity(x);
            case 4:
                return new ItemModule().quantity(x);
            case 5:
                return new MindVisionModule().quantity(x);
            case 6:
                return new PotionModule().quantity(x);
            case 7:
                return new ScrollModule().quantity(x);
            case 8:
                return new PurityModule().quantity(x);
            case 9:
                return new TimeReverserModule().quantity(x);
            case 10:
                return new ExpBoostModule().quantity(x);
            case 11:
                return new HealthModule().quantity(x);
            case 12:
                return new LuckModule().quantity(x);
        }
    }

}

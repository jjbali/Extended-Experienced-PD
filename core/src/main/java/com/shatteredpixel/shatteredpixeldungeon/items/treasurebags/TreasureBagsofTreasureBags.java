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

package com.shatteredpixel.shatteredpixeldungeon.items.treasurebags;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Perks;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.TimeReverser;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.EnergyCrystal;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.LiquidMetal;
import com.shatteredpixel.shatteredpixeldungeon.items.RandomBuffGiver;
import com.shatteredpixel.shatteredpixeldungeon.items.Stylus;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.DimensionalRiftModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.HealerModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.LevelModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.LuckModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.MagicalSightModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.OverloadModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.TimeReverserModule;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.BinaryItem;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreasureBagsofTreasureBags extends TreasureBag {
    {
        image = ItemSpriteSheet.TREASURE_OF_TREASURE;
    }

    @Override
    protected ArrayList<Item> items() {
        ArrayList<Item> items = new ArrayList<>();
        List<Class<? extends Item>> normal = Arrays.asList(
                DimensionalRiftModule.class,
                HealerModule.class,
                LevelModule.class,
                LuckModule.class,
                OverloadModule.class,
                MagicalSightModule.class,
                TimeReverserModule.class
        );
        int amount = Random.Int(2, 3);
        int amount_2 = Random.Int(2, 5);
        if (Dungeon.hero.perks.contains(Perks.Perk.MORE_BAG)) amount_2 *= 3.5f;
        if (BinaryItem.streak_i >= 20) amount *= 10f;
        if (Random.Int(4) == 0) {
            items.add(Reflection.newInstance(Random.element(normal)).quantity(amount));
        }
        items.add(new TreasureBag() {
            @Override
            protected ArrayList<Item> items() {
                return null;
            }
        }.randombag().quantity(amount_2));
        return items;
    }

    @Override
    public long value() {
        return 152301 * quantity;
    }
}

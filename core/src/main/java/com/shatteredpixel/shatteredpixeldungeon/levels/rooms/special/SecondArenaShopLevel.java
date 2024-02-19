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

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.ExpGenerator;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.ChaosFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.AlchemyBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.BiggerGambleBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.GambleBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.MiscBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.PotionBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.QualityBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.ScrollBag;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;

import java.util.ArrayList;

public class SecondArenaShopLevel extends ShopRoom{
    @Override
    public int minWidth() {
        return 8;
    }
    public int minHeight() {
        return 8;
    }

    @Override
    public void paint( Level level ) {

        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY_SP );

        placeItems( level );

        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
        }

    }

    protected void placeItems( Level level ){

        if (itemsToSpawn == null){
            itemsToSpawn = new ArrayList<>();
            itemsToSpawn.add(new ExpGenerator().quantity(Dungeon.IntRange(1, 10)));
            itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.EXPOTION));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.EXPOTION ));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.EXPOTION ));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.EXPOTION ));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.EXPOTION ));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.SCROLL ));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.SCROLL ));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.SCROLL ));
            itemsToSpawn.add(new ChaosFishingRod().upgrade(5));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.WEP_T5 ));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.WEP_T5 ));
            itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.WEP_T5 ));
        }

        Point itemPlacement = new Point(entrance());
        if (itemPlacement.y == top){
            itemPlacement.y++;
        } else if (itemPlacement.y == bottom) {
            itemPlacement.y--;
        } else if (itemPlacement.x == left){
            itemPlacement.x++;
        } else {
            itemPlacement.x--;
        }

        for (Item item : itemsToSpawn) {

            if (itemPlacement.x == left+1 && itemPlacement.y != top+1){
                itemPlacement.y--;
            } else if (itemPlacement.y == top+1 && itemPlacement.x != right-1){
                itemPlacement.x++;
            } else if (itemPlacement.x == right-1 && itemPlacement.y != bottom-1){
                itemPlacement.y++;
            } else {
                itemPlacement.x--;
            }

            int cell = level.pointToCell(itemPlacement);

            if (level.heaps.get( cell ) != null) {
                do {
                    cell = level.pointToCell(random());
                } while (level.heaps.get( cell ) != null || level.findMob( cell ) != null);
            }

            level.drop( item, cell ).type = Heap.Type.FOR_ARENA_SALE;
        }

    }
}

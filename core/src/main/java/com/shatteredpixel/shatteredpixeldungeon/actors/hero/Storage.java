/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;

import java.util.Iterator;

public class Storage implements Iterable<Item> {

    public static final int BACKPACK_SIZE	= 40;

    public Bag backpack;



    public Storage(Hero owner) {

        backpack = new Bag() {
            {
                name = "Storage";
                size = 25;
            }

            @Override
            public String name() {
                return name;
            }

            public int capacity(){
                    return size; // default container size
            }
        };
        backpack.owner = owner;
    }



    public void storeInBundle( Bundle bundle ) {

        backpack.storeInBundle2( bundle );


    }

    public void restoreFromBundle( Bundle bundle ) {

        backpack.clear();
        backpack.restoreFromBundle2( bundle );


    }

    @Override
    public Iterator<Item> iterator() {
        return new ItemIterator();
    }

    private static class ItemIterator implements Iterator<Item> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Item next() {
            return null;

        }

        @Override
        public void remove() {

        }
    }
}
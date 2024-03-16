/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.bags;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class CheeseCheest extends Bag {

	{
		image = ItemSpriteSheet.BACKPACK;
	}

	@Override
	public boolean canHold( Item item ) {
		if (item instanceof Artifact || item instanceof Ring){
			return super.canHold(item);
		} else {
			return false;
		}
	}

	@Override
	public boolean collect(Bag container) {
		if (super.collect( container )) {

			if (!Dungeon.LimitedDrops.CHEESY_CHEEST.dropped()){
				Dungeon.LimitedDrops.CHEESY_CHEEST.drop();
			}

			return true;
		} else {
			return false;
		}
	}
	public int capacity(){
		return 54;
	}

	@Override
	public long value() {
		return 650 * (Dungeon.cycle + 1);
	}

	@Override
	public String name() { return "Cheese Cheest"; }

	@Override
	public String desc() { return "This bag expands your inventory by storing artifacts and rings in this bag."; }

}

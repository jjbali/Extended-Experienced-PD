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

package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;

public class Modifiers {

	//Some of these internal IDs are outdated and don't represent what these challenges do

	public static final int FIFTYPERCENTMOREHP			= 1;
	public static final int ONEMORETIME			= 2;
	public static final int LETHAL_POTIONS			= 4;
	public static final int BLINDNESS			= 8;
	public static final int FEW_ITEMS = 16;
	public static final int PRELUDE = 32;
	public static final int MAX_VALUE           = 512;

	public static final String[] NAME_IDS = {
			"fiftypercentmorehp",
			"onemoretime",
			"lethal_potions",
			"blind",
			"few_items",
			"prelude"
	};

	public static final int[] MASKS = {
			FIFTYPERCENTMOREHP,
			ONEMORETIME,
			LETHAL_POTIONS,
			BLINDNESS,
			FEW_ITEMS,
			PRELUDE
	};

    public static int activeModifiers(){
		int mdCount = 0;
		for (int md : Modifiers.MASKS){
			if ((Dungeon.modifiers & md) != 0) mdCount++;
		}
		return mdCount;
	}

	public static boolean isItemBlocked( Item item ){

		return false;

	}

}
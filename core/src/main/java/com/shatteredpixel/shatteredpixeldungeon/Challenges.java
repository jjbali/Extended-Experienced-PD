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

package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStamina;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfDivination;

public class Challenges {

	//Some of these internal IDs are outdated and don't represent what these challenges do
	public static final int DARKNESS			= 1;
	public static final int CHAMPION_ENEMIES	= 2;
	public static final int LUCK_IN = 8;
	public static final int INTUITION_TEST = 16;
	public static final int BLAZING_ECONOMY = 32;
	public static final int GOLDFISH_MEMORY = 64;
	public static final int CRUEL_WORLD = 128;
	public static final int THE_TRUE_FATALITY = 256;
	public static final int ENDLESS_MOMENTUM = 512;
	public static final int COND_INFLATION = 1024;
	public static final int DISP_ENEMIES = 2048;
	public static final int FALL_ECONOMY = 4096;

	public static final int MAX_VALUE           = 8192;

	public static final String[] NAME_IDS = {
			"champion_enemies",
			"darkness",
			"luck_in",
			"intuition_test",
			"blazing_economy",
			"goldfish_memory",
			"cruel_world",
			"the_true_fatality",
			"endless_momentum",
			"cond_inflation",
			"disp_enemies",
			"fall_economy"
	};

	public static final int[] MASKS = {
			CHAMPION_ENEMIES,
			DARKNESS,
			LUCK_IN,
			INTUITION_TEST,
			BLAZING_ECONOMY,
			GOLDFISH_MEMORY,
			CRUEL_WORLD,
			THE_TRUE_FATALITY,
			ENDLESS_MOMENTUM,
			COND_INFLATION,
			DISP_ENEMIES,
			FALL_ECONOMY
	};

    public static int activeChallenges(){
		int chCount = 0;
		for (int ch : Challenges.MASKS){
			if ((Dungeon.challenges & ch) != 0) chCount++;
		}
		return chCount;
	}

	public static boolean isItemBlocked( Item item ){

		if (Dungeon.isChallenged(INTUITION_TEST) && item instanceof ScrollOfIdentify){
			return true;
		}

		if (Dungeon.isChallenged(INTUITION_TEST) && item instanceof ScrollOfDivination){
			return true;
		}

		if (Dungeon.isChallenged(THE_TRUE_FATALITY) && (item instanceof PotionOfHealing ||
														item instanceof ScrollOfUpgrade ||
														item instanceof RingOfMight)) {
			return true;
		}

		if (Dungeon.isChallenged(ENDLESS_MOMENTUM) && (item instanceof PotionOfHaste || item instanceof PotionOfStamina)) {
			return true;
		}

		return false;

	}

}
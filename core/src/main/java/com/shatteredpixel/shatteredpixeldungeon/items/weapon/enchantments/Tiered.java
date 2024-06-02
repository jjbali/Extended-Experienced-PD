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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.DamageEnhance;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite.Glowing;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class Tiered extends Weapon.Enchantment {
	//TODO make this available for a time.
	private static Glowing GOLDEN = new Glowing( 0xFAD777 );
	
	@Override
	public long proc( Weapon weapon, Char attacker, Char defender, long damage ) {
		//int level = (int) Math.max( 0, weapon.buffedLvl() );
		if (Dungeon.Int(50) == 0) {
			weapon.tier++;
			GLog.h("Your weapon's tier has been increased!");
		} else {
			if (attacker.buff(BlobImmunity.class) == null) Buff.affect(attacker, BlobImmunity.class, 3f);
			if (attacker.buff(PotionOfCleansing.Cleanse.class) == null) Buff.affect(attacker, PotionOfCleansing.Cleanse.class, 3f);
		}
		return damage;
	}
	
	@Override
	public Glowing glowing() {
		return GOLDEN;
	}
}

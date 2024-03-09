/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2024 Trashbox Bobylev
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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.noosa.Visual;
import com.watabou.utils.Random;

public class Lootstreaker extends Weapon.Enchantment {

	private static Glowing GREEN = new Glowing( 0x10FF30 );
	
	@Override
	public long proc( Weapon weapon, Char attacker, Char defender, long damage ) {
		long level = Math.max( 0, weapon.buffedLvl() );

		// lvl 0 - 10%
		// lvl 1 ~ 12%
		// lvl 2 ~ 14%
		float procChance = (level+4f)/(level+40f) * procChanceMultiplier(attacker);
		if (defender.HP <= damage && Random.Float() < procChance){

			float powerMulti = Math.max(3f, procChance);

			//default is -5: 80% common, 20% uncommon, 0% rare
			//ring level increases by 1 for each 20% above 100% proc rate
			Buff.affect(defender, Streaker.class).ringLevel = 5 + Math.round(5*powerMulti);
		}
		
		return damage;

	}
	
	public static Item genLoot(){
		//80% common, 20% uncommon, 0% rare
		return RingOfWealth.genConsumableDrop(-5);
	}

	public static void showFlare( Visual vis ){
		RingOfWealth.showFlareForBonusDrop(vis);
	}

	@Override
	public Glowing glowing() {
		return GREEN;
	}
	
	//used to keep track of whether a luck proc is incoming. see Mob.die()
	public static class Streaker extends Buff {

		private int ringLevel = 5;
		
		@Override
		public boolean act() {
			detach();
			return true;
		}

		public Item genLoot(){
			detach();
			return RingOfWealth.genConsumableDrop(ringLevel);
		}
	}
	
}

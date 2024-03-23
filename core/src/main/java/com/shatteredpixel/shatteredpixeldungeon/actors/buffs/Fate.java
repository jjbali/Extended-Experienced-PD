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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Fate extends Buff {

	
	{
		type = buffType.POSITIVE;
	}

	@Override
	public int icon() {
		return BuffIndicator.STICK;
	}

	@Override
	public void tintIcon(Image icon) {
		icon.hardlight(0xFFFF00);
	}

	@Override
	public boolean act() {
		if (Random.Float() < 0.15f && target == Dungeon.hero) {
			int D = Random.Int(12);
			switch (D) {
				case 0: default:
					Buff.prolong(Dungeon.hero, Blindness.class, 2f);
					break;
				case 1:
					Buff.affect(Dungeon.hero, Burning.class).reignite(Dungeon.hero, 2f);
					break;
				case 2:
					Buff.affect(Dungeon.hero, Corrosion.class).set(2f, 3);
					break;
				case 3:
					Buff.prolong(Dungeon.hero, Cripple.class, 2f);
					break;
				case 4:
					Buff.prolong(Dungeon.hero, Degrade.class, 2f);
					break;
				case 5:
					Buff.prolong(Dungeon.hero, Frost.class, 2f);
					break;
				case 6:
					Buff.prolong(Dungeon.hero, Hex.class, 2f);
					break;
				case 7:
					Buff.prolong(Dungeon.hero, Paralysis.class, 2f);
					break;
				case 8:
					Buff.affect(Dungeon.hero, Poison.class).set(2f);
					break;
				case 9:
					Buff.prolong(Dungeon.hero, Vulnerable.class, 2f);
					break;
				case 10:
					Buff.prolong(Dungeon.hero, Weakness.class, 2f);
					break;
				case 11:
					Buff.prolong(Dungeon.hero, Vertigo.class, 2f);
					break;
				case 12:
					Buff.prolong(Dungeon.hero, Daze.class, 2f);
					break;
			}
		}
		spend(0.1f);
		return true;
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}

}

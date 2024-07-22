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

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.THE_TRUE_FATALITY;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class SuperExp extends Buff {

	
	{
		type = buffType.POSITIVE;
	}

	public static final float DURATION = 500f;
	protected float left;

	public float multiplier = 1f;
	@Override
	public int icon() {
		return BuffIndicator.UPGRADE;
	}

	@Override
	public void tintIcon(Image icon) {
		icon.hardlight(0xFFFF00);
	}

	public float expfactor(){
		return multiplier;
	}
	public void set( float duration ) {
		this.left = duration;
	}

	@Override
	public boolean act() {
		multiplier += 0.005f;
		spend(TICK);
		left -= TICK;
		if (left <= 0){
			detach();
		}
		return true;
	}


	@Override
	public float iconFadePercent() {
		return Math.max(0, (left - visualcooldown()) / DURATION);
	}

	private static final String LEFT	= "left";
	public static final String MULTIPLIER = "multiplier";
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(MULTIPLIER, multiplier);
		bundle.put( LEFT, left );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		multiplier = bundle.getFloat(MULTIPLIER);
		left = bundle.getFloat( LEFT );
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", (int)(100*(multiplier-1)), (int)(left));
	}

}

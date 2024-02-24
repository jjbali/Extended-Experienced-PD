/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

import java.text.DecimalFormat;

public class DamageEnhance extends Buff {

	private float dmgBonus = 0;
	private float duration = 0f;
	private float initialDuration = 0f;

	@Override
	public int icon() {
		return BuffIndicator.PREPARATION;
	}

	@Override
	public void tintIcon(Image icon) {
		icon.hardlight(1f, 0f, 1f);
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (initialDuration - duration)/ initialDuration);
	}

	public float getDmg() {
		return dmgBonus;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	public void set(int lvl) {
		dmgBonus = 1 + 0.2f * lvl;
		duration = 5f;
		initialDuration = duration;

		BuffIndicator.refreshHero(); //refresh the buff visually on-hit
	}

	public void addTime( float time ){
		duration += time;
	}

	@Override
	public void detach() {
		super.detach();
	}

	@Override
	public boolean act() {
		duration-=TICK;
		spend(TICK);
		if (duration <= 0) {
			detach();
		}
		return true;
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", new DecimalFormat("#").format(100f*dmgBonus), dispTurns(duration));
	}

	private static final String DMGBONUS  = "dmgBonus";
	private static final String DURATION  = "duration";
	private static final String INITIALDURATION  = "initialDuration";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DMGBONUS, dmgBonus);
		bundle.put(DURATION, duration);
		bundle.put(INITIALDURATION, initialDuration);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		dmgBonus = bundle.getFloat( DMGBONUS );
		duration = bundle.getFloat( DURATION );
		initialDuration = bundle.getFloat( INITIALDURATION );
	}

}
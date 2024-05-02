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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.variants.sewers;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.Ratmogrify;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RatSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class RatZ extends Mob {

	{
		spriteClass = RatSprite.class;
		
		HP = HT = 80;
		defenseSkill = 20;
		
		maxLvl = 5;

		switch (Dungeon.cycle){
            case 1:
                HP = HT = 1000;
                defenseSkill = 260;
                EXP = 150;
                break;
            case 2:
                HP = HT = 12500;
                defenseSkill = 1200;
                EXP = 1200;
                break;
            case 3:
                HP = HT = 189000;
                defenseSkill = 3400;
                EXP = 8780;
                break;
            case 4:
                HP = HT = 10000000;
                defenseSkill = 12000;
                EXP = 260000;
                break;
        }
	}

	@Override
	protected boolean act() {
		if (Dungeon.level.heroFOV[pos] && Dungeon.hero.armorAbility instanceof Ratmogrify){
			alignment = Alignment.ALLY;
			if (state == SLEEPING) state = WANDERING;
		}
		return super.act();
	}

	@Override
	public long damageRoll() {
	    switch (Dungeon.cycle) {
            case 1: return Random.NormalIntRange(25, 31);
            case 2: return Random.NormalIntRange(110, 145);
            case 3: return Random.NormalIntRange(475, 589);
            case 4: return Random.NormalIntRange(3200, 5000);
        }
        return Random.NormalIntRange(1, 4);
	}
	
	@Override
	public int attackSkill( Char target ) {
	    switch (Dungeon.cycle){
            case 1: return 38;
            case 2: return 175;
            case 3: return 520;
            case 4: return 1350;
        }
		return 8;
	}
	
	@Override
	public int cycledDrRoll() {
        switch (Dungeon.cycle){
            case 1: return Random.NormalIntRange(5, 15);
            case 2: return Random.NormalIntRange(60, 100);
            case 3: return Random.NormalIntRange(250, 434);
            case 4: return Random.NormalIntRange(2000, 4500);
        }
		return Random.NormalIntRange(0, 1);
	}

	private static final String RAT_ALLY = "rat_ally";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		if (alignment == Alignment.ALLY) bundle.put(RAT_ALLY, true);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.contains(RAT_ALLY)) alignment = Alignment.ALLY;
	}
}

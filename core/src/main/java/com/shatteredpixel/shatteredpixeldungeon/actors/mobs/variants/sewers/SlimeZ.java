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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SlimeSprite;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class SlimeZ extends Mob {
	
	{
		spriteClass = SlimeSprite.class;
		
		HP = HT = 200;
		defenseSkill = 50;
		
		EXP = 40;
		maxLvl = 9;
		
		lootChance = 0.2f; //by default, see lootChance()

        switch (Dungeon.cycle){
            case 1:
                HP = HT = 2000;
                defenseSkill = 300;
                EXP = 220;
                break;
            case 2:
                HP = HT = 21350;
                defenseSkill = 1500;
                EXP = 1960;
                break;
            case 3:
                HP = HT = 400000;
                defenseSkill = 4150;
                EXP = 15000;
                break;
            case 4:
                HP = HT = 30000000;
                defenseSkill = 19000;
                EXP = 430000;
                break;
        }
	}
	
	@Override
	public long damageRoll() {
        switch (Dungeon.cycle) {
            case 1: return Random.NormalIntRange(32, 38);
            case 2: return Random.NormalIntRange(164, 189);
            case 3: return Random.NormalIntRange(560, 740);
            case 4: return Random.NormalIntRange(7000, 9000);
        }
	    return Random.NormalIntRange( 2, 5 );
	}
	
	@Override
	public int attackSkill( Char target ) {
        switch (Dungeon.cycle){
            case 1: return 46;
            case 2: return 225;
            case 3: return 580;
            case 4: return 2200;
        }
		return 12;
	}
	
	@Override
	public void damage(long dmg, Object src) {
		double scaleFactor = AscensionChallenge.statModifier(this);
		long scaledDmg = Math.round(dmg/scaleFactor);
		if (scaledDmg >= 5 + Dungeon.cycle * 75 && Dungeon.cycle < 2){
			//takes 5/6/7/8/9/10 dmg at 5/7/10/14/19/25 incoming dmg
			scaledDmg = 4 + Dungeon.cycle * 75 + (int)(Math.sqrt(8*(scaledDmg - 4) + 1) - 1)/2;
		}
		dmg = (long) (scaledDmg*AscensionChallenge.statModifier(this));
		super.damage(dmg, src);
	}

	@Override
	public float lootChance(){
		//each drop makes future drops 1/3 as likely
		// so loot chance looks like: 1/5, 1/15, 1/45, 1/135, etc.
		return super.lootChance() * (float)Math.pow(1/3f, Dungeon.LimitedDrops.SLIME_WEP.count);
	}
	
	@Override
	public Item createLoot() {
		Dungeon.LimitedDrops.SLIME_WEP.count++;
		Generator.Category c = Generator.Category.WEP_T2;
		MeleeWeapon w = (MeleeWeapon) Reflection.newInstance(c.classes[Random.chances(c.defaultProbs)]);
		assert w != null;
		w.random();
		w.level(0);
		return w;
	}
}

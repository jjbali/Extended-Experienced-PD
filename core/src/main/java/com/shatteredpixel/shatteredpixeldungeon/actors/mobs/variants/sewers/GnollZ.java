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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GnollSprite;
import com.watabou.utils.Random;

public class GnollZ extends Mob {
	
	{
		spriteClass = GnollSprite.class;
		
		HP = HT = 120;
		defenseSkill = 40;
		
		EXP = 20;
		maxLvl = 8;
		
		loot = Gold.class;
		lootChance = 0.5f;

        switch (Dungeon.cycle){
            case 1:
                HP = HT = 1150;
                defenseSkill = 270;
                EXP = 170;
                break;
            case 2:
                HP = HT = 15000;
                defenseSkill = 1260;
                EXP = 1400;
                break;
            case 3:
                HP = HT = 220000;
                defenseSkill = 3600;
                EXP = 9340;
                break;
            case 4:
                HP = HT = 14000000;
                defenseSkill = 14500;
                EXP = 310000;
                break;
        }
	}
	
	@Override
	public long damageRoll() {
        switch (Dungeon.cycle) {
            case 1: return Random.NormalIntRange(28, 40);
            case 2: return Random.NormalIntRange(130, 167);
            case 3: return Random.NormalIntRange(512, 644);
            case 4: return Random.NormalIntRange(4000, 7000);
        }
		return Random.NormalIntRange( 1, 6 );
	}
	
	@Override
	public int attackSkill( Char target ) {
        switch (Dungeon.cycle){
            case 1: return 42;
            case 2: return 190;
            case 3: return 540;
            case 4: return 1580;
        }
		return 10;
	}
	
	@Override
	public int cycledDrRoll() {
        switch (Dungeon.cycle){
            case 1: return Random.NormalIntRange(6, 17);
            case 2: return Random.NormalIntRange(69, 130);
            case 3: return Random.NormalIntRange(275, 500);
            case 4: return Random.NormalIntRange(3000, 6000);
        }
		return Random.NormalIntRange(0, 2);
	}
}

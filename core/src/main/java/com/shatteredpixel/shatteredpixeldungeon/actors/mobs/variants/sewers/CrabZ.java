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
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CrabSprite;
import com.watabou.utils.Random;

public class CrabZ extends Mob {

	{
		spriteClass = CrabSprite.class;
		
		HP = HT = 150;
		defenseSkill = 50;
		baseSpeed = 2f;
		
		EXP = 40;
		maxLvl = 9;
		
		loot = new MysteryMeat();
		lootChance = 0.167f;

        switch (Dungeon.cycle){
            case 1:
                HP = HT = 1400;
                defenseSkill = 300;
                EXP = 200;
                break;
            case 2:
                HP = HT = 18450;
                defenseSkill = 1400;
                EXP = 1750;
                break;
            case 3:
                HP = HT = 350000;
                defenseSkill = 3900;
                EXP = 12800;
                break;
            case 4:
                HP = HT = 22500000;
                defenseSkill = 17000;
                EXP = 390000;
                break;
        }
	}
	
	@Override
	public long damageRoll() {
        switch (Dungeon.cycle) {
            case 1: return Random.NormalIntRange(30, 45);
            case 2: return Random.NormalIntRange(150, 184);
            case 3: return Random.NormalIntRange(540, 721);
            case 4: return Random.NormalIntRange(6000, 10000);
        }
		return Random.NormalIntRange( 1, 7 );
	}
	
	@Override
	public int attackSkill( Char target ) {
        switch (Dungeon.cycle){
            case 1: return 46;
            case 2: return 210;
            case 3: return 560;
            case 4: return 2000;
        }
		return 12;
	}
	
	@Override
	public int cycledDrRoll() {
        switch (Dungeon.cycle){
            case 1: return Random.NormalIntRange(8, 20);
            case 2: return Random.NormalIntRange(76, 150);
            case 3: return Random.NormalIntRange(300, 550);
            case 4: return Random.NormalIntRange(4000, 8500);
        }
		return Random.NormalIntRange(0, 4);
	}
}

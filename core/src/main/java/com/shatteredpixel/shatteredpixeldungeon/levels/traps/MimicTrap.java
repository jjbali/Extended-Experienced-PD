/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MimicTrap extends Trap {

	{
		color = YELLOW;
		shape = CROSSHAIR;
	}

	@Override
	public Trap hide() {
		//this one can't be hidden
		return reveal();
	}

	@Override
	public void activate() {
		Mimic mimic = null;
		int tries = 20;
		do{
			pos = Random.Int(Dungeon.level.length());
			tries --;
		} while (tries > 0 && (Dungeon.level.solid[pos] || Actor.findChar( pos ) != null));
		if (tries > 0) {
			mimic = Mimic.spawnAt(pos, Mimic.class);
			Sample.INSTANCE.play(Assets.Sounds.CURSED);
		}

		if (mimic != null) {
			mimic.adjustStats(Dungeon.depth + 8);
			mimic.HP = mimic.HT;
			Item reward;
			do {
				reward = Generator.random(Random.oneOf(Generator.Category.WEAPON, Generator.Category.ARMOR,
						Generator.Category.RING, Generator.Category.WAND));
			} while (reward.level() < 1);
			Sample.INSTANCE.play(Assets.Sounds.MIMIC, 1, 1, 0.5f);
			mimic.items.clear();
			mimic.items.add(reward);
		}
	}

}

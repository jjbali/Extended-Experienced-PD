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

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.AlarmTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BlazingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.BurningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ChillingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ConfusionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.CorrosionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.DisintegrationTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.DistortionTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ExplosiveTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FlashingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FlockTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.FrostTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrimTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrippingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.OozeTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.PoisonDartTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.ShockingTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.TeleportationTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.WarpingTrap;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomTrapfieldRoom extends StandardRoom {

	@Override
	public float[] sizeCatProbs() {
		return new float[]{4, 1, 0};
	}

	@Override
	public boolean canMerge(Level l, Point p, int mergeTerrain) {
		int cell = l.pointToCell(pointInside(p, 1));
		return l.map[cell] == Terrain.EMPTY;
	}

	@Override
	public void paint(Level level) {
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY );
		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

		int mines = (int)Math.round(Math.sqrt(square()));

		switch (sizeCat){
			case NORMAL:
				mines -= 3;
				break;
			case LARGE:
				mines += 3;
				break;
			case GIANT:
				mines += 9;
				break;
		}

		for (int i = 0; i < mines; i++ ) {
			int pos;
			int chances;
			do {
				pos = level.pointToCell(random(1));
				chances = Random.Int( 10 );
			} while (level.traps.get(pos) != null);

			//randomly places some embers around the mines
			for (int j = 0; j < 8; j++) {
				int c = PathFinder.NEIGHBOURS8[Random.Int(8)];
				if (level.traps.get(pos + c) == null && level.map[pos + c] == Terrain.EMPTY) {
					Painter.set(level, pos + c, Terrain.EMBERS);
				}
			}

			Painter.set(level, pos, Terrain.SECRET_TRAP);
			switch (chances) {
				case 0: default:
					level.setTrap(new ExplosiveTrap().hide(), pos);
					break;
				case 1:
					level.setTrap(new DisintegrationTrap().hide(), pos);
					break;
				case 2:
					level.setTrap(new ShockingTrap().hide(), pos);
					break;
				case 3:
					level.setTrap(new FrostTrap().hide(), pos);
					break;
				case 4:
					level.setTrap(new ChillingTrap().hide(), pos);
					break;
				case 5:
					level.setTrap(new WarpingTrap().hide(), pos);
					break;
				case 6:
					level.setTrap(new GrippingTrap().hide(), pos);
					break;
				case 7:
					level.setTrap(new OozeTrap().hide(), pos);
					break;
				case 8:
					level.setTrap(new SummoningTrap().hide(), pos);
					break;
				case 9:
					level.setTrap(new DistortionTrap().hide(), pos);
					break;
			}

		}

	}
}

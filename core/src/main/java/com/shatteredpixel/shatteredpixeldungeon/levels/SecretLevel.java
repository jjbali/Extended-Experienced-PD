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

package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.RatKing;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.SentryRoom;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

public class SecretLevel extends Level {


	private static final int SIZE = 8;

	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		locked = true;
	}

	@Override
	public String tilesTex() {
		return Assets.Environment.WATER_ARENA;
	}

	@Override
	public String waterTex() {
		return Assets.Environment.TILES_ARENA;
	}

	@Override
	protected boolean build() {

		setSize(SIZE + 2, SIZE + 2);

		for (int i=2; i < SIZE; i++) {
			for (int j=2; j < SIZE; j++) {
				map[i * width() + j] = Terrain.EMPTY;
			}
		}

		for (int i=1; i <= SIZE; i++) {
			map[width() + i] =
					map[width() * SIZE + i] =
							map[width() * i + 1] =
									map[width() * i + SIZE] =
											Terrain.WATER;
		}

		Painter.fill(this, 3, 3, 1, 3, Terrain.WALL);
		Painter.fill(this, 6, 3, 1, 3, Terrain.WALL);
		int entrance = SIZE * width() + SIZE / 2 + 1;
		map[entrance] = Terrain.WATER;

		if (Dungeon.branch == 0) {
			transitions.add(new LevelTransition(this, entrance, LevelTransition.Type.REGULAR_ENTRANCE));
		} else {
			transitions.add(new LevelTransition(this,
					entrance,
					LevelTransition.Type.BRANCH_ENTRANCE,
					Dungeon.depth,
					0,
					LevelTransition.Type.BRANCH_EXIT));
		}

		Mob ratsample = new RatKing();
		ratsample.pos = 23;
		GameScene.add(ratsample);

		ratsample = new RatKing();
		ratsample.pos = 26;
		GameScene.add(ratsample);

		return true;
	}

	@Override
	public Mob createMob() {
		return null;
	}

	@Override
	protected void createMobs() {
	}

	public Actor addRespawner() {
		return null;
	}

	@Override
	protected void createItems() {
		Item item = Bones.get();
		if (item != null) {
			drop( item, entrance()-width() ).setHauntedIfCursed().type = Heap.Type.REMAINS;
		}
	}

	@Override
	public int randomRespawnCell( Char ch ) {
		return entrance()-width();
	}

}

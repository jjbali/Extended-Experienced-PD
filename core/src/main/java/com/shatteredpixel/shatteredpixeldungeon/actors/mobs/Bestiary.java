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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.THE_TRUE_FATALITY;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class Bestiary {
	
	public static ArrayList<Class<? extends Mob>> getMobRotation( int depth ){
		ArrayList<Class<? extends Mob>> mobs = standardMobRotation( depth );
		addRareMobs(depth, mobs);
		addSuperRareMobs(depth, mobs);
		swapMobAlts(mobs);
		Random.shuffle(mobs);
		return mobs;
	}
	
	//returns a rotation of standard mobs, unshuffled.
	private static ArrayList<Class<? extends Mob>> standardMobRotation( int depth ){
		if (depth < 5 && depth > 0) {
			return new ArrayList<>(Arrays.asList(
					Rat.class,
					Snake.class,
					Gnoll.class,
					Swarm.class,
					Crab.class,
					Slime.class
			));
		} else if (depth < 10 && depth > 5) {
			return new ArrayList<>(Arrays.asList(
					Skeleton.class,
					Thief.class,
					Swarm.class,
					DM100.class,
					Guard.class,
					Necromancer.class
			));
		} else if (depth < 15 && depth > 10) {
			return new ArrayList<>(Arrays.asList(
					Bat.class,
					Brute.class,
					Shaman.random(),
					Spinner.class,
					DM200.class
			));
		} else if (depth < 20 && depth > 15) {
			return new ArrayList<>(Arrays.asList(
					Ghoul.class,
					Elemental.random(),
					Warlock.class,
					Monk.class,
					Golem.class
			));
		} else if (depth < 101 && depth > 20) {
			return new ArrayList<>(Arrays.asList(
					Succubus.class,
					Eye.class,
					Scorpio.class,
					RipperDemon.class
			));
		} else if (depth > 101) {
			return new ArrayList<>(Arrays.asList(
					Rat.class,
					Snake.class,
					Gnoll.class,
					Swarm.class,
					Crab.class,
					Slime.class,
					Skeleton.class,
					Thief.class,
					Swarm.class,
					DM100.class,
					Guard.class,
					Necromancer.class,
					Bat.class,
					Brute.class,
					Shaman.random(),
					Spinner.class,
					DM200.class,
					Ghoul.class,
					Elemental.random(),
					Warlock.class,
					Monk.class,
					Golem.class,
					Succubus.class,
					Eye.class,
					Scorpio.class,
					RipperDemon.class,
					OOFThief.class
			));
		} else {
			return null;
		}
	}
	
	//has a chance to add a rarely spawned mobs to the rotation
	public static void addRareMobs( int depth, ArrayList<Class<?extends Mob>> rotation ){
		if (Dungeon.isChallenged(THE_TRUE_FATALITY)) {
			rotation.add(Wraith.class);
			rotation.add(Wraith.class);
			rotation.add(Wraith.class);
			rotation.add(Wraith.class);
			if (Random.Float() < 0.05f) rotation.add(OOFThief.class);
		}

		switch (depth){

			// Sewers
			default:
				return;
			case 1: case 2: case 3: case 4:
				if (Random.Float() < 0.05f) rotation.add(Thief.class);
				if (Random.Float() < 0.05f) rotation.add(Skeleton.class);
				if (Random.Float() < 0.05f) rotation.add(Necromancer.class);
				if (Random.Float() < 0.05f) rotation.add(DM100.class);
				return;

			// Prison
			case 6: case 7: case 8: case 9:
				if (Random.Float() < 0.05f) rotation.add(Bat.class);
				if (Random.Float() < 0.05f) rotation.add(Brute.class);
				if (Random.Float() < 0.05f) rotation.add(Shaman.random());
				if (Random.Float() < 0.05f) rotation.add(DM200.class);
				return;

			// Caves
			case 11: case 12: case 13: case 14:
				if (Random.Float() < 0.05f) rotation.add(Ghoul.class);
				if (Random.Float() < 0.05f) rotation.add(Elemental.random());
				if (Random.Float() < 0.05f) rotation.add(Golem.class);
				if (Random.Float() < 0.05f) rotation.add(Monk.class);
				return;

			// City
			case 16: case 17: case 18: case 19:
				if (Random.Float() < 0.05f) rotation.add(Succubus.class);
				if (Random.Float() < 0.05f) rotation.add(Scorpio.class);
				if (Random.Float() < 0.05f) rotation.add(RipperDemon.class);
				return;
		}
	}
	public static void addSuperRareMobs(int depth, ArrayList<Class<?extends Mob>> rotation){

		switch (depth){

			// Sewers
			default:
				return;
			case 1: case 2: case 3: case 4:
				if (Random.Float() < 0.025f) rotation.add(Bat.class);
				if (Random.Float() < 0.025f) rotation.add(Shaman.random());
				return;

			// Prison
			case 6: case 7: case 8: case 9:
				if (Random.Float() < 0.025f) rotation.add(Elemental.random());
				if (Random.Float() < 0.025f) rotation.add(Monk.class);
				return;

			// Caves
			case 11: case 12: case 13: case 14:
				if (Random.Float() < 0.025f) rotation.add(Scorpio.class);
				if (Random.Float() < 0.025f) rotation.add(RipperDemon.class);
				return;

			// City
			case 16: case 17: case 18: case 19:
				if (Random.Float() < 0.025f) rotation.add(OOFThief.class);
				return;
		}
	}
	
	//switches out regular mobs for their alt versions when appropriate
	private static void swapMobAlts(ArrayList<Class<?extends Mob>> rotation){
		for (int i = 0; i < rotation.size(); i++){
			if (Random.Int( 10 ) == 0) {
				Class<? extends Mob> cl = rotation.get(i);
				if (cl == Rat.class) {
					cl = Albino.class;
				} else if (cl == Slime.class) {
					cl = CausticSlime.class;
				} else if (cl == Thief.class) {
					cl = Bandit.class;
				} else if (cl == Necromancer.class){
					cl = SpectralNecromancer.class;
				} else if (cl == Brute.class) {
					cl = ArmoredBrute.class;
				} else if (cl == DM200.class) {
					cl = DM201.class;
				} else if (cl == Monk.class) {
					cl = Senior.class;
				} else if (cl == Scorpio.class) {
					cl = Acidic.class;
				}
				rotation.set(i, cl);
			}
		}
	}
}

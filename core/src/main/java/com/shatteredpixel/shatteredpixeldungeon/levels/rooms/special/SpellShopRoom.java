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

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.ArcaneBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Firebomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Flashbang;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.FrostBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.HolyBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Noisemaker;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.RegrowthBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.ShockBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.ShrapnelBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.WoollyBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Alchemize;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.BeaconOfReturning;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.CurseInfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.FeatherFall;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.FireBooster;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.HandyBarricade;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicBridge;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicalPorter;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Metamorph;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PhaseShift;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ReclaimTrap;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Recycle;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.RespawnBooster;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.SummonElemental;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.TelekineticGrab;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Vampirism;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.WildEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.AdrenalineDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.BlindingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.ChillingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.CleansingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.DisplacingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.HealingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.HolyDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.IncendiaryDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.ParalyticDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.PoisonDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.RotDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.ShockingDart;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpellShopRoom extends SpecialRoom {

    protected ArrayList<Item> itemsToSpawn;
	
	@Override
	public int minWidth() {
		return  Math.max(6, (int)(Math.sqrt(itemCount())+3));
	}
	
	@Override
	public int minHeight() {
		return  Math.max(6, (int)(Math.sqrt(itemCount())+3));
	}

	public int itemCount(){
		if (itemsToSpawn == null) itemsToSpawn = generateItems();
		return itemsToSpawn.size();
	}
	
	public void paint( Level level ) {
		
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY_SP );

		placeItems( level );
		
		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

	}

	protected void placeShopkeeper( Level level ) {

		int pos = level.pointToCell(center());

		Mob shopkeeper = new Shopkeeper();
		shopkeeper.pos = pos;
		level.mobs.add( shopkeeper );

	}

	protected void placeItems( Level level ){

		if (itemsToSpawn == null){
			itemsToSpawn = generateItems();
		}

		Point entryInset = new Point(entrance());
		if (entryInset.y == top){
			entryInset.y++;
		} else if (entryInset.y == bottom) {
			entryInset.y--;
		} else if (entryInset.x == left){
			entryInset.x++;
		} else {
			entryInset.x--;
		}

		Point curItemPlace = entryInset.clone();

		int inset = 1;

		for (Item item : itemsToSpawn.toArray(new Item[0])) {

			//place items in a clockwise pattern
			if (curItemPlace.x == left+inset && curItemPlace.y != top+inset){
				curItemPlace.y--;
			} else if (curItemPlace.y == top+inset && curItemPlace.x != right-inset){
				curItemPlace.x++;
			} else if (curItemPlace.x == right-inset && curItemPlace.y != bottom-inset){
				curItemPlace.y++;
			} else {
				curItemPlace.x--;
			}

			//once we get to the inset from the entrance again, move another cell inward and loop
			if (curItemPlace.equals(entryInset)){

				if (entryInset.y == top+inset){
					entryInset.y++;
				} else if (entryInset.y == bottom-inset){
					entryInset.y--;
				}
				if (entryInset.x == left+inset){
					entryInset.x++;
				} else if (entryInset.x == right-inset){
					entryInset.x--;
				}
				inset++;

				if (inset > (Math.min(width(), height())-3)/2){
					break; //out of space!
				}

				curItemPlace = entryInset.clone();

				//make sure to step forward again
				if (curItemPlace.x == left+inset && curItemPlace.y != top+inset){
					curItemPlace.y--;
				} else if (curItemPlace.y == top+inset && curItemPlace.x != right-inset){
					curItemPlace.x++;
				} else if (curItemPlace.x == right-inset && curItemPlace.y != bottom-inset){
					curItemPlace.y++;
				} else {
					curItemPlace.x--;
				}
			}

			int cell = level.pointToCell(curItemPlace);
			level.drop( item.quantity(Random.Int(11, 41)), cell ).type = Heap.Type.FOR_SALE;
			itemsToSpawn.remove(item);
		}

		//we didn't have enough space to place everything neatly, so now just fill in anything left
		if (!itemsToSpawn.isEmpty()){
			for (Point p : getPoints()){
				int cell = level.pointToCell(p);
				if ((level.map[cell] == Terrain.EMPTY_SP || level.map[cell] == Terrain.EMPTY)
						&& level.heaps.get(cell) == null && level.findMob(cell) == null){
					level.drop( itemsToSpawn.remove(0), level.pointToCell(p) ).type = Heap.Type.FOR_SALE;
				}
				if (itemsToSpawn.isEmpty()){
					break;
				}
			}
		}

		if (!itemsToSpawn.isEmpty()){
			ShatteredPixelDungeon.reportException(new RuntimeException("failed to place all items in a shop!"));
		}

	}
	
	protected static ArrayList<Item> generateItems() {

		ArrayList<Item> itemsToSpawn = new ArrayList<>();

		itemsToSpawn.add( new Alchemize());
		itemsToSpawn.add( new AquaBlast());
		itemsToSpawn.add( new ArcaneCatalyst());
		itemsToSpawn.add( new BeaconOfReturning());
		itemsToSpawn.add( new CurseInfusion());
		itemsToSpawn.add( new FeatherFall());
		itemsToSpawn.add( new FireBooster());
		itemsToSpawn.add( new MagicalPorter());
		itemsToSpawn.add( new PhaseShift());
		itemsToSpawn.add( new ReclaimTrap());
		itemsToSpawn.add( new Recycle());
		itemsToSpawn.add( new RespawnBooster());
		itemsToSpawn.add( new SummonElemental());
		itemsToSpawn.add( new TelekineticGrab());
		itemsToSpawn.add( new Vampirism());
		itemsToSpawn.add( new WildEnergy());
		itemsToSpawn.add( new MagicBridge());
		itemsToSpawn.add( new HandyBarricade());

		if (Dungeon.Int(3) == 0) itemsToSpawn.add( new Metamorph());

		//use a new generator here to prevent items in shop stock affecting levelgen RNG (e.g. sandbags)
		//we can use a random long for the seed as it will be the same long every time
		Random.pushGenerator(Random.Long());
			Random.shuffle(itemsToSpawn);
		Random.popGenerator();

		return itemsToSpawn;
	}

}

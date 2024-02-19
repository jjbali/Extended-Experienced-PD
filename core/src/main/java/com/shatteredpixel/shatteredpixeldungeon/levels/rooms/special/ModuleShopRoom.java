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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SuperExp;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ChampionModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.CycleModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ExpBoostModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.HealthModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.MagicalSightModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.RewardBoostModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ScreenshotModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.StaminaModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.GoldModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.HasteModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.HealerModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ImmortalityModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ItemModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.LevelModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.LuckModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.MindVisionModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.OverloadModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.PotionModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.PurityModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ScrollModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.StrengthModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.TimeReverserModule;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ModuleShopRoom extends SpecialRoom {

    protected ArrayList<Item> itemsToSpawn;
	
	@Override
	public int minWidth() {
		return Math.max(10, (int)(Math.sqrt(itemCount())+3));
	}
	
	@Override
	public int minHeight() {
		return Math.max(10, (int)(Math.sqrt(itemCount())+3));
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
			door.set( Door.Type.CRYSTAL );
		}

		level.addItemToSpawn( new CrystalKey( Dungeon.depth ) );
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
			level.drop( item, cell ).type = Heap.Type.FOR_SALE;
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

		itemsToSpawn.add(new ChampionModule());
		itemsToSpawn.add(new CycleModule());
		if (hero.buff(SuperExp.class) == null) {
			itemsToSpawn.add(new ExpBoostModule());
		}
		itemsToSpawn.add(new GoldModule());
		itemsToSpawn.add(new HasteModule());
		itemsToSpawn.add(new HealerModule());
		itemsToSpawn.add(new ImmortalityModule());
		itemsToSpawn.add(new ItemModule());
		itemsToSpawn.add(new LevelModule());
		itemsToSpawn.add(new LuckModule());
		itemsToSpawn.add(new MindVisionModule());
		itemsToSpawn.add(new OverloadModule());
		itemsToSpawn.add(new PurityModule());
		//TODO add more modules?
		itemsToSpawn.add(new ScrollModule());
		itemsToSpawn.add(new PotionModule());
		itemsToSpawn.add(new StaminaModule());
		itemsToSpawn.add(new HealthModule());
		itemsToSpawn.add(new RewardBoostModule());
		itemsToSpawn.add(new StrengthModule());
		itemsToSpawn.add(new TimeReverserModule());
		itemsToSpawn.add(new ScreenshotModule());
		itemsToSpawn.add(new MagicalSightModule());



		//hard limit is 63 items + 1 shopkeeper, as shops can't be bigger than 8x8=64 internally
		if (itemsToSpawn.size() > 63) {
			throw new RuntimeException("Shop attempted to carry more than 63 items!");
		}

		//use a new generator here to prevent items in shop stock affecting levelgen RNG (e.g. sandbags)
		//we can use a random long for the seed as it will be the same long every time
		Random.pushGenerator(Random.Long());
		Random.shuffle(itemsToSpawn);
		Random.popGenerator();

		return itemsToSpawn;
	}

}

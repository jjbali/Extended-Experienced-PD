/*
 *
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2024 Trashbox Bobylev
 *
 * Extended Experienced Pixel Dungeon
 * Copyright (C) 2023-2024 John Nollas
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
 *
 */

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Belongings;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Elemental;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.items.*;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.*;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.StormBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.*;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Cheese;
import com.shatteredpixel.shatteredpixeldungeon.items.fragments.FragmentCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.DimensionalRiftModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ExpBoostModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.Module;
import com.shatteredpixel.shatteredpixeldungeon.items.notebook.Notebook;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.AdditionItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.MultiplicationItem;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Metamorph;
import com.shatteredpixel.shatteredpixeldungeon.items.test_tubes.TubeOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.totem.TotemOfFire;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.AlchemyBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.BiggerGambleBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.GambleBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.PotionBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.QualityBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.ScrollBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.SpellBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.TippedDartBag;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopRoom extends SpecialRoom {

    protected ArrayList<Item> itemsToSpawn;
	
	@Override
	public int minWidth() {
		return Math.max(9, (int)(Math.sqrt(itemCount())+3));
	}
	
	@Override
	public int minHeight() {
		return Math.max(9, (int)(Math.sqrt(itemCount())+3));
	}

	public int itemCount(){
		if (itemsToSpawn == null) itemsToSpawn = generateItems();
		return itemsToSpawn.size();
	}
	
	public void paint( Level level ) {
		
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY_SP );

		placeShopkeeper( level );

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

		switch (Dungeon.cycle){
			case 0: itemsToSpawn.add(new BasicFishingRod().upgrade(Random.Int(2, 5))); break;
			case 1: itemsToSpawn.add(new GoldenFishingRod().upgrade(Random.Int(2, 5))); break;
			case 2: itemsToSpawn.add(new NeutroniumFishingRod().upgrade(Random.Int(2, 5))); break;
			case 3: itemsToSpawn.add(new AvaritiaFishingRod().upgrade(Random.Int(2, 5))); break;
			case 4: default: itemsToSpawn.add(new ChaosFishingRod().upgrade(Random.Int(2, 5))); break;
		}

		itemsToSpawn.add( Generator.random(Generator.Category.SCROLL) );
		itemsToSpawn.add( Generator.random(Generator.Category.EXSCROLL) );
		itemsToSpawn.add( Generator.random(Generator.Category.POTION) );
		itemsToSpawn.add( Generator.random(Generator.Category.EXPOTION) );
		itemsToSpawn.add( Generator.randomMissile() );
		itemsToSpawn.add( Generator.randomArtifact() );
		itemsToSpawn.add( Generator.randomArmor() );
		itemsToSpawn.add( new Ankh() );
		itemsToSpawn.add( Generator.random(Generator.Category.FOOD) );
		itemsToSpawn.add( Generator.random(Generator.Category.FOOD) );
		itemsToSpawn.add( Generator.random(Generator.Category.TREASUREBAG) );
		itemsToSpawn.add( Generator.random(Generator.Category.TREASUREBAG) );
		itemsToSpawn.add( Generator.random(Generator.Category.WEAPON) );
		itemsToSpawn.add( Generator.random(Generator.Category.WEAPON) );
		itemsToSpawn.add( new Module().random(1) );
		itemsToSpawn.add( Generator.random(Generator.Category.STONE) );
		itemsToSpawn.add( Generator.random(Generator.Category.ARTIFACT) );
		itemsToSpawn.add(Generator.random(Generator.Category.RING));
		itemsToSpawn.add(Generator.random(Generator.Category.RING));
		itemsToSpawn.add( new RandomItemTicket() );
		itemsToSpawn.add( new DimensionalRiftModule() );

		if (MultiplicationItem.streak_c >= 20) {
			itemsToSpawn.add(new TicketToArena());
			itemsToSpawn.add(new TicketToSecondArena());
			itemsToSpawn.add(new TicketToThirdArena());
			itemsToSpawn.add(new TicketToFourthArena());
		}

		if ( Random.Int(10) == 0 ) {
			itemsToSpawn.add( new Metamorph() );
		}

		if ( Random.Int(250) == 0 ) {
			itemsToSpawn.add( new Cheese() );
		}

		if ( Random.Int(600) == 0 ) {
			itemsToSpawn.add( new ExpBoostModule() );
		}

		if ( Random.Int(450) == 0 ) {
			itemsToSpawn.add( new FragmentCatalyst() );
		}

		if ( Random.Int(10) == 0 ) {
			itemsToSpawn.add( Generator.random(Generator.Category.TUBES) );
		}

			if (hero.belongings.getItem(VelvetPouch.class) == null) {
				itemsToSpawn.add(new VelvetPouch());
			}
			if (hero.belongings.getItem(ScrollHolder.class) == null) {
				itemsToSpawn.add(new ScrollHolder());
			}
			if (hero.belongings.getItem(PotionBandolier.class) == null) {
				itemsToSpawn.add(new PotionBandolier());
			}
			if (hero.belongings.getItem(MagicalHolster.class) == null) {
				itemsToSpawn.add(new MagicalHolster());
			}
			if (hero.belongings.getItem(TreasureHolder.class) == null) {
				itemsToSpawn.add(new TreasureHolder());
			}
			if (hero.belongings.getItem(CheeseCheest.class) == null) {
				itemsToSpawn.add(new CheeseCheest());
			}
			if (hero.belongings.getItem(Notebook.class) == null) {
				itemsToSpawn.add(new Notebook());
			}
			if (hero.belongings.getItem(NotebookHolder.class) == null) {
				itemsToSpawn.add(new NotebookHolder());
			}

		if (Dungeon.Int(20) == 0) {
			itemsToSpawn.add( Generator.random(Generator.Category.WEP_JJBALI) );
		}

		if (AdditionItem.streak_a >= 100) {
			itemsToSpawn.add(new StormBomb());
		}

		if (!Dungeon.oofedItems.isEmpty()) {
			Item lol = Dungeon.oofedItems.get(Random.index(Dungeon.oofedItems));
			if (lol != null){
				lol.wereOofed = true;
				itemsToSpawn.add(lol);
				Dungeon.oofedItems.remove(lol);
			}
		}

		//use a new generator here to prevent items in shop stock affecting levelgen RNG (e.g. sandbags)
		//we can use a random long for the seed as it will be the same long every time
		Random.pushGenerator(Random.Long());
			Random.shuffle(itemsToSpawn);
		Random.popGenerator();

		return itemsToSpawn;
	}

}

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

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.cycle;
import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.depth;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.ArcaneResin;
import com.shatteredpixel.shatteredpixeldungeon.items.ExpGenerator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.OverloadBeacon;
import com.shatteredpixel.shatteredpixeldungeon.items.RandomBuffGiver;
import com.shatteredpixel.shatteredpixeldungeon.items.RandomItemTicket;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToArena;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToFourthArena;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToSecondArena;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToThirdArena;
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
import com.shatteredpixel.shatteredpixeldungeon.items.food.Cheese;
import com.shatteredpixel.shatteredpixeldungeon.items.food.FrozenCarpaccio;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MeatPie;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Pasty;
import com.shatteredpixel.shatteredpixeldungeon.items.food.SmallRation;
import com.shatteredpixel.shatteredpixeldungeon.items.food.StewedMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCorrosiveGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfDragonsBreath;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfEarthenArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfHolyFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfMagicalSight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfMastery;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfShielding;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfShroudingFog;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfSnapFreeze;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStamina;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfArcana;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfElements;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfEvasion;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfForce;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfFuror;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfTenacity;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfAntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfConfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfDivination;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfDread;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfMysticalEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPassage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPolymorph;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPrismaticImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Alchemize;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.BeaconOfReturning;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.CurseInfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Evolution;
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
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.AlchemyBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.BiggerGambleBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.GambleBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.MiscBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.PotionBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.QualityBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.ScrollBag;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfBlastWave;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorrosion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfCorruption;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfDisintegration;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLightning;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfPrismaticLight;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfRegrowth;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfTransfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfWarding;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.BlacksmithWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.FantasmalStabber;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.FiringSnapper;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.GleamingStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.RegrowingSlasher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.StarlightSmasher;
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

public class ItemShopRoom extends SpecialRoom {

    protected ArrayList<Item> itemsToSpawn;

	@Override
	public int minWidth() {
		return 15;
	}

	@Override
	public int maxWidth() {
		return 15;
	}
	@Override
	public int minHeight() {
		return 15;
	}

	@Override
	public int maxHeight() {
		return 15;
	}


	public int itemCount(){
		if (itemsToSpawn == null) itemsToSpawn = generateItems();
		return itemsToSpawn.size();
	}
	
	public void paint( Level level ) {

		Painter.fill(level, this, Terrain.WALL);
		Painter.fill(level, this, 1, Terrain.EMPTY_SP);


		placeItems(level);

		for (Door door : connected.values()) {
			door.set(Door.Type.REGULAR);
		}

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
			if (item instanceof Ring || item instanceof Wand) {
				if (cycle == 0) {
					level.drop(item.upgrade(Dungeon.NormalLongRange(2, 11) * depth).identify(), cell).type = Heap.Type.FOR_SALE;
				} else {
					level.drop(item.upgrade((Dungeon.NormalLongRange(50, 60) * depth) * (cycle + 1) ^ 2).identify(), cell).type = Heap.Type.FOR_SALE;
				}
			} else {
				level.drop( item.quantity(Dungeon.NormalLongRange(2, 6) * depth), cell ).type = Heap.Type.FOR_ARENA_SALE;
			}
			//level.drop( item, cell ).type = Heap.Type.FOR_SALE;
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
			// POTIONS
				itemsToSpawn.add(new PotionOfStrength());
				itemsToSpawn.add(new PotionOfHealing());
				itemsToSpawn.add(new PotionOfMindVision());
				itemsToSpawn.add(new PotionOfFrost());
				itemsToSpawn.add(new PotionOfLiquidFlame());
				itemsToSpawn.add(new PotionOfToxicGas());
				itemsToSpawn.add(new PotionOfHaste());
				itemsToSpawn.add(new PotionOfInvisibility());
				itemsToSpawn.add(new PotionOfLevitation());
				itemsToSpawn.add(new PotionOfParalyticGas());
				itemsToSpawn.add(new PotionOfPurity());
				itemsToSpawn.add(new PotionOfExperience());

			// SCROLLS
				itemsToSpawn.add(new ScrollOfUpgrade());
				itemsToSpawn.add(new ScrollOfIdentify());
				itemsToSpawn.add(new ScrollOfRemoveCurse());
				itemsToSpawn.add(new ScrollOfMirrorImage());
				itemsToSpawn.add(new ScrollOfRecharging());
				itemsToSpawn.add(new ScrollOfTeleportation());
				itemsToSpawn.add(new ScrollOfLullaby());
				itemsToSpawn.add(new ScrollOfMagicMapping());
				itemsToSpawn.add(new ScrollOfRage());
				itemsToSpawn.add(new ScrollOfRetribution());
				itemsToSpawn.add(new ScrollOfTerror());
				itemsToSpawn.add(new ScrollOfTransmutation());
				
			// EXOTICPOTION
				itemsToSpawn.add(new PotionOfCleansing());
				itemsToSpawn.add(new PotionOfCorrosiveGas());
				itemsToSpawn.add(new PotionOfDragonsBreath());
				itemsToSpawn.add(new PotionOfEarthenArmor());
				itemsToSpawn.add(new PotionOfHolyFuror());
				itemsToSpawn.add(new PotionOfMagicalSight());
				itemsToSpawn.add(new PotionOfMastery());
				itemsToSpawn.add(new PotionOfShielding());
				itemsToSpawn.add(new PotionOfShroudingFog());
				itemsToSpawn.add(new PotionOfSnapFreeze());
				itemsToSpawn.add(new PotionOfStamina());
				itemsToSpawn.add(new PotionOfStormClouds());
				
			// EXOTICSCROLL
				itemsToSpawn.add(new ScrollOfDivination());
				itemsToSpawn.add(new ScrollOfEnchantment());
				itemsToSpawn.add(new ScrollOfAntiMagic());
				itemsToSpawn.add(new ScrollOfSirensSong());
				itemsToSpawn.add(new ScrollOfConfusion());
				itemsToSpawn.add(new ScrollOfDread());
				itemsToSpawn.add(new ScrollOfMysticalEnergy());
				itemsToSpawn.add(new ScrollOfForesight());
				itemsToSpawn.add(new ScrollOfPassage());
				itemsToSpawn.add(new ScrollOfPsionicBlast());
				itemsToSpawn.add(new ScrollOfPrismaticImage());
				itemsToSpawn.add(new ScrollOfPolymorph());
				
			// TREASUREBAGS
				itemsToSpawn.add(new AlchemyBag());
				itemsToSpawn.add(new BiggerGambleBag());
				itemsToSpawn.add(new GambleBag());
				itemsToSpawn.add(new MiscBag());
				itemsToSpawn.add(new PotionBag());
				itemsToSpawn.add(new QualityBag());
				itemsToSpawn.add(new ScrollBag());
				
			// WANDS
				itemsToSpawn.add( new WandOfMagicMissile());
				itemsToSpawn.add( new WandOfLightning());
				itemsToSpawn.add( new WandOfDisintegration());
				itemsToSpawn.add( new WandOfFireblast());
				itemsToSpawn.add( new WandOfCorrosion());
				itemsToSpawn.add( new WandOfBlastWave());
				itemsToSpawn.add( new WandOfLivingEarth());
				itemsToSpawn.add( new WandOfFrost());
				itemsToSpawn.add( new WandOfPrismaticLight());
				itemsToSpawn.add( new WandOfWarding());
				itemsToSpawn.add( new WandOfTransfusion());
				itemsToSpawn.add( new WandOfCorruption());
				itemsToSpawn.add( new WandOfRegrowth());

			// BOMBS
				itemsToSpawn.add( new ArcaneBomb());
				itemsToSpawn.add( new Bomb());
				itemsToSpawn.add( new Firebomb());
				itemsToSpawn.add( new Flashbang());
				itemsToSpawn.add( new FrostBomb());
				itemsToSpawn.add( new HolyBomb());
				itemsToSpawn.add( new Noisemaker());
				itemsToSpawn.add( new RegrowthBomb());
				itemsToSpawn.add( new ShockBomb());
				itemsToSpawn.add( new ShrapnelBomb());
				itemsToSpawn.add( new WoollyBomb());

			// TIPPEDDARTS
				itemsToSpawn.add( new AdrenalineDart());
				itemsToSpawn.add( new BlindingDart());
				itemsToSpawn.add( new ChillingDart());
				itemsToSpawn.add( new CleansingDart());
				itemsToSpawn.add( new DisplacingDart());
				itemsToSpawn.add( new HealingDart());
				itemsToSpawn.add( new HolyDart());
				itemsToSpawn.add( new IncendiaryDart());
				itemsToSpawn.add( new ParalyticDart());
				itemsToSpawn.add( new PoisonDart());
				itemsToSpawn.add( new RotDart());
				itemsToSpawn.add( new ShockingDart());

			// SPELLS :)
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
				itemsToSpawn.add( new Evolution());
				itemsToSpawn.add( new MagicBridge());
				itemsToSpawn.add( new HandyBarricade());

				if (Dungeon.Int(3) == 0) itemsToSpawn.add( new Metamorph());

			//MISCS (RANDOMIZED)
				if (Dungeon.Int(2) == 0) {
					itemsToSpawn.add( new RandomItemTicket());
					itemsToSpawn.add( new SmallRation());
					itemsToSpawn.add( new Pasty());
					itemsToSpawn.add( new OverloadBeacon());
				} else {
					//itemsToSpawn.add( new ExpGenerator());
					itemsToSpawn.add( new Ankh());
					itemsToSpawn.add( new ArcaneResin());
					itemsToSpawn.add( new AlchemicalCatalyst());
					itemsToSpawn.add( new StewedMeat());
					itemsToSpawn.add( new MeatPie());
				}

				if (Dungeon.Int(100) == 0) {
					itemsToSpawn.add( new Cheese() );
				}

			// RINGS
				itemsToSpawn.add( new RingOfAccuracy());
				itemsToSpawn.add( new RingOfArcana());
				itemsToSpawn.add( new RingOfElements());
				itemsToSpawn.add( new RingOfEnergy());
				itemsToSpawn.add( new RingOfEvasion());
				itemsToSpawn.add( new RingOfForce());
				itemsToSpawn.add( new RingOfFuror());
				itemsToSpawn.add( new RingOfHaste());
				itemsToSpawn.add( new RingOfMight());
				itemsToSpawn.add( new RingOfSharpshooting());
				itemsToSpawn.add( new RingOfTenacity());

			//Blacksmith Items
				//itemsToSpawn.add( new FantasmalStabber());
				//itemsToSpawn.add( new FiringSnapper());
				//itemsToSpawn.add( new GleamingStaff());
				//itemsToSpawn.add( new RegrowingSlasher());
				//itemsToSpawn.add( new StarlightSmasher());

			//Spellbooks
				//itemsToSpawn.add( new BookOfBlast() );
				//itemsToSpawn.add( new BookOfCorruption() );
				//itemsToSpawn.add( new BookOfCorrosion() );
				//itemsToSpawn.add( new BookOfDisintegration() );
				//itemsToSpawn.add( new BookOfEarth() );
				//itemsToSpawn.add( new BookOfFire() );
				//itemsToSpawn.add( new BookOfFrost() );
				//itemsToSpawn.add( new BookOfLight() );
				//itemsToSpawn.add( new BookOfMagic() );
				//itemsToSpawn.add( new BookOfRegrowth() );
				//itemsToSpawn.add( new BookOfThunderBolt() );
				//itemsToSpawn.add( new BookOfTransfusion() );
				//itemsToSpawn.add( new BookOfWarding() );



				
				
				
			// TODO add more items if possible :)
				
				
			//hard limit is 63 items + 1 shopkeeper, as shops can't be bigger than 8x8=64 internally
			if (itemsToSpawn.size() > 169) {
				throw new RuntimeException("Shop attempted to carry more than 121 items!");
			}
	
			//use a new generator here to prevent items in shop stock affecting levelgen RNG (e.g. sandbags)
			//we can use a random long for the seed as it will be the same long every time
			Random.pushGenerator(Random.Long());
				Random.shuffle(itemsToSpawn);
			Random.popGenerator();
	
			return itemsToSpawn;
	}

}

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

package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.*;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.*;
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
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.StormBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.WoollyBomb;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.AvaritiaFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.BasicFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.ChaosFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.FishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.GoldenFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.NeutroniumFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Berry;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Blandfruit;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChargrilledMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Cheese;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.FrozenCarpaccio;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MeatPie;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Pasty;
import com.shatteredpixel.shatteredpixeldungeon.items.food.PhantomMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.SmallRation;
import com.shatteredpixel.shatteredpixeldungeon.items.food.StewedMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ChampionModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.CycleModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.DimensionalRiftModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ExpBoostModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.GoldModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.HasteModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.HealerModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.HealthModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ImmortalityModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ItemModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.LevelModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.LuckModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.MindVisionModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.OverloadModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.PotionModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.PurityModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.RewardBoostModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ScreenshotModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.ScrollModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.StaminaModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.StrengthModule;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.TimeReverserModule;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.*;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.BlizzardBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.Brew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.CausticBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.InfernalBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.ShockingBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.Elixir;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfDragonsBlood;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.ExoticPotion;
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
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CorpseDust;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Embers;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.*;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.*;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ExoticScroll;
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
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicalInfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PhaseShift;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ReclaimTrap;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Recycle;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Spell;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.SummonElemental;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.TelekineticGrab;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.WildEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.*;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.AlchemyBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.BiggerGambleBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.DKTreasureBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.DM300TreasureBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.GambleBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.GooTreasureBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.MiscBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.PotionBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.QualityBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.ScrollBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.SpellBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.TenguTreasureBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.TippedDartBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.TreasureBag;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.*;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.*;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.BlacksmithWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.FantasmalStabber;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.FiringSnapper;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.GleamingStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.RegrowingSlasher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.blacksmith.StarlightSmasher;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.*;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.AdrenalineDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.BlindingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.ChillingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.CleansingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.Dart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.DisplacingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.HealingDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.HolyDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.IncendiaryDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.ParalyticDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.PoisonDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.RotDart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.ShockingDart;
import com.shatteredpixel.shatteredpixeldungeon.plants.*;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {


    public enum Category {
		WEAPON	( 2, 2, MeleeWeapon.class),
		WEP_T1	( 5, 0, MeleeWeapon.class),
		WEP_T2	( 4, 0, MeleeWeapon.class),
		WEP_T3	( 3, 0, MeleeWeapon.class),
		WEP_T4	( 2, 0, MeleeWeapon.class),
		WEP_T5	( 1, 0, MeleeWeapon.class),
		ARMOR	( 2, 1, Armor.class ),
		MISSILE ( 1, 2, MissileWeapon.class ),
		MIS_T1  ( 1, 0, MissileWeapon.class ),
		MIS_T2  ( 1, 0, MissileWeapon.class ),
		MIS_T3  ( 1, 0, MissileWeapon.class ),
		MIS_T4  ( 1, 0, MissileWeapon.class ),
		MIS_T5  ( 1, 0, MissileWeapon.class ),
		WAND	( 1, 1, Wand.class ),
		RING	( 1, 1, Ring.class ),
		ARTIFACT( 2, 0, Artifact.class),
		FOOD	( 10, 3, Food.class ),
		POTION	( 5, 3, Potion.class ),
		SEED	( 5, 5, Plant.Seed.class ),
		
		SCROLL	( 5, 5, Scroll.class ),
		STONE   ( 5, 5, Runestone.class),
		TREASUREBAG(2, 0, TreasureBag.class),
		EXPOTION(2, 0, ExoticPotion.class),
		EXSCROLL(2, 0, ExoticScroll.class),
		//new generator, because of potionofdebug.
		TICKETS (0, 0, TicketToArena.class),
		SPELLS (0, 0, Spell.class),
		BLACKSMITH (0, 0, BlacksmithWeapon.class),
		MISCS(0, 0, Item.class),
		FISHINGROD(0, 0, FishingRod.class),
		BOMB(0, 0, Bomb.class),
		BREWS(0, 0, Brew.class),
		ELIXIR(0, 0, Elixir.class),
		MISCS2(0, 0, Item.class),
		DARTS(0, 0, Dart.class),
		MODULES(0, 0, Item.class),
		MODULES2(0, 0, Item.class),

		GOLD	( 3, 3,   Gold.class );

		
		public Class<?>[] classes;

		//some item types use a deck-based system, where the probs decrement as items are picked
		// until they are all 0, and then they reset. Those generator classes should define
		// defaultProbs. If defaultProbs is null then a deck system isn't used.
		//Artifacts in particular don't reset, no duplicates!
		public float[] probs;
		public float[] defaultProbs = null;
		//These variables are used as a part of the deck system, to ensure that drops are consistent
		// regardless of when they occur (either as part of seeded levelgen, or random item drops)
		public Long seed = null;
		public int dropped = 0;

		//game has two decks of 35 items for overall category probs
		//one deck has a ring and extra armor, the other has an artifact and extra thrown weapon
		//Note that pure random drops only happen as part of levelgen atm, so no seed is needed here
		public float firstProb;
		public float secondProb;
		public Class<? extends Item> superClass;
		
		private Category( float firstProb, float secondProb, Class<? extends Item> superClass ) {
			this.firstProb = firstProb;
			this.secondProb = secondProb;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}

			//items without a category-defined order are sorted based on the spritesheet
			return Short.MAX_VALUE+item.image();
		}

		static {
			MODULES2.classes = new Class<?>[]{
					LuckModule.class,
					MindVisionModule.class,
					OverloadModule.class,
					PotionModule.class,
					PurityModule.class,
					RewardBoostModule.class,
					ScreenshotModule.class,
					ScrollModule.class,
					StaminaModule.class,
					StrengthModule.class,
					TimeReverserModule.class,
					DimensionalRiftModule.class
			};
			MODULES.classes = new Class<?>[]{
					ChampionModule.class,
					CycleModule.class,
					ExpBoostModule.class,
					GoldModule.class,
					HasteModule.class,
					HealerModule.class,
					HealthModule.class,
					ImmortalityModule.class,
					ItemModule.class,
					LevelModule.class
			};
			DARTS.classes = new Class<?>[]{
					AdrenalineDart.class,
					BlindingDart.class,
					ChillingDart.class,
					CleansingDart.class,
					DisplacingDart.class,
					HealingDart.class,
					HolyDart.class,
					IncendiaryDart.class,
					ParalyticDart.class,
					PoisonDart.class,
					RotDart.class,
					ShockingDart.class,
					Dart.class
			};
			MISCS2.classes = new Class<?>[]{
					SpyGlass.class,
					OverloadBeacon.class,
					Teleporter.class,
					MerchantsBeacon.class,
					ExpGenerator.class,
					BrokenSeal.class,
					CreativeGloves.class
			};

			ELIXIR.classes = new Class<?>[]{
					ElixirOfAquaticRejuvenation.class,
					ElixirOfArcaneArmor.class,
					ElixirOfDragonsBlood.class,
					ElixirOfHoneyedHealing.class,
					ElixirOfIcyTouch.class,
					ElixirOfMight.class,
					ElixirOfToxicEssence.class
			};

			BREWS.classes = new Class<?>[]{
					BlizzardBrew.class,
					CausticBrew.class,
					InfernalBrew.class,
					ShockingBrew.class
			};

			BOMB.classes = new Class<?>[]{
					ArcaneBomb.class,
					Firebomb.class,
					Flashbang.class,
					FrostBomb.class,
					HolyBomb.class,
					Noisemaker.class,
					RegrowthBomb.class,
					ShockBomb.class,
					ShrapnelBomb.class,
					WoollyBomb.class,
					StormBomb.class
			};

			FISHINGROD.classes = new Class<?>[]{
					BasicFishingRod.class,
					GoldenFishingRod.class,
					AvaritiaFishingRod.class,
					NeutroniumFishingRod.class,
					ChaosFishingRod.class
			};

			MISCS.classes = new Class<?>[]{
					Torch.class,
					GooBlob.class,
					MetalShard.class,
					Honeypot.class,
					Ankh.class,
					Waterskin.class,
					Stylus.class,
					KingsCrown.class,
					TengusMask.class,
					LiquidMetal.class,
					ArcaneResin.class,
					Embers.class,
					CorpseDust.class,
					RandomBuffGiver.class,
					RandomItemTicket.class,
					ArcaneCatalyst.class,
					AlchemicalCatalyst.class
			};

			BLACKSMITH.classes = new Class<?>[]{
					FantasmalStabber.class,
					FiringSnapper.class,
					GleamingStaff.class,
					RegrowingSlasher.class,
					StarlightSmasher.class
			};

			SPELLS.classes = new Class<?>[] {
					Alchemize.class,
					AquaBlast.class,
					BeaconOfReturning.class,
					CurseInfusion.class,
					FeatherFall.class,
					TelekineticGrab.class,
					PhaseShift.class,
					ReclaimTrap.class,
					Recycle.class,
					WildEnergy.class,
					SummonElemental.class,
					FireBooster.class,
					HandyBarricade.class,
					MagicBridge.class
			};

			TICKETS.classes = new Class<?>[]{
					TicketToArena.class,
					TicketToSecondArena.class,
					TicketToThirdArena.class,
					TicketToFourthArena.class
			};


			GOLD.classes = new Class<?>[]{
					Gold.class,
					EnergyCrystal.class
			};
			GOLD.probs = new float[]{ 1, 0 };

			TREASUREBAG.classes = new Class<?>[]{
					AlchemyBag.class,
					BiggerGambleBag.class,
					GambleBag.class,
					MiscBag.class,
					PotionBag.class,
					QualityBag.class,
					ScrollBag.class,
					SpellBag.class,
					TippedDartBag.class,
					DKTreasureBag.class,
					DM300TreasureBag.class,
					GooTreasureBag.class,
					TenguTreasureBag.class

			};
			TREASUREBAG.probs = new float[]{ 5, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0 };

			EXPOTION.classes = new Class<?>[]{
					PotionOfCleansing.class,
					PotionOfCorrosiveGas.class,
					PotionOfDragonsBreath.class,
					PotionOfEarthenArmor.class,
					PotionOfHolyFuror.class,
					PotionOfMagicalSight.class,
					PotionOfMastery.class,
					PotionOfShielding.class,
					PotionOfShroudingFog.class,
					PotionOfSnapFreeze.class,
					PotionOfStamina.class,
					PotionOfStormClouds.class
			};
			EXPOTION.probs = new float[]{ 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

			EXSCROLL.classes = new Class<?>[]{
					ScrollOfDivination.class,
					ScrollOfEnchantment.class,
					ScrollOfAntiMagic.class,
					ScrollOfSirensSong.class,
					ScrollOfConfusion.class,
					ScrollOfDread.class,
					ScrollOfMysticalEnergy.class,
					ScrollOfForesight.class,
					ScrollOfPassage.class,
					ScrollOfPsionicBlast.class,
					ScrollOfPrismaticImage.class,
					ScrollOfPolymorph.class
			};
			EXSCROLL.probs = new float[]{ 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
			
			POTION.classes = new Class<?>[]{
					PotionOfStrength.class,
					PotionOfHealing.class,
					PotionOfMindVision.class,
					PotionOfFrost.class,
					PotionOfLiquidFlame.class,
					PotionOfToxicGas.class,
					PotionOfHaste.class,
					PotionOfInvisibility.class,
					PotionOfLevitation.class,
					PotionOfParalyticGas.class,
					PotionOfPurity.class,
					PotionOfExperience.class};
			POTION.defaultProbs = new float[]{ 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
			POTION.probs = POTION.defaultProbs.clone();
			
			SEED.classes = new Class<?>[]{
					Rotberry.Seed.class, //quest item
					Sungrass.Seed.class,
					Fadeleaf.Seed.class,
					Icecap.Seed.class,
					Firebloom.Seed.class,
					Sorrowmoss.Seed.class,
					Swiftthistle.Seed.class,
					Blindweed.Seed.class,
					Stormvine.Seed.class,
					Earthroot.Seed.class,
					Mageroyal.Seed.class,
					Starflower.Seed.class};
			SEED.defaultProbs = new float[]{ 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2 };
			SEED.probs = SEED.defaultProbs.clone();
			
			SCROLL.classes = new Class<?>[]{
					ScrollOfUpgrade.class,
					ScrollOfIdentify.class,
					ScrollOfRemoveCurse.class,
					ScrollOfMirrorImage.class,
					ScrollOfRecharging.class,
					ScrollOfTeleportation.class,
					ScrollOfLullaby.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfRetribution.class,
					ScrollOfTerror.class,
					ScrollOfTransmutation.class
			};
			SCROLL.defaultProbs = new float[]{ 1, 3, 3, 4, 3, 5, 3, 4, 6, 4, 3, 2 };
			SCROLL.probs = SCROLL.defaultProbs.clone();
			
			STONE.classes = new Class<?>[]{
					StoneOfEnchantment.class,
					StoneOfIntuition.class,
					StoneOfDisarming.class,
					StoneOfFlock.class,
					StoneOfShock.class,
					StoneOfBlink.class,
					StoneOfDeepSleep.class,
					StoneOfClairvoyance.class,
					StoneOfAggression.class,
					StoneOfBlast.class,
					StoneOfFear.class,
					StoneOfAugmentation.class
			};
			STONE.defaultProbs = new float[]{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
			STONE.probs = STONE.defaultProbs.clone();

			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfCorrosion.class,
					WandOfBlastWave.class,
					WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					WandOfWarding.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class,
					WandOfUnstable.class
			};
			WAND.defaultProbs = new float[]{ 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0 };
			WAND.probs = WAND.defaultProbs.clone();
			
			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};
			
			WEP_T1.classes = new Class<?>[]{
					WornShortsword.class,
					MagesStaff.class,
					Dagger.class,
					Gloves.class,
					Rapier.class
			};
			WEP_T1.defaultProbs = new float[]{ 2, 0, 2, 2, 2 };
			WEP_T1.probs = WEP_T1.defaultProbs.clone();
			
			WEP_T2.classes = new Class<?>[]{
					Shortsword.class,
					HandAxe.class,
					Spear.class,
					Quarterstaff.class,
					Dirk.class,
					Sickle.class
			};
			WEP_T2.defaultProbs = new float[]{ 2, 2, 2, 2, 2, 2 };
			WEP_T2.probs = WEP_T2.defaultProbs.clone();
			
			WEP_T3.classes = new Class<?>[]{
					Sword.class,
					Mace.class,
					Scimitar.class,
					RoundShield.class,
					Sai.class,
					Whip.class
			};
			WEP_T3.defaultProbs = new float[]{ 2, 2, 2, 2, 2, 2 };
			WEP_T3.probs = WEP_T1.defaultProbs.clone();
			
			WEP_T4.classes = new Class<?>[]{
					Longsword.class,
					BattleAxe.class,
					Flail.class,
					RunicBlade.class,
					AssassinsBlade.class,
					Crossbow.class,
					Katana.class
			};
			WEP_T4.defaultProbs = new float[]{ 2, 2, 2, 2, 2, 2, 2 };
			WEP_T4.probs = WEP_T4.defaultProbs.clone();
			
			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					WarHammer.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
					Gauntlet.class,
					WarScythe.class
			};
			WEP_T5.defaultProbs = new float[]{ 2, 2, 2, 2, 2, 2, 2 };
			WEP_T5.probs = WEP_T5.defaultProbs.clone();
			
			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class,
					WarriorArmor.class,
					MageArmor.class,
					RogueArmor.class,
					HuntressArmor.class,
					DuelistArmor.class
			};
			ARMOR.probs = new float[]{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 };
			
			//see Generator.randomMissile
			MISSILE.classes = new Class<?>[]{};
			MISSILE.probs = new float[]{};
			
			MIS_T1.classes = new Class<?>[]{
					ThrowingStone.class,
					ThrowingKnife.class,
					ThrowingSpike.class
			};
			MIS_T1.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T1.probs = MIS_T1.defaultProbs.clone();
			
			MIS_T2.classes = new Class<?>[]{
					FishingSpear.class,
					ThrowingClub.class,
					Shuriken.class
			};
			MIS_T2.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T2.probs = MIS_T2.defaultProbs.clone();
			
			MIS_T3.classes = new Class<?>[]{
					ThrowingSpear.class,
					Kunai.class,
					Bolas.class
			};
			MIS_T3.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T3.probs = MIS_T3.defaultProbs.clone();
			
			MIS_T4.classes = new Class<?>[]{
					Javelin.class,
					Tomahawk.class,
					HeavyBoomerang.class
			};
			MIS_T4.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T4.probs = MIS_T4.defaultProbs.clone();
			
			MIS_T5.classes = new Class<?>[]{
					Trident.class,
					ThrowingHammer.class,
					ForceCube.class
			};
			MIS_T5.defaultProbs = new float[]{ 3, 3, 3 };
			MIS_T5.probs = MIS_T5.defaultProbs.clone();
			
			FOOD.classes = new Class<?>[]{
					Food.class,
					Pasty.class,
					MysteryMeat.class,
					Cheese.class,
					// Bloodline
					Berry.class,
					Blandfruit.class,
					ChargrilledMeat.class,
					FrozenCarpaccio.class,
					MeatPie.class,
					PhantomMeat.class,
					SmallRation.class,
					StewedMeat.class
			};
			FOOD.defaultProbs = new float[]{ 5, 5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			FOOD.probs = FOOD.defaultProbs.clone();
			
			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfArcana.class,
					RingOfElements.class,
					RingOfEnergy.class,
					RingOfEvasion.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class};
			RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			
			ARTIFACT.classes = new Class<?>[]{
					AlchemistsToolkit.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					DriedRose.class,
					EtherealChains.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class
			};
			ARTIFACT.defaultProbs = new float[]{ 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 };
			ARTIFACT.probs = ARTIFACT.defaultProbs.clone();
		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{10, 75, 20, 14, 11},
			{10, 25, 50, 20, 15},
			{10, 10, 40, 50, 10},
			{10, 10, 20, 40, 40},
			{10, 10, 10, 20, 80},
            {10, 25, 25, 25, 25}
	};

	private static boolean usingFirstDeck = false;
	private static HashMap<Category,Float> defaultCatProbs = new LinkedHashMap<>();
	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();

	public static void fullReset() {
		usingFirstDeck = Random.Int(2) == 0;
		generalReset();
		for (Category cat : Category.values()) {
			reset(cat);
			if (cat.defaultProbs != null) {
				cat.seed = Random.Long();
				cat.dropped = 0;
			}
		}
	}

	public static void generalReset(){
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, usingFirstDeck ? cat.firstProb : cat.secondProb );
			defaultCatProbs.put( cat, cat.firstProb + cat.secondProb );
		}
	}

	public static void reset(Category cat){
		if (cat.defaultProbs != null) cat.probs = cat.defaultProbs.clone();
	}

	//reverts changes to drop chances generates by this item
	//equivalent of shuffling the card back into the deck, does not preserve order!
	public static void undoDrop(Item item){
		for (Category cat : Category.values()){
			if (item.getClass().isAssignableFrom(cat.superClass)){
				if (cat.defaultProbs == null) continue;
				for (int i = 0; i < cat.classes.length; i++){
					if (item.getClass() == cat.classes[i]){
						cat.probs[i]++;
					}
				}
			}
		}
	}
	
	public static Item random() {
		Category cat = Dungeon.chances( categoryProbs );
		if (cat == null){
			usingFirstDeck = !usingFirstDeck;
			generalReset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);

		if (cat == Category.SEED) {
			//We specifically use defaults for seeds here because, unlike other item categories
			// their predominant source of drops is grass, not levelgen. This way the majority
			// of seed drops still use a deck, but the few that are spawned by levelgen are consistent
			return randomUsingDefaults(cat);
		} else {
			return random(cat);
		}
	}

	public static Item randomUsingDefaults(){
		return randomUsingDefaults(Random.chances( defaultCatProbs ));
	}

	public static Item random( Category cat ) {
		if (Dungeon.Int(150) == 0 &&
			cat != Category.WAND &&
			cat != Category.WEAPON &&
			!Arrays.asList(wepTiers).contains(cat) &&
			!Arrays.asList(misTiers).contains(cat) &&
			cat != Category.RING) {
			return new ElixirOfMight();
		}
		switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case MISSILE:
				return randomMissile();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				if (cat.defaultProbs != null && cat.seed != null){
					Random.pushGenerator(cat.seed);
					for (int i = 0; i < cat.dropped; i++) Random.Long();
				}

				int i = Random.chances(cat.probs);
				if (i == -1) {
					reset(cat);
					i = Random.chances(cat.probs);
				}
				if (cat.defaultProbs != null) cat.probs[i]--;

				if (cat.defaultProbs != null && cat.seed != null){
					Random.popGenerator();
					cat.dropped++;
				}

				return ((Item) Reflection.newInstance(cat.classes[i])).random();
		}
	}

	//overrides any deck systems and always uses default probs
	// except for artifacts, which must always use a deck
	public static Item randomUsingDefaults( Category cat ){
		if (cat == Category.WEAPON){
			return randomWeapon(true);
		} else if (cat == Category.MISSILE){
			return randomMissile(true);
		} else if (cat.defaultProbs == null || cat == Category.ARTIFACT) {
			return random(cat);
		} else {
			return ((Item) Reflection.newInstance(cat.classes[Random.chances(cat.defaultProbs)])).random();
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		return Reflection.newInstance(cl).random();
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);
		
		Armor a = (Armor)Reflection.newInstance(Category.ARMOR.classes[Dungeon.chances(floorSetTierProbs[floorSet])]);
		a.random();
		return a;
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5
	};

	public static MeleeWeapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}

	public static MeleeWeapon randomWeapon(int floorSet) {
		return randomWeapon(floorSet, false);
	}

	public static MeleeWeapon randomWeapon(boolean useDefaults) {
		return randomWeapon(Dungeon.depth / 5, useDefaults);
	}

	public static MeleeWeapon randomWeapon(int floorSet, boolean useDefaults) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		MeleeWeapon w;
		if (useDefaults){
			w = (MeleeWeapon) randomUsingDefaults(wepTiers[Dungeon.chances(floorSetTierProbs[floorSet])]);
		} else {
			w = (MeleeWeapon) random(wepTiers[Random.chances(floorSetTierProbs[floorSet])]);
		}
		return w;
	}
	
	public static final Category[] misTiers = new Category[]{
			Category.MIS_T1,
			Category.MIS_T2,
			Category.MIS_T3,
			Category.MIS_T4,
			Category.MIS_T5
	};
	
	public static MissileWeapon randomMissile(){
		return randomMissile(Dungeon.depth / 5);
	}

	public static MissileWeapon randomMissile(int floorSet) {
		return randomMissile(floorSet, false);
	}

	public static MissileWeapon randomMissile(boolean useDefaults) {
		return randomMissile(Dungeon.depth / 5, useDefaults);
	}

	public static MissileWeapon randomMissile(int floorSet, boolean useDefaults) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);

		MissileWeapon w;
		if (useDefaults){
			w = (MissileWeapon)randomUsingDefaults(misTiers[Dungeon.chances(floorSetTierProbs[floorSet])]);
		} else {
			w = (MissileWeapon)random(misTiers[Random.chances(floorSetTierProbs[floorSet])]);
		}
		return w;
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		Category cat = Category.ARTIFACT;

		if (cat.defaultProbs != null && cat.seed != null){
			Random.pushGenerator(cat.seed);
			for (int i = 0; i < cat.dropped; i++) Random.Long();
		}

		int i = Random.chances( cat.probs );

		if (cat.defaultProbs != null && cat.seed != null){
			Random.popGenerator();
			cat.dropped++;
		}

		//if no artifacts are left, return null
		if (i == -1){
			return null;
		}

		return (Artifact) Reflection.newInstance((Class<? extends Artifact>) cat.classes[i]).random();

	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++){
			if (cat.classes[i].equals(artifact) && cat.probs[i] > 0) {
				cat.probs[i] = 0;
				return true;
			}
		}
		return false;
	}

	private static final String FIRST_DECK = "first_deck";
	private static final String GENERAL_PROBS = "general_probs";
	private static final String CATEGORY_PROBS = "_probs";
	private static final String CATEGORY_SEED = "_seed";
	private static final String CATEGORY_DROPPED = "_dropped";

	public static void storeInBundle(Bundle bundle) {
		bundle.put(FIRST_DECK, usingFirstDeck);

		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);

		for (Category cat : Category.values()){
			if (cat.defaultProbs == null) continue;

			bundle.put(cat.name().toLowerCase() + CATEGORY_PROBS,   cat.probs);
			if (cat.seed != null) {
				bundle.put(cat.name().toLowerCase() + CATEGORY_SEED, cat.seed);
				bundle.put(cat.name().toLowerCase() + CATEGORY_DROPPED, cat.dropped);
			}
		}
	}

	public static void restoreFromBundle(Bundle bundle) {
		fullReset();

		usingFirstDeck = bundle.getBoolean(FIRST_DECK);

		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			for (int i = 0; i < probs.length; i++){
				categoryProbs.put(Category.values()[i], probs[i]);
			}
		}

		for (Category cat : Category.values()){
			if (bundle.contains(cat.name().toLowerCase() + CATEGORY_PROBS)){
				float[] probs = bundle.getFloatArray(cat.name().toLowerCase() + CATEGORY_PROBS);
				if (cat.defaultProbs != null && probs.length == cat.defaultProbs.length){
					cat.probs = probs;
				}
				if (bundle.contains(cat.name().toLowerCase() + CATEGORY_SEED)){
					cat.seed = bundle.getLong(cat.name().toLowerCase() + CATEGORY_SEED);
					cat.dropped = bundle.getInt(cat.name().toLowerCase() + CATEGORY_DROPPED);
				}
			}
		}
		
	}
}

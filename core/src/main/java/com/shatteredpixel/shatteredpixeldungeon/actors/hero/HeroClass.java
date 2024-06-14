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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.THE_TRUE_FATALITY;

import com.shatteredpixel.shatteredpixeldungeon.*;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.Ratmogrify;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Feint;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.ratking.LegacyWrath;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.EnemyAttributeModifier;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.ImmortalShieldAffecter;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.LevelTeleporter;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.MobAttributeViewer;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.MobPlacer;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.TestBag;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.TimeReverser;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.TrapPlacer;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.LazyTest;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.RandomGiver;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnArmor;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnArtifact;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnMissile;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnRingOrWand;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.SpawnWeapon;
import com.shatteredpixel.shatteredpixeldungeon.custom.testmode.TestPotion;
import com.shatteredpixel.shatteredpixeldungeon.items.ExtendedDictBook;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.InfoBook;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.PlateArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.lottery.LotteryItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.AdditionItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.BinaryItem;
import com.shatteredpixel.shatteredpixeldungeon.items.BrokenSeal;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.DivisibilityItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.DivisionItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.ExponentialItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.MixedOperationItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.MultiItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.MultiplicationItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.OddEvenItem;
import com.shatteredpixel.shatteredpixeldungeon.items.ParallelUniverse;
import com.shatteredpixel.shatteredpixeldungeon.items.PotionOfDebug;
import com.shatteredpixel.shatteredpixeldungeon.items.PsycheChest;
import com.shatteredpixel.shatteredpixeldungeon.items.QuestionaireItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.RectangularItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.SubtractionItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Waterskin;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.CloakOfShadows;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.EtherealChains;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.NotebookHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.PotionBandolier;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.TreasureHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.notebook.Notebook;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.ThreeOperationQuestionnaire;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.TriangularItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.TypingItem;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.*;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gloves;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MagesStaff;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Rapier;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.WornShortsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingSpike;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.DeviceCompat;
import com.zrp200.scrollofdebug.ScrollOfDebug;

import java.util.Calendar;
import java.util.GregorianCalendar;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	DUELIST( HeroSubClass.CHAMPION, HeroSubClass.MONK ),
	RAT_KING(HeroSubClass.KING);

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		GregorianCalendar gregcal = new GregorianCalendar();
		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();
		i.quantity(10);

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();

		if (gregcal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			i = Generator.random(Generator.Category.POTION);
			i.collect();
			i.quantity(5);
		}
		if (gregcal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			i = Generator.random(Generator.Category.WEAPON);
			i.collect();
			i.upgrade(2);
		}
		if (gregcal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			i = Generator.random(Generator.Category.FOOD);
			i.quantity(5);
			i.collect();

			i = Generator.random(Generator.Category.ARTIFACT);
			i.collect();
		}

		Waterskin waterskin = new Waterskin();
		waterskin.collect();

		new ScrollOfIdentify().identify();
		//DebugItems
		if (DeviceCompat.isDebug()) {
			new PotionOfDebug().collect();
			new SpawnArmor().collect();
			new SpawnArtifact().collect();
			new SpawnWeapon().collect();
			new SpawnMissile().collect();
			new TestPotion().collect();
			new SpawnRingOrWand().collect();
			new TestBag().collect();
			new MobPlacer().collect();
			new LevelTeleporter().collect();
			new ImmortalShieldAffecter().collect();
			new EnemyAttributeModifier().collect();
			new TrapPlacer().collect();
			new TimeReverser().collect();
			new LazyTest().collect();
			new RandomGiver().collect();
			if (DeviceCompat.isAndroid()) new ScrollOfDebug().collect();
		}
		new QuestionaireItem().collect();
		new AdditionItem().collect();
		new SubtractionItem().collect();
		new MultiplicationItem().collect();
		new DivisionItem().collect();
		new ExponentialItem().collect();
		new MixedOperationItem().collect();
		new OddEvenItem().collect();
		new DivisibilityItem().collect();
		new BinaryItem().collect();
		new RectangularItem().collect();
		new TriangularItem().collect();
		new TypingItem().collect();
		new ThreeOperationQuestionnaire().collect();
		new MultiItem().collect();
		new ParallelUniverse().collect();
		//new DictBook().collect();
		new ExtendedDictBook().collect();
		new Notebook().collect();
		new NotebookHolder().collect();
		new LotteryItem().collect();
		new InfoBook().collect();
		if (Dungeon.isChallenged(THE_TRUE_FATALITY)) {
			i = new ScrollOfUpgrade();
			i.collect();
			i.quantity(20);

			i = new PlateArmor();
			i.collect();
			i.upgrade(10);
		}

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case RAT_KING:
				initRK(hero);
				break;

			case DUELIST:
				initDuelist( hero );
				break;
		}

		if (SPDSettings.quickslotWaterskin()) {
			for (int s = 0; s < QuickSlot.SIZE; s++) {
				if (Dungeon.quickslot.getItem(s) == null) {
					Dungeon.quickslot.setSlot(s, waterskin);
					break;
				}
			}
		}

		new PsycheChest().collect();
		hero.grinding = true;
	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case DUELIST:
				return Badges.Badge.MASTERY_DUELIST;
			case RAT_KING:
				return Badges.Badge.MASTERY_RAT_KING;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()).identify();
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(3).collect();
		Dungeon.quickslot.setSlot(0, stones);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();

	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().identify();

	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify();
		new PotionOfInvisibility().identify();

	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfMindVision().identify();
		new ScrollOfLullaby().identify();

	}

	private static void initDuelist( Hero hero ) {

		(hero.belongings.weapon = new Rapier()).identify();
		hero.belongings.weapon.activate(hero);

		ThrowingSpike spikes = new ThrowingSpike();
		spikes.quantity(2).collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);
		Dungeon.quickslot.setSlot(1, spikes);

		new PotionOfStrength().identify();
		new ScrollOfMirrorImage().identify();

	}

	private static void initRK( Hero hero ) {
		MagesStaff staff;
		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(1, bow);

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		Dungeon.quickslot.setSlot(2, cloak);

		new ScrollHolder().collect();
		Dungeon.LimitedDrops.SCROLL_HOLDER.drop();
		new PotionBandolier().collect();
		Dungeon.LimitedDrops.POTION_BANDOLIER.drop();
		new MagicalHolster().collect();
		Dungeon.LimitedDrops.MAGICAL_HOLSTER.drop();
		new TreasureHolder().collect();
		Dungeon.LimitedDrops.TREASURE_HOLDER.drop();

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();

		EtherealChains eth = new EtherealChains();
		//eth.collect();
		//eth.identify();

		AlchemistsToolkit alc = new AlchemistsToolkit();
		//alc.collect();
		//alc.identify();
		  
	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}

	public String shortDesc(){
		return Messages.get(HeroClass.class, name()+"_desc_short");
	}

	public String perks_desc(){
		return Messages.get(HeroClass.class, name()+"_perk_desc");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
			case DUELIST:
				return new ArmorAbility[]{new Challenge(), new ElementalStrike(), new Feint()};
			case RAT_KING:
				return new ArmorAbility[]{new Ratmogrify(), new LegacyWrath()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case DUELIST:
				return Assets.Sprites.DUELIST;
			case RAT_KING:
				return Assets.Sprites.RAT_KING_HERO;
		}
	}

	public String splashArt(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
			case DUELIST:
				return Assets.Splashes.DUELIST;
			case RAT_KING:
				return Assets.Splashes.RATKING;
		}
	}

	public boolean isUnlocked(){
        return true;
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

}

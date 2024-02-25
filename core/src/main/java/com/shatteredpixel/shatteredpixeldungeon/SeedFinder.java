package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ArmoredStatue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.CrystalMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.GoldenMimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mimic;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.EnergyCrystal;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap.Type;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.CrystalKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.GoldenKey;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.IronKey;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
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
import com.shatteredpixel.shatteredpixeldungeon.items.quest.CeremonialCandle;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Embers;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Pickaxe;
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
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
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
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.utils.DungeonSeed;
import com.watabou.noosa.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class SeedFinder {
	enum Condition {ANY, ALL};

	public static class Options {
		public static int floors;
		public static Condition condition;
		public static String itemListFile;
		public static String outputFile;
		public static long seed;
		public static long startingSeed;
		public static long endingSeed;

		public static boolean quietMode;
		public static boolean runesOn;
		public static boolean compactOutput;
		public static boolean skipConsumables;
	}


	public class HeapItem {
		public Item item;
		public Heap heap;

		public HeapItem(Item item, Heap heap) {
			this.item = item;
			this.heap = heap;
		}
	}

	List<Class<? extends Item>> blacklist;
	ArrayList<String> itemList;

	Map<String, String> returnValues = new HashMap<String, String>();

	private void addTextItems(String caption, ArrayList<HeapItem> items, StringBuilder builder) {
		Options.compactOutput = true;  //SJC

		if (!items.isEmpty()) {
			if (!Options.compactOutput) builder.append(caption + ":\n");

			for (HeapItem item : items) {
				Item i = item.item;
				Heap h = item.heap;

				if (h.type == Type.CRYSTAL_CHEST) builder.append("* ");
				else builder.append("- ");

				if (((i instanceof Armor && ((Armor) i).hasGoodGlyph()) ||
						(i instanceof Weapon && ((Weapon) i).hasGoodEnchant()) ||
						(i instanceof Ring) || (i instanceof Wand) || (i instanceof Artifact)) && i.cursed)
					builder.append("cursed " + i.title().toLowerCase());

				else
					builder.append(i.title().toLowerCase());

				if (i instanceof Potion){
					builder.append(" (" + ((Potion) i).color + ")" );
				}
				if (i instanceof Scroll){
					builder.append(" (" + ((Scroll) i).rune.toLowerCase() + ")" );
				}
				if (i instanceof Ring){
					builder.append(" (" + ((Ring) i).gem + ")" );
				}

				if (h.type != Type.HEAP)
					builder.append(" (" + h.title().toLowerCase() + ")");

				builder.append("\n");
			}

			if (!Options.compactOutput) builder.append("\n");
		}
	}

	private void addTextQuest(String caption, ArrayList<Item> items, StringBuilder builder) {
		if (!items.isEmpty()) {
			builder.append(caption).append(":\n");

			for (Item i : items) {
				if (i.cursed)
					builder.append(" * cursed ").append(i.title().toLowerCase()).append("\n");

				else
					builder.append(" * ").append(i.title().toLowerCase()).append("\n");
			}

			if (!Options.compactOutput) builder.append("\n");
		}
	}

	// SJC
	public Map<String, String> getListofItems(int floors, String seed){
		Map<String, String> returnValue = logSeedItems(seed, floors);
		return returnValue;
	}


	private ArrayList<Heap> getMobDrops(Level l) {
		ArrayList<Heap> heaps = new ArrayList<>();

		for (Mob m : l.mobs) {

			if (m instanceof ArmoredStatue) {
				Heap h = new Heap();
				h.items = new LinkedList<>();
				h.items.add(((ArmoredStatue) m).armor.identify());
				h.items.add(((ArmoredStatue) m).weapon.identify());
				h.type = Type.STATUE;
				heaps.add(h);
			}

			else if (m instanceof Statue) {
				Heap h = new Heap();
				h.items = new LinkedList<>();
				h.items.add(((Statue) m).weapon.identify());
				h.type = Type.STATUE;
				heaps.add(h);
			}

			else if (m instanceof Mimic) {
				Heap h = new Heap();
				h.items = new LinkedList<>();

				for (Item item : ((Mimic) m).items)
					h.items.add(item.identify());

				if (m instanceof GoldenMimic) h.type = Type.GOLDEN_MIMIC;
				else if (m instanceof CrystalMimic) h.type = Type.CRYSTAL_MIMIC;
				else h.type = Type.MIMIC;
				heaps.add(h);
			}
		}

		return heaps;
	}

	private boolean testSeed(String seed, int floors) {
		SPDSettings.customSeed(seed);
		if (Options.runesOn) SPDSettings.challenges(64); else SPDSettings.challenges(0);
		GamesInProgress.selectedClass = HeroClass.WARRIOR;
		Dungeon.init();

		boolean[] itemsFound = new boolean[itemList.size()];

		for (int i = 0; i < floors; i++) {
			Level l = Dungeon.newLevel();


			boolean crystalChestFound = false;
			boolean questRewardFound = false;
			boolean questItemRequested = false;
			if(Dungeon.depth % 5 != 0) {

				ArrayList<Heap> heaps = new ArrayList<>(l.heaps.valueList());
				heaps.addAll(getMobDrops(l));

				for (Heap h : heaps) {
					for (Item item : h.items) {
						item.identify();

						for (int j = 0; j < itemList.size(); j++) {
							if (crystalChestFound && h.type == Type.CRYSTAL_CHEST) continue;
							if (item.title().toLowerCase().contains(itemList.get(j))) {
								if (!itemsFound[j]) {
									itemsFound[j] = true;
//	SJC questItem doesn't exist								if (item.questItem) questItemRequested = true;
									if (h.type == Type.CRYSTAL_CHEST) crystalChestFound = true;
									break;
								}
							}
						}
					}
				}

				ArrayList<Item> rewards = getPossibleQuestRewards(l);
				for (Item item : rewards) {
					if (questItemRequested) break;
					item.identify();
					for (int j = 0; j < itemList.size(); j++) {
						if (questRewardFound) continue;

						if (item.title().toLowerCase().contains(itemList.get(j))) {
							if (!itemsFound[j]) {
								itemsFound[j] = true;
								questRewardFound = true;
								break;
							}
						}
					}
				}
			}
			Dungeon.depth++;
		}

		if (Options.condition == Condition.ANY) {
			for (int i = 0; i < itemList.size(); i++) {
				if (itemsFound[i] == true)
					return true;
			}

			return false;
		}

		else {
			for (int i = 0; i < itemList.size(); i++) {
				if (itemsFound[i] == false)
					return false;
			}

			return true;
		}
	}

	private ArrayList<Item> getPossibleQuestRewards(Level level){
		ArrayList<Item> rewards = new ArrayList<>();
		if (Ghost.Quest.armor != null) {
			rewards.add(Ghost.Quest.armor.identify());
			rewards.add(Ghost.Quest.weapon.identify());
			Ghost.Quest.complete();
		}
		if (Wandmaker.Quest.wand1 != null) {
			rewards.add(Wandmaker.Quest.wand1.identify());
			rewards.add(Wandmaker.Quest.wand2.identify());
			Wandmaker.Quest.complete();
		}
		if (Imp.Quest.reward != null) {
			rewards.add(Imp.Quest.reward.identify());
			Imp.Quest.complete();
		}
		return rewards;
	}

	private String potionString(Potion potion) {
		return "- " + potion.identify().name().replace("potion of","") + ": " + potion.color + "\n";
	}
	private String scrollString(Scroll scroll) {
		return "- " + scroll.identify().name().replace("scroll of","") + ": " + scroll.rune + "\n";
	}
	private String ringString(Ring ring) {
		return "- " + ring.identify().name().replace("ring of","") + ": " + ring.gem + "\n";
	}


//	private void logSeedItems(String seed, int floors) {
public Map<String, String> logSeedItems(String seed, int floors) {

		SPDSettings.customSeed(seed);
		if (Options.runesOn) SPDSettings.challenges(64); else SPDSettings.challenges(0);
		GamesInProgress.selectedClass = HeroClass.WARRIOR;
		Dungeon.init();

		blacklist = Arrays.asList(Gold.class, Dewdrop.class, IronKey.class, GoldenKey.class, CrystalKey.class, EnergyCrystal.class,
				Embers.class, CeremonialCandle.class, Pickaxe.class);

		String titleString = "\n\nItems for seed "+DungeonSeed.convertToCode(Dungeon.seed)+" ("+Dungeon.seed+")";
		//titleString += Options.runesOn ? "[Runes On]" : "[Runes Off]";
		titleString += "\n\n";
		int noOfLines = 3;
		noOfLines +=14; // For potion index
		noOfLines +=14; // for scroll index
		noOfLines +=11; // for special items area, but that hardcoded. dynamically adding it doesn't work as it's mixed in with main pass and we need to know in advance so we can add to the floor title
		// need to re-architect the whole thing.

		titleString += "***** Special items before floor 10 *****\n";
		String artifactString = "";
		String ringString = "";
		String wandString = "";
		String otherString = "";
		String returnString = "";

		String sewersString = "";
		String prisonString = "";
		String cavesString = "";
		String metropolisString = "";
		String hallsString = "";

		for (int i = 0; i < floors; i++) {
			// SJC
			returnString += "\n--- Floor: "+Dungeon.depth+" --- Line: "+noOfLines+"\n";
			noOfLines +=2; // one for the title, one for empty line after section.

			Level l = Dungeon.newLevel();
			ArrayList<Heap> heaps = new ArrayList<>(l.heaps.valueList());
			StringBuilder builder = new StringBuilder();
			ArrayList<HeapItem> scrolls = new ArrayList<>();
			ArrayList<HeapItem> potions = new ArrayList<>();
			ArrayList<HeapItem> equipment = new ArrayList<>();
			ArrayList<HeapItem> rings = new ArrayList<>();
			ArrayList<HeapItem> artifacts = new ArrayList<>();
			ArrayList<HeapItem> wands = new ArrayList<>();
			ArrayList<HeapItem> others = new ArrayList<>();

			// list quest rewards
			if (Ghost.Quest.armor != null) {
				ArrayList<Item> rewards = new ArrayList<>();
				rewards.add(Ghost.Quest.armor.identify());
				rewards.add(Ghost.Quest.weapon.identify());
				Ghost.Quest.complete();

				addTextQuest("Ghost quest rewards", rewards, builder);
				noOfLines +=3;

			}

			if (Wandmaker.Quest.wand1 != null) {
				ArrayList<Item> rewards = new ArrayList<>();
				rewards.add(Wandmaker.Quest.wand1.identify());
				rewards.add(Wandmaker.Quest.wand2.identify());
	//SJC
				wandString += "- " + Wandmaker.Quest.wand1.name() + " +" + Wandmaker.Quest.wand1.level() + " - floor " + (i+1) +" (Wandmaker)\n";
				wandString += "- " + Wandmaker.Quest.wand2.name() + " +" + Wandmaker.Quest.wand2.level() + " - floor " + (i+1) +" (Wandmaker)\n";

				Wandmaker.Quest.complete();

				builder.append("Wandmaker quest item: ");

				switch (Wandmaker.Quest.type) {
					case 1: default:
						builder.append("corpse dust\n");
						break;
					case 2:
						builder.append("fresh embers\n");
						break;
					case 3:
						builder.append("rotberry seed\n");
						break;

				}
				if (!Options.compactOutput) builder.append("\n");
				addTextQuest("Wandmaker quest rewards", rewards, builder);
				noOfLines +=4;
			}

			if (Imp.Quest.reward != null) {
				ArrayList<Item> rewards = new ArrayList<>();
				rewards.add(Imp.Quest.reward.identify());
				Imp.Quest.complete();

				addTextQuest("Imp quest reward", rewards, builder);
				noOfLines +=2;

			}

			heaps.addAll(getMobDrops(l));

			// list items
			for (Heap h : heaps) {
				for (Item item : h.items) {
					item.identify();

					if (h.type == Type.FOR_SALE) continue;
					else if (blacklist.contains(item.getClass())) continue;
					else if (item instanceof Scroll) { scrolls.add(new HeapItem(item, h)); noOfLines +=1;}
					else if (item instanceof Potion) { potions.add(new HeapItem(item, h)); noOfLines +=1;}
					else if (item instanceof MeleeWeapon || item instanceof Armor) { equipment.add(new HeapItem(item, h)); noOfLines +=1;}
					else if (item instanceof Ring) { rings.add(new HeapItem(item, h)); noOfLines +=1;}
					else if (item instanceof Artifact) { artifacts.add(new HeapItem(item, h)); noOfLines +=1;}
					else if (item instanceof Wand) { wands.add(new HeapItem(item, h)); noOfLines +=1;}
// SJC					else others.add(new HeapItem(item, h));
					else {
						others.add(new HeapItem(item, h));
						noOfLines +=1;
						if (i <= 9 && (item.name().contains("boomerang") || item.name().contains("shuriken"))) {
							otherString += "- " + item.name() + " +" + item.level() + " - floor " + (i+1);
							otherString += item.cursed ? " (cursed)" : "";
							otherString += "\n";
						}
					}
					if (i <= 9 && item instanceof Ring) {
						ringString += "- " + item.name() + " +" + item.level() + " - floor " + (i+1);
						ringString += item.cursed ? " (cursed)" : "";
						ringString += "\n";
					}
					if (i <= 9 && item instanceof Artifact) {
						artifactString += "- " + item.name() + " +" + item.level() + " - floor " + (i+1);
						artifactString += item.cursed ? " (cursed)" : "";
						artifactString += "\n";
					}
					if (i <= 9 && item instanceof Wand) {
						wandString += "- " + item.name() + " +" + item.level() + " - floor " + (i+1);
						wandString += item.cursed ? " (cursed)" : "";
						wandString += "\n";
					}
// SJCEND
				}
			}
			if (!Options.skipConsumables) {
				addTextItems("Scrolls", scrolls, builder);
				addTextItems("Potions", potions, builder);
			}
			addTextItems("Equipment", equipment, builder);
			addTextItems("Rings", rings, builder);
			addTextItems("Artifacts", artifacts, builder);
			addTextItems("Wands", wands, builder);
			if (!Options.skipConsumables) addTextItems("Other", others, builder);
			//SJC
//			out.print(builder.toString());
			returnString += builder.toString();
			if (i<=4) { sewersString += "\n--- Floor: "+Dungeon.depth+" --- Line: "+noOfLines+"\n"+builder.toString(); }
			else if (i>4 && i<=9) { prisonString += "\n--- Floor: "+Dungeon.depth+" --- Line: "+noOfLines+"\n"+builder.toString(); }
			else if (i>9 && i<=14) { cavesString += "\n--- Floor: "+Dungeon.depth+" --- Line: "+noOfLines+"\n"+builder.toString(); }
			else if (i>14 && i<=19) { metropolisString += "\n--- Floor: "+Dungeon.depth+" --- Line: "+noOfLines+"\n"+builder.toString(); }
			else if (i>19 && i<=24) { hallsString += "\n--- Floor: "+Dungeon.depth+" --- Line: "+noOfLines+"\n"+builder.toString(); }

			Dungeon.depth++;
		}

		//get potion and scroll identifiers

		String indexString = "";
		indexString += "***** Potion Index *****\n";
		indexString += potionString(new PotionOfStrength());
		indexString += potionString(new PotionOfHealing());
		indexString += potionString(new PotionOfMindVision());
		indexString += potionString(new PotionOfFrost());
		indexString += potionString(new PotionOfLiquidFlame());
		indexString += potionString(new PotionOfToxicGas());
		indexString += potionString(new PotionOfHaste());
		indexString += potionString(new PotionOfInvisibility());
		indexString += potionString(new PotionOfLevitation());
		indexString += potionString(new PotionOfParalyticGas());
		indexString += potionString(new PotionOfPurity());
		indexString += potionString(new PotionOfExperience());
		indexString += "\n";

		indexString += "***** Scroll Index *****\n";
		indexString += scrollString(new ScrollOfUpgrade());
		indexString += scrollString(new ScrollOfIdentify());
		indexString += scrollString(new ScrollOfRemoveCurse());
		indexString += scrollString(new ScrollOfMirrorImage());
		indexString += scrollString(new ScrollOfRecharging());
		indexString += scrollString(new ScrollOfTeleportation());
		indexString += scrollString(new ScrollOfLullaby());
		indexString += scrollString(new ScrollOfMagicMapping());
		indexString += scrollString(new ScrollOfRage());
		indexString += scrollString(new ScrollOfRetribution());
		indexString += scrollString(new ScrollOfTerror());
		indexString += scrollString(new ScrollOfTransmutation());
		indexString += "\n";

		indexString += "***** Ring Index *****\n";
		indexString += ringString(new RingOfAccuracy());
		indexString += ringString(new RingOfArcana());
		indexString += ringString(new RingOfElements());
		indexString += ringString(new RingOfEnergy());
		indexString += ringString(new RingOfEvasion());
		indexString += ringString(new RingOfForce());
		indexString += ringString(new RingOfFuror());
		indexString += ringString(new RingOfHaste());
		indexString += ringString(new RingOfMight());
		indexString += ringString(new RingOfSharpshooting());
		indexString += ringString(new RingOfTenacity());
		indexString += ringString(new RingOfWealth());

		//SJC
//		out.close();

		returnValues.put("titleString", titleString + ringString + artifactString + wandString + otherString + "\n");
		returnValues.put("indexString", indexString);
		returnValues.put("sewersString", sewersString);
		returnValues.put("prisonString", prisonString);
		returnValues.put("cavesString", cavesString);
		returnValues.put("metropolisString", metropolisString);
		returnValues.put("hallsString", hallsString);

		return returnValues;
	}

}

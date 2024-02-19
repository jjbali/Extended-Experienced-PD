package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.EnergyCrystal;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Honeypot;
import com.shatteredpixel.shatteredpixeldungeon.items.KingsCrown;
import com.shatteredpixel.shatteredpixeldungeon.items.OverloadBeacon;
import com.shatteredpixel.shatteredpixeldungeon.items.RandomItemTicket;
import com.shatteredpixel.shatteredpixeldungeon.items.TengusMask;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToArena;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToSecondArena;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToThirdArena;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.PlateArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.bombs.Bomb;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.AvaritiaFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.BasicFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.ChaosFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.GoldenFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.NeutroniumFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Pasty;
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
import com.shatteredpixel.shatteredpixeldungeon.items.potions.brews.InfernalBrew;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfMagicalSight;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.MetalShard;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfAccuracy;
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
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.SummonElemental;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfAugmentation;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfBlink;
import com.shatteredpixel.shatteredpixeldungeon.items.stones.StoneOfFlock;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greatsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.Dart;
import com.shatteredpixel.shatteredpixeldungeon.plants.Blindweed;
import com.shatteredpixel.shatteredpixeldungeon.plants.Earthroot;
import com.shatteredpixel.shatteredpixeldungeon.plants.Fadeleaf;
import com.shatteredpixel.shatteredpixeldungeon.plants.Firebloom;
import com.shatteredpixel.shatteredpixeldungeon.plants.Icecap;
import com.shatteredpixel.shatteredpixeldungeon.plants.Mageroyal;
import com.shatteredpixel.shatteredpixeldungeon.plants.Rotberry;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sorrowmoss;
import com.shatteredpixel.shatteredpixeldungeon.plants.Starflower;
import com.shatteredpixel.shatteredpixeldungeon.plants.Stormvine;
import com.shatteredpixel.shatteredpixeldungeon.plants.Sungrass;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class LazyTest extends TestGenerator {
    {
        image = ItemSpriteSheet.EBONY_CHEST;
    }

    @Override
    public void execute(Hero hero, String action){
        if(action.equals(AC_GIVE)){
            new PotionOfExperience().quantity(15000).identify().collect();
            new PotionOfFrost().quantity(15000).identify().collect();
            new PotionOfHaste().quantity(15000).identify().collect();
            new PotionOfHealing().quantity(15000).identify().collect();
            new PotionOfInvisibility().quantity(15000).identify().collect();
            new PotionOfLevitation().quantity(15000).identify().collect();
            new PotionOfLiquidFlame().quantity(15000).identify().collect();
            new PotionOfMindVision().quantity(15000).identify().collect();
            new PotionOfParalyticGas().quantity(15000).identify().collect();
            new PotionOfPurity().quantity(15000).identify().collect();
            new PotionOfStrength().quantity(15000).identify().collect();
            new PotionOfToxicGas().quantity(15000).identify().collect();

            new PotionOfMagicalSight().quantity(15000).identify().collect();

            new ScrollOfIdentify().quantity(15000).identify().collect();
            new ScrollOfLullaby().quantity(15000).identify().collect();
            new ScrollOfMagicMapping().quantity(15000).identify().collect();
            new ScrollOfMirrorImage().quantity(15000).identify().collect();
            new ScrollOfRage().quantity(15000).identify().collect();
            new ScrollOfRecharging().quantity(15000).identify().collect();
            new ScrollOfRemoveCurse().quantity(15000).identify().collect();
            new ScrollOfRetribution().quantity(15000).identify().collect();
            new ScrollOfTeleportation().quantity(15000).identify().collect();
            new ScrollOfTerror().quantity(15000).identify().collect();
            new ScrollOfTransmutation().quantity(15000).identify().collect();
            new ScrollOfUpgrade().quantity(15000).identify().collect();

            PlateArmor plateArmor = new PlateArmor();
            plateArmor.level(15000);
            plateArmor.identify().collect();

            Greatsword greatsword = new Greatsword();
            greatsword.level(15000);
            greatsword.identify().collect();

            new Gold().quantity(16000000).doPickUp(hero);

            new EnergyCrystal().quantity(1500000).doPickUp(hero);

            new Pasty().quantity(15000).collect();

            new Food().quantity(15000).collect();

            new Dart().quantity(15000).collect();

            new Bomb().quantity(15000).collect();

            //new TomeOfMastery().collect();
            new TengusMask().collect();

            new KingsCrown().collect();

            new Honeypot().quantity(15000).collect();

            new StoneOfFlock().quantity(15000).collect();

            new Torch().quantity(15000).identify().collect();

            new AlchemicalCatalyst().quantity(15000).collect();

            new ArcaneCatalyst().quantity(15000).collect();

            new StoneOfBlast().quantity(15000).collect();

            new StoneOfBlink().quantity(15000).collect();

            new StoneOfAugmentation().quantity(15000).collect();

            new Blindweed.Seed().quantity(15000).identify().collect();
            new Mageroyal.Seed().quantity(15000).identify().collect();
            new Earthroot.Seed().quantity(15000).identify().collect();
            new Fadeleaf.Seed().quantity(15000).identify().collect();
            new Firebloom.Seed().quantity(15000).identify().collect();
            new Icecap.Seed().quantity(15000).identify().collect();
            new Rotberry.Seed().quantity(15000).identify().collect();
            new Sorrowmoss.Seed().quantity(15000).identify().collect();
            new Starflower.Seed().quantity(15000).identify().collect();
            new Stormvine.Seed().quantity(15000).identify().collect();
            new Sungrass.Seed().quantity(15000).identify().collect();
            new Swiftthistle.Seed().quantity(15000).identify().collect();

            for (int i = 0; i < 6; i++) {
                new PotionOfStrength().apply(hero);
            }

            RingOfAccuracy roa = new RingOfAccuracy();
            roa.level(22);
            roa.identify().collect();

            new ScrollOfPsionicBlast().quantity(15000).identify().collect();
            new PotionOfCleansing().quantity(15000).identify().collect();

            new GooBlob().quantity(15000).collect();
            new MetalShard().quantity(15000).collect();
            new InfernalBrew().quantity(15000).collect();

            new SummonElemental().quantity(15000).collect();

            new TicketToArena().quantity(15000).collect();
            new TicketToSecondArena().quantity(15000).collect();
            new TicketToThirdArena().quantity(15000).collect();
            new RandomItemTicket().quantity(15000).collect();
            new OverloadBeacon().quantity(15000).collect();
            new AvaritiaFishingRod().collect();
            new BasicFishingRod().collect();
            new ChaosFishingRod().collect();
            new GoldenFishingRod().collect();
            new NeutroniumFishingRod().collect();

            //detach(hero.belongings.backpack);
        }
    }
}

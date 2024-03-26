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

package com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs;

import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CorrosiveImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.DamageEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Foresight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PhysicalEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PrismaticGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RageShield;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToArena;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToFourthArena;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToThirdArena;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.BasicFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.TheTrueFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Cheese;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.tieredcards.LeveledTieredCard;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfEarthblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfLivingEarth;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.input.ScrollEvent;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ElixirOfUltimatum extends Elixir {

	{
		image = ItemSpriteSheet.ELIXIR_ULTIMATUM;

		unique = true;
	}
	
	@Override
	public void apply( Hero hero ) {
		identify();

		Buff.affect(hero, Adrenaline.class, 350f * hero.lvl);
		Buff.affect(hero, AnkhInvulnerability.class, 85f * hero.lvl);
		Buff.affect(hero, ArcaneArmor.class).set(963L * hero.lvl, Integer.MAX_VALUE);
		Buff.affect(hero, ArtifactRecharge.class).set(745f * hero.lvl);
		Buff.affect(hero, Barrier.class).setShield(850L * hero.lvl);
		Buff.affect(hero, Bless.class, 1000f * hero.lvl);
		Buff.affect(hero, BlobImmunity.class, 950f * hero.lvl);
		Buff.affect(hero, CorrosiveImbue.class).set(750f * hero.lvl);
		Buff.affect(hero, DamageEnhance.class).set(2L * hero.lvl, 450L * hero.lvl);
		Buff.affect(hero, FireImbue.class).set(850f * hero.lvl);
		Buff.affect(hero, Foresight.class, 75f * hero.lvl);
		Buff.affect(hero, FrostImbue.class, 850f * hero.lvl);
		Buff.affect(hero, Light.class, 850f * hero.lvl);
		Buff.affect(hero, PhysicalEmpower.class).set((Random.Int(1, 10) + 1) * hero.lvl, 200 * hero.lvl);
		Buff.affect(hero, PrismaticGuard.class).set(hero.HT);
		Buff.affect(hero, RageShield.class).set(500 * hero.lvl);
		Buff.affect(hero, Recharging.class, 1000f * hero.lvl);
		Buff.affect(hero, WellFed.class).reset();
		for (int i = 0; i < 50; i++) {
			hero.STR++;
		}
		hero.sprite.showStatusWithIcon(CharSprite.POSITIVE, "50", FloatingText.STRENGTH);
		Badges.validateStrengthAttained();
		Badges.validateDuelistUnlock();

		GLog.p("Not bad, should I apply more? Maybe we can conquer this dungeon easier.");
	}
	
	public String desc() {
		return Messages.get(this, "desc");
	}
	
	@Override
	public long value() {
		//prices of ingredients
		return quantity * (50 + 40);
	}
	
	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe {

		@Override
		public boolean testIngredients(ArrayList<Item> ingredients) {
			boolean ticket = false;
			boolean ltieredcard = false;
			boolean sou = false;

			for (Item ingredient : ingredients){
				if (ingredient.quantity() > 0) {
					if (ingredient instanceof TicketToThirdArena) {
						ticket = true;
					} else if (ingredient instanceof LeveledTieredCard) {
						ltieredcard = true;
					} else if (ingredient instanceof ScrollOfUpgrade) {
						sou = true;
					}
				}
			}

			return ticket && ltieredcard && sou;
		}

		@Override
		public long cost(ArrayList<Item> ingredients) {
			return 1000;
		}

		@Override
		public Item brew(ArrayList<Item> ingredients) {
			if (!testIngredients(ingredients)) return null;

			for (Item ingredient : ingredients){
				ingredient.quantity(ingredient.quantity() - 1);
			}

			return sampleOutput(null);
		}

		@Override
		public Item sampleOutput(ArrayList<Item> ingredients) {
			return new ElixirOfUltimatum().quantity(5);
		}
		
	}
}

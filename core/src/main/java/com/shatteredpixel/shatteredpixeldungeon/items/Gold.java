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

package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.FALL_ECONOMY;
import static com.shatteredpixel.shatteredpixeldungeon.Modifiers.FORGOTTEN_GOLD;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Perks;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.items.fragments.VioletFragment;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.RectangularItem;
import com.shatteredpixel.shatteredpixeldungeon.items.totem.TotemOfFortune;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Gold extends Item {

	private static final String TXT_VALUE	= "%+d";
	
	{
		image = ItemSpriteSheet.GOLD;
		stackable = true;
	}
	GregorianCalendar gregcal = new GregorianCalendar();
	
	public Gold() {
		this( 1 );
	}
	
	public Gold( long value ) {
		this.quantity = value;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		return new ArrayList<>();
	}
	
	@Override
	public boolean doPickUp(Hero hero, int pos, float time) {
		
		Dungeon.gold += quantity;
		Statistics.goldCollected += quantity;
		Badges.validateGoldCollected();

		GameScene.pickUp( this, pos );
		hero.sprite.showStatusWithIcon( CharSprite.NEUTRAL, Long.toString(quantity), FloatingText.GOLD );
		if (time > 0)
			hero.spendAndNext( TIME_TO_PICK_UP );
		
		Sample.INSTANCE.play( Assets.Sounds.GOLD, 1, 1, Random.Float( 0.9f, 1.1f ) );
		updateQuickslot();

		return true;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public Item random() {
		quantity = (Dungeon.IntRange( 16 + Dungeon.escalatingDepth() * 3, 31 + Dungeon.escalatingDepth() * 6 ));
		if (Dungeon.hero.perks.contains(Perks.Perk.MORE_COINS)) quantity *= 5;
		if (Dungeon.hero.buff(TotemOfFortune.FortuneBuff.class) != null) quantity *= 5;
		if (RectangularItem.totalAnswers_j > 0) quantity *= 1 + (RectangularItem.totalAnswers_j * 0.25f);
		if (gregcal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) quantity *= 4;
		quantity *= VioletFragment.goldMulti();
		if (Dungeon.isChallenged(FALL_ECONOMY)) quantity *= 0.05f;
		if (Dungeon.isModified(FORGOTTEN_GOLD)) quantity *= 0.1f;
		return this;
	}

}

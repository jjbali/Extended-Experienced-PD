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

package com.shatteredpixel.shatteredpixeldungeon.items.test_tubes;

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.GOLDFISH_MEMORY;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Perks;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Splash;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.ItemStatusHandler;
import com.shatteredpixel.shatteredpixeldungeon.items.Recipe;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.*;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.plants.*;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndUseItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Tubes extends Item {

	public static final String AC_DRINK = "DRINK";

	//used internally for potions that can be drunk or thrown
	public static final String AC_CHOOSE = "CHOOSE";

	private static final float TIME_TO_DRINK = 1f;

	private static final LinkedHashMap<String, Integer> colors = new LinkedHashMap<String, Integer>() {
		{
			put("red",ItemSpriteSheet.RED_TEST_TUBE);
			put("orange",ItemSpriteSheet.ORANGE_TEST_TUBE);
			put("yellow",ItemSpriteSheet.YELLOW_TEST_TUBE);
			put("green",ItemSpriteSheet.GREEN_TEST_TUBE);
			put("cyan",ItemSpriteSheet.CYAN_TEST_TUBE);
			put("blue",ItemSpriteSheet.BLUE_TEST_TUBE);
			put("violet",ItemSpriteSheet.VIOLET_TEST_TUBE);
			put("pink",ItemSpriteSheet.PINK_TEST_TUBE);
		}
	};

	private static final HashSet<Class<?extends Tubes>> mustThrowPots = new HashSet<>();
	static{
		//mustThrowPots.add(PotionOfToxicGas.class);
		//mustThrowPots.add(PotionOfLiquidFlame.class);
		//mustThrowPots.add(PotionOfParalyticGas.class);
		//mustThrowPots.add(PotionOfFrost.class);

		//exotic
		//mustThrowPots.add(PotionOfCorrosiveGas.class);
		//mustThrowPots.add(PotionOfSnapFreeze.class);
		//mustThrowPots.add(PotionOfShroudingFog.class);
		//mustThrowPots.add(PotionOfStormClouds.class);

		//also all brews, hardcoded
	}

	private static final HashSet<Class<?extends Tubes>> canThrowPots = new HashSet<>();
	static{
		//canThrowPots.add(AlchemicalCatalyst.class);

		canThrowPots.add(TubeOfPureImmunity.class);
		//canThrowPots.add(PotionOfLevitation.class);

		//exotic
		//canThrowPots.add(PotionOfCleansing.class);

		//elixirs
		//canThrowPots.add(ElixirOfHoneyedHealing.class);
	}

	protected static ItemStatusHandler<Tubes> handler;

	public String color;

	{
		stackable = true;
		defaultAction = AC_DRINK;
	}

	@SuppressWarnings("unchecked")
	public static void initColors() {
		handler = new ItemStatusHandler<>( (Class<? extends Tubes>[])Generator.Category.TUBES.classes, colors );
	}

	public static void save( Bundle bundle ) {
		handler.save( bundle );
	}

	public static void saveSelectively( Bundle bundle, ArrayList<Item> items ) {
		ArrayList<Class<?extends Item>> classes = new ArrayList<>();
		for (Item i : items){
			if (i instanceof ExoticPotion){
				if (!classes.contains(ExoticPotion.exoToReg.get(i.getClass()))){
					classes.add(ExoticPotion.exoToReg.get(i.getClass()));
				}
			} else if (i instanceof Tubes){
				if (!classes.contains(i.getClass())){
					classes.add(i.getClass());
				}
			}
		}
		handler.saveClassesSelectively( bundle, classes );
	}

	@SuppressWarnings("unchecked")
	public static void restore( Bundle bundle ) {
		handler = new ItemStatusHandler<>( (Class<? extends Tubes>[])Generator.Category.TUBES.classes, colors, bundle );
	}

	public Tubes() {
		super();
		reset();
	}

	//anonymous potions are always IDed, do not affect ID status,
	//and their sprite is replaced by a placeholder if they are not known,
	//useful for items that appear in UIs, or which are only spawned for their effects
	protected boolean anonymous = false;
	public void anonymize(){
		if (!isKnown()) image = ItemSpriteSheet.TEST_TUBE_HOLDER;
		anonymous = true;
	}

	@Override
	public void reset(){
		super.reset();
		if (handler != null && handler.contains(this)) {
			image = handler.image(this);
			color = handler.label(this);
		}
	}

	@Override
	public String defaultAction() {
		if (isKnown() && mustThrowPots.contains(this.getClass())) {
			return AC_THROW;
		} else if (isKnown() &&canThrowPots.contains(this.getClass())){
			return AC_CHOOSE;
		} else {
			return AC_DRINK;
		}
	}

	@Override
	public Item random() {
		quantity = 1;
		return this;
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_DRINK );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_CHOOSE )){

			GameScene.show(new WndUseItem(null, this) );

		} else if (action.equals( AC_DRINK )) {

			if (isKnown() && mustThrowPots.contains(getClass())) {

				GameScene.show(
						new WndOptions(new ItemSprite(this),
								Messages.get(Tubes.class, "harmful"),
								Messages.get(Tubes.class, "sure_drink"),
								Messages.get(Tubes.class, "yes"), Messages.get(Tubes.class, "no") ) {
							@Override
							protected void onSelect(int index) {
								if (index == 0) {
									drink( hero );
								}
							}
						}
				);

			} else {
				drink( hero );
			}
		}
	}

	@Override
	public void doThrow( final Hero hero ) {

		if (isKnown()
				&& !mustThrowPots.contains(this.getClass())
				&& !canThrowPots.contains(this.getClass())) {

			GameScene.show(
					new WndOptions(new ItemSprite(this),
							Messages.get(Tubes.class, "beneficial"),
							Messages.get(Tubes.class, "sure_throw"),
							Messages.get(Tubes.class, "yes"), Messages.get(Tubes.class, "no") ) {
						@Override
						protected void onSelect(int index) {
							if (index == 0) {
								Tubes.super.doThrow( hero );
							}
						}
					}
			);

		} else {
			super.doThrow( hero );
		}
	}

	protected void drink( Hero hero ) {

		detach( hero.belongings.backpack );

		hero.spend( TIME_TO_DRINK );
		hero.busy();
		apply( hero );

		Sample.INSTANCE.play( Assets.Sounds.DRINK );

		hero.sprite.operate( hero.pos );
		if (!anonymous){

		}
	}

	@Override
	protected void onThrow( int cell ) {
		if (Dungeon.level.map[cell] == Terrain.WELL || Dungeon.level.pit[cell]) {

			super.onThrow( cell );

		} else  {

			Dungeon.level.pressCell( cell );
			shatter( cell );

		}
	}

	public void apply( Hero hero ) {
		shatter( hero.pos );
	}

	public void shatter( int cell ) {
		if (Dungeon.level.heroFOV[cell]) {
			GLog.i( Messages.get(Tubes.class, "shatter") );
			Sample.INSTANCE.play( Assets.Sounds.SHATTER );
			splash( cell );
		}
	}

	@Override
	public void cast( final Hero user, int dst ) {
		super.cast(user, dst);
	}

	public boolean isKnown() {
		if (Dungeon.isChallenged(GOLDFISH_MEMORY)) return false;
		return anonymous || (handler != null && handler.isKnown( this ));
	}

	public void setKnown() {
		if (!anonymous) {
			if (!isKnown()) {
				handler.know(this);
				updateQuickslot();
			}

			if (Dungeon.hero.isAlive()) {
				Catalog.setSeen(getClass());
			}
		}
	}

	@Override
	public Item identify( boolean byHero ) {
		super.identify(byHero);

		if (!isKnown()) {
			setKnown();
		}
		return this;
	}

	@Override
	public String name() {
		return isKnown() ? super.name() : Messages.get(this, color);
	}

	@Override
	public String info() {
		return isKnown() ? desc() : Messages.get(this, "unknown_desc");
	}

	@Override
	public boolean isIdentified() {
		return !Dungeon.isChallenged(GOLDFISH_MEMORY) && isKnown();
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	public static HashSet<Class<? extends Tubes>> getKnown() {
		return handler.known();
	}

	public static HashSet<Class<? extends Tubes>> getUnknown() {
		return handler.unknown();
	}

	public static boolean allKnown() {
		return handler.known().size() == Generator.Category.TUBES.classes.length;
	}

	protected int splashColor(){
		return anonymous ? 0x00AAFF : ItemSprite.pick( image, 6, 15 );
	}

	protected void splash( int cell ) {

		Fire fire = (Fire)Dungeon.level.blobs.get( Fire.class );
		if (fire != null)
			fire.clear( cell );

		final int color = splashColor();

		Char ch = Actor.findChar(cell);
		if (ch != null && ch.alignment == Char.Alignment.ALLY) {
			Buff.detach(ch, Burning.class);
			Buff.detach(ch, Ooze.class);
			Splash.at( ch.sprite.center(), color, 5 );
		} else {
			Splash.at( cell, color, 5 );
		}
	}

	@Override
	public long value() {
		return 30 * quantity;
	}

	@Override
	public long energyVal() {
		return 6 * quantity;
	}

	public static class PlaceHolder extends Tubes {

		{
			image = ItemSpriteSheet.TEST_TUBE_HOLDER;
		}

		@Override
		public boolean isSimilar(Item item) {
			return ExoticPotion.regToExo.containsKey(item.getClass())
					|| ExoticPotion.regToExo.containsValue(item.getClass());
		}

		@Override
		public String info() {
			return "";
		}
	}

}

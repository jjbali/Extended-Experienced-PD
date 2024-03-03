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

import static com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene.cancel;
import static com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene.updateItemDisplays;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WeaponEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.Dart;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.TippedDart;
import com.shatteredpixel.shatteredpixeldungeon.journal.Catalog;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MissileSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.InventoryPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.QuickSlotButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.ItemIconTitle;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Item implements Bundlable {

	public static final String AC_STORE		= "STORE";
	public static final String AC_STORE_TAKE		= "STORETAKE";
    protected static final String TXT_TO_STRING_LVL		= "%s %+d";
	protected static final String TXT_TO_STRING_X		= "%s x%d";
	
	protected static final float TIME_TO_THROW		= 1.0f;
	protected static final float TIME_TO_PICK_UP	= 1.0f;
	protected static final float TIME_TO_DROP		= 1.0f;
	
	public static final String AC_DROP		= "DROP";
	public static final String AC_THROW		= "THROW";
	private static final String AC_INCREASE = "INCREASE";
	private static final String AC_IDENTIFY = "IDENTIFY";
	private static final String AC_MULTIPLY = "MULTIPLY";
	public static final String AC_RENAME = "RENAME";
	public static final String AC_AIM		= "AIM_MI";


	public String defaultAction;
	public boolean usesTargeting;

	//TODO should these be private and accessed through methods?
	public int image = 0;
	public int icon = -1; //used as an identifier for items with randomized images
	
	public boolean stackable = false;
	public boolean wereOofed = false;
    protected long quantity = 1;
	public boolean dropsDownHeap = false;
	
	private long level = 0;

	public boolean levelKnown = false;
	
	public boolean cursed;
	public boolean cursedKnown;
	
	// Unique items persist through revival
	public boolean unique = false;

	// These items are preserved even if the hero's inventory is lost via unblessed ankh
	public boolean keptThoughLostInvent = false;

	// whether an item can be included in heroes remains
	public boolean bones = false;
	public String notes = "";
	public String identification = randomID();
	public String identification_2 = randomID(6);
	
	public static final Comparator<Item> itemComparator = new Comparator<Item>() {
		@Override
		public int compare( Item lhs, Item rhs ) {
			return Generator.Category.order( lhs ) - Generator.Category.order( rhs );
		}
	};
	public String customName = "";

	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = new ArrayList<>();
		actions.add( AC_DROP );
		actions.add( AC_THROW );
		if (DeviceCompat.isDebug()) {
			if (stackable) actions.add( AC_INCREASE );
			if (!isIdentified()) actions.add( AC_IDENTIFY );
			if (stackable) actions.add( AC_MULTIPLY );
		}
		return actions;
	}

	public String actionName(String action, Hero hero){
		return Messages.get(this, "ac_" + action);
	}

	public boolean doPickUp(Hero hero) {
		return doPickUp( hero, hero.pos );
	}

	public boolean doPickUp(Hero hero, int pos) {
		return doPickUp( hero, hero.pos, TIME_TO_PICK_UP);
	}

	public boolean doPickUp(Hero hero, int pos, float time) {
		if (collect( hero.belongings.backpack )) {
			
			GameScene.pickUp( this, pos );
			Sample.INSTANCE.play( Assets.Sounds.ITEM );
			if (time > 0f)
				hero.spendAndNext( time );
			return true;
			
		} else {
			return false;
		}
	}
	
	public void doDrop( Hero hero ) {
		hero.spendAndNext(TIME_TO_DROP);
		int pos = hero.pos;
		Dungeon.level.drop(detachAll(hero.belongings.backpack), pos).sprite.drop(pos);
	}

	//resets an item's properties, to ensure consistency between runs
	public void reset(){
		keptThoughLostInvent = false;
	}

	public void doThrow( Hero hero ) {
		GameScene.selectCell(thrower);
	}
	public void doAim( Hero hero ) {
		GameScene.selectCell(aimer);
	}

	
	public void execute( Hero hero, String action ) {

		cancel();
		curUser = hero;
		curItem = this;

		if (action.equals( AC_DROP )) {
			if (hero.belongings.backpack.contains(this) || isEquipped(hero)) {
				doDrop(hero);
			}
		} else if (action.equals( AC_THROW )) {
			if (hero.belongings.backpack.contains(this) || isEquipped(hero)) {
				doThrow(hero);
			}
		} else if (action.equals( AC_INCREASE )) {
			if (hero.belongings.backpack.contains(this) || !isEquipped(hero)) {
				askAmount(curItem);
			}
		} else if (action.equals( AC_IDENTIFY )) {
			curItem.identify();
		} else if (action.equals( AC_MULTIPLY )) {
			if (hero.belongings.backpack.contains(this) || !isEquipped(hero)) {
				askMulti(curItem);
			}
		} else if (action.equals( AC_RENAME )) {
			if (hero.belongings.backpack.contains(this) || isEquipped(hero)) {
				rename(curItem);
			}
		} else if (action.equals( AC_AIM )) {
			if (hero.belongings.backpack.contains(this) || isEquipped(hero)) {
				doAim(hero);
			}
		} else if (action.equals( AC_STORE )) {
			doAddStorage( hero );
		} else if (action.equals( AC_STORE_TAKE )) {
			doTakeStorage( hero );
		}
	}

	public void doTakeStorage( Hero hero ) {
		hero.spendAndNext( TIME_TO_DROP );
		Dungeon.level.drop( detachAll( hero.storage.backpack ), hero.pos ).sprite.drop( hero.pos );
	}

	public void doAddStorage( Hero hero ) {
		if( collect( hero.storage.backpack) )
		{
			hero.spendAndNext( TIME_TO_DROP );
			detachAll( hero.belongings.backpack );
		}
	}

	private void rename(Item item) {
		GameScene.show( new WndTextInput( "Rename","", item.name(), 50, false, "Rename", "Cancel" ) {
			@Override
			public void onSelect( boolean positive, String text ) {
				if (text != null && positive && !text.equals(item.name())) {
					curItem.customName = text;
				} else if (text != null && positive && text.equals(item.name())) {
					curItem.customName = "";
				}
			}

			@Override
			public void onBackPressed() {
				GLog.w("You didn't set a name for this.");
			}
		} );
	}

	private void askAmount( Item item ) {
		GameScene.show( new WndTextInput( "Input Quantity","", "", 8, false, "Done", "Cancel" ) {
			@Override
			public void onSelect( boolean positive, String text ) {
				int amount = 1;
				try {
					amount = Integer.parseInt( text );
				} catch ( NumberFormatException e ) {
					GLog.w( "Invalid number" );
				}
				item.quantity( amount );
			}
		} );
	}

	private void askMulti( Item item ) {
		GameScene.show( new WndTextInput( "Input Multiplier","", "", 8, false, "Done", "Cancel" ) {
			@Override
			public void onSelect( boolean positive, String text ) {
				int amount = 1;
				try {
					amount = Integer.parseInt( text );
				} catch ( NumberFormatException e ) {
					GLog.w( "Invalid number" );
				}
				item.quantity( quantity() * amount );
			}
		} );
	}

	//can be overridden if default action is variable
	public String defaultAction(){
		return defaultAction;
	}
	
	public void execute( Hero hero ) {
		String action = defaultAction();
		if (action != null) {
			execute(hero, defaultAction());
		}
	}
	
	protected void onThrow( int cell ) {
		Heap heap = Dungeon.level.drop( this, cell );
		if (!heap.isEmpty()) {
			heap.sprite.drop( cell );
		}
	}
	
	//takes two items and merges them (if possible)
	public Item merge( Item other ){
		if (isSimilar( other )) {
			quantity += other.quantity;
			other.quantity = 0;
			if (notes.isEmpty()) {
				notes = other.notes;
			}
		}
		return this;
	}

	public final String randomID(int length) {
		String glitchy = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++){
			builder.append(glitchy.charAt(Random.Int(glitchy.length())));
		}
		glitchy = String.valueOf(builder);
		return glitchy;
	}

	public String randomID() {
		String glitchy = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 10; i++){
			builder.append(glitchy.charAt(Random.Int(glitchy.length())));
		}
		glitchy = String.valueOf(builder);
		return glitchy;
	}
	
	public boolean collect( Bag container ) {

		if (quantity <= 0){
			return true;
		}

		ArrayList<Item> items = container.items;

		if (items.contains( this )) {
			return true;
		}

		for (Item item:items) {
			if (item instanceof Bag && ((Bag)item).canHold( this )) {
				if (collect( (Bag)item )){
					return true;
				}
			}
		}

		if (!container.canHold(this)){
			return false;
		}

		//if (notes.isEmpty() && !(this instanceof MissileWeapon)) {
			//notes = Messages.get(this, "depth_note", Dungeon.depth);
			//generates unique id every game (doesn't depends on seed)
			//notes += "\nID: " + randomID();
		//}
		
		if (stackable) {
			for (Item item:items) {
				if (isSimilar( item )) {
					item.merge( this );
					updateQuickslot();
					if (Dungeon.hero != null && Dungeon.hero.isAlive()) {
						Badges.validateItemLevelAquired( this );
						Talent.onItemCollected(Dungeon.hero, item);
						if (isIdentified()) Catalog.setSeen(getClass());
					}
					if (TippedDart.lostDarts > 0){
						Dart d = new Dart();
						d.quantity(TippedDart.lostDarts);
						TippedDart.lostDarts = 0;
						if (!d.collect()){
							//have to handle this in an actor as we can't manipulate the heap during pickup
							Actor.add(new Actor() {
								{ actPriority = VFX_PRIO; }
								@Override
								protected boolean act() {
									Dungeon.level.drop(d, Dungeon.hero.pos).sprite.drop();
									Actor.remove(this);
									return true;
								}
							});
						}
					}
					return true;
				}
			}
		}

		if (Dungeon.hero != null && Dungeon.hero.isAlive()) {
			Badges.validateItemLevelAquired( this );
			Talent.onItemCollected( Dungeon.hero, this );
			if (isIdentified()) Catalog.setSeen(getClass());
		}

		items.add( this );
		Dungeon.quickslot.replacePlaceholder(this);
		Collections.sort( items, itemComparator );
		updateQuickslot();
		return true;

	}
	
	public boolean collect() {
		return collect( Dungeon.hero.belongings.backpack );
	}
	
	//returns a new item if the split was sucessful and there are now 2 items, otherwise null
	public Item split( long amount ){
		if (amount <= 0 || amount >= quantity()) {
			return null;
		} else {
			//pssh, who needs copy constructors?
			Item split = Reflection.newInstance(getClass());
			
			if (split == null){
				return null;
			}
			
			Bundle copy = new Bundle();
			this.storeInBundle(copy);
			split.restoreFromBundle(copy);
			split.quantity(amount);
			split.notes = "";
			quantity -= amount;
			
			return split;
		}
	}
	
	public final Item detach( Bag container ) {
		
		if (quantity <= 0) {
			
			return null;
			
		} else
		if (quantity == 1) {

			if (stackable){
				Dungeon.quickslot.convertToPlaceholder(this);
			}

			return detachAll( container );
			
		} else {
			
			
			Item detached = split(1);
			updateQuickslot();
			if (detached != null) detached.onDetach( );
			return detached;
			
		}
	}

	protected boolean notePersists() {
		return false;
	}
	
	public final Item detachAll( Bag container ) {
		Dungeon.quickslot.clearItem( this );

		if (!notePersists()) {
			notes = "";
		}

		for (Item item : container.items) {
			if (item == this) {
				container.items.remove(this);
				item.onDetach();
				container.grabItems(); //try to put more items into the bag as it now has free space
				updateQuickslot();
				return this;
			} else if (item instanceof Bag) {
				Bag bag = (Bag)item;
				if (bag.contains( this )) {
					return detachAll( bag );
				}
			}
		}

		updateQuickslot();
		return this;
	}
	
	public boolean isSimilar( Item item ) {
		return level == item.level && getClass() == item.getClass();
	}

	public void onDetach(){}

	//returns the true level of the item, ignoring all modifiers aside from upgrades
	public final long trueLevel(){
		return level;
	}

	//returns the persistant level of the item, only affected by modifiers which are persistent (e.g. curse infusion)
	public long level(){
		return level;
	}
	
	//returns the level of the item, after it may have been modified by temporary boosts/reductions
	//note that not all item properties should care about buffs/debuffs! (e.g. str requirement)
	public long buffedLvl(){
		//only the hero can be affected by Degradation
		if (Dungeon.hero.buff( Degrade.class ) != null
			&& (isEquipped( Dungeon.hero ) || Dungeon.hero.belongings.contains( this ))) {
			return Degrade.reduceLevel(level());
		} else {
			return level();
		}
	}

	public void level( long value ){
		level = value;

		updateQuickslot();
	}
	
	public Item upgrade() {
		
		this.level++;

		updateQuickslot();
		
		return this;
	}

	final public Item upgrade(long n ) {
		if (n > 20){
			for (long i=0; i < 20; i++) {
				upgrade();
			}
			this.level += n - 20;
			updateQuickslot();
		} else {
			for (long i = 0; i < n; i++) {
				upgrade();
			}
		}
		
		return this;
	}
	
	public Item degrade() {
		
		this.level--;
		
		return this;
	}

	final public Item degrade(long n ) {
		if (n > 20){
			for (long i=0; i < 20; i++) {
				degrade();
			}
			this.level -= n - 20;
			updateQuickslot();
		} else {
			for (long i = 0; i < n; i++) {
				degrade();
			}
		}
		
		return this;
	}
	
	public long visiblyUpgraded() {
		return levelKnown ? level() : 0;
	}

	public long buffedVisiblyUpgraded() {
		return levelKnown ? buffedLvl() : 0;
	}
	
	public boolean visiblyCursed() {
		return cursed && cursedKnown;
	}
	
	public boolean isUpgradable() {
		return true;
	}
	
	public boolean isIdentified() {
		return levelKnown && cursedKnown;
	}
	
	public boolean isEquipped( Hero hero ) {
		return false;
	}

	public final Item identify(){
		return identify(true);
	}

	public Item identify( boolean byHero ) {

		if (byHero && Dungeon.hero != null && Dungeon.hero.isAlive()){
			Catalog.setSeen(getClass());
			if (!isIdentified()) Talent.onItemIdentified(Dungeon.hero, this);
		}

		levelKnown = true;
		cursedKnown = true;
		Item.updateQuickslot();
		
		return this;
	}
	
	public void onHeroGainExp( float levelPercent, Hero hero ){
		//do nothing by default
	}

	public boolean needsAim() {
		return false;
	}

	//A list of all tiles the aim button highlights.
	public List<Integer> aimTiles(int target) {
		Ballistica b = new Ballistica(Dungeon.hero.pos, target, Ballistica.WONT_STOP);
		return b.subPath(1, b.dist);
	}
	
	public static void evoke( Hero hero ) {
		hero.sprite.emitter().burst( Speck.factory( Speck.EVOKE ), 5 );
	}

	public String title() {

		String name = name();

		if (visiblyUpgraded() != 0)
			name = Messages.format( TXT_TO_STRING_LVL, name, visiblyUpgraded()  );

		if (quantity > 1)
			name = Messages.format( TXT_TO_STRING_X, name, quantity );


		return name;

	}

	public String name() {
		if (this.customName.equals("") || this.customName.isEmpty()) {
			return trueName();
		}
		return this.customName;
	}
	
	public final String trueName() {
		return Messages.get(this, "name");
	}
	
	public int image() {
		return image;
	}
	
	public ItemSprite.Glowing glowing() {
		return null;
	}

	public Emitter emitter() { return null; }
	
	public String info() {
		return desc();
	}
	
	public String desc() {
		return Messages.get(this, "desc");
	}
	
	public long quantity() {
		return quantity;
	}
	
	public Item quantity( long value ) {
		quantity = value;
		return this;
	}

	//item's value in gold coins
	public long value() {
		return 0;
	}

	//item's value in energy crystals
	public long energyVal() {
		return 0;
	}

	public final long sellPrice(){
		return value() * 5 * (Dungeon.depth / 5 + 1);
	}
	
	public Item virtual(){
		Item item = Reflection.newInstance(getClass());
		if (item == null) return null;
		
		item.quantity = 0;
		item.level = level;
		return item;
	}
	
	public Item random() {
		return this;
	}
	public String status() {
		return quantity != 1 ? Long.toString( quantity ) : null;
	}

	public static void updateQuickslot() {
		GameScene.updateItemDisplays = true;
	}
	
	private static final String QUANTITY		= "quantity";
	private static final String LEVEL			= "level";
	private static final String LEVEL_KNOWN		= "levelKnown";
	private static final String CURSED			= "cursed";
	private static final String CURSED_KNOWN	= "cursedKnown";
	private static final String QUICKSLOT		= "quickslotpos";
	private static final String KEPT_LOST       = "kept_lost";
	private static final String WERE_OOFED      = "were_oofed";
	private static final String NOTES = "player_notes";
	private static final String IDENTIFICATION = "ID";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		bundle.put( QUANTITY, quantity );
		bundle.put( LEVEL, level );
		bundle.put( LEVEL_KNOWN, levelKnown );
		bundle.put( CURSED, cursed );
		bundle.put( CURSED_KNOWN, cursedKnown );
		if (Dungeon.quickslot.contains(this)) {
			bundle.put( QUICKSLOT, Dungeon.quickslot.getSlot(this) );
		}
		bundle.put( KEPT_LOST, keptThoughLostInvent );
		bundle.put( WERE_OOFED, wereOofed);
		bundle.put( NOTES, notes );
		bundle.put( IDENTIFICATION, identification_2 );

		if (!this.customName.equals("")) {
			bundle.put("customName", this.customName);
		}
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		quantity	= bundle.getLong( QUANTITY );
		levelKnown	= bundle.getBoolean( LEVEL_KNOWN );
		cursedKnown	= bundle.getBoolean( CURSED_KNOWN );

		long level = bundle.getLong( LEVEL );
		if (level > 0) {
			upgrade( level );
		} else if (level < 0) {
			degrade( -level );
		}
		
		cursed	= bundle.getBoolean( CURSED );

		//only want to populate slot on first load.
		if (Dungeon.hero == null) {
			if (bundle.contains(QUICKSLOT)) {
				Dungeon.quickslot.setSlot(bundle.getInt(QUICKSLOT), this);
			}
		}

		keptThoughLostInvent = bundle.getBoolean( KEPT_LOST );
		if (bundle.contains("customName")) {
			this.customName = bundle.getString("customName");
		}
		notes = bundle.getString(NOTES);
		identification_2 = bundle.getString(IDENTIFICATION);

	}

	public void editNotes(IconButton toUpdate) {
		ShatteredPixelDungeon.scene().addToFront(new WndTextInput(Messages.get(this, "note_prompt"), null,
				notes, -1, true,
				Messages.get(this, "note_ok"),
				Messages.get(this, "note_nok")) {
			@Override
			public void onSelect(boolean positive, String text) {
				if (positive) {
					notes = text;
					toUpdate.icon(ItemIconTitle.noteIconByText(text));
				}
			}
		});
	}

	public int targetingPos( Hero user, int dst ){
		return throwPos( user, dst );
	}

	public int throwPos( Char user, int dst){
		return new Ballistica( user.pos, dst, Ballistica.PROJECTILE ).collisionPos;
	}

    public int throwPos( Hero user, int dst){
        return new Ballistica( user.pos, dst, Ballistica.PROJECTILE ).collisionPos;
    }

	public void throwSound(){
		Sample.INSTANCE.play(Assets.Sounds.MISS, 0.6f, 0.6f, 1.5f);
	}
	
	public void cast( final Hero user, final int dst ) {
		
		final int cell = throwPos( user, dst );
		user.sprite.zap( cell );
		user.busy();

		throwSound();

		Char enemy = Actor.findChar( cell );
		QuickSlotButton.target(enemy);
		
		final float delay = castDelay(user, dst);

		if (enemy != null) {
			((MissileSprite) user.sprite.parent.recycle(MissileSprite.class)).
					reset(user.sprite,
							enemy.sprite,
							this,
							new Callback() {
						@Override
						public void call() {
							curUser = user;
							Item i = Item.this.detach(user.belongings.backpack);
							if (i != null) i.onThrow(cell);
							if (curUser.heroClass == HeroClass.WARRIOR
									&& !(Item.this instanceof MissileWeapon)
									&& curUser.buff(Talent.ImprovisedProjectileCooldown.class) == null){
								if (enemy != null && enemy.alignment != curUser.alignment){
									Sample.INSTANCE.play(Assets.Sounds.HIT);
									Buff.affect(enemy, Blindness.class, 3);
									Buff.affect(curUser, Talent.ImprovisedProjectileCooldown.class, 40f);
								}
							}
							if (user.buff(Talent.LethalMomentumTracker.class) != null){
								user.buff(Talent.LethalMomentumTracker.class).detach();
								user.next();
							} else {
								user.spendAndNext(delay);
							}
						}
					});
		} else {
			((MissileSprite) user.sprite.parent.recycle(MissileSprite.class)).
					reset(user.sprite,
							cell,
							this,
							new Callback() {
						@Override
						public void call() {
							curUser = user;
							Item i = Item.this.detach(user.belongings.backpack);
							if (i != null) i.onThrow(cell);
							user.spendAndNext(delay);
						}
					});
		}
	}

    public void cast( final Char user, final int dst ) {

        final int cell = throwPos( user, dst );

        throwSound();

        Char enemy = Actor.findChar( cell );
        Item itLink = this;
        if (enemy != null) {

            ((MissileSprite) user.sprite.parent.recycle(MissileSprite.class)).
                    reset(user.sprite,
                            enemy.sprite,
                            this,
                            new Callback() {
                                @Override
                                public void call() {
                                    Dungeon.level.drop(itLink, cell).sprite.drop(cell);
                                    user.next();
                                }
                            });
        } else {
            ((MissileSprite) user.sprite.parent.recycle(MissileSprite.class)).
                    reset(user.sprite,
                            cell,
                            this,
                            new Callback() {
                                @Override
                                public void call() {
                                    Dungeon.level.drop(itLink, cell).sprite.drop(cell);
                                    user.next();
                                }
                            });
        }
    }
	
	public float castDelay( Char user, int dst ){
		return TIME_TO_THROW;
	}
	
	protected static Hero curUser = null;
	protected static Item curItem = null;
	protected static CellSelector.Listener thrower = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				curItem.cast( curUser, target );
			}
		}
		@Override
		public String prompt() {
			return Messages.get(Item.class, "prompt");
		}
	};

	private static final CellSelector.Listener aimer = new CellSelector.Listener() {
		@Override
		public void onSelect(Integer cell) {
			if (cell != null) {
				List<Integer> cells = curItem.aimTiles(cell);
				if (cells.isEmpty()) {
					GLog.w(Messages.get(curItem, "no_aim"));
				}
				for (int i : cells) {
					Dungeon.hero.sprite.parent.add(new TargetedCell(i, 0xFF0000));
				}
			}
		}

		@Override
		public String prompt() {
			return Messages.get(Item.class, "aim_prompt");
		}
	};

	public String anonymousName() {
		return "ITEM_NAME_ANONYMOUS";
	}
}

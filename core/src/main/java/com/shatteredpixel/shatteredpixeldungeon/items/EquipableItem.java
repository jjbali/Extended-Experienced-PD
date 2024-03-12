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

import static com.shatteredpixel.shatteredpixeldungeon.Challenges.INTUITION_TEST;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Degrade;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LostInventory;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.Artifact;
import com.shatteredpixel.shatteredpixeldungeon.items.journal.Guidebook;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.journal.Document;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.ListUtils;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;


import java.util.ArrayList;

public abstract class EquipableItem extends Item {

	public static final String AC_EQUIP		= "EQUIP";
	public static final String AC_UNEQUIP	= "UNEQUIP";
	public static final String AC_DISMANTLE = "DISMANTLE";
	public static final String AC_UPGRADE = "UPGRADE";
	public static final String AC_INDENTIFY = "IDENTIFY";
	private static final String AC_ENCHANT = "ENCHANT";
	private static final String AC_EXTRACT = "EXTRACT";

	{
		bones = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( isEquipped( hero ) ? AC_UNEQUIP : AC_EQUIP );
		actions.add( AC_DISMANTLE );
		if (DeviceCompat.isDebug()) {
			if (isUpgradable()) actions.add( AC_UPGRADE );
			if (this instanceof Weapon) actions.add( AC_ENCHANT );
		}
		if (this instanceof MeleeWeapon) actions.add( AC_EXTRACT );
		return actions;
	}

	@Override
	public boolean doPickUp(Hero hero, int pos, float time) {
		if (super.doPickUp(hero, pos, time)){
			if (!isIdentified() && !Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_IDING)){
				GLog.p(Messages.get(Guidebook.class, "hint"));
				GameScene.flashForDocument(Document.ADVENTURERS_GUIDE, Document.GUIDE_IDING);
			}
			return true;
		} else {
			return false;
		}
	}

	protected static int slotOfUnequipped = -1;

	@Override
	public void execute( Hero hero, String action ) {

		super.execute( hero, action );

		if (action.equals( AC_EQUIP )) {
			//In addition to equipping itself, item reassigns itself to the quickslot
			//This is a special case as the item is being removed from inventory, but is staying with the hero.
			int slot = Dungeon.quickslot.getSlot( this );
			slotOfUnequipped = -1;
			doEquip(hero);
			if (slot != -1) {
				Dungeon.quickslot.setSlot( slot, this );
				updateQuickslot();
			//if this item wasn't quickslotted, but the item it is replacing as equipped was
			//then also have the item occupy the unequipped item's quickslot
			} else if (slotOfUnequipped != -1 && defaultAction() != null) {
				Dungeon.quickslot.setSlot( slotOfUnequipped, this );
				updateQuickslot();
			}
		} else if (action.equals( AC_UNEQUIP )) {
			doUnequip( hero, true );
		} else if (action.equals( AC_DISMANTLE )) {

			if (hero.buff(Degrade.class) != null) {
				GLog.w( Messages.get(this, "degrade"));
			} else if (curItem.isEquipped(hero)) {
				GLog.w( Messages.get(this, "unequip_first"));
			} else if (curItem instanceof Artifact) {
				curItem.detach(curUser.belongings.backpack);
				Dungeon.level.drop(new ArcaneCatalyst().quantity(Random.Int(3, 20)), curUser.pos).sprite.drop();
				if (Random.Float() < 0.15f) {
					Dungeon.level.drop(new ScrollOfUpgrade().quantity(1), curUser.pos).sprite.drop();
				}
				updateQuickslot();

				hero.spendAndNext( 1f );

				Sample.INSTANCE.play(Assets.Sounds.DRINK);
				curUser.sprite.operate(curUser.pos);
			} else if (curItem instanceof MissileWeapon) {
				curItem.detach(curUser.belongings.backpack);
				if (Random.Float() < 0.15f) {
					Dungeon.level.drop(new ScrollOfUpgrade().quantity(1), curUser.pos).sprite.drop();
				} else {
					Dungeon.level.drop(new LiquidMetal().quantity(Random.Int(3, 20)), curUser.pos).sprite.drop();
				}
				updateQuickslot();

				hero.spendAndNext( 1f );

				Sample.INSTANCE.play(Assets.Sounds.DRINK);
				curUser.sprite.operate(curUser.pos);
			} else {
				curItem.detach(curUser.belongings.backpack);
				if (curItem.level() > 0) {
					Dungeon.level.drop(new ScrollOfUpgrade().quantity(curItem.level()), curUser.pos).sprite.drop();
				} else {
					Dungeon.gold += Dungeon.NormalIntRange(500, 1000);
				}
				updateQuickslot();

				hero.spendAndNext( 1f );

				Sample.INSTANCE.play(Assets.Sounds.DRINK);
				curUser.sprite.operate(curUser.pos);
			}
			GameScene.show(new WndBag(Dungeon.hero.belongings.backpack));
		} else if (action.equals( AC_UPGRADE )) {
			askUpgradeLevel(curItem);
		} else if (action.equals( AC_INDENTIFY )) {
			curItem.identify();
		} else if (action.equals( AC_ENCHANT )) {
			askEnchant(curItem);
		} else if (action.equals( AC_EXTRACT )) {


			if (hero.buff(Degrade.class) != null) {
				GLog.w( "While degraded, you can't extract!");
			} else if (curItem.isEquipped(hero)) {
				GLog.w( "To extract, you must unequip the item first!" );
			} else {
				if (curItem.level() > 0) {
					Dungeon.level.drop(new ScrollOfUpgrade().quantity(curItem.level()), curUser.pos).sprite.drop();
				} else {
					GLog.w( "To extract, the item level must be greater to zero!" );
				}
				curItem.degrade(curItem.level());
				updateQuickslot();

				hero.spendAndNext( 1f );

				Sample.INSTANCE.play(Assets.Sounds.DRINK);
				curUser.sprite.operate(curUser.pos);
			}


		}
	}



	private void askEnchant( Item item ) {

		GameScene.show( new PotionOfDebug.WndBetterOptions( "Choose Enchant", "Pick Category", "Normal", "Curses", "None" ) {
			@Override
			protected void onSelect( int index ) {
				Class<?>[] enchants;
				if ( index == 0 ) enchants = Weapon.Enchantment.allEnchants();
				else if ( index == 1 ) enchants = Weapon.Enchantment.getCurses();
				else {
					GLog.w("None selected");
					return;
				}
				GameScene.show( new PotionOfDebug.WndBetterOptions( "Choose enchant", "Pick an enchantment",
						ListUtils.map( enchants, String.class, Class::getSimpleName ) ) {
					@Override
					protected void onSelect( int index ) {
						((Weapon) item).enchant( (Weapon.Enchantment) Reflection.newInstance( enchants[index] ) );
					}
				} );
			}
		} );
	}

	private void askUpgradeLevel( Item item ) {
		GameScene.show( new WndTextInput( "Input Level","", "", 6, false, "Done", "Cancel" ) {
			@Override
			public void onSelect( boolean positive, String text ) {
				int level = 0;
				try {
					if ( positive ) {
						level = Integer.parseInt( text );
					}
				} catch ( NumberFormatException e ) {
					GLog.w( "Invalid number" );
				}
				item.upgrade( level );
			}
		} );
	}

	@Override
	public void doDrop( Hero hero ) {
		if (!isEquipped( hero ) || doUnequip( hero, false, false )) {
			super.doDrop( hero );
		}
	}

	@Override
	public void cast( final Hero user, int dst ) {

		if (isEquipped( user )) {
			if (quantity == 1 && !this.doUnequip( user, false, false )) {
				return;
			}
		}

		super.cast( user, dst );
	}

	public static void equipCursed( Hero hero ) {
		hero.sprite.emitter().burst( ShadowParticle.CURSE, 6 );
		Sample.INSTANCE.play( Assets.Sounds.CURSED );
	}

	protected float time2equip( Hero hero ) {
		return 1;
	}

	public abstract boolean doEquip( Hero hero );

	public interface Tierable {
		int tier();
	}

	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {

		if (cursed
				&& hero.buff(MagicImmune.class) == null
				&& (hero.buff(LostInventory.class) == null || keptThoughLostInvent)) {
			GLog.w(Messages.get(EquipableItem.class, "unequip_cursed"));
			return false;
		}

		if (single) {
			hero.spendAndNext( time2equip( hero ) );
		} else {
			hero.spend( time2equip( hero ) );
		}

		slotOfUnequipped = Dungeon.quickslot.getSlot(this);

		//temporarily keep this item so it can be collected
		boolean wasKept = keptThoughLostInvent;
		keptThoughLostInvent = true;
		if (!collect || !collect( hero.belongings.backpack )) {
			onDetach();
			Dungeon.quickslot.clearItem(this);
			updateQuickslot();
			if (collect) Dungeon.level.drop( this, hero.pos ).sprite.drop();
		}
		keptThoughLostInvent = wasKept;

		return true;
	}

	final public boolean doUnequip( Hero hero, boolean collect ) {
		return doUnequip( hero, collect, true );
	}

	public void activate( Char ch ){}
}

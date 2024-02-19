/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.PurpleParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.EquipableItem;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.KindofMisc;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;

public class EnchantingTrap extends Trap {

	{
		color = TEAL;
		shape = CROSSHAIR;
	}

	@Override
	public void activate() {
		if (Dungeon.level.heroFOV[ pos ]) {
			CellEmitter.get(pos).burst(PurpleParticle.BURST, 10);
			Sample.INSTANCE.play(Assets.Sounds.BURNING);
		}

		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null){
			for (Item item : heap.items){
				if (item.isUpgradable() && !(item instanceof MissileWeapon)||item instanceof SpiritBow)
					curse(item);
			}
		}

		if (Dungeon.hero.pos == pos && !Dungeon.hero.flying){
			curse(Dungeon.hero);
		}
	}

	public static void curse(Hero hero){
		//items the trap wants to curse because it will create a more negative effect
		ArrayList<Item> priorityCurse = new ArrayList<>();
		//items the trap can curse if nothing else is available.
		ArrayList<Item> canCurse = new ArrayList<>();

		KindOfWeapon weapon = hero.belongings.weapon;
		if (weapon instanceof Weapon && !weapon.cursed){
			if (((Weapon) weapon).enchantment == null)
				priorityCurse.add(weapon);
			else
				canCurse.add(weapon);
		}

		Armor armor = hero.belongings.armor;
		if (armor != null && !armor.cursed){
			if (armor.glyph == null)
				priorityCurse.add(armor);
			else
				canCurse.add(armor);
		}

		Collections.shuffle(priorityCurse);
		Collections.shuffle(canCurse);

		int numCurses = Random.Int(2) == 0 ? 1 : 2;

		for (int i = 0; i < numCurses; i++){
			if (!priorityCurse.isEmpty()){
				curse(priorityCurse.remove(0));
			} else if (!canCurse.isEmpty()){
				curse(canCurse.remove(0));
			}
		}

		//EquipableItem.equipCursed(hero);
		GLog.p( Messages.get(EnchantingTrap.class, "curse") );
	}

	private static void curse(Item item){

		if (item instanceof Weapon){
			Weapon w = (Weapon) item;
			//if (w.enchantment == null){
			w.enchant();
			//}
		}
		if (item instanceof Armor){
			Armor a = (Armor) item;
			//if (a.glyph == null){
			a.inscribe();
			//}
		}
	}
}

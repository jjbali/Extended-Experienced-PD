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

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Overload;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class TubeOfUltimatePower extends Tubes {

	{
		icon = ItemSpriteSheet.Icons.RING_WEALTH;

		bones = true;
	}
	
	@Override
	public void apply( Hero hero ) {
		identify();
		hero.earnExp(10L * hero.lvl, TubeOfUltimatePower.class);
		Buff.affect(hero, Bless.class, 50f * hero.lvl);
		Buff.affect(hero, Overload.class, 50f * hero.lvl);
	}

	@Override
	public long value() {
		return isKnown() ? 250 * quantity : super.value();
	}

	@Override
	public long energyVal() {
		return isKnown() ? 8 * quantity : super.energyVal();
	}
}

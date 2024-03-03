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

package com.shatteredpixel.shatteredpixeldungeon.items.spells;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.InventoryScroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfMetamorphosis;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndEnergizeItem;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndInfoItem;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTradeItem;
import com.watabou.noosa.audio.Sample;

public class Metamorph extends Spell {
	
	{
		image = ItemSpriteSheet.RESPAWN;
		unique = true;
	}

	private static WndBag parentWnd;
	
	@Override
	protected void onCast(Hero hero) {
		GameScene.show(new ScrollOfMetamorphosis.WndMetamorphChoose());
		detach(Dungeon.hero.belongings.backpack);
	}

	public void confirmCancelation_metamoprh( Window chooseWindow ) {
		GameScene.show( new WndOptions(new ItemSprite(this),
				Messages.titleCase(name()),
				"Do you really want to cancel this spell usage? It will be consumed anyway.",
				"Yes, I'm positive",
				"No, I changed my mind" ) {
			@Override
			protected void onSelect( int index ) {
				switch (index) {
					case 0:
						curUser.spendAndNext( 1f );
						chooseWindow.hide();
						break;
					case 1:
						//do nothing
						break;
				}
			}
			public void onBackPressed() {}
		} );
	}
	
	@Override
	public long value() {
		//prices of ingredients, divided by output quantity, rounds down
		return (int)(400 * (quantity/8f));
	}

	//TODO also allow alchemical catalyst? Or save that for an elixir/brew?
	public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

		{
			inputs =  new Class[]{ScrollOfTransmutation.class, ArcaneCatalyst.class};
			inQuantity = new int[]{1, 1};
			
			cost = 2;
			
			output = Metamorph.class;
			outQuantity = 8;
		}
		
	}
}

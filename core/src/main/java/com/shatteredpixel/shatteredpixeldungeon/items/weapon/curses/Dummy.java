package com.shatteredpixel.shatteredpixeldungeon.items.weapon.curses;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;

public class Dummy extends Weapon.Enchantment {
	private static final ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

	@Override
	public boolean curse() {
		return true;
	}

	@Override
	public long proc( Weapon weapon, Char attacker, Char defender, long damage ) {
		return damage;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return BLACK;
	}
}
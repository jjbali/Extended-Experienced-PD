package com.shatteredpixel.shatteredpixeldungeon.items.weapon;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfSharpshooting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.SpiritBow;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PhantasticalBow extends SpiritBow {
    {
        image = ItemSpriteSheet.SPIRIT_BOW;
    }

    private static final ItemSprite.Glowing RANDOM1 = new ItemSprite.Glowing( 0xFFFFFF );
    private static final ItemSprite.Glowing RANDOM2 = new ItemSprite.Glowing( 0x000000 );

    @Override
    public ItemSprite.Glowing glowing() {
        int flash = Dungeon.Int(2);
        return flash == 0 ? RANDOM1 : RANDOM2;
    }

    @Override
    public int STRReq(long lvl) {
        return STRReq(2, lvl); //tier 2
    }

    @Override
    public long min(long lvl) {
        long dmg = 10 + Math.round(Dungeon.hero.lvl/6f)
                + RingOfSharpshooting.levelDamageBonus(Dungeon.hero)/2
                + (curseInfusionBonus ? 1 + Dungeon.hero.lvl/30 : 0);
        return Math.max(0, dmg * ((Dungeon.depth /5) + 3));
    }

    @Override
    public long max(long lvl) {
        long dmg = 50 + (int)(Dungeon.hero.lvl/4f)
                + RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 2 + Dungeon.hero.lvl/15 : 0);
        return Math.max(0, dmg * ((Dungeon.depth /5) + 3));
    }
}
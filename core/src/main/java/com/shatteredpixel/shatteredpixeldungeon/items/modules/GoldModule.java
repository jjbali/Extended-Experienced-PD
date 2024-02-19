package com.shatteredpixel.shatteredpixeldungeon.items.modules;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class GoldModule extends Module {

    {
        stackable = true;
        image = ItemSpriteSheet.MODULE_ITEM;

        defaultAction = AC_USE;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0xFFEEEE );
    }
    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
        if (action.equals(AC_USE)) {
            int quantity = Dungeon.IntRange(1000 * Dungeon.escalatingDepth(), 2500 * Dungeon.escalatingDepth());
            Dungeon.gold += quantity;
            hero.sprite.showStatusWithIcon(CharSprite.NEUTRAL, Integer.toString(quantity), FloatingText.GOLD);
            detach(hero.belongings.backpack);
        }
    }



    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public long value() {
        return 1500 * quantity * Dungeon.escalatingDepth();
    }

    @Override
    public Item random() {
        quantity = (Dungeon.IntRange( 1 + Dungeon.escalatingDepth() * 2, 2 + Dungeon.escalatingDepth() * 2 ));
        return this;
    }

    @Override
    public String name() {
        return "Gold Module";
    }

    @Override
    public String desc() {
        return "This module gives you a gold.";
    }
}

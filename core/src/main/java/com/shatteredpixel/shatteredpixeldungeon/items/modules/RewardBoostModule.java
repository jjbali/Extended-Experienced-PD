package com.shatteredpixel.shatteredpixeldungeon.items.modules;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SuperExp;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class RewardBoostModule extends Module {

    {
        stackable = true;
        image = ItemSpriteSheet.MODULE_ITEM;

        defaultAction = AC_USE;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0xAA00AA );
    }
    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
        if (action.equals(AC_USE)) {

            Buff.prolong(hero, RewardBoost2.class, 10 + hero.lvl/2f);

            detach(hero.belongings.backpack);
        }
    }




    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public long value() {
        return 35000 * quantity * Dungeon.depth;
    }

    @Override
    public Item random() {
        quantity = (Dungeon.IntRange( 1 + Dungeon.escalatingDepth() * 2, 2 + Dungeon.escalatingDepth() * 2 ));
        return this;
    }

    @Override
    public String name() {
        return "Reward Boost Module";
    }

    @Override
    public String desc() {
        return "This module gives you a reward and 30% exp boost for a short time (scales with hero's level).";
    }

    public static class RewardBoost2 extends FlavourBuff {
        {
            type = buffType.POSITIVE;
        }

        @Override
        public int icon() {
            return BuffIndicator.UPGRADE;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0x00FF00);
        }

        @Override
        public String name() {
            return "Reward Boost";
        }

        @Override
        public String desc() {
            return "Did you use that module before?";
        }
    }
}

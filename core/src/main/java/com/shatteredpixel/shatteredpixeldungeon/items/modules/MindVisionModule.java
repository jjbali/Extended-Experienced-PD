package com.shatteredpixel.shatteredpixeldungeon.items.modules;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class MindVisionModule extends Module {

    {
        stackable = true;
        image = ItemSpriteSheet.MODULE_ITEM;

        defaultAction = AC_USE;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0x3ff3f3 );
    }
    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
        if (action.equals(AC_USE)) {
            Buff.affect( hero, MindVision.class, MindVision.DURATION * 5 * (hero.lvl/2f));
            SpellSprite.show(hero, SpellSprite.VISION, 1, 0.77f, 0.9f);
            Dungeon.observe();

            if (Dungeon.level.mobs.size() > 0) {
                GLog.i( Messages.get(PotionOfMindVision.class, "see_mobs") );
            } else {
                GLog.i( Messages.get(PotionOfMindVision.class, "see_none") );
            }
            detach(hero.belongings.backpack);
        }
    }




    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public long value() {
        return 24000 * quantity;
    }

    @Override
    public Item random() {
        quantity = (Dungeon.IntRange( 1 + Dungeon.escalatingDepth() * 2, 2 + Dungeon.escalatingDepth() * 2 ));
        return this;
    }

    @Override
    public String name() {
        return "Mind Vision Module";
    }

    @Override
    public String desc() {
        return "This module allows you to see something on the dungeon for a short time.";
    }
}

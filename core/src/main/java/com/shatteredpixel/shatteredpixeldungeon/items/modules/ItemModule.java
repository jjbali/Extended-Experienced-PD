package com.shatteredpixel.shatteredpixeldungeon.items.modules;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class ItemModule extends Module {

    {
        stackable = true;
        image = ItemSpriteSheet.MODULE_ITEM;

        defaultAction = AC_USE;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0x0000EE );
    }
    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
        if (action.equals(AC_USE)) {
            switch (Random.Int(3)) {
                case 0: default:
                    if (Random.Int(2) == 0) {
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.POTION).quantity(Random.Int(2, 5)), curUser.pos).sprite.drop();
                    } else {
                        detach( curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.EXPOTION).quantity(Random.Int(2, 5)), curUser.pos).sprite.drop();
                    }
                    break;
                case 1:
                    if (Random.Int(2) == 0) {
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.SCROLL).quantity(Random.Int(2, 5)), curUser.pos).sprite.drop();
                    } else {
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.EXSCROLL).quantity(Random.Int(2, 5)), curUser.pos).sprite.drop();
                    }
                    break;
                case 2:
                    if (Random.Int(2) == 0) {
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(), curUser.pos).sprite.drop();
                    } else {
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG).quantity(Random.Int(2, 3)), curUser.pos).sprite.drop();
                    }
                    break;
            }
        }
    }




    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public long value() {
        return 150 * quantity;
    }

    @Override
    public Item random() {
        quantity = (Dungeon.IntRange( 1 + Dungeon.escalatingDepth() * 2, 2 + Dungeon.escalatingDepth() * 2 ));
        return this;
    }

    @Override
    public String name() {
        return "Item Module";
    }

    @Override
    public String desc() {
        return "This module gives you a random item.";
    }
}

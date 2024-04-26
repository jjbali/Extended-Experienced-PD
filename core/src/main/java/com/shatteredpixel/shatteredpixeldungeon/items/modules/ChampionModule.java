package com.shatteredpixel.shatteredpixeldungeon.items.modules;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ChampionModule extends Module {

    {
        stackable = true;
        image = ItemSpriteSheet.MODULE_ITEM;

        defaultAction = AC_USE;
    }


    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0x000000 );
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
        if (action.equals(AC_USE)) {
            switch (Random.Int(7)) {
                case 1: default:
                    Buff.affect(hero, ChampionEnemy.Blazing.class);
                    break;
                case 2:
                    Buff.affect(hero, ChampionEnemy.Blessed.class);
                    break;
                case 3:
                    Buff.affect(hero, ChampionEnemy.Confused.class);
                    break;
                case 4:
                    Buff.affect(hero, ChampionEnemy.Growing.class);
                    break;
                case 5:
                    Buff.affect(hero, ChampionEnemy.Immunization.class);
                    break;
                case 6:
                    Buff.affect(hero, ChampionEnemy.Poisonous.class);
                    break;
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
        return 75000 * quantity;
    }

    @Override
    public Item random() {
        quantity = (Dungeon.IntRange( 1 + Dungeon.escalatingDepth() * 2, 2 + Dungeon.escalatingDepth() * 2 ));
        return this;
    }

    @Override
    public String name() {
        return "Championized Module";
    }

    @Override
    public String desc() {
        return "This module gives you a random champion title.";
    }
}

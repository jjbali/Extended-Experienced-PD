/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class RandomItemTicket extends Item {

    private static final String AC_DRINK	= "DRINK";

    {
        image = ItemSpriteSheet.RANDOM_ITEM_GIVER;

        defaultAction = AC_DRINK;

        stackable = true;
        unique = true;
    }

    @Override
    public ArrayList<String> actions( Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add( AC_DRINK );
        return actions;
    }

    @Override
    public void execute(final Hero hero, String action ) {

        super.execute(hero, action);

        if (action.equals(AC_DRINK)) {
            switch (Random.Int(3)) {
                case 0: default:
                    if (Random.Int(2) == 0) {
                        GameScene.flash(0x80FFFFFF);
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                    } else {
                        GameScene.flash(0x80FFFFFF);
                        detach( curUser.belongings.backpack );
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.EXPOTION), curUser.pos).sprite.drop();
                    }
                    break;
                case 1:
                    if (Random.Int(2) == 0) {
                        GameScene.flash(0x80FFFFFF);
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                    } else {
                        GameScene.flash(0x80FFFFFF);
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.EXSCROLL), curUser.pos).sprite.drop();
                    }
                    break;
                case 2:
                    if (Random.Int(2) == 0) {
                        GameScene.flash(0x80FFFFFF);
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(), curUser.pos).sprite.drop();
                    } else {
                        GameScene.flash(0x80FFFFFF);
                        detach(curUser.belongings.backpack);
                        updateQuickslot();
                        Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), curUser.pos).sprite.drop();
                    }
                    break;
            }
        }
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public long value() {
        return 100 * quantity;
    }

    @Override
    public String name() {
        return "Random Item Ticket";
    }

    @Override
    public String desc() {
        return "Gives you a random item such as:\n\n" +
                "_-_ Exotic Scroll\n_-_ Exotic Potion\n_-_ Potions\n_-_ Scrolls\n_-_ Treasure Bags" +
                "\n\nDoesn't give some items like weapons, I'll add them in the next update :)";
    }
}
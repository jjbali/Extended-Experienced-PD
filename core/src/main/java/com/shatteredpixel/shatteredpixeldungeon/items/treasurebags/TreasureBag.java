/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2020 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.treasurebags;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfElements;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public abstract class TreasureBag extends Item {
    private static final String AC_OPEN = "OPEN";
    {
        defaultAction = AC_OPEN;
        stackable = true;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions =  super.actions(hero);
        actions.add(AC_OPEN);
        return actions;
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
        return 1;
    }

    protected abstract ArrayList<Item> items();

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_OPEN)){
            detach(hero.belongings.backpack);
            ArrayList<Item> items = items();
            for (Item item: items){
                if (item != null) {
                    if (!item.doPickUp(hero, hero.pos, 0f)) {
                        Dungeon.level.drop(item, hero.pos).sprite.drop();
                    } else {
                        GLog.i(Messages.get(Hero.class, "you_now_have", item.name()));
                    }
                }
            }
            hero.spendAndNext(Actor.TICK);
        }
    }

    public Item randombag() {
        Item bag = null;
        switch (Random.Int(26)) {
            case 0: case 7: case 13:  case 18:
               bag = new AlchemyBag(); break;
            case 1: case 8:
               bag = new BiggerGambleBag(); break;
            case 2:
               bag = new IdealBag(); break;
            case 3: case 9: case 14:
               bag = new GambleBag(); break;
            case 4: case 10: case 15:  case 19:  case 22:  case 24:
               bag = new PotionBag(); break;
            case 5: case 11: case 16:  case 20: case 23:  case 25:
               bag = new ScrollBag(); break;
            case 6: case 12:  case 17:  case 21:
               bag = new SpellBag(); break;
        }
        return bag;
    }

}

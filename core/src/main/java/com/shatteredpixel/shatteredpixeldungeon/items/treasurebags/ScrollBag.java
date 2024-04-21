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
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Perks;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.BinaryItem;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfAntiMagic;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfDetermination;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfDivination;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfDread;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfForesight;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfMysticalEnergy;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPassage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPolymorph;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPrismaticImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPsionicBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrollBag extends TreasureBag {
    {
        image = ItemSpriteSheet.SCROLL_BAG;
    }

    @Override
    protected ArrayList<Item> items() {
        ArrayList<Item> items = new ArrayList<>();
        List<Class<? extends Item>> normal = Arrays.asList(
                //ScrollOfUpgrade.class,
                ScrollOfIdentify.class,
                ScrollOfRemoveCurse.class,
                ScrollOfMirrorImage.class,
                ScrollOfRecharging.class,
                ScrollOfTeleportation.class,
                ScrollOfLullaby.class,
                ScrollOfMagicMapping.class,
                ScrollOfRage.class,
                ScrollOfRetribution.class,
                ScrollOfTerror.class,
                ScrollOfTransmutation.class
        );
        List<Class<? extends Item>> exotic = Arrays.asList(
                ScrollOfDivination.class,
                ScrollOfEnchantment.class,
                ScrollOfAntiMagic.class,
                ScrollOfSirensSong.class,
                ScrollOfDetermination.class,
                ScrollOfDread.class,
                ScrollOfMysticalEnergy.class,
                ScrollOfForesight.class,
                ScrollOfPassage.class,
                ScrollOfPsionicBlast.class,
                ScrollOfPrismaticImage.class,
                ScrollOfPolymorph.class
        );
        int amount = Random.Int(2, 6);
        if (Dungeon.hero.perks.contains(Perks.Perk.MORE_BAG)) amount *= 3.5f;
        if (BinaryItem.streak_i >= 20) amount *= 10f;
        items.add(Reflection.newInstance(Random.element(normal)).quantity(amount));
        items.add(Reflection.newInstance(Random.element(exotic)).quantity(amount));
        return items;
    }

    @Override
    public long value() {
        return 1500 * quantity;
    }

}

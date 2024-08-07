/*
 *
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2024 Trashbox Bobylev
 *
 * Extended Experienced Pixel Dungeon
 * Copyright (C) 2023-2024 John Nollas
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
 *
 */

package com.shatteredpixel.shatteredpixeldungeon.items.fragments;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class RedFragment extends Fragment {
    {
        levelKnown = true;
        identify();
        image = ItemSpriteSheet.RED;
    }

    @Override
    public String name() {
        return "Red Fragment";
    }

    @Override
    public String desc() {
        return "A primary colored fragment, it can increase the chance of the weapons and armors being enchanted based on this level\n\nEnchantment Chance: +" + 100 * equipmentEnchantChance() + "%";
    }

    @Override
    protected long upgradeEnergyCost() {
        return 2 + 2 * level();
    }

    public static float equipmentEnchantChance(){
        return equipmentEnchantChance(fragmentLevel(RedFragment.class));
    }

    public static float equipmentEnchantChance( long level ){
        if (level == -1){
            return 0.01f;
        } else {
            return 0.02f*level;
        }
    }
}

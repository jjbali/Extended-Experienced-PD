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

public class VioletFragment extends Fragment {
    {
        levelKnown = true;
        identify();
        image = ItemSpriteSheet.VIOLET;
    }

    @Override
    public String name() {
        return "Violet Fragment";
    }

    @Override
    public String desc() {
        return "A secondary colored fragment, it can multiply the dropped gold based on this level\n\nGold Multiplier: " + goldMulti();
    }

    @Override
    protected long upgradeEnergyCost() {
        return 5 + 2 * level();
    }

    public static float goldMulti(){
        return goldMulti(fragmentLevel(VioletFragment.class));
    }

    public static float goldMulti( long level ){
        if (level == -1){
            return 0f;
        } else {
            return 1f + (0.002f * level);
        }
    }
}

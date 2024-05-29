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

public class MagentaFragment extends Fragment {
    {
        levelKnown = true;
        identify();
        image = ItemSpriteSheet.MAGENTA;
    }

    @Override
    public String name() {
        return "Magenta Fragment";
    }

    @Override
    public String desc() {
        return "A tertiary colored fragment, it can add more mobs based on this level\n\nMobs: " + mobsAdd();
    }

    @Override
    protected long upgradeEnergyCost() {
        return (long) (1 + 0.5f * level());
    }

    public static int mobsAdd(){
        return mobsAdd(fragmentLevel(MagentaFragment.class));
    }

    public static int mobsAdd( long level ){
        if (level == -1){
            return 0;
        } else {
            return (int) Math.min(20f, level/5f);
        }
    }
}

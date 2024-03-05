/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.levels.traps;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ShadowCaster;
import com.watabou.utils.Point;

import java.util.ArrayList;
import java.util.Collections;

public class MultiTrap extends Trap {

    private static final int DIST = 32;

    {
        color = GREY;
        shape = STARS;
    }

    @Override
    public Trap hide() {
        //cannot hide this trap
        return reveal();
    }

    @SuppressWarnings("NewApi")
    @Override
    public void activate() {
        boolean[] FOV = new boolean[Dungeon.level.length()];
        Point c = Dungeon.level.cellToPoint(pos);
        ShadowCaster.castShadow(c.x, c.y, FOV, Dungeon.level.losBlocking, DIST);

        int sX = Math.max(0, c.x - DIST);
        int eX = Math.min(Dungeon.level.width() - 1, c.x + DIST);

        int sY = Math.max(0, c.y - DIST);
        int eY = Math.min(Dungeon.level.height() - 1, c.y + DIST);

        ArrayList<Trap> disarmCandidates = new ArrayList<>();

        for (int y = sY; y <= eY; y++) {
            int curr = y * Dungeon.level.width() + sX;
            for (int x = sX; x <= eX; x++) {

                if (true) {

                    Trap t = Dungeon.level.traps.get(curr);
                    if (t != null && t.active) {
                        disarmCandidates.add(t);
                    }

                }
                curr++;
            }
        }

        //uses at most five traps
        int iii = 10;
        while (disarmCandidates.size() > 5 && iii > 0) {
            try {
                Trap t = disarmCandidates.get(0).getClass().newInstance();
                disarmCandidates.remove(0);
                t.pos = pos;
                if (!(t instanceof MultiTrap)) {
                    t.activate();
                }
                Collections.shuffle(disarmCandidates);
            } catch ( IllegalAccessException | InstantiationException e) {

            }
            iii--;
        }
    }
}

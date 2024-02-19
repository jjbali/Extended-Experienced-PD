/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.secret;

import com.shatteredpixel.shatteredpixeldungeon.items.spells.AquaBlast;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.BeaconOfReturning;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.CurseInfusion;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicBridge;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.PhaseShift;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ReclaimTrap;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Recycle;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Spell;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.SummonElemental;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.TelekineticGrab;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.WildEnergy;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.HashMap;

public class SecretSpellRoom extends SecretRoom {

    @Override
    public int minWidth() {
        return Math.max(7, super.minWidth());
    }

    @Override
    public int minHeight() {
        return Math.max(7, super.minHeight());
    }

    private static HashMap<Class<? extends Spell>, Float> spellChances = new HashMap<>();
    static {
        spellChances.put(ReclaimTrap.class, 3f);
        spellChances.put(MagicBridge.class, 3f);
        spellChances.put(AquaBlast.class, 2f);
        spellChances.put(SummonElemental.class, 2f);
        spellChances.put(PhaseShift.class, 2f);
        spellChances.put(WildEnergy.class, 2f);
        spellChances.put(BeaconOfReturning.class, 1f);
        spellChances.put(CurseInfusion.class, 1f);
        spellChances.put(Recycle.class, 1f);
        spellChances.put(TelekineticGrab.class, 1f);
    }

    public void paint(Level level) {

        Painter.fill(level, this, Terrain.WALL);
        Painter.fill(level, this, 1, Terrain.BOOKSHELF);

        Painter.fillEllipse(level, this, 2, Terrain.EMPTY_SP);

        Door entrance = entrance();
        if (entrance.x == left || entrance.x == right) {
            Painter.drawInside(level, this, entrance, (width() - 3) / 2, Terrain.EMPTY_SP);
        } else {
            Painter.drawInside(level, this, entrance, (height() - 3) / 2, Terrain.EMPTY_SP);
        }
        entrance.set(Door.Type.HIDDEN);

        int n = Random.IntRange(2, 3);
        HashMap<Class<? extends Spell>, Float> chances = new HashMap<>(spellChances);
        for (int i = 0; i < n; i++) {
            int pos;
            do {
                pos = level.pointToCell(random());
            } while (level.map[pos] != Terrain.EMPTY_SP || level.heaps.get(pos) != null);

            Class<? extends Spell> spellCls = Random.chances(chances);
            chances.put(spellCls, 0f);

            int amount = 1 + Random.Int(3);
            for (int j = 0; j < amount; j++) {
                level.drop(Reflection.newInstance(spellCls), pos);
            }
        }
    }
}
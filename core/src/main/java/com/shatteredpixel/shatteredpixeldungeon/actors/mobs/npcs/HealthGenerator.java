/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2019-2024 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2024 Trashbox Bobylev
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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ExpGenSprite;
import com.watabou.utils.PathFinder;

public class HealthGenerator extends Mob {
    {
        spriteClass = ExpGenSprite.HealthGenSprite.class;
        state = PASSIVE;
        alignment = Alignment.ALLY;
        state = WANDERING;
    }

    public long level;

    public void set(long lvl){
        level = lvl;
        HT = HP = (long) ((25 + Dungeon.escalatingDepth() * 2L) * (1.2f * level));
    }

    @Override
    protected boolean getCloser(int target) {
        return false;
    }

    @Override
    protected boolean getFurther(int target) {
        return false;
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        if (cause instanceof Char && !(cause instanceof Hero)) {
            Buff.affect((Char) cause, Adrenaline.class, 50f);
            Buff.affect((Char) cause, Barkskin.class).set((int) (enemy.HT*5), 0);
            Buff.affect((Char) cause, Bless.class, 60f);
            Buff.affect((Char) cause, Levitation.class, 60f);
            Buff.affect((Char) cause, ArcaneArmor.class).set((int) (enemy.HT*5), 0);
        }
        Dungeon.level.drop(new com.shatteredpixel.shatteredpixeldungeon.items.HealthGenerator().upgrade(level - 1), pos).sprite.drop();
    }

    @Override
    public boolean reset() {
        return true;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return false;
    }

    @Override
    protected void onAdd() {
        super.onAdd();
        spend(3f);
    }

    @Override
    protected boolean act() {
        spend(1f);
        boolean mobs = false;
        for (Mob mob : Dungeon.level.mobs) {
            if (mob.alignment == Alignment.ENEMY) {
                PathFinder.Path path = PathFinder.find(mob.pos, pos, Dungeon.level.passable);
                if (path != null) mobs = true;
            }
        }
        if (mobs) {
            int tolerance = (int) (4 * level);
            if (Dungeon.hero.HP < Dungeon.hero.HT) Dungeon.hero.HP += tolerance;


            if (Dungeon.hero.fieldOfView[pos]) sprite.showStatusWithIcon(CharSprite.POSITIVE, Integer.toString(tolerance), FloatingText.HEALING);
            else Dungeon.hero.sprite.showStatusWithIcon(CharSprite.POSITIVE, Integer.toString(tolerance), FloatingText.HEALING);
        }
        return super.act();
    }
}

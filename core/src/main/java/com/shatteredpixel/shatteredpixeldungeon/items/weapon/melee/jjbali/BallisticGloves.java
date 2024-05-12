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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.jjbali;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Bbat;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class BallisticGloves extends JjbaliWeapon {

	{
		image = ItemSpriteSheet.GLOVES_ENERGY;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 1f;
	}

    @Override
    public long max(long lvl) {
        return  7*(tier+1) +    //70
                lvl*(tier+8);   //+12
    }

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

    @Override
    public long proc(Char attacker, Char defender, long damage) {
        return super.proc(attacker, defender, damage);
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        if (target == null) {
            return;
        }

        Ballistica aim = new Ballistica(hero.pos, target, Ballistica.WONT_STOP);
        Char enemy = Actor.findChar(target);

        float chargeUse = abilityChargeUse(hero, enemy);

        if (chargeUse <= 0) {
            GLog.w(Messages.get(this, "ability_no_charge"));
            return;
        }

        int maxDist = 30;
        int dist = Math.min(aim.dist, maxDist);

        ConeAOE cone = new ConeAOE(aim,
                dist,
                20,
                Ballistica.STOP_SOLID | Ballistica.STOP_TARGET);

        for (Ballistica ray : cone.outerRays){
            ((MagicMissile)curUser.sprite.parent.recycle( MagicMissile.class )).reset(
                    MagicMissile.RAINBOW_CONE,
                    hero.sprite,
                    ray.path.get(ray.dist),
                    null
            );
        }

        hero.sprite.zap(target);
        MagicMissile.boltFromChar(curUser.sprite.parent,
                MagicMissile.RAINBOW_CONE,
                curUser.sprite,
                aim.path.get(dist / 2),
                new Callback() {
                    @Override
                    public void call() {
                        beforeAbilityUsed(hero, enemy);
                        for (int cell: cone.cells){
                            Char ch = Actor.findChar( cell );
                            if (ch != null && !(ch instanceof Bbat)) {
                                Sample.INSTANCE.play(Assets.Sounds.HIT_MAGIC, 2f, 0.65f);
                                for (int i = 0; i < 3; i++) {
                                    int D = Random.Int(12);
                                    switch (D) {
                                        case 0: default: Buff.prolong(ch, Blindness.class, Blindness.DURATION);break;
                                        case 1: Buff.affect(ch, Burning.class).reignite(ch, 10f);break;
                                        case 2: Buff.affect(ch, Corrosion.class).set(10f, 6L);break;
                                        case 3: Buff.prolong(ch, Cripple.class, Cripple.DURATION);break;
                                        case 4: Buff.prolong(ch, Chill.class, Chill.DURATION);break;
                                        case 5: Buff.prolong(ch, Frost.class, Frost.DURATION);break;
                                        case 6: Buff.prolong(ch, Hex.class, Hex.DURATION);break;
                                        case 7: Buff.prolong(ch, Paralysis.class, Paralysis.DURATION);break;
                                        case 8: Buff.affect(ch, Poison.class).set(10f);break;
                                        case 9: Buff.prolong(ch, Vulnerable.class, Vulnerable.DURATION);break;
                                        case 10: Buff.prolong(ch, Weakness.class, Weakness.DURATION);break;
                                        case 11: Buff.prolong(ch, Vertigo.class, Vertigo.DURATION);break;
                                    }
                                }
                            }
                        }

                        Invisibility.dispel();
                        hero.spendAndNext(hero.attackDelay());
                        afterAbilityUsed(hero);
                    }
                });
        Sample.INSTANCE.play( Assets.Sounds.ZAP );

    }
}

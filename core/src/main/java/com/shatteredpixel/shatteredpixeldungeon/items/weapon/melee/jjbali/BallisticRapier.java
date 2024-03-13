/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.jjbali;

import static com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger.sneakAbility;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.DamageEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Foresight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LifeLink;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ToxicImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Bbat;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Longsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Sickle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.traits.PreparationAllowed;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class BallisticRapier extends JjbaliWeapon {


    {
        image = ItemSpriteSheet.RAPIER_ENERGY;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1f;
    }
    @Override
    public long max(long lvl) {
        return  15L*(tier+1) +    //70
                lvl*(tier+20);   //+12
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
    public long damageRoll(Char owner) {
        if (owner instanceof Hero) {
            Hero hero = (Hero) owner;
            Char enemy = hero.enemy();
            if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                //deals 67% toward max to max on surprise, instead of min to max.
                long diff = max() - min();
                long damage = augment.damageFactor(Random.NormalLongRange(
                        min() + Math.round(diff * 0.67f),
                        max()));
                int exStr = hero.STR() - STRReq();
                if (exStr > 0) {
                    damage += Dungeon.IntRange(0, exStr);
                }
                return damage;
            }
        }
        return super.damageRoll(owner);
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

        int maxDist = 10;
        int dist = Math.min(aim.dist, maxDist);

        ConeAOE cone = new ConeAOE(aim,
                dist,
                360,
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
                                for (int i = 0; i < 10; i++) {
                                    Buff.affect(ch, Longsword.HolyExpEffect.class).stacks++;
                                }
                                Buff.affect(ch, LifeLink.class, 200f);
                                Buff.affect(ch, Paralysis.class, 5f);
                                Buff.affect(ch, Roots.class, 5f);
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

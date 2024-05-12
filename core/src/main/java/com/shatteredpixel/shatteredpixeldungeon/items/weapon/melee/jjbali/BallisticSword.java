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
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Statue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Bbat;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Longsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StatueSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.AttackIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class BallisticSword extends JjbaliWeapon {

	{
		image = ItemSpriteSheet.WORN_SHORTSWORD_ENERGY;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 1f;
	}

    @Override
    public long max(long lvl) {
        return  6*(tier+1) +    //70
                lvl*(tier+6);   //+12
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

        int maxDist = 5 + Math.round(chargeUse);
        int dist = Math.min(aim.dist, maxDist);

        ConeAOE cone = new ConeAOE(aim,
                dist,
                90,
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
                                Buff.affect(ch, Weakness.class, 10 + chargeUse/3);
                                Buff.affect(ch, Blindness.class, 3 + chargeUse/3);
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

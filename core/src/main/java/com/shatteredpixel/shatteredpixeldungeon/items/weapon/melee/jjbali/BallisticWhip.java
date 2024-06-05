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
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Bbat;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.effects.TargetedCell;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.EarthParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.FlameParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.RainbowParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SparkParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.ConeAOE;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class BallisticWhip extends JjbaliWeapon {

	{
		image = ItemSpriteSheet.WHIP_ENERGY;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.3f;

		DLY = 1.5f;
	}

	@Override
	public long proc(Char attacker, Char defender, long damage) {
		Ballistica path = new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID);
		for (int cell: path.subPath(1, path.dist)){
			//CellEmitter.center(cell).burst(EarthParticle.FACTORY, 10);
			CellEmitter.center(cell).burst(RainbowParticle.BURST, 5);
			if (Actor.findChar(cell) != null){
				attacker.sprite.parent.add(new TargetedCell(cell, 0xFF7F00));
				//CellEmitter.center(cell).burst(EarthParticle.FACTORY, 15);
				CellEmitter.center(cell).burst(RainbowParticle.BURST, 10);
				this.hitSound(0.85f);
				ArrayList<Char> affected = new ArrayList<>();

				for (int n : PathFinder.NEIGHBOURS9) {
					int c = cell + n;
					if (c >= 0 && c < Dungeon.level.length()) {
						if (Dungeon.level.heroFOV[c]) {
							CellEmitter.get(c).burst(SparkParticle.FACTORY, 2);
						}

						Char ch = Actor.findChar(c);
						if (ch != null && ch.alignment != attacker.alignment) {
							affected.add(ch);
						}
					}
				}

				for (Char ch : affected){

					//if they have already been killed by another bomb
					if(!ch.isAlive()){
						continue;
					}

					long dmg = super.proc(attacker, ch, Math.round(damage*1.0d));

					//those not at the center of the blast take less damage
					if (ch.pos != cell){
						dmg = Math.round(dmg*0.5d);
					}

					if (dmg > 0) {
						ch.damage(dmg, this);
					}
				}
			}
		}

		return -1;
	}

	@Override
	public boolean canReach(Char owner, int target) {
		return new Ballistica(owner.pos, target, Ballistica.STOP_TARGET | Ballistica.STOP_SOLID).collisionPos == target;
	}

	@Override
	public long min(long lvl) {
		return tier()+lvl/2;
	}

	@Override
	public long max(long lvl) {
		return 3L *(tier()) +    //16 base, down from 24
				lvl*(tier()-1)/2;     //+4 per level, down from +5
	}

	public static class WhipReachBooster extends Buff {};

	@Override
	public int reachFactor(Char owner) {
		if (owner.buff(WhipReachBooster.class) != null)
			return Char.INFINITE_ACCURACY;
		else
			return super.reachFactor(owner);
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
				45,
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
								Buff.affect(ch, Burning.class).reignite(ch, 10f);
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

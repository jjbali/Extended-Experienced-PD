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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.SacrificialFire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AllyBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.shatteredpixel.shatteredpixeldungeon.effects.FloatingText;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GhoulSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Ghoul extends Mob {
	
	{
		spriteClass = GhoulSprite.class;
		
		HP = HT = 45;
		defenseSkill = 20;
		
		EXP = 5;
		maxLvl = 20;
		
		SLEEPING = new Sleeping();
		WANDERING = new Wandering();
		state = SLEEPING;

		loot = Gold.class;
		lootChance = 0.2f;
		
		properties.add(Property.UNDEAD);
        switch (Dungeon.cycle){
            case 1:
                HP = HT = 430;
                defenseSkill = 61;
                EXP = 30;
                break;
            case 2:
                HP = HT = 7843;
                defenseSkill = 267;
                EXP = 273;
                break;
            case 3:
                HP = HT = 100000;
                defenseSkill = 600;
                EXP = 2800;
                break;
            case 4:
                HP = HT = 30000000;
                defenseSkill = 4800;
                EXP = 200000;
                break;
        }
	}

	@Override
	public long damageRoll() {
        switch (Dungeon.cycle) {
            case 1: return Random.NormalIntRange(64, 79);
            case 2: return Random.NormalIntRange(276, 400);
            case 3: return Random.NormalIntRange(1500, 1821);
            case 4: return Random.NormalIntRange(23000, 71000);
        }
		return Random.NormalIntRange( 16, 22 );
	}

	@Override
	public int attackSkill( Char target ) {
        switch (Dungeon.cycle){
            case 1: return 95;
            case 2: return 338;
            case 3: return 880;
            case 4: return 5500;
        }
		return 24;
	}

	@Override
	public int cycledDrRoll() {
        switch (Dungeon.cycle){
            case 1: return Random.NormalIntRange(22, 43);
            case 2: return Random.NormalIntRange(100, 211);
            case 3: return Random.NormalIntRange(621, 1111);
            case 4: return Random.NormalIntRange(18000, 46000);
        }
		return Random.NormalIntRange(0, 4);
	}

	@Override
	public float spawningWeight() {
		return 0.5f;
	}

	private int timesDowned = 0;
	protected int partnerID = -1;

	private static final String PARTNER_ID = "partner_id";
	private static final String TIMES_DOWNED = "times_downed";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( PARTNER_ID, partnerID );
		bundle.put( TIMES_DOWNED, timesDowned );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		partnerID = bundle.getInt( PARTNER_ID );
		timesDowned = bundle.getInt( TIMES_DOWNED );
	}
	
	@Override
	protected boolean act() {
		//create a child
		if (partnerID == -1){
			
			ArrayList<Integer> candidates = new ArrayList<>();
			
			int[] neighbours = {pos + 1, pos - 1, pos + Dungeon.level.width(), pos - Dungeon.level.width()};
			for (int n : neighbours) {
				if (Dungeon.level.passable[n]
						&& Actor.findChar( n ) == null
						&& (!Char.hasProp(this, Property.LARGE) || Dungeon.level.openSpace[n])) {
					candidates.add( n );
				}
			}
			
			if (!candidates.isEmpty()){
				Ghoul child = new Ghoul();
				child.partnerID = this.id();
				this.partnerID = child.id();
				if (state != SLEEPING) {
					child.state = child.WANDERING;
				}
				
				child.pos = Random.element( candidates );

				GameScene.add( child );
				Dungeon.level.occupyCell(child);
				
				if (sprite.visible) {
					Actor.add( new Pushing( child, pos, child.pos ) );
				}

				for (Buff b : buffs(ChampionEnemy.class)){
					Buff.affect( child, b.getClass());
				}

			}
			
		}
		return super.act();
	}

	private boolean beingLifeLinked = false;

	@Override
	public void die(Object cause) {
		if (cause != Chasm.class && cause != GhoulLifeLink.class && !Dungeon.level.pit[pos]){
			Ghoul nearby = GhoulLifeLink.searchForHost(this);
			if (nearby != null){
				beingLifeLinked = true;
				timesDowned++;
				Actor.remove(this);
				Dungeon.level.mobs.remove( this );
				Buff.append(nearby, GhoulLifeLink.class).set(timesDowned*5, this);
				((GhoulSprite)sprite).crumple();
				return;
			}
		}

		super.die(cause);
	}

	@Override
	public boolean isAlive() {
		return super.isAlive() || beingLifeLinked;
	}

	@Override
	public boolean isActive() {
		return !beingLifeLinked && isAlive();
	}

	@Override
	protected synchronized void onRemove() {
		if (beingLifeLinked) {
			for (Buff buff : buffs()) {
				if (buff instanceof SacrificialFire.Marked){
					//don't remove and postpone so marked stays on
					Buff.prolong(this, SacrificialFire.Marked.class, timesDowned*5);
				} else if (buff instanceof AllyBuff
						|| buff instanceof ChampionEnemy
						|| buff instanceof DwarfKing.KingDamager) {
					//don't remove
				} else {
					buff.detach();
				}
			}
		} else {
			super.onRemove();
		}
	}

	private class Sleeping extends Mob.Sleeping {
		@Override
		public boolean act( boolean enemyInFOV, boolean justAlerted ) {
			Ghoul partner = (Ghoul) Actor.findById( partnerID );
			if (partner != null && partner.state != partner.SLEEPING){
				state = WANDERING;
				target = partner.pos;
				return true;
			} else {
				return super.act( enemyInFOV, justAlerted );
			}
		}
	}
	
	private class Wandering extends Mob.Wandering {
		
		@Override
		protected boolean continueWandering() {
			enemySeen = false;
			
			Ghoul partner = (Ghoul) Actor.findById( partnerID );
			if (partner != null && (partner.state != partner.WANDERING || Dungeon.level.distance( pos,  partner.target) > 1)){
				target = partner.pos;
				int oldPos = pos;
				if (getCloser( target )){
					spend( 1 / speed() );
					return moveSprite( oldPos, pos );
				} else {
					spend( TICK );
					return true;
				}
			} else {
				return super.continueWandering();
			}
		}
	}

	public static class GhoulLifeLink extends Buff{

		private Ghoul ghoul;
		private int turnsToRevive;

		@Override
		public boolean act() {
			if (target.alignment != ghoul.alignment){
				detach();
				return true;
			}

			if (target.fieldOfView == null){
				target.fieldOfView = new boolean[Dungeon.level.length()];
				Dungeon.level.updateFieldOfView( target, target.fieldOfView );
			}

			if (!target.fieldOfView[ghoul.pos] && Dungeon.level.distance(ghoul.pos, target.pos) >= 4){
				detach();
				return true;
			}

			if (Dungeon.level.pit[ghoul.pos]){
				super.detach();
				ghoul.beingLifeLinked = false;
				ghoul.die(this);
				return true;
			}

			//have to delay this manually here are a downed ghouls can't be directly frozen otherwise
			if (target.buff(Challenge.DuelParticipant.class) == null) {
				turnsToRevive--;
			}
			if (turnsToRevive <= 0){
				if (Actor.findChar( ghoul.pos ) != null) {
					ArrayList<Integer> candidates = new ArrayList<>();
					for (int n : PathFinder.NEIGHBOURS8) {
						int cell = ghoul.pos + n;
						if (Dungeon.level.passable[cell]
								&& Actor.findChar( cell ) == null
								&& (!Char.hasProp(ghoul, Property.LARGE) || Dungeon.level.openSpace[cell])) {
							candidates.add( cell );
						}
					}
					if (candidates.size() > 0) {
						int newPos = Random.element( candidates );
						Actor.add( new Pushing( ghoul, ghoul.pos, newPos ) );
						ghoul.pos = newPos;

					} else {
						spend(TICK);
						return true;
					}
				}
				ghoul.HP = Math.round(ghoul.HT/10f);
				ghoul.beingLifeLinked = false;
				Actor.add(ghoul);
				ghoul.timeToNow();
				Dungeon.level.mobs.add(ghoul);
				Dungeon.level.occupyCell( ghoul );
				ghoul.sprite.idle();
				ghoul.sprite.showStatusWithIcon(CharSprite.POSITIVE, Integer.toString(Math.round(ghoul.HT/10f)), FloatingText.HEALING);
				super.detach();
				return true;
			}

			spend(TICK);
			return true;
		}

		public void updateVisibility(){
			if (ghoul != null && ghoul.sprite != null){
				ghoul.sprite.visible = Dungeon.level.heroFOV[ghoul.pos];
			}
		}

		public void set(int turns, Ghoul ghoul){
			this.ghoul = ghoul;
			turnsToRevive = turns;
		}

		@Override
		public void fx(boolean on) {
			if (on && ghoul != null && ghoul.sprite == null){
				GameScene.addSprite(ghoul);
				((GhoulSprite)ghoul.sprite).crumple();
			}
		}

		@Override
		public void detach() {
			super.detach();
			Ghoul newHost = searchForHost(ghoul);
			if (newHost != null){
				attachTo(newHost);
				timeToNow();
			} else {
				ghoul.beingLifeLinked = false;
				ghoul.die(this);
			}
		}

		private static final String GHOUL = "ghoul";
		private static final String LEFT  = "left";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(GHOUL, ghoul);
			bundle.put(LEFT, turnsToRevive);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			ghoul = (Ghoul) bundle.get(GHOUL);
			ghoul.beingLifeLinked = true;
			turnsToRevive = bundle.getInt(LEFT);
		}

		public static Ghoul searchForHost(Ghoul dieing){

			for (Char ch : Actor.chars()){
				//don't count hero ally ghouls or duel frozen ghouls
				if (ch != dieing && ch instanceof Ghoul
						&& ch.alignment == dieing.alignment
						&& ch.buff(Challenge.SpectatorFreeze.class) == null){
					if (ch.fieldOfView == null){
						ch.fieldOfView = new boolean[Dungeon.level.length()];
						Dungeon.level.updateFieldOfView( ch, ch.fieldOfView );
					}
					if (ch.fieldOfView[dieing.pos] || Dungeon.level.distance(ch.pos, dieing.pos) < 4){
						return (Ghoul) ch;
					}
				}
			}
			return null;
		}
	}
}

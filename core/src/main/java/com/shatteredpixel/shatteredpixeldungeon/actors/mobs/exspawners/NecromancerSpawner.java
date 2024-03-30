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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.exspawners;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AscensionChallenge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Dread;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Sleep;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Necromancer;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Rat;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SpawnerSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class NecromancerSpawner extends Mob {

	{
		spriteClass = SpawnerSprite.NecromancerSpawner.class;

		HP = HT = 40;
		defenseSkill = 0;

		EXP = 15;
		maxLvl = 4;

		state = PASSIVE;

		loot = ScrollOfIdentify.class;
		lootChance = 1f;

		properties.add(Property.IMMOVABLE);
		properties.add(Property.MINIBOSS);
        switch (Dungeon.cycle){
            case 1:
                HP = HT = 70;
                defenseSkill = 0;
                EXP = 30;
                break;
            case 2:
                HP = HT = 190;
                defenseSkill = 0;
                EXP = 1000;
                break;
            case 3:
                HP = HT = 5700;
                defenseSkill = 0;
                EXP = 25000;
                break;
            case 4:
                HP = HT = 485000;
                defenseSkill = 0;
                EXP = 90000;
                break;
        }
	}

	@Override
	public int cycledDrRoll() {
        switch (Dungeon.cycle){
            case 1: return Random.NormalIntRange(48, 73);
            case 2: return Random.NormalIntRange(185, 365);
            case 3: return Random.NormalIntRange(1800, 3120);
            case 4: return Random.NormalIntRange(130000, 200000);
        }
		return Random.NormalIntRange(0, 12);
	}

	@Override
	public void beckon(int cell) {
		//do nothing
	}

	@Override
	public boolean reset() {
		return true;
	}

	private float spawnCooldown = 0;

	@Override
	protected boolean act() {

		if (Dungeon.hero.buff(AscensionChallenge.class) != null && spawnCooldown > 20){
			spawnCooldown = 20;
		}

		spawnCooldown--;
		if (spawnCooldown <= 0){

			//we don't want spawners to store multiple ripper demons
			if (spawnCooldown < -20){
				spawnCooldown = -20;
			}

			ArrayList<Integer> candidates = new ArrayList<>();
			for (int n : PathFinder.NEIGHBOURS8) {
				if (Dungeon.level.passable[pos+n] && Actor.findChar( pos+n ) == null) {
					candidates.add( pos+n );
				}
			}

			if (!candidates.isEmpty()) {
				Necromancer spawn = new Necromancer();

				spawn.pos = Random.element( candidates );
				spawn.state = spawn.HUNTING;

				GameScene.add( spawn, 1 );
				Dungeon.level.occupyCell(spawn);

				if (sprite.visible) {
					Actor.add(new Pushing(spawn, pos, spawn.pos));
				}

				spawnCooldown += 30;
			}
		}
		alerted = false;
		return super.act();
	}

	@Override
	public void damage(long dmg, Object src) {
		if (dmg >= 50 + Dungeon.cycle * 300L && Dungeon.cycle < 2){
			//halves the damage taken
			dmg /= 2;
		}
		spawnCooldown -= dmg;
		super.damage(dmg, src);
	}

	@Override
	public void die(Object cause) {
		GLog.h(Messages.get(this, "on_death"));
		super.die(cause);
	}

	public static final String SPAWN_COOLDOWN = "spawn_cooldown";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SPAWN_COOLDOWN, spawnCooldown);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		spawnCooldown = bundle.getFloat(SPAWN_COOLDOWN);
	}

	{
		immunities.add( Paralysis.class );
		immunities.add( Amok.class );
		immunities.add( Sleep.class );
		immunities.add( Dread.class );
		immunities.add( Terror.class );
		immunities.add( Vertigo.class );
	}
}

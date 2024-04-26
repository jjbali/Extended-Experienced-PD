/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2020 Trashbox Bobylev
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

package com.shatteredpixel.shatteredpixeldungeon.levels;


import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ChampionEnemy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Overload;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RageShield;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ToxicImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.ExpGenerator;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.RandomItemTicket;
import com.shatteredpixel.shatteredpixeldungeon.items.SpyGlass;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToThirdArena;
import com.shatteredpixel.shatteredpixeldungeon.items.Torch;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.ChaosFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.MiscBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.PotionBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.ScrollBag;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Longsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.jjbali.BallisticSword;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.SecondArenaShopLevel;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.SummoningTrap;
import com.shatteredpixel.shatteredpixeldungeon.levels.traps.Trap;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTileSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.Bundle;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

import java.util.ArrayList;

public class SecondArenaLevel extends Level {
	
	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
	}

	@Override
	public void playLevelMusic() {
		Music.INSTANCE.play(Assets.Music.ARENA, true);
	}

	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;

	private static final int ROOM_LEFT		= WIDTH / 2 - 3;
	private static final int ROOM_RIGHT		= WIDTH / 2 + 1;
	private static final int ROOM_TOP		= HEIGHT / 2 - 2;
	private static final int ROOM_BOTTOM	= HEIGHT / 2 + 2;
	
	private int arenaDoor;
	private boolean enteredArena = false;
	private boolean keyDropped = false;
	private SecondArenaShopLevel shop;
	
	@Override
	public String tilesTex() {
		return Assets.Environment.TILES_HALLS;
	}
	
	@Override
	public String waterTex() {
		return Assets.Environment.WATER_HALLS;
	}
	
	private static final String DOOR	= "door";
	private static final String ENTERED	= "entered";
	private static final String DROPPED	= "droppped";
	private static final String SHOP = "shop";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( DOOR, arenaDoor );
		bundle.put( ENTERED, enteredArena );
		bundle.put( DROPPED, keyDropped );
		bundle.put( SHOP, shop);
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		arenaDoor = bundle.getInt( DOOR );
		enteredArena = bundle.getBoolean( ENTERED );
		keyDropped = bundle.getBoolean( DROPPED );
		shop = (SecondArenaShopLevel) bundle.get(SHOP);
	}
	
	@Override
	protected boolean build() {
		
		setSize(WIDTH, HEIGHT);

		Rect space = new Rect();

		space.set(
				Random.IntRange(2, 6),
				Random.IntRange(2, 6),
				Random.IntRange(width-6, width-2),
				Random.IntRange(height-6, height-2)
		);

		Painter.fillEllipse( this, space, Terrain.EMPTY );

		exit = space.left + space.width()/2 + (space.top - 1) * width();
		
		map[exit] = Terrain.LOCKED_EXIT;

		Painter.fill( this, ROOM_LEFT - 1, ROOM_TOP - 1,
			ROOM_RIGHT - ROOM_LEFT + 3, ROOM_BOTTOM - ROOM_TOP + 3, Terrain.WALL );
		Painter.fill( this, ROOM_LEFT, ROOM_TOP + 1,
			ROOM_RIGHT - ROOM_LEFT + 1, ROOM_BOTTOM - ROOM_TOP, Terrain.EMPTY );

		Painter.fill( this, ROOM_LEFT, ROOM_TOP,
			ROOM_RIGHT - ROOM_LEFT + 1, 1, Terrain.EMPTY_DECO );
		shop = new SecondArenaShopLevel();
		shop.set(ROOM_LEFT-1, ROOM_TOP-1, ROOM_RIGHT+1, ROOM_BOTTOM+1);
		
		arenaDoor = Random.Int( ROOM_LEFT, ROOM_RIGHT ) + (ROOM_BOTTOM + 1) * width();
		map[arenaDoor] = Terrain.DOOR;
		
		int entrance = Random.Int( ROOM_LEFT + 1, ROOM_RIGHT - 1 ) +
			Random.Int( ROOM_TOP + 1, ROOM_BOTTOM - 1 ) * width();
		transitions.add(new LevelTransition(this, entrance, LevelTransition.Type.REGULAR_ENTRANCE));
		map[entrance] = Terrain.PEDESTAL;

		{
			itemsToSpawn = new ArrayList<>();
			itemsToSpawn.add(new Torch().quantity(Dungeon.IntRange(5, 100)));
			itemsToSpawn.add(new ScrollOfEnchantment().quantity(4));
			itemsToSpawn.add(new ArcaneCatalyst().quantity(Dungeon.IntRange(5, 100)));
			// TODO OTHER BAGS
			itemsToSpawn.add(new PotionBag().quantity(Dungeon.IntRange(5, 100)));
			itemsToSpawn.add(new ScrollBag().quantity(Dungeon.IntRange(5, 100)));
			itemsToSpawn.add(new MiscBag().quantity(Dungeon.IntRange(5, 100)));
			itemsToSpawn.add(new RandomItemTicket().quantity(Dungeon.IntRange(5, 100)));
			itemsToSpawn.add(new SpyGlass().quantity(Dungeon.IntRange(5, 100)));
			itemsToSpawn.add(new TicketToThirdArena());
			// TODO OTHER 6 ITEMS
			itemsToSpawn.add(new BallisticSword().upgrade(Dungeon.IntRange(1, 10)));
			itemsToSpawn.add(new ChaosFishingRod().upgrade(Dungeon.IntRange(1, 10)));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.EXPOTION ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.EXPOTION ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.POTION ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.POTION ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.EXSCROLL ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.EXSCROLL ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.SCROLL ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.SCROLL ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.WEP_T1 ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.WEP_T2 ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.WEP_T3 ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.WEP_T4 ));
			itemsToSpawn.add( Generator.randomUsingDefaults( Generator.Category.WEP_T5 ));


			Point itemPlacement = new Point(cellToPoint(arenaDoor));
			if (itemPlacement.y == ROOM_TOP-1){
				itemPlacement.y++;
			} else if (itemPlacement.y == ROOM_BOTTOM+1) {
				itemPlacement.y--;
			} else if (itemPlacement.x == ROOM_LEFT-1){
				itemPlacement.x++;
			} else {
				itemPlacement.x--;
			}

			for (Item item : itemsToSpawn) {

				if (itemPlacement.x == ROOM_LEFT && itemPlacement.y != ROOM_TOP){
					itemPlacement.y--;
				} else if (itemPlacement.y == ROOM_TOP && itemPlacement.x != ROOM_RIGHT){
					itemPlacement.x++;
				} else if (itemPlacement.x == ROOM_RIGHT && itemPlacement.y != ROOM_BOTTOM){
					itemPlacement.y++;
				} else {
					itemPlacement.x--;
				}

				int cell = pointToCell(itemPlacement);

				if (heaps.get( cell ) != null) {
					do {
						cell = pointToCell(new Point( Random.IntRange( ROOM_TOP, ROOM_RIGHT ),
								Random.IntRange( ROOM_TOP, ROOM_BOTTOM )));
					} while (heaps.get( cell ) != null || findMob( cell ) != null);
				}

				drop( item, cell ).type = Heap.Type.FOR_ARENA_SALE;
			}
		}
		
		boolean[] patch = Patch.generate( width, height, 0.30f, 6, true );
		for (int i=0; i < length(); i++) {
			if (map[i] == Terrain.EMPTY && patch[i]) {
				map[i] = Terrain.WATER;
			}
		}

		for (int i=0; i < length(); i++) {
			if (map[i] == Terrain.EMPTY && Random.Int( 6 ) == 0) {
				map[i] = Terrain.INACTIVE_TRAP;
				Trap t = new SummoningTrap().reveal();
				t.active = false;
				setTrap(t, i);
			}
		}
		
		for (int i=width() + 1; i < length() - width(); i++) {
			if (map[i] == Terrain.EMPTY) {
				int n = 0;
				if (map[i+1] == Terrain.WALL) {
					n++;
				}
				if (map[i-1] == Terrain.WALL) {
					n++;
				}
				if (map[i+width()] == Terrain.WALL) {
					n++;
				}
				if (map[i-width()] == Terrain.WALL) {
					n++;
				}
				if (Random.Int( 8 ) <= n) {
					map[i] = Terrain.EMPTY_DECO;
				}
			}
		}
		
		for (int i=0; i < length() - width(); i++) {
			if (map[i] == Terrain.WALL
					&& DungeonTileSheet.floorTile(map[i + width()])
					&& Random.Int( 3 ) == 0) {
				map[i] = Terrain.WALL_DECO;
			}
		}

		
		return true;
	}
	
	@Override
	protected void createMobs() {
	}

	public static class SecondArenaCounter extends CounterBuff {}
	
	@Override
	public Respawner respawner() {
        return new SecondArenaRespawner();
    }

    public static class SecondArenaBuff extends Buff {

    }
	
	@Override
	protected void createItems() {
		Item item = Bones.get();
		if (item != null) {
			int pos;
			do {
				pos = Random.IntRange( ROOM_LEFT, ROOM_RIGHT ) + Random.IntRange( ROOM_TOP + 1, ROOM_BOTTOM ) * width();
			} while (pos == entrance);
			drop( item, pos ).setHauntedIfCursed().type = Heap.Type.REMAINS;
		}
	}
	
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.GRASS:
				return Messages.get(CityLevel.class, "grass_name");
			case Terrain.HIGH_GRASS:
				return Messages.get(CityLevel.class, "high_grass_name");
			case Terrain.WATER:
				return Messages.get(CityLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc( int tile ) {
		switch (tile) {
			case Terrain.ENTRANCE:
				return Messages.get(HallsLevel.class, "entrance_desc");
			case Terrain.EXIT:
				return Messages.get(HallsLevel.class, "exit_desc");
			case Terrain.HIGH_GRASS:
				return Messages.get(HallsLevel.class, "high_grass_desc");
			case Terrain.WALL_DECO:
				return Messages.get(HallsLevel.class, "wall_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(HallsLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}

	@Override
	public Group addVisuals() {
		super.addVisuals();
		CityLevel.addCityVisuals(this, visuals);
		return visuals;
	}

	public static class SecondArenaRespawner extends Respawner {

		{
			actPriority = BUFF_PRIO; //as if it were a buff.
		}

		@Override
		protected boolean act() {
			float count = 0;

			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])){
				if (mob.alignment == Char.Alignment.ENEMY && !mob.properties().contains(Char.Property.MINIBOSS)) {
					count += mob.spawningWeight();
				}
			}

			SecondArenaCounter counter = Dungeon.hero.buff(SecondArenaCounter.class);
			float timerBasis;
			timerBasis = 15f;


			if (count < 100) {

				Mob mob = Dungeon.level.createMob();
				mob.state = mob.WANDERING;
				mob.pos = Dungeon.level.randomRespawnCell( mob );
				mob.HP = mob.HT *= 2;
				if (Dungeon.hero.isAlive() && mob.pos != -1 && Dungeon.level.distance(Dungeon.hero.pos, mob.pos) >= 4) {
					GameScene.add( mob );
					mob.beckon( Dungeon.hero.pos );
					Buff.affect(mob, SecondArenaBuff.class);
					if (counter != null){
						counter.countUp(Actor.TICK);
						int power = (int) counter.count();
						timerBasis = 15f + power / 2f;

						if (power >= 0){
							for (int i = 0; i < 5; i++){
								Buff.affect(mob, Longsword.HolyExpEffect.class).stacks++;
							}
						}
						if (power >= 100){
							Buff.affect(mob, Stamina.class, power * 3);
							mob.aggro(Dungeon.hero);
						}
						if (power >= 300){
							Buff.affect(mob, ElixirOfAquaticRejuvenation.AquaHealing.class)
									.set(power*5);
						}
						if (power >= 700){
							Buff.affect(mob, Overload.class, 200f);
						}
						if (power >= 1500){
							Buff.affect(mob, BlobImmunity.class, power);
						}
						if (power >= 2850){
							Class<?extends ChampionEnemy> buffCls;
							switch (Random.Int(6)){
								case 0: default:    buffCls = ChampionEnemy.Blazing.class;      break;
								case 1:             buffCls = ChampionEnemy.Projecting.class;   break;
								case 2:             buffCls = ChampionEnemy.AntiMagic.class;    break;
								case 3:             buffCls = ChampionEnemy.Giant.class;        break;
								case 4:             buffCls = ChampionEnemy.Blessed.class;      break;
								case 5:             buffCls = ChampionEnemy.Growing.class;      break;
							}
							Buff.affect(mob, buffCls);
							mob.HP = mob.HT *= 3;
						}
						if (power >= 3500){
							Buff.affect(mob, Levitation.class, 200f);
							Buff.affect(mob, BlobImmunity.class, BlobImmunity.DURATION);
						}
						if (power >= 7000){
							Buff.affect(mob, MagicalSight.class, MagicalSight.DURATION);
							mob.HP = mob.HT *= 10;
							if (power == 7000) {
								Badges.validatePowerReached();
							}
						}
					}
				}
			}
			spend(Dungeon.level.respawnCooldown() / timerBasis);
			return true;
		}
	}
}

/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2020 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AdrenalineSurge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArtifactRecharge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Awareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Enduring;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Foresight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Godspeed;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LifeLink;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalCircle;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Momentum;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Overload;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PhysicalEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PrismaticGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RageShield;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RevealedArea;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ScrollEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ThunderImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ToxicImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WandEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomBuffGiver extends Item {
    {
        image = ItemSpriteSheet.BEACON;
        defaultAction = AC_THROW;
        identify();
        stackable = true;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0xFFFFFF );
    }

    @Override
    protected void onThrow(int cell) {
        if (!Dungeon.level.passable[cell]){
            super.onThrow(cell);
        } else {
            Char ch = Actor.findChar(cell);
            if (ch == Dungeon.hero || ch instanceof Mob) {
                int random = Random.Int(1, 30);
                switch (random) {
                    default: case 1:
                        Buff.affect(ch, Invisibility.class, Invisibility.DURATION * 0.66f + (super.level() * 1.5f));
                        GLog.p("> You are now invisible.");
                        break;
                    case 2:
                        Buff.affect(ch, Haste.class, Haste.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are now faster than ever.");
                        break;
                    case 3:
                        Buff.affect(ch, FireImbue.class).set(FireImbue.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are now imbued with fire.");
                        break;
                    case 4:
                        Buff.affect(ch, FrostImbue.class, FrostImbue.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are now imbued with frost.");
                        break;
                    case 5:
                        Buff.affect(ch, MindVision.class, MindVision.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You can now see through walls.");
                        break;
                    case 6:
                        Buff.affect(ch, Bless.class, Bless.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are now blessed.");
                        break;
                    case 7:
                        Buff.affect(ch, AdrenalineSurge.class).reset(2, 500  + (super.level() * 1.5f));
                        GLog.p("> You are given a surge of humanism.");
                        break;
                    case 8:
                        Buff.affect(ch, Adrenaline.class, Adrenaline.DURATION * 0.66f   + (super.level() * 1.5f));
                        GLog.p("> You are given a surge of physical power.");
                        break;
                    case 9:
                        Buff.affect(ch, BlobImmunity.class, BlobImmunity.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are now immune to negative effects.");
                        break;
                    case 10:
                        Buff.affect(ch, Recharging.class, Recharging.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You can now recharge faster.");
                        break;
                    case 11:
                        Buff.affect(ch, AnkhInvulnerability.class, AnkhInvulnerability.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are invulnerable.");
                        break;
                    case 12:
                        Buff.affect(ch, Awareness.class, Awareness.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are now aware.");
                        break;
                    case 13:
                        Buff.affect(ch, EnhancedRings.class, Random.Int(2, 10) * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> Your rings are enhanced.");
                        break;
                    case 14:
                        Buff.affect(ch, Foresight.class, Foresight.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You can reveal 8x8 area.");
                        break;
                    case 15:
                        Buff.affect(ch, Levitation.class, Levitation.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are now levitated.");
                        break;
                    case 16:
                        Buff.affect(ch, Light.class, Light.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You can see through darkness.");
                        break;
                    case 17:
                        Buff.affect(ch, LifeLink.class, Random.Int(2, 10) * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> Your life has been linked to share some damage.");
                        break;
                    case 18:
                        Buff.affect(ch, MagicalSight.class, MagicalSight.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You can see through walls.");
                        break;
                    case 19:
                        Buff.affect(ch, MagicImmune.class, MagicImmune.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are now immune to magical effects.");
                        break;
                    case 20:
                        Buff.affect(ch, Overload.class, Overload.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> Step out and cry into the hell.");
                        break;
                    case 21:
                        Buff.affect(ch, PrismaticGuard.class).set((int) (15 * level()));
                        GLog.p("> You have a guard.");
                        break;
                    case 22:
                        Buff.affect(ch, RageShield.class).set(15 * level());
                        GLog.p("> Your rage has became a shield.");
                        break;
                    case 23:
                        Buff.affect(ch, ScrollEmpower.class).reset(1);
                        GLog.p("> Your scrolls has been empowered.");
                        break;
                    case 24:
                        Buff.affect(ch, Stamina.class, Stamina.DURATION * 0.66f  + (super.level() * 1.5f));
                        GLog.p("> You are faster that others.");
                        break;
                    case 25:
                        Buff.affect(ch, ThunderImbue.class).set(15 * level());
                        GLog.p("> You are now imbued with thunder.");
                        break;
                    case 26:
                        Buff.affect(ch, WandEmpower.class).set((int) (15 * level()), (int) (3 * level()));
                        GLog.p("> Your wands has been empowered.");
                        break;
                    case 27:
                        Buff.affect(ch, WellFed.class).reset();
                        GLog.p("> Well fed.");
                        break;
                    case 28:
                        Buff.affect(ch, Barkskin.class).set((int) (5 * level()), (int) (20 * level()));
                        GLog.p("> You have a barkskin");
                        break;
                    case 29:
                        Buff.affect(ch, Barrier.class).setShield((int) (10 * level()));
                        GLog.p("> You have a barrier.");
                        break;
                }
            }
        }
    }

    @Override
    public long level() {
        return super.level();
    }

    @Override
    public long visiblyUpgraded() {
        return super.level();
    }

    @Override
    public long value() {
        return 100 * quantity;
    }
}

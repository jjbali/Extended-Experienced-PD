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

package com.shatteredpixel.shatteredpixeldungeon.actors.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ArcaneArmor;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Corrosion;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.CounterBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.DamageEnhance;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Foresight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Frost;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hex;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HoldFast;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ToxicImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vulnerable;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Perks {
    public enum Perk {
        SUCKER_PUNCH,
        DIRECTIVE,
        FOLLOW_UP_STRIKE,
        HOLD_FAST{
            public String desc() {
                return Messages.get(Perks.class, name() + ".desc", HoldFast.minArmor(), HoldFast.armor());
            }
        },
        PROTEIN_INFUSION,
        FISHING_PRO,
        WAND_PRO,
        IRON_WILL,
        MYSTICAL_MEAL,
        ADDITIONAL_MONEY,
        BETTER_BARTERING,
        POTIONS,
        MORE_BAG,
        NO_ONESHOTS,
        COLLECT_EVERYTHING,
        RAT_SUMMONS,
        GRASS_HEALING,
        POTION_COLLECTOR,
        SCROLL_COLLECTOR,
        TREASUREBAG_COLLECTOR,
        INTERFERENCE,
        INBOUND_COLLABORATION,
        MORE_COINS,
        FRIENDLY_BEES,
        PROTECTIVE_BARRIER,
        MIND_GAZER,
        OOZE;

        public String desc() {
            return Messages.get(Perks.class, name() + ".desc");
        }

        @Override
        public String toString() {
            return Messages.titleCase(Messages.get(Perks.class, name() + ".name"));
        }
    }

    public static abstract class Cooldown extends FlavourBuff {
        public static <T extends Cooldown> void affectHero(Class<T> cls) {
            if(cls == Cooldown.class) return;
            T buff = Buff.affect(Dungeon.hero, cls);
            buff.spend( buff.duration() );
        }
        public abstract float duration();
        public float iconFadePercent() { return Math.max(0, visualcooldown() / duration()); }
        public String toString() { return Messages.get(this, "name"); }
        public String desc() { return Messages.get(this, "desc", dispTurns(visualcooldown())); }
    }
    public static class SuckerPunchTracker extends Buff{}
    public static class DirectiveTracker extends FlavourBuff {}
    public static class FollowupStrikeTracker extends Buff{};
    public static class DirectiveMovingTracker extends CounterBuff {
        public int duration() { return 3;}
        public float iconFadePercent() { return Math.max(0, 1f - ((count()) / (duration()))); }
        public String toString() { return Messages.get(this, "name"); }
        public String desc() { return Messages.get(this, "desc", dispTurns(duration() - (count()))); }
        public int icon() { return BuffIndicator.MOMENTUM; }
        public void tintIcon(Image icon) { icon.hardlight(0.15f, 0.7f, 0.5f); }
    }

    public static long onAttackProc(Hero hero, Char enemy, long damage){
        if (hero.perks.contains(Perk.SUCKER_PUNCH)
                && enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)
                && enemy.buff(SuckerPunchTracker.class) == null){
            damage += hero.damageRoll()/4;
            Buff.affect(enemy, SuckerPunchTracker.class);
        }
        if (hero.perks.contains(Perk.DIRECTIVE)
                && enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)
                && enemy.buff(DirectiveTracker.class) == null){
            Actor.addDelayed(new Actor() {
                @Override
                protected boolean act() {
                    Buff.count(hero, DirectiveMovingTracker.class, -1);
                    diactivate();
                    return true;
                }
            }, 0);

            Buff.affect(enemy, DirectiveTracker.class);
        }
        if (hero.perks.contains(Perk.FOLLOW_UP_STRIKE)) {
            if (hero.belongings.weapon instanceof MissileWeapon) {
                Buff.affect(enemy, FollowupStrikeTracker.class);
            } else if (enemy.buff(FollowupStrikeTracker.class) != null){
                damage += hero.damageRoll()/4;
                if (!(enemy instanceof Mob) || !((Mob) enemy).surprisedBy(hero)){
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG, 0.75f, 1.2f);
                }
                enemy.buff(FollowupStrikeTracker.class).detach();
            }
        }
        if (hero.perks.contains(Perk.INTERFERENCE) && Random.Int(3) == 0) {
            int D = Random.Int(12);
            int F = Random.Int(12);
                switch (D) {
                case 0: default: Buff.prolong(enemy, Blindness.class, 4f * Random.Int(2, 10));break;
                case 1: Buff.affect(enemy, Burning.class).reignite(enemy, 4f * Random.Int(2, 10));break;
                case 2: Buff.affect(enemy, Corrosion.class).set(4f * Random.Int(2, 10), 6 * Random.Int(2, 10)/4);break;
                case 3: Buff.prolong(enemy, Cripple.class, 4f * Random.Int(2, 10));break;
                case 4: Buff.prolong(enemy, Chill.class, 4f * Random.Int(2, 10));break;
                case 5: Buff.prolong(enemy, Frost.class, 4f * Random.Int(2, 10));break;
                case 6: Buff.prolong(enemy, Hex.class, 4f * Random.Int(2, 10));break;
                case 7: Buff.prolong(enemy, Paralysis.class, 4f * Random.Int(2, 10));break;
                case 8: Buff.affect(enemy, Poison.class).set(4f * Random.Int(2, 10));break;
                case 9: Buff.prolong(enemy, Vulnerable.class, 4f * Random.Int(2, 10));break;
                case 10: Buff.prolong(enemy, Weakness.class, 4f * Random.Int(2, 10));break;
                case 11: Buff.prolong(enemy, Vertigo.class, 4f * Random.Int(2, 10));break;
                }
                switch (F) {
                    case 0: Buff.affect(hero, BlobImmunity.class, 3f * Random.Int(2, 10));break;
                    case 1: Buff.affect(hero, Bless.class, 3f * Random.Int(2, 10));break;
                    case 2: Buff.affect(hero, Haste.class, 4f * Random.Int(2, 10));break;
                    case 3: Buff.affect(hero, Healing.class).setHeal(30 * Random.Int(2, 10), 0.1f, 0);break;
                    case 4: Buff.affect(hero, Adrenaline.class, 3f * Random.Int(2, 10));break;
                    case 5: Buff.affect(hero, FireImbue.class).set(3f * Random.Int(2, 10));break;
                    case 6: Buff.affect(hero, FrostImbue.class, 3f * Random.Int(2, 10));break;
                    case 7: Buff.affect(hero, ToxicImbue.class).set(3f * Random.Int(2, 10));break;
                    case 8: Buff.affect(hero, Foresight.class, 3f * Random.Int(2, 10));break;
                    case 9: Buff.affect(hero, ArcaneArmor.class).set(Dungeon.hero.lvl, 3 * Random.Int(3, 10));break;
                    case 10: Buff.affect(hero, Recharging.class, 2f * Random.Int(2, 10));break;
                    case 11: Buff.affect(hero, DamageEnhance.class).set(Dungeon.hero.lvl);break;
                }
        }
        if (hero.perks.contains(Perk.INBOUND_COLLABORATION)) {
            damage *= Random.Int(2, Dungeon.hero.lvl);
        }
        if (hero.perks.contains(Perk.PROTECTIVE_BARRIER)) {
            damage *= Random.Int(2, 5);
            Buff.affect(hero, Barrier.class).setShield((20L * Dungeon.hero.lvl));
        }
        if (hero.perks.contains(Perk.MIND_GAZER)) {
            damage *= Random.Int(2, 5);
            Buff.affect(hero, MindVision.class, 2f);
        }
        if (hero.perks.contains(Perk.OOZE)) {
            Buff.affect(enemy, Ooze.class).set(10f);
        }
        return damage;
    }

    public static int nextPerkLevel(){
        int num = 50;
        for (int i = 0; i < Dungeon.hero.perks.size(); i++){
            num += 50 + i;
        }
        return num;
    }

    public static void debugEarnPerk(String string){
        Perk perk = Perk.valueOf(string);
        Dungeon.hero.perks.add(perk);
        GLog.p(Messages.get(Perks.class, "perk_obtain", perk.toString()));
        if (Dungeon.hero.sprite != null)
            Dungeon.hero.sprite.emitter().burst(Speck.factory(Speck.STAR), 20);
    }

    @SuppressWarnings("SuspiciousIndentation")
    public static void earnPerk(Hero hero){
        if (hero.perks.size() < Perk.values().length && hero.lvl == nextPerkLevel()){
            Perk perk;
            do {
                perk = Random.element(Perk.values());
            } while (hero.perks.contains(perk));
            hero.perks.add(perk);
            GLog.p(Messages.get(Perks.class, "perk_obtain", perk.toString()));
            if (hero.sprite != null)
                hero.sprite.emitter().burst(Speck.factory(Speck.STAR), 20);
        }
    }

    public static void storeInBundle(Bundle bundle, ArrayList<Perk> perks) {
        ArrayList<String> conductIds = new ArrayList<>();
        for (Perk conduct: perks){
            conductIds.add(conduct.name());
        }
        bundle.put("perks", conductIds.toArray(new String[0]));
    }

    public static void restoreFromBundle(Bundle bundle, ArrayList<Perk> perks) {
        perks.clear();
        if (bundle.getStringArray("perks") != null) {
            String[] conductIds = bundle.getStringArray("perks");
            for (String conduct : conductIds) {
                perks.add(Perk.valueOf(conduct));
            }
        }
    }
}

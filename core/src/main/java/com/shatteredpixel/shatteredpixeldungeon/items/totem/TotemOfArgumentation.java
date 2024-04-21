package com.shatteredpixel.shatteredpixeldungeon.items.totem;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Adrenaline;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AdrenalineSurge;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Awareness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FireImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Foresight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FrostImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Levitation;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LifeLink;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Light;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicImmune;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MagicalSight;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.MindVision;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Overload;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.PrismaticGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RageShield;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Recharging;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ScrollEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ThunderImbue;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WandEmpower;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.WellFed;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfUltimatum;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPolymorph;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TotemOfArgumentation extends Totem {

    {
        image = ItemSpriteSheet.ARGUMENTATIVE;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = new ArrayList<>();
        actions.remove( AC_THROW );
        actions.add( AC_DROP );
        return actions;
    }

    @Override
    public boolean doPickUp(Hero hero) {
        Buff.affect(hero, ArgumentationBuff.class);
        return doPickUp( hero, hero.pos );
    }

    @Override
    public void doDrop( Hero hero ) {
        Buff.detach(hero, ArgumentationBuff.class);
        super.doDrop(hero);
    }

    @Override
    public void onHeroGainExp( float levelPercent, Hero hero ){
        int random = Random.Int(1, 30);
        switch (random) {
            default: case 1:
                Buff.affect(hero, Invisibility.class, Invisibility.DURATION * 0.40f + (hero.lvl * 0.1f));
                break;
            case 2:
                Buff.affect(hero, Haste.class, Haste.DURATION * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 3:
                Buff.affect(hero, FireImbue.class).set(FireImbue.DURATION * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 4:
                Buff.affect(hero, FrostImbue.class, FrostImbue.DURATION * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 5:
                Buff.affect(hero, MindVision.class, MindVision.DURATION * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 6:
                Buff.affect(hero, Bless.class, Bless.DURATION * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 7:
                Buff.affect(hero, AdrenalineSurge.class).reset(2, 20 * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 8:
                Buff.affect(hero, Adrenaline.class, Adrenaline.DURATION * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 9:
                Buff.affect(hero, BlobImmunity.class, 2f * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 10:
                Buff.affect(hero, Recharging.class, 5f * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 11:
                Buff.affect(hero, AnkhInvulnerability.class, AnkhInvulnerability.DURATION * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 12:
                Buff.affect(hero, Awareness.class, Awareness.DURATION * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 13:
                Buff.affect(hero, EnhancedRings.class, Random.Int(2, 10) * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 14:
                Buff.affect(hero, Foresight.class, 20f * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 15:
                Buff.affect(hero, Levitation.class, Levitation.DURATION * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 16:
                Buff.affect(hero, Light.class, 10f * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 17:
                Buff.affect(hero, LifeLink.class, Random.Int(2, 10) * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 18:
                Buff.affect(hero, MagicalSight.class, 10f* 0.40f + (hero.lvl* 1.5f));
                break;
            case 19:
                Buff.affect(hero, MagicImmune.class, 10f * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 20:
                Buff.affect(hero, Overload.class, 10f * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 21:
                Buff.affect(hero, PrismaticGuard.class).set(15 + hero.lvl);
                break;
            case 22:
                Buff.affect(hero, RageShield.class).set(15 + hero.lvl);
                break;
            case 23:
                Buff.affect(hero, ScrollEmpower.class).reset(1);
                break;
            case 24:
                Buff.affect(hero, Stamina.class, 5f * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 25:
                Buff.affect(hero, ThunderImbue.class).set(15 * (0.40f + (hero.lvl * 0.01f)));
                break;
            case 26:
                Buff.affect(hero, WandEmpower.class).set(15 + hero.lvl, 3 + hero.lvl);
                break;
            case 27:
                Buff.affect(hero, WellFed.class).reset();
                break;
            case 28:
                Buff.affect(hero, Barkskin.class).set((int) (5 * (0.40f + (hero.lvl * 0.01f))), 20 + hero.lvl);
                break;
            case 29:
                Buff.affect(hero, Barrier.class).setShield(10 + hero.lvl);
                break;
        }
    }

    @Override
    public void onDetach(){}

    @Override
    public String name() {
        return "Totem of Argumentation";
    }
    @Override
    public String desc() {
        return "This glamorous totem gives you a boost and buffs when gaining exp, and also decreases the max experience per level by 10%";
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
            inputs =  new Class[]{Totem.class, ScrollOfTransmutation.class, ElixirOfUltimatum.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 50;

            output = TotemOfArgumentation.class;
            outQuantity = 1;
        }
    }

    public static class ArgumentationBuff extends FlavourBuff {
        {
            type = buffType.POSITIVE;
        }

        @Override
        public int icon() {
            return BuffIndicator.NONE;
        }
    }
}

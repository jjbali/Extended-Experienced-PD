package com.shatteredpixel.shatteredpixeldungeon.items.totem;

import static com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth.tryForBonusDrop;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SacrificialParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.OverloadBeacon;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfPolymorph;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ArcaneCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfUnstable;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TotemOfUndefined extends Totem {

    {
        image = ItemSpriteSheet.UNDEFINED;
    }

    public static final String AC_GENERATE		= "GENERATE";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = new ArrayList<>();
        actions.remove( AC_THROW );
        actions.add( AC_DROP );
        actions.add( AC_GENERATE );
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_GENERATE) && hero.buff(UndefinedCooldown.class) == null) {
            Buff.affect(hero, UndefinedCooldown.class).set(100);
            Sample.INSTANCE.play( Assets.Sounds.BEACON );

            int rolls = 5 * hero.lvl;
            ArrayList<Item> bonus = tryForBonusDrop(rolls);
            if (!bonus.isEmpty()) {
                for (Item b : bonus) Dungeon.level.drop(b, hero.pos).sprite.drop();
                RingOfWealth.showFlareForBonusDrop(hero.sprite);
            }
            for (int i : PathFinder.NEIGHBOURS9){
                CellEmitter.center(hero.pos + i).burst(SacrificialParticle.FACTORY, 50);
            }
            hero.spendAndNext(1f);
        } else if (action.equals(AC_GENERATE) && hero.buff(UndefinedCooldown.class) != null) {
            GLog.w("This item is unavailable.");
            GameScene.flash(0xFFFF0000);
        }

    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing(1f);
    }

    @Override
    public boolean doPickUp(Hero hero) {
        return doPickUp( hero, hero.pos );
    }

    @Override
    public void doDrop( Hero hero ) {
        super.doDrop(hero);
    }

    @Override
    public void onDetach(){}

    @Override
    public String name() {
        return "Totem of Undefined";
    }
    @Override
    public String desc() {
        return "This green and vine-like totem generates what the hero needs. While on your inventory, this item can generate a loot and scales with your level.";
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
            inputs = new Class[]{Totem.class, ArcaneCatalyst.class, TotemOfFortune.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 100;

            output = TotemOfUndefined.class;
            outQuantity = 1;
        }
    }

    public static class UndefinedCooldown extends Buff {

        int duration = 0;
        int maxDuration = 0;

        {
            type = buffType.NEUTRAL;
            announced = false;
        }

        public void set(int time) {
            maxDuration = time;
            duration = maxDuration;
        }

        public void hit(int time) {
            duration -= time;
            if (duration <= 0) detach();
        }

        @Override
        public boolean act() {
            duration--;
            if (duration <= 0) {
                detach();
            }
            spend(TICK);
            return true;
        }

        @Override
        public int icon() {
            return BuffIndicator.TIME;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0x384222);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (maxDuration - duration) / maxDuration);
        }

        @Override
        public String iconTextDisplay() {
            return Integer.toString(duration);
        }

        @Override
        public String name() {
            return "Cooldown: Totem of Undefined";
        }

        @Override
        public String desc() {
            return "You recently generated a loot. You may not use it right now.\n\nTurns remaining: " + duration;
        }

        private static final String MAX_DURATION = "maxDuration";
        private static final String DURATION = "duration";
        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put( MAX_DURATION, maxDuration );
            bundle.put( DURATION, duration );
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            maxDuration = bundle.getInt( MAX_DURATION );
            duration = bundle.getInt( DURATION );
        }
    }
}

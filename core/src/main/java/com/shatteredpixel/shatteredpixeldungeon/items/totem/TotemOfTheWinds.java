package com.shatteredpixel.shatteredpixeldungeon.items.totem;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfStamina;
import com.shatteredpixel.shatteredpixeldungeon.plants.Firebloom;
import com.shatteredpixel.shatteredpixeldungeon.plants.Swiftthistle;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

import java.util.ArrayList;

public class TotemOfTheWinds extends Totem {

    {
        image = ItemSpriteSheet.THE_WINDS;
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = new ArrayList<>();
        actions.remove( AC_THROW );
        actions.add( AC_DROP );
        return actions;
    }

    @Override
    public boolean doPickUp(Hero hero) {
        Buff.affect(hero, HasteBuff.class);
        return doPickUp( hero, hero.pos );
    }

    @Override
    public void doDrop( Hero hero ) {
        Buff.detach(hero, HasteBuff.class);
        super.doDrop(hero);
    }

    @Override
    public void onDetach(){}

    @Override
    public String name() {
        return "Totem of The Winds";
    }
    @Override
    public String desc() {
        return "This airy green totem invokes great swiftness to its bearer. While this totem is in your inventory, you automatically gain the Haste status, but enemy attacks have a chance to knock you far back every attack they land.";
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
            inputs =  new Class[]{Totem.class, PotionOfHaste.class, PotionOfStamina.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 50;

            output = TotemOfTheWinds.class;
            outQuantity = 1;
        }
    }

    public static class HasteBuff extends FlavourBuff {
        {
            type = buffType.POSITIVE;
        }

        @Override
        public int icon() {
            return BuffIndicator.NONE;
        }
    }
}

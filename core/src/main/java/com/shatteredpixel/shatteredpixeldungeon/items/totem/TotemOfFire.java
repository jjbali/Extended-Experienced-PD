package com.shatteredpixel.shatteredpixeldungeon.items.totem;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfDragonsBreath;
import com.shatteredpixel.shatteredpixeldungeon.plants.Firebloom;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

import java.util.ArrayList;

public class TotemOfFire extends Totem {

    {
        image = ItemSpriteSheet.FIRE;
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
        Buff.affect(hero, FireBuff.class);
        return doPickUp( hero, hero.pos );
    }

    @Override
    public void doDrop( Hero hero ) {
        Buff.detach(hero, FireBuff.class);
        super.doDrop(hero);
    }

    @Override
    public void onDetach(){}

    @Override
    public String name() {
        return "Totem of Fire";
    }
    @Override
    public String desc() {
        return "This bright red totem crackles with blazing heat. While this totem is in your inventory, you become immune to fire damage, resist damage from fire elementals, but you become weak to the cold. Ice elementals will deal more damage, and being chilled or frozen will hurt you.";
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {
        {
            inputs =  new Class[]{Totem.class, PotionOfLiquidFlame.class, PotionOfDragonsBreath.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 50;

            output = TotemOfFire.class;
            outQuantity = 1;
        }
    }

    public static class FireBuff extends FlavourBuff {
        {
            type = buffType.POSITIVE;
        }

        @Override
        public int icon() {
            return BuffIndicator.NONE;
        }
    }
}

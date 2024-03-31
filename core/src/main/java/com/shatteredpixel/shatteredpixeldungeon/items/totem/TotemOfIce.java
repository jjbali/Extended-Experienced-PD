package com.shatteredpixel.shatteredpixeldungeon.items.totem;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.FlavourBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;

import java.util.ArrayList;

public class TotemOfIce extends Totem {

    {
        image = ItemSpriteSheet.ICE;
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
        Buff.affect(hero, IceBuff.class);
        return doPickUp( hero, hero.pos );
    }

    @Override
    public void doDrop( Hero hero ) {
        Buff.detach(hero, IceBuff.class);
        super.doDrop(hero);
    }

    @Override
    public void onDetach(){}

    @Override
    public String name() {
        return "Totem of Ice";
    }
    @Override
    public String desc() {
        return "This icy blue totem shivers with freezing cold. While this totem is in your inventory, you become immune to being chilled or frozen, resist damage from ice elementals, but you become weak to the heat. Fire elementals will deal more damage, and being set on fire will deal even more damage.";
    }

    public static class IceBuff extends FlavourBuff {
        {
            type = buffType.POSITIVE;
        }

        @Override
        public int icon() {
            return BuffIndicator.NONE;
        }
    }
}

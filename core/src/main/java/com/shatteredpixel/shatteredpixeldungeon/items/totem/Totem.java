package com.shatteredpixel.shatteredpixeldungeon.items.totem;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;

import java.util.ArrayList;

public class Totem extends Item {

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = new ArrayList<>();
        actions.remove( AC_THROW );
        return actions;
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
    public boolean isUpgradable() {
        return true;
    }

    @Override
    public void onHeroGainExp( float levelPercent, Hero hero ){
        //do nothing by default
    }
}

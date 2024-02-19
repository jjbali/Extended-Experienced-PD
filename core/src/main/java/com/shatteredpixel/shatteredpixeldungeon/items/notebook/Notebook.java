package com.shatteredpixel.shatteredpixeldungeon.items.notebook;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class Notebook extends Item {
    {
        image = ItemSpriteSheet.NOTEBOOK;
        stackable = false;
    }

    private static final String AC_WRITE = "WRITE";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = new ArrayList<>();
        actions.remove( AC_DROP );
        actions.remove( AC_THROW );
        actions.add( AC_WRITE );
        return actions;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals(AC_WRITE)) {
            NotebookPage nps = new NotebookPage();
            if (!nps.collect()){
                Dungeon.level.drop(nps, hero.pos);
            } else {
                GLog.p( "You picked up [" + nps.name() + "]" );
            }
        }
    }

    @Override
    public String name() {
        return "Notebook";
    }

    @Override
    public String desc() {
        return "This notebook produces pages that are writable. Page notes can be dissipated to remove all the contents of the page.";
    }
}

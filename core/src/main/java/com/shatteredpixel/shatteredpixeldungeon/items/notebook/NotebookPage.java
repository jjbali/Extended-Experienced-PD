package com.shatteredpixel.shatteredpixeldungeon.items.notebook;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.ExtendedDictionaryJournal;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class NotebookPage extends Item {

    {
        image = ItemSpriteSheet.NOTEBOOK_PAGE;
        stackable = false;
    }
    private static final String AC_DISSIPATE = "DISSIPATE";


    private String id = id(6);
    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = new ArrayList<>();
        actions.add( AC_DROP );
        actions.remove( AC_THROW );
        actions.add( AC_DISSIPATE );
        return actions;
    }

    @Override
    protected boolean notePersists() {
        return true;
    }

    @Override
    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals(AC_DISSIPATE) && !this.notes.isEmpty()) {
            this.notes = "";
            GLog.p( "Note for [" + this.name() + "] has been dissipated." );
        } else if (action.equals(AC_DISSIPATE) && this.notes.isEmpty()) {
            GLog.w("The page has no content.");
        }


    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public final String id(int length) {
        String glitchy = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++){
            builder.append(glitchy.charAt(Random.Int(glitchy.length())));
        }
        glitchy = String.valueOf(builder);
        return glitchy;
    }

    @Override
    public String name() {
        if (!this.customName.isEmpty()) {
            return this.customName;
        } else {
            return "Page: " + id;
        }
    }


    @Override
    public String desc() {
        String renamed = "\nYou can also rename this page.";
        if (!this.customName.isEmpty()) {
            renamed = "";
        }
        if (!this.notes.isEmpty()) {
            return "_This page has some contents._" + renamed + "\nRefer to the text below:\n\nContents:\n" + notes;
        } else {
            return "_This page has no contents._" + renamed + "\n\nYou may add something that you want here. Click the white scroll to edit your notes";
        }
    }

    private static final String identfication		= "identification";
    @Override
    public void storeInBundle( Bundle bundle ) {
        bundle.put( identfication, id );
    }
    @Override
    public void restoreFromBundle( Bundle bundle ) {
        id = bundle.getString( identfication );
    }
}

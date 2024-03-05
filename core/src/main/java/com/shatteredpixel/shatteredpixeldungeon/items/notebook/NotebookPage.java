package com.shatteredpixel.shatteredpixeldungeon.items.notebook;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.ExtendedDictionaryJournal;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.zrp200.scrollofdebug.ScrollOfDebug;

import java.util.ArrayList;

public class NotebookPage extends Item {

    {
        image = ItemSpriteSheet.NOTEBOOK_PAGE;
        stackable = false;
    }
    private static final String AC_DISSIPATE = "DISSIPATE";
    private static final String AC_SHOW_NOTES = "SHOW_NOTES";


    private String id = id(6);
    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = new ArrayList<>();
        actions.add( AC_DROP );
        actions.remove( AC_THROW );
        actions.add( AC_DISSIPATE );
        actions.add( AC_SHOW_NOTES );
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
        } else if (action.equals(AC_SHOW_NOTES)) {
            GameScene.show(new NoteWindow("Notes for [" + name() + "]:\n\n"+ notes));
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
        String instructions = "\n\nRefer to the highlighting system:\n- Red: use #hash#\n- Blue: use --double dashes--\n- Green: use @arroba@\n- Yellow: use _underscore_\nYou can also @use@ this to _highlight_ the name of %%every item%%";
        if (!this.customName.isEmpty()) {
            renamed = "";
        }
        if (!this.notes.isEmpty()) {
            return "_This page has some contents._" + renamed;
        } else {
            return "_This page has no contents._" + renamed + instructions + "\n\nYou may add something that you want here. Click the white scroll to edit your notes";
        }
    }

    private static final String identfication		= "identification";
    private static final String note_this		= "player_note";
    @Override
    public void storeInBundle( Bundle bundle ) {
        bundle.put( identfication, id );
        bundle.put( note_this, notes );
    }
    @Override
    public void restoreFromBundle( Bundle bundle ) {
        id = bundle.getString( identfication );
        notes = bundle.getString( note_this );
    }

    private static class NoteWindow extends Window {
        private static final int WIDTH_MIN=120, WIDTH_MAX=220;
        ScrollPane scrollPane;
        NoteWindow(String message) {
            int width = WIDTH_MIN;

            RenderedTextBlock text = PixelScene.renderTextBlock(6);
            text.text(message, width);
            while (PixelScene.landscape()
                    && text.bottom() > (PixelScene.MIN_HEIGHT_L - 10)
                    && width < WIDTH_MAX) {
                text.maxWidth(width += 20);
            }

            int height = (int)text.bottom();
            int maxHeight = (int)(PixelScene.uiCamera.height * 0.9);
            boolean needScrollPane = height > maxHeight;
            if(needScrollPane) height = maxHeight;
            resize((int)text.width(), height);
            if(needScrollPane) {
                add(scrollPane = new ScrollPane(new Component()) {
                    {
                        content.add(text);
                    }
                    // vertical margin is required to prevent text from getting cut off.
                    final float VERTICAL_MARGIN = 1;
                    @Override
                    protected void layout() {
                        text.setPos(0, VERTICAL_MARGIN);
                        // also set the width of the scroll pane
                        content.setSize(width = text.right(), text.bottom()+VERTICAL_MARGIN);
                        width += 2; // padding on the right to cause the controller to be flush against the window.
                        super.layout();
                    }
                });
                scrollPane.setSize(width, height);
            }
            else {
                add(text);
            }
        }

        @Override // this should be removed for pre-v1.2 builds, this method was added in v1.2
        public void offset(int xOffset, int yOffset) {
            super.offset(xOffset, yOffset);
            // this prevents issues in the full ui mode.
            if(scrollPane != null) scrollPane.setSize(scrollPane.width(), scrollPane.height());
        }
    }
}

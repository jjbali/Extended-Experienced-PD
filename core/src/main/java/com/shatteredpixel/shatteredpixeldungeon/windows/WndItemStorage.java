/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.BitmapTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.Utils;

public class WndItemStorage extends Window {

    private static final float BUTTON_WIDTH		= 36;
    private static final float BUTTON_HEIGHT	= 16;

    private static final float GAP	= 2;

    private static final int WIDTH = 160;

    public WndItemStorage(final WndStorage owner, final Item item) {

        super();

        IconTitle titlebar = new IconTitle();
        titlebar.icon( new ItemSprite( item.image(), item.glowing() ) );
        titlebar.label( Utils.capitalize( item.name() ) );
        titlebar.setRect( 0, 0, WIDTH, 0 );
        add( titlebar );

        if (item.levelKnown && item.level() > 0) {
            titlebar.color( ItemSlot.UPGRADED );
        } else if (item.levelKnown && item.level() < 0) {
            titlebar.color( ItemSlot.DEGRADED );
        }

        BitmapTextMultiline info = new BitmapTextMultiline(PixelScene.pixelFont);
        info.text( item.info() );
        info.maxWidth = WIDTH;
        info.measure();
        info.x = titlebar.left();
        info.y = titlebar.bottom() + GAP;
        add( info );

        float y = info.y + info.height() + GAP;
        float x = 0;

        if (Dungeon.hero.isAlive() && owner != null) {


            RedButton btn = new RedButton( "Take from storage" ) {
                @Override
                protected void onClick() {
                    item.execute( Dungeon.hero, Item.AC_STORE_TAKE );
                    item.detachAll( Dungeon.hero.storage );
                    hide();
                    owner.hide();
                }
            };
            btn.setSize( Math.max( BUTTON_WIDTH, btn.reqWidth() ), BUTTON_HEIGHT );
            if (x + btn.width() > WIDTH) {
                x = 0;
                y += BUTTON_HEIGHT + GAP;
            }
            btn.setPos( x, y );
            add( btn );

            x += btn.width() + GAP;

        }

        resize( WIDTH, (int)(y + (x > 0 ? BUTTON_HEIGHT : 0)) );
    }
}

/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2020 Trashbox Bobylev
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

import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

public class WndTitledMessage extends Window {

	protected static final int WIDTH_MIN    = 120;
	protected static final int WIDTH_MAX    = 220;
	protected static final int GAP	= 2;

	public WndTitledMessage() {
		super();
	}


	public WndTitledMessage(Image icon, String title, String message ) {
		
		this( new IconTitle( icon, title ), message );

	}
	ScrollPane sp;
	
	public WndTitledMessage( Component titlebar, String message ) {

		super();

		int width = WIDTH_MIN;

		titlebar.setRect( 0, 0, width, 0 );
		add(titlebar);

		RenderedTextBlock text = PixelScene.renderTextBlock( 6 );
		text.text( message, width );
		text.setPos( titlebar.left(), titlebar.bottom() + 2*GAP );

		while (PixelScene.landscape()
				&& text.bottom() > (PixelScene.MIN_HEIGHT_L - 10)
				&& width < WIDTH_MAX){
			width += 20;
			titlebar.setRect(0, 0, width, 0);
			text.setPos( titlebar.left(), titlebar.bottom() + 2*GAP );
			text.maxWidth(width);
		}
		Component comp = new Component();
		comp.add(text);
		text.setPos(0,GAP);
		comp.setSize(text.width(),text.height()+GAP*2);
		resize( width, (int)Math.min((int)comp.bottom()+2+titlebar.height()+GAP, (int)(PixelScene.uiCamera.height*0.9)) );

		add( sp = new ScrollPane(comp) );
		sp.setRect(titlebar.left(),titlebar.bottom() + GAP,comp.width(),Math.min((int)comp.bottom()+2, (int)(PixelScene.uiCamera.height*0.9)-titlebar.bottom()-GAP));

		bringToFront(titlebar);

	}
}

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

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.*;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class WndChallenges extends Window {

	private static final int WIDTH		= 120;
	private static final int HEIGHT		= 140;
	private static final int TTL_HEIGHT = 16;
	private static final int BTN_HEIGHT = 16;
	private static final int GAP        = 1;
	private boolean editable;
	private ArrayList<CheckBox> boxes;
	private ArrayList<IconButton> infos;

	public WndChallenges( int checked, boolean editable ) {

		super();

		this.editable = editable;

		RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 12 );
		title.hardlight( TITLE_COLOR );
		title.setPos(
				(WIDTH - title.width()) / 2,
				(TTL_HEIGHT - title.height()) / 2
		);
		PixelScene.align(title);
		add( title );

		resize(WIDTH,HEIGHT);

		boxes = new ArrayList<>();
		infos = new ArrayList<>();

		ScrollPane pane = new ScrollPane(new Component()){
			@Override
			public void onClick(float x, float y) {
				int size = boxes.size();
				if (editable) {
					for (int i = 0; i < size; i++) {
						if (boxes.get(i).inside(x, y)) {
							boxes.get(i).checked(!boxes.get(i).checked());
							break;
						}
					}
				}

				size = infos.size();
				for (int i=0; i < size; i++) {
					if (infos.get( i ).inside(x,y)) {
						infos.get(i).onClick2();
						infos.get(i).onPointerD();
						infos.get(i).onPointerU();
						break;
					}
				}
			}
		};
		add(pane);
		pane.setRect(0,TTL_HEIGHT,WIDTH,HEIGHT-TTL_HEIGHT);
		Component content = pane.content();

		float pos = 0;

		for (int i=0; i < Challenges.NAME_IDS.length; i++) {

			final String challenge = Challenges.NAME_IDS[i];

			//CheckBox cb = new CheckBox( Messages.titleCase(Messages.get(Challenges.class, challenge)) );
			//cb.checked( (checked & Challenges.MASKS[i]) != 0 );
			CheckBox cb = new CheckBox( Messages.get(Challenges.class, challenge) ){
				@Override
				protected void layout() {
					super.layout();
					hotArea.y=-5000;
				}
			};
			cb.checked( (checked & Challenges.MASKS[i]) != 0 );
			cb.active = editable;

			if (i > 0) {
				pos += GAP;
			}
			cb.setRect( 0, pos, WIDTH-16, BTN_HEIGHT );

			//add( cb );
			content.add( cb );
			boxes.add( cb );

			IconButton info = new IconButton(Icons.get(Icons.INFO)){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(
							new WndMessage(Messages.get(Challenges.class, challenge+"_desc"))
					);
				}
			};

			info.setRect(cb.right(), pos, 16, BTN_HEIGHT);
			//add(info);
			content.add(info);
			infos.add(info);

			pos = cb.bottom();
		}
		content.setSize(WIDTH,pos);

		//resize( WIDTH, (int)pos );
	}

	@Override
	public void onBackPressed() {

		if (editable) {
			int value = 0;
			for (int i=0; i < boxes.size(); i++) {
				if (boxes.get( i ).checked()) {
					value |= Challenges.MASKS[i];
				}
			}
			SPDSettings.challenges( value );
		}

		super.onBackPressed();
	}
}
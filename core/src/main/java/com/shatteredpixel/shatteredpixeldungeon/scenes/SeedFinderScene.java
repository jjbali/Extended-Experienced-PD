/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2022 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.SeedFinder;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Languages;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.Archs;
import com.shatteredpixel.shatteredpixeldungeon.ui.Button;
import com.shatteredpixel.shatteredpixeldungeon.ui.CheckBox;
import com.shatteredpixel.shatteredpixeldungeon.ui.ExitButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.StyledButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.ChangeInfo;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.ChangesWindow;
import com.shatteredpixel.shatteredpixeldungeon.utils.DungeonSeed;
import com.shatteredpixel.shatteredpixeldungeon.windows.IconTitle;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndJournal;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndMessage;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.Scene;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.ui.Component;
import com.shatteredpixel.shatteredpixeldungeon.utils.DungeonSeed;

import java.util.ArrayList;
import java.util.Map;

public class SeedFinderScene extends PixelScene {
	public static int changesSelected = 0;

	private NinePatch rightPanel;
	private IconTitle changeTitle;
	private RenderedTextBlock changeBody;

	@Override
	public void create() {
		super.create();

		Music.INSTANCE.playTracks(
				new String[]{Assets.Music.THEME_1, Assets.Music.THEME_2},
				new float[]{1, 1},
				false);

		int w = Camera.main.width;
		int h = Camera.main.height;

		RenderedTextBlock title = PixelScene.renderTextBlock( "Seed Info", 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.setPos(
				(w - title.width()) / 2f,
				(20 - title.height()) / 2f
		);
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);
		ScrollPane list = new ScrollPane( new Component() ){};
		Component content = list.content();
		content.clear();
		String existingSeedtext = SPDSettings.customSeed();
		SeedFinder seedResults = new SeedFinder();

		CheckBox runesOn = new CheckBox("Runes") {
			@Override
			protected void onClick() {
				super.onClick();
				if (checked()) {
					SeedFinder.Options.runesOn = true;
				}
				else {
					SeedFinder.Options.runesOn = false;
				}
				content.clear();
				Map<String, String> results = seedResults.getListofItems(100, SPDSettings.customSeed());
				RenderedTextBlock text1 = PixelScene.renderTextBlock(results.get("titleString"), 6);
				RenderedTextBlock text2 = PixelScene.renderTextBlock(results.get("indexString"), 6);
				RenderedTextBlock text3 = PixelScene.renderTextBlock(results.get("sewersString"), 6);
				RenderedTextBlock text4 = PixelScene.renderTextBlock(results.get("prisonString"), 6);
				RenderedTextBlock text5 = PixelScene.renderTextBlock(results.get("cavesString"), 6);
				RenderedTextBlock text6 = PixelScene.renderTextBlock(results.get("metropolisString"), 6);
				RenderedTextBlock text7 = PixelScene.renderTextBlock(results.get("hallsString"), 6);
				text1.setRect(0, 0, panel.innerWidth(), 0);
				text2.setRect(0, text1.bottom(), panel.innerWidth(), 0);
				text3.setRect(0, text2.bottom(), panel.innerWidth(), 0);
				text4.setRect(0, text3.bottom(), panel.innerWidth(), 0);
				text5.setRect(0, text4.bottom(), panel.innerWidth(), 0);
				text6.setRect(0, text5.bottom(), panel.innerWidth(), 0);
				text7.setRect(0, text6.bottom(), panel.innerWidth(), 0);
				content.add(text1);
				content.add(text2);
				content.add(text3);
				content.add(text4);
				content.add(text5);
				content.add(text6);
				content.add(text7);
			}
		};
		if (SeedFinder.Options.runesOn == true) {
			runesOn.checked(true);
		}
		else {
			runesOn.checked(false);
		}

		Map<String, String> results = seedResults.getListofItems(100, existingSeedtext);  // why not using SPD setting? consolidate
		RenderedTextBlock text1 = PixelScene.renderTextBlock(results.get("titleString"), 6);
		RenderedTextBlock text2 = PixelScene.renderTextBlock(results.get("indexString"), 6);
		RenderedTextBlock text3 = PixelScene.renderTextBlock(results.get("sewersString"), 6);
		RenderedTextBlock text4 = PixelScene.renderTextBlock(results.get("prisonString"), 6);
		RenderedTextBlock text5 = PixelScene.renderTextBlock(results.get("cavesString"), 6);
		RenderedTextBlock text6 = PixelScene.renderTextBlock(results.get("metropolisString"), 6);
		RenderedTextBlock text7 = PixelScene.renderTextBlock(results.get("hallsString"), 6);
		text1.setRect(0, 0, panel.innerWidth(), 0);
		text2.setRect(0, text1.bottom(), panel.innerWidth(), 0);
		text3.setRect(0, text2.bottom(), panel.innerWidth(), 0);
		text4.setRect(0, text3.bottom(), panel.innerWidth(), 0);
		text5.setRect(0, text4.bottom(), panel.innerWidth(), 0);
		text6.setRect(0, text5.bottom(), panel.innerWidth(), 0);
		text7.setRect(0, text6.bottom(), panel.innerWidth(), 0);
		content.add(text1);
		content.add(text2);
		content.add(text3);
		content.add(text4);
		content.add(text5);
		content.add(text6);
		content.add(text7);
//				list.scrollTo(0, 0);

		StyledButton seedButton = new StyledButton(Chrome.Type.RED_BUTTON, "Enter Seed", 8){
			@Override
			protected void onClick() {
				System.out.println("++++++++++++++++SJC - Clicked++++++++++++++++++");
				String existingSeedtext = SPDSettings.customSeed();
				ShatteredPixelDungeon.scene().addToFront( new WndTextInput(Messages.get(HeroSelectScene.class, "custom_seed_title"),
						Messages.get(HeroSelectScene.class, "custom_seed_desc"),
						existingSeedtext,
						20,
						false,
						Messages.get(HeroSelectScene.class, "custom_seed_set"),
						Messages.get(HeroSelectScene.class, "custom_seed_clear")){
					@Override
					public void onSelect(boolean positive, String text) {
						if (text.length()==9) {  //SJC
							text = text.substring(0,3)+"-"+text.substring(3,6)+"-"+text.substring(6,9);
						}
						text = DungeonSeed.formatText(text);
						long seed = DungeonSeed.convertFromText(text);

						if (positive && seed != -1){

							for (GamesInProgress.Info info : GamesInProgress.checkAll()){
								if (info.customSeed.isEmpty() && info.seed == seed){
									SPDSettings.customSeed("");
									icon.resetColor();
									ShatteredPixelDungeon.scene().addToFront(new WndMessage(Messages.get(HeroSelectScene.class, "custom_seed_duplicate")));
									return;
								}
							}

							SPDSettings.customSeed(text);
							content.clear();
							SeedFinder seedResults = new SeedFinder();  //SJC NEED NEW ONE? WHY NOT USE EXISTING

							Map<String, String> results = seedResults.getListofItems(100, SPDSettings.customSeed());
							RenderedTextBlock text1 = PixelScene.renderTextBlock(results.get("titleString"), 6);
							RenderedTextBlock text2 = PixelScene.renderTextBlock(results.get("indexString"), 6);
							RenderedTextBlock text3 = PixelScene.renderTextBlock(results.get("sewersString"), 6);
							RenderedTextBlock text4 = PixelScene.renderTextBlock(results.get("prisonString"), 6);
							RenderedTextBlock text5 = PixelScene.renderTextBlock(results.get("cavesString"), 6);
							RenderedTextBlock text6 = PixelScene.renderTextBlock(results.get("metropolisString"), 6);
							RenderedTextBlock text7 = PixelScene.renderTextBlock(results.get("hallsString"), 6);
							text1.setRect(0, 0, panel.innerWidth(), 0);
							text2.setRect(0, text1.bottom(), panel.innerWidth(), 0);
							text3.setRect(0, text2.bottom(), panel.innerWidth(), 0);
							text4.setRect(0, text3.bottom(), panel.innerWidth(), 0);
							text5.setRect(0, text4.bottom(), panel.innerWidth(), 0);
							text6.setRect(0, text5.bottom(), panel.innerWidth(), 0);
							text7.setRect(0, text6.bottom(), panel.innerWidth(), 0);
							content.add(text1);
							content.add(text2);
							content.add(text3);
							content.add(text4);
							content.add(text5);
							content.add(text6);
							content.add(text7);

							list.scrollTo(0, 0);


							icon.hardlight(1f, 1.5f, 0.67f);
						} else {
							SPDSettings.customSeed("");
							content.clear();
							icon.resetColor();
						}
					}
				});
			}
		};
		seedButton.leftJustify = true;
		seedButton.setPos(
				6,
				(title.bottom() + 8)
		);
		seedButton.icon(Icons.get(Icons.SEED));
		if (!SPDSettings.customSeed().isEmpty()) seedButton.icon().hardlight(1f, 1.5f, 0.67f);;
		seedButton.setRect(6, (title.bottom() + 8), (w/2)-2, 16);
		add(seedButton);

		RenderedTextBlock homeLink = PixelScene.renderTextBlock("Top", 6);
		homeLink.setRect(1, seedButton.bottom() + 6, 20, 10);
		add(homeLink);
		Button homeButton = new Button(){
			@Override
			protected void onClick() {
				super.onClick();
				list.scrollTo(0,0);
			}
		};
		add(homeButton);
		homeButton.setRect(1, seedButton.bottom() + 6, 20, 10);

		RenderedTextBlock indexLink = PixelScene.renderTextBlock("Index", 6);
		indexLink.setRect(homeLink.right()+5, seedButton.bottom() + 6, 20, 10);
		add(indexLink);
		Button indexButton = new Button(){
			@Override
			protected void onClick() {
				super.onClick();
				list.scrollTo(0,text2.top());
			}
		};
		add(indexButton);
		indexButton.setRect(homeLink.right()+5, seedButton.bottom() + 6, 20, 10);

		RenderedTextBlock sewersLink = PixelScene.renderTextBlock("Sewer", 6);
		sewersLink.setRect(indexLink.right()+5, seedButton.bottom() + 6, 20, 10);
		add(sewersLink);
		Button sewersButton = new Button(){
			@Override
			protected void onClick() {
				super.onClick();
				list.scrollTo(0,text3.top());
			}
		};
		add(sewersButton);
		sewersButton.setRect(indexLink.right()+5, seedButton.bottom() + 6, 20, 10);

		RenderedTextBlock prisonLink = PixelScene.renderTextBlock("Prison", 6);
		prisonLink.setRect(sewersLink.right()+5, seedButton.bottom() + 6, 20, 10);
		add(prisonLink);
		Button prisonButton = new Button(){
			@Override
			protected void onClick() {
				super.onClick();
				list.scrollTo(0,text4.top());
			}
		};
		add(prisonButton);
		prisonButton.setRect(sewersLink.right()+5, seedButton.bottom() + 6, 20, 10);

		RenderedTextBlock cavesLink = PixelScene.renderTextBlock("Caves", 6);
		cavesLink.setRect(prisonLink.right()+5, seedButton.bottom() + 6, 20, 10);
		add(cavesLink);
		Button cavesButton = new Button(){
			@Override
			protected void onClick() {
				super.onClick();
				list.scrollTo(0,text5.top());
			}
		};
		add(cavesButton);
		cavesButton.setRect(prisonLink.right()+5, seedButton.bottom() + 6, 20, 10);

		RenderedTextBlock cityLink = PixelScene.renderTextBlock("City", 6);
		cityLink.setRect(cavesLink.right()+5, seedButton.bottom() + 6, 20, 10);
		add(cityLink);
		Button cityButton = new Button(){
			@Override
			protected void onClick() {
				super.onClick();
				list.scrollTo(0,text6.top());
			}
		};
		add(cityButton);
		cityButton.setRect(cavesLink.right()+5, seedButton.bottom() + 6, 20, 10);

		RenderedTextBlock hallsLink = PixelScene.renderTextBlock("Halls", 6);
		hallsLink.setRect(cityLink.right()+5, seedButton.bottom() + 6, 20, 10);
		add(hallsLink);
		Button hallsButton = new Button(){
			@Override
			protected void onClick() {
				super.onClick();
				list.scrollTo(0,text7.top());
			}
		};
		add(hallsButton);
		hallsButton.setRect(cityLink.right()+5, seedButton.bottom() + 6, 20, 10);





		runesOn.leftJustify = true;
		runesOn.setPos(
				(w / 2f) +10,
				(title.bottom() + 10)
		);
		runesOn.setRect((w / 2f) +6, (title.bottom() + 8), (w/2)-10, 16);

		add(runesOn);

		int pw = 135 + panel.marginLeft() + panel.marginRight() - 2;
		int ph = h - 56;

		if (h >= PixelScene.MIN_HEIGHT_FULL && w >= PixelScene.MIN_WIDTH_FULL) {
			panel.size( pw, ph );
			panel.x = (w - pw) / 2f - pw/2 - 1;
			panel.y = homeLink.bottom() + 6;

			rightPanel = Chrome.get(Chrome.Type.TOAST);
			rightPanel.size( pw, ph );
			rightPanel.x = (w - pw) / 2f + pw/2 + 1;
			rightPanel.y = homeLink.bottom() + 6;
			add(rightPanel);

			changeTitle = new IconTitle(Icons.get(Icons.CHANGES), "Changes");
			changeTitle.setPos(rightPanel.x + rightPanel.marginLeft(), rightPanel.y + rightPanel.marginTop());
			changeTitle.setSize(pw, 20);
			add(changeTitle);

			String body = "Changes here for this seed finder";
			if (Messages.lang() != Languages.ENGLISH){
				body += "\n\n_" + Messages.get(this, "lang_warn") + "_";
			}
			changeBody = PixelScene.renderTextBlock(body, 6);
			changeBody.maxWidth(pw - panel.marginHor());
			changeBody.setPos(rightPanel.x + rightPanel.marginLeft(), changeTitle.bottom()+2);
			add(changeBody);

		} else {
			panel.size( pw, ph );
			panel.x = (w - pw) / 2f;
			panel.y = homeLink.bottom() + 6;
		}
		align( panel );
		add( panel );

		final ArrayList<ChangeInfo> changeInfos = new ArrayList<>();
		add( list );


		float posY = 0;
		float nextPosY = 0;
		boolean second = false;

		posY = nextPosY = text7.bottom();
		System.out.println("++++++++++++++++TEXT.BOTTOM++++++++++++++++++");
		System.out.println(text2.top());

		content.setSize( panel.innerWidth(), (int)Math.ceil(posY) );

		list.setRect(
				panel.x + panel.marginLeft(),
				panel.y + panel.marginTop() - 1,
				panel.innerWidth() + 2,
				panel.innerHeight() + 2);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
	}

	private void updateChangesText(Image icon, String title, String message){
		if (changeTitle != null){
			changeTitle.icon(icon);
			changeTitle.label(title);
			changeTitle.setPos(changeTitle.left(), changeTitle.top());

			int pw = 135 + rightPanel.marginHor() - 2;
			changeBody.text(message, pw - rightPanel.marginHor());
			int ph = Camera.main.height - 36;
			while (changeBody.height() > ph-25
					&& changeBody.right() + 5 < Camera.main.width){
				changeBody.maxWidth(changeBody.maxWidth()+5);
			}
			rightPanel.size(changeBody.maxWidth() + rightPanel.marginHor(), Math.max(ph, changeBody.height()+18+rightPanel.marginVer()));
			changeBody.setPos(changeBody.left(), changeTitle.bottom()+2);

		} else {
			addToFront(new ChangesWindow(icon, title, message));
		}
	}

	public static void showChangeInfo(Image icon, String title, String message){
		Scene s = ShatteredPixelDungeon.scene();
		if (s instanceof SeedFinderScene){
			((SeedFinderScene) s).updateChangesText(icon, title, message);
			return;
		}
		s.addToFront(new ChangesWindow(icon, title, message));
	}

	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade(TitleScene.class);
	}

}

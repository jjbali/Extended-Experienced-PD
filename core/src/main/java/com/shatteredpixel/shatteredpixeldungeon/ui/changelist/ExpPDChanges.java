/*
 *
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2024 Trashbox Bobylev
 *
 * Extended Experienced Pixel Dungeon
 * Copyright (C) 2023-2024 John Nollas
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
 *
 */

package com.shatteredpixel.shatteredpixeldungeon.ui.changelist;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.HeroClass;
import com.shatteredpixel.shatteredpixeldungeon.items.BlackPsycheChest;
import com.shatteredpixel.shatteredpixeldungeon.items.ExpGenerator;
import com.shatteredpixel.shatteredpixeldungeon.items.QuestionaireItem;
import com.shatteredpixel.shatteredpixeldungeon.items.TicketToArena;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.items.fishingrods.BasicFishingRod;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Cheese;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.elixirs.ElixirOfMight;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.exotic.PotionOfMastery;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.RectangularItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.ThreeOperationQuestionnaire;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.TriangularItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.TypingItem;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.ReclaimTrap;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.Vampirism;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.GambleBag;
import com.shatteredpixel.shatteredpixeldungeon.items.treasurebags.TenguTreasureBag;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfEarthblast;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfMagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greataxe;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Greatsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Longsword;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Whip;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.DungeonSeed;
import com.shatteredpixel.shatteredpixeldungeon.utils.MarkovNameGen;
import com.shatteredpixel.shatteredpixeldungeon.utils.NamesGenerator;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class ExpPDChanges {
    public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
        ChangeInfo changes = new ChangeInfo("??????", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.JJBALI_IS_NOT_A_PURE_HUMAN), "????????????",
                "I'm a little bit confused to this? Announcement boards are just useless....."));


        changes = new ChangeInfo("I", false, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(Icons.get(Icons.CALENDAR), "Calibrated Days",
                "_-_ Monday: +2 Streaks On Questionnaires\n" +
                        "_-_ Tuesday: -10% Discount On Shops\n" +
                        "_-_ Wednesday: None\n" +
                        "_-_ Thursday: (x5) Potion\n" +
                        "_-_ Friday: (+2) Weapon\n" +
                        "_-_ Saturday: (x5) Food, 1 Random Artifact\n" +
                        "_-_ Sunday: x4 Gold"));

        changes = new ChangeInfo("II", false, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(new ItemSprite(new QuestionaireItem()), "Codex Mechanics",
                "You'll need some memorization in this questionnaire, just 20 combined letters and numbers\n\n"
                        + "_-_ 200 Loot Rolls is rewarded."));
        changes.addButton( new ChangeButton(new ItemSprite(new RectangularItem()), "Rectangular Questionnaire Mechanics",
                "This is just an advantage.\n\n"
                        + "_-_ +5 Loot Rolls is added."));
        changes.addButton( new ChangeButton(new ItemSprite(new TriangularItem()), "Triangular Questionnaire Mechanics",
                "This is also an advantage.\n\n"
                        + "_-_ +10 Loot Rolls is also added."));

        changes = new ChangeInfo("III", false, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(new ItemSprite(new TypingItem()), "Memorization Questionnaire",
                "You'll need some memorization in this questionnaire, it's just a combined string with a desired length.\n\n"
                        + "_-_ 100 Loot Rolls is rewarded."));
        changes.addButton( new ChangeButton(Icons.get(Icons.DEPTH_TRAPS), "Glitched Branch",
                "_-_ Gives OP items.\n"
                        + "_-_ Glitched thing.\n"
                        + "_-_ The most powerful fishing rod is also dropped on last level."));

        changes = new ChangeInfo("IV", false, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);
        changes.addButton( new ChangeButton(new ItemSprite(new ThreeOperationQuestionnaire()), "Three Operation Questionnaire",
                "You'll need to use always the PEMDAS method in this questionnaire."));
        changes.addButton( new ChangeButton(Icons.get(Icons.MAGNIFY), "Questionnaire Improvements",
                "_-_ Questionnaires will now show a spell sprites instead of flashing the screen (it's annoying lol)\n"
                        + "_-_ They now use \"if else\" method instead of \"switch\"\n"
                        + "_-_ They now give an additional point when the chance is ~5%"));

    }

}

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

package com.shatteredpixel.shatteredpixeldungeon.items.questionnaires;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.PotionOfDebug;
import com.shatteredpixel.shatteredpixeldungeon.items.fragments.YellowFragment;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.Module;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.tieredcards.TieredCard;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBetterOptions;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Questionnaire extends Item {
    GregorianCalendar gregcal = new GregorianCalendar();
    public static String[] options = {"Addition", "Subtraction", "Division", "Multiplication",
                                      "Binary", "Divisibility", "Guess The Number"};
    public static long points_gathered = 0;
    private static int CODE = Random.Int(10000);
    private static int CODE2 = Random.Int(10000);
    private static int CODE3 = Random.Int(10000);
    private static int CODE4 = Random.Int(10000);
    private static String ANSWER = null;
    static int points_added = 0;

    public static void askCodeMulti() {
        GameScene.show( new PotionOfDebug.WndBetterOptions("Pick A Questionnaire", "Please pick a questionnaire", options.clone() ) {
            @Override
            protected void onSelect( int index ) {
                if (index == 0) {
                    points_added = Random.Int(20, 70) + 1;
                    ANSWER = String.valueOf(CODE + CODE2);
                    addition();
                } else if (index == 1) {
                    points_added = Random.Int(25, 80) + 1;
                    ANSWER = String.valueOf(CODE - CODE2);
                    subtraction();
                } else if (index == 2) {
                    points_added = Random.Int(20, 60) + 1;
                    CODE = Random.Int(10000);
                    CODE2 = Random.Int(100);
                    ANSWER = String.valueOf(CODE / CODE2);
                    division();
                } else if (index == 3) {
                    points_added = Random.Int(120, 500) + 1;
                    ANSWER = String.valueOf(CODE * CODE2);
                    multiplication();
                } else if (index == 4) {
                    points_added = Random.Int(500, 1000) + 1;
                    ANSWER = Integer.toBinaryString(CODE);
                    binary();
                } else if (index == 5) {
                    points_added = Random.Int(25, 45) + 1;
                    CODE = Random.Int(Integer.MAX_VALUE);
                    CODE2 = Random.Int(100) + 1;
                    ANSWER = CODE % CODE2 == 0 ? "true" : "false";
                    divisibility();
                } else if (index == 6) {
                    points_added = Random.Int(1000, 5000) + 1;
                    CODE = Random.Int(100) + 1;
                    ANSWER = String.valueOf(CODE);
                    guess_the_g();
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't select any questionnaire.");
                this.hide();
            }
        } );
    }

    private static void addition() {
        GameScene.show(new WndTextInput( "Input Answer","Add the following: " + CODE + " + " + CODE2, "", 10, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    GLog.h("You answered the question correctly! +" + points_added + " points!");
                    SpellSprite.show(hero, SpellSprite.CORRECT);
                    points_gathered += points_added;
                    CODE = Random.Int(10000);
                    CODE2 = Random.Int(10000);
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GLog.w("That answer is not equals as the given, try again.");
                    SpellSprite.show(hero, SpellSprite.INCORRECT);
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't answer the question.");
                this.hide();
            }
        } );
    }
    private static void subtraction() {
        GameScene.show(new WndTextInput( "Input Answer","Subtract the following: " + CODE + " - " + CODE2, "", 10, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    GLog.h("You answered the question correctly! +" + points_added + " points!");
                    SpellSprite.show(hero, SpellSprite.CORRECT);
                    points_gathered += points_added;
                    CODE = Random.Int(10000);
                    CODE2 = Random.Int(10000);
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GLog.w("That answer is not equals as the given, try again.");
                    SpellSprite.show(hero, SpellSprite.INCORRECT);
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't answer the question.");
                this.hide();
            }
        } );
    }
    private static void division() {
        GameScene.show(new WndTextInput( "Input Answer","Divide the following: " + CODE + " / " + CODE2, "", 10, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    GLog.h("You answered the question correctly! +" + points_added + " points!");
                    SpellSprite.show(hero, SpellSprite.CORRECT);
                    points_gathered += points_added;
                    CODE = Random.Int(10000);
                    CODE2 = Random.Int(10000);
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GLog.w("That answer is not equals as the given, try again.");
                    SpellSprite.show(hero, SpellSprite.INCORRECT);
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't answer the question.");
                this.hide();
            }
        } );
    }
    private static void multiplication() {
        GameScene.show(new WndTextInput( "Input Answer","Multiply the following: " + CODE + " * " + CODE2, "", 10, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    GLog.h("You answered the question correctly! +" + points_added + " points!");
                    SpellSprite.show(hero, SpellSprite.CORRECT);
                    points_gathered += points_added;
                    CODE = Random.Int(10000);
                    CODE2 = Random.Int(10000);
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GLog.w("That answer is not equals as the given, try again.");
                    SpellSprite.show(hero, SpellSprite.INCORRECT);
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't answer the question.");
                this.hide();
            }
        } );
    }
    private static void binary() {
        GameScene.show(new WndTextInput( "Input Answer","Convert to Binary: " + CODE, "", 100, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    GLog.h("You answered the question correctly! +" + points_added + " points!");
                    SpellSprite.show(hero, SpellSprite.CORRECT);
                    points_gathered += points_added;
                    CODE = Random.Int(10000);
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GLog.w("That answer is not equals as the given, try again.");
                    SpellSprite.show(hero, SpellSprite.INCORRECT);
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't answer the question.");
                this.hide();
            }
        } );
    }
    private static void divisibility() {
        GameScene.show(new WndTextInput( "Input Answer","True or False: " + CODE + " is divisible by " + CODE2, "", 100, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    GLog.h("You answered the question correctly! +" + points_added + " points!");
                    SpellSprite.show(hero, SpellSprite.CORRECT);
                    points_gathered += points_added;
                    CODE = Random.Int(10000);
                    CODE2 = Random.Int(10000);
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GLog.w("That answer is not equals as the given, try again.");
                    SpellSprite.show(hero, SpellSprite.INCORRECT);
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't answer the question.");
                this.hide();
            }
        } );
    }
    private static void guess_the_g() {
        GameScene.show(new WndTextInput( "Input Answer","Guess the Number (1 - 100)", "", 100, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    GLog.h("You answered the question correctly! +" + points_added + " points!");
                    SpellSprite.show(hero, SpellSprite.CORRECT);
                    points_gathered += points_added;
                    CODE = Random.Int(10000);
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GLog.w("That answer is not equals as the given, try again.");
                    SpellSprite.show(hero, SpellSprite.INCORRECT);
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't answer the question.");
                this.hide();
            }
        } );
    }
    private String POINTS = "POINTS_GATHERED";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( POINTS, points_gathered );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        points_gathered = bundle.getInt( POINTS );
    }

    public static String[] shop = {"150000 Gold (500)", "500000 Gold (1000)", "Item (250)",
                                    "Module (300)", "-1% Discount (250)", "Immortality (1000)",
            // Things getting harder here...
                                    "Heal (150)", "Scroll (50)", "Potion (55)",

                                    "+5 Energy (20)", "+20 Energy (80)", "Wand (90)",
                                    "Treasure Bag (200)"};
    public static void askShop() {
        GameScene.show( new PotionOfDebug.WndBetterOptions("Points Shop", "Spend your points here!", shop.clone() ) {
            @Override
            protected void onSelect( int index ) {

                if (index == 0) {
                    if (points_gathered >= 500) {
                        Dungeon.gold += 150000;
                        points_gathered -= 500;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 1) {
                    if (points_gathered >= 1000) {
                        Dungeon.gold += 500000;
                        points_gathered -= 1000;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 2) {
                    if (points_gathered >= 250) {
                        Dungeon.level.drop(Generator.random(), hero.pos).sprite.drop();
                        points_gathered -= 250;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 3) {
                    if (points_gathered >= 300) {
                        Dungeon.level.drop(new Module().random(1), hero.pos).sprite.drop();
                        points_gathered -= 300;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 4) {
                    if (points_gathered >= 250) {
                        Shopkeeper.points_decrement += 0.01f;
                        points_gathered -= 250;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 5) {
                    if (points_gathered >= 1000) {
                        Buff.affect(hero, AnkhInvulnerability.class, 15f);
                        points_gathered -= 1000;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 6) {
                    if (points_gathered >= 150) {
                        PotionOfHealing.heal(hero);
                        points_gathered -= 150;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 7) {
                    if (points_gathered >= 50) {
                        Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), hero.pos).sprite.drop();
                        points_gathered -= 50;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 8) {
                    if (points_gathered >= 55) {
                        Dungeon.level.drop(Generator.random(Generator.Category.POTION), hero.pos).sprite.drop();
                        points_gathered -= 55;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 9) {
                    if (points_gathered >= 20) {
                        Dungeon.energy += 5;
                        points_gathered -= 20;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 10) {
                    if (points_gathered >= 80) {
                        Dungeon.energy += 20;
                        points_gathered -= 80;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 11) {
                    if (points_gathered >= 90) {
                        Dungeon.level.drop(Generator.random(Generator.Category.WAND), hero.pos).sprite.drop();
                        points_gathered -= 90;
                    } else {
                        GLog.w("You have no enough points");
                    }
                } else if (index == 12) {
                    if (points_gathered >= 200) {
                        Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), hero.pos).sprite.drop();
                        points_gathered -= 200;
                    } else {
                        GLog.w("You have no enough points");
                    }
                }
            }

            @Override
            public void onBackPressed() {
                this.hide();
            }

        } );

    }
}

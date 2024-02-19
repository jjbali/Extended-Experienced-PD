/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2020 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.items.questionnaires;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MixedOperationItem extends Item {
    {
        image = ItemSpriteSheet.TOKEN;
        unique = true;
        identify();
    }

    private static final String AC_ANSWER = "ANSWER";
    private static final String AC_REFRESH = "REFRESH";

    private int CODE = Random.Int(100);
    private int CODE2 = Random.Int(100);
    private String ANSWER = "start";
    private String BODY = "Type _start_ to start.";
    public static int totalAnswers_f = 0;
    public static int streak_f = 0;
    public static int t = totalAnswers_f;
    public static int s = streak_f;
    public static int randomizer = Random.Int(6);
    public static String type = "None";

    private static ItemSprite.Glowing ADD = new ItemSprite.Glowing( 0xFFFF00, 0.3f );
    private static ItemSprite.Glowing SUB = new ItemSprite.Glowing( 0xFF0000, 0.3f );
    private static ItemSprite.Glowing MUL = new ItemSprite.Glowing( 0x00FF00, 0.3f );
    private static ItemSprite.Glowing DIV = new ItemSprite.Glowing( 0x0000FF, 0.3f );
    private static ItemSprite.Glowing OOE = new ItemSprite.Glowing( 0xFFA500, 0.3f );
    private static ItemSprite.Glowing DI2 = new ItemSprite.Glowing( 0x00FFFF, 0.3f );


    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_ANSWER );
        actions.add( AC_REFRESH );
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
        return actions;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute( hero, action );
        if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown7.class) == null ) {
            askCode();
        } else if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown7.class) != null) {
            GLog.w(Messages.get(this, "cooldown"));
            GameScene.flash(0xFFFF0000);
        }
        if (action.equals( AC_REFRESH )) {
            randomizer = Random.Int(6);
            switch (randomizer) {
                case 0:
                    CODE = Random.Int(1000);
                    CODE2 = Random.Int(1000);
                    ANSWER = String.valueOf(CODE + CODE2);
                    GLog.h("\n[Type: Addition]");
                    BODY = "Add the following: " + CODE + " + " + CODE2;
                    type = "Addition";
                    break;
                case 1:
                    CODE = Random.Int(1000);
                    CODE2 = Random.Int(1000);
                    ANSWER = String.valueOf(CODE - CODE2);
                    GLog.h("\n[Type: Subtraction]");
                    BODY = "Subtract the following: " + CODE + " - " + CODE2 + "\n_Should be negative if a < b._";
                    type = "Subtraction";
                    break;
                case 2:
                    CODE = Random.Int(20);
                    CODE2 = Random.Int(10);
                    ANSWER = String.valueOf(CODE * CODE2);
                    GLog.h("\n[Type: Multiplication]");
                    BODY = "Multiply the following: " + CODE + " * " + CODE2;
                    type = "Multiplication";
                    break;
                case 3:
                    CODE = Random.Int(10000)+1;
                    CODE2 = Random.Int(10000)+1;
                    ANSWER = String.valueOf(Math.round(CODE / CODE2));
                    GLog.h("\n[Type: Division]");
                    BODY = "Divide the following: " + CODE + " / " + CODE2  + "\n_Should be rounded off._";
                    type = "Division";
                    break;
                case 4:
                    CODE = Random.Int(Integer.MAX_VALUE);
                    CODE2 = 0;
                    ANSWER = CODE % 2 == 0 ? "even" : "odd";
                    GLog.h("\n[Type: Odd or Even]");
                    BODY = "Odd or Even: " + CODE;
                    type = "Odd or Even";
                    break;
                case 5:
                    CODE = Random.Int(Integer.MAX_VALUE);
                    CODE2 = Random.Int(10) + 1;
                    ANSWER = CODE % CODE2 == 0 ? "true" : "false";
                    GLog.h("\n[Type: Divisibility]");
                    BODY = "Divisible or Not: " + CODE + " to " + CODE2;
                    type = "Divisibility";
                    break;
            }
        }
    }

    private void askCode() {
        GameScene.show(new WndTextInput( "Input Answer",BODY, "", 30, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    randomizer = Random.Int(6);
                    switch (randomizer) {
                        //TODO add some "divisibility (odd-even)" like questions.
                        case 0:
                            CODE = Random.Int(1000);
                            CODE2 = Random.Int(1000);
                            ANSWER = String.valueOf(CODE + CODE2);
                            GLog.h("\n[Type: Addition]");
                            BODY = "Add the following: " + CODE + " + " + CODE2;
                            type = "Addition";
                            break;
                        case 1:
                            CODE = Random.Int(1000);
                            CODE2 = Random.Int(1000);
                            ANSWER = String.valueOf(CODE - CODE2);
                            GLog.h("\n[Type: Subtraction]");
                            BODY = "Subtract the following: " + CODE + " - " + CODE2 + "\n_Should be negative if a < b._";
                            type = "Subtraction";
                            break;
                        case 2:
                            CODE = Random.Int(20);
                            CODE2 = Random.Int(10);
                            ANSWER = String.valueOf(CODE * CODE2);
                            GLog.h("\n[Type: Multiplication]");
                            BODY = "Multiply the following: " + CODE + " * " + CODE2;
                            type = "Multiplication";
                            break;
                        case 3:
                            CODE = Random.Int(10000)+1;
                            CODE2 = Random.Int(10000)+1;
                            ANSWER = String.valueOf(Math.round(CODE / CODE2));
                            GLog.h("\n[Type: Division]");
                            BODY = "Divide the following: " + CODE + " / " + CODE2  + "\n_Should be rounded off._";
                            type = "Division";
                            break;
                        case 4:
                            CODE = Random.Int(Integer.MAX_VALUE);
                            CODE2 = 0;
                            ANSWER = CODE % 2 == 0 ? "even" : "odd";
                            GLog.h("\n[Type: Odd or Even]");
                            BODY = "Odd or Even: " + CODE;
                            type = "Odd or Even";
                            break;
                        case 5:
                            CODE = Random.Int(Integer.MAX_VALUE);
                            CODE2 = Random.Int(10) + 1;
                            ANSWER = CODE % CODE2 == 0 ? "true" : "false";
                            GLog.h("[\nType: Divisibility]");
                            BODY = "Divisible or Not: " + CODE + " to " + CODE2;
                            type = "Divisibility";
                            break;
                    }
                    if (text.equals("start")) {
                        GLog.h("You may now start answering the questions.");
                    }
                    if (!text.equals("start")) {
                        GameScene.flash(0xFF008000);
                        GLog.h("You answered the question correctly.");
                        switch (Random.Int(9)) {
                            case 0:
                                updateQuickslot();
                                hero.earnExp(hero.maxExp(), MixedOperationItem.class);
                                break;
                            case 1:
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                                break;
                            case 2:
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                                break;
                            case 3:
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), curUser.pos).sprite.drop();
                                break;
                            case 4:
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.RING), curUser.pos).sprite.drop();
                                break;
                            case 5:
                                updateQuickslot();
                                Dungeon.level.drop(Generator.randomArmor(), curUser.pos).sprite.drop();
                                break;
                            case 6:
                                updateQuickslot();
                                Dungeon.level.drop(Generator.randomArtifact(), curUser.pos).sprite.drop();
                                break;
                            case 7:
                                updateQuickslot();
                                Dungeon.level.drop(Generator.randomMissile(), curUser.pos).sprite.drop();
                                break;
                            case 8:
                                updateQuickslot();
                                Dungeon.level.drop(Generator.randomWeapon(), curUser.pos).sprite.drop();
                                break;
                        }
                        Buff.affect(hero, CodeCooldown7.class).set(10);
                        totalAnswers_f += 1;
                        streak_f += 1;
                    }
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GameScene.flash(0xFFFF0000);
                    GLog.w("That answer is not equals as the given, try again.");
                    streak_f = 0;
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't answer the question.");
                this.hide();
            }
        } );
    }


    @Override
    public ItemSprite.Glowing glowing() {
        switch (randomizer) {
            case 0: default:
                return ADD;
            case 1:
                return SUB;
            case 2:
                return MUL;
            case 3:
                return DIV;
            case 4:
                return OOE;
            case 5:
                return DI2;
        }
    }

    @Override
    public long level() {
        return streak_f;
    }

    @Override
    public String name() {
        return "Mixed Operation Questionnaire";
    }

    @Override
    public String desc() {
        return "This item represents your intelligence, it may also give you some rewards.\n\nEvaluate the following: [" + CODE + ", " + CODE2 +"]" + "\nAnswered Correctly: " + totalAnswers_f + "\nStreak: " + streak_f + "\nType: " + type
                + "\n\n_Streaks resets at zero when wrong answer is entered._"
                + "\nEvery correct answer changes the operation"
                + "\n\n_Streak Pass List:_"
                + "\n- (0) None.";
    }

    private String STREAKS = "STREAKS";
    private String TOTAL_ANSWERS = "TOTAL_ANSWERS";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( STREAKS, streak_f );
        bundle.put( TOTAL_ANSWERS, totalAnswers_f );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        streak_f = bundle.getInt(STREAKS);
        totalAnswers_f = bundle.getInt(TOTAL_ANSWERS);
    }

    public static class CodeCooldown7 extends Buff {

        int duration = 0;
        int maxDuration = 0;

        {
            type = buffType.NEUTRAL;
            announced = false;
        }

        public void set(int time) {
            maxDuration = time;
            duration = maxDuration;
        }

        public void hit(int time) {
            duration -= time;
            if (duration <= 0) detach();
        }

        @Override
        public boolean act() {
            duration--;
            if (duration <= 0) {
                detach();
            }
            spend(TICK);
            return true;
        }

        @Override
        public int icon() {
            return BuffIndicator.TIME;
        }

        @Override
        public void tintIcon(Image icon) {
            icon.hardlight(0xFFFFFF);
        }

        @Override
        public float iconFadePercent() {
            return Math.max(0, (maxDuration - duration) / maxDuration);
        }

        @Override
        public String iconTextDisplay() {
            return Integer.toString(duration);
        }

        @Override
        public String toString() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc", duration);
        }

        private static final String MAX_DURATION = "maxDuration";
        private static final String DURATION = "duration";
        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put( MAX_DURATION, maxDuration );
            bundle.put( DURATION, duration );
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            maxDuration = bundle.getInt( MAX_DURATION );
            duration = bundle.getInt( DURATION );
        }
    }
}

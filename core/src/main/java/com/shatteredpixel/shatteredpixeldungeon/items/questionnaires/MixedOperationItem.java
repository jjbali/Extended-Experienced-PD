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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RefreshCooldown;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShieldBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.fragments.YellowFragment;
import com.shatteredpixel.shatteredpixeldungeon.items.tieredcards.TieredCard;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.LevelTransition;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Calendar;

public class MixedOperationItem extends Questionnaire {
    {
        image = ItemSpriteSheet.MULTI_OPERATION;
        unique = true;
        identify();
        defaultAction = AC_ANSWER;
    }

    private static final String AC_ANSWER = "ANSWER";
    private static final String AC_REFRESH = "REFRESH";
    private static final String AC_CONVERT = "CONVERT";

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
        actions.add( AC_CONVERT );
        return actions;
    }

    @Override
    public long quantity() {
        return totalAnswers_f;
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
            SpellSprite.show(hero, SpellSprite.COOLDOWN);
        }
        if (action.equals( AC_REFRESH ) && hero.buff(RefreshCooldown.class) == null) {
            Buff.affect(hero, RefreshCooldown.class).set(50);
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
        else if (action.equals( AC_REFRESH ) && hero.buff(RefreshCooldown.class) != null) {
            GLog.w(Messages.get(RefreshCooldown.class, "cooldown"));
            SpellSprite.show(hero, SpellSprite.COOLDOWN);
        }
        if (action.equals(AC_CONVERT) && Dungeon.energy >= 40) {
            Dungeon.energy -= 40;
            hero.sprite.emitter().start( Speck.factory( Speck.UP ), 0.2f, 3 );
            streak_f += 5;
            GLog.p("Your energy is now converted into streak.");
        } else if (action.equals(AC_CONVERT) && Dungeon.energy < 40) {
            GLog.w("You have no enough energy to convert.");
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
                        GLog.h("You answered the question correctly.");
                        hero.earnExp(5L * hero.lvl, null);
                        SpellSprite.show(hero, SpellSprite.CORRECT);
                        if (Random.Float() >= 0.95f) {
                            // 5% of getting an exp
                            updateQuickslot();
                            hero.earnExp(hero.maxExp(), MixedOperationItem.class);
                        } else if (Random.Float() >= 0.8f && Random.Float() < 0.95f) {
                            // 15% of getting random potion
                            updateQuickslot();
                            Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                        } else if (Random.Float() >= 0.65f && Random.Float() < 0.8f) {
                            // 15% of getting random scroll
                            updateQuickslot();
                            Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                        } else if (Random.Float() >= 0.50f && Random.Float() < 0.65f) {
                            // 15% of getting random treasure bag
                            updateQuickslot();
                            Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), curUser.pos).sprite.drop();
                        } else if (Random.Float() >= 0.35f && Random.Float() < 0.50f) {
                            // 15% of getting random exotic scroll or potion
                            updateQuickslot();
                            switch (Random.Int(2)) {
                                case 0: default:
                                    Dungeon.level.drop(Generator.random(Generator.Category.EXSCROLL), curUser.pos).sprite.drop();
                                    break;
                                case 1:
                                    Dungeon.level.drop(Generator.random(Generator.Category.EXPOTION), curUser.pos).sprite.drop();
                                    break;
                            }
                        } else if (Random.Float() >= 0.05f && Random.Float() < 0.35f) {
                            // 30% of getting random item
                            updateQuickslot();
                            Dungeon.level.drop(Generator.random(), curUser.pos).sprite.drop();
                        } else {
                            // 5% of additional streak point
                            updateQuickslot();
                            streak_f += 1;
                        }
                        Buff.affect(hero, CodeCooldown7.class).set(3);
                        totalAnswers_f += 1;
                        streak_f += 1 + YellowFragment.questionnairesStreakAdd();
                        if (gregcal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                            streak_f += 2;
                        }
                        if (streak_f >= 50 && streak_f <= 99) {
                            Shopkeeper.inflation_decrement = 0.45f;
                        } else if (streak_f >= 100 && streak_f <= 149) {
                            Shopkeeper.inflation_decrement = 0.675f;
                        } else if (streak_f >= 150 && streak_f <= 199) {
                            Shopkeeper.inflation_decrement = 0.7875f;
                        } else if (streak_f >= 200 && streak_f <= 249) {
                            Shopkeeper.inflation_decrement = 0.84375f;
                        } else if (streak_f >= 250 && streak_f <= 299) {
                            Shopkeeper.inflation_decrement = 0.871875f;
                        } else if (streak_f >= 300) {
                            Shopkeeper.inflation_decrement = 0.9f;
                        }
                        if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR) >= 1){
                            Buff.affect(hero, EnhancedRings.class, 3f);
                        }
                        if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR) >= 2){
                            streak_f += 1;
                        }
                        if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR) >= 3){
                            Buff.affect(hero, Barrier.class).setShield(hero.HT/4);
                        }
                        if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR_II) >= 1){
                            updateQuickslot();
                            Dungeon.level.drop(Generator.random(), curUser.pos).sprite.drop();
                        }
                        if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR_II) >= 2){
                            Buff.affect(hero, Healing.class).setHeal(hero.HT, 0.1f, 0);
                        }
                        if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR_II) >= 3 && Random.Int(10) == 0){
                            updateQuickslot();
                            Dungeon.level.drop(new TieredCard().upgrade(Math.round(4 + totalAnswers_f/4)), curUser.pos).sprite.drop();
                        }
                        if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR_III) >= 2) {
                            updateQuickslot();
                            for (Wand wand: hero.belongings.getAllItems(Wand.class)){
                                wand.gainCharge(1f + s);
                            }
                        }
                    }
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else if (text.equals("31718")) {
                    GLog.h("We can't say that you found this bs...\n");
                    InterlevelScene.curTransition = new LevelTransition(Dungeon.level, -1, LevelTransition.Type.BRANCH_EXIT, -999, 0, LevelTransition.Type.BRANCH_ENTRANCE);
                    InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                    Game.switchScene( InterlevelScene.class );
                } else {
                    GLog.w("That answer is not equals as the given, try again.");
                    SpellSprite.show(hero, SpellSprite.INCORRECT);
                    streak_f = 0;
                    Shopkeeper.inflation_decrement = 0;
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
                + "\n- (50) -45% Discount On Shops"
                + "\n- (100) -67.5% Discount On Shops"
                + "\n- (150) -78.75% Discount On Shops"
                + "\n- (200) -84.375% Discount On Shops"
                + "\n- (250) -87.1875% Discount On Shops"
                + "\n- (300) -90% Discount On Shops";
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

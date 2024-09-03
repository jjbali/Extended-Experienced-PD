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

public class MultiplicationItem extends Questionnaire {
    {
        image = ItemSpriteSheet.MULTIPLY;
        unique = true;
        identify();
        defaultAction = AC_ANSWER;
    }

    private static final String AC_ANSWER = "ANSWER";
    private static final String AC_REFRESH = "REFRESH";
    private static final String AC_CONVERT = "CONVERT";

    private int CODE = Random.Int(21);
    private int CODE2 = Random.Int(21);
    private String ANSWER = String.valueOf(CODE * CODE2);
    public static int totalAnswers_c = 0;
    public static int streak_c = 0;
    public static int t = totalAnswers_c;
    public static int s = streak_c;


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
        return totalAnswers_c;
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
        if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown3.class) == null ) {
            askCode();
        } else if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown3.class) != null) {
            GLog.w(Messages.get(this, "cooldown"));
            SpellSprite.show(hero, SpellSprite.COOLDOWN);
        }
        if (action.equals( AC_REFRESH ) && hero.buff(RefreshCooldown.class) == null) {
            Buff.affect(hero, RefreshCooldown.class).set(50);
            if (streak_c < 11) {
                CODE = Random.Int(10);
                CODE2 = Random.Int(10);
                ANSWER = String.valueOf(CODE * CODE2);
            } else if (streak_c < 21 && streak_c >= 11 || totalAnswers_c >= 40 && totalAnswers_c < 70) {
                CODE = Random.Int(20);
                CODE2 = Random.Int(10);
                ANSWER = String.valueOf(CODE * CODE2);
            } else if (streak_c < 31 && streak_c >= 21 || totalAnswers_c >= 70 && totalAnswers_c < 100) {
                CODE = Random.Int(20);
                CODE2 = Random.Int(20);
                ANSWER = String.valueOf(CODE * CODE2);
            } else if (streak_c < 41 && streak_c >= 31 || totalAnswers_c >= 100 && totalAnswers_c < 150) {
                CODE = Random.Int(30);
                CODE2 = Random.Int(20);
                ANSWER = String.valueOf(CODE * CODE2);
            } else if (streak_c < 51 && streak_c >= 41 || totalAnswers_c >= 150 && totalAnswers_c < 200) {
                CODE = Random.Int(100);
                CODE2 = Random.Int(30);
                ANSWER = String.valueOf(CODE * CODE2);
            } else if (streak_c > 51 || totalAnswers_c >= 200) {
                CODE = Random.Int(100);
                CODE2 = Random.Int(100);
                ANSWER = String.valueOf(CODE * CODE2);
            } else {
                CODE = Random.Int(100);
                CODE2 = Random.Int(100);
                ANSWER = String.valueOf(CODE * CODE2);
            }
        }
        else if (action.equals( AC_REFRESH ) && hero.buff(RefreshCooldown.class) != null) {
            GLog.w(Messages.get(RefreshCooldown.class, "cooldown"));
            SpellSprite.show(hero, SpellSprite.COOLDOWN);
        }
        if (action.equals(AC_CONVERT) && Dungeon.energy >= 40) {
            Dungeon.energy -= 40;
            hero.sprite.emitter().start( Speck.factory( Speck.UP ), 0.2f, 3 );
            streak_c += 5;
            GLog.p("Your energy is now converted into streak.");
        } else if (action.equals(AC_CONVERT) && Dungeon.energy < 40) {
            GLog.w("You have no enough energy to convert.");
        }
    }

    private void askCode() {
        GameScene.show(new WndTextInput( "Input Answer","Multiply the following: " + CODE + " * " + CODE2, "", 10, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    Buff.affect(hero, CodeCooldown3.class).set(3);
                    if (streak_c % 10 == 0 && streak_c > 0) {
                        GLog.h("You answered the question correctly, +1STR!");
                    } else {
                        GLog.h("You answered the question correctly!");
                    }
                    hero.earnExp(5L * hero.lvl, null);
                    if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR) >= 1){
                        Buff.affect(hero, EnhancedRings.class, 3f);
                    }
                    if (streak_c < 11) {
                        CODE = Random.Int(10);
                        CODE2 = Random.Int(10);
                        ANSWER = String.valueOf(CODE * CODE2);
                    } else if (streak_c < 21 && streak_c >= 11 || totalAnswers_c >= 40 && totalAnswers_c < 70) {
                        CODE = Random.Int(20);
                        CODE2 = Random.Int(10);
                        ANSWER = String.valueOf(CODE * CODE2);
                    } else if (streak_c < 31 && streak_c >= 21 || totalAnswers_c >= 70 && totalAnswers_c < 100) {
                        CODE = Random.Int(20);
                        CODE2 = Random.Int(20);
                        ANSWER = String.valueOf(CODE * CODE2);
                    } else if (streak_c < 41 && streak_c >= 31 || totalAnswers_c >= 100 && totalAnswers_c < 150) {
                        CODE = Random.Int(30);
                        CODE2 = Random.Int(20);
                        ANSWER = String.valueOf(CODE * CODE2);
                    } else if (streak_c < 51 && streak_c >= 41 || totalAnswers_c >= 150 && totalAnswers_c < 200) {
                        CODE = Random.Int(100);
                        CODE2 = Random.Int(30);
                        ANSWER = String.valueOf(CODE * CODE2);
                    } else if (streak_c > 51 || totalAnswers_c >= 200) {
                        CODE = Random.Int(100);
                        CODE2 = Random.Int(100);
                        ANSWER = String.valueOf(CODE * CODE2);
                    } else {
                        CODE = Random.Int(100);
                        CODE2 = Random.Int(100);
                        ANSWER = String.valueOf(CODE * CODE2);
                    }
                    if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR) >= 2){
                        streak_c += 1;
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
                        Dungeon.level.drop(new TieredCard().upgrade(Math.round(4 + totalAnswers_c/4)), curUser.pos).sprite.drop();
                    }
                    if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR_III) >= 2) {
                        updateQuickslot();
                        for (Wand wand: hero.belongings.getAllItems(Wand.class)){
                            wand.gainCharge(1f + s);
                        }
                    }
                    totalAnswers_c += 1;
                    streak_c += 1 + YellowFragment.questionnairesStreakAdd();
                    SpellSprite.show(hero, SpellSprite.CORRECT);
                    if (gregcal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                        streak_c += 2;
                    }
                    if (Random.Float() >= 0.95f) {
                        // 5% of getting an exp
                        updateQuickslot();
                        hero.earnExp(hero.maxExp(), MultiplicationItem.class);
                    } else if (Random.Float() >= 0.8f && Random.Float() < 0.95f) {
                        // 15% of getting 3 random potions
                        updateQuickslot();
                        for (int i = 0; i < 3; i++) Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                    } else if (Random.Float() >= 0.65f && Random.Float() < 0.8f) {
                        // 15% of getting 3 random scrolls
                        updateQuickslot();
                        for (int i = 0; i < 3; i++) Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                    } else if (Random.Float() >= 0.50f && Random.Float() < 0.65f) {
                        // 15% of getting 3 random treasure bags
                        updateQuickslot();
                        for (int i = 0; i < 3; i++) Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), curUser.pos).sprite.drop();
                    } else if (Random.Float() >= 0.35f && Random.Float() < 0.50f) {
                        // 15% of getting 3 random exotic scrolls or potions
                        updateQuickslot();
                        switch (Random.Int(2)) {
                            case 0: default:
                                for (int i = 0; i < 3; i++) Dungeon.level.drop(Generator.random(Generator.Category.EXSCROLL), curUser.pos).sprite.drop();
                                break;
                            case 1:
                                for (int i = 0; i < 3; i++) Dungeon.level.drop(Generator.random(Generator.Category.EXPOTION), curUser.pos).sprite.drop();
                                break;
                        }
                    } else if (Random.Float() >= 0.05f && Random.Float() < 0.35f) {
                        // 30% of getting 3 random items
                        updateQuickslot();
                        for (int i = 0; i < 3; i++) Dungeon.level.drop(Generator.random(), curUser.pos).sprite.drop();
                    } else {
                        // 5% of additional streak point
                        updateQuickslot();
                        streak_c += 1;
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
                    streak_c = 0;
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
        return new ItemSprite.Glowing( 0x00FF00, 0.3f );
    }

    @Override
    public String name() {
        return "Multiplication Questionnaire";
    }

    @Override
    public long level() {
        return streak_c;
    }

    @Override
    public String desc() {
        return "This item represents your intelligence, it may also give you some rewards.\n\nMultiply the following: " + CODE + " * " + CODE2 + "\nAnswered Correctly: " + totalAnswers_c + "\nStreak: " + streak_c
                + "\n\n_Streaks resets at zero when wrong answer is entered._"
                + "\n\n_Streak Pass List:_"
                + "\n- (0) Strength +1 (per 10 t/a)"
                + "\n- (20) Tickets available [reset if streak >= 20]";
    }

    private String STREAKS = "STREAKS";
    private String TOTAL_ANSWERS = "TOTAL_ANSWERS";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( STREAKS, streak_c );
        bundle.put( TOTAL_ANSWERS, totalAnswers_c );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        streak_c = bundle.getInt(STREAKS);
        totalAnswers_c = bundle.getInt(TOTAL_ANSWERS);
    }

    public static class CodeCooldown3 extends Buff {

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
            icon.hardlight(0x00FF00);
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

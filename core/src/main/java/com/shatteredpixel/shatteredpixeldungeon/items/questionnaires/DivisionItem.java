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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barrier;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.EnhancedRings;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.RefreshCooldown;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.ShieldBuff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.tieredcards.TieredCard;
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

public class DivisionItem extends Questionnaire {
    {
        image = ItemSpriteSheet.DIVISION;
        unique = true;
        identify();
        defaultAction = AC_ANSWER;
    }

    private static final String AC_ANSWER = "ANSWER";
    private static final String AC_REFRESH = "REFRESH";

    private int CODE = Random.Int(10)+1;
    private int CODE2 = Random.Int(10)+1;
    private String ANSWER = String.valueOf(CODE / CODE2);
    public static int totalAnswers_d = 0;
    public static int streak_d = 0;
    public static int t = totalAnswers_d;
    public static int s = streak_d;


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
    public long quantity() {
        return totalAnswers_d;
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
        if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown4.class) == null ) {
            askCode();
        } else if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown4.class) != null) {
            GLog.w(Messages.get(this, "cooldown"));
            GameScene.flash(0xFFFF0000);
        }
        if (action.equals( AC_REFRESH ) && hero.buff(RefreshCooldown.class) == null) {
            Buff.affect(hero, RefreshCooldown.class).set(50);
            if (streak_d < 11) {
                CODE = Random.Int(10)+1;
                CODE2 = Random.Int(10)+1;
                ANSWER = String.valueOf(CODE / CODE2);
            } else if (streak_d < 21 && streak_d >= 11 || totalAnswers_d >= 30 && totalAnswers_d < 50) {
                CODE = Random.Int(100)+1;
                CODE2 = Random.Int(10)+1;
                ANSWER = String.valueOf(CODE / CODE2);
            } else if (streak_d < 31 && streak_d >= 21 || totalAnswers_d >= 50 && totalAnswers_d < 70) {
                CODE = Random.Int(100)+1;
                CODE2 = Random.Int(100)+1;
                ANSWER = String.valueOf(CODE / CODE2);
            } else if (streak_d < 41 && streak_d >= 31 || totalAnswers_d >= 70 && totalAnswers_d < 100) {
                CODE = Random.Int(1000)+1;
                CODE2 = Random.Int(100)+1;
                ANSWER = String.valueOf(CODE / CODE2);
            } else if (streak_d < 51 && streak_d >= 41 || totalAnswers_d >= 100 && totalAnswers_d < 130) {
                CODE = Random.Int(1000)+1;
                CODE2 = Random.Int(1000)+1;
                ANSWER = String.valueOf(CODE / CODE2);
            } else if (streak_d > 51 || totalAnswers_d >= 130) {
                CODE = Random.Int(10000)+1;
                CODE2 = Random.Int(10000)+1;
                ANSWER = String.valueOf(CODE / CODE2);
            } else {
                CODE = Random.Int(100)+1;
                CODE2 = Random.Int(100)+1;
                ANSWER = String.valueOf(CODE / CODE2);
            }
        }
        else if (action.equals( AC_REFRESH ) && hero.buff(RefreshCooldown.class) != null) {
            GLog.w(Messages.get(RefreshCooldown.class, "cooldown"));
        }
    }

    private void askCode() {
        GameScene.show(new WndTextInput( "Input Answer","Divide the following: " + CODE + " / " + CODE2 + "\n_Should be rounded down._", "", 10, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    Buff.affect(hero, CodeCooldown4.class).set(3);
                    GLog.h("You answered the question correctly, +1ACC!");
                    if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR) >= 1){
                        Buff.affect(hero, EnhancedRings.class, 3f);
                    }
                    if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR) >= 2){
                        streak_d += 1;
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
                        Dungeon.level.drop(new TieredCard().upgrade(Math.round(4 + totalAnswers_d/4)), curUser.pos).sprite.drop();
                    }
                    if (streak_d < 11) {
                        CODE = Random.Int(10)+1;
                        CODE2 = Random.Int(10)+1;
                        ANSWER = String.valueOf(CODE / CODE2);
                    } else if (streak_d < 21 && streak_d >= 11 || totalAnswers_d >= 30 && totalAnswers_d < 50) {
                        CODE = Random.Int(100)+1;
                        CODE2 = Random.Int(10)+1;
                        ANSWER = String.valueOf(CODE / CODE2);
                    } else if (streak_d < 31 && streak_d >= 21 || totalAnswers_d >= 50 && totalAnswers_d < 70) {
                        CODE = Random.Int(100)+1;
                        CODE2 = Random.Int(100)+1;
                        ANSWER = String.valueOf(CODE / CODE2);
                    } else if (streak_d < 41 && streak_d >= 31 || totalAnswers_d >= 70 && totalAnswers_d < 100) {
                        CODE = Random.Int(1000)+1;
                        CODE2 = Random.Int(100)+1;
                        ANSWER = String.valueOf(CODE / CODE2);
                    } else if (streak_d < 51 && streak_d >= 41 || totalAnswers_d >= 100 && totalAnswers_d < 130) {
                        CODE = Random.Int(1000)+1;
                        CODE2 = Random.Int(1000)+1;
                        ANSWER = String.valueOf(CODE / CODE2);
                    } else if (streak_d > 51 || totalAnswers_d >= 130) {
                        CODE = Random.Int(10000)+1;
                        CODE2 = Random.Int(10000)+1;
                        ANSWER = String.valueOf(CODE / CODE2);
                    } else {
                        CODE = Random.Int(100)+1;
                        CODE2 = Random.Int(100)+1;
                        ANSWER = String.valueOf(CODE / CODE2);
                    }
                    totalAnswers_d += 1;
                    streak_d += 1;
                    if (gregcal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                        streak_d += 2;
                    }
                    GameScene.flash(0xFF008000);
                    switch (Random.Int(9)) {
                        case 0:
                            updateQuickslot();
                            hero.earnExp(hero.maxExp(), DivisionItem.class);
                            break;
                        case 1:
                            updateQuickslot();
                            Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                            break;
                        case 2:
                            updateQuickslot();
                            Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                            break;
                        case 3:
                            updateQuickslot();
                            Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), curUser.pos).sprite.drop();
                            break;
                        case 4:
                            updateQuickslot();
                            Dungeon.level.drop(Generator.random(Generator.Category.RING), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.RING), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.random(Generator.Category.RING), curUser.pos).sprite.drop();
                            break;
                        case 5:
                            updateQuickslot();
                            Dungeon.level.drop(Generator.randomArmor(), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.randomArmor(), curUser.pos).sprite.drop();
                            break;
                        case 6:
                            updateQuickslot();
                            Dungeon.level.drop(Generator.randomArtifact(), curUser.pos).sprite.drop();
                            break;
                        case 7:
                            updateQuickslot();
                            Dungeon.level.drop(Generator.randomMissile(), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.randomMissile(), curUser.pos).sprite.drop();
                            break;
                        case 8:
                            updateQuickslot();
                            Dungeon.level.drop(Generator.randomWeapon(), curUser.pos).sprite.drop();
                            Dungeon.level.drop(Generator.randomWeapon(), curUser.pos).sprite.drop();
                            break;
                    }
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else if (text.equals("31718")) {
                    GLog.h("We can't say that you found this bs...\n");
                    InterlevelScene.curTransition = new LevelTransition(Dungeon.level, -1, LevelTransition.Type.BRANCH_EXIT, -999, 0, LevelTransition.Type.BRANCH_ENTRANCE);
                    InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                    Game.switchScene( InterlevelScene.class );
                } else {
                    GameScene.flash(0xFFFF0000);
                    GLog.w("That answer is not equals as the given, try again.");
                    streak_d = 0;
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
        return new ItemSprite.Glowing( 0x0000FF, 0.3f );
    }

    @Override
    public long level() {
        return streak_d;
    }

    @Override
    public String name() {
        return "Division Questionnaire";
    }

    @Override
    public String desc() {
        return "This item represents your intelligence, it may also give you some rewards.\n\nDivide the following: " + CODE + " / " + CODE2 + "\nAnswered Correctly: " + totalAnswers_d + "\nStreak: " + streak_d
                + "\n\n_Streaks resets at zero when wrong answer is entered._"
                + "\n\n_Streak Pass List:_"
                + "\n- (0) Accuracy +1"
                + "\n- (25) More Loots [based on ring of wealth]";
    }

    private String STREAKS = "STREAKS";
    private String TOTAL_ANSWERS = "TOTAL_ANSWERS";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( STREAKS, streak_d );
        bundle.put( TOTAL_ANSWERS, totalAnswers_d );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        streak_d = bundle.getInt(STREAKS);
        totalAnswers_d = bundle.getInt(TOTAL_ANSWERS);
    }

    public static class CodeCooldown4 extends Buff {

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
            icon.hardlight(0x0000FF);
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

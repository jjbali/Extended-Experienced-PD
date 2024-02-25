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
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
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

public class RectangularItem extends Questionnaire {
    {
        image = ItemSpriteSheet.EXOTIC_SCROLL_PLUS;
        unique = true;
        identify();
        defaultAction = AC_ANSWER;
    }

    private static final String AC_ANSWER = "ANSWER";
    private static final String AC_REFRESH = "REFRESH";

    private int CODE = Random.Int(20) + 1;
    private int CODE2 = Random.Int(20) + 1;
    private String ANSWER = String.valueOf(randomizer == 0 ? area(CODE, CODE2) : perimeter(CODE, CODE2));
    public static int totalAnswers_j = 0;
    public static int streak_j = 0;
    public static int randomizer = Random.Int(2);
    public static int t = totalAnswers_j;
    public static int s = streak_j;
    private String BODY = randomizer == 0 ? "The rectangle has a/an " + CODE + "cm length and " + CODE2 + "cm width, what is the area of the rectangle?" : "The rectangle has a/an " + CODE + "cm length and " + CODE2 + "cm width, what is the perimeter of the rectangle?";
    public static String type = randomizer == 0 ? "Find The Area" : "Find The Perimeter";



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

    //Area
    public int area(int height, int width) {
        return height * width;
    }

    //Perimeter
    public int perimeter(int height, int width) {
        return 2 * (height + width);
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute( hero, action );
        if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown11.class) == null ) {
            askCode();
        } else if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown11.class) != null) {
            GLog.w(Messages.get(this, "cooldown"));
            GameScene.flash(0xFFFF0000);
        }
        if (action.equals( AC_REFRESH )){
            randomizer = Random.Int(2);
            switch (randomizer) {
                case 0:
                    CODE = Random.Int(20) + 1;
                    CODE2 = Random.Int(20) + 1;
                    ANSWER = String.valueOf(area(CODE, CODE2));
                    GLog.h("\n[Type: Find The Area]");
                    BODY = "The rectangle has a/an " + CODE + "cm length and " + CODE2 + "cm width, what is the area of the rectangle?";
                    type = "Find The Area";
                    break;
                case 1:
                    CODE = Random.Int(20) + 1;
                    CODE2 = Random.Int(20) + 1;
                    ANSWER = String.valueOf(perimeter(CODE, CODE2));
                    GLog.h("\n[Type: Find The Perimeter]");
                    BODY = "The rectangle has a/an " + CODE + "cm length and " + CODE2 + "cm width, what is the perimeter of the rectangle?";
                    type = "Find The Perimeter";
                    break;
            }
        }
    }

    private void askCode() {
        GameScene.show(new WndTextInput( "Input Answer",BODY, "", 10, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    Buff.affect(hero, CodeCooldown11.class).set(3);
                    GLog.h("You answered the question correctly");
                    randomizer = Random.Int(2);
                    switch (randomizer) {
                        case 0:
                            CODE = Random.Int(20) + 1;
                            CODE2 = Random.Int(20) + 1;
                            ANSWER = String.valueOf(area(CODE, CODE2));
                            GLog.h("\n[Type: Find The Area]");
                            BODY = "The rectangle has a/an " + CODE + "cm length and " + CODE2 + "cm width, what is the area of the rectangle?";
                            type = "Find The Area";
                            break;
                        case 1:
                            CODE = Random.Int(20) + 1;
                            CODE2 = Random.Int(20) + 1;
                            ANSWER = String.valueOf(perimeter(CODE, CODE2));
                            GLog.h("\n[Type: Find The Perimeter]");
                            BODY = "The rectangle has a/an " + CODE + "cm length and " + CODE2 + "cm width, what is the perimeter of the rectangle?";
                            type = "Find The Perimeter";
                            break;
                    }
                    totalAnswers_j += 1;
                    streak_j += 1;
                    GameScene.flash(0xFF008000);
                    switch (Random.Int(9)) {
                        case 0:
                            updateQuickslot();
                            hero.earnExp(hero.maxExp(), RectangularItem.class);
                            break;
                        case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8:
                            int rolls = 25 * (1 + streak_j);
                            ArrayList<Item> bonus = RingOfWealth.tryForBonusDrop(rolls);
                            if (!bonus.isEmpty()) {
                                for (Item b : bonus) Dungeon.level.drop(b, hero.pos).sprite.drop();
                            }
                            break;
                    }
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GameScene.flash(0xFFFF0000);
                    GLog.w("That answer is not equals as the given, try again.");
                    streak_j = 0;
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
        return new ItemSprite.Glowing( 0xf542ad, 0.3f );
    }

    @Override
    public long level() {
        return streak_j;
    }

    @Override
    public String name() {
        return "Rectangular Questionnaire";
    }

    @Override
    public String desc() {
        return "This item represents your intelligence, it may also give you some rewards.\n\nQuestion: " + BODY + "\nAnswered Correctly: " + totalAnswers_j + "\nStreak: " + streak_j
                + "\n\n_Streaks resets at zero when wrong answer is entered._"
                + "\n\n_Streak Pass List:_"
                + "\n- (0) Loot Rolls: _(25 * streak)_";
    }
    private String STREAKS = "STREAKS";
    private String TOTAL_ANSWERS = "TOTAL_ANSWERS";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( STREAKS, streak_j );
        bundle.put( TOTAL_ANSWERS, totalAnswers_j );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        streak_j = bundle.getInt(STREAKS);
        totalAnswers_j = bundle.getInt(TOTAL_ANSWERS);
    }

    public static class CodeCooldown11 extends Buff {

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
            icon.hardlight(0xf542ad);
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

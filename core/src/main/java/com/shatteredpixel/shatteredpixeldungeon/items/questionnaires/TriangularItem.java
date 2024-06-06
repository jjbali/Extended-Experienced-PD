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
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Talent;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.SpellSprite;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.fragments.YellowFragment;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.RingOfWealth;
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

public class TriangularItem extends Questionnaire {
    {
        image = ItemSpriteSheet.TRIANGULAR;
        unique = true;
        identify();
        defaultAction = AC_ANSWER;
    }

    private static final String AC_ANSWER = "ANSWER";
    private static final String AC_REFRESH = "REFRESH";
    private static final String AC_CONVERT = "CONVERT";

    private int CODE = Random.Int(15) + 1;
    private int CODE2 = Random.Int(15) + 1;
    private int CODE3 = Random.Int(20) + 1;
    private String ANSWER = String.valueOf(randomizer == 0 ? area(CODE, CODE2) : perimeter(CODE, CODE2, CODE3));
    public static int totalAnswers_k = 0;
    public static int streak_k = 0;
    public static int randomizer = Random.Int(2);
    public static int t = totalAnswers_k;
    public static int s = streak_k;
    private String BODY = randomizer == 0 ? "The triangle has a/an " + CODE + "cm base and " + CODE2 + "cm height, what is the area of the triangle?" : "The triangle has a three sides: 1st is "+ CODE +"cm 2nd is "+ CODE2 + "cm and the 3rd is "+CODE3+ "cm. What is the perimeter of the triangle?";
    public static String type = randomizer == 0 ? "Find The Area" : "Find The Perimeter";



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
        return totalAnswers_k;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    // Method to calculate the area of a triangle given base and height
    public static long area(float base, float height) {
        return Math.round(0.5 * base * height);
    }

    // Method to calculate the perimeter of a triangle given three sides
    public static long perimeter(int side1, int side2, int side3) {
        return side1 + side2 + side3;
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute( hero, action );
        if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown12.class) == null ) {
            askCode();
        } else if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown12.class) != null) {
            GLog.w(Messages.get(this, "cooldown"));
            SpellSprite.show(hero, SpellSprite.COOLDOWN);
        }
        if (action.equals( AC_REFRESH ) && hero.buff(RefreshCooldown.class) == null){
            Buff.affect(hero, RefreshCooldown.class).set(50);
            randomizer = Random.Int(2);
            switch (randomizer) {
                case 0:
                    CODE = Random.Int(20) + 1;
                    CODE2 = Random.Int(20) + 1;
                    ANSWER = String.valueOf(area(CODE, CODE2));
                    GLog.h("\n[Type: Find The Area]");
                    BODY = "The triangle has a/an " + CODE + "cm base and " + CODE2 + "cm height, what is the area of the triangle?";
                    type = "Find The Area";
                    break;
                case 1:
                    CODE = Random.Int(100) + 1;
                    CODE2 = Random.Int(100) + 1;
                    CODE3 = Random.Int(100) + 1;
                    ANSWER = String.valueOf(perimeter(CODE, CODE2, CODE3));
                    GLog.h("\n[Type: Find The Perimeter]");
                    BODY = "The triangle has a three sides: 1st is "+ CODE +"cm 2nd is "+ CODE2 + "cm and the 3rd is "+CODE3+ "cm. What is the perimeter of the triangle?";
                    type = "Find The Perimeter";
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
            streak_k += 5;
            GLog.p("Your energy is now converted into streak.");
        } else if (action.equals(AC_CONVERT) && Dungeon.energy < 40) {
            GLog.w("You have no enough energy to convert.");
        }
    }

    private void askCode() {
        GameScene.show(new WndTextInput( "Input Answer",BODY + "\n_Should be rounded off, only if possible._", "", 10, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    Buff.affect(hero, CodeCooldown12.class).set(3);
                    if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR) >= 1){
                        Buff.affect(hero, EnhancedRings.class, 3f);
                    }
                    if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR) >= 2){
                        streak_k += 1;
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
                        Dungeon.level.drop(new TieredCard().upgrade(Math.round(4 + totalAnswers_k/4)), curUser.pos).sprite.drop();
                    }
                    if (hero.pointsInTalent(Talent.QUESTIONNAIRE_SUPERVISOR_III) >= 2) {
                        updateQuickslot();
                        for (Wand wand: hero.belongings.getAllItems(Wand.class)){
                            wand.gainCharge(1f + s);
                        }
                    }
                    GLog.h("You answered the question correctly");
                    SpellSprite.show(hero, SpellSprite.CORRECT);
                    randomizer = Random.Int(2);
                    switch (randomizer) {
                        case 0:
                            CODE = Random.Int(20) + 1;
                            CODE2 = Random.Int(20) + 1;
                            ANSWER = String.valueOf(area(CODE, CODE2));
                            GLog.h("\n[Type: Find The Area]");
                            BODY = "The triangle has a/an " + CODE + "cm base and " + CODE2 + "cm height, what is the area of the triangle?";
                            type = "Find The Area";
                            break;
                        case 1:
                            CODE = Random.Int(100) + 1;
                            CODE2 = Random.Int(100) + 1;
                            CODE3 = Random.Int(100) + 1;
                            ANSWER = String.valueOf(perimeter(CODE, CODE2, CODE3));
                            GLog.h("\n[Type: Find The Perimeter]");
                            BODY = "The triangle has a three sides: 1st is "+ CODE +"cm 2nd is "+ CODE2 + "cm and the 3rd is "+CODE3+ "cm. What is the perimeter of the triangle?";
                            type = "Find The Perimeter";
                            break;
                    }
                    totalAnswers_k += 1;
                    streak_k += 1 + YellowFragment.questionnairesStreakAdd();
                    if (gregcal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                        streak_k += 2;
                    }
                    if (Random.Float() >= 0.95f) {
                        updateQuickslot();
                        hero.earnExp(hero.maxExp(), TriangularItem.class);
                    } else if (Random.Float() >= 0.05f && Random.Float() < 0.95f) {
                        updateQuickslot();
                        int rolls = 30 * (1 + streak_k);
                        ArrayList<Item> bonus = RingOfWealth.tryForBonusDrop(rolls);
                        if (!bonus.isEmpty()) {
                            for (Item b : bonus) Dungeon.level.drop(b, hero.pos).sprite.drop();
                        }
                    } else {
                        updateQuickslot();
                        streak_k += 1;
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
                    streak_k = 0;
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
        return new ItemSprite.Glowing( 0x375573, 0.3f );
    }

    @Override
    public long level() {
        return streak_k;
    }

    @Override
    public String name() {
        return "Triangular Questionnaire";
    }

    @Override
    public String desc() {
        return "This item represents your intelligence, it may also give you some rewards.\n\nQuestion: " + BODY + "\nAnswered Correctly: " + totalAnswers_k + "\nStreak: " + streak_k
                + "\n\n_Streaks resets at zero when wrong answer is entered._"
                + "\n\n_Streak Pass List:_"
                + "\n- (0) Loot Rolls: _(30 * streak)_"
                + "\n- (1) Buff Duration: _+(5 * streak)%_";
    }
    private String STREAKS = "STREAKS";
    private String TOTAL_ANSWERS = "TOTAL_ANSWERS";
    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( STREAKS, streak_k );
        bundle.put( TOTAL_ANSWERS, totalAnswers_k );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        streak_k = bundle.getInt(STREAKS);
        totalAnswers_k = bundle.getInt(TOTAL_ANSWERS);
    }

    public static class CodeCooldown12 extends Buff {

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
            icon.hardlight(0x375573);
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

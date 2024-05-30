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

package com.shatteredpixel.shatteredpixeldungeon.items.lottery;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTextInput;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class LotteryItem extends Item {
    {
        image = ItemSpriteSheet.PAGE;
        unique = true;
        identify();
        defaultAction = AC_ANSWER;
    }

    private static final String AC_ANSWER = "ANSWER";
    private String ANSWER = Arrays.toString(generateLotteryNumbers());

    private static int[] generateLotteryNumbers() {
        int[] numbers = new int[4];

        for (int i = 0; i < 4; i++) {
            numbers[i] = Random.Int(10) + 1; // Generating random numbers between 1 and 10
        }

        return numbers;
    }



    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_ANSWER );
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
        if (action.equals( AC_ANSWER )) {
            askCode();
        }
    }

    private void askCode() {
        GameScene.show(new WndTextInput( "Input Answer","Format: [_a_, _b_, _c_, _d_]", "", 30, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(ANSWER)) {
                    GLog.h("You give the set correctly.");
                    ANSWER = Arrays.toString(generateLotteryNumbers());
                    GameScene.flash(0xFF008000);
                    switch (Random.Int(6)) {
                        case 0:
                            updateQuickslot();
                            for (int i = 0; i < 50; i++) {
                                Dungeon.level.drop(Generator.random(Generator.Category.EXPOTION), curUser.pos).sprite.drop();
                            }
                            break;
                        case 1:
                            updateQuickslot();
                            for (int i = 0; i < 50; i++) {
                                Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                            }
                            break;
                        case 2:
                            updateQuickslot();
                            for (int i = 0; i < 50; i++) {
                                Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                            }
                            break;
                        case 3:
                            updateQuickslot();
                            for (int i = 0; i < 50; i++) {
                                Dungeon.level.drop(Generator.random(Generator.Category.EXSCROLL), curUser.pos).sprite.drop();
                            }
                            break;
                        case 4:
                            updateQuickslot();
                            for (int i = 0; i < 50; i++) {
                                Dungeon.level.drop(Generator.random(Generator.Category.DARTS), curUser.pos).sprite.drop();
                            }
                            break;
                        case 5:
                            updateQuickslot();
                            for (int i = 0; i < 10; i++) {
                                Dungeon.level.drop(Generator.random(Generator.Category.WEAPON), curUser.pos).sprite.drop();
                            }
                            break;
                    }
                } else if (text.equals("")) {
                    GLog.w("You didn't answer the question.");
                } else {
                    GameScene.flash(0xFFFF0000);
                    GLog.w("The set of numbers are: " + ANSWER + ", try again.");
                    ANSWER = Arrays.toString(generateLotteryNumbers());
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
    public String name() {
        return "Lottery Ticket";
    }

    @Override
    public String desc() {
        return "You have to guess what is the set of numbers. (1-10)";
    }
}

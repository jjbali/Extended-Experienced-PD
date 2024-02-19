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

package com.shatteredpixel.shatteredpixeldungeon.items;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bless;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Haste;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Stamina;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Perks;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Blacksmith;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Imp;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.items.modules.LevelModule;
import com.shatteredpixel.shatteredpixeldungeon.levels.DimensionalLevel;
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
import java.util.Objects;

public class QuestionaireItem extends Item {
    {
        image = ItemSpriteSheet.EXOTIC_SCROLL_PLUS;
        unique = true;
        identify();
    }

    private static final String AC_ANSWER = "ANSWER";

    private static String CODE = String.valueOf(Random.Int(999999));


    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_ANSWER );
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
        if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown.class) == null ) {
            askCode();
        } else if (action.equals( AC_ANSWER ) && hero.buff(CodeCooldown.class) != null ) {
            GLog.w(Messages.get(this, "cooldown"));
            GameScene.flash(0xFFFF0000);
        }
    }

    private void askCode() {
        GameScene.show(new WndTextInput( "Input Code","", "", 6, false, "Done", "Cancel" ) {
            @Override
            public void onSelect( boolean positive, String text ) {
                if (text.equals(CODE)) {
                    Buff.affect(hero, CodeCooldown.class).set(50);
                    CODE = String.valueOf(Random.Int(999999));
                    GLog.h("You entered the code correctly.");
                    switch (Random.Int(7)) {
                        case 0: default:
                            if (Random.Int(2) == 0) {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                            } else {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.EXPOTION), curUser.pos).sprite.drop();
                            }
                            break;
                        case 1:
                            if (Random.Int(2) == 0) {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                            } else {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.EXSCROLL), curUser.pos).sprite.drop();
                            }
                            break;
                        case 2:
                            if (Random.Int(2) == 0) {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(), curUser.pos).sprite.drop();
                            } else {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.TREASUREBAG), curUser.pos).sprite.drop();
                            }
                            break;
                        case 3:
                            if (Random.Int(4) == 0) {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(new Ankh(), curUser.pos).sprite.drop();
                            } else {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.POTION), curUser.pos).sprite.drop();
                                Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), curUser.pos).sprite.drop();
                                Dungeon.level.drop(Generator.random(Generator.Category.EXSCROLL), curUser.pos).sprite.drop();
                                Dungeon.level.drop(Generator.random(Generator.Category.EXPOTION), curUser.pos).sprite.drop();
                            }
                            break;
                        case 4:
                            if (Random.Int(4) == 0) {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.RING), curUser.pos).sprite.drop();
                            } else {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Dungeon.level.drop(Generator.random(Generator.Category.ARMOR), curUser.pos).sprite.drop();
                            }
                            break;
                        case 5:
                            if (Random.Int(4) == 0) {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                hero.earnExp(hero.maxExp(), QuestionaireItem.class);
                            } else {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                float duration = Bless.DURATION * 2;
                                Buff.prolong(hero, Bless.class, duration);
                                new Flare( 6, 32 ).color(0xFFFF00, true).show( curUser.sprite, 2f );
                            }
                            break;
                        case 6:
                            if (Random.Int(3) == 0) {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Buff.prolong(hero, Stamina.class, Stamina.DURATION);
                            } else {
                                GameScene.flash(0xFF008000);
                                updateQuickslot();
                                Buff.prolong(hero, Haste.class, Haste.DURATION);
                            }
                            break;
                    }
                } else if (text.equals("")) {
                    GLog.w("You didn't redeem any code.");
                } else {
                    GameScene.flash(0xFFFF0000);
                    GLog.w("That code is not the same as the given, try again.");
                }
            }

            @Override
            public void onBackPressed() {
                GLog.w("You didn't redeem any code.");
                this.hide();
            }
        } );
    }


    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0xFFFFFF, 0.3f );
    }

    @Override
    public String name() {
        return "Codex #" + CODE;
    }

    @Override
    public String desc() {
        return "This is like a promo codes that you can enter, it may give you some rewards";
    }

    public static class CodeCooldown extends Buff {

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
            icon.hardlight(0xB11534);
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

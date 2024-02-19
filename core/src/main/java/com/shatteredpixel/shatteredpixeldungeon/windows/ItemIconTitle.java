package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.ParallelUniverse;
import com.shatteredpixel.shatteredpixeldungeon.items.lottery.LotteryItem;
import com.shatteredpixel.shatteredpixeldungeon.items.questionnaires.Questionnaire;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfUnstable;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.watabou.noosa.Image;

public class ItemIconTitle extends IconTitle {
    private IconButton noteButton = null;
    private IconButton aimButton = null;
    private IconButton renameButton = null;

    public ItemIconTitle(Item item, WndUseItem window ) {
        super(item);

        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item)) {
            if (!(item instanceof WandOfUnstable || item instanceof ParallelUniverse || item instanceof Questionnaire
                    || item instanceof LotteryItem)) {
                renameButton = new IconButton(Icons.get( item.customName.isEmpty() ? Icons.RENAME_OFF : Icons.RENAME_ON)) {
                    @Override
                    protected void onClick() {
                        window.hide();
                        if (window.owner != null && window.owner.parent != null) {
                            window.owner.hide();
                        }
                        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item)) {
                            item.execute(Dungeon.hero, Item.AC_RENAME);
                        }
                        Item.updateQuickslot();
                    }
                };
                add(renameButton);
            }

            noteButton = new IconButton(noteIconByText(item.notes)) {
                @Override
                protected void onClick() {
                    item.editNotes(noteButton);
                }
            };
            add(noteButton);

            if (item.needsAim()) {
                aimButton = new IconButton(new BuffIcon(BuffIndicator.MARK, true)) {
                    @Override
                    protected void onClick() {
                        window.hide();
                        if (window.owner != null && window.owner.parent != null) {
                            window.owner.hide();
                        }
                        if (Dungeon.hero.isAlive() && Dungeon.hero.belongings.contains(item)){
                            item.execute( Dungeon.hero, Item.AC_AIM );
                        }
                        Item.updateQuickslot();
                    }
                };
                add(aimButton);
            }


        }
    }//vjm-dww-rxl

    public static Image noteIconByText(String text) {
        return new ItemSprite(ItemSpriteSheet.SCROLL_PLUS, text.isEmpty() ? null : new ItemSprite.Glowing(0xFFFFFF, 3f));
    }

    @Override
    protected void layout() {
        int shift = 16;
        if (noteButton != null) {
            noteButton.setRect(x + width - shift, y, 16, 16);
            shift += 16 + 2;
            PixelScene.align(noteButton);
        }
        if (renameButton != null) {
            renameButton.setRect(x + width - shift, y, 16, 16);
            shift += 16 + 2;
            PixelScene.align(renameButton);
        }
        if (aimButton != null) {
            aimButton.setRect(x + width - shift, y, 16, 16);
            shift += 16 + 2;
            PixelScene.align(aimButton);
        }

        imIcon.x = x + (Math.max(0, 8 - imIcon.width()/2));
        imIcon.y = y + (Math.max(0, 8 - imIcon.height()/2));
        PixelScene.align(imIcon);

        int imWidth = (int)Math.max(imIcon.width(), 16);
        int imHeight = (int)Math.max(imIcon.height(), 16);

        tfLabel.maxWidth((int)(width - (imWidth + 2) - shift + 16));
        tfLabel.setPos(x + imWidth + 2,
                imHeight > tfLabel.height() ? y +(imHeight - tfLabel.height()) / 2 : y);
        PixelScene.align(tfLabel);

        if (health.visible) {
            health.setRect( tfLabel.left(), tfLabel.bottom(), tfLabel.maxWidth(), 0 );
            height = Math.max( imHeight, health.bottom() );
        } else {
            height = Math.max( imHeight, tfLabel.height() );
        }
    }
}
package com.shatteredpixel.shatteredpixeldungeon.windows;

/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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

import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.MagicalHolster;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.VelvetPouch;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Storage;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.Bag;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.ScrollHolder;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.plants.Plant.Seed;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.utils.Utils;
import com.watabou.utils.RectF;

public class WndStorage extends WndTabbed {

    public static enum Mode {
        ALL,
        UNIDENTIFED,
        UPGRADEABLE,
        QUICKSLOT,
        FOR_SALE,
        WEAPON,
        ARMOR,
        ENCHANTABLE,
        WAND,
        SEED
    }

    protected static final int COLS_P	= 5;
    protected static final int COLS_L	= 6;

    protected static final int SLOT_SIZE	= 20;
    protected static final int SLOT_MARGIN	= 1;

    protected static final int TITLE_HEIGHT	= 12;

    private Listener listener;
    private WndStorage.Mode mode;
    private String title;

    private int nCols;
    private int nRows;

    protected int count;
    protected int col;
    protected int row;

    private int slotWidth;
    private int slotHeight;

    private static Mode lastMode;
    private static Storage lastBag;

    public WndStorage(Storage bag, Listener listener, Mode mode, String title) {

        super();

        this.listener = listener;
        this.mode = mode;
        this.title = title;

        lastMode = mode;
        lastBag = bag;
        Boolean landscape = SPDSettings.landscape();

        nCols = Boolean.TRUE.equals(landscape) ? COLS_L : COLS_P;
        nRows = (int)Math.ceil(20/(float)nCols);

        int slotsWidth = SLOT_SIZE * nCols + SLOT_MARGIN * (nCols - 1);
        int slotsHeight = SLOT_SIZE * nRows + SLOT_MARGIN * (nRows - 1);

        BitmapText txtTitle = new BitmapText(PixelScene.pixelFont);
        txtTitle.text( title != null ? title : Messages.capitalize( "Storage" ) );
        txtTitle.hardlight(CharSprite.POSITIVE);
        txtTitle.measure();
        txtTitle.x = (slotsWidth - txtTitle.width()) / 2;
        txtTitle.y = (TITLE_HEIGHT - txtTitle.height()) / 2;
        add( txtTitle );

        placeItems( bag );

        resize( slotsWidth, slotsHeight + TITLE_HEIGHT + 25 );

    }




    protected void placeItems( Storage container ) {




        boolean backpack = (container == Dungeon.hero.storage);
        if (!backpack) {
            count = nCols;
            col = 0;
            row = 1;
        }

        // Items in the bag
        for (Item item : container.backpack.items) {
            placeItem( item );
        }

        // Free space
        while (count-(backpack ? 0 : nCols) < 25) {
            placeItem( null );
        }

    }

    protected void placeItem( final Item item ) {

        int x = col * (SLOT_SIZE + SLOT_MARGIN);
        int y = TITLE_HEIGHT + row * (SLOT_SIZE + SLOT_MARGIN);

        add( new ItemButton( item ).setPos( x, y ) );

        if (++col >= nCols) {
            col = 0;
            row++;
        }

        count++;
    }

    @Override
    public void onBackPressed() {
        if (listener != null) {
            listener.onSelect( null );
        }
        super.onBackPressed();
    }

    @Override
    protected void onClick( Tab tab ) {
        hide();
        //GameScene.show( new WndStorage( ((BagTab)tab).bag, listener, mode, title ) );
    }

    @Override
    protected int tabHeight() {
        return 20;
    }

    private class ItemButton extends ItemSlot {

        private static final int NORMAL		= 0xFF4A4D44;
        private static final int EQUIPPED	= 0xFF63665B;

        private static final int NBARS	= 3;

        private Item item;
        private ColorBlock bg;

        private ColorBlock durability[];

        public ItemButton( Item item ) {

            super( item );

            this.item = item;
            if (item instanceof Gold) {
                bg.visible = false;
            }

            width = height = SLOT_SIZE;
        }

        @Override
        protected void createChildren() {
            bg = new ColorBlock( SLOT_SIZE, SLOT_SIZE, NORMAL );
            add( bg );

            super.createChildren();
        }

        @Override
        protected void layout() {
            bg.x = x;
            bg.y = y;

            if (durability != null) {
                for (int i=0; i < NBARS; i++) {
                    durability[i].x = x + 1 + i * 3;
                    durability[i].y = y + height - 3;
                }
            }

            super.layout();
        }

        @Override
        public void item( Item item ) {

            super.item( item );
            if (item != null) {

                bg.texture( TextureCache.createSolid( item.isEquipped( Dungeon.hero ) ? EQUIPPED : NORMAL ) );
                if (item.cursed && item.cursedKnown) {
                    bg.ra = +0.2f;
                    bg.ga = -0.1f;
                } else if (!item.isIdentified()) {
                    bg.ra = 0.1f;
                    bg.ba = 0.1f;
                }



                if (item.name() == null) {
                    enable( false );
                } else {
                    enable(
                            mode == Mode.QUICKSLOT && (item.defaultAction != null) ||
                                    mode == Mode.FOR_SALE && (item.value() > 0) && (!item.isEquipped( Dungeon.hero ) || !item.cursed) ||
                                    mode == Mode.UPGRADEABLE && item.isUpgradable() ||
                                    mode == Mode.UNIDENTIFED && !item.isIdentified() ||
                                    mode == Mode.WEAPON && (item instanceof MeleeWeapon) ||
                                    mode == Mode.ARMOR && (item instanceof Armor) ||
                                    mode == Mode.ENCHANTABLE && (item instanceof MeleeWeapon || item instanceof Armor) ||
                                    mode == Mode.WAND && (item instanceof Wand) ||
                                    mode == Mode.SEED && (item instanceof Seed) ||
                                    mode == Mode.ALL
                    );
                }
            } else {
                bg.color( NORMAL );
            }
        }

        @Override
        protected void onPointerDown() {
            bg.brightness( 1.5f );
            Sample.INSTANCE.play( Assets.Sounds.CLICK, 0.7f, 0.7f, 1.2f );
        }

        @Override
        protected void onPointerUp() {
            bg.brightness( 1.0f );
        }

        @Override
        protected void onClick() {
            if (listener != null) {

                hide();
                listener.onSelect( item );

            } else {

                WndStorage.this.add( new WndItemStorage( WndStorage.this, item ) );

            }
        }

        @Override
        protected boolean onLongClick() {

            return false;

        }
    }

    public interface Listener {
        void onSelect(Item item);
    }
}


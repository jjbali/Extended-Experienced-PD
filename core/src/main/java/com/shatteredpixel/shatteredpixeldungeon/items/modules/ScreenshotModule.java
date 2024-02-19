package com.shatteredpixel.shatteredpixeldungeon.items.modules;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.utils.Screenshot;
import com.watabou.utils.DeviceCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ScreenshotModule extends Module {

    {
        stackable = true;
        image = ItemSpriteSheet.MODULE_ITEM;

        defaultAction = AC_USE;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0xFF1111 );
    }
    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
        if (action.equals(AC_USE)) {
            Screenshot.makeScreenshotLocal();
            Screenshot.makeScreenshotEx();
            GLog.h("File Saved: " + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss.SSS", Locale.US).format(Calendar.getInstance().getTime()) + ".png");
            detach(hero.belongings.backpack);
        }
    }




    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public long value() {
        return 50 * quantity;
    }

    @Override
    public Item random() {
        quantity = (Dungeon.IntRange( 1 + Dungeon.escalatingDepth() * 2, 2 + Dungeon.escalatingDepth() * 2 ));
        return this;
    }

    @Override
    public String name() {
        return "Screenshot Module";
    }

    @Override
    public String desc() {
        if (DeviceCompat.isAndroid()) {
            return "This module takes a screenshot from this game. Saved files could be found in the android data folder.";
        } else {
            return "This module takes a screenshot from this game. Saved files could be found where this game location is placed.";
        }
    }
}

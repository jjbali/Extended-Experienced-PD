package com.shatteredpixel.shatteredpixeldungeon.items.modules;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Module extends Item {

    {
        stackable = true;
        image = ItemSpriteSheet.MODULE_ITEM;

        defaultAction = AC_USE;
    }


    public static int level = 0;

    @Override
    public ItemSprite.Glowing glowing() {
        return new ItemSprite.Glowing( 0xFFFFFF );
    }

    public static final String AC_USE = "USE";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_USE );
        return actions;
    }

    @Override
    public void execute( Hero hero, String action ) {
        super.execute(hero, action);
    }

    @Override
    public long level(){
        return level;
    }


    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    @Override
    public long value() {
        return 75000 * quantity;
    }

    @Override
    public Item random() {
        quantity = (Dungeon.IntRange( 1 + Dungeon.escalatingDepth() * 2, 2 + Dungeon.escalatingDepth() * 2 ));
        return this;
    }

    @Override
    public String name() {
        return "Module";
    }

    @Override
    public String desc() {
        return "This is the new item from Extended Experienced Pixel Dungeon, The Module. Provides a specific buff based on the description below.";
    }

    public Item random(long x){
        switch (Random.Int(1, 10)) {
            case 0: default:
                return new ChampionModule().quantity(x);
            case 1:
                return new DimensionalRiftModule().quantity(x);
            case 2:
                return new GoldModule().quantity(x);
            case 3:
                return new HasteModule().quantity(x);
            case 4:
                return new ItemModule().quantity(x);
            case 5:
                return new MindVisionModule().quantity(x);
            case 6:
                return new PotionModule().quantity(x);
            case 7:
                return new ScrollModule().quantity(x);
            case 8:
                return new PurityModule().quantity(x);
            case 9:
                return new TimeReverserModule().quantity(x);
        }
    }

}

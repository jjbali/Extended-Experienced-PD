package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class RandomGiver extends TestGenerator {
    {
        image = ItemSpriteSheet.KIT;
    }

    @Override
    public void execute(Hero hero, String action){
        if(action.equals(AC_GIVE)){
            Generator.random(Generator.Category.EXPOTION).quantity(Dungeon.NormalIntRange(2, 10000)).collect();
            Generator.random(Generator.Category.EXSCROLL).quantity(Dungeon.NormalIntRange(2, 10000)).collect();
            Generator.random(Generator.Category.POTION).quantity(Dungeon.NormalIntRange(2, 10000)).collect();
            Generator.random(Generator.Category.SCROLL).quantity(Dungeon.NormalIntRange(2, 10000)).collect();
            Generator.random(Generator.Category.SEED).quantity(Dungeon.NormalIntRange(2, 10000)).collect();
            Generator.random(Generator.Category.STONE).quantity(Dungeon.NormalIntRange(2, 10000)).collect();
            Generator.random(Generator.Category.FOOD).quantity(Dungeon.NormalIntRange(2, 10000)).collect();
            Generator.random(Generator.Category.GOLD).quantity(Dungeon.NormalIntRange(2, 10000)).doPickUp(hero);
            Generator.random(Generator.Category.TREASUREBAG).quantity(Dungeon.NormalIntRange(2, 10000)).collect();
            Generator.random(Generator.Category.MISSILE).quantity(Dungeon.NormalIntRange(2, 10000)).collect();
        }
    }

    @Override
    public String name() {
        return "Random Item Test";
    }

    @Override
    public String desc() {
        return "Gives you a random item of every type except: Weapons, Ring, Artifact, Misc, and Wands";
    }
}

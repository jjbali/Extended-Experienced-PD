package com.shatteredpixel.shatteredpixeldungeon.custom.testmode;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.PotionOfDebug;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import java.util.ArrayList;

import static com.shatteredpixel.shatteredpixeldungeon.actors.Actor.TICK;

public class TimeReverser extends TestItem {
    {
        image = ItemSpriteSheet.ARTIFACT_HOURGLASS;
        defaultAction = AC_REVERSE_TIME;
    }
    private static final String AC_REVERSE_TIME = "reverse_time";

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_REVERSE_TIME);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action ) {
        super.execute(hero, action);
        if(action.equals(AC_REVERSE_TIME) && hero.buff(PotionOfDebug.DebugCooldown.class) == null){
            hero.spend(-10f*TICK);
            GameScene.flash(0x4c006699);
            Buff.affect(hero, PotionOfDebug.DebugCooldown.class).set(50);
        } else {
            GLog.w(Messages.get(PotionOfDebug.class, "cooldown"));
        }
    }
}

package com.shatteredpixel.shatteredpixeldungeon.actors.buffs;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class RefreshCooldown extends Buff {

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
        icon.hardlight(0xFAFA00);
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

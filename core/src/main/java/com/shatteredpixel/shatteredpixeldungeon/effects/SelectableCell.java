package com.shatteredpixel.shatteredpixeldungeon.effects;

import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.watabou.noosa.Image;

public class SelectableCell extends Image {
    public SelectableCell(Image sprite) {
        super(Icons.get(Icons.TARGET));
        point( sprite.center(this) );
        sprite.parent.addToFront(this);
    }
}

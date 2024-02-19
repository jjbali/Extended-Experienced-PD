package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.custom.dict.DictSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

import java.util.Collection;
import java.util.LinkedHashMap;

public enum ExtendedDictionaryJournal {

    JOURNAL,
    DOCUMENTS,
    UNCLASSIFIED;

    private LinkedHashMap<String, Integer> page = new LinkedHashMap<>();

    public Collection<String> keyList(){return page.keySet();}

    public Collection<Integer> imageList() {return page.values();}

    static {
        //Documents
        DOCUMENTS.page.put("first",       ItemSpriteSheet.GUIDE_PAGE);
        //Journal
        JOURNAL.page.put("tip",       ItemSpriteSheet.CITY_PAGE);
        JOURNAL.page.put("tip_2",       ItemSpriteSheet.ALCH_PAGE);
        JOURNAL.page.put("weapon",       ItemSpriteSheet.SWORD);
        JOURNAL.page.put("armor",       ItemSpriteSheet.ARMOR_CLOTH);
        JOURNAL.page.put("grinding",       ItemSpriteSheet.MAGIC_INFUSE);
    }

}

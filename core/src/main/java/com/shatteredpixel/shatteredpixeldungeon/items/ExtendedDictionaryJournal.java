/*
 *
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * Experienced Pixel Dungeon
 * Copyright (C) 2019-2024 Trashbox Bobylev
 *
 * Extended Experienced Pixel Dungeon
 * Copyright (C) 2023-2024 John Nollas
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
 *
 */

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
        DOCUMENTS.page.put("ballistics",       ItemSpriteSheet.DAGGER_ENERGY);
        DOCUMENTS.page.put("questionnaire",       ItemSpriteSheet.EXOTIC_SCROLL_PLUS);
        DOCUMENTS.page.put("self_explanatory",       ItemSpriteSheet.SOMETHING);
        //Journal
        JOURNAL.page.put("tip",       ItemSpriteSheet.CITY_PAGE);
        JOURNAL.page.put("tip_2",       ItemSpriteSheet.ALCH_PAGE);
        JOURNAL.page.put("weapon",       ItemSpriteSheet.SWORD);
        JOURNAL.page.put("armor",       ItemSpriteSheet.ARMOR_CLOTH);
        JOURNAL.page.put("grinding",       ItemSpriteSheet.MAGIC_INFUSE);

        UNCLASSIFIED.page.put("armor_cloth",         ItemSpriteSheet.ARMOR_CLOTH);
        UNCLASSIFIED.page.put("armor_leather",       ItemSpriteSheet.ARMOR_LEATHER);
        UNCLASSIFIED.page.put("armor_mail",          ItemSpriteSheet.ARMOR_MAIL);
        UNCLASSIFIED.page.put("armor_scale",         ItemSpriteSheet.ARMOR_SCALE);
        UNCLASSIFIED.page.put("armor_plate",         ItemSpriteSheet.ARMOR_PLATE);
        UNCLASSIFIED.page.put("armor_epic",          ItemSpriteSheet.ARMOR_WARRIOR);
    }

}

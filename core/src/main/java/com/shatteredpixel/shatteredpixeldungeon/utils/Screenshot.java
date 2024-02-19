package com.shatteredpixel.shatteredpixeldungeon.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import com.watabou.utils.FileUtils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.zip.Deflater;

import jdk.internal.org.jline.utils.Display;


public class Screenshot {



    public static boolean makeScreenshotEx() {
        FileHandle fh = FileUtils.getFileHandle(Files.FileType.External, "EExpPD-Screenshots/", new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss.SSS", Locale.US).format(Calendar.getInstance().getTime())+".png");

        Pixmap pixmap = Pixmap.createFromFrameBuffer(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        PixmapIO.writePNG(fh, pixmap, Deflater.BEST_COMPRESSION, true);
        pixmap.dispose();
        return true;
    }
    public static boolean makeScreenshotLocal() {
        FileHandle fh = FileUtils.getFileHandle(Files.FileType.Local, "EExpPD-Screenshots/", new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss.SSS", Locale.US).format(Calendar.getInstance().getTime())+".png");

        Pixmap pixmap = Pixmap.createFromFrameBuffer(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        PixmapIO.writePNG(fh, pixmap, Deflater.BEST_COMPRESSION, true);
        pixmap.dispose();
        return true;
    }
}

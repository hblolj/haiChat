package com.jt.wb.ys.common.app;

import android.os.SystemClock;
import android.support.annotation.StringRes;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.io.File;

public class Application extends android.app.Application{

    private static Application instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static File getCacheDirFile(){
        return instance.getCacheDir();
    }

    public static File getPortraitTmpFile(){
        // 得到头像的缓存地址
        File dir = new File(getCacheDirFile(), "portrait");
        // 创建所有的对应的文件夹
        dir.mkdirs();

        File[] files = dir.listFiles();
        if (files != null && files.length > 0){
            for (File file : files) {
                file.delete();
            }
        }

        // 返回一个当前时间戳的目录文件地址
        File path = new File(dir, SystemClock.uptimeMillis() + ".jpg");
        return path.getAbsoluteFile();
    }

    public static File getAudioTmpFile(boolean isTmp) {
        File dir = new File(getCacheDirFile(), "audio");
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

        // aar
        File path = new File(getCacheDirFile(), isTmp ? "tmp.mp3" : SystemClock.uptimeMillis() + ".mp3");
        return path.getAbsoluteFile();
    }

    public static void showToast(final String msg) {
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showToast(@StringRes int msgId) {
        showToast(instance.getString(msgId));
    }
}

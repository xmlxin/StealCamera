package com.xiaoxin.jhang.steal.util;

import com.xiaoxin.jhang.steal.Config;

import java.io.File;

/**
 * @author: xiaoxin
 * date: 2018/5/31
 * describe:
 * 修改内容:
 */

public class FileUtil {

    /**
     * 检查路径是否存在
     */
    public static void isPathExist() {
        File rootFile = new File(Config.PATH) ;
        if(!rootFile.exists()){
            rootFile.mkdirs() ;
        }
        File nomedia = new File(Config.PATH+".nomedia") ;
        if (!nomedia.exists()) {
            nomedia.mkdir();
        }
    }

    public static File picturePath() {
        FileUtil.isPathExist();
        File pictureFile = new File(Config.PATH + "IMG_" + DateUtil.FormatData() + ".jpg");
        return pictureFile;
    }
}

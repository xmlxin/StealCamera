package com.xiaoxin.jhang.steal.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: xiaoxin
 * date: 2018/5/31
 * describe:
 * 修改内容:
 */

public class DateUtil {

    public static String FormatData() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    public static String formatDataToFile() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}

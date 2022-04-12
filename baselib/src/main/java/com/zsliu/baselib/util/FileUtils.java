package com.zsliu.baselib.util;

import java.io.File;

/**
 * title：FileUtils
 * author: zsliu
 * created by Administrator on 2022/2/8 16:03
 * description: 文件工具
 */
public class FileUtils {
    protected static volatile FileUtils fileUtils;

    private FileUtils getInstance() {
        if (fileUtils == null) {
            synchronized (FileUtils.class) {
                if (fileUtils == null) {
                    fileUtils = new FileUtils();
                }
            }
        }
        return fileUtils;
    }
}

package org.spring.springboot.utils;

import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.client.config.utils.ConcurrentDiskUtil;
import com.alibaba.nacos.client.config.utils.IOUtils;
import com.alibaba.nacos.client.config.utils.JVMUtil;
import com.alibaba.nacos.client.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读取本地nacos配置文件
 *
 * @author xiaokai@gooagoo.com
 * @version 2019-11-27
 */
public class LocalFileUtils {
    public static final String LOCAL_SNAPSHOT_PATH = "/home/op/log";

    /**
     * 根据目录层级构建文件
     *
     * @param dataId
     * @param group
     * @param tenant 命名空间
     * @return
     */
    public static File getFailoverFile(String dataId, String group, String tenant) {
        if (group.indexOf(":") != -1) {
            group = group.replaceAll(":", "￥");
        }
        if (dataId.indexOf(":") != -1) {
            dataId = dataId.replaceAll(":", "￥");
        }
        return new File(new File(arrangeFile(tenant), group), dataId);
    }

    /**
     * 读取指定文件的内容
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFile(File file) throws IOException {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        if (JVMUtil.isMultiInstance()) {
            return ConcurrentDiskUtil.getFileContent(file, Constants.ENCODE);
        } else {
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                return IOUtils.toString(is, Constants.ENCODE);
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }
                } catch (IOException e) {
//                    SyncLog.error("readFile().IOException", e);
                }
            }
        }
    }

    /**
     * 根据nacos服务端获取的内容写指定目录下的文件
     *
     * @param dataId
     * @param group
     * @param tenant  命名空间
     * @param content
     */
    public static void writeFile(String dataId, String group, String tenant, String content) {
        if (group.indexOf(":") != -1) {
            group = group.replaceAll(":", "￥");
        }
        if (dataId.indexOf(":") != -1) {
            dataId = dataId.replaceAll(":", "￥");
        }
        File file = new File(arrangeFile(tenant), group);
        //创建文件目录
        if ((!file.exists()) || (!file.isDirectory())) {
            boolean res = file.mkdirs();
            if (res) {
//                SyncLog.info("nacos配置文件目录[" + file.getAbsolutePath() + "]创建成功！");
            }
        }
        try {
            String filePath = file.getAbsolutePath() + File.separator + dataId;
            IOUtils.writeStringToFile(new File(filePath), content, Constants.ENCODE);
//            SyncLog.info("nacos配置文件[" + filePath + "]创建成功！");
        } catch (IOException e) {
//            SyncLog.error("writeFile().IOException", e);
        }
    }

    private static File arrangeFile(String tenant) {
        File tmp = new File(LOCAL_SNAPSHOT_PATH, "nacos");
        tmp = new File(tmp, "data");
        if (StringUtils.isBlank(tenant)) {
            tmp = new File(tmp, "config-data");
        } else {
            tmp = new File(tmp, "tenant-config-data");
            tmp = new File(tmp, tenant);
        }
        return tmp;
    }

}

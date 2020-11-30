package org.spring.springboot.utils;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 配置中心工具类
 *
 * @author tangming
 */
public class NacosUtils {
    @Value("${nacos.config.server-addr}")
    private static String nacosUrl;

    private static ConfigService configService;

    /**
     * 读取配置超时时间，单位 ms
     */
    private static final int TIMEOUT = 1000 * 3;
    /**
     * 获取配置文件内容
     */
    private static String content = "";

    /**
     * 动态读取nocas配置内容
     *
     * @param dataId 配置ID
     * @param group  分组
     * @return
     */
    public static Properties getConfig(String dataId, String group) throws IOException, NacosException {
        Properties properties = null;
        try {
            configService = NacosFactory.createConfigService("192.168.2.100:8848");
            content = configService.getConfig(dataId, group, TIMEOUT);
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    content = configInfo;
//                    SyncLog.info("修改后的配置ID是：[" + dataId + "]，配置分组是：[" + group + "]获取的配置信息是" + content);
                    LocalFileUtils.writeFile(dataId, group, null, content);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });

            if (null != content) {
                LocalFileUtils.writeFile(dataId, group, null, content);
                properties = load(content);
            } else {
                properties = getLocalConfig(dataId, group, null);
            }
        } catch (NacosException e) {
//            SyncLog.error("Nacos读取配置超时或网络异常", e);
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
//            SyncLog.error("加载到properties对象出现IO异常", e);
            throw e;
        }
        return properties;
    }

    /**
     * 获取本地nacos配置文件内容
     *
     * @param dataId /**
     *               获取本地nacos配置文件内容
     * @param dataId
     * @param group
     * @param tenant 命名空间
     * @return
     */
    public static Properties getLocalConfig(String dataId, String group, String tenant) throws IOException {
        Properties properties = null;
        File localPath = LocalFileUtils.getFailoverFile(dataId, group, tenant);
        if (!localPath.exists() || !localPath.isFile()) {
            return null;
        }
        try {
            String config = LocalFileUtils.readFile(localPath);
            properties = load(config);
        } catch (IOException e) {
            e.printStackTrace();
//            SyncLog.error("Get failover error, " + localPath, ioe);
            throw e;
        }
        return properties;
    }

    private static Properties load(String content) throws IOException {
        Properties properties = new Properties();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        properties.load(byteArrayInputStream);
        return properties;
    }

}

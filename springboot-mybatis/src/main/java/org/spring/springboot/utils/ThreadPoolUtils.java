package org.spring.springboot.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述: 线程池工具类
 *
 * @param:
 * @return:
 * @auther: 李旭辉
 * @date: 2019/3/20/020 13:36
 */
public class ThreadPoolUtils
{
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolUtils.class);
    /**
     * CPU数量*2+4
     */
    public static final int CPU_NUMS = Runtime.getRuntime().availableProcessors() * 2 + 4;

    protected static final ThreadPoolExecutor fixedThreadPool;

    static {
        log.info("", "【线程池日志】创建开始...");
        fixedThreadPool = new ThreadPoolExecutor(CPU_NUMS, CPU_NUMS, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        log.info("", "【线程池日志】创建结束..., 创建线程数为： " + CPU_NUMS);

    }

    public static void execute(String logUUID,Runnable command) {
        fixedThreadPool.execute(command);
        log.info(logUUID, "【线程池日志】当前线程池中线程数目：" + fixedThreadPool.getPoolSize() + "，无界队列中等待执行的任务数目：" + fixedThreadPool.getQueue().size() + "，已执行完的任务数目：" + fixedThreadPool.getCompletedTaskCount());
    }

    public static void execute(Runnable command) {
        fixedThreadPool.execute(command);
        log.info("", "【线程池日志】当前线程池中线程数目：" + fixedThreadPool.getPoolSize() + "，无界队列中等待执行的任务数目：" + fixedThreadPool.getQueue().size() + "，已执行完的任务数目：" + fixedThreadPool.getCompletedTaskCount());
    }




}

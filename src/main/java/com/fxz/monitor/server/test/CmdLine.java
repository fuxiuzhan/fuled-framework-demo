package com.fxz.monitor.server.test;

import com.fxz.fuled.dynamic.threadpool.ThreadPoolRegistry;
import com.fxz.fuled.dynamic.threadpool.manage.impl.DefaultThreadExecuteHook;
import com.fxz.fuled.dynamic.threadpool.wrapper.QueueWrapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.concurrent.*;

@Component
public class CmdLine implements ApplicationRunner {

    static sun.misc.Unsafe UNSAFE = null;
    static long offset;

    static {
        try {
            UNSAFE = getUnsafeInstance();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.HOURS, new ArrayBlockingQueue<>(1024));
        modifyFinal(threadPoolExecutor, "workQueue", new ArrayBlockingQueue<>(1024));
        System.out.println("version->" + ThreadPoolRegistry.class.getPackage().getImplementationVersion());
    }

    private static void modifyFinal(Object object, String fieldName, Object newFieldValue) throws Exception {
        Field field = null;
        if (object.getClass().equals(ThreadPoolExecutor.class)) {
            field = object.getClass().getDeclaredField(fieldName);
        } else if (object.getClass().equals(ScheduledThreadPoolExecutor.class)) {
            //获取父类
            field = object.getClass().getSuperclass().getDeclaredField(fieldName);
        }
        offset = UNSAFE.objectFieldOffset(field);
        Object object1 = UNSAFE.getObject(object, offset);
        BlockingQueue queueWrapper = QueueWrapper.wrapper((BlockingQueue) object1, new DefaultThreadExecuteHook(), "test");
        UNSAFE.putObject(object, offset, queueWrapper);
        System.out.println(System.getProperty("java.version"));
        System.out.println("a");
    }

    private static sun.misc.Unsafe getUnsafeInstance()
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field theUnsafeInstance = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (sun.misc.Unsafe) theUnsafeInstance.get(sun.misc.Unsafe.class);
    }
}

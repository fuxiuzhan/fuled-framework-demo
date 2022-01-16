package com.fxz.monitor.server.listener;

import com.fxz.monitor.server.executor.ThreadPoolWrapper;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author fxz
 */
public class ClassLoaderWrapper extends URLClassLoader {
    public ClassLoaderWrapper(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("ClassLoaderWrapper findClass->" + name);
        if ("java.util.concurrent.ThreadPoolExecutor".equalsIgnoreCase(name)) {
            System.out.println("ThreadPoolExecutorWrapped");
            return ThreadPoolWrapper.class;
        }
        return super.findClass(name);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        System.out.println("ClassLoaderWrapper loadClass->" + name);
        return loadClass(name, false);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        System.out.println("ClassLoaderWrapper loadClass->" + name + " resolve->" + resolve);
        if ("java.util.concurrent.ThreadPoolExecutor".equalsIgnoreCase(name)) {
            System.out.println("ThreadPoolExecutorWrapped");
            return ThreadPoolWrapper.class;
        }
        return super.loadClass(name, resolve);
    }
}

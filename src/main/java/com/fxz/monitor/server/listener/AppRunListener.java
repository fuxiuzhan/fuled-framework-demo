package com.fxz.monitor.server.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;

import java.net.URLClassLoader;

/**
 * @author fxz
 */
public class AppRunListener implements SpringApplicationRunListener {
    SpringApplication sa;
    String[] args;

    public AppRunListener(SpringApplication sa, String[] args) {
        this.sa = sa;
        this.args = args;
    }

    @Override
    public void starting() {
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(urlClassLoader.getURLs(), urlClassLoader);
        try {
            classLoaderWrapper.loadClass("java.util.concurrent.ThreadPoolExecutor");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Thread.currentThread().setContextClassLoader(classLoaderWrapper);
    }
}

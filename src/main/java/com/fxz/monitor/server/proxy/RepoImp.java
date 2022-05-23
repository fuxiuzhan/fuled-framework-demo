package com.fxz.monitor.server.proxy;

public class RepoImp implements IRepo {
    @Override
    public RepoPojo findById(String id) {
        RepoPojo repoPojo = new RepoPojo();
        repoPojo.setName("dubbo-name");
        return repoPojo;
    }
}

package service.impl;

import service.MyService;

public class MyServiceImplB implements MyService {
    private int id = 2;
    private String name = "ServiceB";

    @Override
    public void startService(){
        System.out.println("Starting serviceB...");
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

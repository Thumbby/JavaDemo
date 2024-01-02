package service.impl;

import service.MyService;

public class MyServiceImplA implements MyService {
    private int id = 1;
    private String name = "ServiceA";
    @Override
    public void startService(){
        System.out.println("Starting serviceA...");
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

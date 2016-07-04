package com.cornucopia.di.dagger2;

public class D2ServiceManager implements D2Service {

    @Override
    public String greet(String user) {
        return "hello " + user + " from d2."; 
    }

}

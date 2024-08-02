package com.mccoy;

public class Idle implements State {
    @Override
    public void update(StatefulObject so) {
        System.out.println(so.getObjectDetails().name + " is in Idle");
    }
}

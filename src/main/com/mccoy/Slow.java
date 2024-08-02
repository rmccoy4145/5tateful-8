package com.mccoy;

public class Slow implements StatusEffect {
    int duration = 10;

    @Override
    public boolean isComplete() {
        return duration <= 0;
    }

    @Override
    public void impact(StatefulObject so) {
        System.out.println(so.getObjectDetails().name + " is slowed");
        duration--;
    }
}

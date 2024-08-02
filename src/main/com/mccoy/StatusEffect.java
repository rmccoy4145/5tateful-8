package com.mccoy;

public interface StatusEffect {
    boolean isComplete();

    void impact(StatefulObject so);
}

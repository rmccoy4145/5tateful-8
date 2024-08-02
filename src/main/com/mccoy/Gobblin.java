package com.mccoy;

import java.util.ArrayList;

public class Gobblin implements StatefulObject {
    ObjectDetails objectDetails;
    TransformController transformController = new TransformController();
    ArrayList<StatefulObject> children = new ArrayList<>();
    ArrayList<StatusEffect> statusEffects = new ArrayList<>();
    State currentState;

    public Gobblin() {
        ObjectDetails od = new ObjectDetails();
        od.name = "gobblin";
        objectDetails = od;
        TransformController tc = new TransformController();
        tc.size = new int[]{10, 10};
        currentState = new Idle();
    }

    @Override
    public void changeState(State newState) {
        this.currentState = newState;
    }

    @Override
    public State getState() {
        return currentState;
    }

    @Override
    public ObjectDetails getObjectDetails() {
        return this.objectDetails;
    }

    @Override
    public TransformController getTransformController() {
        return this.transformController;
    }

    @Override
    public ArrayList<StatefulObject> getChildren() {
        return this.children;
    }

    @Override
    public ArrayList<StatusEffect> getStatusEffects() {
        return this.statusEffects;
    }
}

package com.mccoy;

import java.util.ArrayList;

public interface StatefulObject {
    void changeState(State state);

    State getState();

    ObjectDetails getObjectDetails();

    TransformController getTransformController();

    ArrayList<StatefulObject> getChildren();

    ArrayList<StatusEffect> getStatusEffects();

    default void update(double delta) {
        getState().update(this);
        getChildren().forEach(soChild -> soChild.update(delta));
        getStatusEffects().forEach((statusEffect -> statusEffect.impact(this)));
        getStatusEffects().removeIf(StatusEffect::isComplete);
    }
}

package com.mccoy;

import java.util.ArrayList;

public class main {

    public interface StatefulObject {
        void changeState(State state);
        State getState();
        ObjectDetails getObjectDetails();
        TransformController getTransformController();
        ArrayList<StatefulObject> getChildren();
        ArrayList<StatusEffect> getStatusEffects();

        default void update() {
            getState().update(this);
            getChildren().forEach(StatefulObject::update);
            getStatusEffects().forEach((statusEffect -> statusEffect.impact(this)));
            getStatusEffects().removeIf(StatusEffect::isComplete);
        }
    }

    public interface State {
        void update(StatefulObject so);
    }

    public static class ObjectDetails {
        String name;
    }

    public static class TransformController {
        int[] position = new int[]{0,0};
        int[] size = new int[]{0,0};
    }

    public interface StatusEffect {
        boolean isComplete();
        void impact(StatefulObject so);
    }

    public static class Idle implements State {
        @Override
        public void update(StatefulObject so) {
            System.out.println(so.getObjectDetails().name + " is in Idle");
        }
    }

    public static class Slow implements StatusEffect {
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

    public static class Gobblin implements StatefulObject {
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
            tc.size = new int[]{10,10};
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

    public static void main(String[] args) {
        ArrayList<StatefulObject> world = new ArrayList<>();

        Gobblin gobblin = new Gobblin();
        gobblin.statusEffects.add(new Slow());

        world.add(gobblin);

        while(true) {
            world.forEach(statefulObject -> {
                statefulObject.update();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

    }

}

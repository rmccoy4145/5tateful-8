package com.mccoy;

import java.util.ArrayList;

public class Engine {
    private boolean running = true;
    private final int TARGET_FPS = 60;
    private final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS; // Nanoseconds per frame
    ArrayList<StatefulObject> world = new ArrayList<>();

    public void start() {
        long lastLoopTime = System.nanoTime();

        Gobblin gobblin = new Gobblin();
        gobblin.statusEffects.add(new Slow());

        world.add(gobblin);

        while (running) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;

            double delta = updateLength / ((double)OPTIMAL_TIME);

            // Update the game logic
            update(delta);

            // Render the game
            render();

            // Sleep for the remainder of the frame time if necessary
            long sleepTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void update(double delta) {
        world.forEach(statefulObject -> {
            statefulObject.update(delta);
        });
    }

    private void render() {
        // Render the game here
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.start();
    }

}

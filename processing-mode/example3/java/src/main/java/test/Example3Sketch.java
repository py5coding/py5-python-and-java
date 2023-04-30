package test;

import py5.core.SketchBase;

/**
 * Flocking + Clustering
 * original Flocking Sketch by Daniel Shiffman.
 * 
 * An implementation of Craig Reynold's Boids program to simulate
 * the flocking behavior of birds. Each boid steers itself based on
 * rules of avoidance, alignment, and coherence.
 * 
 * Click the mouse to add a new boid.
 */

public class Example3Sketch extends SketchBase {

  protected Flock flock;

  public void settings() {
    size(640, 360);
  }

  public void setup() {
    flock = new Flock();
    // Add an initial set of boids into the system
    for (int i = 0; i < 150; i++) {
      flock.addBoid(new Boid(this, width / 2, height / 2));
    }
  }

  public void draw() {
    background(50);
    flock.run();
  }

  // Add a new boid into the System
  public void mousePressed() {
    flock.addBoid(new Boid(this, mouseX, mouseY));
  }

}

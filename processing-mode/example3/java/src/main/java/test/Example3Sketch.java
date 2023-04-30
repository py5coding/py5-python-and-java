package test;

import py5.core.SketchBase;

/**
 * Flocking + Clustering
 * original Flocking Sketch by Daniel Shiffman.
 * 
 * In this adaptation of Dan Shiffman's Flocking example, the boids are
 * repeatedly clustered using the KMeans algorithm from scikit-learn. Each Boid
 * will ignore Boids in other clusters. The cluster labels are used to give the
 * boids in each cluster a unique color.
 * 
 * An implementation of Craig Reynold's Boids program to simulate
 * the flocking behavior of birds. Each boid steers itself based on
 * rules of avoidance, alignment, and coherence.
 * 
 * Click the mouse to add a new boid.
 */

public class Example3Sketch extends SketchBase {

  protected Flock flock;
  protected int[] clusterColorsLUT;

  public void settings() {
    size(640, 360);
  }

  public void setup() {
    // the double cast in the next line is necessary because Python numbers
    // are longs in Java but we want an int to initialize the array size
    int clusterCount = (int) (long) callPython("cluster_count");
    // initialize group color lookup table
    // boids in the same cluster will have the same color
    clusterColorsLUT = new int[clusterCount];
    colorMode(SketchBase.HSB, 360, 100, 100);
    for (int i = 0; i < clusterCount; i++) {
      clusterColorsLUT[i] = color(random(360), 80, 80);
    }

    flock = new Flock();
    // Add an initial set of boids into the system
    for (int i = 0; i < 150; i++) {
      flock.addBoid(new Boid(this, clusterColorsLUT, this.random(width), this.random(height)));
    }

    // start a thread to repeatedly cluster the boids. a thread is used here
    // because the clustering algorithm would slow down the draw() method and
    // reduce the frame rate to a crawl.
    thread("runClustering");
  }

  public void draw() {
    background(50);
    flock.run();
  }

  // Add a new boid into the System
  public void mousePressed() {
    flock.addBoid(new Boid(this, clusterColorsLUT, mouseX, mouseY));
  }

  public void runClustering() {
    while (true) {
      float[][] boidLocations = flock.getBoidLocations();
      int[] boidClusterLabels = (int[]) callPython("cluster_boids", (Object) boidLocations);
      flock.setBoidClusterLabels(boidClusterLabels);
    }
  }

}

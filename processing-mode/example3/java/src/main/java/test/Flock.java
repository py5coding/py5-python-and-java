package test;

import java.util.ArrayList;

// The Flock (a list of Boid objects)

class Flock {

  protected ArrayList<Boid> boids; // An ArrayList for all the boids

  public Flock() {
    boids = new ArrayList<Boid>(); // Initialize the ArrayList
  }

  protected void run() {
    for (Boid b : boids) {
      b.run(boids); // Passing the entire list of boids to each boid individually
    }
  }

  protected void addBoid(Boid b) {
    boids.add(b);
  }

  protected float[][] getBoidLocations() {
    float[][] boidsArray = new float[boids.size()][2];
    for (int i = 0; i < boids.size(); i++) {
      boidsArray[i][0] = boids.get(i).position.x;
      boidsArray[i][1] = boids.get(i).position.y;
    }
    return boidsArray;
  }

  public void setBoidClusterLabels(int[] boidClusterLabels) {
    for (int i = 0; i < boidClusterLabels.length; i++) {
      boids.get(i).setClusterLabel(boidClusterLabels[i]);
    }
  }

}

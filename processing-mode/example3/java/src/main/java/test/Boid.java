package test;

import java.util.ArrayList;

import processing.core.PVector;
import py5.core.SketchBase;

// The Boid class

class Boid {

  protected SketchBase sketch;

  protected PVector position;
  protected PVector velocity;
  protected PVector acceleration;
  protected int clusterLabel;
  protected int[] clusterColorsLUT;
  protected float r;
  protected float maxforce; // Maximum steering force
  protected float maxspeed; // Maximum speed

  public Boid(SketchBase sketch, int[] clusterColorsLUT, float x, float y) {
    this.sketch = sketch;
    this.clusterColorsLUT = clusterColorsLUT;
    acceleration = new PVector(0, 0);

    // This is a new PVector method not yet implemented in JS
    // velocity = PVector.random2D();

    // Leaving the code temporarily this way so that this example runs in JS
    float angle = sketch.random(SketchBase.TWO_PI);
    velocity = new PVector(SketchBase.cos(angle), SketchBase.sin(angle));

    position = new PVector(x, y);
    r = 2.0f;
    maxspeed = 2;
    maxforce = 0.03f;
  }

  public void setClusterLabel(int clusterLabel) {
    this.clusterLabel = clusterLabel;
  }

  public void run(ArrayList<Boid> boids) {
    flock(boids);
    update();
    borders();
    render();
  }

  protected void applyForce(PVector force) {
    // We could add mass here if we want A = F / M
    acceleration.add(force);
  }

  // We accumulate a new acceleration each time based on three rules
  protected void flock(ArrayList<Boid> boids) {
    PVector sep = separate(boids); // Separation
    PVector ali = align(boids); // Alignment
    PVector coh = cohesion(boids); // Cohesion
    // Arbitrarily weight these forces
    sep.mult(1.5f);
    ali.mult(1.0f);
    coh.mult(1.0f);
    // Add the force vectors to acceleration
    applyForce(sep);
    applyForce(ali);
    applyForce(coh);
  }

  // Method to update position
  protected void update() {
    // Update velocity
    velocity.add(acceleration);
    // Limit speed
    velocity.limit(maxspeed);
    position.add(velocity);
    // Reset accelertion to 0 each cycle
    acceleration.mult(0);
  }

  // A method that calculates and applies a steering force towards a target
  // STEER = DESIRED MINUS VELOCITY
  protected PVector seek(PVector target) {
    PVector desired = PVector.sub(target, position); // A vector pointing from the position to the target
    // Scale to maximum speed
    desired.normalize();
    desired.mult(maxspeed);

    // Above two lines of code below could be condensed with new PVector setMag()
    // method
    // Not using this method until Processing.js catches up
    // desired.setMag(maxspeed);

    // Steering = Desired minus Velocity
    PVector steer = PVector.sub(desired, velocity);
    steer.limit(maxforce); // Limit to maximum steering force
    return steer;
  }

  protected void render() {
    // Draw a triangle rotated in the direction of velocity
    float theta = velocity.heading() + SketchBase.radians(90);

    sketch.fill(200, 100);
    sketch.stroke(clusterColorsLUT[clusterLabel]);
    sketch.pushMatrix();
    sketch.translate(position.x, position.y);
    sketch.rotate(theta);
    sketch.beginShape(SketchBase.TRIANGLES);
    sketch.vertex(0, -r * 2);
    sketch.vertex(-r, r * 2);
    sketch.vertex(r, r * 2);
    sketch.endShape();
    sketch.popMatrix();
  }

  // Wraparound
  protected void borders() {
    if (position.x < -r)
      position.x = sketch.width + r;
    if (position.y < -r)
      position.y = sketch.height + r;
    if (position.x > sketch.width + r)
      position.x = -r;
    if (position.y > sketch.height + r)
      position.y = -r;
  }

  // Separation
  // Method checks for nearby boids and steers away
  protected PVector separate(ArrayList<Boid> boids) {
    float desiredseparation = 25.0f;
    PVector steer = new PVector(0, 0, 0);
    int count = 0;
    // For every boid in the system, check if it's too close
    for (Boid other : boids) {
      float d = PVector.dist(position, other.position);
      // If the distance is greater than 0 and less than an arbitrary amount (0 when
      // you are yourself)
      if ((d > 0) && (d < desiredseparation)) {
        // Calculate vector pointing away from neighbor
        PVector diff = PVector.sub(position, other.position);
        diff.normalize();
        diff.div(d); // Weight by distance
        steer.add(diff);
        count++; // Keep track of how many
      }
    }
    // Average -- divide by how many
    if (count > 0) {
      steer.div((float) count);
    }

    // As long as the vector is greater than 0
    if (steer.mag() > 0) {
      // First two lines of code below could be condensed with new PVector setMag()
      // method
      // Not using this method until Processing.js catches up
      // steer.setMag(maxspeed);

      // Implement Reynolds: Steering = Desired - Velocity
      steer.normalize();
      steer.mult(maxspeed);
      steer.sub(velocity);
      steer.limit(maxforce);
    }
    return steer;
  }

  // Alignment
  // For every nearby boid in the system, calculate the average velocity
  protected PVector align(ArrayList<Boid> boids) {
    PVector sum = new PVector(0, 0);
    int count = 0;
    for (Boid other : boids) {
      // ignore boids in other clusters
      if (other.clusterLabel != clusterLabel) {
        continue;
      }
      float d = PVector.dist(position, other.position);
      if (d > 0) {
        sum.add(other.velocity);
        count++;
      }
    }
    if (count > 0) {
      sum.div((float) count);
      // First two lines of code below could be condensed with new PVector setMag()
      // method
      // Not using this method until Processing.js catches up
      // sum.setMag(maxspeed);

      // Implement Reynolds: Steering = Desired - Velocity
      sum.normalize();
      sum.mult(maxspeed);
      PVector steer = PVector.sub(sum, velocity);
      steer.limit(maxforce);
      return steer;
    } else {
      return new PVector(0, 0);
    }
  }

  // Cohesion
  // For the average position (i.e. center) of all nearby boids, calculate
  // steering vector towards that position
  protected PVector cohesion(ArrayList<Boid> boids) {
    PVector sum = new PVector(0, 0); // Start with empty vector to accumulate all positions
    int count = 0;
    for (Boid other : boids) {
      // ignore boids in other clusters
      if (other.clusterLabel != clusterLabel) {
        continue;
      }
      float d = PVector.dist(position, other.position);
      if (d > 0) {
        sum.add(other.position); // Add position
        count++;
      }
    }
    if (count > 0) {
      sum.div(count);
      return seek(sum); // Steer towards the position
    } else {
      return new PVector(0, 0);
    }
  }

}

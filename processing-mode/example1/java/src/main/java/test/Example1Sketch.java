package test;

import processing.core.PImage;
import py5.core.SketchBase;

public class Example1Sketch extends SketchBase {

  public void settings() {
    size(400, 400, P2D);
  }

  public void setup() {
    String msg = "Hello from Java!";
    PImage img = createImage(200, 200, RGB);

    // call Python function `alter_image(msg, img)` and get back a PImage object
    PImage imgResponse = (PImage) callPython("test_transfer", msg, img);
    image(imgResponse, 100, 100);

    // call numpy `random.randint()` function
    long randomNumber = (long) callPython("np.random.randint", 0, 100);
    py5Println("JAVA: Random number from numpy: " + randomNumber);
  }

  public void draw() {
    rect(mouseX, mouseY, 20, 20);
  }

}

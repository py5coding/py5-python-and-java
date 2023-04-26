package test;

import processing.core.PImage;
import py5.core.SketchBase;

public class Example2Sketch extends SketchBase {

  protected TestInterface test;

  public void settings() {
    size(400, 400, P2D);
  }

  public void setup() {
    rectMode(CENTER);

    test = (TestInterface) callPython("setup_test_interface");

    String message = "Hello from Java!";
    PImage pimage = createImage(150, 150, RGB);
    PImage imageResponse = test.passImage(message, pimage);
    if (imageResponse != null) {
      image(imageResponse, 100, 100);
    }

  }

  public void draw() {
    rect(mouseX, mouseY, 20, 20);
  }

}

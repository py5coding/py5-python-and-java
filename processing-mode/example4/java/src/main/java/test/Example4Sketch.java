/**
 * Edge Detection Example
 * 
 * Canny edge detection algorithm adapted from:
 * https://pyimagesearch.com/2015/04/06/zero-parameter-automatic-canny-edge-detection-with-python-and-opencv/
 * 
 * Press 't', 'w', or any other key to set the edge detection mode.
 */
package test;

import processing.core.PImage;
import py5.core.SketchBase;

public class Example4Sketch extends SketchBase {

  protected PImage img;
  protected PImage imgFiltered;
  protected String mode;

  public void settings() {
    size(640, 360);
  }
  
  public void setup() {
    mode = "auto";
    img = loadImage("moon.jpg");
    imgFiltered = createImage(img.width, img.height, RGB);

    py5Println("Press 't' for tight edge detection");
    py5Println("Press 'w' for wide edge detection");
    py5Println("Press any other key for auto edge detection");

    callPython("filter_image", img, imgFiltered, mode);
  }

  public void draw() {
    image(img, 0, 0);
    image(imgFiltered, width/2, 0);
  }

  public void keyPressed() {
    if (key == 't') {
      mode = "tight";
    } else if (key == 'w') {
      mode = "wide";
    } else {
      mode = "auto";
    }
    py5Println("Edge Detection Mode set to: " + mode);
    callPython("filter_image", img, imgFiltered, mode);
  }

}

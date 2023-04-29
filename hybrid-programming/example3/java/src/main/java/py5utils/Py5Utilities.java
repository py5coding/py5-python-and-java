package py5utils;

import py5.core.Sketch;

public class Py5Utilities {

  protected Sketch sketch;

  public Py5Utilities(Sketch sketch) {
    // This constructor is called before the sketch starts running. DO NOT use
    // Processing methods here, as they may not work correctly.
    this.sketch = sketch;
  }

  public void drawHappyFace(float x, float y, int color) {
    drawHappyFace(x, y, color, 100);
  }

  public void drawHappyFace(float x, float y, int color, float size) {
    sketch.pushMatrix();
    sketch.pushStyle();

    // set style
    sketch.noStroke();
    sketch.fill(color);

    // position face
    sketch.translate(x, y);
    sketch.scale(size / 100.0f);
    sketch.translate(-50, -50);

    // head
    sketch.ellipse(50, 50, 80, 80);

    // eyes and nose
    sketch.fill(0);
    sketch.ellipse(35, 40, 15, 15);
    sketch.ellipse(65, 40, 15, 15);
    sketch.ellipse(50, 55, 10, 10);

    // mouth
    sketch.stroke(0);
    sketch.noFill();
    sketch.strokeWeight(3);
    sketch.arc(50, 50, 50, 50, Sketch.radians(20), Sketch.radians(160));

    sketch.popStyle();
    sketch.popMatrix();
  }
}

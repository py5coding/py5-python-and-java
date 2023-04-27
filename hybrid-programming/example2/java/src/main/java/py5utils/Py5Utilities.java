package py5utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import processing.core.PShape;
import py5.core.Sketch;

public class Py5Utilities {

  public Sketch sketch;

  protected PShape shape;
  protected IntBuffer colors;
  protected FloatBuffer points;

  public Py5Utilities(Sketch sketch) {
    this.sketch = sketch;
  }

  public void shareBuffers(IntBuffer colors, FloatBuffer points) {
    // share the direct buffers created with Python code
    this.colors = colors;
    this.points = points;
  }

  public void drawColoredPoints() {
    // iterate through the colors and points direct buffers to draw
    // multicolored points
    sketch.pushStyle();
    sketch.beginShape(Sketch.POINTS);
    for (int i = 0; i < colors.capacity() / 3; i++) {
      sketch.stroke(colors.get(i));
      sketch.vertex(points.get(i * 2), points.get(i * 2 + 1));
    }
    sketch.endShape();
    sketch.popStyle();
  }

}

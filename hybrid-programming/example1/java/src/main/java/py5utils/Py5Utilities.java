package py5utils;

import py5.core.Sketch;

public class Py5Utilities {

  public Sketch sketch;

  public Py5Utilities(Sketch sketch) {
    // This constructor is called before the sketch starts running. DO NOT use
    // Processing methods here, as they may not work correctly.
    this.sketch = sketch;
  }

  public void drawColoredPoints(int[][] colors, float[][] coords) {
    // iterate through the colors and coords arrays to draw multicolored points
    sketch.pushStyle();
    sketch.beginShape(Sketch.POINTS);
    for (int i = 0; i < colors.length; i++) {
      sketch.stroke(colors[i][0], colors[i][1], colors[i][2]);
      sketch.vertex(coords[i][0], coords[i][1]);
    }
    sketch.endShape();
    sketch.popStyle();
  }

}

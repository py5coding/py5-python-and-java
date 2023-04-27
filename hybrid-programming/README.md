# Hybrid Programming

Hybrid Programming refers to py5’s ability to augment your py5 Sketch with Java code. This is very much like creating custom Processing extensions to enhance py5.

[Hybrid Programming](http://py5coding.org/content/hybrid_programming.html) is documented on py5's website. The first two examples are code samples from the online documentation.

Here is a quick example of a py5 `draw()` method that uses Hybrid Programming:

```python
def draw():
    colors = (255 * np.random.rand(N, 3)).astype(np.int32)
    points = np.random.rand(N, 2).astype(np.float32) * [py5.width, py5.height]
    # call Py5Utilities Java method
    py5.utils.drawColoredPoints(colors, points)
```

The `drawColoredPoints()` method is not a builtin method in py5; it is a user-created extension implemented in a special Java `Py5Utilities` class:

```java
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
```

The main idea is that any methods added to this Java `Py5Utilities` class will be made available to you through the `py5.utils` attribute. Use of this feature can greatly enhance the performance and capabilities of your py5 Python code.

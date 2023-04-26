# py5 Hybrid Programming & Processing Mode Example Code

This repo contains example code for those looking to experiment with py5's hybrid programming and processing mode functionality.

## What is py5?

## Hybrid Programming

Hybrid Programming refers to py5’s ability to augment your py5 Sketch with Java code. This is very much like creating custom Processing extensions to enhance py5.

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
    this.sketch = sketch;
  }

  public void drawColoredPoints(int[][] colors, float[][] coords) {
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

The main idea is that any methods added to this Java `Py5Utilities` class will be made available to you through the `py5.utils` attribute. Use of this feature can greatly enhance the performance and capabilities of your py5 Sketch code.

[Hybrid Programming is documented on py5's website.](http://py5coding.org/content/hybrid_programming.html)

## Processing Mode

Processing Mode refers to py5’s ability to serve as a bridge from Java to Python, allowing Processing Sketches to call Python code using a new `callPython()` method. This is a solid feature that in time will add a significant amount of value to the Processing community.

Here is a quick example of a Processing Sketch that uses py5 in Processing Mode:

```java
package test;

import processing.core.PImage;
import py5.core.SketchBase;

public class Example1Sketch extends SketchBase {

  public void settings() {
    size(400, 400, P2D);
  }

  public void setup() {
    rectMode(CENTER);

    String msg = "Hello from Java!";
    PImage img = createImage(200, 200, RGB);

    PImage imgResponse = (PImage) callPython("test_transfer", msg, img);
    image(imgResponse, 100, 100);

    long randomNumber = (long) callPython("np.random.randint", 0, 100);
    py5Println("JAVA: Random number from numpy: " + randomNumber);
  }

  public void draw() {
    rect(mouseX, mouseY, 20, 20);
  }

}
```

The `callPython()` method in this Sketch is added to Processing with py5's `py5.core.SketchBase` class. It can pass parameters and return objects back to Java.

The Python code used to execute the above example Sketch is the following:

```python
import numpy as np

import py5_tools
import py5


def alter_image(msg: str, img: py5.Py5Image):
    py5.println("PYTHON:", msg)
    py5.println("PYTHON:", img)

    img.load_np_pixels()
    img.np_pixels[::2, ::2] = [255, 255, 0, 0]
    img.update_np_pixels()

    return img


py5_tools.register_processing_mode_key('test_transfer', alter_image)
py5_tools.register_processing_mode_key('np', np)

py5.run_sketch(jclassname='test.Example1Sketch')
```

The main idea is for py5 to provide Processing users with efficient access to the Python Ecosystem. Calls to `callPython()` in Java are linked to Python through registered "keys" that map to Python functions, callables, and modules.

[Processing Mode is documented on py5's website.](http://py5coding.org/content/processing_mode.html)

## Example Code Setup


# Processing Mode

Processing Mode refers to py5’s ability to serve as a bridge from Java to Python, allowing Processing Sketches to call Python code using a new `callPython()` method. This is a solid feature that in time will add a significant amount of value to the Processing community.

[Processing Mode](http://py5coding.org/content/processing_mode.html) is documented on py5's website. The first two examples are code samples from the online documentation.

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
    String msg = "Hello from Java!";
    PImage img = createImage(200, 200, RGB);

    // call Python function `alter_image(msg, img)` and get back a PImage
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


# register processing mode keys so the Java `callPython()` method can find them
py5_tools.register_processing_mode_key('test_transfer', alter_image)
py5_tools.register_processing_mode_key('np', np)

# run the sketch in processing mode, specifying the Java class to instantiate
py5.run_sketch(jclassname='test.Example1Sketch')
```

The main idea is for py5 to provide Processing users with efficient access to the Python Ecosystem. Calls to `callPython()` in Java are linked to Python through registered "keys" that map to Python functions and modules. Refer to the online [Processing Mode](http://py5coding.org/content/processing_mode.html) documentation to learn more.

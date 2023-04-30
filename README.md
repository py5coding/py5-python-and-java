# py5 Hybrid Programming & Processing Mode Example Code

This repo contains example code for those looking to experiment with py5's Hybrid Programming and Processing Mode functionality. Both Hybrid Programming and Processing Mode allow you to combine Python and Processing code in new and exciting ways.

## What is py5?

py5 is a version of [**Processing**][processing] for Python 3.8+. It makes the Java [**Processing**][processing] jars available to the CPython interpreter using [**JPype**][jpype]. It can do just about all of the 2D and 3D drawing [**Processing**][processing] can do, except with Python instead of Java code.

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

[Hybrid Programming](http://py5coding.org/content/hybrid_programming.html) is documented on py5's website.

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

The main idea is for py5 to provide Processing users with efficient access to the Python Ecosystem. Calls to `callPython()` in Java are linked to Python through registered "keys" that map to Python functions and modules.

[Processing Mode](http://py5coding.org/content/processing_mode.html) is documented on py5's website.

## Example Code Setup

Here's how to use this GitHub repo.

### Create Anaconda Environment

If you have [Anaconda][anaconda] or [Miniconda][miniconda] installed, you can create the Anaconda environment with this command:

```bash
conda env create -f environment.yml
```

This will install py5 and some libraries used in the example code.

### Install Maven

You will need to [install Maven](https://maven.apache.org/install.html) if you wish to edit and compile the example Java code. This repo provides compiled Jars for those who have difficulty with this step.

Theoretically you can install Maven through Anaconda but installing it that way didn't work during my tests so it is not recommended.

### Explore Code in IDE

Before proceeding, you should confirm that your IDE recognizes the Java projects in this repo. The Maven configuration files, `pom.xml`, link to the necessary Jar files using system paths. The configuration files also depend on the Anaconda environment variable `CONDA_PREFIX`. If you are not using Anaconda, the environment variable `CONDA_PREFIX` will not be defined. If you are using VSCode, you will need to use the palette command "Python: Select Interpreter" to select the Python interpreter for your environment.

The Jar files are located in the Jars that come with your py5 installation. If you are not using an Anaconda environment or you deviated from the provided `environment.yml` file, you will probably need to alter the `pom.xml` files.

Check the `pom.xml` files carefully if your IDE is not recognizing or properly compiling the Java code.

If you know more about Maven and have a better idea of how to organize this aspect of this repo, please tell us about it in [py5's GitHub discussions][py5_github_discussions].

### Experiment With Example Code

Once your IDE and environment are setup correctly, start experimenting with what you can do with py5's Hybrid Programming and Processing Mode. Share your creations with us in [py5's GitHub discussions][py5_github_discussions] or on social media with the hashtag `#py5`. If something is confusing to you, please let us know! We are happy to add more examples to make any of these concepts more clear.

## Get In Touch

Have a comment or question? We'd love to hear from you! The best ways to reach out are:

* github [discussions](https://github.com/py5coding/py5generator/discussions) and [issues](https://github.com/py5coding/py5generator/issues)
* Mastodon <a rel="me" href="https://fosstodon.org/@py5coding">fosstodon.org/@py5coding</a>
* twitter [@py5coding](https://twitter.com/py5coding)
* [processing foundation discourse](https://discourse.processing.org/)

[py5_github_discussions]: https://github.com/py5coding/py5generator/discussions
[processing]: https://github.com/processing/processing4
[jpype]: https://github.com/jpype-project/jpype

[anaconda]: https://docs.anaconda.com/anaconda/install/
[miniconda]: https://conda.io/projects/conda/en/stable/user-guide/install/index.html

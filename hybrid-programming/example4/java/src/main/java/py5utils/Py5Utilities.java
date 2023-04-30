package py5utils;

import processing.core.PShape;
import py5.core.Sketch;

public class Py5Utilities {

  protected Sketch sketch;

  public Py5Utilities(Sketch sketch) {
    // This constructor is called before the sketch starts running. DO NOT use
    // Processing methods here, as they may not work correctly.
    this.sketch = sketch;
  }

  public PShape createTorus(float radius, float tube, int radialSegments, int tubularSegments) {
    // Torus algorithm adapted from Three.js (https://threejs.org/).
    // https://github.com/mrdoob/three.js/blob/dev/src/geometries/TorusGeometry.js
    float vertices[][] = new float[(radialSegments + 1) * (tubularSegments + 1)][3];
    for (int i = 0; i <= radialSegments; i++) {
      float phi = i * Sketch.TWO_PI / radialSegments;
      float sin_phi = Sketch.sin(phi);
      float cos_phi = Sketch.cos(phi);

      for (int j = 0; j <= tubularSegments; j++) {
        float theta = j * Sketch.TWO_PI / tubularSegments;

        vertices[i * (tubularSegments + 1) + j][0] = (radius + tube * cos_phi) * Sketch.cos(theta);
        vertices[i * (tubularSegments + 1) + j][1] = (radius + tube * cos_phi) * Sketch.sin(theta);
        vertices[i * (tubularSegments + 1) + j][2] = tube * sin_phi;
      }
    }

    PShape torus = sketch.createShape();
    torus.beginShape(Sketch.QUADS);
    for (int i = 1; i <= radialSegments; i++) {
      for (int j = 1; j <= tubularSegments; j++) {
        int a = (tubularSegments + 1) * i + j - 1;
        int b = (tubularSegments + 1) * (i - 1) + j - 1;
        int c = (tubularSegments + 1) * (i - 1) + j;
        int d = (tubularSegments + 1) * i + j;

        torus.vertex(vertices[a][0], vertices[a][1], vertices[a][2]);
        torus.vertex(vertices[b][0], vertices[b][1], vertices[b][2]);
        torus.vertex(vertices[c][0], vertices[c][1], vertices[c][2]);
        torus.vertex(vertices[d][0], vertices[d][1], vertices[d][2]);
      }
    }
    torus.endShape();

    return torus;
  }

}

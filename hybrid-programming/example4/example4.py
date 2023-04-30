# Draw Torus Example
#
# Torus geometry algorithm adapted from Three.js (https://threejs.org/).
# https://github.com/mrdoob/three.js/blob/dev/src/geometries/TorusGeometry.js

import py5

# Import the PeasyCam library (a Java library, not a Python library)
# PeasyCam was created by Jonathan Feinberg (https://mrfeinberg.com/peasycam/)
# JPype lets you import Java libraries into Python as you would import a Python library
from peasy import PeasyCam  # type: ignore


torus = None

def setup():
    global torus
    py5.size(500, 500, py5.P3D)
    py5.smooth(4)

    cam = PeasyCam(py5.get_current_sketch(), 500)

    torus = py5.utils.createTorus(200, 40, 20, 50)

    py5.stroke(0)
    py5.stroke_weight(3)
    py5.fill(255)


def draw():
    py5.background(128)
    py5.shape(torus)


py5.run_sketch()

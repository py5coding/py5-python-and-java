import py5

from peasy import PeasyCam


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

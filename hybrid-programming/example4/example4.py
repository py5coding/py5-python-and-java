import py5


torus = None

def setup():
    global torus
    py5.size(500, 500, py5.P3D)
    py5.smooth(4)

    torus = py5.utils.createTorus(200, 25, 20, 40)

    py5.stroke(0)
    py5.stroke_weight(3)
    py5.fill(255)


def draw():
    py5.background(128)
    py5.translate(py5.width / 2, py5.height / 2, 0)
    py5.shape(torus)


py5.run_sketch()

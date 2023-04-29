import py5


def setup():
    py5.size(500, 500, py5.P2D)


def draw():
    for _ in range(3):
        size = py5.random_int(50, 150)
        color = py5.color(py5.random_int(0, 255), py5.random_int(0, 255), py5.random_int(0, 255))
        # call Py5Utilities Java method to draw a happy face
        py5.utils.drawHappyFace(py5.random(py5.width), py5.random(py5.height), color, size)


py5.run_sketch()

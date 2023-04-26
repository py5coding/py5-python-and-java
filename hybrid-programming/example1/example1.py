import numpy as np
import py5


N = 10_000


def setup():
    py5.size(500, 500, py5.P2D)


def draw():
    colors = (255 * np.random.rand(N, 3)).astype(np.int32)
    points = np.random.rand(N, 2).astype(np.float32) * [py5.width, py5.height]
    # call Py5Utilities Java method
    py5.utils.drawColoredPoints(colors, points)


py5.run_sketch()

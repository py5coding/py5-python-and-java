import numpy as np
import jpype
import py5


N = 100_000

# create Direct Buffers
points_buffer = jpype.nio.convertToDirectBuffer(bytearray(4 * N * 2)).asFloatBuffer()
byte_buffer = jpype.nio.convertToDirectBuffer(bytearray(4 * N))
colors_buffer = byte_buffer.asIntBuffer()

# create numpy arrays backed by the Direct Buffers
colors = np.asarray(byte_buffer).reshape(N, 4)
points = np.asarray(points_buffer).reshape(N, 2)

# Note that the `colors_buffer` is like an array of 32 bit Integers, which is
# how Processing stores color values. The `colors` array is an array of unsigned
# integers ranging from 0 to 255. The third dimension of the `colors` array
# stores the alpha, red, green, and blue color values, in that order.

def setup():
    py5.size(500, 500, py5.P2D)
    # call Py5Utilities Java method
    py5.utils.shareBuffers(colors_buffer, points_buffer)
    colors[:, 0] = 255


def draw():
    colors[:, 1:] = np.random.randint(0, 255, (N, 3), dtype=np.uint8)
    points[:, 0] = np.random.randint(0, py5.width, N, dtype=np.int32)
    points[:, 1] = np.random.randint(0, py5.height, N, dtype=np.int32)
    # call Py5Utilities Java method
    py5.utils.drawColoredPoints()


py5.run_sketch()

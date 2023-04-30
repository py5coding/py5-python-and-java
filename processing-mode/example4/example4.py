# Edge Detection Example
#
# Canny edge detection algorithm adapted from:
# https://pyimagesearch.com/2015/04/06/zero-parameter-automatic-canny-edge-detection-with-python-and-opencv/
#
# Press 't', 'w', or any other key to set the edge detection mode.

import traceback

import numpy as np
import cv2

from jpype import JClass

import py5_tools
import py5


def filter_image(img: py5.Py5Image, img_filtered: py5.Py5Image, mode: str):
    try:
        img.load_np_pixels()

        if mode == 'auto':
            sigma = 0.33
            median = np.median(img.np_pixels)
            lower_limit = int(max(0, (1 - sigma) * median))
            upper_limit = int(min(255, (1 + sigma) * median))
        elif mode == 'tight':
            lower_limit, upper_limit = 225, 250
        elif mode == 'wide':
            lower_limit, upper_limit = 10, 200

        img_array_gray = cv2.cvtColor(img.np_pixels, cv2.COLOR_BGR2GRAY)
        img_array_blurred = cv2.GaussianBlur(img_array_gray, (5, 5), 0)
        img_array_edges = cv2.Canny(
            img_array_blurred, lower_limit, upper_limit)

        img_filtered.set_np_pixels(img_array_edges, bands='L')
    except Exception as e:
        traceback.print_exc()
        return JClass('java.lang.RuntimeException')(str(e))


py5_tools.register_processing_mode_key("filter_image", filter_image)


# run the sketch in processing mode, specifying the Java class to instantiate
py5.run_sketch(jclassname='test.Example4Sketch')

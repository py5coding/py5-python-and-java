import traceback

import py5_tools
import py5
from py5 import object_conversion


def cluster_boids():
    pass


py5_tools.register_processing_mode_key("cluster_boids", cluster_boids)


# run the sketch in processing mode, specifying the Java class to instantiate
py5.run_sketch(jclassname='test.Example3Sketch')

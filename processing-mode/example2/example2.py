import traceback

from jpype import JImplements, JOverride, JClass

import py5_tools
import py5
from py5 import object_conversion


# tell JPype this class implements the Java Interface test.TestInterface
@JImplements('test.TestInterface')
class Test:

    # tell JPype this method implements method in the Java Interface
    @JOverride
    def passImage(self, message, pimage):
        try:
            py5.println(f"PYTHON: message type is {type(message)}")
            py5.println(f"PYTHON: message is {message}")

            py5.println(f"PYTHON: img type is {type(pimage)}")

            # convert Java PImage object to Python Py5Image object
            py5image = object_conversion.convert_to_python_type(pimage)

            py5.println(f"PYTHON: py5image type is {type(py5image)}")
            py5.println(f"PYTHON: py5image is {py5image}")

            new_py5image = py5.create_image(200, 200, py5.RGB)
            new_py5image.load_np_pixels()
            new_py5image.np_pixels[::2, ::2] = [255, 255, 0, 0]
            new_py5image.update_np_pixels()

            # convert Python Py5Image object to Java PImage object
            return object_conversion.convert_to_java_type(new_py5image)
        except Exception as e:
            traceback.print_exc()
            return JClass('java.lang.RuntimeException')(str(e))


# register processing mode keys so the Java `callPython()` method can find them
py5_tools.register_processing_mode_key('setup_test_interface', lambda: Test())

# run the sketch in processing mode, specifying the Java class to instantiate
py5.run_sketch(jclassname='test.Example2Sketch')

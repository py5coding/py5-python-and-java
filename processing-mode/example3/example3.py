import traceback

from jpype import JClass

from sklearn.cluster import KMeans

import py5_tools
import py5


NUMBER_OF_CLUSTERS = 8
cluster_centers = None


def get_cluster_count():
    # the number of clusters should be set in one place for both the Python and Java code
    return NUMBER_OF_CLUSTERS


def cluster_boids(boid_locations):
    global cluster_centers

    try:
        init_arg = 'k-means++' if cluster_centers is None else cluster_centers
        kmeans = KMeans(n_clusters=NUMBER_OF_CLUSTERS, random_state=0, init=init_arg, n_init=1).fit(boid_locations)
        # save the cluster centers to initialize clustering in the next invocation
        # this keeps cluster labels stable from one call to the next
        cluster_centers = kmeans.cluster_centers_.copy()
        return kmeans.labels_
    except Exception as e:
        traceback.print_exc()
        return JClass('java.lang.RuntimeException')(str(e))


py5_tools.register_processing_mode_key("cluster_boids", cluster_boids)
py5_tools.register_processing_mode_key("cluster_count", get_cluster_count)


# run the sketch in processing mode, specifying the Java class to instantiate
py5.run_sketch(jclassname='test.Example3Sketch')

# Flocking + Clustering
# original Flocking Sketch by Daniel Shiffman.
# 
# In this adaptation of Dan Shiffman's Flocking example, the boids are
# repeatedly clustered using the KMeans algorithm from scikit-learn. Each Boid
# will ignore Boids in other clusters, but each Boid will also change cluster
# membership over time. The cluster labels are used to give the boids in each
# cluster a unique color.
# 
# An implementation of Craig Reynold's Boids program to simulate
# the flocking behavior of birds. Each boid steers itself based on
# rules of avoidance, alignment, and coherence.
# 
# Click the mouse to add a new boid.

import traceback

from jpype import JClass

from sklearn.cluster import KMeans

import py5_tools
import py5


NUMBER_OF_CLUSTERS = 8
cluster_centers = None


def cluster_count():
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


# py5_tools.register_processing_mode_key("cluster_boids", cluster_boids)
# py5_tools.register_processing_mode_key("cluster_count", cluster_count)


# run the sketch in processing mode, specifying the Java class to instantiate
py5.run_sketch(jclassname='test.Example3Sketch')

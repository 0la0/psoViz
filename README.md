Particle Swarm Optimization Visualization
=================

A Java program for visualizing a basic implementation of the [Particle Swarm Optimization](http://en.wikipedia.org/wiki/Particle_swarm_optimization).

The basic algorithm was created from the pseudocode from [this tutorial](http://www.swarmintelligence.org/tutorials.php). 

###Usage:

To view and interact with the 2D version:

Compile: 
```Shell
javac -d bin src/pso/* src/driver2d/*
```

Run:
```Shell
java -cp ./bin driver2d/Init2D
```

The 3D version runs on the [lwjgl](http://www.lwjgl.org/).
<div align="center">
  <img src="images/logo.png" alt="Project Logo" width="100%"/>
</div>

<h1 align="center">Fast Matrix Multiplication Solutions</h1>

This Java library provides implementations for matrix operations in parallel and distributed computing environments, making use of java executor services, MapReduce, and HazelCast, to deploy the work in a cluster of machines. You'll find below some explanations for the different components of the library, starting with the model definition, where matrices themselves are defined.

(*) For efficiency aspects, please check [Benchmarks of Parallel tile Multiplication](Benchmarks_of_Parallel_tile_Multiplication.pdf)

## Matrices

### Dense Matrix

A dense matrix is a two-dimensional array of elements, where each element occupies a specific position indexed by two integers. In this library, the `DenseMatrix` class represents dense matrices.

```java
DenseMatrix denseMatrix = new DenseMatrix(size, matrix);
```

### Block Matrix
A block matrix is a matrix that is partitioned into smaller matrices, often called blocks. Each block can be manipulated independently, allowing for parallel processing. The `BlockMatrix` class in this library represents block matrices.

```java
BlockMatrix blockMatrix = new BlockMatrix(size, matrixMap);
```

## Matrix Builders
### DenseMatrixBuilder
The `DenseMatrixBuilder` class constructs dense matrices. It takes a size parameter and sets values at specified coordinates.

```java
DenseMatrixBuilder matrixBuilder = new DenseMatrixBuilder(size);
matrixBuilder.set(new Coordinate(0, 0), value1);
matrixBuilder.set(new Coordinate(0, 1), value2);
...

DenseMatrix denseMatrix = matrixBuilder.get();
```

### BlockMatrixBuilder
The `BlockMatrixBuilder` class constructs block matrices. It takes a size parameter and sets dense matrices at specified coordinates.

```java
MatrixBuilder matrixBuilder = new BlockMatrixBuilder(size);
matrixBuilder.set(new Coordinate(0, 0), denseMatrix1);
matrixBuilder.set(new Coordinate(0, 1), denseMatrix2);
...

BlockMatrix blockMatrix = (BlockMatrix) matrixBuilder.get();
```

## Operators
### Matrix Transformers
Matrix transformers are classes that convert matrices from one form to another.

`Transform2BlockMatrix`: The Transform2BlockMatrix class transforms a dense matrix into a block matrix.

```java
Transform2BlockMatrix transformer = new Transform2BlockMatrix();
BlockMatrix blockMatrix = transformer.execute(denseMatrix);
```

`Transform2DenseMatrix`: The Transform2DenseMatrix class transforms a block matrix into a dense matrix.

```java
Transform2DenseMatrix transformer = new Transform2DenseMatrix();
DenseMatrix denseMatrix = transformer.execute(blockMatrix);
```

### Matrix Multipliers
Matrix multipliers are classes that perform matrix multiplication.

`DenseMatrixMultiplication`: The DenseMatrixMultiplication class performs multiplication for dense matrices.

```java
MatrixMultiplication multiplier = new DenseMatrixMultiplication();
DenseMatrix result = multiplier.multiply(matrixA, matrixB);
```

`BlockMatrixMultiplication`: The BlockMatrixMultiplication class performs multiplication for block matrices.

```java
MatrixMultiplication multiplier = new BlockMatrixMultiplication();
        BlockMatrix result = multiplier.multiply(matrixA, matrixB);
```

`ParallelBlockMatrixMultiplication`: The ParallelBlockMatrixMultiplication class performs multiplication for block matrices in parallel, taking advantage of your CPU cores.

```java
MatrixMultiplication multiplier = new ParallelBlockMatrixMultiplication();
BlockMatrix result = multiplier.multiply(matrixA, matrixB);
```

You can pass both Dense and Block matrices, as the share the same Matrix interface which is the one you may pass to this function. If you directly two dense matrices, they will be divided into blocks in such a way no cores of your CPU are idle. If you have two block matrices, make sure the were constructed using this values for size and block size, respectively.

```java
int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
int SIZE = AVAILABLE_PROCESSORS;
int BLOCK_SIZE = 2048/AVAILABLE_PROCESSORS;
```

Or reshape them using transformers as

```java
Transform2DenseMatrix transformer2Dense = new Transform2DenseMatrix();
DenseMatrix denseMatrix = transformer2Dense.execute(blockMatrix);

Transform2BlockMatrix transform2Block = new Transform2BlockMatrix();
BlockMatrix blockAfterTransform = transform2Block.execute(denseMatrix);
```

## HazelCast: Distributed Matrix Multiplication

This approach make use of HazelCast to split the multiplication process in different computers or application servers. Each end-point at the HazelCast network apply parallelism to compute its multiplication, making use of `ExecutorService` as `ParallelBlockMatrixMultiplication` class does. Here's an example of use:

```java
// CLIENT: Waits for orchestrator to multiply the corresponding matrices
DistributeMultiplicationClient client = new DistributeMultiplicationClient();
client.start();
```

```java
// ORCHESTRATOR: splits the work for different clients, including orchestrator it-self
DistributedMultiplicationOrchestrator orchestrator = new DistributedMultiplicationOrchestrator();
Matrix result = orchestrator.multiply(matrixA, matrixB);
```

These two could co-exists in the same machine, even though it will show an under-performance when compared to simple parallelism. However, you should make sure that clients are running before the orchestrator. Otherwise, the orchestrator will take the whole work.  

## MapReduce Matrix Multiplication

MapReduce multiplication has also been included in this library as a way for multiplying matrices in a cluster of computers. Here you have an example of use:

```java
DenseMatrix matrixA = buildDenseMatrix(128);
DenseMatrix matrixB = buildDenseMatrix(128);

MapReduceMatrixMultiplication multiplier = new MapReduceMatrixMultiplication();
multiplier.multiply(matrixA, matrixB);
```

To be able to run this code properly, make sure you have defined the following environment variables, according to your hadoop installation.

```
hadoop.home.dir=C:\hadoop-3.2.2
HADOOP_HOME=C:\hadoop-3.2.2
Path=C:\hadoop-3.2.2\bin
```

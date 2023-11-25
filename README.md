# <b>ParaBlock</b>: Parallel Tile Matrix Multiplication

This Java library provides implementations for matrix operations in parallel and distributed computing environments. You'll find bellow some explanations for the different components of the library, starting by the model definition, where matrices it-self are defined.

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
BlockMatrixBuilder matrixBuilder = new BlockMatrixBuilder(size);
matrixBuilder.set(new Coordinate(0, 0), denseMatrix1);
matrixBuilder.set(new Coordinate(0, 1), denseMatrix2);
...

BlockMatrix blockMatrix = matrixBuilder.get();
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
MatrixMultiplication multiplier = new DoubleBlockMatrixMultiplication();
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
// CLIENT
DistributeMultiplicationClient client = new DistributeMultiplicationClient();
client.start();
```

```java
// ORCHESTRATOR
Orchestrator distributedMultiplicationOrchestrator = new DistributedMultiplicationOrchestrator();
Matrix result = Orchestrator.multiply(matrixA, matrixB);
```

These two could co-exists in the same method, even though it will show an under-performance when comparing to simple parallelism. However, you should make sure that clients are running before orchestrator. Otherwise, orchestrator will take the whole work.  



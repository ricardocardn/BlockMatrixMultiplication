# <b>ParaBlock</b>: Parallel Tile Matrix Multiplication

This Java library provides implementations for matrix operations in parallel computing environments. Below, you'll find explanations for the different components of the library.

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

`DoubleBlockMatrixMultiplication`: The DoubleBlockMatrixMultiplication class performs multiplication for block matrices with Double elements.

```java
MatrixMultiplication multiplier = new DoubleBlockMatrixMultiplication();
BlockMatrix result = multiplier.multiply(matrixA, matrixB);
```

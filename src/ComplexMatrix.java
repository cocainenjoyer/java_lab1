
public class ComplexMatrix {
    private final ComplexNumber[][] matrix;

    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows and columns must be positive integers.");
        }
        matrix = new ComplexNumber[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = new ComplexNumber(0, 0);
            }
        }
    }

    public void set(int row, int col, ComplexNumber value) {
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length) {
            throw new IndexOutOfBoundsException("Row or column index is out of bounds.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value must not be null.");
        }
        matrix[row][col] = value;
    }

    public ComplexNumber get(int row, int col) {
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length) {
            throw new IndexOutOfBoundsException("Row or column index is out of bounds.");
        }
        return matrix[row][col];
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (other == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        if (this.matrix.length != other.matrix.length || this.matrix[0].length != other.matrix[0].length) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for addition.");
        }
        ComplexMatrix result = new ComplexMatrix(this.matrix.length, this.matrix[0].length);
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                result.set(i, j, this.get(i, j).sum(other.get(i, j)));
            }
        }
        return result;
    }

    public ComplexMatrix subtract(ComplexMatrix other) {
        if (other == null) {
            throw new IllegalArgumentException("Argument must not be null");
        }
        if (this.matrix.length != other.matrix.length || this.matrix[0].length != other.matrix[0].length) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for subtraction.");
        }
        ComplexMatrix result = new ComplexMatrix(this.matrix.length, this.matrix[0].length);
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[0].length; j++) {
                result.set(i, j, this.get(i, j).subtract(other.get(i, j)));
            }
        }
        return result;
    }

    public ComplexNumber determinant() {
        return determinant(matrix);
    }

    private ComplexNumber determinant(ComplexNumber[][] mat) {
        int n = mat.length;
        if (n == 1) {
            return mat[0][0];
        }

        if (n == 2) {
            return mat[0][0].mult(mat[1][1]).subtract(mat[0][1].mult(mat[1][0]));
        }

        ComplexNumber det = new ComplexNumber(0, 0);
        for (int j = 0; j < n; j++) {
            ComplexNumber sign = (j % 2 == 0) ? new ComplexNumber(1, 0) : new ComplexNumber(-1, 0);
            ComplexNumber[][] subMat = new ComplexNumber[n - 1][n - 1];
            for (int row = 1; row < n; row++) {
                for (int col = 0; col < n; col++) {
                    if (col < j) {
                        subMat[row - 1][col] = mat[row][col];
                    } else if (col > j) {
                        subMat[row - 1][col - 1] = mat[row][col];
                    }
                }
            }
            det = det.sum(sign.mult(mat[0][j]).mult(determinant(subMat)));
        }
        return det;
    }

    public ComplexMatrix transpose() {
        int rows = matrix.length;
        int cols = matrix[0].length;
        ComplexMatrix transposed = new ComplexMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed.set(j, i, matrix[i][j]);
            }
        }
        return transposed;
    }

    private ComplexMatrix adjugate() {
        int n = matrix.length;
        ComplexMatrix adjugate = new ComplexMatrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ComplexNumber sign = ((i + j) % 2 == 0) ? new ComplexNumber(1, 0) : new ComplexNumber(-1, 0);
                ComplexNumber[][] subMat = new ComplexNumber[n - 1][n - 1];
                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++) {
                        if (row != i && col != j) {
                            int newRow = row < i ? row : row - 1;
                            int newCol = col < j ? col : col - 1;
                            subMat[newRow][newCol] = matrix[row][col];
                        }
                    }
                }
                adjugate.set(j, i, sign.mult(determinant(subMat)));
            }
        }
        return adjugate;
    }

    public ComplexMatrix inverse() {
        ComplexNumber det = determinant();
        if (det.re == 0 && det.im == 0) {
            throw new IllegalArgumentException("Matrix is singular and cannot be inverted.");
        }

        ComplexMatrix adjugate = adjugate();
        ComplexMatrix inverse = new ComplexMatrix(matrix.length, matrix[0].length);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                inverse.set(i, j, adjugate.get(i, j).mult(1.0 / det.re));
            }
        }
        return inverse;
    }

    public ComplexMatrix mult(ComplexMatrix other) {
        if (this.matrix[0].length != other.matrix.length) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }
        ComplexMatrix result = new ComplexMatrix(this.matrix.length, other.matrix[0].length);
        for (int i = 0; i < result.matrix.length; i++) {
            for (int j = 0; j < result.matrix[0].length; j++) {
                ComplexNumber sum = new ComplexNumber(0, 0);
                for (int k = 0; k < this.matrix[0].length; k++) {
                    sum = sum.sum(this.matrix[i][k].mult(other.matrix[k][j]));
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        if (other == null) {
            throw new IllegalArgumentException("Other matrix must not be null.");
        }
        ComplexMatrix inverseOther = other.inverse();
        return mult(inverseOther);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ComplexNumber[] row : matrix) {
            for (ComplexNumber cn : row) {
                sb.append(cn.toString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inverse3x3matrix;

import java.util.Arrays;

/**
 *
 * @author gavan
 */
public class Inverse3x3Matrix {

    private static int[][] transposeMatrix(int[][] matrix) {
        // get dimensions
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        // create transposedMatrix
        int transposedMatrix[][] = new int[numCols][numRows];

        // for every row of matrix
        for (int i = 0; i < numRows; i++) {
            // for every col of matrix
            for (int j = 0; j < numCols; j++) {
                for (int k = 0; k < numRows; k++) {
                    // use i=rowMatrix and j = colMatrix
                    transposedMatrix[j][i] = matrix[i][j];
                }
            }
        }
        return transposedMatrix;
    }

    private static int calc2x2Determinant(int[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }

    private static int calc3x3Determinant(int[][] matrix) {
        int numElements = matrix.length * matrix[0].length; // 9 elements in the matrix
        int sideLength = matrix.length; // 3x3 matrix
        int determinant = 0; // store the determinant sum as we calculate it

        // for all elements in first row of matrix
        for (int i = 0; i < sideLength; i++) {

            /*
            If we flatten the matrix into a 1D array, then we can calculate the
            row and column of the i-th element of the 1D array
             */
            int row = Math.floorDiv(i, sideLength);
            int col = i % sideLength;

            // we get the scalar in our original array using the row and col we
            // calculated
            int scalar = matrix[row][col];

            /* we use minorIndex for the following:
            i) To keep track of our position in the matrix of minors as we fill
            in the values
            ii) To terminate our inner for loop once minorMatrix is full
             */
            int minorIndex = 0;
            int minorMatrix[][] = new int[2][2];

            // this loop constructs the matrix of minors for each element a i
            for (int j = 0; j < numElements; j++) {
                // the matrix of minors only has 4 elements
                if (minorIndex > 3) {
                    break;
                }

                // the element itself can't be in the matrix of minors
                if (j == i) {
                    continue;
                }

                // convert our 1D index, j, to a 2D row and col
                int currRow = Math.floorDiv(j, sideLength);
                int currCol = j % sideLength;

                // ignore the row and column that contain our element at index i
                if (currRow == row || currCol == col) {
                    continue;
                }

                // we convert the 1D value of minorIndex to the
                // 2D row and column equivalent
                int minorMatrixRow = Math.floorDiv(minorIndex, minorMatrix.length);
                int minorMatrixCol = minorIndex % minorMatrix.length;

                // add the element to the matrix of minors
                minorMatrix[minorMatrixRow][minorMatrixCol] = matrix[currRow][currCol];

                // increment minorIndex, ready to add the next element
                minorIndex++;

            }

            // we use i to determine the sign of the minor
            if (i % 2 == 0) {
                determinant += calc2x2Determinant(minorMatrix) * scalar;
            } else {
                determinant += -(calc2x2Determinant(minorMatrix) * scalar);
            }
        }
        return determinant;

    }

    private static void constructCofactorMatrix(int[][] matrix) {
        int numElements = matrix.length * matrix[0].length; // 9 elements in the matrix
        int sideLength = matrix.length; // 3x3 matrix
        int determinant = 0; // store the determinant sum as we calculate it
        int[][] cofactor3x3Matrix = new int[sideLength][sideLength];
        int cofactor3x3MatrixIndex = 0;

        // for all elements of matrix
        for (int i = 0; i < numElements; i++) {

            /*
            If we flatten the matrix into a 1D array, then we can calculate the
            row and column of the i-th element of the 1D array
             */
            int row = Math.floorDiv(i, sideLength);
            int col = i % sideLength;

            // we get the scalar in our original array using the row and col we
            // calculated
            int scalar = matrix[row][col];

            /* we use minorIndex for the following:
            i) To keep track of our position in the matrix of minors as we fill
            in the values
            ii) To terminate our inner for loop once minorMatrix is full
             */
            int minorIndex = 0;
            int minorMatrix[][] = new int[2][2];

            // this loop constructs the matrix of minors for each element at i
            for (int j = 0; j < numElements; j++) {
                // the matrix of minors only has 4 elements
                if (minorIndex > 3) {
                    break;
                }

                // the element itself can't be in the matrix of minors
                if (j == i) {
                    continue;
                }

                // convert our 1D index, j, to a 2D row and col
                int currRow = Math.floorDiv(j, sideLength);
                int currCol = j % sideLength;

                // ignore the row and column that contain our element at index i
                if (currRow == row || currCol == col) {
                    continue;
                }

                // we convert the 1D value of minorIndex to the
                // 2D row and column equivalent
                int minorMatrixRow = Math.floorDiv(minorIndex, minorMatrix.length);
                int minorMatrixCol = minorIndex % minorMatrix.length;

                // add the element to the matrix of minors
                minorMatrix[minorMatrixRow][minorMatrixCol] = matrix[currRow][currCol];

                // increment minorIndex, ready to add the next element
                minorIndex++;

            }

            // we convert the 1D value of cofactor3x3MatrixIndex to the
            // 2D row and column equivalent
            int minor3x3Row = Math.floorDiv(cofactor3x3MatrixIndex, cofactor3x3Matrix.length);
            int minor3x3Col = cofactor3x3MatrixIndex % cofactor3x3Matrix.length;

            // calc determinant
            determinant = calc2x2Determinant(minorMatrix);

            // add to matrix and apply place sign immediately
            // i.e. this is the matrix of co-factors
            if (cofactor3x3MatrixIndex % 2 == 0) {

                cofactor3x3Matrix[minor3x3Row][minor3x3Col] = determinant;
            } else {
                cofactor3x3Matrix[minor3x3Row][minor3x3Col] = -1 * determinant;

            }

            cofactor3x3MatrixIndex++;
        }
        System.out.println("determinant of 3x3 matrix: " + Arrays.deepToString(cofactor3x3Matrix));

        // transpose the matrix of cofactors:
        int[][] matrixTransposed = transposeMatrix(cofactor3x3Matrix);
    }

    private static int[][] constructInverseMatrix() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int matrix[][] = {{1, 2, 3}, {4, 1, 5}, {6, 0, 2}};
        int anotherMatrix[][] = {{2, -3, 1}, {5, -1, 2}, {3, 2, -1}};
        int yetAnotherMatrix[][] = {{5, -1, 3}, {7, 2, 4}, {6, 0, 1}};

        int determinant = calc3x3Determinant(anotherMatrix);

        System.out.println("3x3 det: " + determinant);

        // test constructing minor matrix
        constructCofactorMatrix(anotherMatrix);
    }

}

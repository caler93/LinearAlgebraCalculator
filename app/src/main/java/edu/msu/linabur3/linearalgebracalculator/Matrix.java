package edu.msu.linabur3.linearalgebracalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Class to represent a matrix and associated operations
 */
public class Matrix {

    /**
     * The number of rows for the matrix
     */
    private int rowSize = 2;

    /**
     * The number of columns for this matrix
     */
    private int colSize = 3;

    /**
     * 2-dimensional array to hold matrix
     */
    private Rational map[][];

    /**
     * Get the value at this spot in matrix
     */
    public Rational getVal(int n, int m){
        return map[n][m];
    }

    /**
     * Update value in array with number passed as argument
     */
    public void setVal(int n, int m, Rational val) {map[n][m] = val;}

    /**
     * Get the number of rows for this matrix
     *
     * @return int the number of rows for this matrix
     */
    public int getRowSize() {
        return rowSize;
    }

    /**
     * Set the number of columns for this matrix
     *
     * @param rowSize the number of columns for this matrix
     */
    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    /**
     * Get the number of columns for this matrix
     *
     * @return int the number of columns for this matrix
     */
    public int getColSize() {
        return colSize;
    }

    /**
     * Set the number of rows for this matrix
     *
     * @param colSize the number of rows for this matrix
     */
    public void setColSize(int colSize) {
        this.colSize = colSize;
    }

    /**
     * Constructor
     *
     * constructor for matrix object
     *
     * @param context the context for the app
     */
    public Matrix(Context context) {

        // Create memory
        map = new Rational[rowSize][colSize];

        // Initialize matrix to be empty
        for(int i = 0; i < rowSize; i++)
            for(int j = 0; j < colSize; j++)
                map[i][j] = new Rational(0);

    }

    /**
     * Constructor with arguments
     *
     * constructor for matrix object
     *
     * @param n the rowsize of the matrix
     * @param m the column size of the matrix
     */
    public Matrix(int n, int m) {

        rowSize = n;
        colSize = m;

        // Create memory
        map = new Rational[rowSize][colSize];

        // Initialize matrix to be empty
        for(int i = 0; i < rowSize; i++)
            for(int j = 0; j < colSize; j++)
                map[i][j] = new Rational(0);

    }

    /**
     * Add a row to the Matrix
     */
    public void addRow()
    {
        // Create new matrix with 1 extra row
        Rational new_map[][];
        new_map = new Rational[rowSize+1][colSize];

        // Copy elements over
        for(int i = 0; i < rowSize; i++)
            for(int j = 0; j < colSize; j++)
                new_map[i][j] = map[i][j];

        // Add new row of zeroes
        for(int i = 0; i < colSize; i++)
            new_map[rowSize][i] = new Rational(0);

            map = new_map;
            rowSize = rowSize + 1;
    }

    /**
     * Remove a row from the matrix
     */
    public void removeRow()
    {
        // Error Check: Do not have less than or equal to zero rows or columns
        if (rowSize <= 1)
            return;

        // Create new matrix with 1 extra row
        Rational new_map[][];
        new_map = new Rational[rowSize-1][colSize];

        // Copy elements over
        for(int i = 0; i < rowSize - 1; i++)
            for(int j = 0; j < colSize; j++)
                new_map[i][j] = map[i][j];

        map = new_map;
        rowSize = rowSize - 1;
    }

    /**
     * Add a column to the Matrix
     */
    public void addColumn()
    {
        // Create new matrix with 1 extra column
        Rational new_map[][];
        new_map = new Rational[rowSize][colSize + 1];

        // Copy elements over
        for(int i = 0; i < rowSize; i++)
            for(int j = 0; j < colSize; j++)
                new_map[i][j] = map[i][j];

        // Add new column of zeroes
        for(int i = 0; i < rowSize; i++)
            new_map[i][colSize] = new Rational(0);

        map = new_map;
        colSize = colSize + 1;
    }

    /**
     * Remove a column from the matrix
     */
    public void removeColumn()
    {
        // Error Check: Do not have less than or equal to zero rows or columns
        if (colSize <= 1)
            return;

        // Create new matrix with 1 less column
        Rational new_map[][];
        new_map = new Rational[rowSize][colSize - 1];

        // Copy elements over
        for(int i = 0; i < rowSize; i++)
            for(int j = 0; j < colSize - 1; j++)
                new_map[i][j] = map[i][j];

        map = new_map;
        colSize = colSize - 1;
    }

    /**
     * Compute the reduced row echelon form of this matrix and
     */
    public Matrix rref()
    {
        // Algorithm adapted from RosettaCode.org

        // Declare variables needed
        int i = 0, j = 0, r = 0, lead = 0;
        Matrix mat;

        // Create copy of current matrix
        // Create new matrix with 1 less column
        mat = new Matrix(rowSize, colSize);

        for(i = 0; i < rowSize; i++)
            for(j = 0; j < colSize; j++)
                mat.map[i][j] = map[i][j];

        // Lead and r start at 0
        while (r < rowSize && colSize > lead)
        {
            // Find if swap is needed
            i = r;
            while (mat.map[i][lead].isEqual(0))
            {
                i++;
                if (rowSize == i)
                {
                    i = r;
                    lead++;
                    if (colSize == lead)
                        return mat;
                }
            }

            int pos = 0;

            // Swap rows i and r
            for (pos = 0; pos < colSize; pos++)
            {
                Rational swapper;
                swapper = mat.map[i][pos];
                mat.map[i][pos] = mat.map[r][pos];
                mat.map[r][pos] = swapper;
            }

            // Divide row r by the matrix at M[r][lead] if it is not zero
            if (mat.map[r][lead].notEqual(0))
            {
                pos = 0;
                Rational val = mat.map[r][lead];
                for (pos = 0; pos < colSize; pos++)
                    mat.map[r][pos] = mat.map[r][pos].divide(val);
            }

            // Subtract M[r][lead] multiplied by row r from row i
            for (i = 0; i < rowSize; i++)
            {
                if (i != r)
                {
                    pos = 0;
                    Rational val = mat.map[i][lead];
                    for (pos = 0; pos < colSize; pos++)
                        mat.map[i][pos] = mat.map[i][pos].subtract((val.multiply(mat.map[r][pos])));
                }

            }

            // Increment for next iteration
            lead = lead + 1;
            r++;
        }

        // Return the rref form of this matrix
        return mat;
    }

    /**
     * Compute the inverse of this matrix and return
     *
     * @return Pointer to matrix that is this matrix's inverse
     */
    public Matrix inverse()
    {
        // Create temporary matrix to hold inverse
        Matrix temp;
        int i, j;

        // Copy elements over and create space for identity matrix
        temp = new Matrix(rowSize, 2 * colSize);

        // Copy elements over
        for(i = 0; i < rowSize; i++)
            for(j = 0; j < colSize; j++)
                temp.map[i][j] = map[i][j];

        // Fill other half of matrix with identity matrix
        for (i = 0; i < rowSize; i++)
            for (j = colSize; j < colSize * 2; j++)
            {
                if (j - colSize == i)
                    temp.map[i][j] = new Rational(1);
                else
                    temp.map[i][j] = new Rational(0);
            }

        // Compute reduced row echelon form of this matrix to obtain inverse on the right half
        temp = temp.rref();

        // Isolate right half of result and return, this is inverse
        Matrix inverse;
        inverse = new Matrix(rowSize, colSize);
        for (i = 0; i < rowSize; i++)
            for (j = 0; j < colSize; j++)
                inverse.map[i][j] = temp.map[i][j + colSize];

        return inverse;

    }

    /**
     * Compute the determinant of this matrix
     *
     * @returns Rational the determinant of this matrix as a rational number
     */
    public Rational det()
    {
        // Algorithm adapted from RosettaCode.org

        // Error Check: Cannot compute determinant of non-square matrix
        if(rowSize != colSize)
        {
            return new Rational(-1);
        }

        // Declare variables needed
        int i = 0, j = 0, r = 0, lead = 0;
        Matrix mat;
        Rational det = new Rational(1);

        // Create copy of current matrix
        // Create new matrix with 1 less column
        mat = new Matrix(rowSize, colSize);

        for(i = 0; i < rowSize; i++)
            for(j = 0; j < colSize; j++)
                mat.map[i][j] = map[i][j];

        // Lead and r start at 0
        while (r < rowSize && colSize > lead)
        {
            // Find if swap is needed
            i = r;
            while (mat.map[i][lead].isEqual(0))
            {
                i++;
                if (rowSize == i)
                {
                    i = r;
                    lead++;
                    if (colSize == lead)
                        return new Rational(0);
                }
            }

            int pos = 0;

            // Swap rows i and r
            for (pos = 0; pos < colSize; pos++)
            {
                Rational swapper;
                swapper = mat.map[i][pos];
                mat.map[i][pos] = mat.map[r][pos];
                mat.map[r][pos] = swapper;

                if(i!=r)
                    det = det.multiply(new Rational(-1));
            }

            // Divide row r by the matrix at M[r][lead] if it is not zero
            if (mat.map[r][lead].notEqual(0))
            {
                pos = 0;
                Rational val = mat.map[r][lead];
                for (pos = 0; pos < colSize; pos++)
                    mat.map[r][pos] = mat.map[r][pos].divide(val);

                det = det.multiply(val);
            }

            // Subtract M[r][lead] multiplied by row r from row i
            for (i = 0; i < rowSize; i++)
            {
                if (i != r)
                {
                    pos = 0;
                    Rational val = mat.map[i][lead];
                    for (pos = 0; pos < colSize; pos++)
                        mat.map[i][pos] = mat.map[i][pos].subtract((val.multiply(mat.map[r][pos])));
                }

            }

            // Increment for next iteration
            lead = lead + 1;
            r++;
        }

        // Return the rref form of this matrix
        return det;
    }

}


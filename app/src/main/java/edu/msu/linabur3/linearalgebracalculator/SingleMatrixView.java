package edu.msu.linabur3.linearalgebracalculator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Single Matrix View Class
 *
 * View that will draw a single matrix to the screen
 */
public class SingleMatrixView extends View {

    /**
     * Percentage of screen we want to leave as a margin around whole matrix
     */
    final static float MARGIN_SPACING = .05f;

    /**
     * Scale factor for each entry box for the matrix
     */
    final static float SCALE_FACTOR = .9f;

    /**
     * Paint used for drawing the lines of the matrix
     */
    private Paint boxPaint;

    /**
     * Paint used for drawing the selected box on the matrix
     */
    private Paint selectedPaint;

    /**
     * Paint used for drawing numbers of the matrix
     */
    private Paint numberPaint;

    /**
     * Paint used for drawing numbers of the matrix (as a fraction)
     */
    private Paint fractionPaint;

    /**
     * The row of the selected square of the matrix
     */
    private int selectedX = -1;

    /**
     * The column of the selected square of the matrix
     */
    private int selectedY = -1;

    /**
     * The column of the selected square of the matrix
     */
    private Matrix m;

    public SingleMatrixView(Context context) {
        super(context);
        init(null, 0);
    }

    public SingleMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SingleMatrixView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        // Initialize private members
        boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boxPaint.setColor(0xffffffff);

        selectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(0x604CA13E);

        numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numberPaint.setColor(0xffffffff);

        fractionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fractionPaint.setColor(0xffffffff);

        m = new Matrix(getContext());
    }

    /**
     * Draw the view for the single matrix
     *
     * @param canvas the canvas that this view object is drawing too
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get dimensions of canvas we are drawing on
        int width = canvas.getWidth();
        int height = canvas.getHeight();


        // Calculate margins to draw boxes for matrix at
        int startX = (int) (MARGIN_SPACING * width);
        int stopX = (int) ((1f - MARGIN_SPACING) * width);

        int startY = (int) (MARGIN_SPACING * height);
        int stopY = (int) ((1f - MARGIN_SPACING) * height);

        boxPaint.setStrokeWidth(5);

        // Calculate width of each matrix box
        int stepX = (int) ((stopX - startX) / m.getColSize());
        int stepY = (int) ((stopY - startY) / m.getRowSize());

        if(width < height) {
            numberPaint.setTextSize(width / m.getColSize());
            fractionPaint.setTextSize(width / m.getColSize() / 3);
        }
        else {
            numberPaint.setTextSize(height / m.getRowSize());
            fractionPaint.setTextSize(width / m.getRowSize() / 3);
        }

        // Draw each box for the matrix
        for(int i = 0; i < m.getRowSize(); i++) {
            for (int j = 0; j < m.getColSize(); j++) {
                canvas.save();
                canvas.translate(startX + j*stepX, startY + i*stepY);
                canvas.scale(SCALE_FACTOR, SCALE_FACTOR);

                // If this is the selected spot, draw a green square to notify user this is selected
                if(i == selectedX && j == selectedY)
                    canvas.drawRect(0, 0, stepX, stepY, selectedPaint);

                // Display the value in the matrix at this position

                // Display integer value if denominator is zero
                if(m.getVal(i,j).getBot() == 1)
                {
                    canvas.drawText(Integer.toString(m.getVal(i,j).getTop()),0, stepY, numberPaint);
                }

                // Otherwise display number as fraction
                else
                {
                    canvas.drawText(Integer.toString(m.getVal(i,j).getTop()),0, stepY / 2 - (MARGIN_SPACING * stepY), fractionPaint);
                    canvas.drawLine(0 + (MARGIN_SPACING * stepX), stepY / 2, stepX - (MARGIN_SPACING * stepX), stepY / 2, numberPaint);
                    canvas.drawText(Integer.toString(m.getVal(i,j).getBot()),0, stepY  - (MARGIN_SPACING * stepY) , fractionPaint);
                }
                canvas.restore();

            }
        }



    }

    /**
     * Gets called whenever a touch event is detected on the calculator
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(event);

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_MOVE:
        }

        return false;
    }

    /**
     * Gets called whenever the user touches a square on the matrix
     */
    private boolean onTouched(MotionEvent event) {

        float x, y;
        int matX, matY, stepX, stepY;


        // Get coordinates of touch
        x = event.getX();
        y = event.getY();
        stepX = getWidth() / m.getColSize();
        stepY = getHeight() / m.getRowSize();

        // row and column selected are selectedX and selectedY
        selectedY = (int) x / stepX;
        selectedX = (int) y / stepY;

        invalidate();

        return true;
    }

    /**
     * The number we want to update the currently selected space in the matrix with
     *
     * @param n the number we want to update
     */
    public void onUpdateNumber(int n) {

        // TODO: Check for if no number is being added (error condition)

        m.setVal(selectedX, selectedY, new Rational(n));

        selectedX = selectedY = -1;
        invalidate();

    }

    /**
     * Add a row to the matrix
     */
    public void addRow() {
        m.addRow();
        invalidate();
    }

    /**
     * Remove a row from the matrix
     */
    public void removeRow() {
        m.removeRow();
        invalidate();
    }

    /**
     * Add a column to the matrix
     */
    public void addColumn() {
        m.addColumn();
        invalidate();
    }

    /**
     * Add a column to the matrix
     */
    public void removeColumn() {
        m.removeColumn();
        invalidate();
    }

    /**
     *  Compute the RREF of this matrix and replace current matrix with it
     */
    public void computeRREF() {
        Matrix temp;
        temp = new Matrix(m.getRowSize(), m.getColSize());
        temp = m.rref();
        m = temp;
        invalidate();
    }

    /**
     *  Compute the inverse of this matrix and replace current matrix with it
     */
    public void computeInverse() {
        // Error Check: cannot compute inverse of non-square matrix
        if(m.getColSize() != m.getRowSize())
        {
            Toast.makeText(getContext(), "Cannot compute inverse of non-square matrix!", Toast.LENGTH_SHORT).show();
            return;
        }

        Matrix temp;
        temp = new Matrix(m.getRowSize(), m.getColSize());
        temp = m.inverse();
        m = temp;
        invalidate();
    }

    /**
     *  Compute the determinant of this matrix and display
     */
    public void computeDeterminant() {
        Rational det;
        det = m.det();

        // Error Check: cannot compute determinant of non-square matrix
        if(det.getTop() == -1) {
            Toast.makeText(getContext(), "Cannot compute determinant of a non-square matrix!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(det.getBot() == 1)
            Toast.makeText(getContext(), "Determinant of Matrix is " + Integer.toString(det.getTop()), Toast.LENGTH_SHORT ).show();
        else
            Toast.makeText(getContext(), "Determinant of Matrix is " + Integer.toString(det.getTop()) + "/" + Integer.toString(det.getBot()), Toast.LENGTH_SHORT ).show();
    }
}


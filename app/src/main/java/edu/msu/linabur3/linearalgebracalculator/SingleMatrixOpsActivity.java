package edu.msu.linabur3.linearalgebracalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SingleMatrixOpsActivity extends AppCompatActivity {

    /**
     * The Matrix view
     */
    private SingleMatrixView matView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_matrix_ops);

        matView = (SingleMatrixView) findViewById(R.id.singleMatrixView);
    }


    /**
     * Gets called when we want to enter a number to update in the matrix
     *
     * @param view the button that is calling this function
     */
    public void onEnterNumber(View view)
    {
        // Need to get number entered from editText view
        EditText numberView = (EditText) findViewById(R.id.InputNumber);
        String number = numberView.getText().toString();

        matView.onUpdateNumber(Integer.valueOf(number));

    }

    /**
     * Gets called when we want to add a row to the matrix
     *
     * @param view the add row button
     */
    public void onAddRow(View view)
    {
        // Tell the matrix to add a row
        matView.addRow();

    }

    /**
     * Gets called when we want to delete a row from the matrix
     *
     * @param view the remove row button
     */
    public void onRemoveRow(View view)
    {
        // Tell the matrix to add a row
        matView.removeRow();

    }

    /**
     * Gets called when we want to add a column to the matrix
     *
     * @param view the add column button
     */
    public void onAddColumn(View view)
    {
        // Tell the matrix to add a row
        matView.addColumn();

    }

    /**
     * Gets called when we want to remove a column from the matrix
     *
     * @param view the remove column button
     */
    public void onRemoveColumn(View view)
    {
        // Tell the matrix to add a row
        matView.removeColumn();

    }

    /**
     * Gets called when we want to compute the rref of this matrix
     *
     * @param view the remove column button
     */
    public void onComputeRREF(View view)
    {
        // Tell the matrix to add a row
        matView.computeRREF();

    }

    /**
     * Gets called when we want to compute the inverse of thsi matrix
     *
     * @param view the remove column button
     */
    public void onComputeInverse(View view)
    {
        // Tell the matrix to add a row
        matView.computeInverse();

    }

    /**
     * Gets called when we want to compute the determinant of this matrix
     *
     * @param view the compute determinant button
     */
    public void onComputeDeterminant(View view)
    {
        // Tell the matrix to add a row
        matView.computeDeterminant();
    }
}
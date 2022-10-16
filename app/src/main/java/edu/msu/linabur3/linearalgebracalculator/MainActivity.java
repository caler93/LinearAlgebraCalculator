package edu.msu.linabur3.linearalgebracalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Main Activity
 *
 * This activity is started when the program starts
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Single Matrix Operations
     *
     * @param view the view that called this handler
     */
    public void onSingleMatrixOpSelected(View view)
    {
        // Start Activity for single matrix operations
        Intent i = new Intent(this, SingleMatrixOpsActivity.class);
        startActivity(i);
    }
}
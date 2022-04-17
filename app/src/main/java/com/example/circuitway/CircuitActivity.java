package com.example.circuitway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class CircuitActivity extends AppCompatActivity {

    public ArrayList<Branch> Branches = new ArrayList<>();
    public ArrayList<Pin> Pins = new ArrayList<>();

    public TableLayout PinField;
    public RelativeLayout CircuitField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuit);

        Bundle intent = getIntent().getExtras();
        int level = intent.getInt("level");

        PinField = findViewById(R.id.PinField);
        CircuitField = findViewById(R.id.CircuitField);

        for (int i = 0; i < 5; i++){
            TableRow row = new TableRow(this);
            PinField.addView(row);
            ViewGroup.LayoutParams lp = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            for (int p = 0; p < 5; p++){
                Pin pin = new Pin(this, p, i);
                Pins.add(pin);
                row.addView(pin.PinButton);
            }
        }
    }
}
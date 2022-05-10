package com.example.circuitway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class CircuitActivity extends AppCompatActivity {

    public ArrayList<Branch> Branches = new ArrayList<>();
    public ArrayList<Detail> Details = new ArrayList<>();
    public ArrayList<Pin> Pins = new ArrayList<>();

    public TableLayout PinField;
    public ConstraintLayout DetailField;
    public RelativeLayout CircuitField;
    public Button StepButton;
    public Button StartButton;
    public Button LoopButton;

    public int TPS;
    public boolean Loop;
    public Thread LoopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuit);

        Bundle intent = getIntent().getExtras();
        int level = intent.getInt("level");

        PinField = findViewById(R.id.PinField);
        DetailField = findViewById(R.id.DetailField);
        CircuitField = findViewById(R.id.CircuitField);
        StepButton = findViewById(R.id.StepButton);
        StartButton = findViewById(R.id.StartButton);
        LoopButton = findViewById(R.id.LoopButton);

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
                row.addView(pin.Graphic);
            }
        }

        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        StepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step();
            }
        });
        LoopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLoop();
            }
        });

        LoopThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (Loop){

                }
            }
        });
    }

    public void start(){
        for (Detail d : Details){
            d.getBranch(d);
            System.out.println("[BRANCH] " + d.ID + " " + d.Branch.ID);
        }
    }

    public void step(){
        for (Detail d : Details){
            d.balancePotentials();
        }
        for (Pin p : Pins){
            p.postPotentialBalance();
        }
        for (Detail d : Details){
            d.step();
        }
    }

    public void toggleLoop(){
        Loop = !Loop;
    }
}
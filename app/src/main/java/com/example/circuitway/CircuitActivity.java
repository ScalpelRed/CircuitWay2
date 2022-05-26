package com.example.circuitway;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class CircuitActivity extends AppCompatActivity {

    Resources resources;

    public ArrayList<Branch> Branches = new ArrayList<>();
    public ArrayList<Detail> Details = new ArrayList<>();
    public ArrayList<Pin> Pins = new ArrayList<>();
    public ArrayList<Task> Tasks = new ArrayList<>();
    
    public Detail SelectedDetail = null;

    public TableLayout PinField;
    public ConstraintLayout DetailField;
    public RelativeLayout CircuitField;
    public Button StartButton;

    public ConstraintLayout EditorView;
    public ConstraintLayout RunningView;

    public Button SelectWireButton;
    public Button SelectBatteryButton;
    public Button SelectSwitchButton;
    public ImageView DetailSelection;

    public TextView InfoText;
    public Button RemoveDetailButton;
    public Button SpecialActionButton;

    public ConstraintLayout CircuitView;
    public ConstraintLayout TaskView;
    public Button CloseTasksButton;
    public Button OpenTasksButton;

    public int timeStep = 100;
    public int taskStep = 1000;
    public long lastStepMills = System.currentTimeMillis();
    public long lastTaskMills = System.currentTimeMillis();
    public boolean isRunning;

    
    public Thread SimulationThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuit);

        resources = getResources();

        PinField = findViewById(R.id.PinField);
        DetailField = findViewById(R.id.DetailField);
        CircuitField = findViewById(R.id.CircuitField);
        StartButton = findViewById(R.id.StartButton);

        EditorView = findViewById(R.id.editorView);
        RunningView = findViewById(R.id.runningInfoView);

        SelectWireButton = findViewById(R.id.DSB_wire);
        SelectBatteryButton = findViewById(R.id.DSB_battery);
        SelectSwitchButton = findViewById(R.id.DSB_switch);
        DetailSelection = findViewById(R.id.detailSelection);

        InfoText = findViewById(R.id.InfoText);
        RemoveDetailButton = findViewById(R.id.RemoveButton);
        SpecialActionButton = findViewById(R.id.ActionButton);

        CircuitView = findViewById(R.id.circuitView);
        TaskView = findViewById(R.id.taskView);
        OpenTasksButton = findViewById(R.id.openTasks);
        CloseTasksButton = findViewById(R.id.closeTasks);

        SimulationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (System.currentTimeMillis() - lastStepMills > timeStep) {
                        simulationStep();
                        lastStepMills = System.currentTimeMillis();
                    }
                    if (System.currentTimeMillis() - lastTaskMills > taskStep){
                        checkLevelCompleteness();
                        lastStepMills = System.currentTimeMillis();
                    }

                }
            }
        });

        StartButton.setOnClickListener(v -> runSimulation());

        SelectWireButton.setOnClickListener(v
                -> SetSelectedDetailType(Detail.DetailType.WIRE));
        SelectBatteryButton.setOnClickListener(v
                -> SetSelectedDetailType(Detail.DetailType.BATTERY));
        SelectSwitchButton.setOnClickListener(v
                -> SetSelectedDetailType(Detail.DetailType.SWITCH));

        RemoveDetailButton.setOnClickListener(v
                -> {
                        SelectedDetail.detach();
                        SetSelectedDetail(null);
                    });
        SpecialActionButton.setOnClickListener(v
                -> SelectedDetail.specialAction());

        TaskView.setVisibility(GONE);
        OpenTasksButton.setOnClickListener(v
            -> { CircuitView.setVisibility(GONE);
            TaskView.setVisibility(VISIBLE); });
        CloseTasksButton.setOnClickListener(v
                -> { CircuitView.setVisibility(VISIBLE);
            TaskView.setVisibility(GONE); });

        RunningView.setVisibility(GONE);


        Bundle intent = getIntent().getExtras();
        int level = intent.getInt("level");
        LevelLoader.Open(level, this);
    }

    public void runSimulation(){
        for (Detail d : Details){
            d.getBranch(d);
            //System.out.println("The branch of " + d.ID + " is " + d.Branch.ID);
        }
        for (Branch b : Branches){
            b.FindEdges();
        }

        EditorView.setVisibility(GONE);
        RunningView.setVisibility(VISIBLE);

        //SimulationThread.start();
    }

    private void simulationStep(){
        for (Detail d : Details){
            d.balancePotentials();
        }
        for (Pin p : Pins){
            p.postPotentialBalance();
        }
        for (Branch b : Branches){
            b.CalculateResistance();
        }
        for (Detail d : Details){
            d.step();
        }
        if (SelectedDetail != null){

        }
    }

    public boolean checkLevelCompleteness(){
        for (Task t : Tasks) if (!t.checkCompleteness()) return false;

        return true;
    }

    public void SetInfoText(String text){
        InfoText.setText(text);
    }



    public void SetSelectedDetailType(Detail.DetailType t) {
        Button t2 = null;
        switch (t){
            case WIRE:
                t2 = SelectWireButton;
                Detail.SelectedDetailType = Detail.DetailType.WIRE;
                break;
            case BATTERY:
                t2 = SelectBatteryButton;
                Detail.SelectedDetailType = Detail.DetailType.BATTERY;
                break;
            case SWITCH:
                t2 = SelectSwitchButton;
                Detail.SelectedDetailType = Detail.DetailType.SWITCH;
                break;
        }
        /*ConstraintLayout.LayoutParams p
                = (ConstraintLayout.LayoutParams)DetailSelection.getLayoutParams();
        p.leftToLeft = t2.getId();*/

    }

    public void SetSelectedDetail(Detail d){
        SelectedDetail = d;
        for (Detail d2 : Details)
            d2.SelectionGraphic.setVisibility(GONE);
        if (d == null) SetInfoText("Select a detail to view information about it.");
        else if (!isRunning) SetInfoText(d.getEditorInfo());
        //else SetInfoText(d.getRunningInfo());
    }

    public Detail GetDetailByID(int id){
        for (Detail d : Details) if (d.ID == id) return d;
        return null;
    }

}
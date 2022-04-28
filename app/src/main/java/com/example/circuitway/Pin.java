package com.example.circuitway;


import android.annotation.SuppressLint;
import android.content.Context;
import android.telecom.Call;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;

public class Pin {

    public float Potential = 0;
    public float balancePotential = 0;

    public ArrayList<Detail> Details = new ArrayList<>();
    public byte detailCount;

    Button PinButton;
    static ArrayList<Pin> selectedPins = new ArrayList<Pin>();
    static int maxToSelect = 2;
    boolean isSelected = false;
    public int x;
    public int y;

    public CircuitActivity CircuitActivity;

    public Pin(CircuitActivity context, int x, int y){

        CircuitActivity = context;

        PinButton = new Button(context);
        PinButton.setBackground(AppCompatResources.getDrawable(context,
                R.drawable.circuit_dot));
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                64, 64);
        PinButton.setLayoutParams(params);

        this.x = x;
        this.y = y;

        PinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected){
                    if (checkCorrectnessFor(selectedPins.size() + 1,
                            Pin.this)) {
                        select();
                        if (selectedPins.size() == maxToSelect) {
                            Detail d = new Detail(CircuitActivity,
                                    selectedPins.toArray(new Pin[0]));
                            CircuitActivity.Details.add(d);
                            Details.add(d);
                            System.out.println(d);

                            while (selectedPins.size() > 0) {
                                selectedPins.get(0).deselect();
                            }
                        }
                    }
                    else {
                        while (selectedPins.size() > 0) {
                            selectedPins.get(0).deselect();
                        }
                    }
                }
                else {
                    deselect();
                }
            }
        });
    }

    void select(){
        selectedPins.add(this);
        PinButton.setAlpha(0.5f);
        isSelected = true;
    }

    void deselect(){
        selectedPins.remove(this);
        PinButton.setAlpha(1f);
        isSelected = false;
    }

    boolean checkCorrectnessFor(int n, Pin p){
        switch (n){
            case 1: return true;
            case 2:
                Pin sp = selectedPins.get(0);
                boolean res = (sp.x == p.x && Math.abs(sp.y - p.y) == 2)
                        || (sp.y == p.y && Math.abs(sp.x - p.x) == 2);
                System.out.println("Pattern is correct: " + res);
                return res;
            default: return false;
        }
    }

    public void postPotentialBalance() {
        Potential = balancePotential / Details.size();
        balancePotential = 0;
    }

}

package com.example.circuitway;


import android.annotation.SuppressLint;
import android.content.Context;
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
    public Detail[] Details = new Detail[4];
    public byte detailCount;

    Button PinButton;
    static ArrayList<Pin> selectedPins = new ArrayList<Pin>();
    static int maxToSelect = 5;
    boolean isSelected;
    public int x;
    public int y;

    public Pin(CircuitActivity context, int x, int y){
        PinButton = new Button(context);
        PinButton.setBackground(AppCompatResources.getDrawable(context,
                R.drawable.circuit_dot));
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                64, 64);
        PinButton.setLayoutParams(params);

        PinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected){
                    if (checkCorrectnessFor(selectedPins.size() - 1, Pin.this)) {
                        select();
                        if (selectedPins.size() == maxToSelect) {
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
            case 1:
                Pin sp = selectedPins.get(0);
                if (sp.x == p.x && Math.abs(sp.y - p.y) == 2) return true;
                if (sp.y == p.y && Math.abs(sp.x - p.x) == 2) return true;
                return false;
            default: return true;
        }
    }

    public void BalancePotential(){
        for (int i = 0; i < Details.length; i++){
            if (Details[i] != null){
                for (int p = 0; p < Details[i].Pins.length; p++){
                    Potential = (Potential + Details[i].Pins[p].Potential) / 2;
                }
            }
        }
    }

}

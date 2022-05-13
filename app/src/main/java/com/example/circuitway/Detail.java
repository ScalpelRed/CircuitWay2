package com.example.circuitway;

import android.telecom.Call;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.Objects;

public class Detail {

    private static int lastID = 0;
    public int ID;

    public float Resistance;
    public Pin[] Pins;
    public final int NominalContactCount = 0;

    public Branch Branch;
    public Detail LastBranchCheckSource;

    public Button Graphic;

    CircuitActivity circuitActivity;

    public Detail(CircuitActivity c, Pin[] pins){
        circuitActivity = c;

        ID = lastID++;
        Pins = pins;
        for (int i = 0; i < pins.length; i++){
            pins[i].Details.add(this);
        }

        Graphic = new Button(c);
        Graphic.setBackground(AppCompatResources.getDrawable(c,
                R.drawable.d_unknown));

        ConstraintLayout.LayoutParams params = null;

        if (Pins[0].x < Pins[1].x){
            params = new ConstraintLayout.LayoutParams(128, 64);
            Graphic.setRotation(0);
        }
        else if (Pins[0].y < Pins[1].y){
            params = new ConstraintLayout.LayoutParams(64, 128);
            Graphic.setRotation(90);
        }
        else if (Pins[1].x < Pins[0].x){
            params = new ConstraintLayout.LayoutParams(128, 64);
            Graphic.setRotation(180);
        }
        else if (Pins[1].y < Pins[0].y){
            params = new ConstraintLayout.LayoutParams(64, 128);
            Graphic.setRotation(270);
        }

        params.setMargins(64 * Pins[0].x, 0, 0, 0);
        Graphic.setLayoutParams(params);

        c.DetailField.addView(Graphic);


    }

    public Branch getBranch(Detail d) {
        if (Branch == null){
            for (Pin v : Pins){
                if (v.Details.size() == 2){
                    for (Detail b : v.Details){
                        if (b != this && b.LastBranchCheckSource != d){
                            b.LastBranchCheckSource = d;
                            Branch = b.getBranch(d);
                            Branch.Details.add(this);
                            return Branch;
                        }
                    }
                }
            }
        }
        else return Branch;

        Branch = new Branch(circuitActivity);
        return Branch;
    }

    public float getTotalResistance(){
        return Float.NaN;
    }

    public void balancePotentials(){

    }

    public float getCurrent(){
        return Float.NaN;
    }

    public void step(){

    }

    public final void detach(){
        for (int i = 0; i < Pins.length; i++){
            Pins[i].Details.remove(this);
        }
        circuitActivity.Details.remove(this);
        circuitActivity.DetailField.removeView(Graphic);
    }

    @Override
    public final boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Detail detail = (Detail) o;
        return ID == detail.ID;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(ID);
    }
}

package com.example.circuitway;

import android.telecom.Call;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class Detail {

    private static int lastID = 0;
    public int ID;

    public float Resistance;
    public Pin[] Pins;
    public final int NominalContactCount = 0;

    public boolean IsInBranch = false;
    public Branch Branch;

    public Button Graphic;

    CircuitActivity circuitActivity;

    public Detail(CircuitActivity c, Pin[] pins){
        circuitActivity = c;

        ID = lastID;
        lastID++;
        Pins = pins;
        for (int i = 0; i < pins.length; i++){
            pins[i].Details.add(this);
        }

        Graphic = new Button(c);
        Graphic.setBackground(AppCompatResources.getDrawable(c,
                R.drawable.d_unknown));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                128, 64);
        Graphic.setPadding(128, 0, 0, 0);
        Graphic.setLayoutParams(params);

        /*if (Pins[1].x < Pins[0].x){
            Graphic.setPadding(64 * Pins[1].x, 0, 0, 0);
        }
        else if (Pins[0].y < Pins[1].y){

        }
        else if (Pins[0].x < Pins[1].x){
            Graphic.setPadding(64 * Pins[0].x, 0, 0, 0);
        }
        else if (Pins[1].y < Pins[0].y){

        }*/

        c.DetailField.addView(Graphic);
        System.out.println(Pins[0].x + " " + Pins[1].x);
    }

    public void getBranch(){

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
        // TODO удаление Graphic
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

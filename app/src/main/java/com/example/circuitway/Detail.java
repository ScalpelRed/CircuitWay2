package com.example.circuitway;

import android.widget.Button;

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

    public Detail(Pin ... pins){
        ID = lastID;
        lastID++;
        Pins = pins;


        if (Pins[1].x < Pins[0].x){

        }
        else if (Pins[0].y < Pins[1].y){

        }
        else if (Pins[0].x < Pins[1].x){

        }
        else if (Pins[1].y < Pins[0].y){

        }
    }

    public void getBranch(){

    }

    public float getTotalResistance(){
        return 0;
    }

    public void step(){

    }

    public float getCurrent(){
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Detail detail = (Detail) o;
        return ID == detail.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}

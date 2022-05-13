package com.example.circuitway;

import android.widget.EdgeEffect;
import android.widget.TableRow;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.Objects;

public class Branch {

    private static int lastID = 0;
    public int ID;

    public ArrayList<Detail> Details = new ArrayList<>();
    public Pin Pin1;
    public Pin Pin2;

    public CircuitActivity circuitActivity;

    public float Resistance;

    public Branch(CircuitActivity c) {
        circuitActivity = c;
        c.Branches.add(this);
        ID = lastID++;
    }

    public void CalculateResistance(){
        Resistance = 0;
        for (Detail v : Details) Resistance += v.Resistance;
    }

    public void FindEdges() {
        for (Detail v : Details){
            if (v.Pins[0].Details.size() != 2) {
                if (Pin1 == null) Pin1 = v.Pins[0];
                else Pin2 = v.Pins[0];
            }
            if (v.Pins[1].Details.size() != 2) {
                if (Pin1 == null) Pin1 = v.Pins[1];
                else Pin2 = v.Pins[1];
            }
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return ID == branch.ID;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(ID);
    }

}

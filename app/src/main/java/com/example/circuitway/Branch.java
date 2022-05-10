package com.example.circuitway;

import android.widget.TableRow;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.Objects;

public class Branch {

    private static int lastID = 0;
    public int ID;

    public ArrayList<Detail> Details = new ArrayList<>();
    public CircuitActivity circuitActivity;

    public Branch(CircuitActivity c) {
        circuitActivity = c;
        ID = lastID++;
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

package com.example.circuitway;

import androidx.appcompat.content.res.AppCompatResources;

public class Disistor extends Resistor {
    public final int NominalContactCount = 2;

    public Disistor(CircuitActivity c, Pin ... pins) {

        super(c, pins);

        Graphic.setBackground(AppCompatResources.getDrawable(c,
                R.drawable.d_disistor));
        Graphic.getBackground().setTint(
                circuitActivity.getResources().getColor(R.color.mainTheme));
    }

    @Override
    public String getEditorInfo() {
        String res = "Disistor\nResistance: " + getResistance();
        if (!isEditable) res += "\nLocked";
        return res;
    }

    @Override
    public float getResistance() {
        return -Resistance;
    }
}

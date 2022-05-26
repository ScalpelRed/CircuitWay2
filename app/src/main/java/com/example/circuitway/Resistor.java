package com.example.circuitway;

import androidx.appcompat.content.res.AppCompatResources;

public class Resistor extends Wire {

    public Resistor(CircuitActivity c, Pin ... pins) {

        super(c, pins);

        Graphic.setBackground(AppCompatResources.getDrawable(c,
                R.drawable.d_resistor));
        Graphic.getBackground().setTint(
                circuitActivity.getResources().getColor(R.color.mainTheme));
    }

    @Override
    public String getEditorInfo() {
        String res = "Resistance\nResistance: " + getResistance();
        if (!isEditable) res += "\nLocked";
        return res;
    }
}

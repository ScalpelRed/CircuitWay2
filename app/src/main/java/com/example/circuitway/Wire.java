package com.example.circuitway;

import androidx.appcompat.content.res.AppCompatResources;

public class Wire extends Detail {

    public final int NominalContactCount = 2;

    public Wire(CircuitActivity c, Pin ... pins) {
        super(c, pins);

        Graphic.setBackground(AppCompatResources.getDrawable(c,
                R.drawable.d_wire));
    }

    @Override
    public void step() {
        super.step();

    }

    @Override
    public float getCurrent() {
        super.getCurrent();
        return (Pins[0].Potential - Pins[1].Potential) / getTotalResistance();
    }

    @Override
    public void balancePotentials(){
        Pins[0].balancePotential += Pins[1].Potential;
        Pins[1].balancePotential += Pins[0].Potential;
    }
}

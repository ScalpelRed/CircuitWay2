package com.example.circuitway;

public class Wire extends Detail {

    public final int NominalContactCount = 2;

    @Override
    public void step() {
        super.step();

    }

    @Override
    public float getCurrent() {
        super.getCurrent();
        return (Pins[0].Potential - Pins[1].Potential) / getTotalResistance();
    }
}

package com.example.circuitway;

import androidx.appcompat.content.res.AppCompatResources;

public class Battery extends Wire {
    public float Voltage = 128;

    public Battery(CircuitActivity c, Pin ... pins){
        super(c, pins);

        Graphic.setBackground(AppCompatResources.getDrawable(c,
                R.drawable.d_battery));
    }

    @Override
    public void step(){
        super.step();
    }

    @Override
    public float getCurrent() {
        return 0;
    }

    @Override
    public void balancePotentials() {
        Pins[0].balancePotential =  Voltage / 2;
        Pins[1].balancePotential = -Voltage / 2;
    }
}

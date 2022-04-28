package com.example.circuitway;

import android.widget.TableRow;

import androidx.appcompat.content.res.AppCompatResources;

    public class Branch extends Detail {

        public Branch(CircuitActivity c, Pin ... pins) {
            super(c, pins);

            Graphic.setBackground(AppCompatResources.getDrawable(c, 0));
        }

}

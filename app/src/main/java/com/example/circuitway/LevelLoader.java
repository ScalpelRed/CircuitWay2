package com.example.circuitway;

import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;

public class LevelLoader {

    public static ArrayList<Level> Levels = new ArrayList<>();

    public static void CreateLevels(){
        Levels.add(new Level(0, 0, new String[0], new String[0]));

        Levels.add(new Level(5, 5,
                new String[] {
                        "WIRE 1 1 1 3",
                        "WIRE 1 3 3 3",
                        "BATTERY 3 3 3 1",
                        "WIRE 3 1 1 3",
                },
                new String[] {
                        "0 VOLTAGE MINIMUM 1"
                }
                ));
    }

    public static void Open(int n, CircuitActivity c){
        if (n != 0){
            Levels.get(n).Instantiate(c);
        }
    }

}

class Level{

    public Level(int sx, int sy, String[] details, String[] tasks){
        sizeX = sx;
        sizeY = sy;
        Details = new ArrayList<String>(Arrays.asList(details));
        Tasks = new ArrayList<String>(Arrays.asList(tasks));
    }

    public int sizeX;
    public int sizeY;
    public ArrayList<String> Details;
    public ArrayList<String> Tasks;

    public void Instantiate(CircuitActivity circuitActivity){

        for (int i = 0; i < sizeX; i++){
            TableRow row = new TableRow(circuitActivity);
            circuitActivity.PinField.addView(row);
            ViewGroup.LayoutParams lp = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            for (int p = 0; p < sizeY; p++){
                Pin pin = new Pin(circuitActivity, p, i);
                circuitActivity.Pins.add(pin);
                row.addView(pin.Graphic);
            }
        }

        Detail.lastID = 0;

        Detail.DetailType currentDetailType = Detail.DetailType.INVALID;
        Detail currentDetail = null;

        for (String d : Details){
            String[] words = d.split(" ");

            currentDetailType = Detail.DetailType.valueOf(words[0]);

            int x = Integer.parseInt(words[1]);
            int y = Integer.parseInt(words[2]);
            Pin pin1 = circuitActivity.Pins.get(y * sizeX + x);

            x = Integer.parseInt(words[3]);
            y = Integer.parseInt(words[4]);
            Pin pin2 = circuitActivity.Pins.get(y * sizeX + x);

            currentDetail = Detail.CreateDetail(currentDetailType,
                    circuitActivity, pin1, pin2);

            for (int i = 5; i < words.length; i += 2){
                switch (words[i]){
                    case "res":
                        currentDetail.Resistance
                                = Integer.parseInt(words[i + 1]);
                    case "voltage":
                        ((Battery)currentDetail).Voltage
                                = Integer.parseInt(words[i + 1]);

                }
            }

            currentDetail.isEditable = false;
            circuitActivity.Details.add(currentDetail);
        }

        for (String d : Tasks){
            String[] words = d.split(" ");
            circuitActivity.Tasks.add(new Task(
                    circuitActivity.GetDetailByID(Integer.parseInt(words[0])),
                    Task.TaskObjective.valueOf(words[1]),
                    Task.TaskCondition.valueOf(words[2]),
                    Integer.parseInt(words[3]), circuitActivity));

        }
    }
}

package com.example.myapplication;

import static java.lang.Long.reverse;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.Arrays;


public class HelpActivity extends Activity {
    public static void reverseArray(int[] array)
    {
        // Length of the array
        int n = array.length;

        // Swapping the first half elements
        // With last Half Elements
        for (int i = 0; i < n / 2; i++) {

            // Storing the first half elements temporarily
            int temp = array[i];

            // Assigning the first half
            // to the last half
            array[i] = array[n - i - 1];

            // Assigning the last half
            // to the first half
            array[n - i - 1] = temp;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int scoresArray[]=Globals.getInstance().scores;
        int scoreArray[] = new int[10];
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_layout);
        MainActivity.getPlayer().start();


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(Globals.getInstance().getHighScore());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("key", jsonArray.toString());
        System.out.println(jsonArray);
        editor.apply();

        try {
            JSONArray jsonArray2 = new JSONArray(prefs.getString("key", "[]"));
            for (int i = 0; i < jsonArray2.length(); i++) {
                Log.d("your JSON Array", jsonArray2.getInt(i)+"");
                scoreArray[i]=jsonArray2.getInt(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Arrays.sort(scoreArray);
        reverseArray(scoreArray);
        Log.d("augh", Arrays.toString(scoreArray));
        String text= "Best scores:\n1:"+scoreArray[0]+"\n2:"+scoresArray[1]+"\n3:"+scoresArray[2]+"\n4:"+scoresArray[3];
        TextView tv = findViewById(R.id.scoresView);
        tv.setText(text);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.getPlayer().pause();
    }

}

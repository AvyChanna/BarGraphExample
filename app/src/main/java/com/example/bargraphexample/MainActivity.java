package com.example.bargraphexample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (BarChart) findViewById(R.id.chart);
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateChart();
    }

    /**
     * This is where chart gets all of its data.<br>
     * The following method needs to be used to populate bar/histo/line/stem/...<br>
     * <ul>
     *      <li>Make a list of `*Entry`, with 2d coordinates</li>
     *      <li>Make a `*Dataset` from these entries</li>
     *      <li>Make `*Data` from dataset(Seems redundant, but do it)</li>
     *      <li>Add `*Data` to chart and invalidate</li>
     * </ul>
     */
    public void populateChart() {
        chart.setLogEnabled(true);
        Typeface firaCode = Typeface.createFromAsset(getAssets(), "fonts/FiraCode-Retina.ttf");
        Random random = new Random();
        // This will get us random but deterministic values
        // To test another dataset, change seed (but remember it, if you want to duplicate it)
        random.setSeed(0);

        float[][] dataObjects = new float[100][2];
        for (int i = 0; i < 100; i++) {
            dataObjects[i][0] = i;
            dataObjects[i][1] = 2 * random.nextFloat() - 1;
        }

        List<BarEntry> entries = new ArrayList<>();
        for (float[] data : dataObjects) {
            // turn your data into Entry objects
            entries.add(new BarEntry(data[0], data[1]));
        }

        // add entries to dataset
        BarDataSet dataSet = new BarDataSet(entries, "");
        // Color of bar
        dataSet.setColor(Color.RED);
        // Color of value above/below bar
        dataSet.setValueTextColor(Color.BLUE);
        BarData barData = new BarData(dataSet);
        chart.setData(barData);

        // Keep in mind description is not multiline(bummer)
        Description description = new Description();
        description.setText("Font Ligatures are dope >--|--<>-->-~>");
        description.setTextColor(Color.rgb(0, 128, 0));
        description.setTypeface(firaCode);
        chart.setDescription(description);

        ValueFormatter axisFormatter = new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                float y = barEntry.getY();
                float integral = (int) y;
                float fractional = y - integral;
                if (fractional < 0.5)
                    return String.valueOf((int) y);
                else
                    return String.valueOf((int) y + 1);
            }

            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(firaCode);
        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7, true);
        xAxis.setValueFormatter(axisFormatter);


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(firaCode);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(axisFormatter);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setWordWrapEnabled(true);
        l.setXOffset(10);
//        l.setFormSize(9f);
//        l.setTextSize(11f);
//        l.setXEntrySpace(4f);
        List<LegendEntry> le = new ArrayList<>();
        le.add(new LegendEntry("RED SQUARE", Legend.LegendForm.SQUARE, 9f, Float.NaN, null, Color.RED));
        le.add(new LegendEntry("GREEN CIRCLE", Legend.LegendForm.CIRCLE, 9f, Float.NaN, null, Color.GREEN));
        le.add(new LegendEntry("BLUE LINE", Legend.LegendForm.LINE, 9f, Float.NaN, null, Color.BLUE));
        l.setCustom(le);
//        l.setExtra(new int[]{Color.RED, Color.GREEN,  Color.BLUE}, new String[]{"RED", "GREEN", "BLUE"});


        // Misc
        chart.setMaxVisibleValueCount(25);
        chart.setPinchZoom(true);
        chart.setDrawBarShadow(true);
        chart.setDrawValueAboveBar(true);
        chart.setHighlightFullBarEnabled(true);
        chart.setFitBars(false);
        chart.invalidate();
    }
}
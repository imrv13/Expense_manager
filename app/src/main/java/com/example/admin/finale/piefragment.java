package com.example.admin.finale;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;


public class piefragment extends Fragment {

    PersonDatabaseHelper help;
    account a2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.piechart, container, false);
        help=new PersonDatabaseHelper(this.getActivity());
        long r_id=getArguments().getLong("id");
        a2=new account(r_id);

        drawPieChart(v);


        return v;
    }
    private void drawPieChart(View v){
        // Ceate CategorySeries
        CategorySeries categorySeries=new CategorySeries("Pie chart categories");

        Cursor inco=help.getincome(a2.getid(a2));

        if (inco.moveToFirst()){
            do{
                int income = inco.getInt(inco.getColumnIndex(inco.getColumnName(2)));
                // do what ever you want here
                a2.setincome(income);
            }while(inco.moveToNext());
        }
        inco.close();
        int expen = help.getexpense(a2.getid(a2));



        categorySeries.add("income",a2.getincome());
        categorySeries.add("Expense",expen);



        // Add series render to each slide of the pie chart
        int[] colors={Color.GREEN, Color.MAGENTA};
        DefaultRenderer defaultRenderer=new DefaultRenderer();
        for(int i = 0 ;i<categorySeries.getItemCount();i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            // Spcify render options
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            seriesRenderer.setDisplayChartValuesDistance(0);
            seriesRenderer.setShowLegendItem(true);
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        // Specify default render options
        defaultRenderer.setPanEnabled(true);
        defaultRenderer.setAntialiasing(true);
        defaultRenderer.setShowLabels(true);
        defaultRenderer.setShowLegend(true);
        defaultRenderer.setChartTitle("Income and expense");

        // Get pie chart view
        GraphicalView chartView = ChartFactory.getPieChartView(this.getActivity(), categorySeries, defaultRenderer);
        // Add the pie chart view to the layout to show
        LinearLayout chartlayout=(LinearLayout)v.findViewById(R.id.llayout1);

        chartlayout.addView(chartView);
    }
}

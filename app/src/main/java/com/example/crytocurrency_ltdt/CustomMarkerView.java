package com.example.crytocurrency_ltdt;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.SimpleDateFormat;

public class CustomMarkerView extends MarkerView {
    private TextView tvContent,tvContent2;
    int seekbar;

    public CustomMarkerView(Context context, int layoutResource,int progress) {
        super(context, layoutResource);
        seekbar = progress;
        // this markerview only displays a textview
        tvContent = (TextView) findViewById(R.id.tvContent);
        /*tvContent2 = (TextView) findViewById(R.id.tvContent2);*/
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(("giá: " + e.getY()+" USD"+System.getProperty("line.separator")+EpochToTime(e.getX()))); // set the entry-value as the display text
        /*tvContent2.setText((EpochToTime(e.getX())));*/
        // this will perform necessary layouting
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
   /* @Override
    public int getXOffset() {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }
    @Override
    public int getYOffset() {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }*/
    }

    public String EpochToTime(float i) {
        long fbt = (long) i;
        /*ZonedDateTime dateTime = Instant.ofEpochMilli(fbt).atZone(ZoneId.of("Asia/Jakarta"));*/
        /*return  dateTime.format(formatter);*/
        /*String fromat =  new DateTime( fbt * 1000, DateTimeZone.UTC ).toString();*/
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm ");
        DateTime dt =  new DateTime( fbt * 1000, DateTimeZone.UTC );
        /*String time  = formatter.format(new Date(fbt));*/
        String minute = String.valueOf(dt.getMinuteOfHour());
        String hours =   String.valueOf(dt.getHourOfDay());
        String day=   String.valueOf(dt.getDayOfWeek());
        String day1=   String.valueOf(dt.getDayOfMonth());
        String week=   String.valueOf(dt.getWeekOfWeekyear());
        String month=   String.valueOf(dt.getMonthOfYear());
        String year=   String.valueOf(dt.getYear());
        if(seekbar == 0)
            return new String(hours +":" + minute) ;
        else if (seekbar == 1) {
            if (Integer.parseInt(day) < 7)
                return hours +":" + minute+ "thứ" + day + 1 + "tuần" + week;
            else return hours +":" + minute+ "chủ nhật"  + "tuần" + week;
        }
        else if (seekbar == 2)
            return hours +": " + minute+"ngày " +day1+ "tuần " +week +"tháng " +month;
        else
            return "ngày " +day1+"tháng " +month+"năm " +year;
    }
}

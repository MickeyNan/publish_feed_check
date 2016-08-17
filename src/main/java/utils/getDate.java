package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class getDate {
    private Date end_date;
    private Date start_date;
    private SimpleDateFormat dateFormat;
    public getDate() {
        end_date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end_date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        start_date = calendar.getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public String get_end_time() {
        String end = dateFormat.format(end_date).toString();
        return end;
    }

    public String get_start_time() {
        String start = dateFormat.format(start_date).toString();
        return start;
    }

    /*
    public static void main(String [] args) {
        JSONObject feed = new JSONObject();
        JSONObject test = new JSONObject();
        JSONArray array = new JSONArray();
        array.add(test);
        array.add(1);
        for(int i = 0;i < array.size();i++) {
            JSONObject test = array.getJSONObject()
        }

    }*/

}
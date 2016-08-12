package utils;
import java.io.File;
import java.io.IOException;
import java.lang.Integer;
import java.lang.String;
import java.util.*;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.PrintWriter;
import java.io.File;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;

import java.text.SimpleDateFormat;
import java.util.Date;


public class cleanlog {


    public static void clean(Integer count) {
        getDate which_day = new getDate();
        String date = which_day.get_end_time();
        try {
            PrintWriter writer = new PrintWriter("./out_" + date, "UTF-8");
            for (int i = 0; i < count; i++){
                String file_name = "log" + Integer.toString(i) + ".txt";
                String out = "./total";
                try {
                    LineIterator iter_str = FileUtils.lineIterator(new File(file_name), "UTF-8");
                    while (iter_str.hasNext()) {
                        String line = iter_str.nextLine();
                        writer.println(line);

                    }

                }catch (IOException e) {

                }

                File file = new File(file_name);
                if(file.delete()) {

                }else{
                    System.out.println(file_name + " delete failed!");
                }
            }

            writer.flush();
            writer.close();

        }catch (Exception e) {
            e.printStackTrace();
        }


    }


}
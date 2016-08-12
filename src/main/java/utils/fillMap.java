package utils;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.*;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;

public class fillMap {

    public static void fill(Map<String,String> map,String path) {

        try {
            LineIterator iter_str = FileUtils.lineIterator(new File(path), "UTF-8");
            while (iter_str.hasNext()){
                String line = iter_str.nextLine();
                String[] array = new String[2];
                array = line.split(" ");
                map.put(array[0],array[1]);
            }
            iter_str.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }


}
package utils;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.*;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.alibaba.fastjson.JSONObject;
import sun.jvm.hotspot.debugger.linux.sparc.LinuxSPARCThreadContext;
import com.google.common.collect.Sets;

public class fillOnly {
    //
    public static void fill(List<Map<String,Map<String,String>>> list,String path) {
        try {
            LineIterator iter_str = FileUtils.lineIterator(new File(path), "UTF-8");
            while (iter_str.hasNext()) {
                String line = iter_str.nextLine();
                String [] array = new String [4];
                if (line.contains(" ")) {
                    array = line.split(" ");
                    Map<String,String> map_value = new HashMap<String,String>();
                    Map<String,Map<String,String>> map_table = new HashMap<String,Map<String,String>>();
                    map_value.put(array[1],array[3]);
                    map_table.put(array[0],map_value);
                    list.add(map_table);
                }





            }

        }catch (IOException e) {

        }

    }


}
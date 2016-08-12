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

public class fillList {

    public static void fill(List<String> list,String path) {
        try {
            LineIterator iter_str = FileUtils.lineIterator(new File(path), "UTF-8");
            while (iter_str.hasNext()){
                final String line = iter_str.nextLine();
                list.add(line);
            }
            iter_str.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }


}
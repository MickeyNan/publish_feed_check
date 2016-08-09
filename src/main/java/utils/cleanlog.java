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
import sun.jvm.hotspot.debugger.linux.sparc.LinuxSPARCThreadContext;
import com.google.common.collect.Sets;


public class cleanlog {


    public static void clean() {
        try {
            PrintWriter writer = new PrintWriter("./out", "UTF-8");
            for (int i = 0; i < 318; i++){
                String file_name = "logs/log" + Integer.toString(i) + ".txt";
                String out = "./total";
                try {
                    LineIterator iter_str = FileUtils.lineIterator(new File(file_name), "UTF-8");
                    while (iter_str.hasNext()) {
                        String line = iter_str.nextLine();
                        writer.println(line);

                    }

                }catch (IOException e) {

                }


            }

            writer.flush();
            writer.close();

        }catch (Exception e) {
            e.printStackTrace();
        }


    }



    public static void main(String[] args) {
        cleanlog.clean();

    }

}
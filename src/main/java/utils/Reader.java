package utils;

import java.util.ArrayList ;
import java.util.HashSet;
import java.util.List ;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;



import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Sets;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


public class Reader {
    public static String getInput(String path){
        StringBuffer sb= new StringBuffer("");
        String str = null;

        try {
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (str != null)
            return str;
        str = "No Input";
        return str;
    }
}
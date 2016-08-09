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

    public static void main(String[] args) {
        Reader reader = new Reader();
        String test = reader.getInput("src/main/resources/input.txt");
        System.out.println(test);
        List<String> necessary_strings = new ArrayList<String>();
        System.out.println(necessary_strings.size());
        Integer a = 0;
        int t = a;
        System.out.println(t);
        Set<Integer> test_set = new HashSet<Integer>();
        test_set.add(0);
        test_set.add(1);
        test_set.add(2);
        if (test_set.contains(t)){
            System.out.println("OK了");
        }
        JSONObject test_json = new JSONObject();
        test_json.put("update_flag",3);
        Set<String> string_check_set = null;
        Set<Integer> int_check_set = Sets.newHashSet(0,1,2);
        JSONObject empty_test_json = new JSONObject();
        //Long longnumber = Long.parseLong("");
        empty_test_json.put("update_time","");

        JSONObject total_json = new JSONObject();
        total_json = JSONObject.parseObject(test);
        //total_json.put("date",null);
        JSONArray array = new JSONArray();
        JSONObject result1 = checkUtils1.specificCheck(test_json, string_check_set, int_check_set, "update_flag");
        JSONObject result2 = checkUtils1.EmptyCheck(total_json,"sentences_simhas","long");
       // System.out.println(result2);


    }
}
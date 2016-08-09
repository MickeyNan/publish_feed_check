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
public class fillSpecific {

    public static void fillList(List<Map<String,String>> list,String path) {
        try {
            LineIterator iter_str = FileUtils.lineIterator(new File(path), "UTF-8");
            while (iter_str.hasNext()) {
                String line = iter_str.nextLine();
                String [] array = new String [2];
                if (line.contains(" ")) {
                    array = line.split(" ");
                    Map<String,String> map = new HashMap<String,String>();
                    map.put(array[0],array[1]);
                    list.add(map);
                }





            }

        }catch (IOException e) {

        }

    }
    public static void fill(List<Map<String,Object>> list,String path,String type) {
        try {
            LineIterator iter_str = FileUtils.lineIterator(new File(path), "UTF-8");
            while (iter_str.hasNext()){
                String line = iter_str.nextLine();
                if (line.contains(" ")) {
                    String [] array = new String [2];
                    array = line.split(" ");
                    Map<String,Object> map = new HashMap<String,Object>();
                    if (type == "int") {
                        int element = Integer.parseInt(array[1]);
                        map.put(array[0],element);

                    }else {
                        map.put(array[0],array[1]);
                    }

                    list.add(map);
                }
            }
            iter_str.close();

        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("Read Into specific_strings erro");
        }

    }

    public static JSONObject CheckSpecficElement(JSONObject json,List<Map<String,String>> list,String type) {
        JSONObject result = new JSONObject();
        result.put("message","ok");
        result.put("status",200);
        Set<Integer> int_set = Sets.newHashSet();
        Set<String> string_set = Sets.newHashSet();
        List<Set<Integer>> int_sets_list = new ArrayList<Set<Integer>>();
        List<String> int_keys_list = new ArrayList<String>();

        List<Set<String>> string_sets_list = new ArrayList<Set<String>>();
        List<String> string_keys_list = new ArrayList<String>();


        if (type == "int") {

            Iterator<Map<String,String>> iter = list.iterator();
            while(iter.hasNext()){
                Map<String,String> map = iter.next();
                String key = (String)map.keySet().toArray()[0];
                String value = (String)map.get(key);
                String[] params = value.split(",");
                Set<Integer> set = Sets.newHashSet();
                for (int i = 0; i < params.length;i++) {
                    set.add(Integer.parseInt(params[i]));

                }
                int_sets_list.add(set);
                int_keys_list.add(key);
            }

            Iterator<String> iter_keys = int_keys_list.iterator();
            while (iter_keys.hasNext()) {
                Iterator<Set<Integer>> iter_int_set = int_sets_list.iterator();
                while (iter_int_set.hasNext()) {
                    result = checkUtils1.specificCheck(json,string_set,iter_int_set.next(),iter_keys.next());
                    if (result.getInteger("status") != 200) {
                        result.put("id",json.get("id"));
                        return result;
                    }
                }

            }
        }else if (type == "string") {



            Iterator<Map<String,String>> iter = list.iterator();
            while(iter.hasNext()){
                Map<String,String> map = iter.next();
                String key = (String)map.keySet().toArray()[0];
                String value = (String)map.get(key);
                String[] params = value.split(",");
                Set<String> set = Sets.newHashSet();
                for (int i = 0; i < params.length;i++) {
                    set.add(params[i]);

                }
                string_sets_list.add(set);
                string_keys_list.add(key);
            }

            Iterator<String> iter_keys = string_keys_list.iterator();
            while (iter_keys.hasNext()) {
                Iterator<Set<String >> iter_string_set = string_sets_list.iterator();
                while (iter_string_set.hasNext()) {
                    result = checkUtils1.specificCheck(json,iter_string_set.next(),int_set,iter_keys.next());
                    if (result.getInteger("status") != 200) {
                        result.put("id",json.get("id"));
                        return result;
                    }
                }

            }

        }


        return result;

    }

    public static JSONObject CheckSpecific(JSONObject json,List<Map<String,Object>> list,String type) {
        JSONObject result = new JSONObject();
        result.put("message","ok");
        result.put("status",200);
        Set<Integer> int_set = Sets.newHashSet();
        Set<String> string_set = Sets.newHashSet();

        if (type == "int") {

            Iterator<Map<String,Object>> iter = list.iterator();
            while(iter.hasNext()){
                Map<String,Object> map = iter.next();
                String key = (String)map.keySet().toArray()[0];
                Integer value = (Integer)map.get(key);
                int_set.add(value);
            }
            result = checkUtils1.specificCheck(json,string_set,int_set,"int");
            result.put("id",json.get("id"));
        }else if (type == "string") {
            Iterator<Map<String,Object>> iter = list.iterator();
            while(iter.hasNext()){
                Map<String,Object> map = iter.next();
                String key = (String)map.keySet().toArray()[0];
                String value = (String)map.get(key);
                string_set.add(value);
            }
            result = checkUtils1.specificCheck(json,string_set,int_set,"string");
            result.put("id",json.get("id"));

        }


        return result;

    }


    public static void main(String[] args) {
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        fillSpecific.fillList(list,"src/main/resources/input.txt");
        Set<String> set = Sets.newHashSet("uc_uc","zq_uc","zq_zq","cp_cp","zq_cp");
        Set<Integer> set2 = Sets.newHashSet();
        /*
        System.out.println(set==null);
        Iterator<Map<String,Object>> iter = list.iterator();
        while(iter.hasNext()) {
            Map<String,Object> map = iter.next();
            String key = (String)map.keySet().toArray()[0];
            System.out.println(key);
            //System.out.println(iter.next());
        }*/
        JSONObject json =new JSONObject();
        String str = Reader.getInput("src/main/resources/input.txt");
        json = JSON.parseObject(str);

        JSONObject result = new JSONObject();
        result = checkUtils1.specificCheck(json,set,set2,"feed_base_datasource");
        System.out.println(result);
    }



}
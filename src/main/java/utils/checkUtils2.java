package utils;

import java.lang.String;
import java.util.*;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import utils.*;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;


public class checkUtils2 {
    public static JSONObject check_with_range(JSONObject feed,String path,String key,String type) {
        JSONObject result = new JSONObject();
        JSONObject obj = new JSONObject();
        Map<String,String> map = new HashMap<String,String>();
        result = checkUtils1.EmptyCheck(feed,key,"object");
        if (result.getInteger("status") != 200)
            return result;

        fillMap.fill(map,path);

        for (Map.Entry<String,String> entry : map.entrySet()) {
            String[] str = entry.getValue().split(",");
            result = checkUtils1.CheckSpecific(feed,str,entry.getKey(),type);
            //result = checkUtils1.specificCheck(feed,null,Sets.newHashSet(entry.getValue()),);

        }

        return result;
    }

    public static  JSONObject checkEmpyty(JSONObject feed,String key,List<String> child_keys,String record_type) {
        JSONObject result = new JSONObject();
        JSONObject obj = new JSONObject();
        result.put("message","ok");
        result.put("status",200);

        result = checkUtils1.EmptyCheck(feed,key,"object");
        if (result.getInteger("status") != 200)
            return result;

        obj = feed.getJSONObject(key);
        Iterator<String> iter = child_keys.iterator();
        while(iter.hasNext()) {
            String child_key = iter.next();
            result = checkUtils1.EmptyCheck(obj,child_key,record_type);
            if (result.getInteger("status") != 200)
                return result;
        }


        return result;

    }
}
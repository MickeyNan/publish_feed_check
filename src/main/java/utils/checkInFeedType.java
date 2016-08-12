package utils;

import java.lang.String;
import java.util.ArrayList ;
import java.util.Iterator;
import java.util.List ;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import utils.*;
import com.alibaba.fastjson.JSON;
import java.util.Set;
import com.google.common.collect.Sets;

public class checkInFeedType {
    public static JSONObject check(JSONObject feed,String key,String type) {
        JSONObject result = new JSONObject();
        result.put("message","ok");
        result.put("status",200);

        result = checkUtils1.EmptyCheck(feed,"type","string");
        if (result.getInteger("status") != 200)
            return result;

        String feed_type = feed.getString("type");
        result = checkUtils1.EmptyCheck(feed,key,type);

        return result;
    }
}
package utils;

import java.lang.String;
import java.util.ArrayList ;  
import java.util.List ;
import java.util.Map;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;


public class badcaseSelect {

	public static void select_into_map(List<Map<String,String>> list,JSONObject obj) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			int status = obj.getInteger("status");

			if(status != 200 && obj.get("id") != null) {
				map.put("id",obj.getString("id"));
				map.put("reason",obj.getString("message"));
				list.add(map);
			}

		}catch(Exception e) {
			map.put("id", obj.getString("id"));
			map.put("reason", "Exception Reason");
			list.add(map);
		}
	}
	public static void select(List<String> list,JSONObject obj) {
		try {
			int status = obj.getInteger("status");
			if(status != 200 && obj.get("id") != null) {
				list.add(obj.getString("id"));
			}

		}catch(Exception e) {
			e.printStackTrace();
			list.add(obj.getString("id"));
		}

	}

}
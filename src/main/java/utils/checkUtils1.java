package utils;

import java.lang.String;
import java.util.ArrayList ;
import java.util.Iterator;
import java.util.List ;
import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import utils.*;
import com.alibaba.fastjson.JSON;
import java.util.Set;
import com.google.common.collect.Sets;


public class checkUtils1{
	public static JSONObject EmptyCheck(JSONObject json,String key,String type){
		JSONObject result = new JSONObject();
		result.put("message","ok");
		result.put("status",200);
		try {
			switch(type){
				case "string":
					if (!(json.get(key) instanceof String) && json.get(key) != null){
						result.put("message",key + " is not String type");
						result.put("status",404);
						return result;
					}
					if (json.getString(key) == null || json.getString(key) == ""){
						result.put("message",key + " is empty");
						result.put("status",404);
						return result;
					}
					break;
				case "int":

					if (!(json.get(key) instanceof Integer) && json.get(key) != null){
						result.put("message",key + " is not Int type");
						result.put("status",404);
						return result;
					}
					if (json.getInteger(key) == null){
						result.put("message",key + " is empty");
						result.put("status",404);
						return result;
					}
					break;
				case "long":

					if (!(json.get(key) instanceof JSONObject) && json.get(key) != null) {
						result.put("message",key + " is not Long type");
						result.put("status",404);
						return result;
					}
					try {
						if(!(json.getJSONObject(key).containsKey("$numberLong"))) {
							result.put("message",key + " is not Long type");
							result.put("status",404);
							return result;

						}

						if (json.getJSONObject(key).isEmpty() || json.getJSONObject(key).getString("$numberLong") == ""){
							result.put("message",key + " is empty");
							result.put("status",404);
							return result;
						}

					} catch (Exception e) {

					}



					break;
				case "array":
					if (!(json.get(key) instanceof JSONArray) && json.get(key) != null){
						result.put("message",key + " is not Array type");
						result.put("status",404);
						return result;
					}
					if (json.getJSONArray(key) == null || json.getJSONArray(key).size() == 0){
						result.put("message",key + " is empty");
						result.put("status",404);
						return result;
					}
					break;

				case "float":
					if (!(json.get(key) instanceof Double) && json.get(key) != null) {
						result.put("message",key + " is not double type");
						result.put("status",404);
						return result;
					}
					if (json.getFloat(key) == null) {
						result.put("message",key + " is empty");
						result.put("status",404);
					}
					break;

				case "object":
					if(!(json.get(key) instanceof JSONObject) && json.get(key) != null) {
						result.put("message",key + " is not json object");
						result.put("status",404);
						return result;
					}
					if(json.getJSONObject(key) == null) {
						result.put("message",key + " is empty");
						result.put("status",404);
					}
					break;



			}
		}catch (Exception e) {
			e.printStackTrace();
			result.put("message",key + " is empty");
			result.put("status",404);
		}

		return result;
	}


	public static JSONObject CheckSpecificWithMap(Map<String,String> map,JSONObject feed,String parent_key,String type) {
		JSONObject result = new JSONObject();
		JSONObject child_feed = new JSONObject();
		try {
			child_feed = feed.getJSONObject(parent_key);

		}catch (Exception e) {
			e.printStackTrace();
			result.put("message",parent_key + " cannot be found");
			result.put("status",404);
			return result;
		}


		for (Map.Entry<String,String> entry: map.entrySet()) {
			String[] str = entry.getValue().split(",");
			result = CheckSpecific(child_feed,str,entry.getKey(),type);
			if (result.getInteger("status") != 200){
				//System.out.println(result);
				result.put("id",feed.getString("id"));
				break;
			}

		}


		if (result.getInteger("status") == 200)
			result.put("message","The Key of This Map Is All Correct");

		return result;
	}
	public static JSONObject CheckSpecific(JSONObject json,String[] rangeArray,String key,String type) {
		JSONObject result = new JSONObject();
		result.put("message",key + " is OK");
		result.put("status",200);

		if (type == "string") {
			try {
				String value = json.getString(key);
				for (String range : rangeArray) {
					if (!range.contains(value)){
						result.put("message",key + " has unknown value");
						result.put("status",401);

					}else {
						result.put("message",key + " is OK");
						result.put("status",200);
						break;
					}

				}

			}catch(Exception e) {
				e.printStackTrace();
				result.put("message", key + " has wrong value or type");
				result.put("status", 402);
			}
		} else if (type == "int" || type == "double" || type == "float") {
			try {
				Integer value = json.getInteger(key);
				String value_str = value.toString();

				for (String range : rangeArray) {
					if (!range.contains(value_str)){
						result.put("message",key + " has unknown value");
						result.put("status",401);

					}else {
						result.put("message",key + " is OK");
						result.put("status",200);
						break;
					}

				}


			}catch (Exception e) {
				e.printStackTrace();
				result.put("message", key + " has wrong value or type");
				result.put("status", 402);
			}


		}



		return result;

	}

	public static JSONObject specificCheck(JSONObject json,Set<String> string, Set<Integer> ints,String key){
		JSONObject result = new JSONObject();
		result.put("message",key + " is OK");
		result.put("status",200);

		//对特殊字段的string进行检查
		if (! (string.isEmpty() || string == null) && (ints == null || ints.isEmpty())){

			try {
				String value = json.getString(key);
				if (!string.contains(value)){
					result.put("message",key + " has unknown value");
					result.put("status",401);
				}
			}catch(Exception e){
				e.printStackTrace();
				result.put("message",key + " has wrong value or type");
				result.put("status",402);
			}

		} else if (! (ints == null || ints.isEmpty()) && (string == null || string.isEmpty())){
			//int类型的检查
			if(! (json.get(key) instanceof Integer)) {
				result.put("message",key + " is not Integer type");
				result.put("status",404);
				return result;
			}
			try {
				int value = json.getInteger(key);
				if (!ints.contains(value)){
					result.put("message",key + " has unknown value");
					result.put("status",401);
				}
			}catch(NullPointerException e){
				e.printStackTrace();
				result.put("message",key + " has wrong value or type");
				result.put("status",402);

			}

		} else {
			result.put("message","Param Error");
			result.put("status",500);
		}

		return result;
	}


	public static JSONObject onlyCheck(JSONObject json,String type,Set<Integer> set,String jsonobj,List<String> keys) {
		JSONObject result = new JSONObject();
		result.put("message","ok");
		result.put("status",200);

		if (!(json.get(jsonobj) instanceof JSONObject)) {
			result.put("message",jsonobj + " is not a JSONObject");
			result.put("status",401);
			return result;
		}

		JSONObject only_element = json.getJSONObject(jsonobj);

		Iterator<String> iter = keys.iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			result = checkUtils1.EmptyCheck(only_element,key,type);
			if (result.getInteger("status") != 200) {
				return result;
			}

			result = checkUtils1.specificCheck(only_element,null,set,key);

			if (result.getInteger("status") != 200) {
				return result;
			}
		}






		return result;
	}

}
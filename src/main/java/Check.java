import java.io.IOException;
import java.lang.String;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.PrintWriter;

import com.alibaba.fastjson.JSONObject;

import utils.*;
import com.alibaba.fastjson.JSON;

import com.google.common.collect.Sets;

public class Check{
	private List<String> necessary_strings;
	private List<String> necessary_ints;
	private List<String> necessary_arrays;
	private List<String> necessary_longs;

	private List<Map<String,String>> specific_strings;
	private List<Map<String,String>> specific_ints;

	private List<Map<String,Map<String,String>>> only_zixun;
	private List<Map<String,Map<String,String>>> only_huodong;

	//为三个list增加后续需要的key
	//待完善
	public void addElementForList(String key){

	}

	public Check(){
		necessary_strings = new ArrayList<String>();
		necessary_ints =  new ArrayList<String>();
		necessary_longs = new ArrayList<String>();
		necessary_arrays =  new ArrayList<String>();

		specific_strings = new ArrayList<Map<String, String>>();
		specific_ints = new ArrayList<Map<String, String>>();

		try {
			LineIterator iter_str = FileUtils.lineIterator(new File("src/main/java/conf/necessary_strings"), "UTF-8");
			while (iter_str.hasNext()){
				final String line = iter_str.nextLine();
				necessary_strings.add(line);
			}
			iter_str.close();

			LineIterator iter_int = FileUtils.lineIterator(new File("src/main/java/conf/necessary_ints"), "UTF-8");
			while (iter_int.hasNext()){
				final String line = iter_int.nextLine();
				necessary_ints.add(line);
			}
			iter_int.close();

			LineIterator iter_long = FileUtils.lineIterator(new File("src/main/java/conf/necessary_longs"), "UTF-8");
			while (iter_long.hasNext()){
				final String line = iter_long.nextLine();
				necessary_longs.add(line);
			}
			iter_long.close();

			LineIterator iter_array = FileUtils.lineIterator(new File("src/main/java/conf/necessary_arrays"), "UTF-8");
			while (iter_array.hasNext()){
				final String line = iter_array.nextLine();
				necessary_arrays.add(line);
			}
			iter_array.close();

		}catch (IOException e) {
			e.printStackTrace();
		}

		fillSpecific.fillList(specific_strings, "src/main/java/conf/specific_strings");
		fillSpecific.fillList(specific_ints, "src/main/java/conf/specific_ints");

		//fillOnly.fill(only_zixun,"src/main/java/conf/zixun_only");



	}

	public List<String> getStrings() {
		return this.necessary_strings;
	}

	public List<String> getInts() {
		return this.necessary_ints;
	}

	public List<String> getLongs() {
		return this.necessary_longs;
	}

	public List<String> getArrays() {
		return this.necessary_arrays;
	}

	public List<Map<String,String>> getSpecificStrings() {return this.specific_strings;}

	public List<Map<String,String>> getSpecificInts() {return this.specific_ints;}


	public JSONObject CheckList(JSONObject json,List<String> list,String type){
		//若list的值为0
		if (list.size() == 0){

		}
		JSONObject result = new JSONObject();
		result.put("message","ok");
		result.put("status",200);
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()){
			result = checkUtils1.EmptyCheck(json,iter.next(),type);
			if (result.getInteger("status") != 200) {
				result.put("id",json.get("id"));
			}
		}
		return result;
	}

	public  JSONObject CheckSpecficElement(JSONObject json,List<Map<String,String>> list,String type) {
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

	/*
	public JSONObject checkOnlyElement() {
		List<String> list = new ArrayList<String>();

	}*/
	public static void main(String [] args) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		List<String> badcase_ids = new ArrayList<String>();
		Map<String,String>badcase_map = new HashMap<String,String>();
		List<Map<String,String>> list_of_map = new ArrayList<Map<String, String>>();
		JSONObject string_result = new JSONObject();
		JSONObject int_result = new JSONObject();
		JSONObject long_result = new JSONObject();
		JSONObject array_result = new JSONObject();

		JSONObject specific_int_result = new JSONObject();
		JSONObject specific_string_result = new JSONObject();

		JSONObject only_zixun_result = new JSONObject();

		Check feed_check = new Check();

		List<String> item_meta_info_keys = new ArrayList<String>(Arrays.asList("timeliness","hot","quality","authority","sensibility"));
		List<String> text_feature_info_keys = new ArrayList<String>(Arrays.asList("spammer","vulgars","wide_mark","ad","short","repeat","sensitivity_pol"));

		for (int i = 291; i <= 300; i++) {

			list = getData.get("100.67.79.26",8080,"publish","article","publisher","GN6Arp9147MtYE46LY12",10000,i*10000);//从数据库中获取数据
			Iterator<JSONObject> iter = list.iterator();
			while (iter.hasNext()) {
				JSONObject json = iter.next();
				string_result = feed_check.CheckList(json,feed_check.getStrings(),"string");
				int_result = feed_check.CheckList(json,feed_check.getInts(),"int");
				long_result = feed_check.CheckList(json,feed_check.getLongs(),"long");
				array_result = feed_check.CheckList(json,feed_check.getArrays(),"array");
				specific_int_result = feed_check.CheckSpecficElement(json,feed_check.getSpecificInts(),"int");
				specific_string_result = feed_check.CheckSpecficElement(json,feed_check.getSpecificStrings(),"string");

				if (json.getString("type") == "news") {

					only_zixun_result = checkUtils1.onlyCheck(json, "int",Sets.newHashSet(0, 1, 2, 3, 4, 5), "item_meta_info", item_meta_info_keys);
					only_zixun_result = checkUtils1.onlyCheck(json, "int",Sets.newHashSet(0, 1, 2, 3, 4, 5), "text_feature_info", item_meta_info_keys);

				}

				//System.out.println(long_result);
				badcaseSelect.select_into_map(list_of_map,string_result);
				badcaseSelect.select_into_map(list_of_map, int_result);
				badcaseSelect.select_into_map(list_of_map, long_result);
				badcaseSelect.select_into_map(list_of_map, array_result);

				//System.out.println(specific_string_result);
				badcaseSelect.select_into_map(list_of_map, specific_int_result);
				badcaseSelect.select_into_map(list_of_map, specific_string_result);

				//badcaseSelect.select_into_map(list_of_map, only_zixun_result);
				//badcaseSelect.select_into_map(list_of_map, only_zixun_result);

				//System.out.println(list_of_map.size());

				



			}

			if (!list_of_map.isEmpty()) {
				try {
					PrintWriter writer = new PrintWriter("log"+Integer.toString(i)+".txt", "UTF-8");
					Iterator<Map<String,String>> iter_for_list = list_of_map.iterator();
					while (iter_for_list.hasNext()) {
						String str = iter_for_list.next().toString();
						writer.println(str);
					}
					writer.flush();
					writer.close();

				}catch (Exception e) {
					e.printStackTrace();
				}

			}


			list.clear();
			list_of_map.clear();

		}

		//System.out.println(list_of_map.size());




	}



}
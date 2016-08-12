package app;

import java.io.IOException;
import java.lang.String;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.PrintWriter;

import com.alibaba.fastjson.JSONObject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utils.*;
import com.alibaba.fastjson.JSON;

import com.google.common.collect.Sets;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Check{
	private List<String> news_strings;
	private List<String> active_strings;
	private List<String> news_ints;
	private List<String> active_ints;
	private List<String>  news_arrays;
	private List<String> active_arrays;
	private List<String> news_longs;
	private List<String> active_longs;

	private List<String> news_objects;
	private List<String> active_objects;

	private Map<String,String> news_item_meta_info;
	private Map<String,String> news_text_feature_info;


	private List<Map<String,String>> specific_strings;
	private List<Map<String,String>> specific_ints;

	private List<Map<String,Map<String,String>>> only_zixun;
	private List<Map<String,Map<String,String>>> only_huodong;

	//为三个list增加后续需要的key
	//待完善
	public void addElementForList(String key){

	}

	public Check(){
		news_strings = new ArrayList<String>();
		active_strings = new ArrayList<String>();
		news_ints = new ArrayList<String>();
		active_ints = new ArrayList<String>();
		news_longs = new ArrayList<String>();
		active_longs = new ArrayList<String>();
		news_arrays = new ArrayList<String>();
		active_arrays = new ArrayList<String>();

		news_objects = new ArrayList<String>();
		active_objects = new ArrayList<String>();

		news_item_meta_info = new HashMap<String,String>();
		news_text_feature_info = new HashMap<String,String>();
		String online_parent_path = "/home/yannan.wyn/publish_feed_check/";

		fillList.fill(news_strings,online_parent_path + "src/main/java/conf/news_strings");
		fillList.fill(active_strings,online_parent_path + "src/main/java/conf/active_strings");
		fillList.fill(news_ints,online_parent_path + "src/main/java/conf/news_ints");
		fillList.fill(active_ints,online_parent_path + "src/main/java/conf/active_ints");
		fillList.fill(news_longs,online_parent_path + "src/main/java/conf/news_longs");
		fillList.fill(active_longs,online_parent_path + "src/main/java/conf/active_longs");
		fillList.fill(news_arrays,online_parent_path + "src/main/java/conf/news_arrays");
		fillList.fill(active_arrays,online_parent_path + "src/main/java/conf/active_arrays");

		fillList.fill(news_objects,online_parent_path + "src/main/java/conf/news_objects");
		fillList.fill(active_objects,online_parent_path + "src/main/java/conf/active_objects");

		fillMap.fill(news_item_meta_info,online_parent_path + "src/main/java/conf/news_item_meta_info");
		fillMap.fill(news_text_feature_info,online_parent_path + "src/main/java/conf/news_text_feature_info");



		specific_strings = new ArrayList<Map<String, String>>();
		specific_ints = new ArrayList<Map<String, String>>();


		fillSpecific.fillList(specific_strings, online_parent_path + "src/main/java/conf/specific_strings");
		fillSpecific.fillList(specific_ints, online_parent_path + "src/main/java/conf/specific_ints");
	}

	public List<String> getNewsStrings() {return this.news_strings;}
	public List<String> getActiveStrings() {return this.active_strings;}
	public List<String> getNewsInts() {
		return this.news_ints;
	}
	public List<String> getActiveInts() {
		return this.active_ints;
	}

	public List<String> getNewsLongs() {
		return this.news_longs;
	}

	public List<String> getActiveLongs() {
		return this.active_longs;
	}

	public List<String> getNewsArrays() {
		return this.news_arrays;
	}

	public List<String> getActiveArrays() {
		return this.active_arrays;
	}

	public List<String> getNewsObjects() {
		return this.news_objects;
	}

	public List<String> getActiveObjects() {
		return this.active_objects;
	}

	public Map<String,String> getNewsItemMetaInfo() {
		return this.news_item_meta_info;
	}

	public Map<String,String> getNewsTextFeatureInfo() {
		return this.news_text_feature_info;
	}

	public List<Map<String,String>> getSpecificStrings() {return this.specific_strings;}

	public List<Map<String,String>> getSpecificInts() {return this.specific_ints;}


	public JSONObject CheckInList(JSONObject json,List<String> list,String type){
		//若list的值为0
		if (list.size() == 0){

		}
		JSONObject result = new JSONObject();
		result.put("message","ok");
		result.put("status",200);
		Iterator<String> iter = list.iterator();
		while (iter.hasNext()){
			result = checkInFeedType.check(json,iter.next(),type);
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

	@Scheduled(cron = "0 33 14 * * ?")
	public  void run() {
		String online_parent_path = "/home/yannan.wyn/publish_feed_check/";
		getData getdata = new getData();
		getDate which_day = new getDate();
		String start_date = which_day.get_start_time();
		String end_date = which_day.get_end_time();

		int count = 0;

		List<JSONObject> list = new ArrayList<JSONObject>();
		List<String> badcase_ids = new ArrayList<String>();
		Map<String,String>badcase_map = new HashMap<String,String>();
		List<Map<String,String>> list_of_map = new ArrayList<Map<String, String>>();

		JSONObject news_string_result = new JSONObject();
		JSONObject news_int_result = new JSONObject();
		JSONObject news_long_result = new JSONObject();
		JSONObject news_array_result = new JSONObject();
		JSONObject active_string_result = new JSONObject();
		JSONObject active_int_result = new JSONObject();
		JSONObject active_long_result = new JSONObject();
		JSONObject active_array_result = new JSONObject();

		JSONObject news_object_result = new JSONObject();
		JSONObject active_object_result = new JSONObject();

		JSONObject news_item_meta_info_result = new JSONObject();
		JSONObject news_text_feature_info_result = new JSONObject();

		JSONObject specific_int_result = new JSONObject();
		JSONObject specific_string_result = new JSONObject();

		JSONObject only_zixun_result = new JSONObject();

		Check feed_check = new Check();

		List<String> item_meta_info_keys = new ArrayList<String>(Arrays.asList("timeliness","hot","quality","authority","sensibility"));
		List<String> text_feature_info_keys = new ArrayList<String>(Arrays.asList("spammer","vulgars","wide_mark","ad","short","repeat","sensitivity_pol"));


		List<Map<String,String>> server_params = new ArrayList<Map<String, String>>();

		fillSpecific.fillList(server_params,online_parent_path + "/src/main/java/conf/mongo_server_config");


		while(true) {
			list = getdata.get(start_date,10000,"src/main/java/conf/mongo_server_config");//从数据库中获取数据

			start_date = getdata.get_last_time();
			Iterator<JSONObject> iter = list.iterator();

			while (iter.hasNext()) {
				JSONObject json = iter.next();
				if (json.getString("type").contains("news")){
					news_string_result = feed_check.CheckInList(json, feed_check.getNewsStrings(), "string");
					news_int_result = feed_check.CheckInList(json, feed_check.getNewsInts(), "int");
					news_long_result = feed_check.CheckInList(json, feed_check.getNewsLongs(), "long");
					news_array_result = feed_check.CheckInList(json, feed_check.getNewsArrays(), "array");
					news_object_result = feed_check.CheckInList(json,feed_check.getNewsObjects(),"object");
					news_item_meta_info_result = checkUtils1.CheckSpecificWithMap(feed_check.getNewsItemMetaInfo(),json,"item_meta_info","double");
					news_text_feature_info_result = checkUtils1.CheckSpecificWithMap(feed_check.getNewsTextFeatureInfo(),json,"text_feature_info","int");


					badcaseSelect.select_into_map(list_of_map,news_string_result);
					badcaseSelect.select_into_map(list_of_map, news_int_result);
					badcaseSelect.select_into_map(list_of_map,news_long_result);
					badcaseSelect.select_into_map(list_of_map, news_array_result);
					badcaseSelect.select_into_map(list_of_map, news_object_result);
					badcaseSelect.select_into_map(list_of_map, news_item_meta_info_result);
					badcaseSelect.select_into_map(list_of_map, news_text_feature_info_result);

				}else if(json.getString("type").contains("active")) {
					active_string_result = feed_check.CheckInList(json, feed_check.getActiveStrings(), "string");
					active_int_result = feed_check.CheckInList(json, feed_check.getActiveInts(), "int");
					active_long_result = feed_check.CheckInList(json, feed_check.getActiveLongs(), "long");
					active_array_result = feed_check.CheckInList(json, feed_check.getActiveArrays(), "array");
					active_object_result = feed_check.CheckInList(json, feed_check.getActiveObjects(), "object");

					badcaseSelect.select_into_map(list_of_map, active_string_result);
					badcaseSelect.select_into_map(list_of_map, active_int_result);
					badcaseSelect.select_into_map(list_of_map, active_long_result);
					badcaseSelect.select_into_map(list_of_map, active_array_result);
					badcaseSelect.select_into_map(list_of_map, active_object_result);
				}



				specific_int_result = feed_check.CheckSpecficElement(json,feed_check.getSpecificInts(),"int");
				specific_string_result = feed_check.CheckSpecficElement(json,feed_check.getSpecificStrings(),"string");





				badcaseSelect.select_into_map(list_of_map, specific_int_result);
				badcaseSelect.select_into_map(list_of_map, specific_string_result);





			}


			if (!list_of_map.isEmpty()) {
				try {
					PrintWriter writer = new PrintWriter("log"+Integer.toString(count)+".txt", "UTF-8");
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

			count += 1;
			System.out.println(start_date);
			if (list.size() < 10000)
				break;

			System.out.println(list.size());
			System.out.println(list_of_map.size());
			list.clear();
			list_of_map.clear();


		}


		cleanlog.clean(count);



	}



}
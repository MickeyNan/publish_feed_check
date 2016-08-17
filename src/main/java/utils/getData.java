package utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.FindIterable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;


import org.bson.Document;


public class getData {
	private String last_time;
	public String get_last_time() {
		return this.last_time;
	}
	public  List<JSONObject> get(String start_date,int limit,String path){
		List<JSONObject> list = new ArrayList<JSONObject>();
		Map<String,String> param_values = new HashMap<String,String>();

		try {
			LineIterator iter_str = FileUtils.lineIterator(new File(path), "UTF-8");
			while (iter_str.hasNext()) {
				String line = iter_str.nextLine();
				String [] array = new String [2];
				if (line.contains(" ")) {
					array = line.split(" ");
					param_values.put(array[0],array[1]);

				}
				System.out.println(array[1]);

			}

		}catch (IOException e) {
			e.printStackTrace();

		}

		BasicDBObject query = new BasicDBObject();

		query.put("update_time",new BasicDBObject("$gte",start_date));

		try {

			MongoCredential credentialOne = MongoCredential.createCredential(param_values.get("user_name"), param_values.get("db_name"),param_values.get("password").toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress(param_values.get("address"),Integer.parseInt(param_values.get("port"))),Arrays.asList(credentialOne));
			System.out.println(param_values.get("address"));

			//Mongo mongo = new Mongo("localhost", 27017);
			MongoDatabase db = mongoClient.getDatabase(param_values.get("db_name"));
			MongoCollection<Document> collection = db.getCollection(param_values.get("collection_name"));
			FindIterable<Document> findIterable = collection.find(query).limit(limit);
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			JSONObject json = new JSONObject();

			while (mongoCursor.hasNext()){

				json = JSON.parseObject(mongoCursor.next().toJson().toString());
				list.add(json);
				//System.out.println(json);


			}

			this.last_time = json.getString("update_time");

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			return list;
		}

	}

}
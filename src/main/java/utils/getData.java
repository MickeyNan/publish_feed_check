package utils;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
	public static List<JSONObject> get(String update_date,int limit,int skip,String path){
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



		try {

			MongoCredential credentialOne = MongoCredential.createCredential(param_values.get("user_name"), param_values.get("db_name"),param_values.get("password").toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress(param_values.get("addres"),Integer.parseInt(param_values.get("port"))),Arrays.asList(credentialOne));


			//Mongo mongo = new Mongo("localhost", 27017);
			MongoDatabase db = mongoClient.getDatabase(param_values.get("db_name"));
			MongoCollection<Document> collection = db.getCollection(param_values.get("collection_name"));
			FindIterable<Document> findIterable = collection.find().limit(limit).skip(skip);
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()){
				JSONObject json = new JSONObject();
				json = JSON.parseObject(mongoCursor.next().toJson().toString());
				list.add(json);

			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			return list;
		}

	}

	public static void main(String[] args) {
		List<JSONObject> list = getData.get(1,1,"src/main/java/conf/mongo_server_config");
		System.out.println(list);

	}

}
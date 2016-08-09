package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import java.util.Arrays;


import org.bson.Document;


public class getData {
	public static List<JSONObject> get(String addres,int port,String db_name,String collection_name,String user_name,String password,int limit,int skip){
		List<JSONObject> list = new ArrayList<JSONObject>();
		List<Map<String,String>> params = new ArrayList<Map<String,String>>();
		params = 


		try {

			MongoCredential credentialOne = MongoCredential.createCredential(user_name, db_name,password.toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress(addres,port),Arrays.asList(credentialOne));


			//Mongo mongo = new Mongo("localhost", 27017);
			MongoDatabase db = mongoClient.getDatabase(db_name);
			MongoCollection<Document> collection = db.getCollection(collection_name);
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
		List<JSONObject> list = getData.get("100.67.79.26",8080,"publish","article","publisher","GN6Arp9147MtYE46LY12",100,2900000);
	}

}
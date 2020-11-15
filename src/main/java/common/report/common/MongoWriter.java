package common.report.common;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import groovy.util.logging.Slf4j;
import org.bson.Document;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/11/15 15:37
 */
@Slf4j
public class MongoWriter {

    MongoClient mongoClieng;
    MongoDatabase mongoDatabase;
    private static MongoWriter mongoWriter;


    public static MongoWriter getMongoWriter(String host, int port) {
        if (mongoWriter == null) {
            mongoWriter = new MongoWriter(host, port);
        }
        return mongoWriter;
    }


    public MongoWriter(String host, int port) {
        this.mongoClieng = new MongoClient("localhost", 27017);
        this.mongoDatabase = mongoClieng.getDatabase("Automation");
    }

    public void write(String collectionName, Object objectVale) {
        GsonBuilder gsonBuilder =new GsonBuilder();
        Gson gson = gsonBuilder.create();
        MongoCollection collection = mongoDatabase.getCollection(collectionName);
        Document document = Document.parse(gson.toJson(objectVale));
        collection.insertOne(document);
    }
}

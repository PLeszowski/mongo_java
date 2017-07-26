package pl.rozkocha.szymon.mongo_java;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.BsonArray;
import org.bson.BsonDouble;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    		/*MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");
			MongoClient mongoClient = new MongoClient(uri);*/
			MongoClient mongoClient = new MongoClient();
			System.out.println("Connected!");
			
			List<String> names = mongoClient.getDatabaseNames();
			
			names.forEach((name) -> {
				System.out.println(name);
			});
			
			System.out.println();
			
			MongoDatabase people = mongoClient.getDatabase("people");
			MongoIterable<String> collectionNames = people.listCollectionNames();
			
			collectionNames.forEach((String name) -> {
				System.out.println(name);
			});
			
			MongoCollection<Document> p = people.getCollection("people");
			
			/*BsonArray a = new BsonArray();
			a.add(new BsonDouble(12.0));
			a.add(new BsonDouble(3.0));
			a.add(new BsonDouble(71.0));
			a.add(new BsonDouble(66.0));
			
			Document document = new Document()
					.append("name", "Test")
					.append("surname", "Testowy")
					.append("numbers", a);
			
			p.insertOne(document);*/
			
			FindIterable<Document> documents = p.find(new BasicDBObject("_id", new ObjectId("5978da71015b181ea8adf73b")));
			documents.forEach((Document d) -> {
				System.out.println(d);
				d.replace("name", "Test2");
				p.replaceOne(new BasicDBObject("_id", new ObjectId("5978da71015b181ea8adf73b")), d);
			});
			
			p.find().forEach((Document d) -> {
				System.out.println("Document:");
				d.entrySet().forEach((entry) -> {
					System.out.println(entry.getKey() + ": " + entry.getValue().toString());
				});
			});
    }
}

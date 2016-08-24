import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.text.ParseException;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;


/**
 * Created by nohorbee on 22/08/16.
 */
public class RemoveData {

    public static void main(String...args) throws ParseException {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");

        //deleteMultiple(db);
        deleteAll(db);
        dropCollection(db);


    }

    private static void dropCollection(MongoDatabase db) {
        System.out.println(db.getCollection("restaurants"));
        db.getCollection("restaurants").drop();
        System.out.println(db.getCollection("restaurants"));
    }

    private static void deleteAll(MongoDatabase db) {
        DeleteResult result = db.getCollection("restaurants").deleteMany(new Document());
        System.out.println(db.getCollection("restaurants").count());
    }

    private static void deleteMultiple(MongoDatabase db) {
        DeleteResult result = db.getCollection("restaurants").deleteMany(eq("borough", "Manhattan"));
        System.out.println(result);
    }


}

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.ParseException;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Sorts.ascending;


/**
 * Created by nohorbee on 22/08/16.
 */
public class UpdateData {

    public static void main(String...args) throws ParseException {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");

        //updateTopLevel(db);

        //updateEmbedded(db);

        updateMultiple(db);


    }

    private static void updateMultiple(MongoDatabase db) {



        Bson originalCriteria = and(eq("address.zipcode", "10016"), eq("cuisine","Category To Be Determined"));
        Bson resultCriteria = and(eq("address.zipcode", "10016"), eq("cuisine","Other"));

        System.out.println("Count of original criteria before update:" + db.getCollection("restaurants").count(originalCriteria));
        System.out.println("Count of result criteria before update:" + db.getCollection("restaurants").count(resultCriteria));

        UpdateResult result = db.getCollection("restaurants").updateMany(
                originalCriteria,
                new Document("$set", new Document("cuisine","Other"))
                .append("$currentDate", new Document("lastModified", true))
        );

        System.out.println("Count of original criteria after update:" + db.getCollection("restaurants").count(originalCriteria));
        System.out.println("Count of result criteria after update:" + db.getCollection("restaurants").count(resultCriteria));

        System.out.println(result);

    }

    private static void updateEmbedded(MongoDatabase db) {
        UpdateResult result = db.getCollection("restaurants").updateOne(
                new Document("restaurant_id", "41156888"),
                new Document("$set", new Document("address.street", "East 31st Street"))
        );

        System.out.println(result);
    }

    private static void updateTopLevel(MongoDatabase db) {
        UpdateResult result = db.getCollection("restaurants").updateOne(
                new Document("name", "Vella"),
                new Document("$set",
                        new Document("cuisine", "American (New)"))
                .append("$currentDate", new Document("lastModified", true))
        );

        System.out.println(result);
    }

}

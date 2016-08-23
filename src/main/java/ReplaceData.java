import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.ParseException;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


/**
 * Created by nohorbee on 22/08/16.
 */
public class ReplaceData {

    public static void main(String...args) throws ParseException {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");

        replace(db);


    }

    private static void replace(MongoDatabase db) {

        db.getCollection("restaurants").replaceOne(
                eq("restaurant_id", "41704620"),
                new Document("address",
                        new Document()
                        .append("street", "2 Avenue")
                        .append("zipcode", "10075")
                        .append("building", "1480")
                        .append("coord", asList())
                        )
        );

    }

}

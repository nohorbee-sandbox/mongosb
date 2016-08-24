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
import static java.util.Arrays.asList;


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

        FindIterable iterable = db.getCollection("restaurants").find(eq("name","Vella 2"));
        iterable.forEach((Block<Document>) System.out::println);


        UpdateResult result = db.getCollection("restaurants").replaceOne(
                eq("restaurant_id", "41704620"),
                new Document("address",
                        new Document()
                        .append("street", "2 Avenue")
                        .append("zipcode", "10075")
                        .append("building", "1480")
                        .append("coord", asList(-73.9557413, 40.7720266))
                        )
                .append("name", "Vella 2")
        );

        iterable = db.getCollection("restaurants").find(eq("name","Vella 2"));
        iterable.forEach((Block<Document>) System.out::println);
        System.out.println(result);

    }

}

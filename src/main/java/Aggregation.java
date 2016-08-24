import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static java.util.Arrays.asList;

/**
 * Created by nohorbee on 23/08/16.
 */
public class Aggregation {

    public static void main(String...args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");

        AggregateIterable iterable = db.getCollection("restaurants").aggregate(asList(
                Aggregates.match(and(eq("borough","Queens"),eq("cuisine","Brazilian"))),
                Aggregates.group("$address.zipcode", new BsonField("count", new Document("$sum",1)))
                )
        );

        iterable.forEach((Block<Document>)(System.out::println));
    }
}

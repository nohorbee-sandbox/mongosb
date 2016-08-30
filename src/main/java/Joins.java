import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;

import org.bson.Document;


import static com.mongodb.client.model.Projections.fields;
import static java.util.Arrays.asList;

/**
 * Created by nohorbee on 24/08/16.
 */
public class Joins {
    public static void main(String...args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");

          prepareCollections(db);

        AggregateIterable iterable = db.getCollection("restaurants").aggregate(asList(
                Aggregates.lookup("zipcount", "address.zipcode", "_id", "joinCount"),
                Aggregates.project(fields(new Document("name",1).append("zipcode","$address.zipcode").append("joinCount","$joinCount.count")))
                //Aggregates.project(new Document("name",1).append("zipcode","$address.zipcode").append("joinCount","$joinCount.count"))


        ));

        iterable.forEach((Block<Document>)System.out::println);





    }

    private static void cleanScenario(MongoDatabase db) {
        db.getCollection("zipcount").drop();
    }

    private static void prepareCollections(MongoDatabase db) {
        AggregateIterable iterable = db.getCollection("restaurants").aggregate(asList(
                Aggregates.group("$address.zipcode",
                        new BsonField("count", new Document("$sum",1)))
                )
        );

        if (db.getCollection("zipcount")!=null) cleanScenario(db);

        db.createCollection("zipcount");

        iterable.forEach((Block<Document>) (d)-> {
            db.getCollection("zipcount").insertOne(new Document("_id", d.get("_id")).append("count",d.get("count")));
        });
    }
}

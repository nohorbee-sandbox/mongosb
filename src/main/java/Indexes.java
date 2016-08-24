import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

/**
 * Created by nohorbee on 24/08/16.
 */
public class Indexes {

    public static void main(String...args) {

        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");

        MongoCollection<Document> restaurants = db.getCollection("restaurants");

        int[] count = {0,0};

        BenchmarkUtil.tic("no-index");
        FindIterable iterable = restaurants.find(and(eq("cuisine","Brazilian"),eq("address.zipcode","10019")));
        BenchmarkUtil.tac("no-index");

        iterable.forEach((Block<Document>) (d)->{count[0]++;});


        restaurants.createIndex(new Document("cuisine",1).append("address.zipcode",-1), new IndexOptions().name("perfIndex"));


        BenchmarkUtil.tic("index");
        FindIterable iterable2 = restaurants.find(and(eq("cuisine","Brazilian"),eq("address.zipcode","10019")));
        BenchmarkUtil.tac("index");

        iterable2.forEach((Block<Document>) (d)->{count[1]++;});

        restaurants.dropIndex("perfIndex");

        System.out.println("PERFORMANCE TESTING RESULTS");
        System.out.println("===========================");
        System.out.println("\n");
        System.out.println("Without Indexes: ");
        System.out.println("- Count: " + count[0]);
        System.out.println("- Time: " + BenchmarkUtil.getElapsedTime("no-index") + " ns = "+ TimeUnit.MILLISECONDS.convert(BenchmarkUtil.getElapsedTime("no-index"), TimeUnit.NANOSECONDS) +" ms");
        System.out.println("\n");
        System.out.println("Without Indexes: ");
        System.out.println("- Count: " + count[1]);
        System.out.println("- Time: " + BenchmarkUtil.getElapsedTime("index") + " ns = "+ TimeUnit.MILLISECONDS.convert(BenchmarkUtil.getElapsedTime("index"), TimeUnit.NANOSECONDS) +" ms");





    }

}


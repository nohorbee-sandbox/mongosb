import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;
import static java.util.Arrays.asList;


/**
 * Created by nohorbee on 22/08/16.
 */
public class FindData {

    public static void main(String...args) throws ParseException {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("test");
        final int[] count={0};

        Bson criteria = or(eq("cuisine","Italian"),eq("address.zipcode","10075"));

        FindIterable<Document> iterable = db.getCollection("restaurants")
                .find(criteria)
                .sort(ascending("borough","address.zipcode"))
                ;


        iterable.forEach((Block<Document>)(Document document)->{
            System.out.println(document);
            count[0]++;
        });
        System.out.println("Record Count" + db.getCollection("restaurants").count(criteria));
        System.out.println("Total Records: " + db.getCollection("restaurants").count());
    }

}

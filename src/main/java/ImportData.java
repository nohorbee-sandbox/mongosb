import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by nohorbee on 23/08/16.
 */
public class ImportData {
    public static void main(String...args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("test");
        db.createCollection("restaurants");
        ImportData importer = new ImportData();
        importer.run(db.getCollection("restaurants"));

    }

    public void run(MongoCollection<Document> collection) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("primer-dataset.json").getFile());

        try {
            Scanner scanner = new Scanner(file);
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                collection.insertOne(Document.parse(line));
                System.out.println(line);
            }

        } catch (FileNotFoundException e) {
            System.out.println("The primer-dataset.json file couldn't be found");
            System.out.println(e.getStackTrace());
        }
    }


}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadCommand implements Command{

    private String path;

    public LoadCommand(String path){
        this.path = path;
    }

    public Catalog catalogLoader(){
        Catalog catalog = new Catalog();

        try {
            FileReader reader = new FileReader(path + ".txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String documentName;
            String documentPath;

            while ((documentName = bufferedReader.readLine()) != null) {
                documentPath = bufferedReader.readLine();
                Document document = new Document(documentName, documentPath);
                catalog.addDocument(document);
            }
            reader.close();

        } catch (IOException e) {
            catalog = null;
            System.out.println("Catalog not found");
        }
        return catalog;
    }
}

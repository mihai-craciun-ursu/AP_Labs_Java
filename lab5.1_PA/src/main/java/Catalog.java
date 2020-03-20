
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Catalog implements Serializable{

    private static final long serialVersionUID = 1L;
    private List<Document> listOfDocuments;

    public Catalog() {
        this.listOfDocuments = new ArrayList<>();
    }

    public Catalog(List<Document> listOfDocumentsl) {
        this.listOfDocuments = listOfDocumentsl;
    }

    public List<Document> getListOfDocuments() {
        return listOfDocuments;
    }

    public void addDocument(Document document){
        listOfDocuments.add(document);
    }



    @Override
    public String toString() {
        return "Catalog{" +
                "listOfDocuments=" + listOfDocuments +
                '}';
    }
}

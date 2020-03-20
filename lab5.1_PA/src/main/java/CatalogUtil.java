import java.awt.*;
import java.io.*;

public class CatalogUtil {


    private Catalog catalog;

    public CatalogUtil(Catalog catalog){
        this.catalog = catalog;
    }

    public void save(String name){
        try{
            FileOutputStream file = new FileOutputStream(name);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(catalog);

            out.close();
            file.close();

            System.out.println("Object has been serialized");
        }catch(IOException e){
            System.out.println("IOException");
        }
    }

    public Catalog load(String filename){
        Catalog catalog = null;

        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            catalog = (Catalog)in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized ");
            System.out.println(catalog);
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

        this.catalog = catalog;
        return catalog;
    }

    public void view(int index){
        if(index <= catalog.getListOfDocuments().size() - 1) {
            File file = new File(catalog.getListOfDocuments().get(index).getDocumentLocation());

            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) {
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Catalog getCatalog() {
        return catalog;
    }
}

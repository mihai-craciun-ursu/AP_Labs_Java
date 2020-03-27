import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ViewCommand implements Command {

    private Catalog catalog;

    public ViewCommand(Catalog catalog) {
        this.catalog = catalog;
    }

    public void viewCatalog(int index) throws IndexOutOfCatalogSizeException {
        if(index >= catalog.getListOfDocuments().size()){
            throw new IndexOutOfCatalogSizeException("Index out of catalog bound");
        }else{
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
}

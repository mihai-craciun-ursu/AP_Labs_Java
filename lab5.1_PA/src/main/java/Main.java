

public class Main {
    public static void main(String args[]){

        Document document = new Document("ALK", DocumentType.INTERNAL, "E:\\Alk\\java-semantics\\doc\\alk.pdf");
        Catalog catalogToSave = new Catalog();

        catalogToSave.addDocument(document);

        CatalogUtil catalogUtil = new CatalogUtil(catalogToSave);

        catalogUtil.save("catalog");


        Catalog catalog = catalogUtil.load("catalog");

        catalogUtil.view(0);

    }


}

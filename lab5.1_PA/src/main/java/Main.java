import java.util.Scanner;

public class Main {

    private static String inputCommand = "";
    private static Catalog workingCatalog;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]){

        Document document = new Document("ALK", "E:\\Alk\\java-semantics\\doc\\alk.pdf");
        Document document2 = new Document("Legal", "C:\\Users\\craci\\Downloads\\legal.docx");
        Document document3 = new Document("Tema Engleza", "C:\\Users\\craci\\Downloads\\tema-engleza.pdf");
        Document document4 = new Document("Meme", "C:\\Users\\craci\\Downloads\\meme.jpg");

        Catalog catalogToSave = new Catalog();

        catalogToSave.addDocument(document);
        catalogToSave.addDocument(document2);
        catalogToSave.addDocument(document3);
        catalogToSave.addDocument(document4);

        CatalogUtil catalogUtil = new CatalogUtil(catalogToSave);

        catalogUtil.saveText("catalog");



        while(!inputCommand.equals("exit")){
            System.out.println("\nInput command (or type help for a list of available commands) ");
            inputCommand = scanner.nextLine();

            if(inputCommand.startsWith("load ")){
                LoadCommand loadCommand = new LoadCommand(inputCommand.substring(5));
                workingCatalog = loadCommand.catalogLoader();
                if(workingCatalog != null){
                    System.out.println("Catalog loaded successfully");
                }

            }else if(inputCommand.startsWith("view ")){
                if(workingCatalog != null){
                    ViewCommand viewCommand = new ViewCommand(workingCatalog);
                    try {
                        viewCommand.viewCatalog(Integer.parseInt(inputCommand.substring(5)));
                    } catch (IndexOutOfCatalogSizeException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Catalog not loaded yet. Please Load a catalog first");
                }
            }else if(inputCommand.startsWith("list")){
                if(workingCatalog != null){
                    for(Document documentIt : workingCatalog.getListOfDocuments()){
                        System.out.println(workingCatalog.getListOfDocuments().indexOf(documentIt) +
                                ". " + documentIt.getName());
                    }
                }else{
                    System.out.println("Catalog not loaded yet. Please Load a catalog first");
                }

            }else if(inputCommand.startsWith("report html")){
                if(workingCatalog != null) {
                    ReportCommand reportCommand = new ReportCommand(workingCatalog);
                    reportCommand.generateHtmlReport("htmlRep");
                }else{
                    System.out.println("Catalog not loaded yet. Please Load a catalog first");
                }
            }else if(inputCommand.startsWith("help")){
                System.out.println("load <catalog path> -> load a catalog from file to be used");
                System.out.println("view <index in document> -> opens the selected document with the default program");
                System.out.println("list -> list all documents in the selected catalog");
                System.out.println("report html -> generates a html report of the current selected document");
            }else if(inputCommand.startsWith("exit")){

            }else{
                System.out.println("Invalid command. Please type a valid command or type 'help' to see a list of all available commands");
            }
        }

    }


}

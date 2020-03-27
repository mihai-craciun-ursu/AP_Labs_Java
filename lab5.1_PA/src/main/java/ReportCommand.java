import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportCommand implements Command {

    private Catalog catalog;

    public ReportCommand(Catalog catalog) {
        this.catalog = catalog;
    }

    public void generateHtmlReport(String fileNameToBeSaved){
        try {
            FileWriter writer = new FileWriter(fileNameToBeSaved + ".html", false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>Catalog</title>\n" +
                    "</head>\n" +
                    "<body><ul>");
            for(Document document : catalog.getListOfDocuments()) {
                bufferedWriter.write("<li><h2>" + catalog.getListOfDocuments().indexOf(document) + ". " + document.getName() +"</h2>");
                bufferedWriter.newLine();
                bufferedWriter.write("<h3>" + document.getDocumentLocation() +"</h3></li>");
            }

            bufferedWriter.write("</ul></body></head>");

            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Could not generate report");
        }


            File file = new File(fileNameToBeSaved + ".html");

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

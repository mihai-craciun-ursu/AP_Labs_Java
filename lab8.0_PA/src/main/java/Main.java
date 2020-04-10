import com.github.javafaker.Faker;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();
        Random random = new Random();

        ChartController chartController = new ChartController();
        RankController rankController = new RankController();
        AlbumController albumController = new AlbumController();
        ArtistController artistController = new ArtistController();

        //artistController.create("Slim Shady", "U.S.A.");

        rankController.showArtistRankings();




    }
}

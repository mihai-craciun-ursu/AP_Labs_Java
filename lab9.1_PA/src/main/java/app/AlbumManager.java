package app;

import entity.Album;
import entity.Artist;
import entity.Chart;
import repo.AlbumRepository;
import repo.ArtistRepository;
import repo.ChartRepository;

import java.util.List;

public class AlbumManager {
    public static void main(String[] args) {
        ArtistRepository artistRepository = new ArtistRepository();
        AlbumRepository albumRepository = new AlbumRepository();
        ChartRepository chartRepository = new ChartRepository();

        //Artist artistCreated = new Artist("Thousand Foot Krutch", "USA");
        //artistRepository.create(artistCreated);

        Chart chart = chartRepository.findById(1);

        System.out.println(chart.getRankingSet());


        Artist artists = artistRepository.findById(1);
        List<Album> albums = albumRepository.findByArtist(artists);

        for(Album album : albums){
            System.out.println(album);
        }

        System.out.println(albums);
    }
}

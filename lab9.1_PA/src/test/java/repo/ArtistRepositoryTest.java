package repo;

import entity.Album;
import entity.Artist;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistRepositoryTest {

    @Test
    void create() {
        ArtistRepository artistRepository = new ArtistRepository();
        Artist artist = new Artist("Starset", "U.S.A");

        artistRepository.create(artist);
        Artist artistCreated = artistRepository.findById(artist.getId());

        Assert.assertEquals(artist, artistCreated);
    }

    @Test
    void findById() {
        ArtistRepository artistRepository = new ArtistRepository();
        Artist artist = artistRepository.findById(1);

        Assert.assertEquals(1, artist.getId());
    }

    @Test
    void findByName() {
        ArtistRepository artistRepository = new ArtistRepository();

        List<Artist> artists = artistRepository.findByName("NF");

        for(Artist artist : artists){
            Assert.assertEquals("NF", artist.getName());
        }

    }
}
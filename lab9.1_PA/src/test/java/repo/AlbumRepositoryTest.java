package repo;

import entity.Album;
import entity.Artist;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbumRepositoryTest {

    @Test
    void create() {
        AlbumRepository albumRepository = new AlbumRepository();

        Album album = new Album("Black And White", 1, 2018, "rap");

        albumRepository.create(album);

        Album findedAlbum = albumRepository.findById(album.getId());

        Assert.assertEquals(album, findedAlbum);
    }

    @Test
    void findById() {
        AlbumRepository albumRepository = new AlbumRepository();

        Album album = albumRepository.findById(758);

        Assert.assertEquals(758, album.getId());
    }

    @Test
    void findByName() {
        AlbumRepository albumRepository = new AlbumRepository();

        List<Album> albumList = albumRepository.findByName("No Highway");

        for(Album album : albumList){
            Assert.assertEquals("No Highway", album.getName());
        }
    }

    @Test
    void findByArtist() {
        AlbumRepository albumRepository = new AlbumRepository();
        ArtistRepository artistRepository = new ArtistRepository();

        Artist artist = artistRepository.findById(1);

        List<Album> albumList = albumRepository.findByArtist(artist);

        for(Album album : albumList){
            Assert.assertEquals(1, album.getArtistID());
        }
    }
}
import entity.Album;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MusicAlbumPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Album album = entityManager.find(Album.class, 142);
        System.out.println(album);
    }
}

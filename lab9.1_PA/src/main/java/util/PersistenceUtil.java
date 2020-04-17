package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {
    private static PersistenceUtil ourInstance = new PersistenceUtil();

    private EntityManagerFactory entityManagerFactory;

    public static PersistenceUtil getInstance() {
        return ourInstance;
    }

    private PersistenceUtil() {
        entityManagerFactory = Persistence.createEntityManagerFactory("MusicAlbumPU");
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}

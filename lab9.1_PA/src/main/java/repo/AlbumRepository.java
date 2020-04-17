package repo;

import entity.Album;
import entity.Artist;
import util.PersistenceUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class AlbumRepository implements AbstractRepository<Album> {

    private EntityManager entityManager;

    public AlbumRepository(){
        entityManager = PersistenceUtil.getInstance().getEntityManagerFactory().createEntityManager();
    }


    public void create(Album object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    public Album findById(int id) {
        return entityManager.find(Album.class, id);
    }

    public List<Album> findByName(String name) {
        List listOfAlbums = new ArrayList<Album>();

        try{
            entityManager.getTransaction().begin();
            listOfAlbums = entityManager.createNamedQuery("Album.findByName").setParameter("name", name).getResultList();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager.isOpen()){
                entityManager.close();
            }
        }

        return listOfAlbums;

    }

    public List<Album> findByArtist(Artist artist){
        List listOfAlbums = new ArrayList<Album>();

        try{
            entityManager.getTransaction().begin();
            listOfAlbums = entityManager.createNamedQuery("Album.findByArtist").setParameter("artistID", artist.getId()).getResultList();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager.isOpen()){
                entityManager.close();
            }
        }

        return listOfAlbums;
    }
}

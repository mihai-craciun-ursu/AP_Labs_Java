package repo;

import entity.Artist;
import util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class ArtistRepository implements AbstractRepository<Artist> {

    private EntityManager entityManager;

    public ArtistRepository(){
        entityManager = PersistenceUtil.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public void create(Artist object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    public Artist findById(int id) {
        return entityManager.find(Artist.class, id);
    }

    public List<Artist> findByName(String name) {
        List listOfArtists = new ArrayList<Artist>();

        try{
            entityManager.getTransaction().begin();
            listOfArtists = entityManager.createNamedQuery("Artist.findByName").setParameter("name", name).getResultList();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager.isOpen()){
                entityManager.close();
            }
        }

        return listOfArtists;
    }
}

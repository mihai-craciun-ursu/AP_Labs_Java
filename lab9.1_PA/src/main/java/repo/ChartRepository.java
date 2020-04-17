package repo;

import entity.Chart;
import util.PersistenceUtil;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ChartRepository implements AbstractRepository<Chart> {

    private EntityManager entityManager;

    public ChartRepository(){
        entityManager = PersistenceUtil.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public void create(Chart object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }

    public Chart findById(int id) {
        return entityManager.find(Chart.class, id);
    }

    public List<Chart> findByName(String name) {
        List listOfCharts = new ArrayList<Chart>();

        try{
            entityManager.getTransaction().begin();
            listOfCharts = entityManager.createNamedQuery("Chart.findByName").setParameter("name", name).getResultList();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            if (entityManager.isOpen()){
                entityManager.close();
            }
        }

        return listOfCharts;
    }
}

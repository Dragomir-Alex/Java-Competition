package database.dao;

import database.DatabaseConnection;
import database.model.EchipaEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EchipaDao implements DaoI<EchipaEntity> {
    DatabaseConnection connection = new DatabaseConnection();
    @Override
    public EchipaEntity get(long id) {
        return connection.getEntityManager().find(EchipaEntity.class, id);
    }

    @Override
    public List<EchipaEntity> getAll() {
        TypedQuery query = connection.getEntityManager().createQuery("SELECT a FROM EchipaEntity a", EchipaEntity.class);
        return query.getResultList();
    }

    @Override
    public void create(EchipaEntity echipaEntity) {
        connection.executeTransaction(entityManager -> entityManager.persist(echipaEntity));
    }

    @Override
    public void delete(long id) {
        EntityManager em = connection.getEntityManager();
        EchipaEntity tbd = em.find(EchipaEntity.class, id);
        if (tbd != null) {
            em.getTransaction().begin();
            em.remove(tbd);
            em.getTransaction().commit();
        }
    }

    public void updateId(long id, long new_id) {
        EntityManager em = connection.getEntityManager();
        EchipaEntity tbu = em.find(EchipaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setIdEchipa(new_id);
            em.getTransaction().commit();
        }
    }

    public void updateNumeEchipa(long id, String nume_echipa) {
        EntityManager em = connection.getEntityManager();
        EchipaEntity tbu = em.find(EchipaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setNumeEchipa(nume_echipa);
            em.getTransaction().commit();
        }
    }
}

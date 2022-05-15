package database.dao;

import database.DatabaseConnection;
import database.model.PersoanaEntity;
import database.model.enums.UserType;

import javax.persistence.*;
import java.util.List;

public class PersoanaDao implements DaoI<PersoanaEntity> {
    DatabaseConnection connection = new DatabaseConnection();
    @Override
    public PersoanaEntity get(long id) {
        return connection.getEntityManager().find(PersoanaEntity.class, id);
    }

    public PersoanaEntity getByUsername(String username) {
        TypedQuery query = connection.getEntityManager().createQuery("SELECT a FROM PersoanaEntity a WHERE a.username =:username", PersoanaEntity.class)
                .setParameter("username", username);
        if (query.getResultList().size() != 0)
            return (PersoanaEntity) query.getSingleResult();
        else return null;
    }

    @Override
    public List<PersoanaEntity> getAll() {
        TypedQuery query = connection.getEntityManager().createQuery("SELECT a FROM PersoanaEntity a", PersoanaEntity.class);
        return query.getResultList();
    }

    @Override
    public void create(PersoanaEntity PersoanaEntity) {
        TypedQuery query = connection.getEntityManager().createQuery("SELECT COUNT(a) FROM PersoanaEntity a WHERE a.idEchipa = :id", PersoanaEntity.class)
                .setParameter("id", PersoanaEntity.getIdEchipa());
        if ((long) query.getSingleResult() == 5)
            return;
        connection.executeTransaction(entityManager -> entityManager.persist(PersoanaEntity));
    }

    public boolean createByParameters(String username, String nume, String numeEchipa) {
        TypedQuery query;
        Long idEchipa;
        query = connection.getEntityManager().createQuery("SELECT a FROM PersoanaEntity a WHERE a.username =:username", PersoanaEntity.class)
                .setParameter("username", username);
        if (query.getResultList().size() != 0) {
            System.out.println("Registration error 1");
            return false;
        }
        query = connection.getEntityManager().createQuery("SELECT b.idEchipa FROM EchipaEntity b WHERE b.numeEchipa =:numeEchipa", PersoanaEntity.class)
                .setParameter("numeEchipa", numeEchipa);
        if (query.getResultList().size() == 0) {
            System.out.println("Registration error 2");
            return false;
        }
        else idEchipa = (Long) query.getResultList().get(0);
        query = connection.getEntityManager().createQuery("SELECT COUNT(a) FROM PersoanaEntity a WHERE a.idEchipa = :id", PersoanaEntity.class)
                .setParameter("id", idEchipa);
        if ((long) query.getSingleResult() == 5) {
            System.out.println("Registration error 3");
            return false;
        }

        PersoanaEntity persoanaEntity = new PersoanaEntity();
        persoanaEntity.setIdEchipa(idEchipa);
        persoanaEntity.setRol(UserType.USER);
        persoanaEntity.setUsername(username);
        persoanaEntity.setNume(nume);
        create(persoanaEntity);

        return true;
    }

    @Override
    public void delete(long id) {
        EntityManager em = connection.getEntityManager();
        PersoanaEntity tbd = em.find(PersoanaEntity.class, id);
        if (tbd != null) {
            em.getTransaction().begin();
            em.remove(tbd);
            em.getTransaction().commit();
        }
    }

    public void updateId(long id, long new_id) {
        EntityManager em = connection.getEntityManager();
        PersoanaEntity tbu = em.find(PersoanaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setIdPersoana(new_id);
            em.getTransaction().commit();
        }
    }

    public void updateIdEchipa(long id, long id_echipa) {
        EntityManager em = connection.getEntityManager();
        PersoanaEntity tbu = em.find(PersoanaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setIdEchipa(id_echipa);
            em.getTransaction().commit();
        }
    }

    public void updateNume(long id, String nume) {
        EntityManager em = connection.getEntityManager();
        PersoanaEntity tbu = em.find(PersoanaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setNume(nume);
            em.getTransaction().commit();
        }
    }

    public void updateUsername(long id, String username) {
        EntityManager em = connection.getEntityManager();
        PersoanaEntity tbu = em.find(PersoanaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setUsername(username);
            em.getTransaction().commit();
        }
    }

    public void updateRol(long id, UserType rol) {
        EntityManager em = connection.getEntityManager();
        PersoanaEntity tbu = em.find(PersoanaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setRol(rol);
            em.getTransaction().commit();
        }
    }
}

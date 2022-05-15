package database.dao;

import database.DatabaseConnection;
import database.model.ParticipaEntity;
import database.model.PersoanaEntity;

import javax.persistence.TypedQuery;
import java.util.List;

public class ParticipaDao implements DaoI<ParticipaEntity> {
    DatabaseConnection connection = new DatabaseConnection();
    @Override
    public ParticipaEntity get(long id) {
        return connection.getEntityManager().find(ParticipaEntity.class, id);
    }

    public ParticipaEntity getParticipaFromEtapa(long id_etapa) {
        return connection.getEntityManager().find(ParticipaEntity.class, id_etapa);
    }

    public ParticipaEntity getParticipa(long id_persoana, long id_etapa) {
        TypedQuery<ParticipaEntity> query;
        query = (TypedQuery<ParticipaEntity>) connection.getEntityManager().createQuery("SELECT a FROM ParticipaEntity a WHERE ParticipaEntity.idPersoana = :idPersoana AND ParticipaEntity.idEtapa = :idEtapa")
                .setParameter("idPersoana", id_persoana)
                .setParameter("idEtapa", id_etapa);
        return query.getSingleResult();
    }

    @Override
    public List<ParticipaEntity> getAll() {
        TypedQuery<ParticipaEntity> query = connection.getEntityManager().createQuery("SELECT a FROM ParticipaEntity a", ParticipaEntity.class);
        return query.getResultList();
    }

    @Override
    public void create(ParticipaEntity ParticipaEntity) {
        connection.executeTransaction(entityManager -> entityManager.persist(ParticipaEntity));
    }

    public boolean createByParameters(String etapa, Long idPersoana, int punctaj) {
        TypedQuery query;
        Long idEtapa;

        query = connection.getEntityManager().createQuery("SELECT b.idEtapa FROM EtapaEntity b WHERE b.denumire =:etapa", PersoanaEntity.class)
                .setParameter("etapa", etapa);
        if (query.getResultList().size() == 0) {
            System.out.println("Add error 1");
            return false;
        }
        else idEtapa = (Long) query.getResultList().get(0);

        query = connection.getEntityManager().createQuery("SELECT a FROM ParticipaEntity a WHERE a.idEtapa =:idEtapa AND a.idPersoana = :idPersoana", PersoanaEntity.class)
                .setParameter("idEtapa", idEtapa)
                .setParameter("idPersoana", idPersoana);
        if (query.getResultList().size() != 0) {
            System.out.println("Add error 2");
            return false;
        }

        ParticipaEntity participaEntity = new ParticipaEntity();
        participaEntity.setIdEtapa(idEtapa);
        participaEntity.setIdPersoana(idPersoana);
        participaEntity.setPunctaj(punctaj);
        create(participaEntity);

        return true;
    }

    @Override
    public void delete(long id) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("DELETE FROM ParticipaEntity a WHERE a.idEtapa = :id")
                .setParameter("id", id)
                .executeUpdate());
    }

    public void deleteByPK(long id_etapa, long id_persoana) {
        connection.executeTransaction(entityManager -> entityManager.createQuery("DELETE FROM ParticipaEntity a WHERE a.idEtapa = :idEtapa AND a.idPersoana = :idPersoana")
                .setParameter("idEtapa", id_etapa)
                .setParameter("idPersoana", id_persoana)
                .executeUpdate());
    }
}

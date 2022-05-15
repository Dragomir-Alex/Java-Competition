package database.dao;

import database.DatabaseConnection;
import database.model.EchipaEntity;
import database.model.EtapaEntity;
import database.model.PersoanaEntity;
import javafx.util.Pair;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EtapaDao implements DaoI<EtapaEntity> {
    DatabaseConnection connection = new DatabaseConnection();
    @Override
    public EtapaEntity get(long id) {
        return connection.getEntityManager().find(EtapaEntity.class, id);
    }

    @Override
    public List<EtapaEntity> getAll() {
        TypedQuery query = connection.getEntityManager().createQuery("SELECT a FROM EtapaEntity a", EtapaEntity.class);
        return query.getResultList();
    }

    @Override
    public void create(EtapaEntity EtapaEntity) {
        connection.executeTransaction(entityManager -> entityManager.persist(EtapaEntity));
    }

    @Override
    public void delete(long id) {
        EntityManager em = connection.getEntityManager();
        EtapaEntity tbd = em.find(EtapaEntity.class, id);
        if (tbd != null) {
            em.getTransaction().begin();
            em.remove(tbd);
            em.getTransaction().commit();
        }
    }

    public void updateId(long id, long new_id) {
        EntityManager em = connection.getEntityManager();
        EtapaEntity tbu = em.find(EtapaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setIdEtapa(new_id);
            em.getTransaction().commit();
        }
    }

    public void updateDenumire(long id, String denumire) {
        EntityManager em = connection.getEntityManager();
        EtapaEntity tbu = em.find(EtapaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setDenumire(denumire);
            em.getTransaction().commit();
        }
    }

    public void updateIncheiat(long id, LocalDate incheiat) {
        EntityManager em = connection.getEntityManager();
        EtapaEntity tbu = em.find(EtapaEntity.class, id);
        if (tbu != null) {
            em.getTransaction().begin();
            tbu.setIncheiat(incheiat);
            em.getTransaction().commit();
        }
    }

    public String printLeaderboard(String denumire) {
        TypedQuery query;
        Long id;

        query = connection.getEntityManager().createQuery("SELECT b.idEtapa FROM EtapaEntity b WHERE b.denumire =:etapa", PersoanaEntity.class)
                .setParameter("etapa", denumire);
        if (query.getResultList().size() == 0) {
            return("Stage doesn't exist.");
        }
        else id = (Long) query.getResultList().get(0);

        query = connection.getEntityManager().createQuery("SELECT a.nume FROM PersoanaEntity a, ParticipaEntity b WHERE a.idPersoana = b.idPersoana AND b.idEtapa = :id", EchipaEntity.class)
                .setParameter("id", id);
        List<String> listaNume = query.getResultList();

        if (listaNume.size() < 5) {
            return("Leaderboard unavailable.");
        }

        query = connection.getEntityManager().createQuery("SELECT a.denumire FROM EtapaEntity a WHERE a.idEtapa = :id", EchipaEntity.class)
                .setParameter("id", id);
        String denumireEtapa = (String) query.getSingleResult();

        query = connection.getEntityManager().createQuery("SELECT b.punctaj FROM PersoanaEntity a, ParticipaEntity b WHERE a.idPersoana = b.idPersoana AND b.idEtapa = :id AND b.punctaj != -1", EchipaEntity.class)
                .setParameter("id", id);
        List<Integer> listaScor = query.getResultList();

        if (listaScor.size() != listaNume.size()) {
            return ("Leaderboard currently unavailable.");
        }

        List<Pair<String, Integer>> listaClasament = new ArrayList<>();
        for (int i = 0; i < listaNume.size(); ++i) {
            Pair<String, Integer> newPair = new Pair<String, Integer>(listaNume.get(i), listaScor.get(i));
            listaClasament.add(newPair);
        }
        listaClasament.sort(new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                if (o1.getValue() > o2.getValue())
                    return -1;
                else if (o1.getValue().equals(o2.getValue()))
                    return 0;
                else return 1;
            }
        });

        List<Double> listaPunctaj = new ArrayList<>();
        Collections.addAll(listaPunctaj, 10.0, 6.0, 3.0, 1.0, 0.0);
        while (listaPunctaj.size() < listaNume.size())
            listaPunctaj.add(0.0);

        Integer indexStart = -1, indexFinish = -1;
        Double medValue = 0.0;

        for (int i = 1; i < listaClasament.size(); ++i) {
            if (listaClasament.get(i).getValue() == listaClasament.get(i-1).getValue()) {
                if (indexStart == -1) {
                    indexStart = i-1;
                    indexFinish = i;
                    medValue = (double) listaPunctaj.get(i) + (double) listaPunctaj.get(i-1);
                }
                else {
                    indexFinish = i;
                    medValue += listaPunctaj.get(i);
                }
            }
            else if (indexStart != -1) {
                medValue = medValue / (indexFinish - indexStart + 1);
                for (int j = indexStart; j <= indexFinish; ++j)
                    listaPunctaj.set(j, medValue);
                indexStart = -1;
            }
        }
        if (indexStart != -1) {
            medValue = medValue / (indexFinish - indexStart + 1);
            for (int j = indexStart; j <= indexFinish; ++j)
                listaPunctaj.set(j, medValue);
        }

        String finalMessage = "Ranking for " + denumireEtapa + ":\n";
        for (int i = 0; i < listaClasament.size(); ++i)
            finalMessage += listaClasament.get(i).getKey() + " - Score = " + listaClasament.get(i).getValue() + " - Points = " + String.format("%.2f", listaPunctaj.get(i)) + "\n";

        return finalMessage;
    }

    public String printLeaderboardTeams(String denumire) {
        TypedQuery query;
        Long id;

        query = connection.getEntityManager().createQuery("SELECT b.idEtapa FROM EtapaEntity b WHERE b.denumire =:etapa", PersoanaEntity.class)
                .setParameter("etapa", denumire);
        if (query.getResultList().size() == 0) {
            return("Stage doesn't exist.");
        }
        else id = (Long) query.getResultList().get(0);

        query = connection.getEntityManager().createQuery("SELECT DISTINCT c.numeEchipa FROM PersoanaEntity a, ParticipaEntity b, EchipaEntity c WHERE a.idPersoana = b.idPersoana AND b.idEtapa = :id AND a.idEchipa = c.idEchipa", EchipaEntity.class)
                .setParameter("id", id);
        List<String> listaEchipe = query.getResultList();

        query = connection.getEntityManager().createQuery("SELECT DISTINCT c.idEchipa FROM PersoanaEntity a, ParticipaEntity b, EchipaEntity c WHERE a.idPersoana = b.idPersoana AND b.idEtapa = :id AND a.idEchipa = c.idEchipa", EchipaEntity.class)
                .setParameter("id", id);
        List<String> listaIdEchipe = query.getResultList();

        query = connection.getEntityManager().createQuery("SELECT a.denumire FROM EtapaEntity a WHERE a.idEtapa = :id", EchipaEntity.class)
                .setParameter("id", id);
        String denumireEtapa = (String) query.getSingleResult();

        List<Long> listaScor = new ArrayList<>();

        for (int i = 0; i < listaEchipe.size(); ++i) {
            query = connection.getEntityManager().createQuery("SELECT SUM(b.punctaj) FROM PersoanaEntity a, ParticipaEntity b, EchipaEntity c WHERE a.idPersoana = b.idPersoana AND b.idEtapa = :id AND a.idEchipa = :idEchipa", EchipaEntity.class)
                    .setParameter("id", id)
                    .setParameter("idEchipa", listaIdEchipe.get(i));
             listaScor.add((Long) query.getResultList().get(0));
        }

        List<Pair<String, Long>> listaClasament = new ArrayList<>();
        for (int i = 0; i < listaEchipe.size(); ++i) {
            Pair<String, Long> newPair = new Pair<String, Long>(listaEchipe.get(i), listaScor.get(i));
            listaClasament.add(newPair);
        }
        listaClasament.sort(new Comparator<Pair<String, Long>>() {
            @Override
            public int compare(Pair<String, Long> o1, Pair<String, Long> o2) {
                if (o1.getValue() > o2.getValue())
                    return -1;
                else if (o1.getValue().equals(o2.getValue()))
                    return 0;
                else return 1;
            }
        });

        String finalMessage = "Ranking for " + denumireEtapa + ":\n";
        for (int i = 0; i < listaClasament.size(); ++i)
            finalMessage += listaClasament.get(i).getKey() + " - Score = " + listaClasament.get(i).getValue() / 10 + "\n";

        return finalMessage;
    }
}

package database.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "participa", schema = "public", catalog = "competitie")
@IdClass(ParticipaEntityPK.class)
public class ParticipaEntity {
    @Id
    @Column(name = "id_etapa", nullable = false)
    private long idEtapa;
    @Id
    @Column(name = "id_persoana", nullable = false)
    private long idPersoana;
    @Basic
    @Column(name = "punctaj", nullable = false)
    private int punctaj;

    public long getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(long idEtapa) {
        this.idEtapa = idEtapa;
    }

    public long getIdPersoana() {
        return idPersoana;
    }

    public void setIdPersoana(long idPersoana) {
        this.idPersoana = idPersoana;
    }

    public int getPunctaj() {
        return punctaj;
    }

    public void setPunctaj(int punctaj) {
        this.punctaj = punctaj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipaEntity that = (ParticipaEntity) o;
        return idEtapa == that.idEtapa && idPersoana == that.idPersoana && punctaj == that.punctaj;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEtapa, idPersoana, punctaj);
    }
}

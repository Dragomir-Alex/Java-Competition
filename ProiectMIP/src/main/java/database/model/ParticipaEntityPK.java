package database.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ParticipaEntityPK implements Serializable {
    @Column(name = "id_etapa", nullable = false)
    @Id
    private long idEtapa;
    @Column(name = "id_persoana", nullable = false)
    @Id
    private long idPersoana;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipaEntityPK that = (ParticipaEntityPK) o;
        return idEtapa == that.idEtapa && idPersoana == that.idPersoana;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEtapa, idPersoana);
    }
}

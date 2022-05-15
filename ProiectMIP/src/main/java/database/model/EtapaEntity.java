package database.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "etapa", schema = "public", catalog = "competitie")
public class EtapaEntity {
    @Id
    @Column(name = "id_etapa", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEtapa;
    @Basic
    @Column(name = "denumire", nullable = false, length = -1)
    private String denumire;
    @Basic
    @Column(name = "incheiat", nullable = false)
    private LocalDate incheiat;

    public long getIdEtapa() {
        return idEtapa;
    }

    public void setIdEtapa(long idEtapa) {
        this.idEtapa = idEtapa;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public LocalDate getIncheiat() {
        return incheiat;
    }

    public void setIncheiat(LocalDate incheiat) {
        this.incheiat = incheiat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtapaEntity that = (EtapaEntity) o;
        return idEtapa == that.idEtapa && Objects.equals(denumire, that.denumire) && Objects.equals(incheiat, that.incheiat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEtapa, denumire, incheiat);
    }
}

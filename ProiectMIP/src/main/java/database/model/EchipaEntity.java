package database.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "echipa", schema = "public", catalog = "competitie")
public class EchipaEntity {
    @Id
    @Column(name = "id_echipa", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEchipa;
    @Basic
    @Column(name = "nume_echipa", nullable = false, length = -1)
    private String numeEchipa;

    public long getIdEchipa() {
        return idEchipa;
    }

    public void setIdEchipa(long idEchipa) {
        this.idEchipa = idEchipa;
    }

    public String getNumeEchipa() {
        return numeEchipa;
    }

    public void setNumeEchipa(String numeEchipa) {
        this.numeEchipa = numeEchipa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EchipaEntity that = (EchipaEntity) o;
        return idEchipa == that.idEchipa && Objects.equals(numeEchipa, that.numeEchipa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEchipa, numeEchipa);
    }
}

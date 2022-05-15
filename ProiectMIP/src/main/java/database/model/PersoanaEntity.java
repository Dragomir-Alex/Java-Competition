package database.model;

import database.model.enums.UserType;
import database.model.util.UserTypeConverter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "persoana", schema = "public", catalog = "competitie")
public class PersoanaEntity {
    @Id
    @Column(name = "id_persoana", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPersoana;
    @Basic
    @Column(name = "id_echipa", nullable = false)
    private long idEchipa;
    @Basic
    @Column(name = "nume", nullable = false, length = -1)
    private String nume;
    @Basic
    @Column(name = "username", nullable = false, length = -1)
    private String username;
    @Basic
    @Column(name = "rol", nullable = false)
    @Convert(converter = UserTypeConverter.class)
    private UserType rol;

    public long getIdPersoana() {
        return idPersoana;
    }

    public void setIdPersoana(long idPersoana) {
        this.idPersoana = idPersoana;
    }

    public long getIdEchipa() {
        return idEchipa;
    }

    public void setIdEchipa(long idEchipa) {
        this.idEchipa = idEchipa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getRol() {
        return rol;
    }

    public void setRol(UserType rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersoanaEntity that = (PersoanaEntity) o;
        return idPersoana == that.idPersoana && idEchipa == that.idEchipa && Objects.equals(nume, that.nume) && Objects.equals(username, that.username) && Objects.equals(rol, that.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersoana, idEchipa, nume, username, rol);
    }
}

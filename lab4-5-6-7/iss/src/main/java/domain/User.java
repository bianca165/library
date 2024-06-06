package domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends Entitate {

    private String sessionId;

    @Column(name = "nume")
    private String nume;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "rol")
    private String rol;

    @Column(name = "parola")
    private String parola;

    public User() {
        super(0); // constructor implicit necesar pt Hibernate
    }
    public User(int id, String nume, String adresa, String telefon, String rol, String parola) {
        super(id);
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.rol = rol;
        this.parola = parola;
    }

    public User(int id, String sessionId, String nume) {
        super(id);
        this.sessionId = sessionId;
        this.nume = nume;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(nume, user.nume) && Objects.equals(adresa, user.adresa) && Objects.equals(telefon, user.telefon) && Objects.equals(rol, user.rol) && Objects.equals(parola, user.parola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nume, adresa, telefon, rol, parola);
    }
}

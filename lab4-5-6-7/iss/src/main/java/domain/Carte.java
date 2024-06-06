package domain;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "carti")
public class Carte extends Entitate {
    @Column(name = "titlu", nullable = false)
    private String titlu;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "categorie", nullable = false)
    private String categorie;

    @OneToMany(mappedBy = "carte", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Exemplar> exemplare = new HashSet<>();

    public Carte() {
        super(1);
    }

    public Carte(int id, String titlu, String autor, String categorie, Set<Exemplar> exemplare) {
        super(id);
        this.titlu = titlu;
        this.autor = autor;
        this.categorie = categorie;
        this.exemplare = exemplare != null ? new HashSet<>(exemplare) : new HashSet<>();
    }

    public Carte(int id, String titlu, String autor, String categorie) {
        super(id);
        this.titlu = titlu;
        this.autor = autor;
        this.categorie = categorie;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Set<Exemplar> getCoduriExemplare() {
        return exemplare;
    }

    public void setCoduriExemplare(Set<Exemplar> coduriExemplare) {
        this.exemplare = coduriExemplare;
    }

    public int getNumarExemplareDisponibile() {
        return (int) exemplare.stream().filter(exemplar -> exemplar.getDisponibil() > 0).count();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return id == carte.id && Objects.equals(titlu, carte.titlu) && Objects.equals(autor, carte.autor) && Objects.equals(categorie, carte.categorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titlu, autor, categorie);
    }
}

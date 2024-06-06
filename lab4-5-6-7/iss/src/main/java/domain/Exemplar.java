package domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "exemplare")
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_exemplar")
    private int codExemplar;

    @Column(name = "disponibil")
    private int disponibil;

    @ManyToOne
    @JoinColumn(name = "carte_id")
    private Carte carte;


    public Exemplar() {
    }

    public Exemplar(int codExemplar, int disponibil, Carte carte) {
        this.codExemplar = codExemplar;
        this.disponibil = disponibil;
        this.carte = carte;
    }


    public int getCodExemplar() {
        return codExemplar;
    }

    public void setCodExemplar(int codExemplar) {
        this.codExemplar = codExemplar;
    }

    public int getDisponibil() {
        return disponibil;
    }

    public void setDisponibil(int disponibil) {
        this.disponibil = disponibil;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exemplar exemplar = (Exemplar) o;
        return codExemplar == exemplar.codExemplar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codExemplar);
    }
}

package domain;
import javax.persistence.MappedSuperclass;
import javax.persistence.*;

import java.io.Serializable;

@MappedSuperclass
public abstract class Entitate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public Entitate(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

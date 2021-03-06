package si.fri.core.primer_foreign_tables;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.fri.core.Primer;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "typeOfPrimer")

public class TypeOfPrimer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column( unique = true)
    private String typeOfPrimer;

    @OneToMany
    @JoinColumn(name = "typeOfPrimer_id")
    private Set<Primer> primers;

    public TypeOfPrimer() {
        // Jackson deserialization
    }

    public TypeOfPrimer(String typeOfPrimer) {
        this.typeOfPrimer = typeOfPrimer;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty
    public String getTypeOfPrimer() {
        return typeOfPrimer;
    }

    public void setTypeOfPrimer(String typeOfPrimer) {
        this.typeOfPrimer = typeOfPrimer;
    }
}

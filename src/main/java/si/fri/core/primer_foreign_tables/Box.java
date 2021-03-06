package si.fri.core.primer_foreign_tables;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.fri.core.History;
import si.fri.core.Primer;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "box")

public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column( unique = true)
    private String box;

    @OneToMany
    @JoinColumn(name = "box_id")
    private Set<Primer> primers;

    //@PrePersist
    //public void onPreRemove() {
    //    new History();
    //}

    public Box() {
        // Jackson deserialization
    }

    public Box(String box) {
        this.box = box;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty
    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }
}

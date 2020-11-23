package ca.applin.octaveg.assignement.jukes.data;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "jukes")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Builder
public class JukesEntity {

    public static final String ID_PROPERTY = "id";

    @Id
    private String id;

    @Column
    private String model;

    @ElementCollection
    private List<String> settings;

    @ElementCollection
    private List<String> components;

}


package pet.project.pasteBinApplication.model.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "role", schema = "pastebin_schema")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id", nullable = false)
    Long roleId;

    @Column(name = "role_name", nullable = false)
    String roleName;

    @Override
    public String toString() {
        return roleName;
    }
}

package pet.project.pasteBinApplication.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role", schema = "pastebin_schema")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "roleId", nullable = false)
    private Long roleId;
    @Column(name = "roleName", nullable = false)
    private String roleName;

    @Override
    public String toString() {
        return this.getRoleName();
    }
}

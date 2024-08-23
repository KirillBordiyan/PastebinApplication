package pet.project.pasteBinApplication.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "role", schema = "pastebin_schema")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id", nullable = false)
    private Long roleId;
    @Column(name = "role_name", nullable = false)
    private String roleName;

    @Override
    public String toString() {
        return roleName;
    }

//    public static Role of(String roleName){
//        return new Role(, roleName);
//    }
}

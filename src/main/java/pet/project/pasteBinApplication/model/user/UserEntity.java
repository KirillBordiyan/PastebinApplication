package pet.project.pasteBinApplication.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pet.project.pasteBinApplication.model.file.UserFileData;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", schema = "pastebin_schema")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity implements Serializable {

    @Id
    @Column(name = "nick_name", unique = true, nullable = false) //БЕЗ ПРОБЕЛОВ И СИМВОЛОВ?? =  никнейм
    String nickName;

    @Column(name = "email", unique = true, nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Transient
    String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "nick_name", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false),
            schema = "pastebin_schema"
    )
    Set<Role> roles;

    @OneToMany(mappedBy = "ownerNickName", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"fileOwner"})
    List<UserFileData> files;
}

package pet.project.pasteBinApplication.model.user;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import pet.project.pasteBinApplication.model.file.UserFile;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "pastebin_schema")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "userId")
//    UUID userId;

    @Id
    @Column(name = "nick_name", unique = true, nullable = false) //БЕЗ ПРОБЕЛОВ И СИМВОЛОВ?? =  никнейм
    String nickName; //брать как уникальное имя пользователя (displayName)


    @Column(name = "email", unique = true, nullable = false)
    String email;


    @Column(name = "password", nullable = false)
    String password;

    //для подтверждения пароля, не сохраняем в БД
    @Transient
    String passwordConfirm;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "userId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "roleId", nullable = false),
            schema = "pastebin_schema"
    )
    Set<Role> roles;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    Set<UserFile> usersFiles;
}
// presigned urls - unauthorized file access
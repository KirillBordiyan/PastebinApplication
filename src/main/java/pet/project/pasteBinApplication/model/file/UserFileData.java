package pet.project.pasteBinApplication.model.file;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pet.project.pasteBinApplication.model.user.UserEntity;

@Entity
@Table(name = "users_files", schema = "pastebin_schema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFileData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "row_id")
    Long rowId;

    @Column(name = "linked_file_name", unique = true)
    String linkedFileName;

    @Column(name = "original_file_name", nullable = false)
    String originalFileName;

    @ManyToOne
    @JoinColumn(name = "nick_name", nullable = false)
    UserEntity ownerNickName;
}

//    @Column(name = "access level", nullable = false)
//    String level; // close, readOnly, accessAll
//составной ключ для этой таблицы(файлнейм и бакетнейм)


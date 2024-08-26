package pet.project.pasteBinApplication.model.file;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pet.project.pasteBinApplication.model.user.UserEntity;

import java.io.Serializable;

@Entity
@Table(name = "users_files", schema = "pastebin_schema")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFileData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "row_id")
    Long rowId;

    @Column(name = "linked_file_name", unique = true)
    String bucketFileName;

    @Column(name = "original_file_name", nullable = false)
    String originalFileName;

    @ManyToOne
    @JoinColumn(name = "nick_name", nullable = false)
    @JsonIgnoreProperties(value = {"email", "password", "passwordConfirm", "roles", "files"})
    UserEntity fileOwner;
}

//    @Column(name = "access level", nullable = false)
//    String level; // close, readOnly, accessAll
//составной ключ для этой таблицы(файлнейм и бакетнейм)


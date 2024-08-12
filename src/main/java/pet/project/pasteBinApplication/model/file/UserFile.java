package pet.project.pasteBinApplication.model.file;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import pet.project.pasteBinApplication.model.user.UserEntity;

@Entity
@Table(name = "users_files", schema = "pastebin_schema")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "userFileId")
    Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    UserEntity owner;

    @Column(name = "fileName", nullable = false)
    String fileName;

    String bucketName; //если нет в env
    String fileType; // мб оставить (enum)
}
//составной ключ для этой таблицы(файлнейм и бакетнейм)


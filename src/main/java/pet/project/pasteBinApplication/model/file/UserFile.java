package pet.project.pasteBinApplication.model.file;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import pet.project.pasteBinApplication.model.user.UserEntity;

import java.io.Serializable;

//@Entity
//@Table(name = "users_files", schema = "pastebin_schema")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFile /*implements Serializable*/ {

    MultipartFile file;

    // nickName | fileName (uuid) (ne orig)
    // nickname_file.txt

//    @ManyToOne
//    @JoinColumn(name = "nick_name", nullable = false)
//    UserEntity owner;

//    @Column(name = "access level", nullable = false)
//    String level; // close, readOnly, accessAll

}
//составной ключ для этой таблицы(файлнейм и бакетнейм)


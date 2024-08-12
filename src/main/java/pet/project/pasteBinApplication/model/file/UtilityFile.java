package pet.project.pasteBinApplication.model.file;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Deprecated
public class UtilityFile implements Serializable {

    String fileName;
    String contentType; // вопрос, тк у нас только текстовые файлы будут
    Long size;//??
    Date createdTime;//?
    transient InputStream stream; //transient нужен(мб), чтобы не сериализовать поле, тк оно появляется в процессе
}

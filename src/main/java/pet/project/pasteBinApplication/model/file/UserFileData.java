package pet.project.pasteBinApplication.model.file;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @Column(name = "original_file_name", nullable = false)
    String originalFileName;

    @Column(name = "bucket_file_name", unique = true)
    String bucketFileName;

    @Column(name = "owner_nick_name", nullable = false)
    String ownerNickName;
}

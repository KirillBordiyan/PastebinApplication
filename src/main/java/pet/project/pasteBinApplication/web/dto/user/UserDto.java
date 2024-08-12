package pet.project.pasteBinApplication.web.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pet.project.pasteBinApplication.web.dto.validation.OnCreateProcess;
import pet.project.pasteBinApplication.web.dto.validation.OnUpdateProcess;

import java.util.UUID;

@Data
public class UserDto {
    @NotNull(message = "DTO user: ID cannot be null!", groups = OnUpdateProcess.class)
    UUID userId;

    @NotNull(message = "DTO user: USERNAME cannot be null!", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    @Length(max = 255, message = "DTO user: USERNAME must be smaller than 255 symb", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    String userName;

    @NotNull(message = "DTO user: LOGIN cannot be null!", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    @Length(max = 255, message = "DTO user: LOGIN must be smaller than 255 symb", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //не игнорим, а выставляем разрешение только записи
    @NotNull(message = "DTO user: PASSWORD cannot be null!", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "DTO user: PASSWORD CONFIRMATION cannot be null!", groups = OnCreateProcess.class)
    String passwordConfirm;
}

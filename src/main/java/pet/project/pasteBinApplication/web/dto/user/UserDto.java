package pet.project.pasteBinApplication.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import pet.project.pasteBinApplication.web.dto.validation.OnCreateProcess;
import pet.project.pasteBinApplication.web.dto.validation.OnGetProcess;
import pet.project.pasteBinApplication.web.dto.validation.OnUpdateProcess;

@Getter
@Setter
@Schema(description = "User DTO")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @Schema(description = "User nick name", example = "Ivan_Ivanov86")
    @NotNull(message = "DTO user: NICKNAME cannot be null!", groups = {OnUpdateProcess.class, OnCreateProcess.class, OnGetProcess.class})
    @Length(max = 255, message = "DTO user: USERNAME must be smaller than 255 symb", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    String nickName;

    @Schema(description = "User email", example = "email@mail.com")
    @NotNull(message = "DTO user: LOGIN(Email) cannot be null!", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    @Length(max = 255, message = "DTO user: LOGIN must be smaller than 255 symb", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    String email;

    @Schema(description = "User password", example = "123") // в теории это можно перенести в JsonProperty и наоборот
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //не игнорим, а выставляем разрешение только записи
    @NotNull(message = "DTO user: PASSWORD cannot be null!", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    String password;

    @Schema(description = "User confirm password", example = "123")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "DTO user: PASSWORD CONFIRMATION cannot be null!", groups = OnCreateProcess.class)
    String passwordConfirm;
}

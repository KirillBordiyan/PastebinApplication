package pet.project.pasteBinApplication.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import pet.project.pasteBinApplication.web.dto.validation.OnCreateProcess;
import pet.project.pasteBinApplication.web.dto.validation.OnUpdateProcess;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@Schema(description = "User DTO")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @Schema(description = "User ID", exampleClasses = UUID.class, requiredMode = Schema.RequiredMode.NOT_REQUIRED/*"cdc228b8-4d57-4ed2-895f-f0bc1562c03b"*/)
    @NotNull(message = "DTO user: ID cannot be null!", groups = OnUpdateProcess.class)
    UUID userId;

    @Schema(description = "User name", example = "Ivan Ivanov")
    @NotNull(message = "DTO user: USERNAME cannot be null!", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    @Length(max = 255, message = "DTO user: USERNAME must be smaller than 255 symb", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    String userName;

    @Schema(description = "Login = user email", example = "email@mail.com")
    @NotNull(message = "DTO user: LOGIN cannot be null!", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    @Length(max = 255, message = "DTO user: LOGIN must be smaller than 255 symb", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    String login;

    @Schema(description = "User password", example = "123")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //не игнорим, а выставляем разрешение только записи
    @NotNull(message = "DTO user: PASSWORD cannot be null!", groups = {OnUpdateProcess.class, OnCreateProcess.class})
    String password;

    @Schema(description = "User confirm password", example = "123")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "DTO user: PASSWORD CONFIRMATION cannot be null!", groups = OnCreateProcess.class)
    String passwordConfirm;
}

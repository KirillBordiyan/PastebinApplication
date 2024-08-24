package pet.project.pasteBinApplication.service;

import pet.project.pasteBinApplication.model.file.UserFile;

import java.util.List;
import java.util.UUID;

public interface UsersFilesService {
    String upload(UserFile toUploadFile);
    /*
        download(filename), - через минио как-то можно в браузере делать, надо подсмотреть
        update(file), - обновление всего файла
        delete(filename) - удаление по имени
     */


}

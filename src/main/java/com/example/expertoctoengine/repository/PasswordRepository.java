package com.example.expertoctoengine.repository;

import com.example.expertoctoengine.model.Password;
import com.example.expertoctoengine.model.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PasswordRepository extends CrudRepository<Password, Long> {
    Password findPasswordById(Long id);

    List<Password> findPasswordsByPersonId(UUID id);

    List<Password> findPasswordsByPersonIdAndService(UUID id, String service);

    Password findPasswordByPersonIdAndServiceAndLogin(UUID id, String service, String login);

    void removeAllByPersonId(UUID id);

    void removeALLByPersonIdAndService(UUID id, String service);

    @Modifying
    @Query("update Password u set u.password = :password where u.person = :id and u.service = :service and u.login = :login")
    void updatePasswordByPersonIdAndServiceAndLogin(@Param(value = "id") Person person,
                                                    @Param(value = "service") String service,
                                                    @Param(value = "login") String login,
                                                    @Param(value = "password") String newPassword);

    @Modifying
    @Query("update Password u set u.login = :login where u.person = :id and u.service = :service and u.password = :password")
    void updateLoginByPersonIdAndServiceAndPassword(@Param(value = "id") Person id,
                                                    @Param(value = "service") String service,
                                                    @Param(value = "password") String password,
                                                    @Param(value = "login") String newLogin);

    @Modifying
    @Query("update Password u set u.service = :new_service where u.person = :id and u.service = :old_service")
    void updateServiceByPersonId(@Param(value = "id") Person person,
                                 @Param(value = "new_service") String newService,
                                 @Param(value = "old_service") String oldService);

    @Modifying
    @Query("update Password u set u.service = :service where u.id = :id")
    void changeServiceByPasswordId(@Param(value = "id") Long id,
                                   @Param(value = "service") String service);

    @Modifying
    @Query("update Password u set u.password = :password where u.id = :id")
    void changePasswordByPasswordId(@Param("id") Long id,
                                    @Param("password") String password);

    @Modifying
    @Query("update Password u set u.login = :login where u.id = :id")
    void changeLoginById(@Param("id") Long id,
                         @Param("login") String login);
}

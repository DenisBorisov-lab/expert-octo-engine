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
    List<Password> findPasswordsByPersonIdAndServiceAndLogin(UUID id, String service, String login);

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
    @Query("update Password u set u.service = :service where u.person = :id and u.login = :login and u.password = :password")
    void updateServiceByPersonIdAndLoginAndPassword(@Param(value = "id") Person person,
                                                    @Param(value = "login") String login,
                                                    @Param(value = "password") String password,
                                                    @Param(value = "service") String newServiceName);
}

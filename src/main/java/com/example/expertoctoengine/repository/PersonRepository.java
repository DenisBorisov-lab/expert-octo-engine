package com.example.expertoctoengine.repository;

import com.example.expertoctoengine.model.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends CrudRepository<Person, UUID> {
    List<Person> findPersonsByName(String name);

    Person findPersonById(UUID uuid);

    List<Person> findAll();

    @Modifying
    @Query("update Person u set u.name = :name where u.id = :id")
    void updateNameById(@Param(value = "id") UUID id, @Param(value = "name") String name);
}

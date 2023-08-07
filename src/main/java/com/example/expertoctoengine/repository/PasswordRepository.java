package com.example.expertoctoengine.repository;

import com.example.expertoctoengine.model.Password;
import org.springframework.data.repository.CrudRepository;

public interface PasswordRepository extends CrudRepository<Password, Long> {
}

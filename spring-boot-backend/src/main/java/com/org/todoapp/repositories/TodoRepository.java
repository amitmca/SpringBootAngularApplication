package com.org.todoapp.repositories;

import com.org.todoapp.models.Todo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SAGAR
 *
 */
@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

}
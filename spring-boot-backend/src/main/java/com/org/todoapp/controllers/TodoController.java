package com.org.todoapp.controllers;

import javax.validation.Valid;

import com.org.todoapp.models.Todo;
import com.org.todoapp.repositories.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SAGAR
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    /**
     * @return
     */
    @GetMapping("/todos")
    public List<Todo> getAllTodos() {
        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
        return todoRepository.findAll(sortByCreatedAtDesc);
    }

    /**
     * @param todo
     * @return
     */
    @PostMapping("/todos")
    public Todo createTodo(@Valid @RequestBody Todo todo) {
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value="/todos/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") String id) {
        return todoRepository.findById(id)
                .map(todo -> ResponseEntity.ok().body(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * @param id
     * @param todo
     * @return
     */
    @PutMapping(value="/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") String id,
                                           @Valid @RequestBody Todo todo) {
        return todoRepository.findById(id)
                .map(todoData -> {
                    todoData.setTitle(todo.getTitle());
                    todoData.setCompleted(todo.getCompleted());
                    Todo updatedTodo = todoRepository.save(todoData);
                    return ResponseEntity.ok().body(updatedTodo);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping(value="/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String id) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
package fi.academy.todolista.dao;

import fi.academy.todolista.Task;

import java.util.List;
import java.util.Optional;

public interface TodoDao {
    List<Task> listAll();
    int add(Task task);
    Optional<Task> getById(int id);
    Task remove(int id);

}

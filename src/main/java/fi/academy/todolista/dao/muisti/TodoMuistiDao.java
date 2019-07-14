package fi.academy.todolista.dao.muisti;

import fi.academy.todolista.Task;
import fi.academy.todolista.dao.TodoDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("muisti")
public class TodoMuistiDao implements TodoDao {

    private List<Task> tasks;
    private int nextValue=1;

    public TodoMuistiDao(){
        tasks = new ArrayList<>();
        List<Task> tehtavat = Arrays.asList(
                new Task("Vie roskat"),
                new Task("Ime paskaa")
            );
        for (Task t : tehtavat) {
            add(t);
        }

    }


    @Override
    public List<Task> listAll() {
        return tasks;
    }

    @Override
    public Optional<Task> getById(int id) {
        for (Task t : tasks) {
            if (t.getId()==id) {
                return Optional.of(t);
            }
        }

        return Optional.empty();
    }

    @Override
    public int add(Task task) {
        int id = nextValue++;
        task.setId(id);
        tasks.add(task);
        return id;
    }

    @Override
    public Task remove(int id) {
        for(Iterator<Task> it = tasks.iterator(); it.hasNext() ;) {
            Task t = it.next();
            if (t.getId()==id) {
                it.remove();
                return t;
            }
        }

        return null;
    }
}

package fi.academy.todolista;

import fi.academy.todolista.dao.TodoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.annotation.Repeatable;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/todot")
public class TodoController {
    private TodoDao dao;


    @Autowired
    public TodoController(@Qualifier("jdbc") TodoDao dao) {
        this.dao = dao;
    }

    @GetMapping("")
    public List<Task> listAll() {
        List<Task> all = dao.listAll();
        System.out.printf("Haetaan kaikki tehtävät, alkioita: %d kpl\n", all.size());
        return all;
    }

    @PostMapping("")
    public ResponseEntity<?> addTask(@RequestBody Task t) {
        System.out.println("Luodaan uutta");
        int id = dao.add(t);
        System.out.println("Uusi luotu " + t);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(t);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable(name = "id", required = true) int id) {
        Optional<Task> received = dao.getById(id);
        if(!received.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage(String.format("Id &id ei ole olemassa", id)));
        }
        return ResponseEntity.ok(received.get());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTask(@PathVariable int id) {
    Task removed = dao.remove(id);
    if (removed != null) return ResponseEntity.ok(removed);
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorMessage(String.format("Id %d ei ole olemassa: ei muutettu", id)));

    }


}

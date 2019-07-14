package fi.academy.todolista;

public class Task {

    private int id;
    private String task;
    private boolean done;

    public Task() {}

    public Task(String task) {
        this.task = task;
        this.done = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tasks{");
        sb.append("id=").append(id);
        sb.append(", task='").append(task).append('\'');
        sb.append(", done='").append(done).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

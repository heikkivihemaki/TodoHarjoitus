package fi.academy.todolista.dao.jbdc;

import fi.academy.todolista.Task;
import fi.academy.todolista.dao.TodoDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jdbc")
public class JDBCDao implements TodoDao {
    private Connection con;

    public JDBCDao() throws SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/todo", "postgres", "Heikki1");
    }


    @Override
    public List<Task> listAll() {
        String sql = "SELECT * FROM todot";
        List<Task> haetut = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (ResultSet rs = pstmt.executeQuery() ; rs.next() ;) {
                Task t = new Task();
                t.setId(rs.getInt("id"));
                t.setTask(rs.getString("task"));
                t.setDone(rs.getBoolean("done"));
                haetut.add(t);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        return haetut;
    }

    @Override
    public int add(Task task) {
        int key = -1;
        String sql = "INSERT INTO todot(task, done) VALUES (?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, task.getTask());
            pstmt.setBoolean(2, task.isDone());
            pstmt.execute();

            ResultSet keys = pstmt.getGeneratedKeys();
            while (keys.next()) {
                key = keys.getInt("id");
                task.setId(key);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.print("Lisäys ei onnistunut");
        }

        return key;
    }

    @Override
    public Optional<Task> getById(int id) {
        return Optional.empty();
    }

    @Override
    public Task remove(int id) {
        Task t = new Task();
        String sql = "SELECT id, task, done FROM todot WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1,id);
            pstmt.execute();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                t.setId(rs.getInt("id"));
                t.setTask(rs.getString("task"));
                t.setDone(rs.getBoolean("done"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String dsql = "DELETE FROM todot WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(dsql)) {
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return t;
    }
}

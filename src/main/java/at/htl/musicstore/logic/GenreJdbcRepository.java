package at.htl.musicstore.logic;

import at.htl.musicstore.models.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class GenreJdbcRepository extends JdbcRepository<Genre> implements Repository<Genre> {

    public GenreJdbcRepository() {
        super(Genre.class);

        readModels();
    }
    public GenreJdbcRepository(String connectionString, String user, String password) {
        super(Genre.class, connectionString, user, password);
    }

    @Override
    protected void readModels() {
        String sql = "SELECT id, Name FROM Genre";

        try (Connection con = createConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            clear();
            while (rs.next()) {
                Genre model = new Genre();

                model.setId(rs.getInt(1));
                model.setName(rs.getString(2));
                add(model);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void insertModel(Genre model) {
        String sql = String.format("INSERT INTO Genre(id, Name) VALUES(%d, '%s')", model.getId(), model.getName());

        executeSql(sql);
    }
    @Override
    protected void updateModel(Genre model) {
        String sql = String.format("UPDATE Genre Name = '%s' WHERE id = %d", model.getName(), model.getId());

        executeSql(sql);
    }
    @Override
    protected void deleteModel(Genre model) {
        String sql = String.format("DELETE FROM Genre WHERE id = %d", model.getId());

        executeSql(sql);
    }
}

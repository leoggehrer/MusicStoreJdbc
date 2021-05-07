package at.htl.musicstore.logic;

import at.htl.musicstore.models.Artist;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class ArtistJdbcRepository extends JdbcRepository<Artist> implements Repository<Artist> {

    public ArtistJdbcRepository() {
        super(Artist.class);

        readModels();
    }

    @Override
    protected void readModels() {
        String sql = "SELECT id, Name FROM Artist";

        try (Connection con = createConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            clear();
            while (rs.next()) {
                Artist model = new Artist();

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
    protected void insertModel(Artist model) {
        String sql = String.format("INSERT INTO Artist(id, Name) VALUES(%d, '%s')", model.getId(), model.getName());

        executeSql(sql);
    }
    @Override
    protected void updateModel(Artist model) {
        String sql = String.format("UPDATE Artist Name = '%s' WHERE id = %d", model.getName(), model.getId());

        executeSql(sql);
    }
    @Override
    protected void deleteModel(Artist model) {
        String sql = String.format("DELETE FROM Artist WHERE id = %d", model.getId());

        executeSql(sql);
    }
}

package at.htl.musicstore.logic;

import at.htl.musicstore.models.Album;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class AlbumJdbcRepository extends JdbcRepository<Album> implements Repository<Album> {

    public AlbumJdbcRepository() {
        super(Album.class);

        readModels();
    }

    @Override
    protected void readModels() {
        String sql = "SELECT id, Title, ArtistId FROM Album";

        try (Connection con = createConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            clear();
            while (rs.next()) {
                Album model = new Album();

                model.setId(rs.getInt(1));
                model.setTitle(rs.getString(2));
                model.setArtistId(rs.getInt(3));
                add(model);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void insertModel(Album model) {
        String sql = String.format("INSERT INTO Album(id, Title, ArtistId) VALUES(%d, '%s', %d)", model.getId(), model.getTitle(), model.getArtistId());

        executeSql(sql);
    }
    @Override
    protected void updateModel(Album model) {
        String sql = String.format("UPDATE Album Title = '%s', ArtistId = %d WHERE id = %d", model.getTitle(), model.getArtistId(), model.getId());

        executeSql(sql);
    }
    @Override
    protected void deleteModel(Album model) {
        String sql = String.format("DELETE FROM Album WHERE id = %d", model.getId());

        executeSql(sql);
    }
}

package at.htl.musicstore.logic;

import at.htl.musicstore.models.Track;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class TrackJdbcRepository extends JdbcRepository<Track> implements Repository<Track> {

    public TrackJdbcRepository() {
        super(Track.class);

        readModels();
    }

    @Override
    protected void readModels() {
        String sql = "SELECT id, Name, GenreId, AlbumId FROM Track";

        try (Connection con = createConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            clear();
            while (rs.next()) {
                Track model = new Track();

                model.setId(rs.getInt(1));
                model.setName(rs.getString(2));
                model.setGenreId(rs.getInt(3));
                model.setAlbumId(rs.getInt(4));
                add(model);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void insertModel(Track model) {
        String sql = String.format("INSERT INTO Track(id, Name, GenreId, AlbumId) VALUES(%d, '%s', %d, %d)", model.getId(), model.getName(), model.getGenreId(), model.getAlbumId());

        executeSql(sql);
    }
    @Override
    protected void updateModel(Track model) {
        String sql = String.format("UPDATE Track Name = '%s', GenreId = %d, AlbumId = %d WHERE id = %d", model.getName(), model.getGenreId(), model.getAlbumId(), model.getId());

        executeSql(sql);
    }
    @Override
    protected void deleteModel(Track model) {
        String sql = String.format("DELETE FROM Track WHERE id = %d", model.getId());

        executeSql(sql);
    }
}

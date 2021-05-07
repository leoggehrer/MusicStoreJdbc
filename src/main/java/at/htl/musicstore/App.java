package at.htl.musicstore;

import at.htl.musicstore.logic.*;
import at.htl.musicstore.models.Album;
import at.htl.musicstore.models.Artist;
import at.htl.musicstore.models.Genre;
import at.htl.musicstore.models.Track;

/**
 * MusicStore
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello MusicStore!" );
        GenreJdbcRepository jdbcRepo = new GenreJdbcRepository();
        for (Genre model : jdbcRepo.getAll()) {
            System.out.printf("%d %s\n", model.getId(), model.getName());
        }

        printAll();
    }

    private static void printAll() {
        System.out.printf("\nGenre:\n");
        try (GenreCsvRepository csvRepo = new GenreCsvRepository("Genre.csv")) {
            for (Genre m : csvRepo.getAll()) {
                System.out.printf("%s\n", m);
            }
            Genre ng = csvRepo.create();
            ng.setName("Geri-Rock");
            csvRepo.insert(ng);
//            csvRepo.save();
        }
        System.out.printf("\nArtist:\n");
        try (ArtistCsvRepository csvRepo = new ArtistCsvRepository("Artist.csv")) {
            for (Artist m : csvRepo.getAll()) {
                System.out.printf("%s\n", m);
            }
        }
        System.out.printf("\nTrack:\n");
        try (TrackCsvRepository csvRepo = new TrackCsvRepository("Track.csv")) {
            for (Track m : csvRepo.getAll()) {
                System.out.printf("%d %s %d %d\n", m.getId(), m.getName(), m.getAlbumId(), m.getGenreId());
            }
        }
        System.out.printf("\nAlbum:\n");
        try (AlbumCsvRepository csvRepo = new AlbumCsvRepository("Album.csv")) {
            for (Album m : csvRepo.getAll()) {
                System.out.printf("%s\n", m);
            }
        }
    }
    private static void importAll() {
        importGenre("Genre.csv");
        importArtist("Artist.csv");
        importAlbum("Album.csv");
        importTrack("Track.csv");
    }
    private static void importGenre(String filePath) {
        try (GenreCsvRepository csvRepo = new GenreCsvRepository(filePath);
             GenreJdbcRepository jdbcRepo = new GenreJdbcRepository()) {
            for (Genre model : csvRepo.getAll()) {
                System.out.printf("%d %s\n", model.getId(), model.getName());
                jdbcRepo.insert(model);
            }
            jdbcRepo.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void importArtist(String filePath) {
        try (ArtistCsvRepository csvRepo = new ArtistCsvRepository(filePath);
             ArtistJdbcRepository jdbcRepo = new ArtistJdbcRepository()) {
            for (Artist model : csvRepo.getAll()) {
                System.out.printf("%d %s\n", model.getId(), model.getName());
                jdbcRepo.insert(model);
            }
            jdbcRepo.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void importAlbum(String filePath) {
        try (AlbumCsvRepository csvRepo = new AlbumCsvRepository(filePath);
             AlbumJdbcRepository jdbcRepo = new AlbumJdbcRepository()) {
            for (Album model : csvRepo.getAll()) {
                System.out.printf("%d %s %d\n", model.getId(), model.getTitle(), model.getArtistId());
                jdbcRepo.insert(model);
            }
            jdbcRepo.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void importTrack(String filePath) {
        try (TrackCsvRepository csvRepo = new TrackCsvRepository(filePath);
             TrackJdbcRepository jdbcRepo = new TrackJdbcRepository()) {
            for (Track model : csvRepo.getAll()) {
                System.out.printf("%d %s %d %d\n", model.getId(), model.getName(), model.getGenreId(), model.getAlbumId());
                jdbcRepo.insert(model);
            }
            jdbcRepo.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

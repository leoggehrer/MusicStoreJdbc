package at.htl.musicstore.logic;

import at.htl.musicstore.models.Track;

public final class TrackCsvRepository extends CsvRepository<Track> implements Repository<Track> {

    public TrackCsvRepository(String filePath) {
        super(Track.class, filePath);

        readModels();
    }

    @Override
    protected String getHeader() {
        return "Id;Name;GenreId;AlbumId";
    }
    @Override
    protected Track convertCsvToModel(String line) {
        Track model = create();
        String[] data = line.split(";");

        model.setId(Integer.parseInt(data[0]));
        model.setName(data[1]);
        model.setAlbumId(Integer.parseInt(data[2]));
        model.setGenreId(Integer.parseInt(data[4]));
        return model;
    }
    @Override
    protected String convertModelToCsv(Track model){
        return String.format("%d;%s;%d;%d", model.getId(), model.getName(), model.getGenreId(), model.getAlbumId());
    }
}

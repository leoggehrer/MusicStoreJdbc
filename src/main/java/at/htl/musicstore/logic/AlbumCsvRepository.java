package at.htl.musicstore.logic;

import at.htl.musicstore.models.Album;

public final class AlbumCsvRepository extends CsvRepository<Album> implements Repository<Album> {

    public AlbumCsvRepository(String filePath) {
        super(Album.class, filePath);

        readModels();
    }
    @Override
    protected String getHeader() {
        return "Id;Name";
    }
    @Override
    protected Album convertCsvToModel(String line) {
        Album model = create();
        String[] data = line.split(";");

        model.setId(Integer.parseInt(data[0]));
        model.setTitle(data[1]);
        model.setArtistId(Integer.parseInt(data[2]));
        return model;
    }
    @Override
    protected String convertModelToCsv(Album model){
        return String.format("%d;%s;%d", model.getId(), model.getTitle(), model.getArtistId());
    }
}

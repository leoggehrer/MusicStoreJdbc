package at.htl.musicstore.logic;

import at.htl.musicstore.models.Artist;

public final class ArtistCsvRepository extends CsvRepository<Artist> implements Repository<Artist> {

    public ArtistCsvRepository(String filePath) {
        super(Artist.class, filePath);

        readModels();
    }
    @Override
    protected String getHeader() {
        return "Id;Name";
    }
    @Override
    protected Artist convertCsvToModel(String line) {
        Artist model = create();
        String[] data = line.split(";");

        model.setId(Integer.parseInt(data[0]));
        model.setName(data[1]);
        return model;
    }
    @Override
    protected String convertModelToCsv(Artist model){
        return String.format("%d;%s", model.getId(), model.getName());
    }
}

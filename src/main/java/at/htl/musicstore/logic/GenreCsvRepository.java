package at.htl.musicstore.logic;

import at.htl.musicstore.models.Genre;

public final class GenreCsvRepository extends CsvRepository<Genre> implements Repository<Genre> {

    public GenreCsvRepository(String filePath) {
        super(Genre.class, filePath);

        readModels();
    }

    @Override
    protected String getHeader() {
        return "Id;Name";
    }
    @Override
    protected Genre convertCsvToModel(String line) {
        Genre model = create();
        String[] data = line.split(";");

        model.setId(Integer.parseInt(data[0]));
        model.setName(data[1]);
        return model;
    }
    @Override
    protected String convertModelToCsv(Genre model){
        return String.format("%d;%s", model.getId(), model.getName());
    }
}

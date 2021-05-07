package at.htl.musicstore.logic;

import at.htl.musicstore.models.IdentityObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CsvRepository<T extends IdentityObject> extends GenericRepository<T> implements AutoCloseable {
    protected String filePath;

    protected CsvRepository(Class<T> cls, String filePath) {
        super(cls);
        this.filePath = filePath;
    }

    protected abstract String getHeader();
    protected abstract T convertCsvToModel(String csvLine);
    protected abstract String convertModelToCsv(T model);

    protected void readModels() {
        clear();
        for (String line : readFileData()) {
            add(convertCsvToModel(line));
        }
    }
    protected String[] readFileData() {
        List<String> lines = new ArrayList<>();

        try {
            File f = new File(filePath);

            if (f.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line = reader.readLine();

                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines.toArray(new String[lines.size()]);
    }
    protected void writeFileData(String[] lines) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String line : lines) {
                writer.append(line);
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        List<String> data = new ArrayList<>();

        data.add(getHeader());
        for (int i = 0; i < getSize(); i++) {
            T m = getAt(i);
            State state = getStates().get(i);

            if (state == State.Current || state == State.Added || state == State.Modified) {
                data.add(convertModelToCsv(m));
            }
        }
        writeFileData(data.toArray(new String[data.size()]));
        readModels();
    }
    public void close() {
        clear();
    }
}

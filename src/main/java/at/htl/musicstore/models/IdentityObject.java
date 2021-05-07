package at.htl.musicstore.models;

public class IdentityObject {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%-4d", id);
    }
}

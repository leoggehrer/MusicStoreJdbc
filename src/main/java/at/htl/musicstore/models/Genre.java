package at.htl.musicstore.models;

public class Genre extends IdentityObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s %-25s", super.toString(), getName());
    }
}

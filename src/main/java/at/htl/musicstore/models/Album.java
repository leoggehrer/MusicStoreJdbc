package at.htl.musicstore.models;

public class Album extends IdentityObject {
    private int artistId;
    private String title;

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return String.format("%s %-4d %-50s", super.toString(), getArtistId(), getTitle());
    }
}

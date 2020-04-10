public class Album {
    private int id;
    private String name;
    private int artistId;
    private int releasedYear;

    public Album(int id, String name, int artistId, int releasedYear) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
        this.releasedYear = releasedYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getReleasedYear() {
        return releasedYear;
    }

    public void setReleasedYear(int releasedYear) {
        this.releasedYear = releasedYear;
    }
}

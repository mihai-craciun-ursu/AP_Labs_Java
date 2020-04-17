package entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ALBUM")
@NamedQueries({
        @NamedQuery(name = "Album.findByName",
                query = "SELECT album FROM Album album where album.name=:name"),
        @NamedQuery(name = "Album.findByArtist",
                query = "SELECT album FROM Album album where album.artistID=:artistID")
})
public class Album {

    @Id
    @Column(name="ID")
    @GeneratedValue(generator = "incrementator")
    @GenericGenerator(name = "incrementator", strategy = "increment")
    private int id;

    public Album(String name, int artistID, int releasedYear, String gendre) {
        this.name = name;
        this.artistID = artistID;
        this.releasedYear = releasedYear;
        this.gendre = gendre;
    }

    public Album(){
        this.name = "Unknown";
        this.artistID = 1;
        this.releasedYear = 2000;
        this.gendre = "rock";
    }

    @Column(name="NAME")
    private String name;

    @Column(name="ARTIST_ID")
    private int artistID;

    @Column(name="RELEASED_YEAR")
    private int releasedYear;

    @Column(name="GENDRE")
    private String gendre;

    public String getGendre() {
        return gendre;
    }

    public void setGendre(String gendre) {
        this.gendre = gendre;
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

    public int getArtistID() {
        return artistID;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public int getReleasedYear() {
        return releasedYear;
    }

    public void setReleasedYear(int releasedYear) {
        this.releasedYear = releasedYear;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artistID=" + artistID +
                ", releasedYear=" + releasedYear +
                ", gendre='" + gendre + '\'' +
                '}';
    }
}

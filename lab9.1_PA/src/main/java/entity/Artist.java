package entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "artist")
@NamedQueries({
        @NamedQuery(name = "Artist.findByName",
                    query = "SELECT artist FROM Artist artist where artist.name=:name")
})
public class Artist {
    @Id
    @Column(name="ID")
    @GeneratedValue(generator = "incrementator")
    @GenericGenerator(name = "incrementator", strategy = "increment")
    private int id;

    public Artist(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Artist() {
        this.name = "Unknown";
        this.country = "Unknown";
    }

    @Column(name="NAME")
    private String name;

    @Column(name="COUNTRY")
    private String country;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

package entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table
@Entity(name = "RANKING")
public class Ranking {

    @Id
    @Column(name="ID")
    @GeneratedValue(generator = "incrementator")
    @GenericGenerator(name = "incrementator", strategy = "increment")
    private int id;

    @Column(name = "ID_ALBUM")
    private int idAlbum;

    @Column(name = "RANK")
    private int rank;

    @ManyToOne
    @JoinColumn(name = "ID_CHART")
    private Chart chart;

    public Ranking(int idAlbum, int rank) {
        this.idAlbum = idAlbum;
        this.rank = rank;
    }

    public Ranking(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "id=" + id +
                ", idAlbum=" + idAlbum +
                ", rank=" + rank +
                ", chart=" + chart +
                '}';
    }
}

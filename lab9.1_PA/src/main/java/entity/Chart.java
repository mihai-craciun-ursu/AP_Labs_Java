package entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity(name = "CHART")
@NamedQueries({
        @NamedQuery(name = "Chart.findByName",
                query = "SELECT chart FROM CHART chart where chart.name=:name")
})
public class Chart {

    @Id
    @Column(name="ID")
    @GeneratedValue(generator = "incrementator")
    @GenericGenerator(name = "incrementator", strategy = "increment")
    private int id;

    @Column(name="NAME")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CHART")
    private Set<Ranking> rankingSet = new HashSet<Ranking>();

    public Chart(String name) {
        this.name = name;
    }

    public Chart(){
        this.name = "Unknown";
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

    public Set<Ranking> getRankingSet() {
        return rankingSet;
    }

    public void setRankingSet(Set<Ranking> rankingSet) {
        this.rankingSet = rankingSet;
    }

    @Override
    public String toString() {
        return "Chart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

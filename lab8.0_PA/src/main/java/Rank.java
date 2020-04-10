import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Rank {
    private int id;
    private int chartId;
    private int albumId;
    private int rank;



    public Rank(int id, int chartId, int albumId, int rank) {
        this.id = id;
        this.chartId = chartId;
        this.albumId = albumId;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public int getChartId() {
        return chartId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getRank() {
        return rank;
    }
}

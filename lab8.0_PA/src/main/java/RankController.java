import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class RankController {



    private DataBase dataBase = DataBase.getInstance();

    public void create(int rank, int chartId, int albumId){
        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            stmt.executeQuery(String.format("INSERT INTO RANKING(ID , id_chart, id_album, rank) VALUES (ranking_seq.NEXTVAL,%d, %d, %d)", chartId, albumId, rank));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<Rank> findAllByChart(int chartId){
        List<Rank> rankList = new ArrayList<Rank>();

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM RANKING WHERE id_chart = " + chartId);

            while(rs.next()){
                Rank rank = new Rank(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
                rankList.add(rank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rankList;
    }

    public void showArtistRankings(){
        List<Rank> rankList = new ArrayList<Rank>();
        Map<Artist, Integer> mapOfArtists = new HashMap<Artist, Integer>();

        ArtistController artistController = new ArtistController();
        AlbumController albumController = new AlbumController();

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM RANKING");

            while(rs.next()){
                Rank rank = new Rank(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
                rankList.add(rank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Rank rank: rankList){
            Artist artist = albumController.getAlbumArtist(rank.getAlbumId());
            int rankValue = 0;
            if(mapOfArtists.containsKey(artist)){
                rankValue = mapOfArtists.get(artist).intValue();
            }

            mapOfArtists.put(artist, rankValue + (100/rank.getRank()));
        }

        for(Artist artist: sortByValue((HashMap<Artist, Integer>) mapOfArtists).keySet()){
            System.out.println(artist.getName() + ": " + mapOfArtists.get(artist));
        }


    }

    private static HashMap<Artist, Integer> sortByValue(HashMap<Artist, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Artist, Integer> > list =
                new LinkedList<Map.Entry<Artist, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Artist, Integer> >() {
            public int compare(Map.Entry<Artist, Integer> o1,
                               Map.Entry<Artist, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Artist, Integer> temp = new LinkedHashMap<Artist, Integer>();
        for (Map.Entry<Artist, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}

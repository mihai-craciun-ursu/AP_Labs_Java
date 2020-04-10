import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistController {

    private DataBase dataBase = DataBase.getInstance();



    public ArtistController(){

    }

    public void create(String name, String country){
        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            stmt.executeQuery(String.format("INSERT INTO ARTIST(ID ,NAME, COUNTRY) VALUES (artist_seq.NEXTVAL,'%s','%s')", name, country));

        } catch (SQLException e) {
            System.out.println(name + " " + country);
            e.printStackTrace();
        }


    }

    public List<Artist> findByName(String name){
        List<Artist> artistList = new ArrayList<Artist>();

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM ARTIST WHERE NAME LIKE '%"+ name +"%'");

            while(rs.next()){
                Artist artist = new Artist(rs.getInt(1), rs.getString(2), rs.getString(3));
                artistList.add(artist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artistList;
    }

    public Artist findById(int id){
        Artist artist = null;

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM ARTIST WHERE ID="+ id);

            while(rs.next()){
                artist = new Artist(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artist;
    }


}

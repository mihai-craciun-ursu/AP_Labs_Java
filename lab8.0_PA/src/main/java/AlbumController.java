import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumController {
    private DataBase dataBase = DataBase.getInstance();

    public AlbumController(){

    }

    public void create(String name, int artistId, int releasedYear){
        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            stmt.executeQuery(String.format("INSERT INTO ALBUM(ID, NAME, artist_id, released_year) VALUES (album_seq.NEXTVAL, '%s', %d, %d)", name, artistId, releasedYear));
        } catch (SQLException e) {
            System.out.println(name);
            System.out.println(artistId);
            e.printStackTrace();
        }
    }

    public List<Album> findByArtist(int artistId){
        List<Album> albumList = new ArrayList<Album>();

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM ALBUM WHERE ARTIST_ID = " + artistId);

            while(rs.next()){
                Album album = new Album(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                albumList.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return albumList;
    }

    public Artist getAlbumArtist(int id){
        Artist artist = null;

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT artist_id, art.name, art.country FROM ALBUM alb JOIN ARTIST art ON alb.artist_id = art.ID WHERE alb.ID="+ id);

            while(rs.next()){
                artist = new Artist(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artist;
    }

    public boolean checkIfValid(int albumId){
        List<Album> albumList = new ArrayList<Album>();

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM ALBUM WHERE ID = " + albumId);

            while(rs.next()){
                Album album = new Album(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                albumList.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(albumList.size() > 0){
            return true;
        }else{
            return false;
        }
    }
}

package app.controllers;

import app.models.Player;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.crypto.Cipher.SECRET_KEY;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private DataBase dataBase = DataBase.getInstance();
    private final static String SECRET_DISCRET = "secret";


    @PostMapping(value="/login", produces = "application/json", consumes="application/json")
    public String login(HttpServletResponse response , @RequestBody Player player){
        List<Player> playerList = new ArrayList<>();

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT id, name, password FROM PLAYER WHERE name='"+player.getName() +"' AND password = '"+player.getPassword()+"'");

            while(rs.next()){
                Player playerObj = new Player(rs.getInt(1), rs.getString(2), "*");
                playerList.add(playerObj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(playerList.size() > 0) {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            //We will sign our JWT with our ApiKey secret
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_DISCRET);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            String id = String.valueOf(playerList.get(0).getId());
            JwtBuilder builder = Jwts.builder().setId(id).setSubject(id).setIssuer(id).signWith(signatureAlgorithm, signingKey);


            long expMillis = nowMillis + 1000000L;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);

            response.addHeader("Auth-Token", builder.compact());

            return "success";
        }else{
            return "false";
        }
    }

    @GetMapping(value = "/list", produces = "application/json")
    public List<Player> getPlayers(@RequestHeader("Auth-Token") String token){
        int id;
        List<Player> playerList = new ArrayList<>();

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_DISCRET))
                    .parseClaimsJws(token).getBody();


            id = Integer.parseInt(claims.getId());

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT id, name, password FROM PLAYER");

            while(rs.next()){
                Player player = new Player(rs.getInt(1), rs.getString(2), "*");
                playerList.add(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerList;
    }

    @PostMapping(value = "/register", produces = "application/json", consumes="application/json")
    public Player addPlayer(@RequestBody Player player){

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            stmt.executeQuery(String.format("INSERT INTO PLAYER(ID ,NAME, PASSWORD) VALUES (artist_seq.NEXTVAL,'%s','%s')", player.getName(), player.getPassword()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player;
    }

    @PutMapping(value = "/modify", produces =  "application/json")
    public boolean modifyUser(@RequestHeader("Auth-Token") String token, @RequestBody Player player){
        int id;

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_DISCRET))
                    .parseClaimsJws(token).getBody();


            id = Integer.parseInt(claims.getId());

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            stmt.executeQuery(String.format("UPDATE PLAYER SET NAME = '" + player.getName() + "' WHERE ID="+id));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    @DeleteMapping(value = "/delete", produces = "application/json")
    public boolean deleteUser(@RequestHeader("Auth-Token") String token){
        int id;

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_DISCRET))
                    .parseClaimsJws(token).getBody();


            id = Integer.parseInt(claims.getId());

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            stmt.executeQuery(String.format("DELETE FROM PLAYER WHERE ID="+id));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

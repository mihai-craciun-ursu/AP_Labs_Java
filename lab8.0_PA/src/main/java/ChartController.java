import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChartController {
    private DataBase dataBase = DataBase.getInstance();


    public void create(String name){
        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            stmt.executeQuery(String.format("INSERT INTO CHART(ID ,NAME) VALUES (chart_seq.NEXTVAL,'%s')", name));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<Chart> findAll(String name){
        List<Chart> chartList = new ArrayList<Chart>();

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM CHART");

            while(rs.next()){
                Chart chart = new Chart(rs.getInt(1), rs.getString(2));
                chartList.add(chart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chartList;
    }

    public boolean checkIfValidChart(int chartId){
        List<Chart> chartList = new ArrayList<Chart>();

        Statement stmt;
        try {
            stmt = dataBase.getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM CHART WHERE ID=" + chartId);

            while(rs.next()){
                Chart chart = new Chart(rs.getInt(1), rs.getString(2));
                chartList.add(chart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(chartList.size() > 0) {
            return true;
        }else{
            return false;
        }
    }
}

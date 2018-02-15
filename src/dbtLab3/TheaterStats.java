package dbtLab3;
import java.sql.ResultSet;
import java.sql.SQLException;
public class TheaterStats {
    public final String theater_name;
    public final int seats;


    public TheaterStats(ResultSet rs) throws SQLException {
        this.theater_name = rs.getString("theater_name");
        this.seats = rs.getInt("seats");

    }
}

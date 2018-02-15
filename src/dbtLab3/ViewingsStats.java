package dbtLab3;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewingsStats {
    public final String movie_name;
    public final String viewing_date;
    public final String theater_name;


    public ViewingsStats(ResultSet rs) throws SQLException {
        this.movie_name = rs.getString("movie_name");
        this.viewing_date = rs.getString("viewing_date");
        this.theater_name = rs.getString("theater_name");
    }
}
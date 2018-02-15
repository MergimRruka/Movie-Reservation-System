package dbtLab3;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationStats {
    public final String movie_name;
    public final String user_id;
    public final String viewing_date;
    public final int res_nbr;
    public final int count;

    public ReservationStats(ResultSet rs) throws SQLException{
        this.movie_name = rs.getString("movie_name");
        this.user_id = rs.getString("user_id");
        this.viewing_date = rs.getString("viewing_date");
        this.res_nbr = rs.getInt("res_nbr");
        this.count = rs.getInt("cnt");
    }
}

package dbtLab3;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieStats {
    public final String movie_name;

    public MovieStats(ResultSet rs) throws SQLException{
        this.movie_name = rs.getString("movie_name");
    }

}

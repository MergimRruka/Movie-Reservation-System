package dbtLab3;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersStats {
    public final String user_id;
    public final String name;
    public final String adress;
    public final String phone_nbr;


    public UsersStats(ResultSet rs) throws SQLException {
        this.user_id = rs.getString("user_id");
        this.name = rs.getString("name");
        this.adress = rs.getString("adress");
        this.phone_nbr = rs.getString("phone_nbr");
    }
}


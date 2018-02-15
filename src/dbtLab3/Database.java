package dbtLab3;

import javax.swing.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Database is an interface to the college application database, it
 * uses JDBC to connect to a SQLite3 file.
 */
public class Database {

    /**
     * The database connection.
     */
    private Connection conn;

    /**
     * Creates the database interface object. Connection to the
     * database is performed later.
     */
    public Database() {
        conn = null;
    }

    /**
     * Opens a connection to the database, using the specified
     * filename (if we'd used a traditional DBMS, such as PostgreSQL
     * or MariaDB, we would have specified username and password
     * instead).
     */
    public boolean openConnection(String filename) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the connection to the database has been established
     *
     * @return true if the connection has been established
     */
    public boolean isConnected() {
        return conn != null;
    }


    public List<ViewingsStats> getViewingStats(String movie_name) {
        List<ViewingsStats> found = new LinkedList<>();
        String query =
                "SELECT  *\n" +
                        "FROM    viewings\n" +
                        "WHERE  movie_name = ?\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movie_name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(new ViewingsStats(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }

    public String getTheater(String movie_name, String date) {
        ViewingsStats found = null;
        String query =
                "SELECT  *\n" +
                        "FROM    viewings\n" +
                        "WHERE  (movie_name = ? AND viewing_date = ?)\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movie_name);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found = new ViewingsStats(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found.theater_name;
    }

    public int getSeats(String theater_name) {
        TheaterStats found = null;
        String query =
                "SELECT  *\n" +
                        "FROM    theaters\n" +
                        "WHERE  theater_name = ?\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, theater_name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found = new TheaterStats(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found.seats;
    }

    public ReservationStats reservationAmount(String movie_name, String date) {
        ReservationStats found = null;
        String query =
                "SELECT    movie_name, user_id,viewing_date, res_nbr ,COUNT() AS cnt\n" +
                        "FROM      reservations\n" +
                        "WHERE (movie_name = ? AND viewing_date = ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movie_name);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found = new ReservationStats(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }

    public void makeReservation(String user_id, String viewing_date, String movie_name) {
        String query =
                "INSERT\n" +
                        "INTO   reservations(user_id,viewing_date, movie_name)\n" +
                        "VALUES ( ?, ?,?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            conn.setAutoCommit(false);
            ps.setString(1, user_id);
            ps.setString(2, viewing_date);
            ps.setString(3, movie_name);
            ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean UserFound(String user_id) {
        String query =
                "SELECT  *\n" +
                        "FROM    users\n" +
                        "WHERE  user_id = ?\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user_id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<MovieStats> movies() {
        List<MovieStats> found = new LinkedList<>();
        String query =
                "SELECT  *\n" +
                        "FROM    movies\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(new MovieStats(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return found;
    }

}

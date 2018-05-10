
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class DB {

    static String db_url = "jdbc:sqlite:movie.db";


    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS movies  (id INTEGER PRIMARY KEY, title TEXT, yr INTEGER, rating DOUBLE," +
                                                "review TEXT, director TEXT,actors TEXT, imdb TEXT,plot TEXT,pic TEXT)";
    private static final String ADD_DATA = "INSERT INTO movies (title, yr, rating,review, director, actors,imdb,plot, pic) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String DROP = "DROP TABLE movies";

    DB(){
        createTable();
    }

    // creates the table
    private void createTable(){

        try(Connection connection = DriverManager.getConnection(db_url)){
            Statement statement = connection.createStatement();

            statement.executeUpdate(CREATE_TABLE);

        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");
        }

    }

    // adding a movie to the DB
    void addData(String title, int yr,double rating, String review, String diretor,String actors, String imdb,String plot,String pic){
        try(Connection connection = DriverManager.getConnection(db_url)){
            PreparedStatement ps = connection.prepareStatement(ADD_DATA);

            ps.setString(1,title);
            ps.setInt(2,yr);
            ps.setDouble(3,rating);
            ps.setString(4,review);
            ps.setString(5,diretor);
            ps.setString(6,actors);
            ps.setString(7,imdb);
            ps.setString(8,plot);
            ps.setString(9,pic);
            ps.execute();



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");
        }
    }

    // getting all the movies from the DB
    ArrayList<Movie> getAll(){
        final String getAllSQL = "SELECT * FROM movies";
        ArrayList<Movie> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(db_url)){
            PreparedStatement ps = connection.prepareStatement(getAllSQL);

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()){
                System.out.println("Empty");
                return null;
            }else {
                while (rs.next()){
                    String[] actors =rs.getString("actors").split(",");
                    Movie movie = new Movie(rs.getString("title"),rs.getInt("yr"),rs.getDouble("rating"),
                            rs.getString("review"),rs.getString("director"),rs.getString("actors"),rs.getString("imdb"),rs.getString("plot"),
                            rs.getString("pic"));
                    list.add(movie);
                }
                // returns a list of movie objects
                return list;
            }



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");
            return null;
        }
    }
    // updates any reviews
    void updateData(String review,String imdb){
        String updateReview = "UPDATE movies SET review = ? WHERE imdb = ?";

        try(Connection connection = DriverManager.getConnection(db_url)){
            PreparedStatement ps = connection.prepareStatement(updateReview);

            ps.setString(1,review);
            ps.setString(2,imdb);

            ps.executeUpdate();



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");

        }

    }
    // deletes a movie from the DB
    void deleteItem(String imdb){
        String deleteMovie = "DELETE FROM movies WHERE imdb = ?";

        try(Connection connection = DriverManager.getConnection(db_url)){
            PreparedStatement ps = connection.prepareStatement(deleteMovie);


            ps.setString(1,imdb);

            ps.executeUpdate();



        }catch (SQLException sqle){
            System.out.println("SQL EXCEPTION");

        }
    }
    // getting individual movie
    Movie getMovie(String imdb){
        String getMovie = "SELECT * FROM movies WHERE imdb = ?";

        try(Connection connection = DriverManager.getConnection(db_url)){
            PreparedStatement ps = connection.prepareStatement(getMovie);

            ArrayList<Movie> movieList = new ArrayList<>();

            ps.setString(1,imdb);


            ResultSet rs = ps.executeQuery();


            while (rs.next()){
                Movie movie = new Movie(rs.getString("title"),rs.getInt("yr"),rs.getDouble("rating"),
                        rs.getString("review"),rs.getString("director"),rs.getString("actors"),rs.getString("imdb"),
                        rs.getString("plot"),
                        rs.getString("pic"));
                movieList.add(movie);
            }

            return movieList.get(0);

        }catch (SQLException sqle){
            System.out.println(sqle);
            return null;
        }
    }


}



import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class API {
    String baseURL = "http://www.omdbapi.com/?apikey=4efcd7de&s=";
    String imdbURL = "http://www.omdbapi.com/?apikey=4efcd7de&i=";

    ArrayList<Movie> searchMovies(String name) {
        String url = baseURL+name;
        ArrayList<Movie> allMovies = new ArrayList<>();
        try {
            InputStream stream = new URL(url).openConnection().getInputStream();

            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Read stream into String. Use StringBuilder to put multiple lines together.
            // Read lines in a loop until the end of the stream.
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            //and turn the StringBuilder into a String.
            String responseString = builder.toString();


            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            System.out.println(jsonArray);
            for (int x =0; x < jsonArray.length();x++){
                JSONObject movie = jsonArray.getJSONObject(x);
                if (movie.getString("Type").equalsIgnoreCase("movie")) {
                    Movie movie1 = new Movie(movie.getString("Title"), movie.getInt("Year"), movie.getString("imdbID"));
                    allMovies.add(movie1);
                }
            }
            return allMovies;

        }catch (Exception e){
            System.out.println(e);
            return allMovies;
        }
    }

    Movie getMovie(String imdb){
        String url = imdbURL+imdb;
        try {
            InputStream stream = new URL(url).openConnection().getInputStream();

            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Read stream into String. Use StringBuilder to put multiple lines together.
            // Read lines in a loop until the end of the stream.
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            //and turn the StringBuilder into a String.
            String responseString = builder.toString();


            JSONObject jsonObject = new JSONObject(responseString);
            String[] actors = jsonObject.getString("Actors").split(",");
            StringBuilder actor = new StringBuilder();
            for (String a: actors){
                actor.append(a+", ");
            }

            Movie movie = new Movie(jsonObject.getString("Title"),jsonObject.getInt("Year"),jsonObject.getDouble("imdbRating"),
                    "",jsonObject.getString("Director"),actors.toString(),jsonObject.getString("imdbID"),jsonObject.getString("Plot"),jsonObject.getString("Poster"));

            return movie;

        }catch (Exception e){
            System.out.println(e);
            return null;

    }
    }
}

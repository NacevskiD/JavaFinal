import java.util.ArrayList;

public class Movie {
    String title;
    int releaseYear;
    double rating;
    String review;
    String director;
    String actors;
    String imdb;
    String plot;
    String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public Movie(String title, int releaseYear, double rating, String review, String director, String actors,String imdb,String plot,String pic) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.review = review;
        this.director = director;
        this.actors = actors;
        this.imdb = imdb;
        this.plot = plot;
        this.pic = pic;
    }

    public Movie(String title, int year,String imdb){
        this.title = title;
        this.releaseYear = year;
        this.imdb = imdb;

    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                ", director='" + director + '\'' +
                ", actors=" + actors +
                ", imdb='" + imdb + '\'' +
                ", plot='" + plot + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}

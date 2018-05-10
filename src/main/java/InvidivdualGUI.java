import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.security.Guard;
import java.util.ArrayList;

public class InvidivdualGUI  extends JFrame{
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JLabel picLabel;
    private JLabel releaseYearLabel;
    private JLabel ratingLabel;
    private JLabel directorLabel;
    private JLabel actorsLabel;
    private JLabel imdbLabel;
    private JLabel plotLabel;
    private JTextArea reviewTextArea;
    private JButton saveButton;
    private JTextArea plotTextArea;
    DB db;
    API api;
    Movie movie;
    GUI gui;

    // GUI from search table based on IMDB
    InvidivdualGUI(String imdb1){

        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000,500);
        setResizable(false);
        db = new DB();
        api = new API();

        // loads all the elements from the API
        setElements(imdb1);

        // saves data to db
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.addData(movie.getTitle(),movie.getReleaseYear(),movie.getRating(),reviewTextArea.getText(),movie.getDirector(),
                        movie.getActors(),movie.getImdb(),movie.getPlot(),movie.getPic());
                showAlertDialog("Movie added to database!");
            }
        });
    }

    // loads data from the DB
    InvidivdualGUI(Movie movie){
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000,500);
        setResizable(false);
        db = new DB();
        api = new API();

        //loading all the data
        setElementsDB(movie);
        // updates data to the DB
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String review = reviewTextArea.getText();
                db.updateData(review,movie.getImdb());
                showAlertDialog("Movie saved to database!");
                gui.setMyMoviesData();
                gui.myMoviesTableModel.fireTableDataChanged();
            }
        });

    }


    void setImage(String path){
        try {

            System.out.println("Get Image from " + path);
            URL url = new URL(path);
            BufferedImage image = ImageIO.read(url);

            System.out.println("Load image into frame...");
            ImageIcon pic = new ImageIcon(image);

            Image image2 = pic.getImage(); // transform it
            Image newimg = image2.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            pic = new ImageIcon(newimg);  // transform it back
            picLabel.setIcon(pic);
        }catch (Exception e){
            System.out.println("Exception");
        }
    }

    void setElements(String imdb){
        movie = api.getMovie(imdb);
        setImage(movie.getPic());
        titleLabel.setText(movie.getTitle());
        releaseYearLabel.setText("Release: " +Integer.toString(movie.getReleaseYear()));
        ratingLabel.setText("Rating: " +Double.toString(movie.getRating()));
        directorLabel.setText("Director:" +movie.getDirector());
        actorsLabel.setText("Actors: " +movie.getActors());
        imdbLabel.setText("IMDB id: "+imdb);
        plotTextArea.setText(movie.getPlot());

    }

    void setElementsDB(Movie movie){
        setImage(movie.getPic());
        titleLabel.setText(movie.getTitle());
        releaseYearLabel.setText("Release: " +Integer.toString(movie.getReleaseYear()));
        ratingLabel.setText("Rating: " +Double.toString(movie.getRating()));
        directorLabel.setText("Director:" +movie.getDirector());
        actorsLabel.setText("Actors: " +movie.getActors());
        imdbLabel.setText("IMDB id: "+movie.getImdb());
        plotTextArea.setText(movie.getPlot());
        reviewTextArea.setText(movie.getReview());
    }

    protected void showAlertDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}

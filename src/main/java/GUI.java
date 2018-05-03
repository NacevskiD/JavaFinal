import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GUI extends JFrame{
    private JTabbedPane searchTab;
    private JPanel mainPanel;
    private JPanel searchPanel;
    private JPanel myMoviesPanel;
    private JButton searchButton;
    private JTextField searchTextField;
    private JTable searchTable;
    private JButton myMoviesSearchButton;
    private JTextField myMoviesSearchTextField;
    private JTable myMoviesTable;
    DefaultTableModel searchListTableModel;
    DefaultListModel<Movie> searchListDefaultListModel;
    DefaultTableModel myMoviesTableModel;
    DefaultListModel<Movie> myMoviesDefaultListModel;
    DB db;
    API api;

    GUI(){
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        db = new DB();
        api = new API();

        searchListTableModel = new DefaultTableModel();

        searchListDefaultListModel = new DefaultListModel<>();
        // adding the columns
        searchListTableModel.addColumn("Title");
        searchListTableModel.addColumn("Year");
        searchListTableModel.addColumn("IMDB");

        myMoviesTableModel =  new DefaultTableModel();
        myMoviesDefaultListModel = new DefaultListModel<>();

        myMoviesTableModel.addColumn("Title");
        myMoviesTableModel.addColumn("Year");
        myMoviesTableModel.addColumn("IMDB");

        setMyMoviesData();

        myMoviesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myMoviesTable.setModel(myMoviesTableModel);

        searchTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchTable.setModel(searchListTableModel);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchTextField.getText();
                if (query.length() !=0){
                    ArrayList<Movie> movies = api.searchMovies(query);
                    setSearchData(movies);
                }
            }
        });
        searchTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int a = searchTable.getSelectedRow();
                String imdb = searchTable.getModel().getValueAt(a, 2).toString();
                InvidivdualGUI invidivdualGUI = new InvidivdualGUI(imdb);
                setMyMoviesData();
            }
        });
        myMoviesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int a = myMoviesTable.getSelectedRow();
                String imdb = myMoviesTable.getModel().getValueAt(a, 2).toString();
                Movie movie = db.getMovie(imdb);
                InvidivdualGUI invidivdualGUI = new InvidivdualGUI(movie);
            }
        });
    }

    void setMyMoviesData(){
        // loading data from db
        myMoviesTableModel.setRowCount(0);
        try {
            ArrayList<Movie> data = db.getAll();

            if (data.size() != 0) {

                for (Movie movie : data) {
                    myMoviesTableModel.addRow(new String[]{movie.getTitle(), Integer.toString(movie.getReleaseYear()), movie.getImdb()});
                }
            }
        }catch (NullPointerException npe){
            System.out.println("DB empty");
        }

    }

    void setSearchData(ArrayList<Movie> movies){
        searchListTableModel.setRowCount(0);

        if (movies.size() != 0) {

            for (Movie movie : movies) {
                searchListTableModel.addRow(new String[]{movie.getTitle(), Integer.toString(movie.getReleaseYear()), movie.getImdb()});
            }
        }
    }
}

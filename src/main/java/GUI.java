import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
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
    private JButton deleteButton;
    DefaultTableModel searchListTableModel;
    DefaultListModel<Movie> searchListDefaultListModel;
    DefaultTableModel myMoviesTableModel;
    DefaultListModel<Movie> myMoviesDefaultListModel;
    DB db;
    API api;

    GUI(){
        // setting the default settings of the gui
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,400);
        setResizable(false);
        db = new DB();
        api = new API();

        searchListTableModel = new DefaultTableModel();

        searchListDefaultListModel = new DefaultListModel<>();
        // adding the columns to both tables
        searchListTableModel.addColumn("Title");
        searchListTableModel.addColumn("Year");
        searchListTableModel.addColumn("IMDB");

        myMoviesTableModel =  new DefaultTableModel();
        myMoviesDefaultListModel = new DefaultListModel<>();

        myMoviesTableModel.addColumn("Title");
        myMoviesTableModel.addColumn("Year");
        myMoviesTableModel.addColumn("IMDB");



        myMoviesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myMoviesTable.setModel(myMoviesTableModel);

        searchTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchTable.setModel(searchListTableModel);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checking the the search text field is empty
                if (searchTextField.getText().length() != 0) {
                    // formating for a link
                    String query = searchTextField.getText().replace(" ", "+");
                    if (query.length() != 0) {
                        // querying the api for the movie name
                        ArrayList<Movie> movies = api.searchMovies(query);
                        //refreshing the list
                        setSearchData(movies);
                    }
                }else {
                    showAlertDialog("Please enter a search.");
                }
            }
        });
        searchTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // getting the selected row
                int a = searchTable.getSelectedRow();
                //getting the imdb from the table
                String imdb = searchTable.getModel().getValueAt(a, 2).toString();
                // starting the individual gui
                InvidivdualGUI invidivdualGUI = new InvidivdualGUI(imdb);
                // refreshing the table
                setMyMoviesData();
                myMoviesTableModel.fireTableDataChanged();
            }
        });
        myMoviesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                int a = myMoviesTable.getSelectedRow();
                // waiting for a double click to open an individual window
                if (a != -1 && e.getClickCount() ==2) {
                    String imdb = myMoviesTable.getModel().getValueAt(a, 2).toString();
                    Movie movie = db.getMovie(imdb);
                    InvidivdualGUI invidivdualGUI = new InvidivdualGUI(movie);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = myMoviesTable.getSelectedRow();
                String imdb = myMoviesTable.getModel().getValueAt(a, 2).toString();
                // deleting item from table
                db.deleteItem(imdb);
                setMyMoviesData();
                myMoviesTableModel.fireTableDataChanged();
                showAlertDialog("Movie deleted");

            }
        });


        searchTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // listener to refresh lists
                setMyMoviesData();
            }
        });
    }

    void setMyMoviesData(){
        // loading data from db
        myMoviesTableModel.setRowCount(0);
        try {
            DB db1 = new DB();
            ArrayList<Movie> data = db1.getAll();

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
    protected void showAlertDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

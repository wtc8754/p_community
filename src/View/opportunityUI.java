package View;

import Model.exceptions.*;
import javafx.application.Application;
import javafx.collections.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import Controller.*;

import java.io.IOException;
import java.util.List;

/**
 * an alternate UI for Project Opportunity
 *
 * @author Will Clifford (GitHub: wtc8754)
 */
public class opportunityUI extends Application {

    /**
     * this is the default value of the Record One CSV file to parse through
     */
    private static final String CSV = "resources/record_one.csv";

    /**
     * Main pane of the GUI. This is decorated with a Scene and displayed.
     */
    BorderPane mainpane = new BorderPane();

    /**
     * the list of amount discrepancies and its corresponding graphical counterpart
     */
    ObservableList<CommunityException> amountlist = FXCollections.observableArrayList();
    VBox left = new DiscrepancyList(amountlist);

    /**
     * the list of date discrepancies and its corresponding graphical counterpart
     */
    ObservableList<CommunityException> datelist = FXCollections.observableArrayList();
    VBox right = new DiscrepancyList(datelist);

    /**
     * the list of data discrepancies and its corresponding graphical counterpart
     */
    ObservableList<CommunityException> datalist = FXCollections.observableArrayList();
    VBox center = new DiscrepancyList(datalist);

    /**
     * console at bottom of window. Stored in the UI so that it can be written to.
     */
    console console = new console();

    /**
     * the controller that handles all the backend work. This controller works with the model to parse the data.
     */
    controller controller = new controller(CSV, console);

    /**
     * set up all buttons and place them in the right pane underneath the DiscrepancyList on that side
     */
    private void setRight() {
        Button PARSE = new Button("Parse");
        PARSE.setPrefSize(150, 20);
        PARSE.setOnAction(o -> parse());
        Button EXPORT = new Button("export as...");
        EXPORT.setPrefSize(150, 20);
        EXPORT.setOnAction(o -> export());
        right.getChildren().add(new HBox() {{
            getChildren().add(PARSE);
            getChildren().add(EXPORT);
        }});
        mainpane.setRight(right);
    }

    /**
     * validates the controller, then updates all the lists in the GUI
     */
    private void parse() {
        try {
            controller.validate();
            updateAmtList(controller.getAmts());
            updateDateList(controller.getDates());
            updateDataList(controller.getData());
        } catch (IOException e) {
            console.write("ERROR VALIDATING");
        }
    }

    /**
     * saves all data parsed into new CSVs
     */
    private void export() {
        saveAs("resources/amountdiscrepancies.csv", amountlist);
        saveAs("resources/datediscrepancies.csv", datelist);
        saveAs("resources/datadiscrepancies.csv", datalist);
    }

    /**
     * Update the list of items in the Date Discrepancies list
     *
     * @param s list of OpportunityExceptions
     */
    void updateDateList(List<CommunityException> s) {
        datelist.addAll(s);
    }

    /**
     * Update the list of items in the Amount Discrepancies list
     *
     * @param s list of OpportunityExceptions
     */
    void updateAmtList(List<CommunityException> s) {
        amountlist.addAll(s);
    }

    /**
     * Update the list of items in the Data Discrepancies list
     *
     * @param s list of OpportunityExceptions
     */
    void updateDataList(List<CommunityException> s) {
        datalist.addAll(s);
    }

    /**
     * Given a list of OpportunityExceptions, write a CSV file with that data.
     *
     * @param filename name of the file to save it as.
     * @param vals list of OpportunityExceptions.
     */
    void saveAs(String filename, List<CommunityException> vals) {
        if (controller.saveAsCSV(filename, vals))
            console.write("Save SUCCESSFUL!");
        else
            console.write("Save ERROR!");
    }

    /**
     * set up the GUI, display it
     *
     * @param primaryStage the main stage of the GUI
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        setRight();
        mainpane.setBottom(console);
        primaryStage.setTitle("Project Opportunity");
        primaryStage.setScene(new Scene(mainpane));
        primaryStage.show();
    }
}

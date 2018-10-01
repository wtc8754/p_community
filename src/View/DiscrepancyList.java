package View;

import Model.exceptions.CommunityException;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * this is a graphical list of discrepancies that are updated as discrepancies are found.
 * this class's purpose is to declutter the UI code a little bit.
 *
 * @author Will Clifford (GitHub: wtc8754)
 */
public class DiscrepancyList extends VBox {

    /**
     * make a new discrepancy list with a given list of discrepancies
     *
     * @param discr ObservableList of discrepancies
     */
    public DiscrepancyList(ObservableList<CommunityException> discr) {
        getChildren().add(new ListView<CommunityException>() {{ setItems(discr); }});
        setPrefSize(300, 300);
        setPadding(new Insets(15));
    }
}

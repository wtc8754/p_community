package View;

import javafx.scene.control.*;

/**
 * this Scrollpane is a pane of text, intended to be able to be continuously updated with new info as needed
 *
 * @author Will Clifford (GitHub: wtc8754)
 */
public class console extends ScrollPane {

    /**
     * store the text of the console as a stringbuilder
     */
    StringBuilder sb = new StringBuilder();

    /**
     * make a new console, set border, set preferred size
     */
    public console() {
        setStyle("-fx-border-color: black");
        setPrefSize(400, 50);
    }

    /**
     * write a message to the console
     *
     * @param msg message to write to the console
     */
    public void write(String msg) {
        setContent(new Label(sb.append(msg).append("\n").toString()));
        setVvalue(vmaxProperty().doubleValue());
    }
}

package Model.exceptions;

import Model.*;
import Model.enums.*;

/**
 * this is to be thrown when some required data is missing or unavailable.
 *
 * @author Will Clifford (wtc8754, Git: flycliff)
 */
public class MissingInfo extends CommunityException {

    /**
     * the record type of this exception
     */
    private RECORD type;

    /**
     * make a new MissingInfo exception
     *
     * @param entry entry who has a disparity
     */
    public MissingInfo(entry entry, RECORD type) {
        super("MISSING INFO: " + entry.toString());
        this.entry = entry;
        this.type = type;
    }

    /**
     * @return string representation of this exception
     */
    @Override
    public String toString() {
        return entry.getFirstname() + " " + entry.getLastname() + " missing" + type + " record";
    }
}

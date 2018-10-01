package Model.exceptions;

import Model.*;
import Model.enums.*;

/**
 * This is a superclass of all exceptions that can be thrown by Project Opportunity
 *
 * @author Will Clifford (GitHub: wtc8754)
 */
public class CommunityException extends Exception {

    /**
     * entry who threw this exception
     */
    entry entry;

    /**
     * term which has a disparity for this entry's disbursement
     */
    TERM term;

    /**
     * makes a new CommunityException
     *
     * @param s message to be displayed
     */
    public CommunityException(String s) {
        super(s);
    }

    /**
     * @return entry who threw this exception
     */
    public entry getEntry() {
        return entry;
    }

    /**
     * @return the term which has a disparity for this entry's disbursement
     */
    public TERM getTerm() {
        return term;
    }
}

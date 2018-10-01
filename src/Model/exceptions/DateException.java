package Model.exceptions;

import Model.*;
import Model.enums.RECORD;
import Model.enums.TERM;

import java.util.ArrayList;


/**
 * this is to be thrown when the date at which a entry's aid is disbursed doesn't match up between two or more records
 *
 * @author Will Clifford (GitHub: wtc8754)
 */
public class DateException extends CommunityException {

    /**
     * make a new DateException
     *
     * @param entry entry who has a disparity
     * @param term term of the disparity
     */
    public DateException(entry entry, TERM term) {
        super("DATE: " + entry.toString() + " : " + term);
        this.entry = entry;
        this.term = term;
    }

    /**
     * @return string representation of this Exception.
     */
    @Override
    public String toString() {
        ArrayList<disbursement> disb = new ArrayList<>();
        for (RECORD r : RECORD.values())
            for (TERM t : TERM.values())
                disb.add(entry.getDisb(r, t));

        StringBuilder sb = new StringBuilder();
        sb.append(entry.getFirstname()).append(" ").append(entry.getLastname()).append(" : \n");
        for (disbursement d : disb)
            if (d != null)
                sb.append("\t").append(d.getType()).append(": ").append(d.getDisbdate()).append("\n");

        return sb.toString();
    }
}

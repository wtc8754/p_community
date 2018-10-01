package Model.exceptions;

import Model.*;
import Model.enums.RECORD;
import Model.enums.TERM;

import java.util.ArrayList;

/**
 * This is thrown when two or more records report different amounts of aid disbursed
 *
 * @author Will Clifford (GitHub: wtc8754)
 */
public class AmtException extends CommunityException {

    /**
     * make a new AmtException
     *
     * @param entry entry who has a disparity
     * @param term term of the disparity
     */
    public AmtException(entry entry, TERM term) {
        super("AMOUNT: " + entry.toString() + " : " + term);
        this.entry = entry;
        this.term = term;
    }

    /**
     * @return string representation of this Exception
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
                sb.append("\t")
                        .append(d.getType()).append(": ")
                        .append(d.getDisbdate()).append(" : ")
                        .append("$").append(Math.round(d.getAmt())).append("\n");

        return sb.toString();
    }
}

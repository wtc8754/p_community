package Model;

import Model.enums.*;
import Model.exceptions.*;

import java.util.HashMap;

/**
 * this class represents a entry who has distributed to them for one or both academic terms
 *
 * @author Will Clifford (GitHub: wtc8754)
 */
public class entry {

    /**
     * SSN of this entry
     */
    private int SSN;

    /**
     * last name of this entry
     */
    private String lastname;

    /**
     * first name of this entry
     */
    private String firstname;

    /**
     * Dictionary of all the fall disbursements of this entry
     */
    private HashMap<RECORD, disbursement> falldisb = new HashMap<RECORD, disbursement>() {{
        put(RECORD.ONE, null);
        put(RECORD.TWO, null);
        put(RECORD.THR, null);
    }};

    /**
     * Dictionary of all the spring disbursements of this entry
     */
    private HashMap<RECORD, disbursement> springdisb = new HashMap<RECORD, disbursement>() {{
        put(RECORD.ONE, null);
        put(RECORD.TWO, null);
        put(RECORD.THR, null);
    }};

    /**
     * create new entry record
     *
     * @param SSN       Social Security Number of entry. If a entry doesn't have an SSN, their ID can be used
     * @param lastname  last name of this entry
     * @param firstname first name of this entry
     */
    public entry(int SSN, String lastname, String firstname) {
        this.SSN = SSN;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * @return entry SSN
     */
    public int getSSN() {
        return SSN;
    }

    /**
     * @return entry's last name
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @return entry's first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * get a given disbursement for a term and record type.
     *
     * @param r record type
     * @param t term
     * @return disbursement
     */
    public disbursement getDisb(RECORD r, TERM t) {
        if (t.equals(TERM.FALL))
            return falldisb.get(r);
        else
            return springdisb.get(r);
    }

    /**
     * given a disbursement, record, and term, set that disbursement if not yet already set
     *
     * @param d disbursement
     * @param r record type
     * @param t term
     */
    public void setFinAid(disbursement d, RECORD r, TERM t) {
        if (!isDisbursed(r, t))
            if (t.equals(TERM.FALL))
                falldisb.put(r, d);
            else
                springdisb.put(r, d);
    }

    /**
     * given a term and a record, returns true if that disbursement exists already
     *
     * @param r record type
     * @param t term
     * @return true iff the record for that term is not null, and also non-zero
     */
    public boolean isDisbursed(RECORD r, TERM t) {
        disbursement d = getDisb(r, t);
        if (d != null)
            return (d.getAmt() != 0);
        return false;
    }

    /**
     * For a given term, throw all potential errors with this entry's disbursements
     *
     * TODO - when HTMLparser is set up, uncomment out the first check;
     *
     * @param t term to check
     * @throws CommunityException the first error caught with that entry
     */
    public void checkTerm(TERM t) throws CommunityException {
        //check(RECORD.TWO, RECORD.ONE, t) && check(RECORD.TWO, RECORD.THR, t) && check(RECORD.ONE, RECORD.THR, t);
        check(RECORD.TWO, RECORD.THR, t);
    }

    /**
     * check two record types against each other for a given term. Throw the first error found
     *
     * @param one first record type
     * @param two second record type
     * @param t term to check
     * @throws CommunityException first error caught with those two records.
     */
    void check(RECORD one, RECORD two, TERM t) throws CommunityException {
        HashMap<RECORD, disbursement> dict;
        if (t.equals(TERM.FALL))
            dict = falldisb;
        else
            dict = springdisb;
        disbursement x = dict.get(one);
        disbursement y = dict.get(two);

        if (x == null && y != null) {
            throw new MissingInfo(this, one);
        } else if (x != null && y == null) {
            throw new MissingInfo(this, two);
        } else if (x != null) {
            if (!x.equals(y)) {
                if (x.equalsAmt(y)) {
                    throw new DateException(this, t);
                } else
                    throw new AmtException(this, t);
            }
        }
    }

    /**
     * @return string representation of this entry (i.e. "Firstname Lastname")
     */
    @Override
    public String toString() {
        return firstname + " " + lastname;
    }

    /**
     * @return hashcode of this entry, which is their SSN since that is unique
     */
    @Override
    public int hashCode() {
        return SSN;
    }
}

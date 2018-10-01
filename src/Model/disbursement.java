package Model;

import Model.enums.RECORD;

/**
 * this class represents a disbursement for one semester
 *
 * @author Will Clifford (wtc8754, Git: flycliff)
 */
public class disbursement {

    /**
     * entry whose disbursement this is
     */
    private entry entry;

    /**
     * distribution date of this disbursement
     */
    private date disbdate;

    /**
     * amount of the disbursement
     */
    private double amt;

    /**
     * term and database of this disbursement
     */
    private RECORD type;

    /**
     * make a new disbursement record
     *
     * @param entry entry whose disbursement this is
     * @param disb  distribution date of this disbursement
     * @param amt   amount of the disbursement
     * @param type  record type
     */
    public disbursement(entry entry, date disb, double amt, RECORD type) {
        this.entry = entry;
        this.disbdate = disb;
        this.amt = amt;
        this.type = type;
    }

    /**
     * @return entry whose disbursement this is
     */
    public entry getEntry() {
        return entry;
    }

    /**
     * @return distribution date of this disbursement
     */
    public date getDisbdate() {
        return disbdate;
    }

    /**
     * @return amount of the disbursement
     */
    public double getAmt() {
        return amt;
    }

    /**
     * @return term and database of this disbursement
     */
    public RECORD getType() {
        return type;
    }

    /**
     * Used to compare only the amount values of two disbursements
     *
     * @param oth other disbursement to compare to this
     * @return true iff the amounts of this and oth are the same
     */
    public boolean equalsAmt(disbursement oth) {
        return (this.getAmt() == oth.getAmt());
    }

    /**
     * CHECKS ONLY AMOUNT, DISTRIBUTION DATE, and ENTRY. TYPE IS NOT CHECKED.
     * this allows two records to be checked between the two record types and return true if the DATA in them is equal
     *
     * @param o other disbursement
     * @return true iff the entry, distribution date, and distribution amount are the same between this and "o"
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof disbursement) {
            disbursement oth = (disbursement) o;
            return (this.getAmt() == oth.getAmt())
                    && (this.getEntry().equals(oth.getEntry()))
                    && (this.getDisbdate().equals(oth.getDisbdate()));
        }
        return false;
    }

    /**
     * @return Hashcode for this disbursement, which is the entry's SSN multiplied by the hashcode of the distribution
     * date, all squared
     */
    @Override
    public int hashCode() {
        return entry.getSSN() * entry.getSSN() * disbdate.hashCode() * disbdate.hashCode();
    }
}

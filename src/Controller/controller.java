package Controller;

import View.*;
import Model.*;
import Model.enums.*;
import Model.exceptions.*;
import java.io.*;
import java.util.*;

/**
 * Controller for Project Opportunity's GUI.
 * Handles retrieving information and storing it in a meaningful way.
 *
 * @author Will Clifford (wtc8754, Git: flycliff)
 */
public class controller {

    /**
     * filename of the CSV that contains entry data
     */
    String csv;

    /**
     * console for text output to GUI
     */
    console output;

    /**
     * the map of entries found by CSVparser
     */
    HashMap<Integer, entry> entries;

    /**
     * all discrepancies
     */
    ArrayList<CommunityException> discrepancy = new ArrayList<>();

    /**
     * amount discrepancies
     */
    ArrayList<CommunityException> amts = new ArrayList<>();

    /**
     * date discrepancies
     */
    ArrayList<CommunityException> dates = new ArrayList<>();

    /**
     * data discrepancies
     */
    ArrayList<CommunityException> data = new ArrayList<>();

    /**
     * Makes a new controller object
     *
     * @param csv filename of the CSV that contains entry data
     * @param output console for text output to GUI
     */
    public controller(String csv, console output) {
        this.csv = csv;
        this.output = output;
    }

    /**
     * for all entries from the CSV parser, find discrepancies and store them.
     *
     * @throws IOException from CSVparser
     */
    public void validate() throws IOException {
        CSVparser cvp = new CSVparser(csv);
        entries = cvp.getEntries();
        output.write("CSV parsed. Finding discrepancies.");
        for (entry s : entries.values()) {
            for (TERM t: TERM.values()) {
                try {
                    s.checkTerm(t);
                } catch (MissingInfo e) {
                    output.write("Found data discrepancy: " + s);
                    data.add(e);
                    discrepancy.add(e);
                } catch (AmtException e) {
                    output.write("Found amount discrepancy : " + t + " : " + s);
                    amts.add(e);
                    discrepancy.add(e);
                } catch (DateException e) {
                    output.write("Found date discrepancy : " + t + " : " + s);
                    dates.add(e);
                    discrepancy.add(e);
                } catch (CommunityException e) {
                    output.write("Found UNKNOWN discrepancy!");
                    discrepancy.add(e);
                }
            }
        }
        output.write("\tFinished!");
    }

    /**
     * @return a list of all amount discrepancies
     */
    public List<CommunityException> getAmts() {
        return amts;
    }

    /**
     * @return a list of all date discrepancies
     */
    public List<CommunityException> getDates() {
        return dates;
    }

    /**
     * @return a list of all data discrepancies
     */
    public List<CommunityException> getData() {
        return data;
    }

    /**
     * @return a list of all discrepancies
     */
    public List<CommunityException> getDiscrepancies() {
        return discrepancy;
    }

    /**
     * Given the name of a destination file, Write a CSV with a given list of discrepancies
     *
     * @param savefile name of location to save the CSV
     * @param disc list of discrepancies
     * @return true iff the save was successful
     */
    public boolean saveAsCSV(String savefile, List<CommunityException> disc) {
        File f = new File(savefile);
        System.out.println();
        StringBuilder header = new StringBuilder();
        header.append("SSN,").append("Last,").append("First,")
                .append("THR Fall Disb Date,").append("THR Fall Disb Amount,")
                .append("THR Spring Disb Date,").append("THR spring Disb Amount,")
                .append("TWO Fall Disb Date,").append("TWO Fall Disb Amount,")
                .append("TWO Spring Disb Date,").append("TWO Spring Disb Amount,");

        try (PrintWriter writer = new PrintWriter(savefile, "UTF-8")) {
            writer.print(header.toString());
            for (CommunityException e : disc) {
                StringBuilder sb = new StringBuilder();
                entry entry = e.getEntry();

                ArrayList<disbursement> disbs = new ArrayList<disbursement>() {{
                    add(entry.getDisb(RECORD.THR, TERM.FALL));
                    add(entry.getDisb(RECORD.THR, TERM.SPRING));
                    add(entry.getDisb(RECORD.TWO, TERM.FALL));
                    add(entry.getDisb(RECORD.TWO, TERM.SPRING));
                }};

                sb.append("\n").append(entry.getSSN()).append(",").append(entry.getLastname()).append(",").append(entry.getFirstname()).append(",");

                for (disbursement d : disbs) {
                    if (d != null)
                        sb.append(d.getDisbdate()).append(",").append(d.getAmt()).append(",");
                    else
                        sb.append("NONE").append(",").append(0).append(",");
                }
                writer.println(sb.toString());
            }
            writer.flush();
            return true;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            return false;
        }
    }
}

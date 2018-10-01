package Controller;

import java.io.*;
import java.util.*;

import Model.*;
import Model.enums.*;

/**
 * This parses through a CSV file to get the distributions for each entry
 *
 * @author Will Clifford (wtc8754, Git: flycliff)
 */
public class CSVparser {

    /**
     * the output of this class, a HashMap of entries
     */
    HashMap<Integer, entry> entryMap = new HashMap<>();

    /**
     * These are the DEFAULT values for the rows of the CSV that handle their respective data
     * <p>
     * TODO - Make this resettable within the GUI
     */
    private final int SSN = 4;
    private final int LAST = 6;
    private final int FIRST = 7;
    private final int TOTALPKG = 14;
    private final int ONEDISB = 31;
    private final int ONEDISB1 = 30;
    private final int ONEAMT = 29;
    private final int TWODISB = 27;
    private final int TWOAMT = 26;

    /**
     * Creates a new CSV parser object, parses all entries in the CSV file, putting them into
     * a HashMap<Integer, entry> s.t. that entry's SSN is the key
     *
     * @param filename name of CSV to parse
     * @throws IOException from FileReader/BufferedReader
     */
    public CSVparser(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
        br.readLine();
        String line;
        String[] entry;
        line = br.readLine();
        while (line != null) {
            entry = line.split(",");
            int ssn = Integer.parseInt(entry[SSN]);

            entry entry1;

            if (entryMap.keySet().contains(ssn)) {
                entry1 = entryMap.get(ssn);
            } else {
                entry1 = new entry(ssn, entry[LAST], entry[FIRST]);
                entryMap.put(ssn, entry1);
            }

            date onedisb = parseD(entry[ONEDISB]);
            TERM oneterm = parseT(onedisb);
            double aone = Double.parseDouble(entry[ONEAMT]);

            date twodisb = parseD(entry[TWODISB]);
            TERM twoterm = parseT(twodisb);
            double atwo = Double.parseDouble(entry[TWOAMT]);

            entry1.setFinAid(new disbursement(entry1, onedisb, aone, RECORD.TWO), RECORD.TWO, oneterm);
            entry1.setFinAid(new disbursement(entry1, twodisb, atwo, RECORD.THR), RECORD.THR, twoterm);

            line = br.readLine();
        }
        br.close();
    }

    /**
     * Given a string, return a date
     *
     * @param d string representation of a date
     * @return model.date representation of a date
     */
    private date parseD(String d) {
        String[] s = d.replace(" ", "").split("/");
        Integer[] in = new Integer[s.length];

        if (s.length == 3) {
            for (int i = 0; i < s.length; i++) {
                in[i] = Integer.parseInt(s[i]);
            }
            return new date(in[0], in[1], in[2]);
        }
        return new date(0, 0, 0);       // Error case
    }

    /**
     * @param d date of disbursement
     * @return the term of that disbursement
     */
    private TERM parseT(date d) {
        if (d.getMonth() >= 6)
            return TERM.FALL;
        else
            return TERM.SPRING;
    }

    /**
     * @return all entries in the CSV file.
     */
    public HashMap<Integer, entry> getEntries() {
        return entryMap;
    }

}

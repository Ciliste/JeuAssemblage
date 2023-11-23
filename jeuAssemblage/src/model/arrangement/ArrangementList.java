package model.arrangement;


import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import model.PlayBoard;

import javax.swing.table.AbstractTableModel;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ArrangementList implements TableModel {

    private static List<Arrangement> arrangements;
    private static String[] headersName;

    static 
    {
        arrangements = ArrangementList.getArrangementList();
        headersName = new String[] {
            "X",
            "Y",
            "NB PIECE",
            "SEED",
            "USERNAME",
            "SCORE"
        };
    }
    
    private List<TableModelListener> listeners;
    
    public ArrangementList() {

        this.listeners = new ArrayList<TableModelListener>();
    }


    // tableModel Methods
    @Override
    public int getRowCount() {
        return ArrangementList.arrangements.size();
    }

    @Override
    public int getColumnCount() {

        return headersName.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return headersName[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (this.getRowCount() == 0) return Object.class;

        return this.getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {

        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object val = "No";
        Arrangement selected = arrangements.get(rowIndex);

        switch (columnIndex) {
            case 0 : val = selected.sizeX; break;
            case 1 : val = selected.sizeY; break;
            case 2 : val = selected.pieceCount; break;
            case 3 : val = selected.seed; break;
            case 4 : val = selected.username; break;
            case 5 : val = selected.score; break;
        }
        
        return val;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
        throw new IllegalStateException("Impossible");
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        this.listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        this.listeners.remove(l);
    }


    // Static methods
    public static List<Arrangement> getArrangements() {

        return ArrangementList.arrangements;
    }
    
    public static void addArrangement(Arrangement arrangement) {

        Arrangement copy = ArrangementList.exist(arrangement);
        if (copy != null) {

            if (copy.score < arrangement.score) {
                copy.score = arrangement.score;
                copy.username = arrangement.username;
            }

        } else {
            ArrangementList.arrangements.add(arrangement);
        }

        ArrangementList.registerArrangement();

    }

    public static void addArrangement(PlayBoard playboard, String name) {
        addArrangement(
            new Arrangement(
                playboard.getBoardWidth(),
                playboard.getBoardHeight(),
                playboard.getPiecesCount(),
                playboard.getSeed(),
                name,
                playboard.getArea()
            )
         );
    }
    
    private static Arrangement exist(Arrangement arrangement) {

        for (Arrangement a : ArrangementList.arrangements) {
            if (a.equals(arrangement))
                return a;
        }
        return null;
    }
    
    private static void registerArrangement() {
        File f = new File(ArrangementList.getPath() + "/maps.tetriste");
        if (!f.exists()) {
            try { f.createNewFile(); } 
            catch (Exception e) { e.printStackTrace(); }
        }

        String str = "";
        for (Arrangement a : ArrangementList.arrangements) {
            str += a.toString() + "\n";
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            writer.append(str);
            writer.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static ArrayList<Arrangement> getArrangementList() {

        ArrayList<Arrangement> retLst = new ArrayList<Arrangement>();
        File f = new File(ArrangementList.getPath() + "/maps.tetriste");
        if (!f.exists())
            return retLst;

        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNext()) {

                String[] vals = sc.nextLine().split(";");

                int sizeX       = Integer.parseInt(vals[0]);;
                int sizeY       = Integer.parseInt(vals[1]);
                int pieceCount  = Integer.parseInt(vals[2]);
                long seed       = Long.parseLong(vals[3]);
                String username = vals[4];
                int score = Integer.parseInt(vals[5]);

                retLst.add(new Arrangement(sizeX, sizeY, pieceCount, seed, username, score));
            }
            sc.close();
        } catch (Exception e) { e.printStackTrace(); }

        return retLst;
    }
    
    private static String getPath() {

        Path path = Paths.get("");
        return path.toAbsolutePath().toString();
    }
}

package model.arrangement;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ArrangementList {

    private static List<Arrangement> arrangements;

    static {

        ArrangementList.arrangements = ArrangementList.getArrangementList();
    }

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

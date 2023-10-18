package view.component;

import java.util.ArrayList;

import javax.swing.JTable;

import main.Controller;

public class JTableArrangement {
    
    public static String[] columnNames;

    static {
        columnNames = new String[] {
                "Taille X",
                "Taille Y",
                "Nombre Pièces",
                "Seed"
        };
    }
    
    public static Object[][] getData() {
        ArrayList<String> alStr = Controller.getInstance().getArrangement();
        Object[][] obj = new Object[alStr.size()][columnNames.length];
        for (int i = 0; i < alStr.size(); i++) {
            String[] arrayS = alStr.get(i).split(";");
            for (int j = 0; j < arrayS.length; j++) {
                obj[i][j] = arrayS[j];
            }
        }
        
        return obj;
    }

    public static JTable getJTable() {
        JTable jtable = new JTable(getData(), columnNames);
        jtable.setShowGrid(true);
        jtable.setFillsViewportHeight(true);
        return jtable;
    }
}

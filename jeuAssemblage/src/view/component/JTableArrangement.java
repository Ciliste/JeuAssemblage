package view.component;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import main.Controller;

public class JTableArrangement {

    public static JTable getJTable() {
        JTable jtable = new JTable(new MyTableModel());
        jtable.setShowGrid(true);
        jtable.setFillsViewportHeight(true);
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return jtable;
    }

    static class MyTableModel extends AbstractTableModel {

        public final String[] columnNames = new String[] {
                    "Taille X",
                    "Taille Y",
                    "Nombre Pièces",
                    "Seed"
            };

        public final Object[][] obj = getData();

        public Object[][] getData() {
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
        
        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return obj.length;
        }

        @Override
        public Object getValueAt(int arg0, int arg1) {
            return obj[arg0][arg1];
        }
        
    }
}

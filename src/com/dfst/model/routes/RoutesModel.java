package com.dfst.model.routes;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class RoutesModel extends DefaultTableModel {

    private ArrayList<Route> routes;
    private final String[] COLUMN_NAMES = {"Extension", "Path"};
    private final int COLUMNS_COUNT = COLUMN_NAMES.length;

    public RoutesModel() {
        routes = new ArrayList<>();
        setColumnIdentifiers(COLUMN_NAMES);
    }

    public void addNewRoute(String extension, String path) {
        routes.add(new Route(extension, path));
    }

    public void removeAt(int index) {
        if (index >= 0 && index < routes.size())
            routes.remove(index);
    }

    @Override
    public int getRowCount() {
        if (routes != null)
            return routes.size();
        else return 0;
    }

    @Override
    public int getColumnCount() {
        return COLUMNS_COUNT;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final Route route = routes.get(rowIndex);
        final int EXT_COL_INDEX = 0;
        final int PATH_COL_INDEX = 1;

        switch (columnIndex) {
            case EXT_COL_INDEX:
                return route.getExtension();
            case PATH_COL_INDEX:
                return route.getPath();
            default:
                return null;
        }
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
}
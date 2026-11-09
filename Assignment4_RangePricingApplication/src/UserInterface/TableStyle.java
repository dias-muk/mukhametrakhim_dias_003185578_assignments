/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package UserInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Presentation only. Gives any JTable in the application the same look: a flat
 * navy header, striped rows, roomy lines, no vertical grid and numbers aligned
 * to the right.
 *
 * Call {@code TableStyle.apply(myTable)} once, after initComponents(). It can
 * be called again after setModel(), and it does not touch the data.
 *
 * It also turns on column sorting. Because of that, a row the user clicked is
 * a view row, not a model row: read the selected row through the table
 * ({@code table.getValueAt(row, column)}), never straight off the table model,
 * or a sorted table hands back the wrong record.
 */
public class TableStyle {

    public static final Color HEADER_BG = new Color(31, 58, 95);
    public static final Color HEADER_FG = new Color(255, 255, 255);
    public static final Color ROW_BG = new Color(255, 255, 255);
    public static final Color ROW_ALT_BG = new Color(246, 248, 251);
    public static final Color LINE = new Color(230, 235, 241);
    public static final Color TEXT = new Color(30, 41, 59);
    public static final Color SELECTION_BG = new Color(220, 233, 251);

    private static final Font BODY = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font HEADER = new Font("SansSerif", Font.BOLD, 12);

    public static void apply(JTable table) {

        table.setFont(BODY);
        table.setForeground(TEXT);
        table.setBackground(ROW_BG);
        table.setRowHeight(30);
        table.setGridColor(LINE);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        installSorter(table);        
        table.setSelectionBackground(SELECTION_BG);
        table.setSelectionForeground(HEADER_BG);

        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 34));
        header.setDefaultRenderer(new HeaderRenderer());

        table.setDefaultRenderer(Object.class, new CellRenderer());

        if (table.getParent() != null && table.getParent().getParent() instanceof JScrollPane) {
            JScrollPane scroller = (JScrollPane) table.getParent().getParent();
            scroller.setBorder(BorderFactory.createLineBorder(LINE));
            scroller.getViewport().setBackground(ROW_BG);
        }
    }

    /** Flat coloured header with a little breathing room on the left. */
    private static class HeaderRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean selected, boolean focused, int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, selected, focused, row, column);
            label.setOpaque(true);
            label.setBackground(HEADER_BG);
            label.setForeground(HEADER_FG);
            label.setFont(HEADER);
            label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
            label.setHorizontalAlignment(isNumericColumn(table, column)
                    ? SwingConstants.RIGHT : SwingConstants.LEFT);
            label.setIcon(sortArrow(table, column));
            label.setHorizontalTextPosition(SwingConstants.LEADING);
            label.setIconTextGap(6);
            return label;
        }
    }
    
    private static final DecimalFormat MONEY = new DecimalFormat("#,##0.00");
    private static final DecimalFormat SCORE = new DecimalFormat("0.000");
    private static final DecimalFormat WHOLE = new DecimalFormat("#,##0");

    /**
     * Sorts on the value in the cell rather than on the text of it.
     *
     * A DefaultTableModel reports every column as Object, so JTable's own
     * sorter falls back to comparing toString(). That puts 9412 above 2127153,
     * because "9" sorts after "2". Comparing the numbers themselves fixes every
     * numeric column at once.
     */
    private static final Comparator<Object> VALUE_ORDER = (left, right) -> {
        if (left == null || right == null) {
            return left == null ? (right == null ? 0 : -1) : 1;
        }
        if (left instanceof Number && right instanceof Number) {
            return Double.compare(((Number) left).doubleValue(), ((Number) right).doubleValue());
        }
        return left.toString().compareToIgnoreCase(right.toString());
    };

    /**
     * Gives the table a sorter that uses VALUE_ORDER on every column, and puts
     * a fresh one in place whenever the model is replaced - which is what the
     * report screen does each time another report is run.
     */
    private static void installSorter(JTable table) {

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel()) {
            @Override
            public void toggleSortOrder(int modelColumn) {
                // Swing starts every column ascending. On a number column that
                // puts the smallest values on top, which reads as "nothing
                // happened" when the bottom of the list is full of zeros, so
                // the first click here sorts the biggest first instead.
                if (startsDescending(table, modelColumn) && notSortedYet(this, modelColumn)) {
                    setSortKeys(List.of(new RowSorter.SortKey(modelColumn, SortOrder.DESCENDING)));
                    return;
                }
                super.toggleSortOrder(modelColumn);
            }
        };
        for (int column = 0; column < table.getModel().getColumnCount(); column++) {
            sorter.setComparator(column, VALUE_ORDER);
        }
        table.setRowSorter(sorter);

        if (table.getClientProperty("TableStyle.sorterWatched") == null) {
            table.putClientProperty("TableStyle.sorterWatched", Boolean.TRUE);
            table.addPropertyChangeListener("model", event -> installSorter(table));
        }
    }

    /** True when this model column holds numbers, so it should open biggest-first. */
    private static boolean startsDescending(JTable table, int modelColumn) {
        TableModel model = table.getModel();
        return model.getRowCount() > 0 && model.getValueAt(0, modelColumn) instanceof Number;
    }

    /** True when the table is not currently sorted on this column. */
    private static boolean notSortedYet(RowSorter<?> sorter, int modelColumn) {
        for (RowSorter.SortKey key : sorter.getSortKeys()) {
            if (key.getColumn() == modelColumn && key.getSortOrder() != SortOrder.UNSORTED) {
                return false;
            }
        }
        return true;
    }

    /** The arrow drawn in the header of the column the table is sorted on. */
    private static Icon sortArrow(JTable table, int viewColumn) {
        RowSorter<?> sorter = table.getRowSorter();
        if (sorter == null || viewColumn < 0) {
            return null;
        }
        int modelColumn = table.convertColumnIndexToModel(viewColumn);
        for (RowSorter.SortKey key : sorter.getSortKeys()) {
            if (key.getColumn() == modelColumn && key.getSortOrder() != SortOrder.UNSORTED) {
                return new SortArrow(key.getSortOrder() == SortOrder.ASCENDING);
            }
        }
        return null;
    }

    /** A small triangle, drawn in the header colour so it shows on the navy band. */
    private static class SortArrow implements Icon {

        private final boolean pointingUp;

        SortArrow(boolean pointingUp) {
            this.pointingUp = pointingUp;
        }

        @Override
        public int getIconWidth() {
            return 8;
        }

        @Override
        public int getIconHeight() {
            return 6;
        }

        @Override
        public void paintIcon(Component component, Graphics graphics, int x, int y) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(HEADER_FG);
            int[] xs = {x, x + 8, x + 4};
            int[] ys = pointingUp ? new int[]{y + 6, y + 6, y} : new int[]{y, y, y + 6};
            g.fillPolygon(xs, ys, 3);
            g.dispose();
        }
    }

    /** Shows a number the way a person reads it, without making it a String in the model. */
    private static String format(Object value) {
        if (value instanceof Double || value instanceof Float) {
            double number = ((Number) value).doubleValue();
            // scores run from 0 to 1 and need the third decimal; money does not
            return Math.abs(number) < 1 ? SCORE.format(number) : MONEY.format(number);
        }
        if (value instanceof Number) {
            return WHOLE.format(((Number) value).doubleValue());
        }
        return value == null ? "" : value.toString();
    }


    /** True when the first row of this column holds a number. */
    private static boolean isNumericColumn(JTable table, int column) {
        if (table.getRowCount() == 0 || column < 0 || column >= table.getColumnCount()) {
            return false;
        }
        return table.getValueAt(0, column) instanceof Number;
    }

    /** Striped rows, padded cells, numbers to the right. */
    private static class CellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean selected, boolean focused, int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, selected, focused, row, column);
            label.setText(format(value));

            if (!selected) {
                label.setBackground(row % 2 == 0 ? ROW_BG : ROW_ALT_BG);
                label.setForeground(TEXT);
            }
            label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
            label.setHorizontalAlignment(value instanceof Number
                    ? SwingConstants.RIGHT : SwingConstants.LEFT);
            return label;
        }
    }

    private TableStyle() {
    }
}

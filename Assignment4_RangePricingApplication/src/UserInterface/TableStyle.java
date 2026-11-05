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
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

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
        table.setAutoCreateRowSorter(true);
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
            return label;
        }
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

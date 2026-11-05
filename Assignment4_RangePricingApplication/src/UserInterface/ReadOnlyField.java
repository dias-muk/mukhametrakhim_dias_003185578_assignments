/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package UserInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.Painter;
import javax.swing.UIDefaults;

/**
 * Presentation only. Makes a field that merely displays a calculated value
 * look different from one you are meant to type in.
 *
 * Nimbus paints its own text field background and ignores setBackground, so
 * the tint has to be installed as a per-component Nimbus override.
 */
public class ReadOnlyField {

    public static final Color BACKGROUND = new Color(238, 241, 245);
    public static final Color BORDER = new Color(214, 221, 230);

    /**
     * Styles every text field inside the panel that is already marked
     * non-editable. Call it once at the end of the constructor.
     */
    public static void applyAll(Container container) {
        for (Component child : container.getComponents()) {
            if (child instanceof JTextField) {
                JTextField field = (JTextField) child;
                if (!field.isEditable()) {
                    apply(field);
                }
            } else if (child instanceof Container) {
                applyAll((Container) child);
            }
        }
    }

    public static void apply(JTextField field) {

        field.setEditable(false);
        field.setFocusable(false);

        // The background painter fills; the border painter must only outline,
        // because Swing paints the border after the text - filling there would
        // paint straight over the value.
        Painter<JComponent> fill = (graphics, component, width, height) -> {
            graphics.setColor(BACKGROUND);
            graphics.fillRect(0, 0, width, height);
        };
        Painter<JComponent> outline = (graphics, component, width, height) -> {
            graphics.setColor(BORDER);
            graphics.drawRect(0, 0, width - 1, height - 1);
        };

        UIDefaults overrides = new UIDefaults();
        overrides.put("TextField[Enabled].backgroundPainter", fill);
        overrides.put("TextField[Enabled].borderPainter", outline);
        overrides.put("TextField.background", BACKGROUND);

        field.putClientProperty("Nimbus.Overrides", overrides);
        field.putClientProperty("Nimbus.Overrides.InheritDefaults", Boolean.FALSE);
        field.setBackground(BACKGROUND);
    }

    private ReadOnlyField() {
    }
}

/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 4 - Range Pricing Application
 * Dias Mukhametrakhim, NUID 003185578
 */
package TheBusiness;

import UserInterface.Main.PricingMainFrame;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Entry point of the Range Pricing Application. It sets the look and feel and
 * opens the main window on the Swing event dispatch thread. The business model
 * itself is built by the main frame when it starts.
 */
public class RangePricingApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            // Fall back to the default look and feel.
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PricingMainFrame().setVisible(true);
            }
        });
    }
}

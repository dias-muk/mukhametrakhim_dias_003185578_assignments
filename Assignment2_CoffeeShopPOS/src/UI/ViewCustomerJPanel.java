/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package UI;

import Model.Business;
import Model.Customer;
import Model.Order;
import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * The complete profile of one customer together with their order history.
 *
 * <p>The customer fields open read-only; Update unlocks them and Save
 * validates and writes them back. Selecting an order in the history table
 * and pressing "View / Edit Order" pushes a {@link ViewOrderJPanel}.</p>
 *
 * @author Dias Mukhametrakhim
 */
public class ViewCustomerJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private Business business;
    private Customer customer;

    /**
     * Builds the profile of the given customer in view mode.
     *
     * @param userProcessContainer the CardLayout container of MainJFrame
     * @param business the coffee shop the customer belongs to
     * @param customer the customer to show and edit
     */
    public ViewCustomerJPanel(JPanel userProcessContainer, Business business, Customer customer) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        this.customer = customer;

        tblOrderHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblOrderHistory.getSelectionModel().addListSelectionListener(e -> updateButtons());

        // Reload the history when coming back from ViewOrderJPanel.
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                populateOrderTable();
            }
        });

        refreshFields();
        setViewMode();
        populateOrderTable();
    }
    
        private void refreshFields() {
        fieldCustomerId.setText(String.valueOf(customer.getCustomerId()));
        fieldFirstName.setText(customer.getFirstName());
        fieldLastName.setText(customer.getLastName());
        fieldContact.setText(String.valueOf(customer.getContact()));
    }

    private void setViewMode() {
        setFieldsEnabled(false);
        btnSave.setEnabled(false);
        btnUpdate.setEnabled(true);
    }

    private void setEditMode() {
        setFieldsEnabled(true);
        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
    }

    private void setFieldsEnabled(boolean enabled) {
        fieldCustomerId.setEnabled(enabled);
        fieldFirstName.setEnabled(enabled);
        fieldLastName.setEnabled(enabled);
        fieldContact.setEnabled(enabled);
    }
    
    private void populateOrderTable() {
        DefaultTableModel model = (DefaultTableModel) tblOrderHistory.getModel();
        model.setRowCount(0);

        for (Order o : business.getOrderDirectory().findByCustomer(customer)) {
            Object[] row = new Object[7];
            row[0] = o;                     // toString() shows the order ID
            row[1] = o.getFormattedDateTime();
            row[2] = o.getProduct();
            row[3] = o.getQuantity();
            row[4] = String.format("%.2f", o.getTotal());
            row[5] = o.getStatus();
            row[6] = o.isPaid() ? "Yes" : "No";
            model.addRow(row);
        }
        updateButtons();
    }

    private void updateButtons() {
        btnViewOrder.setEnabled(tblOrderHistory.getSelectedRow() >= 0);
    }

    /**
     * Checks the edited customer fields and shows an error dialog for the
     * first problem found.
     *
     * @return true when the changes can be saved
     */
    private boolean validateInput() {
        String idText = fieldCustomerId.getText().trim();
        String firstName = fieldFirstName.getText().trim();
        String lastName = fieldLastName.getText().trim();
        String contactText = fieldContact.getText().trim();

        if (!require(idText, "Customer ID")) return false;
        if (!require(firstName, "First Name")) return false;
        if (!require(lastName, "Last Name")) return false;
        if (!require(contactText, "Contact")) return false;

        if (!Validator.isPositiveInt(idText)) {
            showError("Customer ID must be a whole number greater than 0.");
            return false;
        }
        Customer owner = business.getCustomerDirectory().findById(Integer.parseInt(idText));
        if (owner != null && owner != customer) {
            showError("Customer ID " + idText + " is already used by " + owner.getFullName() + ".");
            return false;
        }
        if (!Validator.isValidName(firstName) || !Validator.isValidName(lastName)) {
            showError("First and last name may contain only letters, spaces, ' and -.");
            return false;
        }
        if (!Validator.isValidContact(contactText)) {
            showError("Contact must be exactly 10 digits and cannot start with 0.");
            return false;
        }
        return true;
    }

    private boolean require(String value, String label) {
        if (value.isEmpty()) {
            showError(label + " is required.");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblContact = new javax.swing.JLabel();
        lblFirstName = new javax.swing.JLabel();
        lblLastName = new javax.swing.JLabel();
        lblCustomerId = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        fieldCustomerId = new javax.swing.JTextField();
        fieldFirstName = new javax.swing.JTextField();
        fieldLastName = new javax.swing.JTextField();
        fieldContact = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrderHistory = new javax.swing.JTable();
        lblCustomer = new javax.swing.JLabel();
        btnViewOrder = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 241, 235));

        lblTitle.setFont(new java.awt.Font("American Typewriter", 1, 24)); // NOI18N
        lblTitle.setText("Customer Profile");

        lblContact.setText("Contact");

        lblFirstName.setText("First Name");

        lblLastName.setText("Last Name");

        lblCustomerId.setText("Customer ID");

        btnUpdate.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        tblOrderHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Order ID", "Date/Time", "Product", "Qty", "Total", "Status", "Paid"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrderHistory.setFillsViewportHeight(true);
        tblOrderHistory.setRowHeight(26);
        jScrollPane1.setViewportView(tblOrderHistory);
        if (tblOrderHistory.getColumnModel().getColumnCount() > 0) {
            tblOrderHistory.getColumnModel().getColumn(0).setResizable(false);
            tblOrderHistory.getColumnModel().getColumn(1).setResizable(false);
            tblOrderHistory.getColumnModel().getColumn(2).setResizable(false);
            tblOrderHistory.getColumnModel().getColumn(3).setResizable(false);
            tblOrderHistory.getColumnModel().getColumn(4).setResizable(false);
            tblOrderHistory.getColumnModel().getColumn(5).setResizable(false);
            tblOrderHistory.getColumnModel().getColumn(6).setResizable(false);
        }

        lblCustomer.setFont(new java.awt.Font("Andale Mono", 1, 16)); // NOI18N
        lblCustomer.setText("Order History");

        btnViewOrder.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnViewOrder.setText("View / Edit Order");
        btnViewOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewOrderActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSave)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCustomerId)
                                    .addComponent(lblFirstName))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(fieldCustomerId, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(fieldFirstName))))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblLastName)
                                .addComponent(lblContact))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fieldLastName)
                            .addComponent(fieldContact, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(220, 220, 220)
                        .addComponent(lblTitle)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCustomer)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnBack)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnViewOrder))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 824, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSave, btnUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLastName)
                            .addComponent(fieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContact)
                            .addComponent(fieldContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCustomerId)
                            .addComponent(fieldCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFirstName)
                            .addComponent(fieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnUpdate))
                .addGap(38, 38, 38)
                .addComponent(lblCustomer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnViewOrder)
                    .addComponent(btnBack))
                .addGap(36, 36, 36))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnSave, btnUpdate});

    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        setEditMode();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (!validateInput()) {
            return;
        }
        customer.setCustomerId(Integer.parseInt(fieldCustomerId.getText().trim()));
        customer.setFirstName(fieldFirstName.getText().trim());
        customer.setLastName(fieldLastName.getText().trim());
        customer.setContact(Long.parseLong(fieldContact.getText().trim()));

        refreshFields();
        setViewMode();
        JOptionPane.showMessageDialog(this, customer.getFullName() + " was updated.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnViewOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewOrderActionPerformed
        int selectedRow = tblOrderHistory.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select an order in the history first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Order order = (Order) tblOrderHistory.getValueAt(selectedRow, 0);

        ViewOrderJPanel panel = new ViewOrderJPanel(userProcessContainer, business, order);
        userProcessContainer.add("ViewOrderJPanel", panel);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.next(userProcessContainer);
    }//GEN-LAST:event_btnViewOrderActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnViewOrder;
    private javax.swing.JTextField fieldContact;
    private javax.swing.JTextField fieldCustomerId;
    private javax.swing.JTextField fieldFirstName;
    private javax.swing.JTextField fieldLastName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblContact;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblCustomerId;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblOrderHistory;
    // End of variables declaration//GEN-END:variables
}

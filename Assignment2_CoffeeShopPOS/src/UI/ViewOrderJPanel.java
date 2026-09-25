/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package UI;

import Model.Business;
import Model.Order;
import Model.OrderDirectory;
import Model.OrderStatus;
import Model.OrderType;
import Model.PaymentMethod;
import Model.Product;
import java.awt.CardLayout;
import java.time.LocalDateTime;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Shows one order and lets the manager update any of its attributes.
 *
 * <p>The panel opens read-only. Update unlocks the fields, Save validates
 * and writes them back to the order. It is pushed onto the card stack both
 * from {@link ListOrdersJPanel} and from {@link ViewCustomerJPanel}, and Back
 * simply pops it off again.</p>
 *
 * @author Dias Mukhametrakhim
 */

public class ViewOrderJPanel extends javax.swing.JPanel {

    private JPanel userProcessContainer;
    private Business business;
    private Order order;

    /**
     * Builds the panel showing the given order in view mode.
     *
     * @param userProcessContainer the CardLayout container of MainJFrame
     * @param business the coffee shop the order belongs to
     * @param order the order to show and edit
     */
    public ViewOrderJPanel(JPanel userProcessContainer, Business business, Order order) {
        initComponents();
        this.userProcessContainer = userProcessContainer;
        this.business = business;
        this.order = order;

        cmbType.setModel(new DefaultComboBoxModel<>(OrderType.values()));
        cmbPayment.setModel(new DefaultComboBoxModel<>(PaymentMethod.values()));
        cmbStatus.setModel(new DefaultComboBoxModel<>(OrderStatus.values()));
        cmbProduct.setModel(new DefaultComboBoxModel<>(
                business.getProductCatalog().getCatalog().toArray(new Product[0])));

        refreshFields();
        setViewMode();
    }
    
     /** Copies the order's current values into the fields. */
    private void refreshFields() {
        lblCustomer.setText("Customer: " + order.getCustomer().getFullName()
                + " (ID " + order.getCustomer().getCustomerId() + ")");
        fieldOrderId.setText(String.valueOf(order.getOrderId()));
        fieldDateTime.setText(order.getFormattedDateTime());
        cmbType.setSelectedItem(order.getType());
        cmbPayment.setSelectedItem(order.getPayment());
        cmbStatus.setSelectedItem(order.getStatus());
        cmbProduct.setSelectedItem(order.getProduct());
        fieldQuantity.setText(String.valueOf(order.getQuantity()));
        chkBoxPaid.setSelected(order.isPaid());
        lblTotal.setText("Total: " + order.getQuantity() + " x $"
                + String.format("%.2f", order.getProduct().getPrice())
                + " = $" + String.format("%.2f", order.getTotal()));
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
        fieldOrderId.setEnabled(enabled);
        fieldDateTime.setEnabled(enabled);
        cmbType.setEnabled(enabled);
        cmbPayment.setEnabled(enabled);
        cmbStatus.setEnabled(enabled);
        cmbProduct.setEnabled(enabled);
        fieldQuantity.setEnabled(enabled);
        chkBoxPaid.setEnabled(enabled);
    }

    /**
     * Checks the edited values and shows an error dialog for the first
     * problem found.
     *
     * @return true when the changes can be saved
     */
    private boolean validateInput() {
        OrderDirectory orderDirectory = business.getOrderDirectory();
        String orderIdText = fieldOrderId.getText().trim();
        String dateTimeText = fieldDateTime.getText().trim();
        String quantityText = fieldQuantity.getText().trim();

        if (!require(orderIdText, "Order ID")) return false;
        if (!require(dateTimeText, "Date/Time")) return false;
        if (!require(quantityText, "Quantity")) return false;

        if (!Validator.isPositiveInt(orderIdText)) {
            showError("Order ID must be a whole number greater than 0.");
            return false;
        }
        Order owner = orderDirectory.findById(Integer.parseInt(orderIdText));
        if (owner != null && owner != order) {
            showError("Order ID " + orderIdText + " is already used by another order.");
            return false;
        }
        if (!Validator.isValidDateTime(dateTimeText)) {
            showError("Date/Time must look like 2026-09-23 14:05 (yyyy-MM-dd HH:mm).");
            return false;
        }
        if (!Validator.isPositiveInt(quantityText)) {
            showError("Quantity must be a whole number greater than 0.");
            return false;
        }
        // Stock is only checked when the product or the quantity changes.
        Product product = (Product) cmbProduct.getSelectedItem();
        int quantity = Integer.parseInt(quantityText);
        boolean itemChanged = product != order.getProduct() || quantity != order.getQuantity();
        if (itemChanged && quantity > product.getNumber()) {
            showError("Only " + product.getNumber() + " x " + product.getName() + " available.");
            return false;
        }
        OrderStatus newStatus = (OrderStatus) cmbStatus.getSelectedItem();
        if (newStatus == OrderStatus.COMPLETED && !chkBoxPaid.isSelected()) {
            showError("A completed order must be marked as paid.");
            return false;
        }
        // Re-opening a completed order must not give the customer two open orders.
        if (order.getStatus() == OrderStatus.COMPLETED && newStatus != OrderStatus.COMPLETED
                && orderDirectory.hasOpenOrder(order.getCustomer())) {
            showError(order.getCustomer().getFullName() + " already has another open order.\n"
                    + "A customer can have only one open order at a time.");
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
        lblCustomer = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        lblOrderId = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();
        lblOrderType = new javax.swing.JLabel();
        lblPayment = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        fieldOrderId = new javax.swing.JTextField();
        fieldDateTime = new javax.swing.JTextField();
        fieldQuantity = new javax.swing.JTextField();
        cmbType = new javax.swing.JComboBox<>();
        cmbPayment = new javax.swing.JComboBox<>();
        cmbStatus = new javax.swing.JComboBox<>();
        chkBoxPaid = new javax.swing.JCheckBox();
        lblProduct = new javax.swing.JLabel();
        cmbProduct = new javax.swing.JComboBox<>();
        btnBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 241, 235));

        lblTitle.setFont(new java.awt.Font("American Typewriter", 1, 24)); // NOI18N
        lblTitle.setText("Order Details");

        lblCustomer.setFont(new java.awt.Font("Andale Mono", 1, 16)); // NOI18N
        lblCustomer.setText("Customer");

        lblTotal.setFont(new java.awt.Font("Andale Mono", 1, 16)); // NOI18N
        lblTotal.setText("Total");

        btnSave.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        lblOrderId.setText("Order ID");

        lblDateTime.setText("Date/Time");

        lblOrderType.setText("Order Type");

        lblPayment.setText("Payment");

        lblStatus.setText("Status");

        lblQuantity.setText("Quantity");

        chkBoxPaid.setText("Paid");

        lblProduct.setText("Product");

        btnBack.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnBack.setText("<<Back");
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
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblProduct, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblPayment, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblOrderType)
                                    .addGap(150, 150, 150)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblQuantity)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addComponent(fieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblOrderId)
                                    .addComponent(lblDateTime))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(chkBoxPaid)
                        .addGap(518, 518, 518))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTitle)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(67, 67, 67)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBack)
                            .addComponent(lblCustomer)
                            .addComponent(lblTotal))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(296, 296, 296)
                    .addComponent(btnUpdate)
                    .addGap(92, 92, 92)
                    .addComponent(btnSave)
                    .addContainerGap(335, Short.MAX_VALUE)))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblDateTime, lblOrderId, lblOrderType, lblPayment, lblProduct, lblQuantity, lblStatus});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbPayment, cmbProduct, cmbStatus, cmbType, fieldDateTime, fieldOrderId, fieldQuantity});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnSave, btnUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblTitle)
                .addGap(45, 45, 45)
                .addComponent(lblCustomer)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderId)
                    .addComponent(fieldOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDateTime)
                    .addComponent(fieldDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderType)
                    .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPayment)
                    .addComponent(cmbPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProduct)
                    .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuantity)
                    .addComponent(fieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkBoxPaid))
                .addGap(46, 46, 46)
                .addComponent(lblTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addGap(31, 31, 31))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(612, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnUpdate)
                        .addComponent(btnSave))
                    .addGap(30, 30, 30)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (!validateInput()) {
            return;
        }
        order.setOrderId(Integer.parseInt(fieldOrderId.getText().trim()));
        order.setDateTime(LocalDateTime.parse(fieldDateTime.getText().trim(), Order.DATE_TIME_FORMAT));
        order.setType((OrderType) cmbType.getSelectedItem());
        order.setPayment((PaymentMethod) cmbPayment.getSelectedItem());
        order.setStatus((OrderStatus) cmbStatus.getSelectedItem());
        order.setProduct((Product) cmbProduct.getSelectedItem());
        order.setQuantity(Integer.parseInt(fieldQuantity.getText().trim()));
        order.setPaid(chkBoxPaid.isSelected());

        refreshFields();
        setViewMode();
        JOptionPane.showMessageDialog(this, "Order #" + order.getOrderId() + " was updated.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        setEditMode();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        userProcessContainer.remove(this);
        CardLayout layout = (CardLayout) userProcessContainer.getLayout();
        layout.previous(userProcessContainer);
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JCheckBox chkBoxPaid;
    private javax.swing.JComboBox<PaymentMethod> cmbPayment;
    private javax.swing.JComboBox<Product> cmbProduct;
    private javax.swing.JComboBox<OrderStatus> cmbStatus;
    private javax.swing.JComboBox<OrderType> cmbType;
    private javax.swing.JTextField fieldDateTime;
    private javax.swing.JTextField fieldOrderId;
    private javax.swing.JTextField fieldQuantity;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblOrderId;
    private javax.swing.JLabel lblOrderType;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JLabel lblProduct;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables
}

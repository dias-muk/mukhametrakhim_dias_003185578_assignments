/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package UI;

import Model.Business;
import Model.Customer;
import Model.CustomerDirectory;
import Model.Order;
import Model.OrderDirectory;
import Model.OrderStatus;
import Model.OrderType;
import Model.PaymentMethod;
import Model.Product;
import java.time.LocalDateTime;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * Registers a customer and places their order with one chosen product.
 *
 * <p>By default the form registers a brand-new customer. Typing an existing
 * Customer ID and pressing "Find Existing" loads that customer instead, so a
 * returning customer can place a new order once their previous one is
 * completed (one open order per customer at a time).</p>
 *
 * <p>All fields are validated before anything is created, so a failed check
 * never leaves a half-created customer behind.</p>
 *
 * @author Dias Mukhametrakhim
 */

public class AddCustomerOrderJPanel extends javax.swing.JPanel {

    private CustomerDirectory customerDirectory;
    private OrderDirectory orderDirectory;
    /** Set by "Find Existing"; null while the form describes a new customer. */
    private Customer existingCustomer;

    /**
     * Builds the form and fills the combo boxes.
     *
     * @param business the coffee shop the customer and order are added to
     */
    
    public AddCustomerOrderJPanel(Business business) {
        initComponents();
        this.customerDirectory = business.getCustomerDirectory();
        this.orderDirectory = business.getOrderDirectory();

        // Enum values fill these combos, so an invalid type, payment method
        // or status simply cannot be chosen.
        cmbType.setModel(new DefaultComboBoxModel<>(OrderType.values()));
        cmbPayment.setModel(new DefaultComboBoxModel<>(PaymentMethod.values()));
        cmbStatus.setModel(new DefaultComboBoxModel<>(OrderStatus.values()));

        // The combo holds the Product objects themselves; toString() shows the
        // name and getSelectedItem() hands back the real product.
        cmbProduct.setModel(new DefaultComboBoxModel<>(
                business.getProductCatalog().getCatalog().toArray(new Product[0])));
        cmbProduct.addActionListener(e -> showProductDetails());

        // Product details are shown for information only.
        fieldProdId.setEditable(false);
        fieldProdCategory.setEditable(false);
        fieldProdPrice.setEditable(false);
        fieldProdNumber.setEditable(false);
        fieldProdPrepTime.setEditable(false);

        resetForm();
        
    }
    
    /** Empties the form for the next customer and suggests the next order ID. */
    
    private void resetForm() {
        existingCustomer = null;
        setCustomerFieldsEditable(true);
        fieldCustomerId.setText("");
        fieldFirstName.setText("");
        fieldLastName.setText("");
        fieldContact.setText("");

        fieldOrderId.setText(String.valueOf(orderDirectory.nextOrderId()));
        fieldDateTime.setText(LocalDateTime.now().format(Order.DATE_TIME_FORMAT));
        cmbType.setSelectedIndex(0);
        cmbPayment.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
        fieldQuantity.setText("1");
        chkBoxPaid.setSelected(false);
        if (cmbProduct.getItemCount() > 0) {
            cmbProduct.setSelectedIndex(0);
        }
        showProductDetails();
    }

    private void setCustomerFieldsEditable(boolean editable) {
        fieldCustomerId.setEditable(editable);
        fieldFirstName.setEditable(editable);
        fieldLastName.setEditable(editable);
        fieldContact.setEditable(editable);
        btnFind.setEnabled(editable);
    }

    /** Copies the chosen product's attributes into the read-only fields. */
    private void showProductDetails() {
        Product p = (Product) cmbProduct.getSelectedItem();
        if (p == null) {
            fieldProdId.setText("");
            fieldProdCategory.setText("");
            fieldProdPrice.setText("");
            fieldProdNumber.setText("");
            fieldProdPrepTime.setText("");
            return;
        }
        fieldProdId.setText(String.valueOf(p.getProductId()));
        fieldProdCategory.setText(p.getCategory());
        fieldProdPrice.setText(String.format("%.2f", p.getPrice()));
        fieldProdNumber.setText(String.valueOf(p.getNumber()));
        fieldProdPrepTime.setText(String.valueOf(p.getPrepTime()));
    }

    /** Checks the four customer fields when a new customer is registered. */
    private boolean validateNewCustomer() {
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
        if (customerDirectory.isIdTaken(Integer.parseInt(idText))) {
            showError("Customer ID " + idText + " already exists.\n"
                    + "Press \"Find Existing\" to place an order for that customer.");
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

    /** Checks the order fields and the chosen product. */
    private boolean validateOrder() {
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
        if (orderDirectory.findById(Integer.parseInt(orderIdText)) != null) {
            showError("Order ID " + orderIdText + " already exists.");
            return false;
        }
        if (!Validator.isValidDateTime(dateTimeText)) {
            showError("Date/Time must look like 2026-09-23 14:05 (yyyy-MM-dd HH:mm).");
            return false;
        }
        Product product = (Product) cmbProduct.getSelectedItem();
        if (product == null) {
            showError("There are no products yet. Add one in Manage Products first.");
            return false;
        }
        if (!Validator.isPositiveInt(quantityText)) {
            showError("Quantity must be a whole number greater than 0.");
            return false;
        }
        if (Integer.parseInt(quantityText) > product.getNumber()) {
            showError("Only " + product.getNumber() + " x " + product.getName() + " available.");
            return false;
        }
        // boolean rule: an order cannot be completed before it is paid.
        if (cmbStatus.getSelectedItem() == OrderStatus.COMPLETED && !chkBoxPaid.isSelected()) {
            showError("A completed order must be marked as paid.");
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
        lblCustomer = new javax.swing.JLabel();
        lblCustomerId = new javax.swing.JLabel();
        lblOrder = new javax.swing.JLabel();
        btnPlace = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        lblProductOpted = new javax.swing.JLabel();
        lblOrderId = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();
        lblOrderType = new javax.swing.JLabel();
        lblPayment = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        fieldCustomerId = new javax.swing.JTextField();
        fieldFirstName = new javax.swing.JTextField();
        fieldLastName = new javax.swing.JTextField();
        fieldContact = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        fieldOrderId = new javax.swing.JTextField();
        fieldDateTime = new javax.swing.JTextField();
        fieldQuantity = new javax.swing.JTextField();
        cmbType = new javax.swing.JComboBox<>();
        cmbPayment = new javax.swing.JComboBox<>();
        cmbStatus = new javax.swing.JComboBox<>();
        chkBoxPaid = new javax.swing.JCheckBox();
        lblProduct = new javax.swing.JLabel();
        lblProductId = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        cmbProduct = new javax.swing.JComboBox<>();
        fieldProdId = new javax.swing.JTextField();
        fieldProdPrice = new javax.swing.JTextField();
        lblCategory = new javax.swing.JLabel();
        fieldProdCategory = new javax.swing.JTextField();
        lblAvailable = new javax.swing.JLabel();
        lblPrep = new javax.swing.JLabel();
        fieldProdNumber = new javax.swing.JTextField();
        fieldProdPrepTime = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(245, 241, 235));

        lblTitle.setFont(new java.awt.Font("American Typewriter", 1, 24)); // NOI18N
        lblTitle.setText("New Customer Order");

        lblContact.setText("Contact");

        lblFirstName.setText("First Name");

        lblLastName.setText("Last Name");

        lblCustomer.setFont(new java.awt.Font("Andale Mono", 1, 16)); // NOI18N
        lblCustomer.setText("Customer");

        lblCustomerId.setText("Customer ID");

        lblOrder.setFont(new java.awt.Font("Andale Mono", 1, 16)); // NOI18N
        lblOrder.setText("Order");

        btnPlace.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnPlace.setText("Place Order");
        btnPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlaceActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lblProductOpted.setFont(new java.awt.Font("Andale Mono", 1, 16)); // NOI18N
        lblProductOpted.setText("Product Opted");

        lblOrderId.setText("Order ID");

        lblDateTime.setText("Date/Time");

        lblOrderType.setText("Order Type");

        lblPayment.setText("Payment");

        lblStatus.setText("Status");

        lblQuantity.setText("Quantity");

        btnFind.setText("Find");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        chkBoxPaid.setText("Paid");

        lblProduct.setText("Product");

        lblProductId.setText("Product ID");

        lblPrice.setText("Price");

        lblCategory.setText("Category");

        lblAvailable.setText("Available");

        lblPrep.setText("Prep (min)");

        jLabel1.setText("yyyy-MM-dd HH:mm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitle)
                .addGap(364, 364, 364))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(256, 256, 256)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnReset)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblPrep)
                                .addComponent(lblAvailable)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(btnPlace))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(fieldProdPrepTime, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblCustomerId)
                                            .addComponent(lblFirstName)
                                            .addComponent(lblLastName)
                                            .addComponent(lblContact))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(fieldCustomerId)
                                            .addComponent(fieldFirstName)
                                            .addComponent(fieldLastName)
                                            .addComponent(fieldContact, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblProduct)
                                            .addComponent(lblProductId)
                                            .addComponent(lblCategory))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(cmbProduct, 0, 125, Short.MAX_VALUE)
                                            .addComponent(fieldProdId)
                                            .addComponent(fieldProdPrice)
                                            .addComponent(fieldProdCategory))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblCustomer)
                            .addComponent(lblProductOpted)
                            .addComponent(lblPrice))
                        .addGap(86, 86, 86)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldProdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblOrder)
                                    .addComponent(lblOrderId)
                                    .addComponent(lblOrderType)
                                    .addComponent(lblDateTime)
                                    .addComponent(lblPayment)
                                    .addComponent(lblStatus)
                                    .addComponent(lblQuantity))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(fieldOrderId)
                                    .addComponent(fieldDateTime)
                                    .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fieldQuantity)
                                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(chkBoxPaid))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel1)))))))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnPlace, btnReset});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {fieldContact, fieldCustomerId, fieldDateTime, fieldFirstName, fieldLastName, fieldOrderId, fieldQuantity});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitle)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomer)
                    .addComponent(lblOrder))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblCustomerId)
                                .addComponent(fieldCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnFind))
                            .addComponent(lblOrderId, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFirstName)
                            .addComponent(lblDateTime)
                            .addComponent(fieldFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLastName)
                            .addComponent(lblOrderType)
                            .addComponent(fieldLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContact)
                            .addComponent(lblPayment)
                            .addComponent(fieldContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fieldOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQuantity)
                    .addComponent(chkBoxPaid))
                .addGap(5, 5, 5)
                .addComponent(lblProductOpted)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProduct)
                    .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProductId)
                    .addComponent(fieldProdId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAvailable)
                    .addComponent(fieldProdNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrice)
                            .addComponent(fieldProdPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPrep)
                            .addComponent(fieldProdPrepTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategory)
                    .addComponent(fieldProdCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset)
                    .addComponent(btnPlace))
                .addGap(43, 43, 43))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnPlace, btnReset});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {fieldContact, fieldCustomerId, fieldDateTime, fieldFirstName, fieldLastName, fieldOrderId, fieldQuantity});

    }// </editor-fold>//GEN-END:initComponents

    private void btnPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlaceActionPerformed
        // 1. Validate everything first - nothing is created unless all checks pass.
        if (existingCustomer == null) {
            if (!validateNewCustomer()) {
                return;
            }
        } else if (orderDirectory.hasOpenOrder(existingCustomer)) {
            showError(existingCustomer.getFullName() + " already has an open order.\n"
                    + "A customer can place only one order at a time.");
            return;
        }
        if (!validateOrder()) {
            return;
        }

        // 2. Create the customer (when new) and the order through the directories.
        Customer customer = existingCustomer;
        if (customer == null) {
            customer = customerDirectory.newCustomer(
                    Integer.parseInt(fieldCustomerId.getText().trim()),
                    fieldFirstName.getText().trim(),
                    fieldLastName.getText().trim(),
                    Long.parseLong(fieldContact.getText().trim()));
        }
        Product product = (Product) cmbProduct.getSelectedItem();
        Order order = orderDirectory.newOrder(
                Integer.parseInt(fieldOrderId.getText().trim()),
                customer,
                product,
                Integer.parseInt(fieldQuantity.getText().trim()),
                (OrderType) cmbType.getSelectedItem(),
                (PaymentMethod) cmbPayment.getSelectedItem(),
                (OrderStatus) cmbStatus.getSelectedItem(),
                chkBoxPaid.isSelected());
        order.setDateTime(LocalDateTime.parse(fieldDateTime.getText().trim(), Order.DATE_TIME_FORMAT));

        JOptionPane.showMessageDialog(this, "Order #" + order.getOrderId() + " placed for " + customer.getFullName()
                + "\n" + order.getQuantity() + " x " + product.getName()
                + " = $" + String.format("%.2f", order.getTotal()),
                "Order Placed", JOptionPane.INFORMATION_MESSAGE);
        resetForm();
    }//GEN-LAST:event_btnPlaceActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        String idText = fieldCustomerId.getText().trim();
        if (!Validator.isPositiveInt(idText)) {
            showError("Type a Customer ID (a whole number greater than 0) first.");
            return;
        }
        Customer found = customerDirectory.findById(Integer.parseInt(idText));
        if (found == null) {
            JOptionPane.showMessageDialog(this, "No customer with ID " + idText + ".\n"
                    + "Fill in the fields to register a new customer.",
                    "Not Found", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        existingCustomer = found;
        fieldFirstName.setText(found.getFirstName());
        fieldLastName.setText(found.getLastName());
        fieldContact.setText(String.valueOf(found.getContact()));
        setCustomerFieldsEditable(false);
        JOptionPane.showMessageDialog(this, found.getFullName() + " loaded. The order will be added to this customer.\n"
                + "Press Reset to register a new customer instead.",
                "Customer Found", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnFindActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnPlace;
    private javax.swing.JButton btnReset;
    private javax.swing.JCheckBox chkBoxPaid;
    private javax.swing.JComboBox<PaymentMethod> cmbPayment;
    private javax.swing.JComboBox<Product> cmbProduct;
    private javax.swing.JComboBox<OrderStatus> cmbStatus;
    private javax.swing.JComboBox<OrderType> cmbType;
    private javax.swing.JTextField fieldContact;
    private javax.swing.JTextField fieldCustomerId;
    private javax.swing.JTextField fieldDateTime;
    private javax.swing.JTextField fieldFirstName;
    private javax.swing.JTextField fieldLastName;
    private javax.swing.JTextField fieldOrderId;
    private javax.swing.JTextField fieldProdCategory;
    private javax.swing.JTextField fieldProdId;
    private javax.swing.JTextField fieldProdNumber;
    private javax.swing.JTextField fieldProdPrepTime;
    private javax.swing.JTextField fieldProdPrice;
    private javax.swing.JTextField fieldQuantity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAvailable;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblContact;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblCustomerId;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblFirstName;
    private javax.swing.JLabel lblLastName;
    private javax.swing.JLabel lblOrder;
    private javax.swing.JLabel lblOrderId;
    private javax.swing.JLabel lblOrderType;
    private javax.swing.JLabel lblPayment;
    private javax.swing.JLabel lblPrep;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblProduct;
    private javax.swing.JLabel lblProductId;
    private javax.swing.JLabel lblProductOpted;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
}

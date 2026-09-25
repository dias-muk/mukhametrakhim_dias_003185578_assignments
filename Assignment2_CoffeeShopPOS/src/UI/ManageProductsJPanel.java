/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 2 - Coffee Shop POS
 * Dias Mukhametrakhim, NUID 003185578
 */
package UI;

import Model.Business;
import Model.Product;
import Model.ProductCatalog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Lets the manager add, update and delete the products in the catalog.
 *
 * @author Dias Mukhametrakhim
 */
public class ManageProductsJPanel extends javax.swing.JPanel {

    private Business business;
    private ProductCatalog catalog;

    /**
     * Builds the panel and fills the table with the current catalog.
     *
     * @param business the coffee shop whose products are managed here
     */
    
    public ManageProductsJPanel(Business business) {
        initComponents();
        this.business = business;
        this.catalog = business.getProductCatalog();
        
        cmbCategory.setModel(new DefaultComboBoxModel<>(new String[] {
            "Coffee", "Tea", "Pastry", "Sandwich", "Dessert", "Other"}));
        
        tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Selecting a row (with the mouse or the arrow keys) fills the form.
        tblProducts.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromSelection();
            }
        });

        populateTable();
    }
    
    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
        model.setRowCount(0);

        for (Product p : catalog.getCatalog()) {
            Object[] row = new Object[6];
            row[0] = p;                     // toString() shows the name
            row[1] = p.getProductId();
            row[2] = p.getCategory();
            row[3] = String.format("%.2f", p.getPrice());
            row[4] = p.getNumber();
            row[5] = p.getPrepTime();
            model.addRow(row);
        }
    }
    
    /** The product in the selected row, or null when no row is selected. */
    private Product getSelectedProduct() {
        int selectedRow = tblProducts.getSelectedRow();
        if (selectedRow < 0) {
            return null;
        }
        return (Product) tblProducts.getValueAt(selectedRow, 0);
    }

    private void fillFormFromSelection() {
        Product p = getSelectedProduct();
        if (p == null) {
            return;
        }
        fieldProductId.setText(String.valueOf(p.getProductId()));
        fieldName.setText(p.getName());
        cmbCategory.setSelectedItem(p.getCategory());
        // String.valueOf always uses a dot, so the value parses back even on a
        // computer whose region format writes 4,75.
        fieldPrice.setText(String.valueOf(p.getPrice()));
        fieldNumber.setText(String.valueOf(p.getNumber()));
        fieldPrepTime.setText(String.valueOf(p.getPrepTime()));
    }

    private void clearForm() {
        tblProducts.clearSelection();
        fieldProductId.setText("");
        fieldName.setText("");
        cmbCategory.setSelectedIndex(0);
        fieldPrice.setText("");
        fieldNumber.setText("");
        fieldPrepTime.setText("");
    }
    
    

    /**
     * Checks every field of the form and shows an error dialog for the
     * first problem found.
     *
     * @param editing the product being updated, or null when adding a new one
     * @return true when the form can be saved
     */
    private boolean validateInput(Product editing) {
        String idText = fieldProductId.getText().trim();
        String name = fieldName.getText().trim();
        String priceText = fieldPrice.getText().trim();
        String numberText = fieldNumber.getText().trim();
        String prepText = fieldPrepTime.getText().trim();

        if (!require(idText, "Product ID")) return false;
        if (!require(name, "Name")) return false;
        if (!require(priceText, "Price")) return false;
        if (!require(numberText, "Number in stock")) return false;
        if (!require(prepText, "Prep time")) return false;

        if (!Validator.isPositiveInt(idText)) {
            showError("Product ID must be a whole number greater than 0.");
            return false;
        }
        int productId = Integer.parseInt(idText);
        Product owner = catalog.findById(productId);
        if (owner != null && owner != editing) {
            showError("Product ID " + productId + " is already used by " + owner.getName() + ".");
            return false;
        }
        if (!Validator.isPositiveDouble(priceText)) {
            showError("Price must be a number greater than 0, for example 4.75");
            return false;
        }
        if (!Validator.isNonNegativeInt(numberText)) {
            showError("Number in stock must be a whole number, 0 or more.");
            return false;
        }
        if (!Validator.isPositiveInt(prepText) || Integer.parseInt(prepText) > 60) {
            showError("Prep time must be a whole number of minutes from 1 to 60.");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducts = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        cmbCategory = new javax.swing.JComboBox<>();
        lblProductId = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblNumber = new javax.swing.JLabel();
        lblPrepTime = new javax.swing.JLabel();
        fieldProductId = new javax.swing.JTextField();
        fieldName = new javax.swing.JTextField();
        fieldPrice = new javax.swing.JTextField();
        fieldPrepTime = new javax.swing.JTextField();
        fieldNumber = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 241, 235));

        lblTitle.setFont(new java.awt.Font("American Typewriter", 1, 24)); // NOI18N
        lblTitle.setText("Manage Products");

        tblProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Product ID", "Category", "Price", "Number", "Prep Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProducts.setFillsViewportHeight(true);
        tblProducts.setRowHeight(26);
        jScrollPane1.setViewportView(tblProducts);
        if (tblProducts.getColumnModel().getColumnCount() > 0) {
            tblProducts.getColumnModel().getColumn(0).setResizable(false);
            tblProducts.getColumnModel().getColumn(1).setResizable(false);
            tblProducts.getColumnModel().getColumn(2).setResizable(false);
            tblProducts.getColumnModel().getColumn(3).setResizable(false);
            tblProducts.getColumnModel().getColumn(4).setResizable(false);
            tblProducts.getColumnModel().getColumn(5).setResizable(false);
        }

        btnAdd.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnAdd.setText("Add Product");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnDelete.setText("Delete Selected");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblProductId.setText("Product ID");

        lblName.setText("Name");

        lblCategory.setText("Category");

        lblPrice.setText("Price");

        lblNumber.setText("Number in Stock");

        lblPrepTime.setText("Prep Time (min)");

        btnUpdate.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnUpdate.setText("Update Selected");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnClear.setText("Clear Form");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCategory)
                    .addComponent(lblProductId)
                    .addComponent(lblName))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbCategory, 0, 128, Short.MAX_VALUE)
                            .addComponent(fieldProductId)
                            .addComponent(fieldName))
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPrice)
                            .addComponent(lblPrepTime)
                            .addComponent(lblNumber))
                        .addGap(91, 91, 91)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fieldNumber)
                            .addComponent(fieldPrepTime)
                            .addComponent(fieldPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(btnAdd)
                .addGap(34, 34, 34)
                .addComponent(btnUpdate)
                .addGap(26, 26, 26)
                .addComponent(btnDelete)
                .addGap(34, 34, 34)
                .addComponent(btnClear)
                .addGap(63, 63, 63))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1)
                    .addContainerGap()))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAdd, btnClear, btnDelete, btnUpdate});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 438, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblName)
                        .addComponent(lblPrice)
                        .addComponent(fieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fieldName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrepTime)
                    .addComponent(lblProductId)
                    .addComponent(fieldProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldPrepTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumber)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCategory)
                    .addComponent(fieldNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addGap(21, 21, 21))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(63, 63, 63)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(248, Short.MAX_VALUE)))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAdd, btnClear, btnDelete, btnUpdate});

    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validateInput(null)) {
            return;
        }
        Product p = catalog.newProduct(
                Integer.parseInt(fieldProductId.getText().trim()),
                fieldName.getText().trim(),
                (String) cmbCategory.getSelectedItem(),
                Double.parseDouble(fieldPrice.getText().trim()),
                Integer.parseInt(fieldNumber.getText().trim()),
                Integer.parseInt(fieldPrepTime.getText().trim()));

        populateTable();
        clearForm();
        JOptionPane.showMessageDialog(this, p.getName() + " was added to the catalog.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        Product p = getSelectedProduct();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Please select a product in the table first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validateInput(p)) {
            return;
        }
        p.setProductId(Integer.parseInt(fieldProductId.getText().trim()));
        p.setName(fieldName.getText().trim());
        p.setCategory((String) cmbCategory.getSelectedItem());
        p.setPrice(Double.parseDouble(fieldPrice.getText().trim()));
        p.setNumber(Integer.parseInt(fieldNumber.getText().trim()));
        p.setPrepTime(Integer.parseInt(fieldPrepTime.getText().trim()));

        populateTable();
        clearForm();
        JOptionPane.showMessageDialog(this, p.getName() + " was updated.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        Product p = getSelectedProduct();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Please select a product in the table first.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // An order keeps a reference to its product, so a product that is
        // still ordered cannot be removed from the catalog.
        if (business.getOrderDirectory().isProductInUse(p)) {
            JOptionPane.showMessageDialog(this, p.getName() + " is used in existing orders and cannot be deleted.",
                    "Cannot Delete", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this, "Delete " + p.getName() + " from the catalog?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            catalog.deleteProduct(p);
            populateTable();
            clearForm();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearForm();
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JTextField fieldName;
    private javax.swing.JTextField fieldNumber;
    private javax.swing.JTextField fieldPrepTime;
    private javax.swing.JTextField fieldPrice;
    private javax.swing.JTextField fieldProductId;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNumber;
    private javax.swing.JLabel lblPrepTime;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblProductId;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblProducts;
    // End of variables declaration//GEN-END:variables
}

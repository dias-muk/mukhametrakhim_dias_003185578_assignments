/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.WorkAreas.AdminRole.AdministerUserAccountsWorkResp;

import Business.Business;
import Business.Profiles.Profile;
import Business.UserAccounts.UserAccount;
import UserInterface.Validator.Validator;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * One user account. Opened with an account, the admin can update or delete
 * it; opened with null, it creates a new account for an existing profile.
 *
 * @author kal bugrara (skeleton), Dias Mukhametrakhim (Assignment 3)
 */
public class AdminUserAccount extends javax.swing.JPanel {

    /**
     * Creates new form ManageSuppliersJPanel
     */
    JPanel CardSequencePanel;
    Business business;
    UserAccount selecteduseraccount;    // null while creating a new account
    UserAccount currentUser;            // the admin who is logged in

    public AdminUserAccount(Business b, UserAccount sua, UserAccount current, JPanel jp) {
        CardSequencePanel = jp;
        business = b;
        selecteduseraccount = sua;
        currentUser = current;
        initComponents();

        fieldName.setEditable(false);
        fieldRole.setEditable(false);
        fieldLastLogin.setEditable(false);
        fieldLastUpdated.setEditable(false);

        if (selecteduseraccount == null) {
            lblTitle.setText("New User Account");
            lblPassword.setText("Password:");
            btnUpdate.setText("Create Account");
            btnDelete.setVisible(false);
            chkActive.setSelected(true);
            fieldLastLogin.setText("Never");
            fieldLastUpdated.setText("Not saved yet");
        } else {
            lblPassword.setText("New password:");
            btnUpdate.setText("Save Changes");
            fieldNuid.setEditable(false);
            showAccount();
        }
    }
    
 /** Fills the form from the selected account. The password box stays empty. */
    private void showAccount() {
        Profile profile = selecteduseraccount.getAssociatedPersonProfile();
        lblTitle.setText("User Account: " + selecteduseraccount.getUserLoginName());
        fieldNuid.setText(profile.getPerson().getPersonId());
        fieldName.setText(profile.getPerson().getName());
        fieldRole.setText(profile.getRole());
        fieldUsername.setText(selecteduseraccount.getUserLoginName());
        fieldPassword.setText("");
        chkActive.setSelected(selecteduseraccount.isActive());
        fieldLastLogin.setText(selecteduseraccount.getLastAccessedText());
        fieldLastUpdated.setText(selecteduseraccount.getLastUpdatedText());
    }
    
    private void createAccount() {
        if (!Validator.isFilled(this, fieldNuid, "NUID")
                || !Validator.isFilled(this, fieldUsername, "Username")
                || !Validator.isFilled(this, fieldPassword, "Password")) {
            return;
        }
        String nuid = fieldNuid.getText().trim();
        String un = fieldUsername.getText().trim();
        String pw = new String(fieldPassword.getPassword());

        Profile profile = business.findProfile(nuid);
        if (profile == null) {
            error("No person with NUID " + nuid + ". Register them first.");
            return;
        }
        if (business.getUserAccountDirectory().findByProfile(profile) != null) {
            error(profile.getPerson().getName() + " already has an account.");
            return;
        }
        if (!usernameIsOk(un) || !passwordIsOk(pw)) {
            return;
        }
        UserAccount ua = business.getUserAccountDirectory().newUserAccount(profile, un, pw);
        ua.setActive(chkActive.isSelected());
        JOptionPane.showMessageDialog(this, "Account \"" + un + "\" created for "
                + profile.getPerson().getName() + " (" + profile.getRole() + ").",
                "Account created", JOptionPane.INFORMATION_MESSAGE);
        goBack();
    }
    
    private void saveChanges() {
        if (!Validator.isFilled(this, fieldUsername, "Username")) {
            return;
        }
        String un = fieldUsername.getText().trim();
        String pw = new String(fieldPassword.getPassword());    // empty = keep the current password
        if (!usernameIsOk(un)) {
            return;
        }
        if (!pw.isEmpty() && !passwordIsOk(pw)) {
            return;
        }
        if (selecteduseraccount == currentUser && !chkActive.isSelected()) {
            error("You cannot disable the account you are logged in with.");
            chkActive.setSelected(true);
            return;
        }
        selecteduseraccount.setUsername(un);
        if (!pw.isEmpty()) {
            selecteduseraccount.setPassword(pw);
        }
        selecteduseraccount.setActive(chkActive.isSelected());
        showAccount();
        JOptionPane.showMessageDialog(this, "Account updated.", "Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    /** The username rule, and no other account may use it already (ignoring case). */
    private boolean usernameIsOk(String un) {
        if (!Validator.isValidUsername(un)) {
            warn(Validator.USERNAME_RULE);
            return false;
        }
        UserAccount other = business.getUserAccountDirectory().findByUsername(un);
        if (other != null && other != selecteduseraccount) {
            error("The username \"" + un + "\" is already taken.");
            return false;
        }
        return true;
    }

    private boolean passwordIsOk(String pw) {
        if (!Validator.isValidPassword(pw)) {
            warn(Validator.PASSWORD_RULE);
            return false;
        }
        return true;
    }

    /** Removes this screen and shows the accounts list again, reloaded. */
    private void goBack() {
        CardSequencePanel.remove(this);
        java.awt.Component[] stack = CardSequencePanel.getComponents();
        java.awt.Component below = stack[stack.length - 1];
        if (below instanceof ManageUserAccountsJPanel) {
            ((ManageUserAccountsJPanel) below).refreshTable();
        }
        ((java.awt.CardLayout) CardSequencePanel.getLayout()).previous(CardSequencePanel);
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void warn(String message) {
        JOptionPane.showMessageDialog(this, message, "Check your input", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnUpdate = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        lblNUID = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblRole = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        chkActive = new javax.swing.JCheckBox();
        lblLastLogin = new javax.swing.JLabel();
        lblLastUpdate = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        fieldUsername = new javax.swing.JTextField();
        fieldNuid = new javax.swing.JTextField();
        fieldName = new javax.swing.JTextField();
        fieldRole = new javax.swing.JTextField();
        fieldPassword = new javax.swing.JPasswordField();
        fieldLastLogin = new javax.swing.JTextField();
        fieldLastUpdated = new javax.swing.JTextField();

        setBackground(new java.awt.Color(31, 58, 95));
        setLayout(null);

        btnUpdate.setBackground(new java.awt.Color(46, 134, 222));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setBorderPainted(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        add(btnUpdate);
        btnUpdate.setBounds(440, 440, 170, 23);

        lblTitle.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Administer User Account");
        add(lblTitle);
        lblTitle.setBounds(21, 20, 550, 28);

        btnBack.setBackground(new java.awt.Color(46, 134, 222));
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("Back");
        btnBack.setBorderPainted(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack);
        btnBack.setBounds(20, 440, 100, 23);

        lblNUID.setForeground(new java.awt.Color(255, 255, 255));
        lblNUID.setText("NUID");
        add(lblNUID);
        lblNUID.setBounds(40, 70, 30, 17);

        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("Name");
        add(lblName);
        lblName.setBounds(40, 100, 34, 17);

        lblRole.setForeground(new java.awt.Color(255, 255, 255));
        lblRole.setText("Role");
        add(lblRole);
        lblRole.setBounds(40, 130, 26, 17);

        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username");
        add(lblUsername);
        lblUsername.setBounds(40, 160, 70, 17);

        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Password");
        add(lblPassword);
        lblPassword.setBounds(40, 190, 70, 17);

        chkActive.setForeground(new java.awt.Color(255, 255, 255));
        chkActive.setText("Account is active");
        chkActive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkActiveActionPerformed(evt);
            }
        });
        add(chkActive);
        chkActive.setBounds(40, 220, 130, 21);

        lblLastLogin.setForeground(new java.awt.Color(255, 255, 255));
        lblLastLogin.setText("Last login");
        add(lblLastLogin);
        lblLastLogin.setBounds(40, 250, 80, 17);

        lblLastUpdate.setForeground(new java.awt.Color(255, 255, 255));
        lblLastUpdate.setText("Last updated");
        add(lblLastUpdate);
        lblLastUpdate.setBounds(40, 280, 90, 17);

        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete Account");
        btnDelete.setBorderPainted(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        add(btnDelete);
        btnDelete.setBounds(40, 350, 150, 23);
        add(fieldUsername);
        fieldUsername.setBounds(110, 150, 170, 23);
        add(fieldNuid);
        fieldNuid.setBounds(108, 60, 170, 23);
        add(fieldName);
        fieldName.setBounds(108, 90, 170, 23);
        add(fieldRole);
        fieldRole.setBounds(108, 120, 170, 23);
        add(fieldPassword);
        fieldPassword.setBounds(110, 190, 170, 23);
        add(fieldLastLogin);
        fieldLastLogin.setBounds(130, 250, 150, 23);
        add(fieldLastUpdated);
        fieldLastUpdated.setBounds(130, 280, 150, 23);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (selecteduseraccount == null) {
            createAccount();
        } else {
            saveChanges();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        goBack();
    }//GEN-LAST:event_btnBackActionPerformed

    private void chkActiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkActiveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkActiveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selecteduseraccount == currentUser) {
            error("You cannot delete the account you are logged in with.");
            return;
        }
        String name = selecteduseraccount.getAssociatedPersonProfile().getPerson().getName();
        int answer = JOptionPane.showConfirmDialog(this,
                "Delete the account \"" + selecteduseraccount.getUserLoginName() + "\"?\n"
                + name + " will no longer be able to log in.",
                "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        business.getUserAccountDirectory().removeUserAccount(selecteduseraccount);
        JOptionPane.showMessageDialog(this, "Account deleted.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
        goBack();
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JCheckBox chkActive;
    private javax.swing.JTextField fieldLastLogin;
    private javax.swing.JTextField fieldLastUpdated;
    private javax.swing.JTextField fieldName;
    private javax.swing.JTextField fieldNuid;
    private javax.swing.JPasswordField fieldPassword;
    private javax.swing.JTextField fieldRole;
    private javax.swing.JTextField fieldUsername;
    private javax.swing.JLabel lblLastLogin;
    private javax.swing.JLabel lblLastUpdate;
    private javax.swing.JLabel lblNUID;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRole;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    // End of variables declaration//GEN-END:variables

}

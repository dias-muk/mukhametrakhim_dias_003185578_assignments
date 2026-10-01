/*
 * INFO 5100 - Application Engineering and Development
 * Assignment 3 - Profiles and Work Areas
 * Dias Mukhametrakhim, NUID 003185578
 */
package UserInterface.WorkAreas.AdminRole.ManageFacultyWorkResp;

import Business.Business;
import Business.Person.Person;
import Business.Profiles.FacultyProfile;
import Business.UserAccounts.UserAccount;
import UserInterface.Validator.Validator;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * One faculty member. Opened with a faculty profile, the admin can update or
 * delete it; opened with null, it adds a new faculty member with a login.
 * Only the admin can create faculty accounts; sign-up makes students only.
 *
 * @author Dias Mukhametrakhim
 */
public class AdministerFacultyJPanel extends javax.swing.JPanel {

    JPanel CardSequencePanel;
    Business business;
    FacultyProfile faculty;     // null while adding a new faculty member

    public AdministerFacultyJPanel(Business bz, FacultyProfile fp, JPanel jp) {

        CardSequencePanel = jp;
        this.business = bz;
        faculty = fp;
        initComponents();

        if (faculty == null) {
            lblHeader.setText("Add New Faculty Member");
            btnSave.setText("Add Faculty");
            btnDelete.setVisible(false);
        } else {
            btnSave.setText("Save Changes");
            fieldNuid.setEditable(false);
            fieldUsername.setEditable(false);   // logins are changed in Administer User Accounts
            lblPassword.setVisible(false);
            fieldPassword.setVisible(false);
            showFaculty();
        }

    }
    
   /** Fills the form from the selected faculty member. */
    private void showFaculty() {
        Person person = faculty.getPerson();
        lblHeader.setText("Faculty: " + person.getName());
        fieldNuid.setText(person.getPersonId());
        fieldName.setText(person.getName());
        fieldEmail.setText(person.getEmail());
        fieldPhone.setText(person.getPhone());
        fieldDepartment.setText(faculty.getDepartment());
        fieldTitle.setText(faculty.getTitle());
        UserAccount ua = business.getUserAccountDirectory().findByProfile(faculty);
        fieldUsername.setText(ua == null ? "No account" : ua.getUserLoginName());
    }

    /** The fields every faculty member needs, checked top to bottom. */
    private boolean detailsAreFilled() {
        return Validator.isFilled(this, fieldNuid, "NUID")
                && Validator.isFilled(this, fieldName, "Name")
                && Validator.isFilled(this, fieldEmail, "Email")
                && Validator.isFilled(this, fieldPhone, "Phone")
                && Validator.isFilled(this, fieldDepartment, "Department")
                && Validator.isFilled(this, fieldTitle, "Title");
    }

    /** Copies the form into the person and the faculty profile. */
    private void copyDetailsInto(Person person, FacultyProfile fp) {
        person.setName(fieldName.getText().trim());
        person.setEmail(fieldEmail.getText().trim());
        person.setPhone(fieldPhone.getText().trim());
        fp.setDepartment(fieldDepartment.getText().trim());
        fp.setTitle(fieldTitle.getText().trim());
    }

    private void addFaculty() {
        if (!detailsAreFilled()
                || !Validator.isFilled(this, fieldUsername, "Username")
                || !Validator.isFilled(this, fieldPassword, "Password")) {
            return;
        }
        String nuid = fieldNuid.getText().trim();
        String un = fieldUsername.getText().trim();
        String pw = new String(fieldPassword.getPassword());

        if (business.getPersonDirectory().findPerson(nuid) != null) {
            error("NUID " + nuid + " is already registered.");
            return;
        }
        if (!Validator.isValidUsername(un)) {
            warn(Validator.USERNAME_RULE);
            return;
        }
        if (business.getUserAccountDirectory().findByUsername(un) != null) {
            error("The username \"" + un + "\" is already taken.");
            return;
        }
        if (!Validator.isValidPassword(pw)) {
            warn(Validator.PASSWORD_RULE);
            return;
        }
        Person person = business.getPersonDirectory().newPerson(nuid, fieldName.getText().trim());
        FacultyProfile fp = business.getFacultyDirectory().newFacultyProfile(person);
        copyDetailsInto(person, fp);
        business.getUserAccountDirectory().newUserAccount(fp, un, pw);
        JOptionPane.showMessageDialog(this, "Faculty member added. They can log in as \"" + un + "\".",
                "Faculty added", JOptionPane.INFORMATION_MESSAGE);
        goBack();
    }

    private void saveChanges() {
        if (!detailsAreFilled()) {
            return;
        }
        copyDetailsInto(faculty.getPerson(), faculty);
        showFaculty();
        JOptionPane.showMessageDialog(this, "Faculty member updated.", "Saved", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Removes this screen and shows the faculty list again, reloaded. */
    private void goBack() {
        CardSequencePanel.remove(this);
        java.awt.Component[] stack = CardSequencePanel.getComponents();
        java.awt.Component below = stack[stack.length - 1];
        if (below instanceof ManageFacultyJPanel) {
            ((ManageFacultyJPanel) below).refreshTable();
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

        btnBack = new javax.swing.JButton();
        lblHeader = new javax.swing.JLabel();
        lblNUID = new javax.swing.JLabel();
        fieldNuid = new javax.swing.JTextField();
        fieldName = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        fieldEmail = new javax.swing.JTextField();
        fieldUsername = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        fieldPassword = new javax.swing.JPasswordField();
        lblTitle = new javax.swing.JLabel();
        fieldTitle = new javax.swing.JTextField();
        lblDepartment = new javax.swing.JLabel();
        fieldDepartment = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        lblPhone = new javax.swing.JLabel();
        fieldPhone = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();

        setBackground(new java.awt.Color(31, 58, 95));
        setForeground(new java.awt.Color(255, 255, 255));
        setLayout(null);

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
        btnBack.setBounds(30, 440, 80, 23);

        lblHeader.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader.setText("Manage Faculty Profile");
        add(lblHeader);
        lblHeader.setBounds(21, 20, 550, 28);

        lblNUID.setForeground(new java.awt.Color(255, 255, 255));
        lblNUID.setText("NUID");
        add(lblNUID);
        lblNUID.setBounds(30, 80, 30, 17);
        add(fieldNuid);
        fieldNuid.setBounds(100, 70, 170, 23);
        add(fieldName);
        fieldName.setBounds(100, 100, 170, 23);

        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("Name");
        add(lblName);
        lblName.setBounds(30, 110, 34, 17);

        lblEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblEmail.setText("Email");
        add(lblEmail);
        lblEmail.setBounds(30, 140, 40, 17);
        add(fieldEmail);
        fieldEmail.setBounds(100, 130, 170, 23);
        add(fieldUsername);
        fieldUsername.setBounds(100, 160, 170, 23);

        lblUsername.setForeground(new java.awt.Color(255, 255, 255));
        lblUsername.setText("Username");
        add(lblUsername);
        lblUsername.setBounds(30, 170, 70, 17);

        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Password");
        add(lblPassword);
        lblPassword.setBounds(30, 200, 70, 17);

        fieldPassword.setText("jPasswordField1");
        add(fieldPassword);
        fieldPassword.setBounds(100, 200, 170, 23);

        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Title");
        add(lblTitle);
        lblTitle.setBounds(30, 230, 80, 17);
        add(fieldTitle);
        fieldTitle.setBounds(120, 230, 150, 23);

        lblDepartment.setForeground(new java.awt.Color(255, 255, 255));
        lblDepartment.setText("Department");
        add(lblDepartment);
        lblDepartment.setBounds(30, 290, 90, 17);
        add(fieldDepartment);
        fieldDepartment.setBounds(120, 290, 150, 23);

        btnDelete.setBackground(new java.awt.Color(192, 57, 43));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete Faculty");
        btnDelete.setBorderPainted(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        add(btnDelete);
        btnDelete.setBounds(30, 360, 150, 23);

        lblPhone.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone.setText("Phone");
        add(lblPhone);
        lblPhone.setBounds(30, 260, 80, 17);
        add(fieldPhone);
        fieldPhone.setBounds(120, 260, 150, 23);

        btnSave.setBackground(new java.awt.Color(0, 128, 0));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.setBorderPainted(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        add(btnSave);
        btnSave.setBounds(260, 360, 160, 23);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        goBack();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int answer = JOptionPane.showConfirmDialog(this,
                "Delete " + faculty.getPerson().getName() + "? Their login will be deleted too.",
                "Confirm delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        business.deleteProfile(faculty);
        JOptionPane.showMessageDialog(this, "Faculty member deleted.", "Deleted", JOptionPane.INFORMATION_MESSAGE);
        goBack();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (faculty == null) {
            addFaculty();
        } else {
            saveChanges();
        }
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JTextField fieldDepartment;
    private javax.swing.JTextField fieldEmail;
    private javax.swing.JTextField fieldName;
    private javax.swing.JTextField fieldNuid;
    private javax.swing.JPasswordField fieldPassword;
    private javax.swing.JTextField fieldPhone;
    private javax.swing.JTextField fieldTitle;
    private javax.swing.JTextField fieldUsername;
    private javax.swing.JLabel lblDepartment;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblNUID;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsername;
    // End of variables declaration//GEN-END:variables

}

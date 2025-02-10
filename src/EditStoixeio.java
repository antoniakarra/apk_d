import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;


public class EditStoixeio extends JFrame {
    private int id_user;
    private String initialNameText, initialYearText, initialDescriptionText, initialFullDescriptionText, initialHistoricalDataText, initialBibliographyText, initialLocationDescriptionText;
    private String initialMembersText, initialSocietyText, initialCommunityText, initialTransmissionText, initialExistingText, initialFutureText;
    private JTextField idStoixeioField;
    private JTextField nameField;
    private JTextField yearField;
    private JTextField otherNameInputField;
    private JTextArea otherNameField;
    private Set<String> otherNameList;
    private JTextArea descriptionArea;
    private JComboBox<String> fieldComboBox;
    private JTextArea justification_of_fieldsField;
    private Set<String> fieldList;
    private Set<String> selectedFields;
    private JTextArea fieldField;
    private JTextArea locationDescriptionArea;
    private JComboBox<String> decentralizedAdminComboBox;
    private JTextArea locationField;
    private Set<String> locationList;
    private JComboBox<String> regionComboBox;
    private JComboBox<String> regionalUnitComboBox;
    private JComboBox<String> municipalityComboBox;
    private JComboBox<String> municipalUnitComboBox;
    private JComboBox<String> communitiesComboBox;
    private JTextField keywordInputField;
    private JTextArea keywordField;
    private Set<String> keywordList;
    private Set<String> foreasList;
    private JTextField foreasNameField;
    private JTextArea foreasDescriptionField;
    private JTextField foreasAddressField;
    private JTextField foreasCityField;
    private JTextField foreasTelField;
    private JTextField foreasEmailField;
    private JTextField foreasTKField;
    private JTextField foreasWebsiteField;
    private JTextArea foreasField;
    private JTextArea full_descriptionArea;
    private JTextField performanceAreaInputField;
    private JTextArea justificationPerformanceAreaField;
    private JTextArea performanceAreaField;
    private Set<String> performanceAreasList;
    private JTextField facilitiesInputField;
    private JTextArea justificationFacilityField;
    private JTextArea facilitiesField;
    private Set<String> facilitiesList;
    private JTextField equipmentInputField;
    private JTextArea justificationEquipmentField;
    private JTextArea equipmentField;
    private Set<String> equipmentList;
    private JTextField productInputField;
    private JTextArea justificationProductField;
    private JTextArea productField;
    private Set<String> productsList;
    private JTextArea historical_dataField;
    private JTextArea societyField;
    private JTextArea communityField;
    private JTextArea membersField;
    private JTextArea transmissionField;
    private JTextArea existingField;
    private JTextArea futureField;
    private JComboBox<String> categoryComboBox;
    private JTextField filePathField;
    private JTextArea evidenceField;
    private Set<String> evidenceList;
    private JTextField dateOfEntryField;
    private JTextField whoUploadedItField;
    private Set<String> competentpersonsList;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField addresscpField;
    private JTextField emailcpField;
    private JTextField telnumberField;
    private JTextArea competentpersonsField;
    private JTextArea bibliographyField;

    // Ρυθμίσεις για σύνδεση με τη βάση δεδομένων
    private static final String DB_URL = "jdbc:mysql://localhost:3306/apk";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "apk2024";

    public EditStoixeio(int id_user, String name, String email) {

        this.id_user = id_user;

        int user_Id = Login.UserSession.getUserId();
        String userName = Login.UserSession.getFullName();

        setTitle("Επεξεργασία Στοιχείου - " + name);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        UIManager.put("Panel.background", new Color(255, 255, 255));
        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 12));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ID (Μόνο για εμφάνιση, χωρίς δυνατότητα επεξεργασίας)
        JPanel IdPanel = new JPanel(new BorderLayout());
        IdPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        IdPanel.add(new JLabel("ID:"), BorderLayout.NORTH);
        idStoixeioField = new JTextField();
        IdPanel.add(idStoixeioField, BorderLayout.CENTER);
        idStoixeioField.setEditable(false);
        formPanel.add(IdPanel);

        // Όνομα
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        namePanel.add(new JLabel("Όνομα:"), BorderLayout.NORTH);
        nameField = new JTextField();
        nameField.setEditable(false);
        namePanel.add(nameField, BorderLayout.CENTER);
        formPanel.add(namePanel);

        JPanel editNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editNameButton = new JButton("Επεξεργασία Ονόματος");
        editNameButton.setBackground(Color.GRAY);
        editNameButton.setForeground(Color.WHITE);
        editNameButton.setVisible(false);
        editNamePanel.add(editNameButton);

        JPanel saveNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveNameButton = new JButton("Αποθήκευση Ονόματος");
        saveNameButton.setVisible(false);
        saveNamePanel.add(saveNameButton);

        JPanel cancelNamePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelNameButton = new JButton("Ακύρωση");
        cancelNameButton.setBackground(Color.lightGray);
        cancelNameButton.setForeground(Color.WHITE);
        cancelNameButton.setVisible(false);
        cancelNamePanel.add(cancelNameButton);

        JPanel NamePanel = new JPanel(new BorderLayout());
        NamePanel.add(saveNamePanel, BorderLayout.CENTER);
        NamePanel.add(cancelNamePanel, BorderLayout.EAST);
        NamePanel.add(editNamePanel, BorderLayout.WEST);

        formPanel.add(NamePanel);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT userrole FROM user WHERE id_user = ?")) {

            stmt.setInt(1, id_user);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && "admin".equalsIgnoreCase(rs.getString("userrole"))) {
                    // Ο χρήστης είναι admin
                    editNameButton.setVisible(true);
                    saveNameButton.setVisible(true);
                    cancelNameButton.setVisible(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά την ανάκτηση δεδομένων από τη βάση.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editNameButton.addActionListener(e -> {
            nameField.setEditable(true);
            nameField.requestFocus();
            saveNameButton.setEnabled(true);
            cancelNameButton.setEnabled(true);
            editNameButton.setEnabled(false);
        });

        // Ενέργεια για το κουμπί "Αποθήκευση"
        saveNameButton.addActionListener(e -> {
            nameField.setEditable(false);
            saveNameButton.setEnabled(false);
            cancelNameButton.setEnabled(false);
            editNameButton.setEnabled(true);

            // Λήψη του `idStoixeio` από το `idStoixeioField`
            int idStoixeio = Integer.parseInt(idStoixeioField.getText());

            String newName = nameField.getText();

            if (newName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Το όνομα είναι υποχρεωτικό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newName.equals(initialNameText)) {
                JOptionPane.showMessageDialog(null, "Το όνομα δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String fullName= Login.UserSession.getFullName();

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String setSessionUser = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionUser)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }

                String updateQuery = "UPDATE stoixeio SET name = ? WHERE idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newName);
                pstmt.setInt(2, idStoixeio);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Το όνομα αποθηκεύτηκε επιτυχώς στη βάση.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η ενημέρωση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            nameField.setEditable(false);
            saveNameButton.setEnabled(false);
            cancelNameButton.setEnabled(false);
            editNameButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelNameButton.addActionListener(e -> {
            nameField.setText(initialNameText);
            nameField.setEditable(false);
            saveNameButton.setEnabled(false);
            cancelNameButton.setEnabled(false);
            editNameButton.setEnabled(true);
        });


        // Έτος
        JPanel yearPanel = new JPanel(new BorderLayout());
        yearPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        yearPanel.add(new JLabel("Έτος:"), BorderLayout.NORTH);
        yearField = new JTextField();
        yearField.setEditable(false);
        yearPanel.add(yearField, BorderLayout.CENTER);
        formPanel.add(yearPanel);

        JPanel editYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editYearButton = new JButton("Επεξεργασία Έτους");
        editYearButton.setBackground(Color.GRAY);
        editYearButton.setForeground(Color.WHITE);
        editYearButton.setVisible(false);
        editYearPanel.add(editYearButton);

        JPanel saveYearPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveYearButton = new JButton("Αποθήκευση Έτους");
        saveYearButton.setVisible(false);
        saveYearPanel.add(saveYearButton);

        JPanel cancelYearPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelYearButton = new JButton("Ακύρωση");
        cancelYearButton.setBackground(Color.lightGray);
        cancelYearButton.setForeground(Color.WHITE);
        cancelYearButton.setVisible(false);
        cancelYearPanel.add(cancelYearButton);

        JPanel YearPanel = new JPanel(new BorderLayout());
        YearPanel.add(saveYearPanel, BorderLayout.CENTER);
        YearPanel.add(cancelYearPanel, BorderLayout.EAST);
        YearPanel.add(editYearPanel, BorderLayout.WEST);

        formPanel.add(YearPanel);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT userrole FROM user WHERE id_user = ?")) {

            stmt.setInt(1, id_user);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && "admin".equalsIgnoreCase(rs.getString("userrole"))) {
                    // Ο χρήστης είναι admin
                    editYearButton.setVisible(true);
                    saveYearButton.setVisible(true);
                    cancelYearButton.setVisible(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά την ανάκτηση δεδομένων από τη βάση.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
        // Ενέργεια για το κουμπί "Επεξεργασία"
        editYearButton.addActionListener(e -> {
            yearField.setEditable(true);
            yearField.requestFocus();
            saveYearButton.setEnabled(true);
            cancelYearButton.setEnabled(true);
            editYearButton.setEnabled(false);
        });

        // Ενέργεια για το κουμπί "Αποθήκευση"
        saveYearButton.addActionListener(e -> {
            yearField.setEditable(false);
            saveYearButton.setEnabled(false);
            cancelYearButton.setEnabled(false);
            editYearButton.setEnabled(true);

            int idStoixeio = Integer.parseInt(idStoixeioField.getText());

            String newYear = yearField.getText();

            if (newYear.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Το έτος είναι υποχρεωτικό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newYear.equals(initialYearText)) {
                JOptionPane.showMessageDialog(null, "Το έτος δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String fullName= Login.UserSession.getFullName();

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String setSessionUser = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionUser)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }

                String updateQuery = "UPDATE stoixeio SET year = ? WHERE idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newYear);
                pstmt.setInt(2, idStoixeio);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Το έτος αποθηκεύτηκε επιτυχώς στη βάση.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η ενημέρωση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }

            yearField.setEditable(false);
            saveYearButton.setEnabled(false);
            cancelYearButton.setEnabled(false);
            editYearButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelYearButton.addActionListener(e -> {
            yearField.setText(initialYearText);
            yearField.setEditable(false);
            saveYearButton.setEnabled(false);
            cancelYearButton.setEnabled(false);
            editYearButton.setEnabled(true);
        });

        // Άλλα Ονόματα
        otherNameList = new  HashSet<>();
        JPanel otherNamePanel = new JPanel(new BorderLayout());
        otherNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        otherNamePanel.add(new JLabel("Άλλα Ονόματα:"), BorderLayout.NORTH);
        otherNameInputField = new JTextField(20);
        otherNamePanel.add(otherNameInputField, BorderLayout.CENTER);
        formPanel.add(otherNamePanel);

        JPanel addOtherNamePanel = new JPanel(new BorderLayout());
        addOtherNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton addOtherNameButton = new JButton("Προσθήκη Άλλου Ονόματος");
        addOtherNamePanel.add(addOtherNameButton, BorderLayout.EAST);
        formPanel.add(addOtherNamePanel);

        otherNameField = new JTextArea(3, 20);
        otherNameField.setEditable(false);
        otherNameField.setLineWrap(true);
        otherNameField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(otherNameField));

        // Κουμπί Επεξεργασίας
        JPanel editOtherNamePanel = new JPanel(new BorderLayout());
        editOtherNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton editOtherNameButton = new JButton("Επεξεργασία Ονόματος");
        editOtherNameButton.setBackground(Color.GRAY);
        editOtherNameButton.setForeground(Color.WHITE);
        editOtherNamePanel.add(editOtherNameButton, BorderLayout.EAST);
        formPanel.add(editOtherNamePanel);

        // Κουμπί Ενημέρωσης
        JPanel updateOtherNamePanel = new JPanel(new BorderLayout());
        updateOtherNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton updateOtherNameButton = new JButton("Ενημέρωση Ονόματος");
        updateOtherNameButton.setBackground(Color.GRAY);
        updateOtherNameButton.setForeground(Color.WHITE);
        updateOtherNameButton.setVisible(false);
        updateOtherNamePanel.add(updateOtherNameButton, BorderLayout.EAST);
        formPanel.add(updateOtherNamePanel);

        // Κουμπί Διαγραφής
        JPanel removeOtherNamePanel = new JPanel(new BorderLayout());
        removeOtherNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeOtherNameButton = new JButton("Διαγραφή Ονόματος");
        removeOtherNameButton.setBackground(Color.DARK_GRAY);
        removeOtherNameButton.setForeground(Color.WHITE);
        removeOtherNamePanel.add(removeOtherNameButton, BorderLayout.EAST);
        formPanel.add(removeOtherNamePanel);

        addOtherNameButton.addActionListener(e -> {
            String otherName = otherNameInputField.getText().trim();
            if (otherName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Το πεδίο για το άλλο όνομα δεν μπορεί να είναι κενό.");
                return;
            }

            if (otherNameList.contains(otherName)) {
                JOptionPane.showMessageDialog(this, "Το όνομα υπάρχει ήδη.");
                return;
            }

            String idStoixeio = idStoixeioField.getText();

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }

                // Εισαγωγή του νέου ονόματος στη βάση
                String insertQuery = "INSERT INTO other_names (name, stoixeio_idStoixeio) VALUES (?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                    pstmt.setString(1, otherName);
                    pstmt.setString(2, idStoixeio);
                    pstmt.executeUpdate();

                    // Προσθήκη στη λίστα και στο TextArea
                    otherNameList.add(otherName);
                    otherNameField.append(otherName + ";" + "\n");
                    otherNameInputField.setText("");
                    JOptionPane.showMessageDialog(this, "Το όνομα προστέθηκε επιτυχώς στη βάση.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα κατά την προσθήκη στη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Επεξεργασία του επιλεγμένου ονόματος
        editOtherNameButton.addActionListener(e -> {
            if (otherNameList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν ονόματα για επεξεργασία.");
                return;
            }

            // Δημιουργία λίστας επιλογής για την επεξεργασία
            String[] namesArray = otherNameList.toArray(new String[0]);
            String selectedName = (String) JOptionPane.showInputDialog(
                    this,
                    "Επιλέξτε ένα όνομα για επεξεργασία:",
                    "Επεξεργασία Ονόματος",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    namesArray,
                    namesArray[0]
            );

            if (selectedName != null) {
                otherNameInputField.setText(selectedName);

                updateOtherNameButton.setVisible(true);
                addOtherNameButton.setEnabled(false);
                removeOtherNameButton.setEnabled(false);

                updateOtherNameButton.setEnabled(true);

                for (ActionListener al : updateOtherNameButton.getActionListeners()) {
                    updateOtherNameButton.removeActionListener(al);
                }

                updateOtherNameButton.addActionListener(updateEvent -> {
                    String updatedOtherName = otherNameInputField.getText().trim();

                    if (updatedOtherName.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Το όνομα δεν μπορεί να είναι κενό.");
                        return;
                    }

                    if (updatedOtherName.equals(selectedName)) {
                        JOptionPane.showMessageDialog(this, "Το όνομα δεν τροποποιήθηκε.");
                        return;
                    }

                    // Έλεγχος αν το όνομα υπάρχει ήδη στη λίστα
                    if (otherNameList.contains(updatedOtherName)) {
                        JOptionPane.showMessageDialog(this, "Το όνομα υπάρχει ήδη στη λίστα.");
                        return;
                    }

                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }

                        // Ανάκτηση του ID του επιλεγμένου ονόματος
                        String fetchIdQuery = "SELECT id FROM other_names WHERE name = ? AND stoixeio_idStoixeio = ?";
                        int otherNameId = -1;
                        try (PreparedStatement pstmt = conn.prepareStatement(fetchIdQuery)) {
                            pstmt.setString(1, selectedName);
                            pstmt.setString(2, idStoixeioField.getText());
                            try (ResultSet rs = pstmt.executeQuery()) {
                                if (rs.next()) {
                                    otherNameId = rs.getInt("id");
                                }
                            }
                        }

                        if (otherNameId == -1) {
                            JOptionPane.showMessageDialog(this, "Το επιλεγμένο όνομα δεν βρέθηκε στη βάση.");
                            return;
                        }

                        // Ενημέρωση του ονόματος με βάση το ID
                        String updateQuery = "UPDATE other_names SET name = ? WHERE id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                            pstmt.setString(1, updatedOtherName);
                            pstmt.setInt(2, otherNameId);
                            int rowsUpdated = pstmt.executeUpdate();

                            if (rowsUpdated > 0) {
                                JOptionPane.showMessageDialog(this, "Το όνομα ενημερώθηκε επιτυχώς.");

                                // Ενημέρωση της λίστας και του TextArea
                                otherNameList.remove(selectedName);
                                otherNameList.add(updatedOtherName);
                                updateOtherNameField();

                                addOtherNameButton.setEnabled(true);
                                removeOtherNameButton.setEnabled(true);
                                updateOtherNameButton.setEnabled(false);

                                otherNameInputField.setText("");

                            } else {
                                JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ενημέρωση της βάσης δεδομένων.");
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την επικοινωνία με τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        });

        // Διαγραφή του επιλεγμένου ονόματος από τη λίστα
        removeOtherNameButton.addActionListener(e -> {
            if (otherNameList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν ονόματα για διαγραφή.");
                return;
            }

            // Αν είναι το μόνο όνομα στη λίστα, απαγορεύεται η διαγραφή
            if (otherNameList.size() == 1) {
                JOptionPane.showMessageDialog(this, "Δεν μπορείτε να διαγράψετε το μόνο όνομα στη λίστα.");
                return;
            }

            // Δημιουργία λίστας επιλογής για τη διαγραφή
            String[] namesArray = otherNameList.toArray(new String[0]);
            String selectedName = (String) JOptionPane.showInputDialog(
                    this,
                    "Επιλέξτε ένα όνομα για διαγραφή:",
                    "Διαγραφή Ονόματος",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    namesArray,
                    namesArray[0]
            );

            if (selectedName != null) {
                String idStoixeio = idStoixeioField.getText().trim();

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    if (conn != null) {

                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }

                        String fetchIdQuery = "SELECT id FROM other_names WHERE name = ? AND stoixeio_idStoixeio = ?";
                        int otherNameId = -1;
                        try (PreparedStatement pstmt = conn.prepareStatement(fetchIdQuery)) {
                            pstmt.setString(1, selectedName);
                            pstmt.setString(2, idStoixeio);
                            try (ResultSet rs = pstmt.executeQuery()) {
                                if (rs.next()) {
                                    otherNameId = rs.getInt("id");
                                }
                            }
                        }

                        if (otherNameId == -1) {
                            JOptionPane.showMessageDialog(this, "Το επιλεγμένο όνομα δεν βρέθηκε στη βάση δεδομένων.");
                            return;
                        }

                        String deleteQuery = "DELETE FROM other_names WHERE id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                            pstmt.setInt(1, otherNameId);
                            int rowsAffected = pstmt.executeUpdate();

                            if (rowsAffected > 0) {
                                otherNameList.remove(selectedName);
                                updateOtherNameField();
                                JOptionPane.showMessageDialog(this, "Το όνομα διαγράφηκε επιτυχώς.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Αποτυχία διαγραφής. Το όνομα δεν βρέθηκε στη βάση.");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Αποτυχία σύνδεσης ή εκτέλεσης διαγραφής στη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Περιγραφή
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        descriptionPanel.add(new JLabel("Σύντομη Περιγραφή (έως 100 λέξεις):"), BorderLayout.NORTH);
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        formPanel.add(descriptionPanel);

        JPanel editDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editDescriptionButton = new JButton("Επεξεργασία Σύντομης Περιγραφής");
        editDescriptionButton.setBackground(Color.GRAY);
        editDescriptionButton.setForeground(Color.WHITE);
        editDescriptionPanel.add(editDescriptionButton);

        JPanel saveDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveDescriptionButton = new JButton("Αποθήκευση Σύντομης Περιγραφής");
        saveDescriptionButton.setEnabled(false);
        saveDescriptionPanel.add(saveDescriptionButton);

        JPanel cancelDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelDescriptionButton = new JButton("Ακύρωση");
        cancelDescriptionButton.setBackground(Color.lightGray);
        cancelDescriptionButton.setForeground(Color.WHITE);
        cancelDescriptionButton.setEnabled(false);
        cancelDescriptionPanel.add(cancelDescriptionButton);

        JPanel DescriptionPanel = new JPanel(new BorderLayout());
        DescriptionPanel.add(saveDescriptionPanel, BorderLayout.CENTER);
        DescriptionPanel.add(cancelDescriptionPanel, BorderLayout.EAST);
        DescriptionPanel.add(editDescriptionPanel, BorderLayout.WEST);

        formPanel.add(DescriptionPanel);

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editDescriptionButton.addActionListener(e -> {
            descriptionArea.setEditable(true);
            descriptionArea.requestFocus();
            saveDescriptionButton.setEnabled(true);
            cancelDescriptionButton.setEnabled(true);
            editDescriptionButton.setEnabled(false);
        });

        // Ενέργεια για το κουμπί "Αποθήκευση"
        saveDescriptionButton.addActionListener(e -> {
            descriptionArea.setEditable(false);
            saveDescriptionButton.setEnabled(false);
            cancelDescriptionButton.setEnabled(false);
            editDescriptionButton.setEnabled(true);

            int idStoixeio = Integer.parseInt(idStoixeioField.getText());

            String newDescription = descriptionArea.getText();

            if (newDescription.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Η σύντομη περιγραφή είναι υποχρεωτική.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Έλεγχος για το μέγιστο αριθμό λέξεων (έως 100)
            String[] words = newDescription.split("\\s+");
            if (words.length > 100) {
                JOptionPane.showMessageDialog(null, "Η σύντομη περιγραφή δεν πρέπει να υπερβαίνει τις 100 λέξεις.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Έλεγχος αν η νέα περιγραφή είναι ίδια με την παλιά
            if (newDescription.equals(initialDescriptionText)) {
                JOptionPane.showMessageDialog(null, "Η σύντομη περιγραφή δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String fullName= Login.UserSession.getFullName();

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String setSessionUser = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionUser)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }

                String updateQuery = "UPDATE stoixeio SET description = ? WHERE idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newDescription);
                pstmt.setInt(2, idStoixeio);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Η σύντομη περιγραφή αποθηκεύτηκε επιτυχώς στη βάση.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η ενημέρωση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            descriptionArea.setEditable(false);
            saveDescriptionButton.setEnabled(false);
            cancelDescriptionButton.setEnabled(false);
            editDescriptionButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelDescriptionButton.addActionListener(e -> {
            descriptionArea.setText(initialDescriptionText);
            descriptionArea.setEditable(false);
            saveDescriptionButton.setEnabled(false);
            cancelDescriptionButton.setEnabled(false);
            editDescriptionButton.setEnabled(true);
        });



        // Πεδίο
        fieldList = new  HashSet<>();
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        fieldComboBox = new JComboBox<>(new String[]{"Επιλέξτε Πεδίο",
                "προφορικές παραδόσεις και εκφράσεις",
                "επιτελεστικές τέχνες",
                "κοινωνικές πρακτικές-τελετουργίες-εορταστικές εκδηλώσεις",
                "γνώσεις και πρακτικές που αφορούν τη φύση και το σύμπαν",
                "τεχνογνωσία που συνδέεται με την παραδοσιακή χειροτεχνία",
                "άλλο"});
        fieldPanel.add(new JLabel("Επιλέξτε Πεδίο"), BorderLayout.NORTH);
        fieldPanel.add(fieldComboBox, BorderLayout.CENTER);
        formPanel.add(fieldPanel);

        // Αιτιολόγηση Πεδίων
        JPanel justificationPanel = new JPanel(new BorderLayout());
        justificationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        justificationPanel.add(new JLabel("Αιτιολόγηση Πεδίων:"), BorderLayout.NORTH);
        justification_of_fieldsField = new JTextArea(5, 20);
        justification_of_fieldsField.setLineWrap(true);
        justification_of_fieldsField.setWrapStyleWord(true);
        justificationPanel.add(new JScrollPane(justification_of_fieldsField), BorderLayout.CENTER);
        formPanel.add(justificationPanel);

        JPanel addFieldPanel = new JPanel(new BorderLayout());
        addFieldPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton addFieldButton = new JButton("Προσθήκη");
        addFieldPanel.add(addFieldButton, BorderLayout.EAST);
        formPanel.add(addFieldPanel);

        // Πεδίο εμφάνισης για πεδία ΑΠΚ
        fieldField = new JTextArea(3, 20);
        fieldField.setEditable(false);
        fieldField.setLineWrap(true);
        fieldField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(fieldField));

        // Κουμπί διαγραφής καταχώρησης από τη λίστα
        JPanel removeEntryPanel = new JPanel(new BorderLayout());
        removeEntryPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeEntryButton = new JButton("Διαγραφή Καταχώρησης");
        removeEntryButton.setBackground(Color.DARK_GRAY);
        removeEntryButton.setForeground(Color.WHITE);
        removeEntryPanel.add(removeEntryButton, BorderLayout.EAST);
        formPanel.add(removeEntryPanel);

        fieldList = new HashSet<>();
        selectedFields = new HashSet<>();

        // Κουμπί προσθήκης
        addFieldButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String field_apk = (String) fieldComboBox.getSelectedItem();
                String justification = justification_of_fieldsField.getText();
                int stoixeioId = Integer.parseInt(idStoixeioField.getText().trim());

                if (field_apk != null && !field_apk.isEmpty() && !"Επιλέξτε Πεδίο".equals(field_apk)) {
                    // Έλεγχος αν το πεδίο είναι "άλλο" και αν το justification είναι κενό
                    if ("άλλο".equals(field_apk) && justification.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Πρέπει να συμπληρώσετε το πεδίο 'Αιτιολόγηση' όταν επιλέξετε 'άλλο'.",
                                "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        if (conn != null) {
                            String setSessionFullName = "SET @full_name = ?";
                            try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                                setSessionStmt.setString(1, Login.UserSession.getFullName());
                                setSessionStmt.executeUpdate();
                            }

                            int fieldApkId = 0;
                            String findFieldQuery = "SELECT idfields_apk FROM fields_apk WHERE field_apk = ?";
                            try (PreparedStatement findStmt = conn.prepareStatement(findFieldQuery)) {
                                findStmt.setString(1, field_apk);
                                try (ResultSet rs = findStmt.executeQuery()) {
                                    if (rs.next()) {
                                        fieldApkId = rs.getInt("idfields_apk");
                                    }
                                }
                            }

                            // Αν το πεδίο δεν υπάρχει, προσθήκη στον πίνακα `fields_apk`
                            if (fieldApkId == 0) {
                                String insertFieldQuery = "INSERT INTO fields_apk (field_apk) VALUES (?)";
                                try (PreparedStatement insertFieldStmt = conn.prepareStatement(insertFieldQuery, Statement.RETURN_GENERATED_KEYS)) {
                                    insertFieldStmt.setString(1, field_apk);
                                    insertFieldStmt.executeUpdate();

                                    try (ResultSet generatedKeys = insertFieldStmt.getGeneratedKeys()) {
                                        if (generatedKeys.next()) {
                                            fieldApkId = generatedKeys.getInt(1);
                                        } else {
                                            throw new SQLException("Απέτυχε η εισαγωγή του πεδίου στον πίνακα fields_apk.");
                                        }
                                    }
                                }
                            }

                            String checkExistingQuery = "SELECT justification FROM stoixeio_fields_apk WHERE stoixeio_idStoixeio = ? AND fields_apk_idfields_apk = ?";
                            try (PreparedStatement checkStmt = conn.prepareStatement(checkExistingQuery)) {
                                checkStmt.setInt(1, stoixeioId);
                                checkStmt.setInt(2, fieldApkId);

                                try (ResultSet rs = checkStmt.executeQuery()) {
                                    if (rs.next()) {
                                        String existingJustification = rs.getString("justification");
                                        if (existingJustification == null) existingJustification = "";

                                        String message = "Το πεδίο υπάρχει ήδη με αιτιολόγηση:\n\n\"" + existingJustification + "\"\n\nΘέλετε να το ενημερώσετε;";
                                        int response = JOptionPane.showConfirmDialog(null, message, "Ενημέρωση Αιτιολόγησης", JOptionPane.YES_NO_OPTION);

                                        if (response == JOptionPane.YES_OPTION) {
                                            // Ενημέρωση της αιτιολόγησης
                                            String updateQuery = "UPDATE stoixeio_fields_apk SET justification = ? WHERE stoixeio_idStoixeio = ? AND fields_apk_idfields_apk = ?";
                                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                                updateStmt.setString(1, justification);
                                                updateStmt.setInt(2, stoixeioId);
                                                updateStmt.setInt(3, fieldApkId);

                                                int rowsAffected = updateStmt.executeUpdate();
                                                if (rowsAffected > 0) {
                                                    JOptionPane.showMessageDialog(null, "Η αιτιολόγηση ενημερώθηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);

                                                    fieldList.removeIf(entry -> entry.contains(field_apk));

                                                    StringBuilder entryBuilder = new StringBuilder(field_apk);
                                                    if (!justification.isEmpty()) {
                                                        entryBuilder.append(": ").append(justification);
                                                    }

                                                    fieldList.add(entryBuilder.toString());

                                                    fieldField.setText(String.join(";\n", fieldList));

                                                    fieldComboBox.setSelectedIndex(0);
                                                    justification_of_fieldsField.setText("");

                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Αποτυχία ενημέρωσης της αιτιολόγησης.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                                }
                                            }
                                        }
                                        return;
                                    }
                                }
                            }

                            String insertQuery = "INSERT INTO stoixeio_fields_apk (stoixeio_idStoixeio, fields_apk_idfields_apk, justification) VALUES (?, ?, ?)";
                            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                                pstmt.setInt(1, stoixeioId);
                                pstmt.setInt(2, fieldApkId);
                                pstmt.setString(3, justification);

                                int rowsAffected = pstmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(null, "Η καταχώρηση προστέθηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);

                                    StringBuilder entryBuilder = new StringBuilder(field_apk);
                                    if (!justification.isEmpty()) {
                                        entryBuilder.append(": ").append(justification);
                                    }

                                    String entry = entryBuilder.toString();
                                    fieldList.add(entry);

                                    fieldField.setText(String.join(";\n", fieldList) + ";");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Αποτυχία προσθήκης στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Σφάλμα σύνδεσης με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    }

                    selectedFields.add(field_apk);

                    fieldComboBox.setSelectedIndex(0);
                    justification_of_fieldsField.setText("");
                }
            }
        });



        removeEntryButton.addActionListener(e -> {
            if (fieldList.size() == 1) {
                JOptionPane.showMessageDialog(this, "Δεν μπορείτε να διαγράψετε την τελευταία καταχώρηση.");
                return;
            }

            String[] entriesArray = fieldList.toArray(new String[0]);
            String selectedEntry = (String) JOptionPane.showInputDialog(
                    this,
                    "Επιλέξτε καταχώρηση για διαγραφή:",
                    "Διαγραφή Καταχώρησης",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    entriesArray,
                    entriesArray[0]);

            if (selectedEntry == null) {
                return;
            }

            String[] entryParts = selectedEntry.split(": ");
            String fieldApk = entryParts[0];
            String justification = entryParts.length > 1 ? entryParts[1] : "";

            String idStoixeio = idStoixeioField.getText().trim();

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String findFieldIdQuery = "SELECT idfields_apk FROM fields_apk WHERE field_apk = ?";
                int fieldApkId;

                try (PreparedStatement stmt = conn.prepareStatement(findFieldIdQuery)) {
                    stmt.setString(1, fieldApk);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            fieldApkId = rs.getInt("idfields_apk");
                        } else {
                            JOptionPane.showMessageDialog(this, "Δεν βρέθηκε το πεδίο: " + fieldApk);
                            return;
                        }
                    }
                }

                String deleteQuery = "DELETE FROM stoixeio_fields_apk WHERE stoixeio_idStoixeio = ? AND fields_apk_idfields_apk = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
                    stmt.setInt(1, Integer.parseInt(idStoixeio));
                    stmt.setInt(2, fieldApkId);

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Η καταχώρηση διαγράφηκε με επιτυχία.");
                        fieldList.remove(selectedEntry);
                        fieldField.setText("");
                        for (String fieldEntry : fieldList) {
                            fieldField.append(fieldEntry + ";" + "\n");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Δεν βρέθηκε η καταχώρηση για διαγραφή.");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα κατά τη διαγραφή: " + ex.getMessage());
            }

        });

        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        locationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        locationPanel.add(new JLabel("Περιοχή που απαντάται"));
        formPanel.add(locationPanel);

        // Περιγραφή Τοποθεσίας
        JPanel descriptionLocationPanel = new JPanel(new BorderLayout());
        descriptionLocationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        descriptionLocationPanel.add(new JLabel("Περιγραφή Τοποθεσίας (έως 100 λέξεις):"), BorderLayout.NORTH);
        locationDescriptionArea = new JTextArea(3, 20);
        locationDescriptionArea.setLineWrap(true);
        locationDescriptionArea.setWrapStyleWord(true);
        locationDescriptionArea.setEditable(false);
        descriptionLocationPanel.add(new JScrollPane(locationDescriptionArea), BorderLayout.CENTER);
        formPanel.add(descriptionLocationPanel);

        JPanel editLocationDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editLocationDescriptionButton = new JButton("Επεξεργασία Περιγραφής Τοποθεσίας");
        editLocationDescriptionButton.setBackground(Color.GRAY);
        editLocationDescriptionButton.setForeground(Color.WHITE);
        editLocationDescriptionPanel.add(editLocationDescriptionButton);

        JPanel saveLocationDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveLocationDescriptionButton = new JButton("Αποθήκευση Περιγραφής Τοποθεσίας");
        saveLocationDescriptionPanel.add(saveLocationDescriptionButton);

        JPanel cancelLocationDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelLocationDescriptionButton = new JButton("Ακύρωση");
        cancelLocationDescriptionButton.setBackground(Color.lightGray);
        cancelLocationDescriptionButton.setForeground(Color.WHITE);
        cancelLocationDescriptionPanel.add(cancelLocationDescriptionButton);

        JPanel LocationDescriptionPanel = new JPanel(new BorderLayout());
        LocationDescriptionPanel.add(saveLocationDescriptionPanel, BorderLayout.CENTER);
        LocationDescriptionPanel.add(cancelLocationDescriptionPanel, BorderLayout.EAST);
        LocationDescriptionPanel.add(editLocationDescriptionPanel, BorderLayout.WEST);

        formPanel.add(LocationDescriptionPanel);


        // Ενέργεια για το κουμπί "Επεξεργασία"
        editLocationDescriptionButton.addActionListener(e -> {
            locationDescriptionArea.setEditable(true);
            locationDescriptionArea.requestFocus();
            saveLocationDescriptionButton.setEnabled(true);
            cancelLocationDescriptionButton.setEnabled(true);
            editLocationDescriptionButton.setEnabled(false);
        });

        // Ενέργεια για το κουμπί "Αποθήκευση"
        saveLocationDescriptionButton.addActionListener(e -> {
            locationDescriptionArea.setEditable(false);
            saveLocationDescriptionButton.setEnabled(false);
            cancelLocationDescriptionButton.setEnabled(false);
            editLocationDescriptionButton.setEnabled(true);

            String newLocationDescription = locationDescriptionArea.getText();

            if (newLocationDescription.equals(initialLocationDescriptionText)) {
                JOptionPane.showMessageDialog(null, "Η περιγραφή τοποθεσίας δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (newLocationDescription.trim().isEmpty() && (locationList == null || locationList.isEmpty())) {
                JOptionPane.showMessageDialog(null, "Η περιγραφή τοποθεσίας δεν μπορεί να είναι κενή αν η λίστα τοποθεσιών είναι κενή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Έλεγχος για το μέγιστο αριθμό λέξεων (έως 100)
            String[] words = newLocationDescription.split("\\s+");
            if (words.length > 100) {
                JOptionPane.showMessageDialog(null, "Η περιγραφή τοποθεσίας δεν πρέπει να υπερβαίνει τις 100 λέξεις.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                int stoixeioId = Integer.parseInt(idStoixeioField.getText());

                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String selectQuery = "SELECT id_location FROM location WHERE stoixeio_idStoixeio = ?";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                    selectStmt.setInt(1, stoixeioId);

                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            int idLocation = rs.getInt("id_location");

                            if (newLocationDescription.trim().isEmpty()) {
                                String updateQuery = "UPDATE location SET locationDescription = NULL WHERE id_location = ?";
                                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                    updateStmt.setInt(1, idLocation);
                                    int rowsUpdated = updateStmt.executeUpdate();
                                    if (rowsUpdated > 0) {
                                        JOptionPane.showMessageDialog(null, "Η περιγραφή τοποθεσίας διαγράφηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Η διαγραφή απέτυχε.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            } else {
                                String updateQuery = "UPDATE location SET locationDescription = ? WHERE id_location = ?";
                                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                    updateStmt.setString(1, newLocationDescription);
                                    updateStmt.setInt(2, idLocation);
                                    int rowsUpdated = updateStmt.executeUpdate();
                                    if (rowsUpdated > 0) {
                                        JOptionPane.showMessageDialog(null, "Η περιγραφή τοποθεσίας ενημερώθηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Η ενημέρωση απέτυχε.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        } else {
                            String insertQuery = "INSERT INTO location (stoixeio_idStoixeio, locationDescription) VALUES (?, ?)";
                            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                                insertStmt.setInt(1, stoixeioId);
                                insertStmt.setString(2, newLocationDescription.trim().isEmpty() ? null : newLocationDescription);
                                int rowsInserted = insertStmt.executeUpdate();
                                if (rowsInserted > 0) {
                                    JOptionPane.showMessageDialog(null, "Η νέα περιγραφή τοποθεσίας προστέθηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Η εισαγωγή απέτυχε.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την επικοινωνία με τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }

            locationDescriptionArea.setEditable(false);
            saveLocationDescriptionButton.setEnabled(false);
            cancelLocationDescriptionButton.setEnabled(false);
            editLocationDescriptionButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelLocationDescriptionButton.addActionListener(e -> {
            locationDescriptionArea.setText(initialLocationDescriptionText);
            locationDescriptionArea.setEditable(false);
            saveLocationDescriptionButton.setEnabled(false);
            cancelLocationDescriptionButton.setEnabled(false);
            editLocationDescriptionButton.setEnabled(true);
        });


        // Πεδία Καλικράτη
        JPanel kalikratisPanel = new JPanel(new GridLayout(6, 2));
        kalikratisPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Πεδίο Αποκεντρωμένης Διοίκησης
        kalikratisPanel.add(new JLabel("Αποκεντρωμένη Διοίκηση:"));
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Επιλέξτε Αποκεντρωμένη Διοίκηση");

        // Σύνδεση με τη βάση δεδομένων
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM decentralized_administration")) {

            // Φόρτωση δεδομένων από τη βάση
            while (rs.next()) {
                model.addElement(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά τη φόρτωση των δεδομένων: " + e.getMessage());
        }

        decentralizedAdminComboBox = new JComboBox<>(model);

        kalikratisPanel.add(decentralizedAdminComboBox);

        // Πεδίο Περιφέρειας
        kalikratisPanel.add(new JLabel("Περιφέρεια:"));
        regionComboBox = new JComboBox<>(new String[]{"Επιλέξτε Περιφέρεια"});
        kalikratisPanel.add(regionComboBox);

        // ActionListener για δυναμική φόρτωση περιφερειών
        decentralizedAdminComboBox.addActionListener(e -> {
            DefaultComboBoxModel<String> regionModel = new DefaultComboBoxModel<>();
            regionModel.addElement("Επιλέξτε Περιφέρεια");

            String selectedAdmin = (String) decentralizedAdminComboBox.getSelectedItem();
            if (selectedAdmin != null && !selectedAdmin.equals("Επιλέξτε Αποκεντρωμένη Διοίκηση")) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(
                             "SELECT r.name FROM region r " +
                                     "JOIN decentralized_administration d " +
                                     "ON r.decentralized_administration_id = d.id " +
                                     "WHERE d.name = ?"
                     )) {
                    stmt.setString(1, selectedAdmin);
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        regionModel.addElement(rs.getString("name"));
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά τη φόρτωση των περιφερειών: " + ex.getMessage());
                }
            }

            regionComboBox.setModel(regionModel);
        });


        // Πεδίο Περιφερειακής Ενότητας
        kalikratisPanel.add(new JLabel("Επιλέξτε Περιφερειακή Ενότητα:"));
        regionalUnitComboBox = new JComboBox<>(new String[]{"Επιλέξτε Περιφερειακή Ενότητα"});
        kalikratisPanel.add(regionalUnitComboBox);

        // Προσθήκη ActionListener για το regionComboBox
        regionComboBox.addActionListener(e -> {
            // Καθαρισμός επιλογών του regionalUnitComboBox
            DefaultComboBoxModel<String> regionalUnitModel = new DefaultComboBoxModel<>();
            regionalUnitModel.addElement("Επιλέξτε Περιφερειακή Ενότητα");

            // Λήψη της επιλεγμένης Περιφέρειας
            String selectedRegion = (String) regionComboBox.getSelectedItem();
            if (selectedRegion != null && !selectedRegion.equals("Επιλέξτε Περιφέρεια")) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(
                             "SELECT ru.name FROM regional_units ru " +
                                     "JOIN region r ON ru.region_id = r.id " +
                                     "WHERE r.name = ?"
                     )) {
                    stmt.setString(1, selectedRegion);
                    ResultSet rs = stmt.executeQuery();

                    // Προσθήκη Περιφερειακών Ενοτήτων στο regionalUnitComboBox
                    while (rs.next()) {
                        regionalUnitModel.addElement(rs.getString("name"));
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά τη φόρτωση των Περιφερειακών Ενοτήτων: " + ex.getMessage());
                }
            }

            regionalUnitComboBox.setModel(regionalUnitModel);
        });



        // Πεδίο Δήμου
        kalikratisPanel.add(new JLabel("Δήμος:"));
        municipalityComboBox = new JComboBox<>(new String[]{"Επιλέξτε Δήμο"});
        kalikratisPanel.add(municipalityComboBox);

        // Προσθήκη ActionListener για το regionalUnitComboBox
        regionalUnitComboBox.addActionListener(e -> {
            // Καθαρισμός επιλογών του municipalityComboBox
            DefaultComboBoxModel<String> municipalityModel = new DefaultComboBoxModel<>();
            municipalityModel.addElement("Επιλέξτε Δήμο");

            // Λήψη της επιλεγμένης Περιφερειακής Ενότητας
            String selectedRegionalUnit = (String) regionalUnitComboBox.getSelectedItem();
            if (selectedRegionalUnit != null && !selectedRegionalUnit.equals("Επιλέξτε Περιφερειακή Ενότητα")) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(
                             "SELECT m.name FROM municipalities m " +
                                     "JOIN regional_units ru ON m.regional_units_id = ru.id " +
                                     "WHERE ru.name = ?"
                     )) {
                    stmt.setString(1, selectedRegionalUnit);
                    ResultSet rs = stmt.executeQuery();

                    // Προσθήκη Δήμων στο municipalityComboBox
                    while (rs.next()) {
                        municipalityModel.addElement(rs.getString("name"));
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά τη φόρτωση των Δήμων: " + ex.getMessage());
                }
            }

            municipalityComboBox.setModel(municipalityModel);
        });

        // Πεδίο Δημοτικής Ενότητας
        kalikratisPanel.add(new JLabel("Δημοτική Ενότητα:"));
        municipalUnitComboBox = new JComboBox<>(new String[]{"Επιλέξτε Δημοτική Ενότητα"});
        kalikratisPanel.add(municipalUnitComboBox);

        municipalityComboBox.addActionListener(e -> {
            DefaultComboBoxModel<String> municipalUnitModel = new DefaultComboBoxModel<>();
            municipalUnitModel.addElement("Επιλέξτε Δημοτική Ενότητα");

            // Λήψη της επιλεγμένης Δημοτικής Ενότητας
            String selectedMunicipality = (String) municipalityComboBox.getSelectedItem();
            if (selectedMunicipality != null && !selectedMunicipality.equals("Επιλέξτε Δήμο")) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(
                             "SELECT mu.name FROM municipal_units mu " +
                                     "JOIN municipalities m ON mu.municipalities_id = m.id " +
                                     "WHERE m.name = ?"
                     )) {
                    stmt.setString(1, selectedMunicipality);
                    ResultSet rs = stmt.executeQuery();

                    // Προσθήκη Δημοτικών Ενοτήτων στο municipalUnitComboBox
                    while (rs.next()) {
                        municipalUnitModel.addElement(rs.getString("name"));
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά τη φόρτωση των Δημοτικών Ενοτήτων: " + ex.getMessage());
                }
            }

            municipalUnitComboBox.setModel(municipalUnitModel);
        });

        // Πεδίο Κοινότητας
        kalikratisPanel.add(new JLabel("Κοινότητα:"));
        communitiesComboBox = new JComboBox<>(new String[]{"Επιλέξτε Κοινότητα"});
        kalikratisPanel.add(communitiesComboBox);

        // Προσθήκη ActionListener για το municipalUnitComboBox
        municipalUnitComboBox.addActionListener(e -> {
            DefaultComboBoxModel<String> communitiesModel = new DefaultComboBoxModel<>();
            communitiesModel.addElement("Επιλέξτε Κοινότητα");

            // Λήψη της επιλεγμένης Δημοτικής Ενότητας
            String selectedMunicipalUnit = (String) municipalUnitComboBox.getSelectedItem();
            if (selectedMunicipalUnit != null && !selectedMunicipalUnit.equals("Επιλέξτε Δημοτική Ενότητα")) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement stmt = conn.prepareStatement(
                             "SELECT c.name FROM communities c " +
                                     "JOIN municipal_units mu ON c.municipal_units_id = mu.id " +
                                     "WHERE mu.name = ?"
                     )) {
                    stmt.setString(1, selectedMunicipalUnit);
                    ResultSet rs = stmt.executeQuery();

                    // Προσθήκη Κοινοτήτων στο communitiesComboBox
                    while (rs.next()) {
                        communitiesModel.addElement(rs.getString("name"));
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά τη φόρτωση των Κοινοτήτων: " + ex.getMessage());
                }
            }

            communitiesComboBox.setModel(communitiesModel);
        });
        formPanel.add(kalikratisPanel);

        locationList = new HashSet<>();

        locationField = new JTextArea(3, 20);
        locationField.setEditable(false);
        locationField.setLineWrap(true);
        locationField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(locationField));

        // Κουμπί για προσθήκη
        JPanel addLocationPanel = new JPanel(new BorderLayout());
        addLocationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton addLocationButton = new JButton("Προσθήκη Τοποθεσίας");
        addLocationPanel.add(addLocationButton, BorderLayout.EAST);
        formPanel.add(addLocationPanel);

        // Προσθήκη κουμπιού επεξεργασίας
        JPanel editlocPanel = new JPanel(new BorderLayout());
        editlocPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton editlocButton = new JButton("Επεξεργασία Τoποθεσίας");
        editlocButton.setBackground(Color.GRAY);
        editlocButton.setForeground(Color.WHITE);
        editlocPanel.add(editlocButton, BorderLayout.EAST);
        formPanel.add(editlocPanel);

        // Προσθήκη κουμπιού ενημέρωσης
        JPanel updateLocPanel = new JPanel(new BorderLayout());
        updateLocPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton updateLocButton = new JButton("Ενημέρωση Τοποθεσίας");
        updateLocButton.setBackground(Color.GRAY);
        updateLocButton.setForeground(Color.WHITE);
        updateLocButton.setVisible(false);
        updateLocPanel.add(updateLocButton, BorderLayout.EAST);
        formPanel.add(updateLocPanel);

        // Κουμπί για διαγραφή περιοχής από τη λίστα
        JPanel removeLocationPanel = new JPanel(new BorderLayout());
        removeLocationPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton removeLocationButton = new JButton("Διαγραφή Τοποθεσίας");
        removeLocationButton.setBackground(Color.DARK_GRAY);
        removeLocationButton.setForeground(Color.WHITE);
        removeLocationPanel.add(removeLocationButton, BorderLayout.EAST);
        formPanel.add(removeLocationPanel);

        //Προσθήκη περιοχών Καλλικράτη με έλεγχο
        addLocationButton.addActionListener(e -> {
            String decentralizedAdmin = (String) decentralizedAdminComboBox.getSelectedItem();
            String region = (String) regionComboBox.getSelectedItem();
            String regionalUnit = (String) regionalUnitComboBox.getSelectedItem();
            String municipality = (String) municipalityComboBox.getSelectedItem();
            String municipalUnit = (String) municipalUnitComboBox.getSelectedItem();
            String communities = (String) communitiesComboBox.getSelectedItem();

            // Λήψη περιγραφής τοποθεσίας
            String locationDescription = locationDescriptionArea.getText().trim();

            // Έλεγχος αν όλα τα πεδία είναι κενά
            if ((decentralizedAdmin.equals("Επιλέξτε Aποκεντρωμένη Διοίκηση") || decentralizedAdmin.isEmpty()) &&
                    (region.equals("Επιλέξτε Περιφέρεια") || region.isEmpty()) &&
                    (regionalUnit.equals("Επιλέξτε Περιφερειακή Ενότητα") || regionalUnit.isEmpty()) &&
                    (municipality.equals("Επιλέξτε Δήμο") || municipality.isEmpty()) &&
                    (municipalUnit.equals("Επιλέξτε Δημοτική Ενότητα") || municipalUnit.isEmpty()) &&
                    (communities.equals("Επιλέξτε Κοινότητα") || communities.isEmpty())&&
                    locationDescription.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Πρέπει να συμπληρώσετε τουλάχιστον ένα από τα πεδία");
                return;
            }
            StringBuilder locationEntry = new StringBuilder();

            if (!decentralizedAdmin.equals("Επιλέξτε Aποκεντρωμένη Διοίκηση")) {
                locationEntry.append(decentralizedAdmin);
            }
            if (!region.equals("Επιλέξτε Περιφέρεια")) {
                if (locationEntry.length() > 0) locationEntry.append(", ");
                locationEntry.append(region);
            }
            if (!regionalUnit.equals("Επιλέξτε Περιφερειακή Ενότητα")) {
                if (locationEntry.length() > 0) locationEntry.append(", ");
                locationEntry.append(regionalUnit);
            }
            if (!municipality.equals("Επιλέξτε Δήμο")) {
                if (locationEntry.length() > 0) locationEntry.append(", ");
                locationEntry.append(municipality);
            }
            if (!municipalUnit.equals("Επιλέξτε Δημοτική Ενότητα")) {
                if (locationEntry.length() > 0) locationEntry.append(", ");
                locationEntry.append(municipalUnit);
            }
            if (!communities.equals("Επιλέξτε Κοινότητα")) {
                if (locationEntry.length() > 0) locationEntry.append(", ");
                locationEntry.append(communities);
            }

            String locationString = locationEntry.toString();
            if (locationList.contains(locationString)) {
                JOptionPane.showMessageDialog(this, "Η περιοχή έχει ήδη προστεθεί.");
                return;
            }

            Integer decentralizedAdminID = null;
            Integer regionID = null;
            Integer regionalUnitID = null;
            Integer municipalityID = null;
            Integer municipalUnitID = null;
            Integer communityID = null;

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);)
            {
                int stoixeioId = Integer.parseInt(idStoixeioField.getText());
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                if (!decentralizedAdmin.isEmpty()) {
                    try (PreparedStatement stmtDecentralizedAdmin = conn.prepareStatement(
                            "SELECT id FROM decentralized_administration WHERE name = ?")) {
                        stmtDecentralizedAdmin.setString(1, decentralizedAdmin);
                        try (ResultSet rs = stmtDecentralizedAdmin.executeQuery()) {
                            if (rs.next()) {
                                decentralizedAdminID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της αποκεντρωμένης διοίκησης: " + ex.getMessage());
                    }
                }

                if (!region.isEmpty() && decentralizedAdminID != null) {
                    try (PreparedStatement stmtRegion = conn.prepareStatement(
                            "SELECT id FROM region WHERE name = ? AND decentralized_administration_id = ?")) {
                        stmtRegion.setString(1, region);
                        stmtRegion.setInt(2, decentralizedAdminID);
                        try (ResultSet rs = stmtRegion.executeQuery()) {
                            if (rs.next()) {
                                regionID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της περιοχής: " + ex.getMessage());
                    }
                }

                if (!regionalUnit.isEmpty() && regionID != null) {
                    try (PreparedStatement stmtRegionalUnit = conn.prepareStatement(
                            "SELECT id FROM regional_units WHERE name = ? AND region_id = ?")) {
                        stmtRegionalUnit.setString(1, regionalUnit);
                        stmtRegionalUnit.setInt(2, regionID);
                        try (ResultSet rs = stmtRegionalUnit.executeQuery()) {
                            if (rs.next()) {
                                regionalUnitID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της περιφερειακής ενότητας: " + ex.getMessage());
                    }
                }

                if (!municipality.isEmpty() && regionalUnitID != null) {
                    try (PreparedStatement stmtMunicipality = conn.prepareStatement(
                            "SELECT id FROM municipalities WHERE name = ? AND regional_units_id = ?")) {
                        stmtMunicipality.setString(1, municipality);
                        stmtMunicipality.setInt(2, regionalUnitID);
                        try (ResultSet rs = stmtMunicipality.executeQuery()) {
                            if (rs.next()) {
                                municipalityID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση του δήμου: " + ex.getMessage());
                    }
                }

                if (!municipalUnit.isEmpty() && municipalityID != null) {
                    try (PreparedStatement stmtMunicipalUnit = conn.prepareStatement(
                            "SELECT id FROM municipal_units WHERE name = ? AND municipalities_id = ?")) {
                        stmtMunicipalUnit.setString(1, municipalUnit);
                        stmtMunicipalUnit.setInt(2, municipalityID);
                        try (ResultSet rs = stmtMunicipalUnit.executeQuery()) {
                            if (rs.next()) {
                                municipalUnitID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της δημοτικής ενότητας: " + ex.getMessage());
                    }
                }

                if (!communities.isEmpty() && municipalUnitID != null) {
                    try (PreparedStatement stmtCommunity = conn.prepareStatement(
                            "SELECT id FROM communities WHERE name = ? AND municipal_units_id = ?")) {
                        stmtCommunity.setString(1, communities);
                        stmtCommunity.setInt(2, municipalUnitID);
                        try (ResultSet rs = stmtCommunity.executeQuery()) {
                            if (rs.next()) {
                                communityID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της κοινότητας: " + ex.getMessage());
                    }
                }

                try (PreparedStatement selectStmt = conn.prepareStatement(
                        "SELECT id_location FROM location WHERE stoixeio_idStoixeio = ?")) {

                    selectStmt.setInt(1, stoixeioId);

                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            int idLocation = rs.getInt("id_location");

                            try (PreparedStatement stmtLocation = conn.prepareStatement(
                                    "INSERT INTO kalikratis (decentralized_administration_id, region_id, regional_unit_id, municipality_id, municipal_unit_id, community_id, location_id_location) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                                stmtLocation.setObject(1, decentralizedAdminID);
                                stmtLocation.setObject(2, regionID);
                                stmtLocation.setObject(3, regionalUnitID);
                                stmtLocation.setObject(4, municipalityID);
                                stmtLocation.setObject(5, municipalUnitID);
                                stmtLocation.setObject(6, communityID);
                                stmtLocation.setObject(7, idLocation);

                                stmtLocation.executeUpdate();

                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(this, "Σφάλμα κατά την εισαγωγή στην τοποθεσία: " + ex.getMessage());
                            }
                        }
                    }

                }

                locationList.add(locationString);
                locationField.append(locationEntry + ";\n");

                decentralizedAdminComboBox.setSelectedIndex(0);
                regionComboBox.setSelectedIndex(0);
                regionalUnitComboBox.setSelectedIndex(0);
                municipalityComboBox.setSelectedIndex(0);
                municipalUnitComboBox.setSelectedIndex(0);
                communitiesComboBox.setSelectedIndex(0);


            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα κατά την εισαγωγή στη βάση δεδομένων: " + ex.getMessage());
            }

        });

        editlocButton.addActionListener(e -> {
            String[] entriesArray = locationList.toArray(new String[0]);
            String selectedEntry = (String) JOptionPane.showInputDialog(
                    this,
                    "Επιλέξτε περιοχή για επεξεργασία:",
                    "Επεξεργασία Περιοχής",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    entriesArray,
                    entriesArray[0]);

            if (selectedEntry == null) {
                return;
            }

            String[] parts = selectedEntry.split(", ");
            String decentralizedAdmin = (parts.length > 0) ? parts[0] : "";
            String region = (parts.length > 1) ? parts[1] : "";
            String regionalUnit = (parts.length > 2) ? parts[2] : "";
            String municipality = (parts.length > 3) ? parts[3] : "";
            String municipalUnit = (parts.length > 4) ? parts[4] : "";
            String communities = (parts.length > 5) ? parts[5] : "";

            if (decentralizedAdmin.isEmpty()) {
                decentralizedAdminComboBox.setSelectedItem("Επιλέξτε Aποκεντρωμένη Διοίκηση");
            } else {
                decentralizedAdminComboBox.setSelectedItem(decentralizedAdmin);
            }

            if (region.isEmpty()) {
                regionComboBox.setSelectedItem("Επιλέξτε Περιφέρεια");
            } else {
                regionComboBox.setSelectedItem(region);
            }

            if (regionalUnit.isEmpty()) {
                regionalUnitComboBox.setSelectedItem("Επιλέξτε Περιφερειακή Ενότητα");
            } else {
                regionalUnitComboBox.setSelectedItem(regionalUnit);
            }

            if (municipality.isEmpty()) {
                municipalityComboBox.setSelectedItem("Επιλέξτε Δήμο");
            } else {
                municipalityComboBox.setSelectedItem(municipality);
            }

            if (municipalUnit.isEmpty()) {
                municipalUnitComboBox.setSelectedItem("Επιλέξτε Δημοτική Ενότητα");
            } else {
                municipalUnitComboBox.setSelectedItem(municipalUnit);
            }

            if (communities.isEmpty()) {
                communitiesComboBox.setSelectedItem("Επιλέξτε Κοινότητα");
            } else {
                communitiesComboBox.setSelectedItem(communities);
            }

            updateLocButton.setVisible(true);
            addLocationButton.setEnabled(false);
            removeLocationButton.setEnabled(false);

            updateLocButton.setEnabled(true);
            updateLocButton.addActionListener(updateEvent -> {
                String newDecentralizedAdmin = decentralizedAdminComboBox.getSelectedItem().toString();
                String newRegion = regionComboBox.getSelectedItem().toString();
                String newRegionalUnit = regionalUnitComboBox.getSelectedItem().toString();
                String newMunicipality = municipalityComboBox.getSelectedItem().toString();
                String newMunicipalUnit = municipalUnitComboBox.getSelectedItem().toString();
                String newCommunities = communitiesComboBox.getSelectedItem().toString();

                StringBuilder updatedEntryBuilder = new StringBuilder();

                if (!newDecentralizedAdmin.isEmpty() && !newDecentralizedAdmin.equals("Επιλέξτε Aποκεντρωμένη Διοίκηση")) {
                    updatedEntryBuilder.append(newDecentralizedAdmin);
                }

                if (!newRegion.isEmpty() && !newRegion.equals("Επιλέξτε Περιφέρεια")) {
                    if (updatedEntryBuilder.length() > 0) {
                        updatedEntryBuilder.append(", ");
                    }
                    updatedEntryBuilder.append(newRegion);
                }

                if (!newRegionalUnit.isEmpty() && !newRegionalUnit.equals("Επιλέξτε Περιφερειακή Ενότητα")) {
                    if (updatedEntryBuilder.length() > 0) {
                        updatedEntryBuilder.append(", ");
                    }
                    updatedEntryBuilder.append(newRegionalUnit);
                }

                if (!newMunicipality.isEmpty() && !newMunicipality.equals("Επιλέξτε Δήμο")) {
                    if (updatedEntryBuilder.length() > 0) {
                        updatedEntryBuilder.append(", ");
                    }
                    updatedEntryBuilder.append(newMunicipality);
                }

                if (!newMunicipalUnit.isEmpty() && !newMunicipalUnit.equals("Επιλέξτε Δημοτική Ενότητα")) {
                    if (updatedEntryBuilder.length() > 0) {
                        updatedEntryBuilder.append(", ");
                    }
                    updatedEntryBuilder.append(newMunicipalUnit);
                }

                if (!newCommunities.isEmpty() && !newCommunities.equals("Επιλέξτε Κοινότητα")) {
                    if (updatedEntryBuilder.length() > 0) {
                        updatedEntryBuilder.append(", ");
                    }
                    updatedEntryBuilder.append(newCommunities);
                }

                String updatedEntry = updatedEntryBuilder.toString();

                if (selectedEntry.equals(updatedEntry)) {
                    JOptionPane.showMessageDialog(this, "Δεν έγινε καμία αλλαγή.", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                locationList.remove(selectedEntry);
                locationList.add(updatedEntry);
                updateLocationField();


                Integer decentralizedAdminID = null;
                Integer regionID = null;
                Integer regionalUnitID = null;
                Integer municipalityID = null;
                Integer municipalUnitID = null;
                Integer communityID = null;
                Integer locationID = null;
                Integer kalikratisID = null;

                Integer newdecentralizedAdminID = null;
                Integer newregionID = null;
                Integer newregionalUnitID = null;
                Integer newmunicipalityID = null;
                Integer newmunicipalUnitID = null;
                Integer newcommunityID = null;

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);)
                {
                    int stoixeioId = Integer.parseInt(idStoixeioField.getText());
                    String setSessionFullName = "SET @full_name = ?";
                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                        setSessionStmt.executeUpdate();
                    }
                    if (!decentralizedAdmin.isEmpty()) {
                        try (PreparedStatement stmtDecentralizedAdmin = conn.prepareStatement(
                                "SELECT id FROM decentralized_administration WHERE name = ?")) {
                            stmtDecentralizedAdmin.setString(1, decentralizedAdmin);
                            try (ResultSet rs = stmtDecentralizedAdmin.executeQuery()) {
                                if (rs.next()) {
                                    decentralizedAdminID = rs.getInt("id");
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της αποκεντρωμένης διοίκησης: " + ex.getMessage());
                        }
                    }

                    if (!region.isEmpty() && decentralizedAdminID != null) {
                        try (PreparedStatement stmtRegion = conn.prepareStatement(
                                "SELECT id FROM region WHERE name = ? AND decentralized_administration_id = ?")) {
                            stmtRegion.setString(1, region);
                            stmtRegion.setInt(2, decentralizedAdminID);
                            try (ResultSet rs = stmtRegion.executeQuery()) {
                                if (rs.next()) {
                                    regionID = rs.getInt("id");
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της περιοχής: " + ex.getMessage());
                        }
                    }

                    if (!regionalUnit.isEmpty() && regionID != null) {
                        try (PreparedStatement stmtRegionalUnit = conn.prepareStatement(
                                "SELECT id FROM regional_units WHERE name = ? AND region_id = ?")) {
                            stmtRegionalUnit.setString(1, regionalUnit);
                            stmtRegionalUnit.setInt(2, regionID);
                            try (ResultSet rs = stmtRegionalUnit.executeQuery()) {
                                if (rs.next()) {
                                    regionalUnitID = rs.getInt("id");
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της περιφερειακής ενότητας: " + ex.getMessage());
                        }
                    }

                    if (!municipality.isEmpty() && regionalUnitID != null) {
                        try (PreparedStatement stmtMunicipality = conn.prepareStatement(
                                "SELECT id FROM municipalities WHERE name = ? AND regional_units_id = ?")) {
                            stmtMunicipality.setString(1, municipality);
                            stmtMunicipality.setInt(2, regionalUnitID);
                            try (ResultSet rs = stmtMunicipality.executeQuery()) {
                                if (rs.next()) {
                                    municipalityID = rs.getInt("id");
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση του δήμου: " + ex.getMessage());
                        }
                    }

                    if (!municipalUnit.isEmpty() && municipalityID != null) {
                        try (PreparedStatement stmtMunicipalUnit = conn.prepareStatement(
                                "SELECT id FROM municipal_units WHERE name = ? AND municipalities_id = ?")) {
                            stmtMunicipalUnit.setString(1, municipalUnit);
                            stmtMunicipalUnit.setInt(2, municipalityID);
                            try (ResultSet rs = stmtMunicipalUnit.executeQuery()) {
                                if (rs.next()) {
                                    municipalUnitID = rs.getInt("id");
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της δημοτικής ενότητας: " + ex.getMessage());
                        }
                    }

                    if (!communities.isEmpty() && municipalUnitID != null) {
                        try (PreparedStatement stmtCommunity = conn.prepareStatement(
                                "SELECT id FROM communities WHERE name = ? AND municipal_units_id = ?")) {
                            stmtCommunity.setString(1, communities);
                            stmtCommunity.setInt(2, municipalUnitID);
                            try (ResultSet rs = stmtCommunity.executeQuery()) {
                                if (rs.next()) {
                                    communityID = rs.getInt("id");
                                }
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της κοινότητας: " + ex.getMessage());
                        }
                    }
                    try (PreparedStatement selectStmt = conn.prepareStatement("SELECT id_location FROM location WHERE stoixeio_idStoixeio = ?")) {
                        selectStmt.setInt(1, stoixeioId);
                        try (ResultSet rs = selectStmt.executeQuery()) {
                            if (rs.next()) {
                                locationID = rs.getInt("id_location");
                            }
                        }
                    }
                    if (locationID != null) {
                        try (PreparedStatement Stmt = conn.prepareStatement("SELECT id FROM kalikratis WHERE location_id_location = ? " +
                                "AND (decentralized_administration_id = ? OR decentralized_administration_id IS NULL) " +
                                "AND (region_id = ? OR region_id IS NULL) " +
                                "AND (regional_unit_id = ? OR regional_unit_id IS NULL) " +
                                "AND (municipality_id = ? OR municipality_id IS NULL) " +
                                "AND (municipal_unit_id = ? OR municipal_unit_id IS NULL) " +
                                "AND (community_id = ? OR community_id IS NULL)")) {
                            Stmt.setInt(1, locationID);
                            Stmt.setObject(2, decentralizedAdminID);
                            Stmt.setObject(3, regionID);
                            Stmt.setObject(4, regionalUnitID);
                            Stmt.setObject(5, municipalityID);
                            Stmt.setObject(6, municipalUnitID);
                            Stmt.setObject(7, communityID);


                            try (ResultSet rs = Stmt.executeQuery()) {
                                if (rs.next()) {
                                    kalikratisID = rs.getInt("id");
                                }
                                if (!newDecentralizedAdmin.isEmpty()) {
                                    try (PreparedStatement stmtDecentralizedAdmin = conn.prepareStatement(
                                            "SELECT id FROM decentralized_administration WHERE name = ?")) {
                                        stmtDecentralizedAdmin.setString(1, newDecentralizedAdmin);
                                        try (ResultSet rsd = stmtDecentralizedAdmin.executeQuery()) {
                                            if (rsd.next()) {
                                                newdecentralizedAdminID = rsd.getInt("id");
                                            }
                                        }
                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της αποκεντρωμένης διοίκησης: " + ex.getMessage());
                                    }
                                }

                                if (!newRegion.isEmpty() && newdecentralizedAdminID != null) {
                                    try (PreparedStatement stmtRegion = conn.prepareStatement(
                                            "SELECT id FROM region WHERE name = ? AND decentralized_administration_id = ?")) {
                                        stmtRegion.setString(1, newRegion);
                                        stmtRegion.setInt(2, newdecentralizedAdminID);
                                        try (ResultSet rsr = stmtRegion.executeQuery()) {
                                            if (rsr.next()) {
                                                newregionID = rsr.getInt("id");
                                            }
                                        }
                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της περιοχής: " + ex.getMessage());
                                    }
                                }

                                if (!newRegionalUnit.isEmpty() && newregionID != null) {
                                    try (PreparedStatement stmtRegionalUnit = conn.prepareStatement(
                                            "SELECT id FROM regional_units WHERE name = ? AND region_id = ?")) {
                                        stmtRegionalUnit.setString(1, newRegionalUnit);
                                        stmtRegionalUnit.setInt(2, newregionID);
                                        try (ResultSet rsru = stmtRegionalUnit.executeQuery()) {
                                            if (rsru.next()) {
                                                newregionalUnitID = rsru.getInt("id");
                                            }
                                        }
                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της περιφερειακής ενότητας: " + ex.getMessage());
                                    }
                                }

                                if (!newMunicipality.isEmpty() && newregionalUnitID != null) {
                                    try (PreparedStatement stmtMunicipality = conn.prepareStatement(
                                            "SELECT id FROM municipalities WHERE name = ? AND regional_units_id = ?")) {
                                        stmtMunicipality.setString(1, newMunicipality);
                                        stmtMunicipality.setInt(2, newregionalUnitID);
                                        try (ResultSet rsm = stmtMunicipality.executeQuery()) {
                                            if (rsm.next()) {
                                                newmunicipalityID = rsm.getInt("id");
                                            }
                                        }
                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση του δήμου: " + ex.getMessage());
                                    }
                                }

                                if (!newMunicipalUnit.isEmpty() && newmunicipalityID != null) {
                                    try (PreparedStatement stmtMunicipalUnit = conn.prepareStatement(
                                            "SELECT id FROM municipal_units WHERE name = ? AND municipalities_id = ?")) {
                                        stmtMunicipalUnit.setString(1, newMunicipalUnit);
                                        stmtMunicipalUnit.setInt(2, newmunicipalityID);
                                        try (ResultSet rsmu = stmtMunicipalUnit.executeQuery()) {
                                            if (rsmu.next()) {
                                                newmunicipalUnitID = rsmu.getInt("id");
                                            }
                                        }
                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της δημοτικής ενότητας: " + ex.getMessage());
                                    }
                                }

                                if (!newCommunities.isEmpty() && newmunicipalUnitID != null) {
                                    try (PreparedStatement stmtCommunity = conn.prepareStatement(
                                            "SELECT id FROM communities WHERE name = ? AND municipal_units_id = ?")) {
                                        stmtCommunity.setString(1, newCommunities);
                                        stmtCommunity.setInt(2, newmunicipalUnitID);
                                        try (ResultSet rsc = stmtCommunity.executeQuery()) {
                                            if (rsc.next()) {
                                                newcommunityID = rsc.getInt("id");
                                            }
                                        }
                                    } catch (SQLException ex) {
                                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της κοινότητας: " + ex.getMessage());
                                    }
                                }
                                if (kalikratisID != null) {
                                    try (PreparedStatement stmt = conn.prepareStatement("UPDATE kalikratis SET " +
                                            "decentralized_administration_id = ?, " +
                                            "region_id = ?, " +
                                            "regional_unit_id = ?, " +
                                            "municipality_id = ?, " +
                                            "municipal_unit_id = ?, " +
                                            "community_id = ? " +
                                            "WHERE id = ?")) {

                                        if (newdecentralizedAdminID == null) {
                                            stmt.setNull(1, java.sql.Types.INTEGER);
                                        } else {
                                            stmt.setInt(1, newdecentralizedAdminID);
                                        }

                                        if (newregionID == null) {
                                            stmt.setNull(2, java.sql.Types.INTEGER);
                                        } else {
                                            stmt.setInt(2, newregionID);
                                        }

                                        if (newregionalUnitID == null) {
                                            stmt.setNull(3, java.sql.Types.INTEGER);
                                        } else {
                                            stmt.setInt(3, newregionalUnitID);
                                        }

                                        if (newmunicipalityID == null) {
                                            stmt.setNull(4, java.sql.Types.INTEGER);
                                        } else {
                                            stmt.setInt(4, newmunicipalityID);
                                        }

                                        if (newmunicipalUnitID == null) {
                                            stmt.setNull(5, java.sql.Types.INTEGER);
                                        } else {
                                            stmt.setInt(5, newmunicipalUnitID);
                                        }

                                        if (newcommunityID == null) {
                                            stmt.setNull(6, java.sql.Types.INTEGER);
                                        } else {
                                            stmt.setInt(6, newcommunityID);
                                        }

                                        stmt.setInt(7, kalikratisID);

                                        int rowsUpdated = stmt.executeUpdate();
                                        if (rowsUpdated > 0) {
                                            JOptionPane.showMessageDialog(this, "Η ενημέρωση έγινε επιτυχώς.");
                                        } else {
                                            System.out.println("Δεν βρέθηκε η εγγραφή προς ενημέρωση.");
                                        }

                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Δεν βρέθηκε η περιοχή.");
                                }

                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "Δεν βρέθηκε η περιοχή.");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ενημέρωση: " + ex.getMessage());
                }

                decentralizedAdminComboBox.setSelectedIndex(0);
                regionComboBox.setSelectedIndex(0);
                regionalUnitComboBox.setSelectedIndex(0);
                municipalityComboBox.setSelectedIndex(0);
                municipalUnitComboBox.setSelectedIndex(0);
                communitiesComboBox.setSelectedIndex(0);

                addLocationButton.setEnabled(true);
                removeLocationButton.setEnabled(true);
                updateLocButton.setEnabled(false);

            });
        });

        // Διαγραφή περιοχής από τη βάση και την τοπική λίστα
        removeLocationButton.addActionListener(e -> {
            String[] entriesArray = locationList.toArray(new String[0]);
            String selectedEntry = (String) JOptionPane.showInputDialog(
                    this,
                    "Επιλέξτε περιοχή για διαγραφή:",
                    "Διαγραφή Περιοχής",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    entriesArray,
                    entriesArray[0]);

            if (selectedEntry == null) {
                return;
            }

            if (locationDescriptionArea.getText().isEmpty() && locationList.size() == 1) {
                JOptionPane.showMessageDialog(this, "Η τελευταία περιοχή δεν μπορεί να διαγραφεί αν η περιγραφή είναι κενή.");
                return;
            }

            String[] parts = selectedEntry.split(", ");
            String decentralizedAdmin = parts[0];
            String region = (parts.length > 1) ? parts[1] : "";
            String regionalUnit = (parts.length > 2) ? parts[2] : "";
            String municipality = (parts.length > 3) ? parts[3] : "";
            String municipalUnit = (parts.length > 4) ? parts[4] : "";
            String communities = (parts.length > 5) ? parts[5] : "";

            Integer decentralizedAdminID = null;
            Integer regionID = null;
            Integer regionalUnitID = null;
            Integer municipalityID = null;
            Integer municipalUnitID = null;
            Integer communityID = null;

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);)
            {
                int stoixeioId = Integer.parseInt(idStoixeioField.getText());
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                if (!decentralizedAdmin.isEmpty()) {
                    try (PreparedStatement stmtDecentralizedAdmin = conn.prepareStatement(
                            "SELECT id FROM decentralized_administration WHERE name = ?")) {
                        stmtDecentralizedAdmin.setString(1, decentralizedAdmin);
                        try (ResultSet rs = stmtDecentralizedAdmin.executeQuery()) {
                            if (rs.next()) {
                                decentralizedAdminID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της αποκεντρωμένης διοίκησης: " + ex.getMessage());
                    }
                }

                if (!region.isEmpty() && decentralizedAdminID != null) {
                    try (PreparedStatement stmtRegion = conn.prepareStatement(
                            "SELECT id FROM region WHERE name = ? AND decentralized_administration_id = ?")) {
                        stmtRegion.setString(1, region);
                        stmtRegion.setInt(2, decentralizedAdminID);
                        try (ResultSet rs = stmtRegion.executeQuery()) {
                            if (rs.next()) {
                                regionID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της περιοχής: " + ex.getMessage());
                    }
                }

                if (!regionalUnit.isEmpty() && regionID != null) {
                    try (PreparedStatement stmtRegionalUnit = conn.prepareStatement(
                            "SELECT id FROM regional_units WHERE name = ? AND region_id = ?")) {
                        stmtRegionalUnit.setString(1, regionalUnit);
                        stmtRegionalUnit.setInt(2, regionID);
                        try (ResultSet rs = stmtRegionalUnit.executeQuery()) {
                            if (rs.next()) {
                                regionalUnitID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της περιφερειακής ενότητας: " + ex.getMessage());
                    }
                }

                if (!municipality.isEmpty() && regionalUnitID != null) {
                    try (PreparedStatement stmtMunicipality = conn.prepareStatement(
                            "SELECT id FROM municipalities WHERE name = ? AND regional_units_id = ?")) {
                        stmtMunicipality.setString(1, municipality);
                        stmtMunicipality.setInt(2, regionalUnitID);
                        try (ResultSet rs = stmtMunicipality.executeQuery()) {
                            if (rs.next()) {
                                municipalityID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση του δήμου: " + ex.getMessage());
                    }
                }

                if (!municipalUnit.isEmpty() && municipalityID != null) {
                    try (PreparedStatement stmtMunicipalUnit = conn.prepareStatement(
                            "SELECT id FROM municipal_units WHERE name = ? AND municipalities_id = ?")) {
                        stmtMunicipalUnit.setString(1, municipalUnit);
                        stmtMunicipalUnit.setInt(2, municipalityID);
                        try (ResultSet rs = stmtMunicipalUnit.executeQuery()) {
                            if (rs.next()) {
                                municipalUnitID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της δημοτικής ενότητας: " + ex.getMessage());
                    }
                }

                if (!communities.isEmpty() && municipalUnitID != null) {
                    try (PreparedStatement stmtCommunity = conn.prepareStatement(
                            "SELECT id FROM communities WHERE name = ? AND municipal_units_id = ?")) {
                        stmtCommunity.setString(1, communities);
                        stmtCommunity.setInt(2, municipalUnitID);
                        try (ResultSet rs = stmtCommunity.executeQuery()) {
                            if (rs.next()) {
                                communityID = rs.getInt("id");
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάκτηση της κοινότητας: " + ex.getMessage());
                    }
                }

                try (PreparedStatement selectStmt = conn.prepareStatement(
                        "SELECT id_location FROM location WHERE stoixeio_idStoixeio = ?")) {

                    selectStmt.setInt(1, stoixeioId);

                    try (ResultSet rs = selectStmt.executeQuery()) {
                        if (rs.next()) {
                            int idLocation = rs.getInt("id_location");

                            try (PreparedStatement deleteStmt = conn.prepareStatement(
                                    "DELETE FROM kalikratis WHERE location_id_location = ? " +
                                            "AND (decentralized_administration_id = ? OR decentralized_administration_id IS NULL) " +
                                            "AND (region_id = ? OR region_id IS NULL) " +
                                            "AND (regional_unit_id = ? OR regional_unit_id IS NULL) " +
                                            "AND (municipality_id = ? OR municipality_id IS NULL) " +
                                            "AND (municipal_unit_id = ? OR municipal_unit_id IS NULL) " +
                                            "AND (community_id = ? OR community_id IS NULL)")) {

                                deleteStmt.setInt(1, idLocation);
                                deleteStmt.setObject(2, decentralizedAdminID);
                                deleteStmt.setObject(3, regionID);
                                deleteStmt.setObject(4, regionalUnitID);
                                deleteStmt.setObject(5, municipalityID);
                                deleteStmt.setObject(6, municipalUnitID);
                                deleteStmt.setObject(7, communityID);

                                int rowsDeleted = deleteStmt.executeUpdate();
                                if (rowsDeleted > 0) {
                                    JOptionPane.showMessageDialog(this, "Η περιοχή διαγράφηκε επιτυχώς.");
                                    locationList.remove(selectedEntry);
                                    updateLocationField();
                                } else {
                                    JOptionPane.showMessageDialog(this, "Δεν βρέθηκε η περιοχή προς διαγραφή.");
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(this, "Σφάλμα κατά τη διαγραφή: " + ex.getMessage());
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Δεν βρέθηκε το ID τοποθεσίας.");
                        }
                    }
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα κατά την εισαγωγή στη βάση δεδομένων: " + ex.getMessage());
            }
        });



        // Λέξεις-Κλειδιά
        keywordList = new HashSet<>();
        JPanel keywordsPanel = new JPanel(new BorderLayout());
        keywordsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        keywordsPanel.add(new JLabel("Λέξεις-κλειδιά (έως 30):"), BorderLayout.NORTH);
        keywordInputField = new JTextField();
        keywordsPanel.add(keywordInputField, BorderLayout.CENTER);
        formPanel.add(keywordsPanel);

        JPanel addKeywordPanel = new JPanel(new BorderLayout());
        addKeywordPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton addKeywordButton = new JButton("Προσθήκη Λέξης-Κλειδί");
        addKeywordPanel.add(addKeywordButton,BorderLayout.EAST);
        formPanel.add(addKeywordPanel);

        keywordField = new JTextArea(3, 20);
        keywordField.setEditable(false);
        keywordField.setLineWrap(true);
        keywordField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(keywordField));

        //Κουμπί Διαγραφής
        JPanel removeKeywordPanel = new JPanel(new BorderLayout());
        removeKeywordPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeKeywordButton = new JButton("Διαγραφή Λέξης-Κλειδί");
        removeKeywordButton.setBackground(Color.DARK_GRAY);
        removeKeywordButton.setForeground(Color.WHITE);
        removeKeywordPanel.add(removeKeywordButton, BorderLayout.EAST);
        formPanel.add(removeKeywordPanel);

        addKeywordButton.addActionListener(e -> {
            String keyword = keywordInputField.getText().trim();
            if (!keyword.isEmpty()) {
                if (keywordList.size() >= 30) {
                    JOptionPane.showMessageDialog(this, "Δεν μπορείτε να προσθέσετε περισσότερες από 30 λέξεις-κλειδιά.");
                    return;
                }
                // Έλεγχος αν η λέξη-κλειδί υπάρχει ήδη στη λίστα
                if (keywordList.contains(keyword)) {
                    JOptionPane.showMessageDialog(this, "Η λέξη-κλειδί υπάρχει ήδη.");
                    return;
                }

                String idStoixeio = idStoixeioField.getText();
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    if (conn != null) {
                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }

                        int keywordId;

                        String selectKeywordQuery = "SELECT id_keyword FROM keywords WHERE keyword = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(selectKeywordQuery)) {
                            pstmt.setString(1, keyword);
                            try (ResultSet rs = pstmt.executeQuery()) {
                                if (rs.next()) {
                                    keywordId = rs.getInt("id_keyword");
                                } else {
                                    String insertKeywordQuery = "INSERT INTO keywords (keyword) VALUES (?)";
                                    try (PreparedStatement insertStmt = conn.prepareStatement(insertKeywordQuery, Statement.RETURN_GENERATED_KEYS)) {
                                        insertStmt.setString(1, keyword);
                                        insertStmt.executeUpdate();
                                        try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                                            if (generatedKeys.next()) {
                                                keywordId = generatedKeys.getInt(1);
                                            } else {
                                                throw new SQLException("Αποτυχία δημιουργίας νέας λέξης-κλειδιού.");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        String insertStoixeioKeywordQuery = "INSERT INTO stoixeio_keywords (stoixeio_idStoixeio, keyword_id_keyword) VALUES (?, ?)";
                        try (PreparedStatement insertStmt = conn.prepareStatement(insertStoixeioKeywordQuery)) {
                            insertStmt.setString(1, idStoixeio);
                            insertStmt.setInt(2, keywordId);
                            int rowsAffected = insertStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                keywordList.add(keyword);
                                keywordField.append(keyword + ";\n");
                                keywordInputField.setText("");
                                JOptionPane.showMessageDialog(this, "Η λέξη-κλειδί προστέθηκε επιτυχώς.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Αποτυχία προσθήκης λέξης-κλειδιού στο στοιχείο.");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Αποτυχία σύνδεσης ή εκτέλεσης εισαγωγής στη βάση δεδομένων.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Η λέξη-κλειδί δεν μπορεί να είναι κενή.");
            }
        });

        removeKeywordButton.addActionListener(e -> {
            if (keywordList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν λέξεις-κλειδιά για διαγραφή.");
                return;
            }

            if (keywordList.size() == 1) {
                JOptionPane.showMessageDialog(this, "Δεν μπορείτε να διαγράψετε την μόνη λέξη-κλειδί στη λίστα.");
                return;
            }

            // Δημιουργία λίστας επιλογής για τη διαγραφή
            String[] keywordsArray = keywordList.toArray(new String[0]);
            String selectedKeyword = (String) JOptionPane.showInputDialog(
                    this,
                    "Επιλέξτε μία λέξη-κλειδί για διαγραφή:",
                    "Διαγραφή Λέξης-Κλειδί",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    keywordsArray,
                    keywordsArray[0]
            );

            if (selectedKeyword != null) {
                int idKeyword = -1;
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    if (conn != null) {
                        String selectQuery = "SELECT id_keyword FROM keywords WHERE keyword = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
                            pstmt.setString(1, selectedKeyword);
                            ResultSet rs = pstmt.executeQuery();
                            if (rs.next()) {
                                idKeyword = rs.getInt("id_keyword");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Αποτυχία σύνδεσης ή εκτέλεσης αναζήτησης στη βάση δεδομένων.");
                }

                if (idKeyword == -1) {
                    JOptionPane.showMessageDialog(this, "Η λέξη-κλειδί δεν βρέθηκε στη βάση δεδομένων.");
                    return;
                }

                String idStoixeio = idStoixeioField.getText();

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    if (conn != null) {
                        conn.setAutoCommit(false);

                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }
                        String deleteAssociationQuery = "DELETE FROM stoixeio_keywords WHERE stoixeio_idStoixeio = ? AND keyword_id_keyword = ?";
                        try (PreparedStatement pstmtAssoc = conn.prepareStatement(deleteAssociationQuery)) {
                            pstmtAssoc.setString(1, idStoixeio);
                            pstmtAssoc.setInt(2, idKeyword);
                            pstmtAssoc.executeUpdate();
                        }
                        conn.commit();

                        keywordList.remove(selectedKeyword);
                        updateKeywordField();
                        JOptionPane.showMessageDialog(this, "Η λέξη-κλειδί διαγράφηκε επιτυχώς.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Αποτυχία σύνδεσης ή εκτέλεσης διαγραφής στη βάση δεδομένων.");
                }
            }
        });


        //Φορείς
        foreasList = new HashSet<>();
        JPanel foreasPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        foreasPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        foreasPanel.add(new JLabel("Φορείς Μετάδοσης"));
        formPanel.add(foreasPanel);

        // Στοιχεία
        JPanel stoixeiaPanel = new JPanel(new GridLayout(8, 2));
        stoixeiaPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        stoixeiaPanel.add(new JLabel("Όνομα Φορέα:"));
        foreasNameField = new JTextField(30);
        stoixeiaPanel.add(foreasNameField);

        stoixeiaPanel.add(new JLabel("Περιγραφή Φορέα:"));
        foreasDescriptionField = new JTextArea(3,30);
        stoixeiaPanel.add(new JScrollPane(foreasDescriptionField));
        foreasDescriptionField.setLineWrap(true);
        foreasDescriptionField.setWrapStyleWord(true);

        stoixeiaPanel.add(new JLabel("email Φορέα:"));
        foreasEmailField = new JTextField(30);
        stoixeiaPanel.add(foreasEmailField);

        stoixeiaPanel.add(new JLabel("Διεύθυνση Φορέα:"));
        foreasAddressField = new JTextField(30);
        stoixeiaPanel.add(foreasAddressField);

        stoixeiaPanel.add(new JLabel("Πόλη Φορέα:"));
        foreasCityField = new JTextField(30);
        stoixeiaPanel.add(foreasCityField);

        stoixeiaPanel.add(new JLabel("TK Φορέα:"));
        foreasTKField = new JTextField(30);
        stoixeiaPanel.add(foreasTKField);

        stoixeiaPanel.add(new JLabel("Τηλέφωνο Φορέα:"));
        foreasTelField = new JTextField(30);
        stoixeiaPanel.add(foreasTelField);

        stoixeiaPanel.add(new JLabel("Website Φορέα:"));
        foreasWebsiteField = new JTextField(30);
        stoixeiaPanel.add(foreasWebsiteField);

        formPanel.add(stoixeiaPanel);

        JPanel addforeasPanel = new JPanel(new BorderLayout());
        addforeasPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton addForeasButton = new JButton("Προσθήκη Φορέα");
        addforeasPanel.add(addForeasButton, BorderLayout.EAST);
        formPanel.add(addforeasPanel);

        // Πεδίο εμφάνισης για φορεα
        foreasField = new JTextArea(7, 20);
        foreasField.setEditable(false);
        foreasField.setLineWrap(true);
        foreasField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(foreasField));

        // Προσθήκη κουμπιού επεξεργασίας
        JPanel editForeasPanel = new JPanel(new BorderLayout());
        editForeasPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton editForeasButton = new JButton("Επεξεργασία Φορέα");
        editForeasButton.setBackground(Color.GRAY);
        editForeasButton.setForeground(Color.WHITE);
        editForeasPanel.add(editForeasButton, BorderLayout.EAST);
        formPanel.add(editForeasPanel);

        // Προσθήκη κουμπιού ενημέρωσης
        JPanel updateForeasPanel = new JPanel(new BorderLayout());
        updateForeasPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton updateForeasButton = new JButton("Ενημέρωση Φορέα");
        updateForeasButton.setBackground(Color.GRAY);
        updateForeasButton.setForeground(Color.WHITE);
        updateForeasButton.setVisible(false);
        updateForeasPanel.add(updateForeasButton, BorderLayout.EAST);
        formPanel.add(updateForeasPanel);

        // Προσθήκη κουμπιού διαγραφής
        JPanel removeForeasPanel = new JPanel(new BorderLayout());
        removeForeasPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeForeasButton = new JButton("Διαγραφή Φορέα");
        removeForeasButton.setBackground(Color.DARK_GRAY);
        removeForeasButton.setForeground(Color.WHITE);
        removeForeasPanel.add(removeForeasButton, BorderLayout.EAST);
        formPanel.add(removeForeasPanel);

        foreasList = new HashSet<>();
        addForeasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Λήψη τιμών από τα πεδία
                String foreasName = foreasNameField.getText();
                String foreasDescription = foreasDescriptionField.getText();
                String foreasEmail = foreasEmailField.getText();
                String foreasAddress = foreasAddressField.getText();
                String foreasCity = foreasCityField.getText();
                String foreasTK = foreasTKField.getText();
                String foreasTel = foreasTelField.getText();
                String foreasWebsite = foreasWebsiteField.getText();

                String idStoixeio = idStoixeioField.getText();

                // Έλεγχος αν όλα τα πεδία είναι συμπληρωμένα
                if (foreasName.isEmpty() || foreasDescription.isEmpty() || foreasAddress.isEmpty() || foreasTel.isEmpty() ||
                        foreasEmail.isEmpty() || foreasTK.isEmpty() || foreasCity.isEmpty() || foreasWebsite.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Όλα τα πεδία είναι υποχρεωτικά!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Έλεγχοι για τη μορφή των πεδίων
                if (foreasDescription.split("\\s+").length > 100) {
                    JOptionPane.showMessageDialog(null, "Η περιγραφή δεν πρέπει να υπερβαίνει τις 100 λέξεις.");
                    return;
                }

                if (!foreasTel.matches("^((\\+30)?[2-9][0-9]{9}|[2-9][0-9]{9})$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το τηλέφωνο", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!foreasEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το email", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Έλεγχος για τον ταχυδρομικό κώδικα (5 ψηφία)
                if (!foreasTK.matches("\\d{5}")) {
                    JOptionPane.showMessageDialog(null, "Ο ταχυδρομικός κώδικας πρέπει να έχει ακριβώς 5 ψηφία.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Έλεγχος για το website (έγκυρη διεύθυνση URL)
                if (!foreasWebsite.matches("^(https?://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}|www\\.[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$")) {
                    JOptionPane.showMessageDialog(null, "Η διεύθυνση του website δεν είναι έγκυρη.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Σύνδεση με τη βάση δεδομένων
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                    String setSessionFullName = "SET @full_name = ?";
                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                        setSessionStmt.executeUpdate();
                    }
                    int foreasId;
                    // Έλεγχος αν ο φορέας υπάρχει ήδη (με βάση το όνομα ή το email)
                    String checkQuery = "SELECT idforeas, namef, descriptionf, address, city, telephone, email, TK, website FROM foreas WHERE namef = ? OR email = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(checkQuery)) {
                        pstmt.setString(1, foreasName);
                        pstmt.setString(2, foreasEmail);

                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                               foreasId = rs.getInt("idforeas");

                                String insertStoixeioForeasQuery = "INSERT INTO stoixeio_foreas (stoixeio_id, foreas_id) VALUES (?, ?)";
                                try (PreparedStatement insertStoixeioForeasPstmt = conn.prepareStatement(insertStoixeioForeasQuery)) {
                                    insertStoixeioForeasPstmt.setInt(1, Integer.parseInt(idStoixeio));
                                    insertStoixeioForeasPstmt.setInt(2, foreasId);
                                    insertStoixeioForeasPstmt.executeUpdate();
                                }
                                String foreasInfo = "Όνομα: " + rs.getString("namef") + "\n" +
                                        "Περιγραφή: " + rs.getString("descriptionf") + "\n" +
                                        "Email: " + rs.getString("email") + "\n" +
                                        "Διεύθυνση: " + rs.getString("address") + "\n" +
                                        "Πόλη: " + rs.getString("city") + "\n" +
                                        "TK: " + rs.getString("TK") + "\n" +
                                        "Τηλέφωνο: " + rs.getString("telephone") + "\n" +
                                        "Website: " + rs.getString("website");
                                foreasList.add(foreasInfo);
                                foreasField.append(foreasInfo + ";\n");
                                JOptionPane.showMessageDialog(null, "Ο φορέας υπάρχει ήδη και προστέθηκε στη λίστα.", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                String insertQuery = "INSERT INTO foreas (namef, descriptionf, email, address, city, TK, telephone, website) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                try (PreparedStatement insertPstmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                                    insertPstmt.setString(1, foreasName);
                                    insertPstmt.setString(2, foreasDescription);
                                    insertPstmt.setString(3, foreasEmail);
                                    insertPstmt.setString(4, foreasAddress);
                                    insertPstmt.setString(5, foreasCity);
                                    insertPstmt.setString(6, foreasTK);
                                    insertPstmt.setString(7, foreasTel);
                                    insertPstmt.setString(8, foreasWebsite);

                                    int rowsInserted = insertPstmt.executeUpdate();
                                    if (rowsInserted > 0) {
                                        try (ResultSet generatedKeys = insertPstmt.getGeneratedKeys()) {
                                            if (generatedKeys.next()) {
                                                foreasId = generatedKeys.getInt(1);

                                                String insertStoixeioForeasQuery = "INSERT INTO stoixeio_foreas (stoixeio_id, foreas_id) VALUES (?, ?)";
                                                try (PreparedStatement insertStoixeioForeasPstmt = conn.prepareStatement(insertStoixeioForeasQuery)) {
                                                    insertStoixeioForeasPstmt.setInt(1, Integer.parseInt(idStoixeio));
                                                    insertStoixeioForeasPstmt.setInt(2, foreasId);
                                                    insertStoixeioForeasPstmt.executeUpdate();
                                                }

                                                String foreasInfo = "Όνομα: " + foreasName + "\n" +
                                                        "Περιγραφή: " + foreasDescription + "\n" +
                                                        "Email: " + foreasEmail + "\n" +
                                                        "Διεύθυνση: " + foreasAddress + "\n" +
                                                        "Πόλη: " + foreasCity + "\n" +
                                                        "TK: " + foreasTK + "\n" +
                                                        "Τηλέφωνο: " + foreasTel + "\n" +
                                                        "Website: " + foreasWebsite;
                                                foreasList.add(foreasInfo);
                                                foreasField.append(foreasInfo + ";\n");
                                                JOptionPane.showMessageDialog(null, "Ο φορέας προστέθηκε επιτυχώς στη βάση και στη λίστα.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }
                                    } else {
                                        throw new SQLException("Η εισαγωγή του φορέα απέτυχε.");
                                    }
                                }
                            }
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά την εισαγωγή στη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                foreasNameField.setText("");
                foreasDescriptionField.setText("");
                foreasAddressField.setText("");
                foreasTelField.setText("");
                foreasCityField.setText("");
                foreasEmailField.setText("");
                foreasTKField.setText("");
                foreasWebsiteField.setText("");
            }
        });


        // Προσθήκη κουμπιού για την επεξεργασία φορέα
        editForeasButton.addActionListener(e -> {
            String[] foreasArray = foreasList.toArray(new String[0]);
            String selectedForeas = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε φορέα για επεξεργασία:",
                    "Επεξεργασία Φορέα",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    foreasArray,
                    foreasArray[0]);

            if (selectedForeas == null) {
                return;
            }

            // Διάσπαση της επιλεγμένης καταχώρησης στα πεδία (διαχωρισμός με ": ")
            String[] foreasParts = selectedForeas.split("\n");
            String foreasName = foreasParts[0].split(": ")[1];
            String foreasDescription = foreasParts[1].split(": ")[1];
            String foreasEmail = foreasParts[2].split(": ")[1];
            String foreasAddress = foreasParts[3].split(": ")[1];
            String foreasCity = foreasParts[4].split(": ")[1];
            String foreasTK = foreasParts[5].split(": ")[1];
            String foreasTel = foreasParts[6].split(": ")[1];
            String foreasWebsite = foreasParts[7].split(": ")[1];

            // Φόρτωση των δεδομένων στα πεδία επεξεργασίας
            foreasNameField.setText(foreasName);
            foreasDescriptionField.setText(foreasDescription);
            foreasAddressField.setText(foreasAddress);
            foreasCityField.setText(foreasCity);
            foreasTelField.setText(foreasTel);
            foreasEmailField.setText(foreasEmail);
            foreasTKField.setText(foreasTK);
            foreasWebsiteField.setText(foreasWebsite);

            updateForeasButton.setVisible(true);
            addForeasButton.setEnabled(false);
            removeForeasButton.setEnabled(false);

            // Ενεργοποίηση του κουμπιού ενημέρωσης
            updateForeasButton.setEnabled(true);

            foreasList.remove(selectedForeas);

            foreasField.setText("");
            for (String foreasEntry : foreasList) {
                foreasField.append(foreasEntry + ";\n");
            }


            updateForeasButton.addActionListener(updateEvent -> {
                String newForeasName = foreasNameField.getText();
                String newForeasDescription = foreasDescriptionField.getText();
                String newForeasAddress = foreasAddressField.getText();
                String newForeasCity = foreasCityField.getText();
                String newForeasTel = foreasTelField.getText();
                String newForeasEmail = foreasEmailField.getText();
                String newForeasTK = foreasTKField.getText();
                String newForeasWebsite = foreasWebsiteField.getText();

                // Έλεγχος αν όλα τα πεδία είναι συμπληρωμένα
                if (newForeasName.isEmpty() || newForeasDescription.isEmpty() || newForeasAddress.isEmpty() || newForeasTel.isEmpty() ||
                        newForeasEmail.isEmpty() || newForeasTK.isEmpty() || newForeasCity.isEmpty() || newForeasWebsite.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Όλα τα πεδία είναι υποχρεωτικά!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (newForeasDescription.split("\\s+").length > 100) {
                    JOptionPane.showMessageDialog(null, "Η περιγραφή δεν πρέπει να υπερβαίνει τις 100 λέξεις.");
                    return;
                }

                if (!newForeasTel.matches("^((\\+30)?[2-9][0-9]{9}|[2-9][0-9]{9})$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το τηλέφωνο", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Έλεγχος για τη μορφή του email
                if (!newForeasEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το email", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Έλεγχος για τον ταχυδρομικό κώδικα (5 ψηφία)
                if (!newForeasTK.matches("\\d{5}")) {
                    JOptionPane.showMessageDialog(null, "Ο ταχυδρομικός κώδικας πρέπει να έχει ακριβώς 5 ψηφία.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!newForeasWebsite.matches("^(https?://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}|www\\.[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$")) {
                    JOptionPane.showMessageDialog(null, "Η διεύθυνση του website δεν είναι έγκυρη.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (newForeasName.equals(foreasName) && newForeasDescription.equals(foreasDescription) &&
                        newForeasAddress.equals(foreasAddress) && newForeasTel.equals(foreasTel) &&
                        newForeasEmail.equals(foreasEmail) && newForeasTK.equals(foreasTK) &&
                        newForeasWebsite.equals(foreasWebsite) && newForeasCity.equals(foreasCity)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Δεν έγινε καμία αλλαγή στον φορέα.",
                            "Πληροφόρηση",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    String unchangedForeasEntry = "Όνομα: " + foreasName + "\n" +
                            "Περιγραφή: " + foreasDescription + "\n" +
                            "Email: " + foreasEmail + "\n" +
                            "Διεύθυνση: " + foreasAddress + "\n" +
                            "Πόλη: " + foreasCity + "\n" +
                            "TK: " + foreasTK + "\n" +
                            "Τηλέφωνο: " + foreasTel + "\n" +
                            "Website: " + foreasWebsite;
                    foreasList.add(unchangedForeasEntry);

                    foreasField.setText("");
                    for (String foreasEntry : foreasList) {
                        foreasField.append(foreasEntry + ";\n");
                    }

                    addForeasButton.setEnabled(true);
                    removeForeasButton.setEnabled(true);
                    updateForeasButton.setEnabled(false);

                    foreasNameField.setText("");
                    foreasDescriptionField.setText("");
                    foreasAddressField.setText("");
                    foreasCityField.setText("");
                    foreasTelField.setText("");
                    foreasEmailField.setText("");
                    foreasTKField.setText("");
                    foreasWebsiteField.setText("");

                    return;
                }

                int foreasId = -1;
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                    String sql = "SELECT idforeas FROM foreas WHERE namef = ? AND descriptionf = ? AND email = ? " +
                            "AND address = ? AND city = ? AND TK = ? AND telephone = ? AND website = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, foreasName);
                        ps.setString(2, foreasDescription);
                        ps.setString(3, foreasEmail);
                        ps.setString(4, foreasAddress);
                        ps.setString(5, foreasCity);
                        ps.setString(6, foreasTK);
                        ps.setString(7, foreasTel);
                        ps.setString(8, foreasWebsite);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                foreasId = rs.getInt("idforeas");
                            }
                        }
                    }

                    String checkQuery = "SELECT COUNT(*) FROM foreas WHERE (namef = ? OR email = ?) AND idforeas != ? AND namef != ? AND email != ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setString(1, newForeasName);
                        checkStmt.setString(2, newForeasEmail);
                        checkStmt.setInt(3, foreasId);
                        checkStmt.setString(4, foreasName);
                        checkStmt.setString(5, foreasEmail);
                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(null, "Το όνομα ή το email ανήκουν ήδη σε άλλο φορέα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    StringBuilder updatedFields = new StringBuilder("Τα εξής πεδία ενημερώθηκαν:\n");

                    // Έλεγχος για την αλλαγή κάθε πεδίου
                    boolean anyUpdated = false;
                    if (!newForeasName.equals(foreasName)) {
                        updatedFields.append("Όνομα\n");
                        anyUpdated = true;
                    }
                    if (!newForeasDescription.equals(foreasDescription)) {
                        updatedFields.append("Περιγραφή\n");
                        anyUpdated = true;
                    }
                    if (!newForeasEmail.equals(foreasEmail)) {
                        updatedFields.append("Email\n");
                        anyUpdated = true;
                    }
                    if (!newForeasAddress.equals(foreasAddress)) {
                        updatedFields.append("Διεύθυνση\n");
                        anyUpdated = true;
                    }
                    if (!newForeasCity.equals(foreasCity)) {
                        updatedFields.append("Πόλη\n");
                        anyUpdated = true;
                    }
                    if (!newForeasTK.equals(foreasTK)) {
                        updatedFields.append("TK\n");
                        anyUpdated = true;
                    }
                    if (!newForeasTel.equals(foreasTel)) {
                        updatedFields.append("Τηλέφωνο\n");
                        anyUpdated = true;
                    }
                    if (!newForeasWebsite.equals(foreasWebsite)) {
                        updatedFields.append("Website\n");
                        anyUpdated = true;
                    }

                    if (anyUpdated) {
                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }
                        // Προετοιμασία της ενημέρωσης της βάσης δεδομένων (μόνο για τα πεδία που άλλαξαν)
                        String updateQuery = "UPDATE foreas SET ";
                        boolean first = true;
                        if (!newForeasName.equals(foreasName)) {
                            updateQuery += "namef = ?, ";
                            first = false;
                        }
                        if (!newForeasDescription.equals(foreasDescription)) {
                            updateQuery += "descriptionf = ?, ";
                            first = false;
                        }
                        if (!newForeasEmail.equals(foreasEmail)) {
                            updateQuery += "email = ?, ";
                            first = false;
                        }
                        if (!newForeasAddress.equals(foreasAddress)) {
                            updateQuery += "address = ?, ";
                            first = false;
                        }
                        if (!newForeasCity.equals(foreasCity)) {
                            updateQuery += "city = ?, ";
                            first = false;
                        }
                        if (!newForeasTK.equals(foreasTK)) {
                            updateQuery += "TK = ?, ";
                            first = false;
                        }
                        if (!newForeasTel.equals(foreasTel)) {
                            updateQuery += "telephone = ?, ";
                            first = false;
                        }
                        if (!newForeasWebsite.equals(foreasWebsite)) {
                            updateQuery += "website = ?, ";
                            first = false;
                        }

                        if (!first) {
                            updateQuery = updateQuery.substring(0, updateQuery.length() - 2) + " WHERE idforeas = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                                int index = 1;
                                if (!newForeasName.equals(foreasName)) pstmt.setString(index++, newForeasName);
                                if (!newForeasDescription.equals(foreasDescription)) pstmt.setString(index++, newForeasDescription);
                                if (!newForeasEmail.equals(foreasEmail)) pstmt.setString(index++, newForeasEmail);
                                if (!newForeasAddress.equals(foreasAddress)) pstmt.setString(index++, newForeasAddress);
                                if (!newForeasCity.equals(foreasCity)) pstmt.setString(index++, newForeasCity);
                                if (!newForeasTK.equals(foreasTK)) pstmt.setString(index++, newForeasTK);
                                if (!newForeasTel.equals(foreasTel)) pstmt.setString(index++, newForeasTel);
                                if (!newForeasWebsite.equals(foreasWebsite)) pstmt.setString(index++, newForeasWebsite);

                                pstmt.setInt(index++, foreasId);

                                int rowsUpdated = pstmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Η ενημέρωση του φορέα ολοκληρώθηκε επιτυχώς!",
                                            "Επιτυχία",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                } else {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Αποτυχία ενημέρωσης του φορέα. Δοκιμάστε ξανά.",
                                            "Σφάλμα",
                                            JOptionPane.ERROR_MESSAGE
                                    );
                                    return;
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Σφάλμα στη σύνδεση με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                                return;
                            }
                        }

                        // Ενημέρωση της λίστας με τον νέο φορέα
                        String updatedForeasEntry = "Όνομα: " + newForeasName + "\n" +
                                "Περιγραφή: " + newForeasDescription + "\n" +
                                "Email: " + newForeasEmail + "\n" +
                                "Διεύθυνση: " + newForeasAddress + "\n" +
                                "Πόλη: " + newForeasCity + "\n" +
                                "TK: " + newForeasTK + "\n" +
                                "Τηλέφωνο: " + newForeasTel + "\n" +
                                "Website: " + newForeasWebsite;
                        foreasList.add(updatedForeasEntry);

                        foreasField.setText("");
                        for (String foreasEntry : foreasList) {
                            foreasField.append(foreasEntry + ";\n");
                        }

                        foreasNameField.setText("");
                        foreasDescriptionField.setText("");
                        foreasAddressField.setText("");
                        foreasCityField.setText("");
                        foreasTelField.setText("");
                        foreasEmailField.setText("");
                        foreasTKField.setText("");
                        foreasWebsiteField.setText("");

                        addForeasButton.setEnabled(true);
                        removeForeasButton.setEnabled(true);
                        updateForeasButton.setEnabled(false);

                        JOptionPane.showMessageDialog(
                                null,
                                updatedFields.toString(),
                                "Ενημέρωση Φορέα",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Σφάλμα στη σύνδεση με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
        });


        removeForeasButton.addActionListener(e -> {
            if (foreasList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν φορείς για διαγραφή.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[] foreasArray = foreasList.toArray(new String[0]);
            String selectedForeas = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε φορέα για διαγραφή:",
                    "Διαγραφή Φορέα",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    foreasArray,
                    foreasArray[0]);

            if (selectedForeas == null) {
                return;
            }


            try {
                // Διάσπαση της επιλεγμένης καταχώρησης στα πεδία (διαχωρισμός με ": ")
                String[] foreasParts = selectedForeas.split("\n");
                if (foreasParts.length < 8) {
                    JOptionPane.showMessageDialog(this, "Σφάλμα στη διάσπαση της επιλεγμένης καταχώρησης. Ελλιπή δεδομένα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String foreasName = getValueFromPart(foreasParts[0]);
                String foreasDescription = getValueFromPart(foreasParts[1]);
                String foreasEmail = getValueFromPart(foreasParts[2]);
                String foreasAddress = getValueFromPart(foreasParts[3]);
                String foreasCity = getValueFromPart(foreasParts[4]);
                String foreasTK = getValueFromPart(foreasParts[5]);
                String foreasTel = getValueFromPart(foreasParts[6]);
                String foreasWebsite = getValueFromPart(foreasParts[7]);

                int foreasId = -1;
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "SELECT idforeas FROM foreas WHERE namef = ? AND descriptionf = ? AND email = ? " +
                            "AND address = ? AND city = ? AND TK = ? AND telephone = ? AND website = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, foreasName);
                        ps.setString(2, foreasDescription);
                        ps.setString(3, foreasEmail);
                        ps.setString(4, foreasAddress);
                        ps.setString(5, foreasCity);
                        ps.setString(6, foreasTK);
                        ps.setString(7, foreasTel);
                        ps.setString(8, foreasWebsite);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                foreasId = rs.getInt("idforeas");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αναζήτηση του φορέα: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (foreasId == -1) {
                    JOptionPane.showMessageDialog(this, "Ο φορέας δεν βρέθηκε στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String setSessionFullName = "SET @full_name = ?";
                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                        setSessionStmt.executeUpdate();
                    }
                    String deleteLinkSql = "DELETE FROM stoixeio_foreas WHERE foreas_id = ? AND stoixeio_id = ?";
                    try (PreparedStatement ps = conn.prepareStatement(deleteLinkSql)) {
                        ps.setInt(1, foreasId);
                        ps.setString(2, idStoixeioField.getText());
                        ps.executeUpdate();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Σφάλμα κατά τη διαγραφή από τον πίνακα stoixeio_foreas: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Διαγραφή από τη λίστα
                foreasList.remove(selectedForeas);

                // Ενημέρωση του JTextArea
                foreasField.setText("");
                for (String foreasEntry : foreasList) {
                    foreasField.append(foreasEntry + ";\n");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Αναλυτική Περιγραφή
        JPanel fullDescriptionPanel = new JPanel(new BorderLayout());
        fullDescriptionPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        fullDescriptionPanel.add(new JLabel("Αναλυτική Περιγραφή (500 έως 1.000 λέξεις):"), BorderLayout.NORTH);
        full_descriptionArea = new JTextArea(5, 20);
        fullDescriptionPanel.add(new JScrollPane(full_descriptionArea), BorderLayout.CENTER);
        full_descriptionArea.setLineWrap(true);
        full_descriptionArea.setWrapStyleWord(true);
        full_descriptionArea.setEditable(false);
        formPanel.add(fullDescriptionPanel);

        JPanel editFullDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editFullDescriptionButton = new JButton("Επεξεργασία Αναλυτικής Περιγραφής");
        editFullDescriptionButton.setBackground(Color.GRAY);
        editFullDescriptionButton.setForeground(Color.WHITE);
        editFullDescriptionPanel.add(editFullDescriptionButton);

        JPanel saveFullDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveFullDescriptionButton = new JButton("Αποθήκευση Αναλυτικής Περιγραφής");
        saveFullDescriptionPanel.add(saveFullDescriptionButton);

        JPanel cancelFullDescriptionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelFullDescriptionButton = new JButton("Ακύρωση");
        cancelFullDescriptionButton.setBackground(Color.lightGray);
        cancelFullDescriptionButton.setForeground(Color.WHITE);
        cancelFullDescriptionPanel.add(cancelFullDescriptionButton);

        JPanel FullDescriptionPanel = new JPanel(new BorderLayout());
        FullDescriptionPanel.add(saveFullDescriptionPanel, BorderLayout.CENTER);
        FullDescriptionPanel.add(cancelFullDescriptionPanel, BorderLayout.EAST);
        FullDescriptionPanel.add(editFullDescriptionPanel, BorderLayout.WEST);

        formPanel.add(FullDescriptionPanel);

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editFullDescriptionButton.addActionListener(e -> {
            initialFullDescriptionText = full_descriptionArea.getText();
            full_descriptionArea.setEditable(true);
            full_descriptionArea.requestFocus();
            saveFullDescriptionButton.setEnabled(true);
            cancelFullDescriptionButton.setEnabled(true);
            editFullDescriptionButton.setEnabled(false);
        });

        // Ενέργεια για το κουμπί "Αποθήκευση"
        saveFullDescriptionButton.addActionListener(e -> {
            full_descriptionArea.setEditable(false);
            saveFullDescriptionButton.setEnabled(false);
            cancelFullDescriptionButton.setEnabled(false);
            editFullDescriptionButton.setEnabled(true);

            int idStoixeio = Integer.parseInt(idStoixeioField.getText());
            String newFullDescription = full_descriptionArea.getText();

            if (newFullDescription.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Η αναλυτική περιγραφή είναι υποχρεωτική.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Έλεγχος για το μήκος (500 έως 1.000 λέξεις)
            String[] words = newFullDescription.split("\\s+");
            if (words.length < 500 || words.length > 1000) {
                JOptionPane.showMessageDialog(null, "Η αναλυτική περιγραφή πρέπει να περιέχει από 500 έως 1.000 λέξεις.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Έλεγχος αν η νέα περιγραφή είναι ίδια με την αρχική
            if (newFullDescription.equals(initialFullDescriptionText)) {
                JOptionPane.showMessageDialog(null, "Η αναλυτική περιγραφή δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                // Σύνδεση με τη βάση δεδομένων
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String updateQuery = "UPDATE stoixeio SET full_description = ? WHERE idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newFullDescription);
                pstmt.setInt(2, idStoixeio);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Η αναλυτική περιγραφή αποθηκεύτηκε επιτυχώς στη βάση.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                    initialFullDescriptionText = newFullDescription;
                } else {
                    JOptionPane.showMessageDialog(null, "Η ενημέρωση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }

            full_descriptionArea.setEditable(false);
            saveFullDescriptionButton.setEnabled(false);
            cancelFullDescriptionButton.setEnabled(false);
            editFullDescriptionButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelFullDescriptionButton.addActionListener(e -> {
            full_descriptionArea.setText(initialFullDescriptionText);
            full_descriptionArea.setEditable(false);
            saveFullDescriptionButton.setEnabled(false);
            cancelFullDescriptionButton.setEnabled(false);
            editFullDescriptionButton.setEnabled(true);
        });



        // Χώρος Επιτέλεσης
        performanceAreasList = new HashSet<>();
        JPanel performanceAreaPanel = new JPanel(new BorderLayout());
        performanceAreaPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        performanceAreaPanel.add(new JLabel("Χώρος Επιτέλεσης:"), BorderLayout.NORTH);
        performanceAreaInputField = new JTextField(20);
        performanceAreaPanel.add((performanceAreaInputField), BorderLayout.CENTER);
        formPanel.add(performanceAreaPanel);

        JPanel justificationPerformanceAreaPanel = new JPanel(new BorderLayout());
        justificationPerformanceAreaPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        justificationPerformanceAreaPanel.add(new JLabel("Αιτιολόγηση Χώρου Επιτέλεσης:"), BorderLayout.NORTH);
        justificationPerformanceAreaField = new JTextArea(3, 20);
        justificationPerformanceAreaField.setLineWrap(true);
        justificationPerformanceAreaField.setWrapStyleWord(true);
        justificationPerformanceAreaPanel.add(new JScrollPane(justificationPerformanceAreaField), BorderLayout.CENTER);
        formPanel.add(justificationPerformanceAreaPanel);

        JPanel addPerformanceAreaPanel = new JPanel(new BorderLayout());
        addPerformanceAreaPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton addPerformanceAreaButton = new JButton("Προσθήκη Χώρου Επιτέλεσης");
        addPerformanceAreaPanel.add(addPerformanceAreaButton, BorderLayout.EAST);
        formPanel.add(addPerformanceAreaPanel);

        performanceAreaField = new JTextArea(3, 20);
        performanceAreaField.setEditable(false);
        performanceAreaField.setLineWrap(true);
        performanceAreaField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(performanceAreaField));

        // Κουμπί Επεξεργασίας
        JPanel editPerformanceAreaPanel = new JPanel(new BorderLayout());
        editPerformanceAreaPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton editPerformanceAreaButton = new JButton("Επεξεργασία Χώρου Επιτέλεσης");
        editPerformanceAreaButton.setBackground(Color.GRAY);
        editPerformanceAreaButton.setForeground(Color.WHITE);
        editPerformanceAreaPanel.add(editPerformanceAreaButton, BorderLayout.EAST);
        formPanel.add(editPerformanceAreaPanel);

        // Κουμπί Ενημέρωσης
        JPanel updatePerformanceAreaPanel = new JPanel(new BorderLayout());
        updatePerformanceAreaPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton updatePerformanceAreaButton = new JButton("Ενημέρωση Χώρου Επιτέλεσης");
        updatePerformanceAreaButton.setBackground(Color.GRAY);
        updatePerformanceAreaButton.setForeground(Color.WHITE);
        updatePerformanceAreaButton.setVisible(false);
        updatePerformanceAreaPanel.add(updatePerformanceAreaButton, BorderLayout.EAST);
        formPanel.add(updatePerformanceAreaPanel);

        //Κουμπί Διαγραφής
        JPanel removePerformanceAreaPanel = new JPanel(new BorderLayout());
        removePerformanceAreaPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removePerformanceAreaButton = new JButton("Διαγραφή Χώρου Επιτέλεσης");
        removePerformanceAreaButton.setBackground(Color.DARK_GRAY);
        removePerformanceAreaButton.setForeground(Color.WHITE);
        removePerformanceAreaPanel.add(removePerformanceAreaButton, BorderLayout.EAST);
        formPanel.add(removePerformanceAreaPanel);

        addPerformanceAreaButton.addActionListener(e -> {
            String performanceArea = performanceAreaInputField.getText().trim();
            String justification = justificationPerformanceAreaField.getText().trim();

            int stoixeioId = Integer.parseInt(idStoixeioField.getText().trim());

            // Έλεγχος αν έχει συμπληρωθεί ο χώρος αλλά όχι η αιτιολόγηση
            if (!performanceArea.isEmpty() && justification.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Αν έχετε συμπληρώσει χώρο επιτέλεσης, πρέπει να συμπληρώσετε και την αιτιολόγηση!",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!performanceArea.isEmpty()) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    // Έλεγχος αν το area υπάρχει ήδη για το συγκεκριμένο stoixeio_idStoixeio
                    String checkQuery = "SELECT COUNT(*) AS count FROM performance_area WHERE stoixeio_idStoixeio = ? AND area = ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setInt(1, stoixeioId);
                        checkStmt.setString(2, performanceArea);

                        try (ResultSet rs = checkStmt.executeQuery()) {
                            rs.next();
                            int count = rs.getInt("count");

                            if (count > 0) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Ο χώρος επιτέλεσης υπάρχει ήδη για αυτό το στοιχείο!",
                                        "Σφάλμα",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                return;
                            }
                        }
                    }

                    String setSessionFullName = "SET @full_name = ?";
                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                        setSessionStmt.executeUpdate();
                    }
                    // Εισαγωγή νέου χώρου στη βάση
                    String insertQuery = "INSERT INTO performance_area (stoixeio_idStoixeio, area, justification_performance_area) VALUES (?, ?, ?)";

                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, stoixeioId);
                        insertStmt.setString(2, performanceArea);
                        insertStmt.setString(3, justification);

                        insertStmt.executeUpdate();

                        JOptionPane.showMessageDialog(
                                null,
                                "Ο χώρος επιτέλεσης προστέθηκε επιτυχώς!",
                                "Επιτυχία",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        // Προσθήκη του χώρου και αιτιολόγησης στη λίστα
                        String performanceAreaEntry = performanceArea + ": " + justification;
                        performanceAreasList.add(performanceAreaEntry);

                        // Ενημέρωση του JTextArea
                        performanceAreaField.setText(String.join(";\n", performanceAreasList));

                        // Καθαρισμός των πεδίων εισαγωγής
                        performanceAreaInputField.setText("");
                        justificationPerformanceAreaField.setText("");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Προέκυψε σφάλμα κατά την πρόσβαση στη βάση δεδομένων: " + ex.getMessage(),
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Το πεδίο του χώρου επιτέλεσης είναι κενό!",
                        "Πληροφορία",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });


        editPerformanceAreaButton.addActionListener(e -> {
            // Παρουσίαση της λίστας των χώρων επιτέλεσης για επιλογή
            String[] performanceAreasArray = performanceAreasList.toArray(new String[0]);
            String selectedPerformanceArea = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε χώρο επιτέλεσης για επεξεργασία:",
                    "Επεξεργασία Χώρου Επιτέλεσης",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    performanceAreasArray,
                    performanceAreasArray.length > 0 ? performanceAreasArray[0] : null);

            if (selectedPerformanceArea == null) {
                return;
            }

            String stoixeioId = idStoixeioField.getText().trim();

            String[] parts = selectedPerformanceArea.split(":", 2);
            String area = parts[0].trim();
            String justification = parts[1].trim();


            performanceAreaInputField.setText(area);
            justificationPerformanceAreaField.setText(justification);

            updatePerformanceAreaButton.setVisible(true);
            addPerformanceAreaButton.setEnabled(false);
            removePerformanceAreaButton.setEnabled(false);

            updatePerformanceAreaButton.setEnabled(true);

            performanceAreasList.remove(selectedPerformanceArea);
            updatePerformanceAreaField();



            updatePerformanceAreaButton.addActionListener(updateEvent -> {
                String newArea = performanceAreaInputField.getText();
                String newJustification = justificationPerformanceAreaField.getText();

                if (!newArea.isEmpty() && newJustification.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Αν έχετε συμπληρώσει χώρο επιτέλεσης, πρέπει να συμπληρώσετε και την αιτιολόγηση!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                if (newArea.equals(area) && newJustification.equals(justification)) {
                    // Εμφάνιση μήνυματος και επαναφορά δεδομένων στη λίστα
                    JOptionPane.showMessageDialog(
                            null,
                            "Δεν έγινε καμία αλλαγή στον Χώρο Επιτέλεσης ή την Αιτιολόγηση.",
                            "Πληροφόρηση",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    String unchangedPerformanceAreaEntry = area + ": " + justification;
                    performanceAreasList.add(unchangedPerformanceAreaEntry);

                    performanceAreaField.setText(String.join(";\n", performanceAreasList));

                    performanceAreaInputField.setText("");
                    justificationPerformanceAreaField.setText("");

                    return;
                }

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String findIdQuery = "SELECT id_area FROM performance_area WHERE area = ? AND stoixeio_idStoixeio = ?";
                    int performanceAreaId = -1;

                    try (PreparedStatement findIdStmt = conn.prepareStatement(findIdQuery)) {
                        findIdStmt.setString(1, area);
                        findIdStmt.setString(2, stoixeioId);

                        ResultSet rs = findIdStmt.executeQuery();
                        if (rs.next()) {
                            performanceAreaId = rs.getInt("id_area");
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Ο επιλεγμένος χώρος επιτέλεσης δεν βρέθηκε στη βάση!",
                                    "Σφάλμα",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                    }

                    String checkQuery = "SELECT COUNT(*) FROM performance_area WHERE area = ? AND stoixeio_idStoixeio = ? AND area != ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setString(1, newArea);
                        checkStmt.setString(2, stoixeioId);
                        checkStmt.setString(3, area);

                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Ο νέος χώρος επιτέλεσης υπάρχει ήδη για αυτό το στοιχείο.",
                                    "Σφάλμα",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                    }

                    StringBuilder updatedPerformanceArea = new StringBuilder("Τα εξής πεδία ενημερώθηκαν:\n");

                    // Έλεγχος για την αλλαγή κάθε πεδίου
                    boolean anyUpdated = false;
                    if (!newArea.equals(area)) {
                        updatedPerformanceArea.append("Χώρος επιτέλεσης\n");
                        anyUpdated = true;
                    }
                    if (!newJustification.equals(justification)) {
                        updatedPerformanceArea.append("Αιτιολόγηση\n");
                        anyUpdated = true;
                    }

                    if (anyUpdated) {

                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }
                        // Προετοιμασία της ενημέρωσης της βάσης δεδομένων (μόνο για τα πεδία που άλλαξαν)
                        String updateQuery = "UPDATE performance_area SET ";
                        boolean first = true;
                        if (!newArea.equals(area)) {
                            updateQuery += "area = ?, ";
                            first = false;
                        }
                        if (!newJustification.equals(justification)) {
                            updateQuery += "justification_performance_area = ?, ";
                            first = false;
                        }


                        if (!first) {
                            updateQuery = updateQuery.substring(0, updateQuery.length() - 2) + " WHERE id_area = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                                int index = 1;
                                if (!newArea.equals(area)) pstmt.setString(index++, newArea);
                                if (!newJustification.equals(justification)) pstmt.setString(index++, newJustification);

                                pstmt.setInt(index++, performanceAreaId);

                                int rowsUpdated = pstmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Η ενημέρωση του χώρου επιτέλεσης ολοκληρώθηκε επιτυχώς!",
                                            "Επιτυχία",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                } else {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Αποτυχία ενημέρωσης του χώρου επιτέλεσης. Δοκιμάστε ξανά.",
                                            "Σφάλμα",
                                            JOptionPane.ERROR_MESSAGE
                                    );
                                    return;
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Σφάλμα στη σύνδεση με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                                return;
                            }
                        }
                        String updatedPerformanceAreaEntry =  newArea + ": " + newJustification;
                        performanceAreasList.add(updatedPerformanceAreaEntry);
                        performanceAreaField.setText(String.join(";\n", performanceAreasList));

                        addPerformanceAreaButton.setEnabled(true);
                        removePerformanceAreaButton.setEnabled(true);
                        updatePerformanceAreaButton.setEnabled(false);


                        performanceAreaInputField.setText("");
                        justificationPerformanceAreaField.setText("");

                        JOptionPane.showMessageDialog(
                                null,
                                updatedPerformanceArea.toString(),
                                "Ενημέρωση Χώρου Επιτέλεσης",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Σφάλμα στη σύνδεση με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
        });

        removePerformanceAreaButton.addActionListener(e -> {
            if (performanceAreasList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν χώροι επιτέλεσης για διαγραφή.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Δημιουργία λίστας επιλογής για διαγραφή
            String[] performanceAreasArray = performanceAreasList.toArray(new String[0]);
            JPanel panel = new JPanel();
            JComboBox<String> comboBox = new JComboBox<>(performanceAreasArray);
            comboBox.setPreferredSize(new Dimension(600, 25));
            panel.add(comboBox);

            int result = JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Επιλέξτε έναν χώρο επιτέλεσης για διαγραφή:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String selectedPerformanceArea = (String) comboBox.getSelectedItem();
                if (selectedPerformanceArea != null) {
                    // Διαχωρισμός της καταχώρησης σε χώρο επιτέλεσης και αιτιολόγηση
                    String[] parts = selectedPerformanceArea.split(": ", 2);
                    String area = parts[0];
                    String justification = parts[1];

                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        String findIdSql = "SELECT id_area FROM performance_area WHERE area = ? AND stoixeio_idStoixeio = ?";
                        try (PreparedStatement findIdStmt = conn.prepareStatement(findIdSql)) {
                            findIdStmt.setString(1, area);
                            findIdStmt.setString(2, idStoixeioField.getText());
                            try (ResultSet rs = findIdStmt.executeQuery()) {
                                if (rs.next()) {
                                    int id = rs.getInt("id_area");
                                    String setSessionFullName = "SET @full_name = ?";
                                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                                        setSessionStmt.executeUpdate();
                                    }
                                    String deleteSql = "DELETE FROM performance_area WHERE id_area = ?";
                                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                                        deleteStmt.setInt(1, id);
                                        int rowsAffected = deleteStmt.executeUpdate();

                                        if (rowsAffected > 0) {
                                            JOptionPane.showMessageDialog(this, "Ο χώρος επιτέλεσης διαγράφηκε επιτυχώς από τη βάση δεδομένων.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                                            performanceAreasList.remove(selectedPerformanceArea);
                                            updatePerformanceAreaField();
                                        } else {
                                            JOptionPane.showMessageDialog(this, "Απέτυχε η διαγραφή του χώρου επιτέλεσης.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Ο χώρος επιτέλεσης δεν βρέθηκε στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά τη σύνδεση ή διαγραφή από τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Η καταχώρηση δεν έχει την αναμενόμενη μορφή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Εγκαταστάσεις
        facilitiesList = new HashSet<>();
        JPanel facilitiesPanel = new JPanel(new BorderLayout());
        facilitiesPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        facilitiesPanel.add(new JLabel("Εγκατάσταση:"), BorderLayout.NORTH);
        facilitiesInputField = new JTextField(20);
        facilitiesPanel.add((facilitiesInputField), BorderLayout.CENTER);
        formPanel.add(facilitiesPanel);

        JPanel justificationFacilityPanel = new JPanel(new BorderLayout());
        justificationFacilityPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        justificationFacilityPanel.add(new JLabel("Αιτιολόγηση Εγκατάστασης:"), BorderLayout.NORTH);
        justificationFacilityField = new JTextArea(3, 20);
        justificationFacilityField.setLineWrap(true);
        justificationFacilityField.setWrapStyleWord(true);
        justificationFacilityPanel.add(new JScrollPane(justificationFacilityField), BorderLayout.CENTER);
        formPanel.add(justificationFacilityPanel);

        JPanel addFacilitiesPanel = new JPanel(new BorderLayout());
        addFacilitiesPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton addFacilitiesButton = new JButton("Προσθήκη Εγκατάστασης");
        addFacilitiesPanel.add(addFacilitiesButton, BorderLayout.EAST);
        formPanel.add(addFacilitiesPanel);

        facilitiesField = new JTextArea(3, 20);
        facilitiesField.setEditable(false);
        facilitiesField.setLineWrap(true);
        facilitiesField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(facilitiesField));

        // Κουμπί επεξεργασίας εγκατάστασης
        JPanel editFacilitiesPanel = new JPanel(new BorderLayout());
        editFacilitiesPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton editFacilitiesButton = new JButton("Επεξεργασία Εγκατάστασης");
        editFacilitiesButton.setBackground(Color.GRAY);
        editFacilitiesButton.setForeground(Color.WHITE);
        editFacilitiesPanel.add(editFacilitiesButton, BorderLayout.EAST);
        formPanel.add(editFacilitiesPanel);

        // Κουμπί ενημέρωσης εγκατάστασης
        JPanel updateFacilitiesPanel = new JPanel(new BorderLayout());
        updateFacilitiesPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton updateFacilitiesButton = new JButton("Ενημέρωση Εγκατάστασης");
        updateFacilitiesButton.setBackground(Color.GRAY);
        updateFacilitiesButton.setForeground(Color.WHITE);
        updateFacilitiesButton.setVisible(false);
        updateFacilitiesPanel.add(updateFacilitiesButton, BorderLayout.EAST);
        formPanel.add(updateFacilitiesPanel);

        // Κουμπί για διαγραφή εγκατάστασης
        JPanel removeFacilitiesPanel = new JPanel(new BorderLayout());
        removeFacilitiesPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeFacilitiesButton = new JButton("Διαγραφή Εγκατάστασης");
        removeFacilitiesButton.setBackground(Color.DARK_GRAY);
        removeFacilitiesButton.setForeground(Color.WHITE);
        removeFacilitiesPanel.add(removeFacilitiesButton, BorderLayout.EAST);
        formPanel.add(removeFacilitiesPanel);

        addFacilitiesButton.addActionListener(e -> {
            String facility = facilitiesInputField.getText().trim();
            String justification = justificationFacilityField.getText().trim();

            int stoixeioId = Integer.parseInt(idStoixeioField.getText().trim());

            if (!facility.isEmpty() && justification.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Αν έχετε συμπληρώσει εγκατάσταση, πρέπει να συμπληρώσετε και την αιτιολόγηση!",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!facility.isEmpty()) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String checkQuery = "SELECT COUNT(*) AS count FROM facilities WHERE stoixeio_idStoixeio = ? AND facility = ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setInt(1, stoixeioId);
                        checkStmt.setString(2, facility);

                        try (ResultSet rs = checkStmt.executeQuery()) {
                            rs.next();
                            int count = rs.getInt("count");

                            if (count > 0) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Η εγκατάσταση υπάρχει ήδη για αυτό το στοιχείο!",
                                        "Σφάλμα",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                return;
                            }
                        }
                    }
                    String setSessionFullName = "SET @full_name = ?";
                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                        setSessionStmt.executeUpdate();
                    }
                    String insertQuery = "INSERT INTO facilities (stoixeio_idStoixeio, facility, justification_facility) VALUES (?, ?, ?)";

                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, stoixeioId);
                        insertStmt.setString(2, facility);
                        insertStmt.setString(3, justification);

                        insertStmt.executeUpdate();

                        JOptionPane.showMessageDialog(
                                null,
                                "Η εγκατάσταση προστέθηκε επιτυχώς!",
                                "Επιτυχία",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        String facilityEntry = facility + ": " + justification;
                        facilitiesList.add(facilityEntry);

                        facilitiesField.setText(String.join(";\n", facilitiesList));

                        facilitiesInputField.setText("");
                        justificationFacilityField.setText("");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Προέκυψε σφάλμα κατά την πρόσβαση στη βάση δεδομένων: " + ex.getMessage(),
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Το πεδίο της εγκατάστασης είναι κενό!",
                        "Πληροφορία",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        editFacilitiesButton.addActionListener(e -> {
            String[] facilitiesArray = facilitiesList.toArray(new String[0]);
            String selectedFacility = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε εγκατάσταση για επεξεργασία:",
                    "Επεξεργασία Εγκατάστασης",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    facilitiesArray,
                    facilitiesArray.length > 0 ? facilitiesArray[0] : null);

            if (selectedFacility == null) {
                return;
            }

            String[] parts = selectedFacility.split(":", 2);
            String facility = parts[0].trim();
            String justification = parts[1].trim();

            facilitiesInputField.setText(facility);
            justificationFacilityField.setText(justification);

            // Διαγραφή της επιλεγμένης εγκατάστασης από τη λίστα πριν την επεξεργασία
            facilitiesList.remove(selectedFacility);
            updateFacilitiesField();

            updateFacilitiesButton.setVisible(true);
            addFacilitiesButton.setEnabled(false);
            removeFacilitiesButton.setEnabled(false);
            // Ενεργοποίηση του κουμπιού ενημέρωσης
            updateFacilitiesButton.setEnabled(true);

            updateFacilitiesButton.addActionListener(updateEvent -> {
                String newFacility = facilitiesInputField.getText();
                String newJustification = justificationFacilityField.getText();

                // Έλεγχος αν έχει συμπληρωθεί η εγκατάσταση αλλά όχι η αιτιολόγηση
                if (!newFacility.isEmpty() && newJustification.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Αν έχετε συμπληρώσει την εγκατάσταση, πρέπει να συμπληρώσετε και την αιτιολόγηση!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Έλεγχος αν δεν έχει αλλάξει τίποτα
                if (newFacility.equals(facility) && newJustification.equals(justification)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Δεν έγινε καμία αλλαγή στην Εγκατάσταση ή την Αιτιολόγηση.",
                            "Πληροφόρηση",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    // Επαναφορά της εγκατάστασης στη λίστα
                    String unchangedFacilityEntry = facility + ": " + justification;
                    facilitiesList.add(unchangedFacilityEntry);

                    // Ενημέρωση του JTextArea με την τρέχουσα λίστα
                    facilitiesField.setText(String.join(";\n", facilitiesList));

                    // Καθαρισμός των πεδίων
                    facilitiesInputField.setText("");
                    justificationFacilityField.setText("");

                    return;
                }

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String findIdQuery = "SELECT id_facility FROM facilities WHERE facility = ? AND stoixeio_idStoixeio = ?";
                    int facilityId = -1;

                    try (PreparedStatement findIdStmt = conn.prepareStatement(findIdQuery)) {
                        findIdStmt.setString(1, facility);
                        findIdStmt.setString(2, idStoixeioField.getText());

                        ResultSet rs = findIdStmt.executeQuery();
                        if (rs.next()) {
                            facilityId = rs.getInt("id_facility");
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Η επιλεγμένη εγκατάσταση δεν βρέθηκε στη βάση!",
                                    "Σφάλμα",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                    }

                    String checkQuery = "SELECT COUNT(*) FROM facilities WHERE facility = ? AND stoixeio_idStoixeio = ? AND facility != ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setString(1, newFacility);
                        checkStmt.setString(2, idStoixeioField.getText());
                        checkStmt.setString(3, facility);

                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Η νέα εγκατάσταση υπάρχει ήδη για αυτό το στοιχείο.",
                                    "Σφάλμα",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                    }

                    // Αρχικοποίηση μιας λίστας για τα πεδία που έχουν ενημερωθεί
                    StringBuilder updatedFacility = new StringBuilder("Τα εξής πεδία ενημερώθηκαν:\n");

                    // Έλεγχος για την αλλαγή κάθε πεδίου
                    boolean anyUpdated = false;
                    if (!newFacility.equals(facility)) {
                        updatedFacility.append("Εγκατάσταση\n");
                        anyUpdated = true;
                    }
                    if (!newJustification.equals(justification)) {
                        updatedFacility.append("Αιτιολόγηση\n");
                        anyUpdated = true;
                    }

                    if (anyUpdated) {
                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }
                        // Προετοιμασία της ενημέρωσης της βάσης δεδομένων (μόνο για τα πεδία που άλλαξαν)
                        String updateQuery = "UPDATE facilities SET ";
                        boolean first = true;
                        if (!newFacility.equals(facility)) {
                            updateQuery += "facility = ?, ";
                            first = false;
                        }
                        if (!newJustification.equals(justification)) {
                            updateQuery += "justification_facility = ?, ";
                            first = false;
                        }

                        if (!first) {
                            updateQuery = updateQuery.substring(0, updateQuery.length() - 2) + " WHERE id_facility = ?";
                             try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                                 int index = 1;
                                 if (!newFacility.equals(facility)) pstmt.setString(index++, newFacility);
                                 if (!newJustification.equals(justification)) pstmt.setString(index++, newJustification);

                                 pstmt.setInt(index++, facilityId);


                                 int rowsUpdated = pstmt.executeUpdate();
                                 if (rowsUpdated > 0) {
                                     JOptionPane.showMessageDialog(
                                             null,
                                             "Η ενημέρωση της εγκατάστασης ολοκληρώθηκε επιτυχώς!",
                                             "Επιτυχία",
                                             JOptionPane.INFORMATION_MESSAGE
                                     );
                                 } else {
                                     JOptionPane.showMessageDialog(
                                             null,
                                             "Αποτυχία ενημέρωσης της εγκατάστασης. Δοκιμάστε ξανά.",
                                             "Σφάλμα",
                                             JOptionPane.ERROR_MESSAGE
                                     );
                                     return;
                                 }
                             } catch (SQLException ex) {
                                 JOptionPane.showMessageDialog(null, "Σφάλμα στη σύνδεση με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                 ex.printStackTrace();
                                 return;
                             }
                        }
                        // Δημιουργία νέας μορφής εγγραφής
                        String updatedFacilityEntry = newFacility + ": " + newJustification;
                        // Ενημέρωση της λίστας
                        facilitiesList.add(updatedFacilityEntry);
                        // Ενημέρωση του JTextArea
                        facilitiesField.setText(String.join(";\n", facilitiesList));

                        // Επαναφορά κουμπιών
                        addFacilitiesButton.setEnabled(true);
                        removeFacilitiesButton.setEnabled(true);
                        updateFacilitiesButton.setEnabled(false);

                        // Καθαρισμός των πεδίων εισαγωγής
                        facilitiesInputField.setText("");
                        justificationFacilityField.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά την επικοινωνία με τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            });
        });


        removeFacilitiesButton.addActionListener(e -> {
            if (facilitiesList.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Δεν υπάρχουν εγκαταστάσεις για διαγραφή.",
                        "Πληροφορία",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            // Δημιουργία λίστας επιλογής για διαγραφή
            String[] facilitiesArray = facilitiesList.toArray(new String[0]);
            JPanel panel = new JPanel();
            JComboBox<String> comboBox = new JComboBox<>(facilitiesArray);
            comboBox.setPreferredSize(new Dimension(600, 25));
            panel.add(comboBox);

            int result = JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Επιλέξτε μία εγκατάσταση για διαγραφή:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String selectedFacility = (String) comboBox.getSelectedItem();
                if (selectedFacility != null) {
                    // Διαχωρισμός σε facility και justification
                    String[] parts = selectedFacility.split(": ", 2);
                    String facility = parts[0];
                    String justification = parts[1];

                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        String findIdSql = "SELECT id_facility FROM facilities WHERE facility = ? AND stoixeio_idStoixeio = ?";
                        try (PreparedStatement findIdStmt = conn.prepareStatement(findIdSql)) {
                            findIdStmt.setString(1, facility);
                            findIdStmt.setString(2, idStoixeioField.getText());
                            try (ResultSet rs = findIdStmt.executeQuery()) {
                                if (rs.next()) {
                                    int id = rs.getInt("id_facility");
                                    String setSessionFullName = "SET @full_name = ?";
                                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                                        setSessionStmt.executeUpdate();
                                    }
                                    String deleteSql = "DELETE FROM facilities WHERE id_facility = ?";
                                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                                        deleteStmt.setInt(1, id);
                                        int rowsAffected = deleteStmt.executeUpdate();

                                        if (rowsAffected > 0) {
                                            JOptionPane.showMessageDialog(this, "Η εγκατάσταση διαγράφηκε επιτυχώς από τη βάση δεδομένων.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                                            facilitiesList.remove(selectedFacility);
                                            updateFacilitiesField();
                                        } else {
                                            JOptionPane.showMessageDialog(this, "Απέτυχε η διαγραφή της εγκατάστασης.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Η εγκατάσταση δεν βρέθηκε στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά τη σύνδεση ή διαγραφή από τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Η καταχώρηση δεν έχει την αναμενόμενη μορφή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Εξοπλισμός
        equipmentList = new HashSet<>();
        JPanel equipmentPanel = new JPanel(new BorderLayout());
        equipmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        equipmentPanel.add(new JLabel("Εξοπλισμός:"), BorderLayout.NORTH);
        equipmentInputField = new JTextField(20);
        equipmentPanel.add((equipmentInputField), BorderLayout.CENTER);
        formPanel.add(equipmentPanel);

        JPanel justificationEquipmentPanel = new JPanel(new BorderLayout());
        justificationEquipmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        justificationEquipmentPanel.add(new JLabel("Αιτιολόγηση Εξοπλισμού:"), BorderLayout.NORTH);
        justificationEquipmentField = new JTextArea(3, 20);
        justificationEquipmentField.setLineWrap(true);
        justificationEquipmentField.setWrapStyleWord(true);
        justificationEquipmentPanel.add(new JScrollPane(justificationEquipmentField), BorderLayout.CENTER);
        formPanel.add(justificationEquipmentPanel);

        JPanel addEquipmentPanel = new JPanel(new BorderLayout());
        addEquipmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton addEquipmentsButton = new JButton("Προσθήκη Εξοπλισμού");
        addEquipmentPanel.add(addEquipmentsButton, BorderLayout.EAST);
        formPanel.add(addEquipmentPanel);

        equipmentField = new JTextArea(3, 20);
        equipmentField.setEditable(false);
        equipmentField.setLineWrap(true);
        equipmentField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(equipmentField));

        // Κουμπί επεξεργασίας εξοπλισμού
        JPanel editEquipmentPanel = new JPanel(new BorderLayout());
        editEquipmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton editEquipmentsButton = new JButton("Επεξεργασία Εξοπλισμού");
        editEquipmentsButton.setBackground(Color.GRAY);
        editEquipmentsButton.setForeground(Color.WHITE);
        editEquipmentPanel.add(editEquipmentsButton, BorderLayout.EAST);
        formPanel.add(editEquipmentPanel);

        // Κουμπί ενημέρωσης εξοπλισμού
        JPanel updateEquipmentPanel = new JPanel(new BorderLayout());
        updateEquipmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton updateEquipmentButton = new JButton("Ενημέρωση Εξοπλισμού");
        updateEquipmentButton.setBackground(Color.GRAY);
        updateEquipmentButton.setForeground(Color.WHITE);
        updateEquipmentButton.setVisible(false);
        updateEquipmentPanel.add(updateEquipmentButton, BorderLayout.EAST);
        formPanel.add(updateEquipmentPanel);

        // Κουμπί για διαγραφή εξοπλισμού
        JPanel removeEquipmentPanel = new JPanel(new BorderLayout());
        removeEquipmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeEquipmentsButton = new JButton("Διαγραφή Εξοπλισμού");
        removeEquipmentsButton.setBackground(Color.DARK_GRAY);
        removeEquipmentsButton.setForeground(Color.WHITE);
        removeEquipmentPanel.add(removeEquipmentsButton, BorderLayout.EAST);
        formPanel.add(removeEquipmentPanel);

        addEquipmentsButton.addActionListener(e -> {
            String equipment = equipmentInputField.getText().trim();
            String justification = justificationEquipmentField.getText().trim();

            int stoixeioId = Integer.parseInt(idStoixeioField.getText().trim());

            // Έλεγχος αν έχει συμπληρωθεί ο εξοπλισμός αλλά όχι η αιτιολόγηση
            if (!equipment.isEmpty() && justification.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Αν έχετε συμπληρώσει εξοπλισμό, πρέπει να συμπληρώσετε και την αιτιολόγηση!",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!equipment.isEmpty()) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    // Έλεγχος αν το equipment υπάρχει ήδη για το συγκεκριμένο stoixeio_idStoixeio
                    String checkQuery = "SELECT COUNT(*) AS count FROM equipment WHERE stoixeio_idStoixeio = ? AND equipment = ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setInt(1, stoixeioId);
                        checkStmt.setString(2, equipment);

                        try (ResultSet rs = checkStmt.executeQuery()) {
                            rs.next();
                            int count = rs.getInt("count");

                            if (count > 0) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Ο εξοπλισμός υπάρχει ήδη για αυτό το στοιχείο!",
                                        "Σφάλμα",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                return;
                            }
                        }
                    }

                    String setSessionFullName = "SET @full_name = ?";
                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                        setSessionStmt.executeUpdate();
                    }
                    String insertQuery = "INSERT INTO equipment (stoixeio_idStoixeio, equipment, justification_equipment) VALUES (?, ?, ?)";

                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, stoixeioId);
                        insertStmt.setString(2, equipment);
                        insertStmt.setString(3, justification);

                        insertStmt.executeUpdate();

                        JOptionPane.showMessageDialog(
                                null,
                                "Ο εξοπλισμός προστέθηκε επιτυχώς!",
                                "Επιτυχία",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        String equipmentEntry = equipment + ": " + justification;
                        equipmentList.add(equipmentEntry);

                        equipmentField.setText(String.join(";\n", equipmentList));

                        equipmentInputField.setText("");
                        justificationEquipmentField.setText("");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Προέκυψε σφάλμα κατά την πρόσβαση στη βάση δεδομένων: " + ex.getMessage(),
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Το πεδίο του εξοπλισμού είναι κενό!",
                        "Πληροφορία",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        editEquipmentsButton.addActionListener(e -> {
            String[] equipmentArray = equipmentList.toArray(new String[0]);
            String selectedEquipment = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε εξοπλισμό για επεξεργασία:",
                    "Επεξεργασία Εξοπλισμού",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    equipmentArray,
                    equipmentArray.length > 0 ? equipmentArray[0] : null);

            if (selectedEquipment == null) {
                return;
            }

            String[] parts = selectedEquipment.split(":", 2);
            String equipment = parts[0].trim();
            String justification = parts[1].trim();

            equipmentInputField.setText(equipment);
            justificationEquipmentField.setText(justification);

            // Διαγραφή του επιλεγμένου εξοπλισμού από τη λίστα πριν την επεξεργασία
            equipmentList.remove(selectedEquipment);
            updateEquipmentField();

            updateEquipmentButton.setVisible(true);
            addEquipmentsButton.setEnabled(false);
            removeEquipmentsButton.setEnabled(false);
            updateEquipmentButton.setEnabled(true);

            updateEquipmentButton.addActionListener(updateEvent -> {
                String newEquipment = equipmentInputField.getText();
                String newJustification = justificationEquipmentField.getText();

                // Έλεγχος αν έχει συμπληρωθεί ο εξοπλισμός αλλά όχι η αιτιολόγηση
                if (!newEquipment.isEmpty() && newJustification.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Αν έχετε συμπληρώσει τον εξοπλισμό, πρέπει να συμπληρώσετε και την αιτιολόγηση!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Έλεγχος αν δεν έχει αλλάξει τίποτα
                if (newEquipment.equals(equipment) && newJustification.equals(justification)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Δεν έγινε καμία αλλαγή στον Εξοπλισμό ή την Αιτιολόγηση.",
                            "Πληροφόρηση",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    // Επαναφορά του εξοπλισμού στη λίστα
                    String unchangedEquipmentEntry = equipment + ": " + justification;
                    equipmentList.add(unchangedEquipmentEntry);

                    // Ενημέρωση του JTextArea με την τρέχουσα λίστα
                    equipmentField.setText(String.join(";\n", equipmentList));

                    // Καθαρισμός των πεδίων
                    equipmentInputField.setText("");
                    justificationEquipmentField.setText("");

                    return;
                }

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String findIdQuery = "SELECT id_equipment FROM equipment WHERE equipment = ? AND stoixeio_idStoixeio = ?";
                    int equipmentId = -1;

                    try (PreparedStatement findIdStmt = conn.prepareStatement(findIdQuery)) {
                        findIdStmt.setString(1, equipment);
                        findIdStmt.setString(2, idStoixeioField.getText());

                        ResultSet rs = findIdStmt.executeQuery();
                        if (rs.next()) {
                            equipmentId = rs.getInt("id_equipment");
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Ο επιλεγμένος εξοπλισμός δεν βρέθηκε στη βάση!",
                                    "Σφάλμα",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                    }

                    String checkQuery = "SELECT COUNT(*) FROM equipment WHERE equipment = ? AND stoixeio_idStoixeio = ? AND equipment != ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setString(1, newEquipment);
                        checkStmt.setString(2, idStoixeioField.getText());
                        checkStmt.setString(3, equipment);

                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Ο νέος εξοπλισμός υπάρχει ήδη για αυτό το στοιχείο.",
                                    "Σφάλμα",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                    }

                    // Αρχικοποίηση μιας λίστας για τα πεδία που έχουν ενημερωθεί
                    StringBuilder updatedEquipment = new StringBuilder("Τα εξής πεδία ενημερώθηκαν:\n");

                    // Έλεγχος για την αλλαγή κάθε πεδίου
                    boolean anyUpdated = false;
                    if (!newEquipment.equals(equipment)) {
                        updatedEquipment.append("Εξοπλισμός\n");
                        anyUpdated = true;
                    }
                    if (!newJustification.equals(justification)) {
                        updatedEquipment.append("Αιτιολόγηση\n");
                        anyUpdated = true;
                    }

                    if (anyUpdated) {
                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }
                        // Προετοιμασία της ενημέρωσης της βάσης δεδομένων (μόνο για τα πεδία που άλλαξαν)
                        String updateQuery = "UPDATE equipment SET ";
                        boolean first = true;
                        if (!newEquipment.equals(equipment)) {
                            updateQuery += "equipment = ?, ";
                            first = false;
                        }
                        if (!newJustification.equals(justification)) {
                            updateQuery += "justification_equipment = ?, ";
                            first = false;
                        }

                        if (!first) {
                            updateQuery = updateQuery.substring(0, updateQuery.length() - 2) + " WHERE id_equipment = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                                int index = 1;
                                if (!newEquipment.equals(equipment)) pstmt.setString(index++, newEquipment);
                                if (!newJustification.equals(justification)) pstmt.setString(index++, newJustification);

                                pstmt.setInt(index++, equipmentId);

                                int rowsUpdated = pstmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Η ενημέρωση του εξοπλισμού ολοκληρώθηκε επιτυχώς!",
                                            "Επιτυχία",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                } else {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Αποτυχία ενημέρωσης του εξοπλισμού. Δοκιμάστε ξανά.",
                                            "Σφάλμα",
                                            JOptionPane.ERROR_MESSAGE
                                    );
                                    return;
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Σφάλμα στη σύνδεση με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                                return;
                            }
                        }
                        String updatedEquipmentEntry = newEquipment + ": " + newJustification;
                        equipmentList.add(updatedEquipmentEntry);
                        equipmentField.setText(String.join(";\n", equipmentList));

                        addEquipmentsButton.setEnabled(true);
                        removeEquipmentsButton.setEnabled(true);
                        updateEquipmentButton.setEnabled(false);

                        equipmentInputField.setText("");
                        justificationEquipmentField.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά την επικοινωνία με τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            });
        });


        removeEquipmentsButton.addActionListener(e -> {
            if (equipmentList.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Δεν υπάρχουν εξοπλισμοί για διαγραφή.",
                        "Πληροφορία",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            // Δημιουργία λίστας επιλογής για διαγραφή
            String[] equipmentArray = equipmentList.toArray(new String[0]);
            JPanel panel = new JPanel();
            JComboBox<String> comboBox = new JComboBox<>(equipmentArray);
            comboBox.setPreferredSize(new Dimension(600, 25));
            panel.add(comboBox);

            int result = JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Επιλέξτε έναν εξοπλισμό για διαγραφή:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String selectedEquipment = (String) comboBox.getSelectedItem();
                if (selectedEquipment != null) {
                    // Διαχωρισμός σε equipment και justification
                    String[] parts = selectedEquipment.split(": ", 2);
                    String equipment = parts[0];
                    String justification = parts[1];

                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        String findIdSql = "SELECT id_equipment FROM equipment WHERE equipment = ? AND stoixeio_idStoixeio = ?";
                        try (PreparedStatement findIdStmt = conn.prepareStatement(findIdSql)) {
                            findIdStmt.setString(1, equipment);
                            findIdStmt.setString(2, idStoixeioField.getText());
                            try (ResultSet rs = findIdStmt.executeQuery()) {
                                if (rs.next()) {
                                    int id = rs.getInt("id_equipment");
                                    String setSessionFullName = "SET @full_name = ?";
                                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                                        setSessionStmt.executeUpdate();
                                    }
                                    String deleteSql = "DELETE FROM equipment WHERE id_equipment = ?";
                                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                                        deleteStmt.setInt(1, id);
                                        int rowsAffected = deleteStmt.executeUpdate();

                                        if (rowsAffected > 0) {
                                            JOptionPane.showMessageDialog(this, "Ο εξοπλισμός διαγράφηκε επιτυχώς από τη βάση δεδομένων.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                                            equipmentList.remove(selectedEquipment);
                                            updateEquipmentField();
                                        } else {
                                            JOptionPane.showMessageDialog(this, "Απέτυχε η διαγραφή του εξοπλισμού.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Ο εξοπλισμός δεν βρέθηκε στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά τη σύνδεση ή διαγραφή από τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Η καταχώρηση δεν έχει την αναμενόμενη μορφή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Προϊόντα
        productsList = new HashSet<>();
        JPanel productsPanel = new JPanel(new BorderLayout());
        productsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        productsPanel.add(new JLabel("Προϊόν:"), BorderLayout.NORTH);
        productInputField = new JTextField(20);
        productsPanel.add((productInputField), BorderLayout.CENTER);
        formPanel.add(productsPanel);

        JPanel justificationProductPanel = new JPanel(new BorderLayout());
        justificationProductPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        justificationProductPanel.add(new JLabel("Αιτιολόγηση Προϊόντος:"), BorderLayout.NORTH);
        justificationProductField = new JTextArea(3, 20);
        justificationProductField.setLineWrap(true);
        justificationProductField.setWrapStyleWord(true);
        justificationProductPanel.add(new JScrollPane(justificationProductField), BorderLayout.CENTER);
        formPanel.add(justificationProductPanel);

        JPanel addProductsPanel = new JPanel(new BorderLayout());
        addProductsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton addProductsButton = new JButton("Προσθήκη Προϊόντος");
        addProductsPanel.add(addProductsButton, BorderLayout.EAST);
        formPanel.add(addProductsPanel);

        productField = new JTextArea(3, 20);
        productField.setEditable(false);
        productField.setLineWrap(true);
        productField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(productField));

        // Κουμπί επεξεργασίας προϊόντος
        JPanel editProductsPanel = new JPanel(new BorderLayout());
        editProductsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton editProductsButton = new JButton("Επεξεργασία Προϊόντος");
        editProductsButton.setBackground(Color.GRAY);
        editProductsButton.setForeground(Color.WHITE);
        editProductsPanel.add(editProductsButton, BorderLayout.EAST);
        formPanel.add(editProductsPanel);

        // Κουμπί ενημέρωσης προϊόντος
        JPanel updateProductsPanel = new JPanel(new BorderLayout());
        updateProductsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton updateProductsButton = new JButton("Ενημέρωση Προϊόντος");
        updateProductsButton.setBackground(Color.GRAY);
        updateProductsButton.setForeground(Color.WHITE);
        updateProductsButton.setVisible(false);
        updateProductsPanel.add(updateProductsButton, BorderLayout.EAST);
        formPanel.add(updateProductsPanel);

        // Κουμπί για διαγραφή προϊόντος
        JPanel removeProductsPanel = new JPanel(new BorderLayout());
        removeProductsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeProductsButton = new JButton("Διαγραφή Προϊόντος");
        removeProductsButton.setBackground(Color.DARK_GRAY);
        removeProductsButton.setForeground(Color.WHITE);
        removeProductsPanel.add(removeProductsButton, BorderLayout.EAST);
        formPanel.add(removeProductsPanel);

        addProductsButton.addActionListener(e -> {
            String product = productInputField.getText().trim();
            String justification = justificationProductField.getText().trim();

            int stoixeioId = Integer.parseInt(idStoixeioField.getText().trim());

            // Έλεγχος αν έχει συμπληρωθεί το προϊόν αλλά όχι η αιτιολόγηση
            if (!product.isEmpty() && justification.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Αν έχετε συμπληρώσει προϊόν, πρέπει να συμπληρώσετε και την αιτιολόγηση!",
                        "Σφάλμα",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (!product.isEmpty()) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    // Έλεγχος αν το product υπάρχει ήδη για το συγκεκριμένο stoixeio_idStoixeio
                    String checkQuery = "SELECT COUNT(*) AS count FROM product WHERE stoixeio_idStoixeio = ? AND product = ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setInt(1, stoixeioId);
                        checkStmt.setString(2, product);

                        try (ResultSet rs = checkStmt.executeQuery()) {
                            rs.next();
                            int count = rs.getInt("count");

                            if (count > 0) {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Το προϊόν υπάρχει ήδη για αυτό το στοιχείο!",
                                        "Σφάλμα",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                return;
                            }
                        }
                    }
                    String setSessionFullName = "SET @full_name = ?";
                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                        setSessionStmt.executeUpdate();
                    }
                    String insertQuery = "INSERT INTO product (stoixeio_idStoixeio, product, justification_product) VALUES (?, ?, ?)";

                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, stoixeioId);
                        insertStmt.setString(2, product);
                        insertStmt.setString(3, justification);

                        insertStmt.executeUpdate();

                        JOptionPane.showMessageDialog(
                                null,
                                "Το προϊόν προστέθηκε επιτυχώς!",
                                "Επιτυχία",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        String productEntry = product + ": " + justification;
                        productsList.add(productEntry);

                        productField.setText(String.join(";\n", productsList));

                        productInputField.setText("");
                        justificationProductField.setText("");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Προέκυψε σφάλμα κατά την πρόσβαση στη βάση δεδομένων: " + ex.getMessage(),
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Το πεδίο του προϊόντος είναι κενό!",
                        "Πληροφορία",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        editProductsButton.addActionListener(e -> {
            String[] productArray = productsList.toArray(new String[0]);
            String selectedProduct = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε προϊόν για επεξεργασία:",
                    "Επεξεργασία Προϊόντος",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    productArray,
                    productArray.length > 0 ? productArray[0] : null);

            if (selectedProduct == null) {
                return;
            }

            String[] parts = selectedProduct.split(":", 2);
            String product = parts[0].trim();
            String justification = parts[1].trim();

            productInputField.setText(product);
            justificationProductField.setText(justification);

            productsList.remove(selectedProduct);
            updateProductField();

            updateProductsButton.setVisible(true);
            addProductsButton.setEnabled(false);
            removeProductsButton.setEnabled(false);
            updateProductsButton.setEnabled(true);

            updateProductsButton.addActionListener(updateEvent -> {
                String newProduct = productInputField.getText();
                String newJustification = justificationProductField.getText();

                // Έλεγχος αν έχει συμπληρωθεί το προϊόν αλλά όχι η αιτιολόγηση
                if (!newProduct.isEmpty() && newJustification.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Αν έχετε συμπληρώσει το προϊόν, πρέπει να συμπληρώσετε και την αιτιολόγηση!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Έλεγχος αν δεν έχει αλλάξει τίποτα
                if (newProduct.equals(product) && newJustification.equals(justification)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Δεν έγινε καμία αλλαγή στο Προϊόν ή την Αιτιολόγηση.",
                            "Πληροφόρηση",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    String unchangedProductEntry = product + ": " + justification;
                    productsList.add(unchangedProductEntry);

                    productField.setText(String.join(";\n", productsList));

                    productInputField.setText("");
                    justificationProductField.setText("");

                    return;
                }

                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String findIdQuery = "SELECT id_product FROM product WHERE product = ? AND stoixeio_idStoixeio = ?";
                    int productId = -1;

                    try (PreparedStatement findIdStmt = conn.prepareStatement(findIdQuery)) {
                        findIdStmt.setString(1, product);
                        findIdStmt.setString(2, idStoixeioField.getText());

                        ResultSet rs = findIdStmt.executeQuery();
                        if (rs.next()) {
                            productId = rs.getInt("id_product");
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Το επιλεγμένο προϊόν δεν βρέθηκε στη βάση!",
                                    "Σφάλμα",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                    }

                    // Έλεγχος αν ο νέος χώρος υπάρχει ήδη
                    String checkQuery = "SELECT COUNT(*) FROM product WHERE product = ? AND stoixeio_idStoixeio = ? AND product != ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setString(1, newProduct);
                        checkStmt.setString(2, idStoixeioField.getText());
                        checkStmt.setString(3, product);

                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Το νέο προϊόν υπάρχει ήδη για αυτό το στοιχείο.",
                                    "Σφάλμα",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                    }

                    // Αρχικοποίηση μιας λίστας για τα πεδία που έχουν ενημερωθεί
                    StringBuilder updatedProduct = new StringBuilder("Τα εξής πεδία ενημερώθηκαν:\n");

                    boolean anyUpdated = false;
                    if (!newProduct.equals(product)) {
                        updatedProduct.append("Προϊόν\n");
                        anyUpdated = true;
                    }
                    if (!newJustification.equals(justification)) {
                        updatedProduct.append("Αιτιολόγηση\n");
                        anyUpdated = true;
                    }

                    if (anyUpdated) {
                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }
                        // Προετοιμασία της ενημέρωσης της βάσης δεδομένων (μόνο για τα πεδία που άλλαξαν)
                        String updateQuery = "UPDATE product SET ";
                        boolean first = true;
                        if (!newProduct.equals(product)) {
                            updateQuery += "product = ?, ";
                            first = false;
                        }
                        if (!newJustification.equals(justification)) {
                            updateQuery += "justification_product = ?, ";
                            first = false;
                        }

                        if (!first) {
                            updateQuery = updateQuery.substring(0, updateQuery.length() - 2) + " WHERE id_product = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                                int index = 1;
                                if (!newProduct.equals(product)) pstmt.setString(index++, newProduct);
                                if (!newJustification.equals(justification)) pstmt.setString(index++, newJustification);

                                pstmt.setInt(index++, productId);

                                int rowsUpdated = pstmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Η ενημέρωση του προϊόντος ολοκληρώθηκε επιτυχώς!",
                                            "Επιτυχία",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                } else {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Αποτυχία ενημέρωσης του προϊόντος. Δοκιμάστε ξανά.",
                                            "Σφάλμα",
                                            JOptionPane.ERROR_MESSAGE
                                    );
                                    return;
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Σφάλμα στη σύνδεση με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                                return;
                            }
                        }
                        String updatedProductEntry = newProduct + ": " + newJustification;
                        productsList.add(updatedProductEntry);
                        productField.setText(String.join(";\n", productsList));

                        addProductsButton.setEnabled(true);
                        removeProductsButton.setEnabled(true);
                        updateProductsButton.setEnabled(false);

                        productInputField.setText("");
                        justificationProductField.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά την επικοινωνία με τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            });
        });


        removeProductsButton.addActionListener(e -> {
            if (productsList.isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Δεν υπάρχουν προϊόντα για διαγραφή.",
                        "Πληροφορία",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            // Δημιουργία λίστας επιλογής για διαγραφή
            String[] productArray = productsList.toArray(new String[0]);
            JPanel panel = new JPanel();
            JComboBox<String> comboBox = new JComboBox<>(productArray);
            comboBox.setPreferredSize(new Dimension(600, 25));
            panel.add(comboBox);

            int result = JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Επιλέξτε ένα προϊόν για διαγραφή:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String selectedProduct = (String) comboBox.getSelectedItem();
                if (selectedProduct != null) {
                    String[] parts = selectedProduct.split(": ", 2);
                    String product = parts[0];
                    String justification = parts[1];

                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        String findIdSql = "SELECT id_product FROM product WHERE product = ? AND stoixeio_idStoixeio = ?";
                        try (PreparedStatement findIdStmt = conn.prepareStatement(findIdSql)) {
                            findIdStmt.setString(1, product);
                            findIdStmt.setString(2, idStoixeioField.getText());
                            try (ResultSet rs = findIdStmt.executeQuery()) {
                                if (rs.next()) {
                                    int id = rs.getInt("id_product");
                                    String setSessionFullName = "SET @full_name = ?";
                                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                                        setSessionStmt.executeUpdate();
                                    }
                                    // Διαγραφή της εγκατάστασης με το συγκεκριμένο ID
                                    String deleteSql = "DELETE FROM product WHERE id_product = ?";
                                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                                        deleteStmt.setInt(1, id);
                                        int rowsAffected = deleteStmt.executeUpdate();

                                        if (rowsAffected > 0) {
                                            JOptionPane.showMessageDialog(this, "Το προϊόν διαγράφηκε επιτυχώς από τη βάση δεδομένων.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                                            productsList.remove(selectedProduct);
                                            updateProductField();
                                        } else {
                                            JOptionPane.showMessageDialog(this, "Απέτυχε η διαγραφή του προϊόντος.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Το προϊόν δεν βρέθηκε στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά τη σύνδεση ή διαγραφή από τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Η καταχώρηση δεν έχει την αναμενόμενη μορφή.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Ιστορικά Δεδομένα
        JPanel historicalDataPanel = new JPanel(new BorderLayout());
        historicalDataPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        historicalDataPanel.add(new JLabel("Ιστορικά Δεδομένα (έως 700 λέξεις):"), BorderLayout.NORTH);
        historical_dataField = new JTextArea(5, 20);
        historical_dataField.setLineWrap(true);
        historical_dataField.setWrapStyleWord(true);
        historical_dataField.setEditable(false);
        historicalDataPanel.add(new JScrollPane(historical_dataField), BorderLayout.CENTER);
        formPanel.add(historicalDataPanel);

        JPanel editHistoricalDataPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editHistoricalDataButton = new JButton("Επεξεργασία Ιστορικών Δεδομένων");
        editHistoricalDataButton.setBackground(Color.GRAY);
        editHistoricalDataButton.setForeground(Color.WHITE);
        editHistoricalDataPanel.add(editHistoricalDataButton);

        JPanel saveHistoricalDataPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveHistoricalDataButton = new JButton("Αποθήκευση Ιστορικών Δεδομένων");
        saveHistoricalDataPanel.add(saveHistoricalDataButton);

        JPanel cancelHistoricalDataPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelHistoricalDataButton = new JButton("Ακύρωση");
        cancelHistoricalDataButton.setBackground(Color.lightGray);
        cancelHistoricalDataButton.setForeground(Color.WHITE);
        cancelHistoricalDataPanel.add(cancelHistoricalDataButton);

        JPanel HistoricalDataPanel = new JPanel(new BorderLayout());
        HistoricalDataPanel.add(saveHistoricalDataPanel, BorderLayout.CENTER);
        HistoricalDataPanel.add(cancelHistoricalDataPanel, BorderLayout.EAST);
        HistoricalDataPanel.add(editHistoricalDataPanel, BorderLayout.WEST);

        formPanel.add(HistoricalDataPanel);

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editHistoricalDataButton.addActionListener(e -> {
            initialHistoricalDataText = historical_dataField.getText();
            historical_dataField.setEditable(true);
            historical_dataField.requestFocus();
            saveHistoricalDataButton.setEnabled(true);
            cancelHistoricalDataButton.setEnabled(true);
            editHistoricalDataButton.setEnabled(false);
        });

        // Ενέργεια για το κουμπί "Αποθήκευση"
        saveHistoricalDataButton.addActionListener(e -> {
            historical_dataField.setEditable(false);
            saveHistoricalDataButton.setEnabled(false);
            cancelHistoricalDataButton.setEnabled(false);
            editHistoricalDataButton.setEnabled(true);

            int idStoixeio = Integer.parseInt(idStoixeioField.getText());
            String newHistoricalData = historical_dataField.getText();

            if (newHistoricalData.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Τα ιστορικά δεδομένα είναι υποχρεωτικά.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] words = newHistoricalData.split("\\s+");
            if (words.length > 700) {
                JOptionPane.showMessageDialog(null, "Τα ιστορικά δεδομένα δεν πρέπει να υπερβαίνουν τις 700 λέξεις", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newHistoricalData.equals(initialHistoricalDataText)) {
                JOptionPane.showMessageDialog(null, "Τα ιστορικά δεδομένα δεν έχουν αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String updateQuery = "UPDATE stoixeio SET historical_data = ? WHERE idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newHistoricalData);
                pstmt.setInt(2, idStoixeio);

                // Εκτέλεση της ενημέρωσης
                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Τα ιστορικα δεδομένα αποθηκεύτηκαν επιτυχώς στη βάση.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                    initialHistoricalDataText = newHistoricalData;
                } else {
                    JOptionPane.showMessageDialog(null, "Η ενημέρωση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            historical_dataField.setEditable(false);
            saveHistoricalDataButton.setEnabled(false);
            cancelHistoricalDataButton.setEnabled(false);
            editHistoricalDataButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelHistoricalDataButton.addActionListener(e -> {
            historical_dataField.setText(initialHistoricalDataText);
            historical_dataField.setEditable(false);
            saveHistoricalDataButton.setEnabled(false);
            cancelHistoricalDataButton.setEnabled(false);
            editHistoricalDataButton.setEnabled(true);
        });


        JPanel importancePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        importancePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        importancePanel.add(new JLabel("Η σημασία του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς σήμερα"));
        formPanel.add(importancePanel);

        // Δημιουργία πεδίου για Members
        JPanel membersPanel = new JPanel(new BorderLayout());
        membersPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        membersPanel.add(new JLabel("Σημασία για τα μέλη της κοινότητας (100 έως 300 λέξεις):"), BorderLayout.NORTH);
        membersField = new JTextArea(3,20);
        membersField.setLineWrap(true);
        membersField.setWrapStyleWord(true);
        membersField.setEditable(false);
        membersPanel.add(new JScrollPane(membersField), BorderLayout.CENTER);
        formPanel.add(membersPanel);

        JPanel editMembersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editMembersButton = new JButton("Επεξεργασία");
        editMembersButton.setBackground(Color.GRAY);
        editMembersButton.setForeground(Color.WHITE);
        editMembersPanel.add(editMembersButton);

        JPanel saveMembersPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveMembersButton = new JButton("Αποθήκευση");
        saveMembersPanel.add(saveMembersButton);

        JPanel cancelMembersPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelMembersButton = new JButton("Ακύρωση");
        cancelMembersButton.setBackground(Color.lightGray);
        cancelMembersButton.setForeground(Color.WHITE);
        cancelMembersPanel.add(cancelMembersButton);

        JPanel MembersPanel = new JPanel(new BorderLayout());
        MembersPanel.add(saveMembersPanel, BorderLayout.CENTER);
        MembersPanel.add(cancelMembersPanel, BorderLayout.EAST);
        MembersPanel.add(editMembersPanel, BorderLayout.WEST);

        formPanel.add(MembersPanel);

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editMembersButton.addActionListener(e -> {
            initialMembersText = membersField.getText();
            membersField.setEditable(true);
            membersField.requestFocus();
            saveMembersButton.setEnabled(true);
            cancelMembersButton.setEnabled(true);
            editMembersButton.setEnabled(false);
        });

        saveMembersButton.addActionListener(e -> {
            String newMembersText = membersField.getText();
            int idStoixeio = Integer.parseInt(idStoixeioField.getText());

            if (newMembersText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Σημασία για τα μέλη της κοινότητας' δεν μπορεί να είναι κενό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Έλεγχος για το μήκος (100 έως 300 λέξεις)
            String[] words = newMembersText.split("\\s+");
            if (words.length < 100 || words.length > 300) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Σημασία για τα μέλη της κοινότητας' πρέπει να περιέχει από 100 έως 300 λέξεις.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newMembersText.equals(initialMembersText)) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Σημασία για τα μέλη της κοινότητας' δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String updateQuery = "UPDATE importance SET members = ? WHERE stoixeio_idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newMembersText);
                pstmt.setInt(2, idStoixeio);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Η σημασία για τα μέλη της κοινότητας αποθηκεύτηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η αποθήκευση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            membersField.setEditable(false);
            saveMembersButton.setEnabled(false);
            cancelMembersButton.setEnabled(false);
            editMembersButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelMembersButton.addActionListener(e -> {
            membersField.setText(initialMembersText);
            membersField.setEditable(false);
            saveMembersButton.setEnabled(false);
            cancelMembersButton.setEnabled(false);
            editMembersButton.setEnabled(true);
        });



        // Δημιουργία πεδίου για Society
        JPanel societyPanel = new JPanel(new BorderLayout());
        societyPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        societyPanel.add(new JLabel("Σημασία για την σύγχρονη Ελληνική κοινωνία (100 έως 200 λέξεις):"), BorderLayout.NORTH);
        societyField = new JTextArea(3,20);
        societyField.setLineWrap(true);
        societyField.setWrapStyleWord(true);
        societyField.setEditable(false);
        societyPanel.add(new JScrollPane(societyField), BorderLayout.CENTER);
        formPanel.add(societyPanel);

        JPanel editSocietyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editSocietyButton = new JButton("Επεξεργασία");
        editSocietyButton.setBackground(Color.GRAY);
        editSocietyButton.setForeground(Color.WHITE);
        editSocietyPanel.add(editSocietyButton);

        JPanel saveSocietyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveSocietyButton = new JButton("Αποθήκευση");
        saveSocietyPanel.add(saveSocietyButton);

        JPanel cancelSocietyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelSocietyButton = new JButton("Ακύρωση");
        cancelSocietyButton.setBackground(Color.lightGray);
        cancelSocietyButton.setForeground(Color.WHITE);
        cancelSocietyPanel.add(cancelSocietyButton);

        JPanel SocietyPanel = new JPanel(new BorderLayout());
        SocietyPanel.add(saveSocietyPanel, BorderLayout.CENTER);
        SocietyPanel.add(cancelSocietyPanel, BorderLayout.EAST);
        SocietyPanel.add(editSocietyPanel, BorderLayout.WEST);

        formPanel.add(SocietyPanel);

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editSocietyButton.addActionListener(e -> {
            initialSocietyText = societyField.getText();
            societyField.setEditable(true);
            societyField.requestFocus();
            saveSocietyButton.setEnabled(true);
            cancelSocietyButton.setEnabled(true);
            editSocietyButton.setEnabled(false);
        });

        saveSocietyButton.addActionListener(e -> {
            String newSocietyText = societyField.getText();
            int idStoixeio = Integer.parseInt(idStoixeioField.getText());

            // Έλεγχος αν το πεδίο είναι κενό
            if (newSocietyText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Σημασία για την σύγχρονη Ελληνική κοινωνία' δεν μπορεί να είναι κενό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Έλεγχος για το μήκος (100 έως 200 λέξεις)
            String[] words = newSocietyText.split("\\s+");
            if (words.length < 100 || words.length > 200) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Σημασία για την σύγχρονη Ελληνική κοινωνία' πρέπει να περιέχει από 100 έως 200 λέξεις.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newSocietyText.equals(initialSocietyText)) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Σημασία για την σύγχρονη Ελληνική κοινωνία' δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String updateQuery = "UPDATE importance SET society = ? WHERE stoixeio_idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newSocietyText);
                pstmt.setInt(2, idStoixeio);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Η σημασία για την σύγχρονη Ελληνική κοινωνία αποθηκεύτηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η αποθήκευση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            // Ενημέρωση της διεπαφής χρήστη
            societyField.setEditable(false);
            saveSocietyButton.setEnabled(false);
            cancelSocietyButton.setEnabled(false);
            editSocietyButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelSocietyButton.addActionListener(e -> {
            societyField.setText(initialSocietyText);
            societyField.setEditable(false);
            saveSocietyButton.setEnabled(false);
            cancelSocietyButton.setEnabled(false);
            editSocietyButton.setEnabled(true);
        });




        // Δημιουργία πεδίου για Community
        JPanel communityPanel = new JPanel(new BorderLayout());
        communityPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        communityPanel.add(new JLabel("Συμμετοχή της κοινότητας στην προετοιμασία της εγγραφής του στο Εθνικό Ευρετήριο Άυλης Πολιτιστικής Κληρονομιάς της Ελλάδας (300 έως 500 λέξεις):"), BorderLayout.NORTH);
        communityField = new JTextArea(3,20);
        communityField.setLineWrap(true);
        communityField.setWrapStyleWord(true);
        communityField.setEditable(false);
        communityPanel.add(new JScrollPane(communityField), BorderLayout.CENTER);
        formPanel.add(communityPanel);

        JPanel editCommunityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editCommunityButton = new JButton("Επεξεργασία");
        editCommunityButton.setBackground(Color.GRAY);
        editCommunityButton.setForeground(Color.WHITE);
        editCommunityPanel.add(editCommunityButton);

        JPanel saveCommunityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveCommunityButton = new JButton("Αποθήκευση");
        saveCommunityPanel.add(saveCommunityButton);

        JPanel cancelCommunityPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelCommunityButton = new JButton("Ακύρωση");
        cancelCommunityButton.setBackground(Color.lightGray);
        cancelCommunityButton.setForeground(Color.WHITE);
        cancelCommunityPanel.add(cancelCommunityButton);

        JPanel CommunityPanel = new JPanel(new BorderLayout());
        CommunityPanel.add(saveCommunityPanel, BorderLayout.CENTER);
        CommunityPanel.add(cancelCommunityPanel, BorderLayout.EAST);
        CommunityPanel.add(editCommunityPanel, BorderLayout.WEST);

        formPanel.add(CommunityPanel);

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editCommunityButton.addActionListener(e -> {
            initialCommunityText = communityField.getText();
            communityField.setEditable(true);
            communityField.requestFocus();
            saveCommunityButton.setEnabled(true);
            cancelCommunityButton.setEnabled(true);
            editCommunityButton.setEnabled(false);
        });

        saveCommunityButton.addActionListener(e -> {
            String newCommunityText = communityField.getText();
            int idStoixeio = Integer.parseInt(idStoixeioField.getText());

            // Έλεγχος αν το πεδίο είναι κενό
            if (newCommunityText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Συμμετοχή της κοινότητας στην προετοιμασία της εγγραφής του στο Εθνικό Ευρετήριο Άυλης Πολιτιστικής Κληρονομιάς της Ελλάδας' δεν μπορεί να είναι κενό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] words = newCommunityText.split("\\s+");
            if (words.length < 300 || words.length > 500) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Συμμετοχή της κοινότητας στην προετοιμασία της εγγραφής του στο Εθνικό Ευρετήριο Άυλης Πολιτιστικής Κληρονομιάς της Ελλάδας' πρέπει να περιέχει από 300 έως 500 λέξεις.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newCommunityText.equals(initialCommunityText)) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Συμμετοχή της κοινότητας στην προετοιμασία της εγγραφής του στο Εθνικό Ευρετήριο Άυλης Πολιτιστικής Κληρονομιάς της Ελλάδας' δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String updateQuery = "UPDATE importance SET community = ? WHERE stoixeio_idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newCommunityText);
                pstmt.setInt(2, idStoixeio);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Η Συμμετοχή της κοινότητας στην προετοιμασία της εγγραφής του στο Εθνικό Ευρετήριο Άυλης Πολιτιστικής Κληρονομιάς της Ελλάδας αποθηκεύτηκε επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η αποθήκευση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            communityField.setEditable(false);
            saveCommunityButton.setEnabled(false);
            cancelCommunityButton.setEnabled(false);
            editCommunityButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelCommunityButton.addActionListener(e -> {
            communityField.setText(initialCommunityText);
            communityField.setEditable(false);
            saveCommunityButton.setEnabled(false);
            cancelCommunityButton.setEnabled(false);
            editCommunityButton.setEnabled(true);
        });


        JPanel preservationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        preservationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        preservationPanel.add(new JLabel("Διαφύλαξη/ανάδειξη του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς"));
        formPanel.add(preservationPanel);

        // Δημιουργία πεδίου για Transmission
        JPanel transmissionPanel = new JPanel(new BorderLayout());
        transmissionPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        transmissionPanel.add(new JLabel("Τρόποι μετάδοσης στις νεότερες γενιές (200 έως 300 λέξεις):"), BorderLayout.NORTH);
        transmissionField = new JTextArea(3,20);
        transmissionField.setLineWrap(true);
        transmissionField.setWrapStyleWord(true);
        transmissionField.setEditable(false);
        transmissionPanel.add(new JScrollPane(transmissionField), BorderLayout.CENTER);
        formPanel.add(transmissionPanel);

        JPanel editTransmissionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editTransmissionButton = new JButton("Επεξεργασία");
        editTransmissionButton.setBackground(Color.GRAY);
        editTransmissionButton.setForeground(Color.WHITE);
        editTransmissionPanel.add(editTransmissionButton);

        JPanel saveTransmissionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveTransmissionButton = new JButton("Αποθήκευση");
        saveTransmissionPanel.add(saveTransmissionButton);

        JPanel cancelTransmissionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelTransmissionButton = new JButton("Ακύρωση");
        cancelTransmissionButton.setBackground(Color.lightGray);
        cancelTransmissionButton.setForeground(Color.WHITE);
        cancelTransmissionPanel.add(cancelTransmissionButton);

        JPanel TransmissionPanel = new JPanel(new BorderLayout());
        TransmissionPanel.add(saveTransmissionPanel, BorderLayout.CENTER);
        TransmissionPanel.add(cancelTransmissionPanel, BorderLayout.EAST);
        TransmissionPanel.add(editTransmissionPanel, BorderLayout.WEST);

        formPanel.add(TransmissionPanel);

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editTransmissionButton.addActionListener(e -> {
            initialTransmissionText = transmissionField.getText();
            transmissionField.setEditable(true);
            transmissionField.requestFocus();
            saveTransmissionButton.setEnabled(true);
            cancelTransmissionButton.setEnabled(true);
            editTransmissionButton.setEnabled(false);
        });

        saveTransmissionButton.addActionListener(e -> {
            String newTransmissionText = transmissionField.getText();
            int idStoixeio = Integer.parseInt(idStoixeioField.getText());

            // Έλεγχος αν το πεδίο είναι κενό
            if (newTransmissionText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Τρόποι μετάδοσης στις νεότερες γενιές' δεν μπορεί να είναι κενό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] words = newTransmissionText.split("\\s+");
            if (words.length < 200 || words.length > 300) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Τρόποι μετάδοσης στις νεότερες γενιές' πρέπει να περιέχει από 200 έως 300 λέξεις.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newTransmissionText.equals(initialTransmissionText)) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Τρόποι μετάδοσης στις νεότερες γενιές' δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String updateQuery = "UPDATE preservation SET transmission = ? WHERE stoixeio_idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newTransmissionText);
                pstmt.setInt(2, idStoixeio);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Οι τρόποι μετάδοσης στις νεότερες γενιές αποθηκεύτηκαν επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η αποθήκευση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            transmissionField.setEditable(false);
            saveTransmissionButton.setEnabled(false);
            cancelTransmissionButton.setEnabled(false);
            editTransmissionButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelTransmissionButton.addActionListener(e -> {
            transmissionField.setText(initialTransmissionText);
            transmissionField.setEditable(false);
            saveTransmissionButton.setEnabled(false);
            cancelTransmissionButton.setEnabled(false);
            editTransmissionButton.setEnabled(true);
        });



        // Δημιουργία πεδίου για Existing
        JPanel existingPanel = new JPanel(new BorderLayout());
        existingPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        existingPanel.add(new JLabel("Μέτρα διαφύλαξης/ανάδειξης του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς που έχουν ληφθεί στο παρελθόν ή που εφαρμόζονται σήμερα (200 έως 300 λέξεις):"), BorderLayout.NORTH);
        existingField = new JTextArea(3,20);
        existingField.setLineWrap(true);
        existingField.setWrapStyleWord(true);
        existingField.setEditable(false);
        existingPanel.add(new JScrollPane(existingField), BorderLayout.CENTER);
        formPanel.add(existingPanel);

        JPanel editExistingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editExistingButton = new JButton("Επεξεργασία");
        editExistingButton.setBackground(Color.GRAY);
        editExistingButton.setForeground(Color.WHITE);
        editExistingPanel.add(editExistingButton);

        JPanel saveExistingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveExistingButton = new JButton("Αποθήκευση");
        saveExistingPanel.add(saveExistingButton);

        JPanel cancelExistingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelExistingButton = new JButton("Ακύρωση");
        cancelExistingButton.setBackground(Color.lightGray);
        cancelExistingButton.setForeground(Color.WHITE);
        cancelExistingPanel.add(cancelExistingButton);

        JPanel ExistingPanel = new JPanel(new BorderLayout());
        ExistingPanel.add(saveExistingPanel, BorderLayout.CENTER);
        ExistingPanel.add(cancelExistingPanel, BorderLayout.EAST);
        ExistingPanel.add(editExistingPanel, BorderLayout.WEST);

        formPanel.add(ExistingPanel);

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editExistingButton.addActionListener(e -> {
            initialExistingText = existingField.getText();
            existingField.setEditable(true);
            existingField.requestFocus();
            saveExistingButton.setEnabled(true);
            cancelExistingButton.setEnabled(true);
            editExistingButton.setEnabled(false);
        });

        saveExistingButton.addActionListener(e -> {
            String newExistingText = existingField.getText();
            int idStoixeio = Integer.parseInt(idStoixeioField.getText());

            // Έλεγχος αν το πεδίο είναι κενό
            if (newExistingText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Μέτρα διαφύλαξης/ανάδειξης του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς που έχουν ληφθεί στο παρελθόν ή που εφαρμόζονται σήμερα' δεν μπορεί να είναι κενό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] words = newExistingText.split("\\s+");
            if (words.length < 200 || words.length > 300) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Μέτρα διαφύλαξης/ανάδειξης του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς που έχουν ληφθεί στο παρελθόν ή που εφαρμόζονται σήμερα' πρέπει να περιέχει από 200 έως 300 λέξεις.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newExistingText.equals(initialExistingText)) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Μέτρα διαφύλαξης/ανάδειξης του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς που έχουν ληφθεί στο παρελθόν ή που εφαρμόζονται σήμερα' δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String updateQuery = "UPDATE preservation SET existing = ? WHERE stoixeio_idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newExistingText);
                pstmt.setInt(2, idStoixeio);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Τα μέτρα διαφύλαξης/ανάδειξης του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς που έχουν ληφθεί στο παρελθόν ή που εφαρμόζονται σήμερα αποθηκεύτηκαν επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η αποθήκευση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            existingField.setEditable(false);
            saveExistingButton.setEnabled(false);
            cancelExistingButton.setEnabled(false);
            editExistingButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelExistingButton.addActionListener(e -> {
            existingField.setText(initialExistingText);
            existingField.setEditable(false);
            saveExistingButton.setEnabled(false);
            cancelExistingButton.setEnabled(false);
            editExistingButton.setEnabled(true);
        });



        // Δημιουργία πεδίου για Future
        JPanel futurePanel = new JPanel(new BorderLayout());
        futurePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        futurePanel.add(new JLabel("Μέτρα διαφύλαξης/ανάδειξης που προτείνεται να εφαρμοστούν στο μέλλον (300 έως 500 λέξεις):"), BorderLayout.NORTH);
        futureField = new JTextArea(3,20);
        futureField.setLineWrap(true);
        futureField.setWrapStyleWord(true);
        futureField.setEditable(false);
        futurePanel.add(new JScrollPane(futureField), BorderLayout.CENTER);
        formPanel.add(futurePanel);

        JPanel editFuturePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editFutureButton = new JButton("Επεξεργασία");
        editFutureButton.setBackground(Color.GRAY);
        editFutureButton.setForeground(Color.WHITE);
        editFuturePanel.add(editFutureButton);

        JPanel saveFuturePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveFutureButton = new JButton("Αποθήκευση");
        saveFuturePanel.add(saveFutureButton);

        JPanel cancelFuturePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelFutureButton = new JButton("Ακύρωση");
        cancelFutureButton.setBackground(Color.lightGray);
        cancelFutureButton.setForeground(Color.WHITE);
        cancelFuturePanel.add(cancelFutureButton);

        JPanel FuturePanel = new JPanel(new BorderLayout());
        FuturePanel.add(saveFuturePanel, BorderLayout.CENTER);
        FuturePanel.add(cancelFuturePanel, BorderLayout.EAST);
        FuturePanel.add(editFuturePanel, BorderLayout.WEST);

        formPanel.add(FuturePanel);

        // Ενέργεια για το κουμπί "Επεξεργασία"
        editFutureButton.addActionListener(e -> {
            initialFutureText = futureField.getText();
            futureField.setEditable(true);
            futureField.requestFocus();
            saveFutureButton.setEnabled(true);
            cancelFutureButton.setEnabled(true);
            editFutureButton.setEnabled(false);
        });

        saveFutureButton.addActionListener(e -> {
            String newFutureText = futureField.getText();
            int idStoixeio = Integer.parseInt(idStoixeioField.getText());

            // Έλεγχος αν το πεδίο είναι κενό
            if (newFutureText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Μέτρα διαφύλαξης/ανάδειξης που προτείνεται να εφαρμοστούν στο μέλλον' δεν μπορεί να είναι κενό.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] words = newFutureText.split("\\s+");
            if (words.length < 300 || words.length > 500) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Μέτρα διαφύλαξης/ανάδειξης που προτείνεται να εφαρμοστούν στο μέλλον' πρέπει να περιέχει από 300 έως 500 λέξεις.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newFutureText.equals(initialFutureText)) {
                JOptionPane.showMessageDialog(null, "Το πεδίο 'Μέτρα διαφύλαξης/ανάδειξης που προτείνεται να εφαρμοστούν στο μέλλον' δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String updateQuery = "UPDATE preservation SET future = ? WHERE stoixeio_idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newFutureText);
                pstmt.setInt(2, idStoixeio);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Τα μέτρα διαφύλαξης/ανάδειξης που προτείνεται να εφαρμοστούν στο μέλλον αποθηκεύτηκαν επιτυχώς.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Η αποθήκευση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
            futureField.setEditable(false);
            saveFutureButton.setEnabled(false);
            cancelFutureButton.setEnabled(false);
            editFutureButton.setEnabled(true);
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelFutureButton.addActionListener(e -> {
            futureField.setText(initialFutureText);
            futureField.setEditable(false);
            saveFutureButton.setEnabled(false);
            cancelFutureButton.setEnabled(false);
            editFutureButton.setEnabled(true);
        });



        // Αρχικοποίηση της λίστας τεκμηρίων
        evidenceList = new HashSet<>();

        JPanel evidencePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        evidencePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        evidencePanel.add(new JLabel("Συμπληρωματικά Τεκμήρια"));
        formPanel.add(evidencePanel);

        // Δημιουργία ComboBox για κατηγορία
        JPanel categoryPanel = new JPanel( new BorderLayout());
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        categoryComboBox = new JComboBox<>(new String[]{"Επιλέξτε κατηγορία","video", "sounds", "images", "files", "link", "maps"});
        categoryPanel.add(new JLabel("Κατηγορία:"), BorderLayout.NORTH);
        categoryPanel.add(categoryComboBox);
        formPanel.add(categoryPanel);

        // Πεδίο για το path του αρχείου
        JPanel filePanel = new JPanel( new BorderLayout());
        filePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        filePathField = new JTextField(20);
        filePathField.setEnabled(false);
        filePanel.add(new JLabel("Path Αρχείου / Σύνδεσμος:"), BorderLayout.NORTH);
        filePanel.add(filePathField);
        formPanel.add(filePanel);

        // Κουμπί για την επιλογή αρχείου
        JPanel selectFilePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selectFilePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton selectFileButton = new JButton("Επιλογή Αρχείου  ή Εισαγωγή Συνδέσμου");
        selectFileButton.setEnabled(false);
        selectFilePanel.add(selectFileButton);
        formPanel.add(selectFilePanel);

        JPanel datePanel = new JPanel( new BorderLayout());
        datePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        dateOfEntryField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
        dateOfEntryField.setColumns(20);
        dateOfEntryField.setEnabled(false);
        datePanel.add(new JLabel("Ημερομηνία Δημιουργίας (yyyy-MM-dd):"), BorderLayout.NORTH);
        datePanel.add(dateOfEntryField);
        formPanel.add(datePanel);

        JPanel uploadPanel = new JPanel( new BorderLayout());
        uploadPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        whoUploadedItField = new JTextField(20);
        whoUploadedItField.setEnabled(false);
        uploadPanel.add(new JLabel("Δημιουργήθηκε από:"), BorderLayout.NORTH);
        uploadPanel.add(whoUploadedItField);
        formPanel.add(uploadPanel);

        categoryComboBox.addActionListener(e -> {
            boolean enabled = categoryComboBox.getSelectedIndex() != 0;
            filePathField.setEnabled(enabled);
            selectFileButton.setEnabled(enabled);
            dateOfEntryField.setEnabled(enabled);
            whoUploadedItField.setEnabled(enabled);

            // Αν επιλεγεί το "link", το πεδίο filePathField θα επιτρέπει εισαγωγή URL και να κλείσει την επιλογή αρχείου
            if (categoryComboBox.getSelectedIndex() == 5) {
                selectFileButton.setText("Εισαγωγή Σύνδεσμου");
                filePathField.setEnabled(true);
            } else {
                selectFileButton.setText("Επιλογή Αρχείου");
            }
        });

        // Κουμπί για προσθήκη τεκμηρίων
        JPanel addEvidencePanel = new JPanel(new BorderLayout());
        addEvidencePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton addEvidenceButton = new JButton("Προσθήκη Τεκμηρίου");
        addEvidencePanel.add(addEvidenceButton, BorderLayout.EAST);
        formPanel.add(addEvidencePanel);

        // Πεδίο εμφάνισης για τεκμήρια
        evidenceField = new JTextArea(3, 20);
        evidenceField.setEditable(false);
        evidenceField.setLineWrap(true);
        evidenceField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(evidenceField));

        // Κουμπί για διαγραφή τεκμηρίων
        JPanel deleteEvidencePanel = new JPanel(new BorderLayout());
        deleteEvidencePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton deleteEvidenceButton = new JButton("Διαγραφή Τεκμηρίου");
        deleteEvidenceButton.setBackground(Color.DARK_GRAY);
        deleteEvidenceButton.setForeground(Color.WHITE);
        deleteEvidencePanel.add(deleteEvidenceButton, BorderLayout.EAST);
        formPanel.add(deleteEvidencePanel);

        // Κουμπί για επιλογή αρχείου ή εισαγωγή συνδέσμου
        selectFileButton.addActionListener(e -> {
            if (categoryComboBox.getSelectedIndex() == 5) {
                String link = JOptionPane.showInputDialog(this, "Εισάγετε το σύνδεσμο:");
                if (link != null && !link.isEmpty()) {
                    filePathField.setText(link);
                }
            } else {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        //Προσθήκη τεκμηρίων με έλεγχο κατηγορίας
        addEvidenceButton.addActionListener(e -> {
            String file_path = filePathField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            String dateOfEntry = dateOfEntryField.getText();
            String whoUploadedIt = whoUploadedItField.getText();

            // Έλεγχος αν το category δεν είναι κενό
            if (category != null && !category.equals("Επιλέξτε κατηγορία")) {
                // Αν το category έχει επιλεγεί, τα υπόλοιπα πεδία είναι υποχρεωτικά
                if (file_path.isEmpty() || dateOfEntry.isEmpty() || whoUploadedIt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Παρακαλώ συμπληρώστε όλα τα πεδία (Αρχείο, Ημερομηνία Δημιουργίας, Δημιουργήθηκε από) αν έχετε επιλέξει κατηγορία.");
                    return;
                }
            }

            if (category.equals("link") && file_path.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε ένα σύνδεσμο.");
                return;
            }

            if (!category.equals("link") && !file_path.isEmpty()) {
                File file = new File(file_path);
                if (!file.exists()) {
                    JOptionPane.showMessageDialog(this, "Το αρχείο δεν υπάρχει. Παρακαλώ επιλέξτε ένα έγκυρο αρχείο.");
                    return;
                }

                // Έλεγχος αν το αρχείο ταιριάζει με την κατηγορία
                if (!isFileValidForCategory(file, category)) {
                    JOptionPane.showMessageDialog(this, "Το αρχείο δεν ταιριάζει με την επιλεγμένη κατηγορία.");
                    return;
                }
            }
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String fileName;
                String fileType;
                long fileSize = 0;
                String mimeType;
                String fileHash;
                byte[] fileBytes = null;

                if (category.equals("link")) {
                    fileName = file_path;
                    fileType = "URL";
                    mimeType = "text/url";
                    fileHash = getLinkHash(file_path);
                } else {
                    File file = new File(file_path);
                    String fileNameWithExtension = file.getName();
                    fileName = fileNameWithExtension.contains(".")
                            ? fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'))
                            : fileNameWithExtension;
                    fileType = getFileType(file);
                    fileSize = file.length();
                    mimeType = getMimeType(file);
                    fileHash = getFileHash(file);

                    try (InputStream fileDataStream = new FileInputStream(file)) {
                        fileBytes = fileDataStream.readAllBytes();
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(this, "Παρουσιάστηκε πρόβλημα κατά την ανάγνωση του αρχείου.");
                        exception.printStackTrace();
                        return;
                    }
                }

                // Έλεγχος αν το αρχείο υπάρχει ήδη στη βάση
                String queryCheck = "SELECT COUNT(*) FROM evidence WHERE file_hash = ? AND stoixeio_idStoixeio = ?";
                boolean fileExists = false;
                try (PreparedStatement psCheck = conn.prepareStatement(queryCheck)) {
                    psCheck.setString(1, fileHash);
                    psCheck.setString(2, idStoixeioField.getText());
                    try (ResultSet rs = psCheck.executeQuery()) {
                        if (rs.next()) {
                            fileExists = rs.getInt(1) > 0;
                        }
                    }
                }

                if (fileExists) {
                    JOptionPane.showMessageDialog(this, "Το αρχείο υπάρχει ήδη για το στοιχείο.");
                    return;
                }
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                // Εισαγωγή στη βάση δεδομένων
                String queryInsert = "INSERT INTO evidence (stoixeio_idStoixeio, category, file_name, file_type, file_size, mime_type, file_hash, date_of_entry, who_uploaded_it, file_data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement psInsert = conn.prepareStatement(queryInsert)) {
                    psInsert.setString(1, idStoixeioField.getText());
                    psInsert.setString(2, category);
                    psInsert.setString(3, fileName);
                    psInsert.setString(4, fileType);
                    psInsert.setLong(5, fileSize);
                    psInsert.setString(6, mimeType);
                    psInsert.setString(7, fileHash);
                    psInsert.setString(8, dateOfEntry);
                    psInsert.setString(9, whoUploadedIt);
                    psInsert.setBytes(10, fileBytes);

                    int rowsInserted = psInsert.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(this, "Το αρχείο προστέθηκε με επιτυχία στη βάση δεδομένων.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Αποτυχία εισαγωγής του αρχείου στη βάση δεδομένων.");
                        return;
                    }
                }
                String evidenceEntry = category + ", " + fileName + ", " + fileHash + ", " + dateOfEntry + ", " + whoUploadedIt;


                evidenceList.add(evidenceEntry);

                evidenceField.setText(String.join(";\n", evidenceList));
                filePathField.setText("");
                dateOfEntryField.setText("");
                whoUploadedItField.setText("");
                categoryComboBox.setSelectedIndex(0);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Προέκυψε σφάλμα κατά τη διαδικασία. Δοκιμάστε ξανά.");
            }

        });

        deleteEvidenceButton.addActionListener(e -> {
            if (evidenceList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν τεκμήρια για διαγραφή.");
                return;
            }

            // Δημιουργία λίστας επιλογής για διαγραφή
            String[] evidenceArray = evidenceList.toArray(new String[0]);
            JPanel panel = new JPanel();
            JComboBox<String> comboBox = new JComboBox<>(evidenceArray);
            comboBox.setPreferredSize(new Dimension(600, 25));
            panel.add(comboBox);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Επιλέξτε μια απόδειξη για διαγραφή:",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String selectedEvidence = (String) comboBox.getSelectedItem();
                if (selectedEvidence != null) {
                    // Διαχωρισμός του evidenceEntry σε επιμέρους στοιχεία
                    String[] evidenceDetails = selectedEvidence.split(", ");
                    String category = evidenceDetails[0].trim();
                    String fileName = evidenceDetails[1].trim();
                    String fileHash = evidenceDetails[2].trim();
                    String dateOfEntry = evidenceDetails[3].trim();
                    String whoUploadedIt = evidenceDetails[4].trim();

                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        String selectQuery = "SELECT id_evidence FROM evidence WHERE file_hash = ? AND stoixeio_idStoixeio = ?";
                        try (PreparedStatement psSelect = conn.prepareStatement(selectQuery)) {
                            psSelect.setString(1, fileHash);
                            psSelect.setString(2, idStoixeioField.getText());

                            ResultSet rs = psSelect.executeQuery();
                            if (rs.next()) {
                                int evidenceId = rs.getInt("id_evidence");
                                String setSessionFullName = "SET @full_name = ?";
                                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                                    setSessionStmt.executeUpdate();
                                }
                                String deleteQuery = "DELETE FROM evidence WHERE id_evidence = ?";
                                try (PreparedStatement psDelete = conn.prepareStatement(deleteQuery)) {
                                    psDelete.setInt(1, evidenceId);
                                    int rowsAffected = psDelete.executeUpdate();

                                    if (rowsAffected > 0) {
                                        JOptionPane.showMessageDialog(this, "Το συμπληρωματικό τεκμήριο διαγράφηκε από τη βάση δεδομένων.");
                                    } else {
                                        JOptionPane.showMessageDialog(this, "Το συμπληρωματικό τεκμήριο δεν βρέθηκε στη βάση δεδομένων.");
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Το συμπληρωματικό τεκμήριο δεν βρέθηκε στη βάση δεδομένων.");
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αναζήτηση της απόδειξης στη βάση δεδομένων: " + ex.getMessage());
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Σφάλμα κατά τη σύνδεση με τη βάση δεδομένων: " + ex.getMessage());
                    }

                    evidenceList.remove(selectedEvidence);
                    updateEvidenceDisplay();
                }
            }
        });

        //Αρμόδια Πρόσωπα
        competentpersonsList = new HashSet<>();
        JPanel competentpersonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        competentpersonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        competentpersonsPanel.add(new JLabel("Αρμόδια Πρόσωπα:"));
        formPanel.add(competentpersonsPanel);

        // Πεδίο για το όνομα αρμόδιου προσώπου
        JPanel firstnamePanel = new JPanel( new BorderLayout());
        firstnamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        firstnameField = new JTextField(20);
        firstnamePanel.add(new JLabel("Όνομα:"), BorderLayout.NORTH);
        firstnamePanel.add(firstnameField);
        formPanel.add(firstnamePanel);

        // Πεδίο για το επώνυμο αρμόδιου προσώπου
        JPanel lastnamePanel = new JPanel( new BorderLayout());
        lastnamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        lastnameField = new JTextField(20);
        lastnamePanel.add(new JLabel("Επώνυμο:"), BorderLayout.NORTH);
        lastnamePanel.add(lastnameField);
        formPanel.add(lastnamePanel);

        // Πεδίο για το email αρμόδιου προσώπου
        JPanel emailcpPanel = new JPanel( new BorderLayout());
        emailcpPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        emailcpField = new JTextField(20);
        emailcpPanel.add(new JLabel("Email:"), BorderLayout.NORTH);
        emailcpPanel.add(emailcpField);
        formPanel.add(emailcpPanel);

        // Πεδίο για το τηλέφωνο αρμόδιου προσώπου
        JPanel telnumberPanel = new JPanel( new BorderLayout());
        telnumberPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        telnumberField = new JTextField(20);
        telnumberPanel.add(new JLabel("Τηλέφωνο:"), BorderLayout.NORTH);
        telnumberPanel.add(telnumberField);
        formPanel.add(telnumberPanel);

        // Πεδίο για την διεύθυνση αρμόδιου προσώπου
        JPanel addresscpPanel = new JPanel( new BorderLayout());
        addresscpPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        addresscpField = new JTextField(20);
        addresscpPanel.add(new JLabel("Διέυθυνση:"), BorderLayout.NORTH);
        addresscpPanel.add(addresscpField);
        formPanel.add(addresscpPanel);

        JPanel addcompetentpersonsPanel = new JPanel(new BorderLayout());
        addcompetentpersonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton addcompetentpersonsButton = new JButton("Προσθήκη Αρμόδιου Προσώπου");
        addcompetentpersonsPanel.add(addcompetentpersonsButton, BorderLayout.EAST);
        formPanel.add(addcompetentpersonsPanel);

        competentpersonsField = new JTextArea(7, 20);
        competentpersonsField.setEditable(false);
        competentpersonsField.setLineWrap(true);
        competentpersonsField.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(competentpersonsField));

        // Προσθήκη κουμπιού επεξεργασίας
        JPanel editcompetentpersonsPanel = new JPanel(new BorderLayout());
        editcompetentpersonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton editcompetentpersonsButton = new JButton("Επεξεργασία Αρμόδιου Προσώπου");
        editcompetentpersonsButton.setBackground(Color.GRAY);
        editcompetentpersonsButton.setForeground(Color.WHITE);

        editcompetentpersonsButton.setVisible(false);

        // Έλεγχος αν ο χρήστης είναι admin
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT userrole FROM user WHERE id_user = ?")) {

            stmt.setInt(1, id_user);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && "admin".equals(rs.getString("userrole"))) {

                    editcompetentpersonsButton.setVisible(true);
                    editcompetentpersonsButton.setBackground(Color.DARK_GRAY);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά την ανάκτηση δεδομένων από τη βάση.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
        editcompetentpersonsPanel.add(editcompetentpersonsButton, BorderLayout.EAST);
        formPanel.add(editcompetentpersonsPanel);

        // Προσθήκη κουμπιού ενημέρωσης
        JPanel updatecompetentpersonsPanel = new JPanel(new BorderLayout());
        updatecompetentpersonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton updatecompetentpersonsButton = new JButton("Ενημέρωση Αρμόδιου Προσώπου");
        updatecompetentpersonsButton.setBackground(Color.GRAY);
        updatecompetentpersonsButton.setForeground(Color.WHITE);
        updatecompetentpersonsButton.setVisible(false);
        updatecompetentpersonsPanel.add(updatecompetentpersonsButton, BorderLayout.EAST);
        formPanel.add(updatecompetentpersonsPanel);

        // Προσθήκη κουμπιού διαγραφής
        JPanel removecompetentpersonsPanel = new JPanel(new BorderLayout());
        removecompetentpersonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removecompetentpersonsButton = new JButton("Διαγραφή Αρμόδιου Προσώπου");
        removecompetentpersonsButton.setBackground(Color.DARK_GRAY);
        removecompetentpersonsButton.setForeground(Color.WHITE);

        removecompetentpersonsButton.setVisible(false);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT userrole FROM user WHERE id_user = ?")) {

            stmt.setInt(1, id_user);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && "admin".equals(rs.getString("userrole"))) {
                    removecompetentpersonsButton.setVisible(true);
                    removecompetentpersonsButton.setBackground(Color.DARK_GRAY);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά την ανάκτηση δεδομένων από τη βάση.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }

        removecompetentpersonsPanel.add(removecompetentpersonsButton, BorderLayout.EAST);
        formPanel.add(removecompetentpersonsPanel);

        addcompetentpersonsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();
                String emailcp = emailcpField.getText();
                String telnumber = telnumberField.getText();
                String addresscp = addresscpField.getText();
                String idStoixeio = idStoixeioField.getText();

                if (addresscp.isEmpty()) {
                    addresscp = "";
                }

                // Έλεγχος αν όλα τα πεδία είναι συμπληρωμένα
                if (firstname.isEmpty() || lastname.isEmpty() || emailcp.isEmpty() || telnumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Όλα τα πεδία είναι υποχρεωτικά!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Έλεγχος εγκυρότητας email
                if (!emailcp.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το email", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!telnumber.matches("^((\\+30)?[2-9][0-9]{9}|[2-9][0-9]{9})$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το τηλέφωνο", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Σύνδεση με τη βάση δεδομένων
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String setSessionFullName = "SET @full_name = ?";
                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                        setSessionStmt.executeUpdate();
                    }
                    int userId;
                    String checkQuery = "SELECT id_user, first_name, last_name, address, email, telephone FROM user WHERE email = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(checkQuery)) {
                        pstmt.setString(1, emailcp);

                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                userId = rs.getInt("id_user");

                                String addressFromDb = rs.getString("address");
                                if (addressFromDb == null || addressFromDb.isEmpty()) {
                                    addressFromDb = "";
                                }

                                String insertStoixeioUserQuery = "INSERT INTO user_stoixeio (stoixeio_id, user_id) VALUES (?, ?)";
                                try (PreparedStatement insertStoixeioUserPstmt = conn.prepareStatement(insertStoixeioUserQuery)) {
                                    insertStoixeioUserPstmt.setInt(1, Integer.parseInt(idStoixeio));
                                    insertStoixeioUserPstmt.setInt(2, userId);
                                    insertStoixeioUserPstmt.executeUpdate();
                                }
                                String competentEntry = "Όνομα: " + rs.getString("first_name") + "\n" +
                                        "Επώνυμο: " + rs.getString("last_name") + "\n" +
                                        "Email: " + rs.getString("email") + "\n" +
                                        "Τηλέφωνο: " + rs.getString("telephone") + "\n" +
                                        "Διεύθυνση: " + addressFromDb;

                                competentpersonsList.add(competentEntry);
                                competentpersonsField.setText(String.join(";\n", competentpersonsList));
                                JOptionPane.showMessageDialog(null, "Το αρμόδιο πρόσωπο υπάρχει ήδη και προστέθηκε στη λίστα.", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                 String insertQuery = "INSERT INTO user (first_name, last_name, password, email, telephone, address) VALUES (?, ?, ?, ?, ?, ?)";
                                 try (PreparedStatement insertpstmt = conn.prepareStatement(insertQuery,  Statement.RETURN_GENERATED_KEYS)) {

                                     insertpstmt.setString(1, firstname);
                                     insertpstmt.setString(2, lastname);
                                     insertpstmt.setString(3, emailcp);
                                     insertpstmt.setString(4, emailcp);
                                     insertpstmt.setString(5, telnumber);
                                     insertpstmt.setString(6, addresscp);

                                     int rowsInserted = insertpstmt.executeUpdate();
                                     if (rowsInserted > 0) {
                                         try (ResultSet generatedKeys = insertpstmt.getGeneratedKeys()) {
                                             if (generatedKeys.next()) {
                                                 userId = generatedKeys.getInt(1);

                                                 String insertStoixeioUserQuery = "INSERT INTO user_stoixeio (stoixeio_id, user_id) VALUES (?, ?)";
                                                 try (PreparedStatement insertStoixeioUserPstmt = conn.prepareStatement(insertStoixeioUserQuery)) {
                                                     insertStoixeioUserPstmt.setInt(1, Integer.parseInt(idStoixeio));
                                                     insertStoixeioUserPstmt.setInt(2, userId);
                                                     insertStoixeioUserPstmt.executeUpdate();
                                                 }
                                                 String competentEntry = "Όνομα: " + firstname + "\n" +
                                                         "Επώνυμο: " + lastname + "\n" +
                                                         "Email: " + emailcp + "\n" +
                                                         "Τηλέφωνο: " + telnumber;

                                                 competentEntry += "\nΔιεύθυνση: " + (addresscp != null && !addresscp.isEmpty() ? addresscp : "");


                                                 competentpersonsList.add(competentEntry);

                                                 // Εμφάνιση των υπεύθυνων ατόμων στο JTextArea
                                                 competentpersonsField.setText(String.join(";\n", competentpersonsList));
                                                 JOptionPane.showMessageDialog(null, "Το αρμόδιο πρόσωπο προστέθηκε επιτυχώς στη βάση και στη λίστα.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                                             }
                                         }
                                     } else {
                                         throw new SQLException("Η εισαγωγή του φορέα απέτυχε.");
                                     }
                                 }
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά την εισαγωγή στη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                firstnameField.setText("");
                lastnameField.setText("");
                addresscpField.setText("");
                emailcpField.setText("");
                telnumberField.setText("");
            }
        });


        // Προσθήκη κουμπιού για την επεξεργασία αρμόδιου προσώπου
        editcompetentpersonsButton.addActionListener(e -> {
            String[] competentArray = competentpersonsList.toArray(new String[0]);
            String selectedCompetent = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε αρμόδιο πρόσωπο για επεξεργασία:",
                    "Επεξεργασία Αρμόδιου Προσώπου",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    competentArray,
                    competentArray[0]);

            if (selectedCompetent == null) {
                return;
            }

            String[] competentParts = selectedCompetent.split("\n");
            String firstname = competentParts[0].split(": ")[1];
            String lastname = competentParts[1].split(": ")[1];
            String emailcp = competentParts[2].split(": ")[1];
            String telnumber = competentParts[3].split(": ")[1];

            String addresscp = competentParts[4].split(": ").length > 1
                    ? competentParts[4].split(": ")[1]
                    : "";

            firstnameField.setText(firstname);
            lastnameField.setText(lastname);
            emailcpField.setText(emailcp);
            telnumberField.setText(telnumber);
            addresscpField.setText(addresscp);

            updatecompetentpersonsButton.setVisible(true);
            addcompetentpersonsButton.setEnabled(false);
            removecompetentpersonsButton.setEnabled(false);

            updatecompetentpersonsButton.setEnabled(true);

            competentpersonsList.remove(selectedCompetent);

            competentpersonsField.setText("");
            for (String competentEntry : competentpersonsList) {
                competentpersonsField.append(competentEntry + ";\n");
            }

            updatecompetentpersonsButton.addActionListener(updateEvent -> {
                String newfirstname = firstnameField.getText();
                String newlastname = lastnameField.getText();
                String newemailcp = emailcpField.getText();
                String newtelnumber = telnumberField.getText();
                String newaddresscp = addresscpField.getText();


                // Έλεγχος αν όλα τα πεδία είναι συμπληρωμένα
                if (newfirstname.isEmpty() || newlastname.isEmpty() || newemailcp.isEmpty() || newtelnumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Όλα τα πεδία είναι υποχρεωτικά!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Έλεγχος για τη μορφή του email
                if (!newemailcp.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το email", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!newtelnumber.matches("^((\\+30)?[2-9][0-9]{9}|[2-9][0-9]{9})$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το τηλέφωνο", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return; // Επιστρέφει αν το τηλέφωνο δεν είναι έγκυρο
                }

                if (newfirstname.equals(firstname) && newlastname.equals(lastname) &&
                        newaddresscp.equals(addresscp) && newemailcp.equals(emailcp) &&
                        newtelnumber.equals(telnumber)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Δεν έγινε καμία αλλαγή στο αρμόδιο πρόσωπο.",
                            "Πληροφόρηση",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    String unchangedCompetentPersonsEntry = "Όνομα: " + firstname + "\n" +
                            "Επώνυμο: " + lastname + "\n" +
                            "Email: " + emailcp + "\n" +
                            "Τηλέφωνο: " + telnumber;

                    unchangedCompetentPersonsEntry += "\nΔιεύθυνση: " + (addresscp.isEmpty() ? "" : addresscp);

                    competentpersonsList.add(unchangedCompetentPersonsEntry);

                    competentpersonsField.setText("");
                    for (String competentEntry : competentpersonsList) {
                        competentpersonsField.append(competentEntry + ";\n");
                    }

                    addcompetentpersonsButton.setEnabled(true);
                    removecompetentpersonsButton.setEnabled(true);
                    updatecompetentpersonsButton.setEnabled(false);

                    firstnameField.setText("");
                    lastnameField.setText("");
                    addresscpField.setText("");
                    emailcpField.setText("");
                    telnumberField.setText("");

                    return;
                }
                int userId = -1;
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "SELECT id_user FROM user WHERE email = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, emailcp);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                userId = rs.getInt("id_user");
                            }
                        }
                    }


                    String checkQuery = "SELECT COUNT(*) FROM user WHERE (email = ?) AND id_user != ? AND email != ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                        checkStmt.setString(1, newemailcp);
                        checkStmt.setInt(2, userId);
                        checkStmt.setString(3, emailcp);
                        ResultSet rs = checkStmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(null, "Το email ανήκει ήδη σε άλλο αρμόδιο πρόσωπο.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                     StringBuilder updatedFields = new StringBuilder("Τα εξής πεδία ενημερώθηκαν:\n");

                    boolean anyUpdated = false;
                    if (!newfirstname.equals(firstname)) {
                        updatedFields.append("Όνομα\n");
                        anyUpdated = true;
                    }
                    if (!newlastname.equals(lastname)) {
                        updatedFields.append("Επώνυμο\n");
                        anyUpdated = true;
                    }
                    if (!newemailcp.equals(emailcp)) {
                        updatedFields.append("Email\n");
                        anyUpdated = true;
                    }
                    if (!newtelnumber.equals(telnumber)) {
                        updatedFields.append("Τηλέφωνο\n");
                        anyUpdated = true;
                    }
                    if (!newaddresscp.equals(addresscp)) {
                        updatedFields.append("Διεύθυνση\n");
                        anyUpdated = true;
                    }

                    if (anyUpdated) {
                        String setSessionFullName = "SET @full_name = ?";
                        try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                            setSessionStmt.setString(1, Login.UserSession.getFullName());
                            setSessionStmt.executeUpdate();
                        }
                        // Προετοιμασία της ενημέρωσης της βάσης δεδομένων (μόνο για τα πεδία που άλλαξαν)
                        String updateQuery = "UPDATE user SET ";
                        boolean first = true;
                        if (!newfirstname.equals(firstname)) {
                            updateQuery += "first_name = ?, ";
                            first = false;
                        }
                        if (!newlastname.equals(lastname)) {
                            updateQuery += "last_name = ?, ";
                            first = false;
                        }
                        if (!newemailcp.equals(emailcp)) {
                            updateQuery += "email = ?, ";
                            first = false;
                        }
                        if (!newtelnumber.equals(telnumber)) {
                            updateQuery += "telephone = ?, ";
                            first = false;
                        }
                        if (!newaddresscp.equals(addresscp)) {
                            updateQuery += "address = ?, ";
                            first = false;
                        }

                        if (!first) {
                            updateQuery = updateQuery.substring(0, updateQuery.length() - 2) + " WHERE id_user = ?";
                            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                                int index = 1;
                                if (!newfirstname.equals(firstname)) pstmt.setString(index++, newfirstname);
                                if (!newlastname.equals(lastname)) pstmt.setString(index++, newlastname);
                                if (!newemailcp.equals(emailcp)) pstmt.setString(index++, newemailcp);
                                if (!newtelnumber.equals(telnumber)) pstmt.setString(index++, newtelnumber);
                                if (!newaddresscp.equals(addresscp)) pstmt.setString(index++, newaddresscp);

                                pstmt.setInt(index++, userId);

                                int rowsUpdated = pstmt.executeUpdate();
                                if (rowsUpdated > 0) {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Η ενημέρωση του αρμοδίου προσώπου ολοκληρώθηκε επιτυχώς!",
                                            "Επιτυχία",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                } else {
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "Αποτυχία ενημέρωσης του αρμοδίου προσώπου. Δοκιμάστε ξανά.",
                                            "Σφάλμα",
                                            JOptionPane.ERROR_MESSAGE
                                    );
                                    return;
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Σφάλμα στη σύνδεση με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                                ex.printStackTrace();
                                return;
                            }
                        }

                        String updatedcompetentpersonsEntry = "Όνομα: " + newfirstname + "\n" +
                                "Επώνυμο: " + newlastname + "\n" +
                                "Email: " + newemailcp + "\n" +
                                "Τηλέφωνο: " + newtelnumber;

                        updatedcompetentpersonsEntry += "\nΔιεύθυνση: " + (newaddresscp.isEmpty() ? "" : newaddresscp);

                        competentpersonsList.add(updatedcompetentpersonsEntry);

                        competentpersonsField.setText("");
                        for (String competentEntry : competentpersonsList) {
                            competentpersonsField.append(competentEntry + ";\n");
                        }

                        addcompetentpersonsButton.setEnabled(true);
                        removecompetentpersonsButton.setEnabled(true);
                        updatecompetentpersonsButton.setEnabled(false);

                        firstnameField.setText("");
                        lastnameField.setText("");
                        emailcpField.setText("");
                        telnumberField.setText("");
                        addresscpField.setText("");

                        JOptionPane.showMessageDialog(
                                null,
                                updatedFields.toString(),
                                "Ενημέρωση Αρμόδιου Προσώπου",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Σφάλμα κατά την επικοινωνία με τη βάση δεδομένων: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        // Προσθήκη κουμπιού για τη διαγραφή αρμόδιου προσώπου
        removecompetentpersonsButton.addActionListener(e -> {
            if (competentpersonsList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν αρμόδια πρόσωπα για διαγραφή.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[] competentArray = competentpersonsList.toArray(new String[0]);
            String selectedCompetent = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε Αρμόδιο Πρόσωπο για διαγραφή:",
                    "Διαγραφή Αρμόδιου Προσώπου",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    competentArray,
                    competentArray[0]);

            if (selectedCompetent == null) {
                return;
            }

            try {
                String[] competentParts = selectedCompetent.split("\n");
                String emailcp = competentParts[2].split(": ")[1];

                int userId = -1;
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "SELECT id_user FROM user WHERE email = ?";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, emailcp);

                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                userId = rs.getInt("id_user");
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αναζήτηση του αρμοδίου προσώπου: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (userId == -1) {
                    JOptionPane.showMessageDialog(this, "Το αρμόδιο πρόσωπο δεν βρέθηκε στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String setSessionFullName = "SET @full_name = ?";
                    try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                        setSessionStmt.setString(1, Login.UserSession.getFullName());
                        setSessionStmt.executeUpdate();
                    }
                    String deleteLinkSql = "DELETE FROM user_stoixeio WHERE user_id = ? AND stoixeio_id = ?";
                    try (PreparedStatement ps = conn.prepareStatement(deleteLinkSql)) {
                        ps.setInt(1, userId);
                        ps.setString(2, idStoixeioField.getText());
                        ps.executeUpdate();
                        competentpersonsList.remove(selectedCompetent);

                        competentpersonsField.setText("");
                        for (String competentEntry : competentpersonsList) {
                            competentpersonsField.append(competentEntry + ";\n");
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Σφάλμα κατά τη διαγραφή από τον πίνακα user_stoixeio: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Σφάλμα: " + ex.getMessage(), "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });


        //Βιβλιογραφία
        JPanel bibliographyPanel = new JPanel(new BorderLayout());
        bibliographyPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        bibliographyPanel.add(new JLabel("Βιβλιογραφία:"), BorderLayout.NORTH);
        bibliographyField = new JTextArea(5, 20);
        bibliographyField.setLineWrap(true);
        bibliographyField.setWrapStyleWord(true);
        bibliographyField.setEditable(false);
        bibliographyPanel.add(new JScrollPane(bibliographyField), BorderLayout.CENTER);
        formPanel.add(bibliographyPanel);

        JPanel editBibliographyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editBibliographyButton = new JButton("Επεξεργασία Βιβλιογραφίας");
        editBibliographyButton.setBackground(Color.GRAY);
        editBibliographyButton.setForeground(Color.WHITE);
        editBibliographyPanel.add(editBibliographyButton);

        JPanel saveBibliographyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveBibliographyButton = new JButton("Αποθήκευση Βιβλιογραφίας");
        saveBibliographyPanel.add(saveBibliographyButton);

        JPanel cancelBibliographyPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelBibliographyButton = new JButton("Ακύρωση");
        cancelBibliographyButton.setBackground(Color.lightGray);
        cancelBibliographyButton.setForeground(Color.WHITE);
        cancelBibliographyPanel.add(cancelBibliographyButton);

        JPanel BibliographyPanel = new JPanel(new BorderLayout());
        BibliographyPanel.add(saveBibliographyPanel, BorderLayout.CENTER);
        BibliographyPanel.add(cancelBibliographyPanel, BorderLayout.EAST);
        BibliographyPanel.add(editBibliographyPanel, BorderLayout.WEST);

        formPanel.add(BibliographyPanel);


        // Ενέργεια για το κουμπί "Επεξεργασία"
        editBibliographyButton.addActionListener(e -> {
            initialBibliographyText = bibliographyField.getText();
            bibliographyField.setEditable(true);
            bibliographyField.requestFocus();
            saveBibliographyButton.setEnabled(true);
            cancelBibliographyButton.setEnabled(true);
            editBibliographyButton.setEnabled(false);
        });

        // Ενέργεια για το κουμπί "Αποθήκευση"
        saveBibliographyButton.addActionListener(e -> {
            bibliographyField.setEditable(false);
            saveBibliographyButton.setEnabled(false);
            cancelBibliographyButton.setEnabled(false);
            editBibliographyButton.setEnabled(true);

            int idStoixeio = Integer.parseInt(idStoixeioField.getText());
            String newBibliography = bibliographyField.getText();

            if (newBibliography.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Η βιβλιογραφία είναι υποχρεωτική.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newBibliography.equals(initialBibliographyText)) {
                JOptionPane.showMessageDialog(null, "Η βιβλιογραφία δεν έχει αλλάξει.", "Πληροφορία", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                String setSessionFullName = "SET @full_name = ?";
                try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                    setSessionStmt.setString(1, Login.UserSession.getFullName());
                    setSessionStmt.executeUpdate();
                }
                String updateQuery = "UPDATE stoixeio SET bibliography = ? WHERE idStoixeio = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setString(1, newBibliography);
                pstmt.setInt(2, idStoixeio);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Η βιβλιογραφία αποθηκεύτηκε επιτυχώς στη βάση.", "Επιτυχία", JOptionPane.INFORMATION_MESSAGE);
                    initialBibliographyText = newBibliography;
                } else {
                    JOptionPane.showMessageDialog(null, "Η ενημέρωση απέτυχε. Ελέγξτε αν το στοιχείο υπάρχει.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                }

                pstmt.close();
                conn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αποθήκευση στη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ενέργεια για το κουμπί "Ακύρωση"
        cancelBibliographyButton.addActionListener(e -> {
            bibliographyField.setText(initialBibliographyText);
            bibliographyField.setEditable(false);
            saveBibliographyButton.setEnabled(false);
            cancelBibliographyButton.setEnabled(false);
            editBibliographyButton.setEnabled(true);
        });


        add(new JScrollPane(formPanel), BorderLayout.CENTER);
    }

    private void updateOtherNameField() {
        otherNameField.setText(String.join(";\n", otherNameList) + ";\n");
    }

    private void updateLocationField() {
        locationField.setText("");

        for (String location : locationList) {
            if (locationField.getText().isEmpty()) {
                locationField.setText(location + ";");
            } else {
                locationField.append("\n" + location + ";");
            }
        }
    }

    private void updateKeywordField() {
        keywordField.setText("");
        for (String keyword : keywordList) {
            keywordField.append(keyword + ";" + "\n");
        }
    }

    private String getValueFromPart(String part) {
        String[] keyValue = part.split(": ");
        if (keyValue.length == 2) {
            return keyValue[1];
        } else {
            return "";
        }
    }

    private void updatePerformanceAreaField() {
        performanceAreaField.setText("");
        for (String area : performanceAreasList) {
            performanceAreaField.append(area + ";" + "\n");
        }
    }

    private void updateFacilitiesField() {
        facilitiesField.setText("");
        for (String facility : facilitiesList) {
            facilitiesField.append(facility + ";" + "\n");
        }
    }

    private void updateEquipmentField() {
        equipmentField.setText("");
        for (String equipment : equipmentList) {
            equipmentField.append(equipment + ";" + "\n");
        }
    }

    private void updateProductField() {
        productField.setText("");
        for (String product : productsList) {
            productField.append(product + ";" + "\n");
        }
    }

    private boolean isFileValidForCategory(File file, String category) {
        String fileName = file.getName();
        switch (category) {
            case "video":
                return fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mov");
            case "sounds":
                return fileName.endsWith(".mp3") || fileName.endsWith(".wav");
            case "images":
                return fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
            case "files":
                return fileName.endsWith(".pdf") || fileName.endsWith(".docx") || fileName.endsWith(".xlsx");
            case "maps":
                return fileName.endsWith(".kml") || fileName.endsWith(".kmz");
            default:
                return false;
        }
    }

    private void updateEvidenceDisplay() {
        evidenceField.setText("");
        for (String evidence : evidenceList) {
            evidenceField.append(evidence + ";" + "\n");
        }
    }

    private String getFileType(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "unknown";
    }

    private String getMimeType(File file) {
        try {
            // Ανιχνεύει τον MIME τύπο του αρχείου μέσω του συστήματος
            String mimeType = java.nio.file.Files.probeContentType(file.toPath());

            // Αν δεν μπορεί να ανιχνευθεί ο MIME τύπος, ελέγχει την επέκταση
            if (mimeType == null || mimeType.isEmpty()) {
                String fileName = file.getName();

                // Εικόνες
                if (fileName.endsWith(".png")) {
                    return "image/png";
                } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                    return "image/jpeg";
                } else if (fileName.endsWith(".gif")) {
                    return "image/gif";
                } else if (fileName.endsWith(".bmp")) {
                    return "image/bmp";
                } else if (fileName.endsWith(".webp")) {
                    return "image/webp";

                    // Βίντεο
                } else if (fileName.endsWith(".mp4")) {
                    return "video/mp4";
                } else if (fileName.endsWith(".avi")) {
                    return "video/x-msvideo";
                } else if (fileName.endsWith(".mkv")) {
                    return "video/x-matroska";
                } else if (fileName.endsWith(".mov")) {
                    return "video/quicktime";
                } else if (fileName.endsWith(".webm")) {
                    return "video/webm";

                    // Ήχος
                } else if (fileName.endsWith(".mp3")) {
                    return "audio/mpeg";
                } else if (fileName.endsWith(".wav")) {
                    return "audio/wav";
                } else if (fileName.endsWith(".ogg")) {
                    return "audio/ogg";
                } else if (fileName.endsWith(".flac")) {
                    return "audio/flac";

                    // Αρχεία
                } else if (fileName.endsWith(".zip")) {
                    return "application/zip";
                } else if (fileName.endsWith(".tar")) {
                    return "application/x-tar";
                } else if (fileName.endsWith(".pdf")) {
                    return "application/pdf";
                } else if (fileName.endsWith(".txt")) {
                    return "text/plain";
                } else if (fileName.endsWith(".doc")) {
                    return "application/msword";
                } else if (fileName.endsWith(".docx")) {
                    return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

                    // Χάρτες
                } else if (fileName.endsWith(".geojson")) {
                    return "application/geo+json";
                } else if (fileName.endsWith(".kml")) {
                    return "application/vnd.google-earth.kml+xml";
                } else if (fileName.endsWith(".kmz")) {
                    return "application/vnd.google-earth.kmz";
                } else if (fileName.endsWith(".gpx")) {
                    return "application/gpx+xml";
                } else if (fileName.endsWith(".mbtiles")) {
                    return "application/x-sqlite3"; // Αν είναι MBTiles
                }
            }

            return mimeType;
        } catch (IOException e) {
            e.printStackTrace();
            return "unknown";
        }
    }


    private String getFileHash(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            byte[] hashBytes = digest.digest(fileBytes);
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }


    private String getLinkHash(String link) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(link.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString(); // Επιστροφή του hash ως δεκαεξαδικό string
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadElement(int id, String name, int year, Set<String> otherNameList, String description, Set<String> fieldList, Set<String> selectedFields, String locationDescription, Set<String> locationList, Set<String> keywordList, Set<String> foreasList, String fullDescription, Set<String> performanceAreaList,
                            Set<String> facilitiesList, Set<String> equipmentList, Set<String> productList, String historical_data, String members, String society, String community, String transmission,
                                String existing, String future, Set<String> evidenceList, Set<String> competentpersonsList, String bibliography) {
        idStoixeioField.setText(String.valueOf(id));
        nameField.setText(name);
        yearField.setText(String.valueOf(year));
        otherNameField.setText(formatSet(otherNameList));
        descriptionArea.setText(description);
        fieldField.setText(formatSet(fieldList));
        locationDescriptionArea.setText(locationDescription);
        locationField.setText(formatSet(locationList));
        keywordField.setText(formatSet(keywordList));
        foreasField.setText(formatSet(foreasList));
        full_descriptionArea.setText(fullDescription);
        performanceAreaField.setText(formatSet(performanceAreaList));
        facilitiesField.setText(formatSet(facilitiesList));
        equipmentField.setText(formatSet(equipmentList));
        productField.setText(formatSet(productList));
        historical_dataField.setText(historical_data);
        membersField.setText(members);
        societyField.setText(society);
        communityField.setText(community);
        transmissionField.setText(transmission);
        existingField.setText(existing);
        futureField.setText(future);
        evidenceField.setText(formatSet(evidenceList));
        competentpersonsField.setText(formatSet(competentpersonsList));
        bibliographyField.setText(bibliography);

        this.otherNameList = otherNameList;
        this.fieldList = fieldList;
        this.locationList = locationList;
        this.keywordList = keywordList;
        this.foreasList = foreasList;
        this.performanceAreasList = performanceAreaList;
        this.facilitiesList = facilitiesList;
        this.equipmentList = equipmentList;
        this.productsList = productList;
        this.evidenceList = evidenceList;
        this.competentpersonsList = competentpersonsList;
        this.selectedFields = selectedFields;

        this.initialNameText = name;
        this.initialYearText = String.valueOf(year);
        this.initialDescriptionText = description;
        this.initialFullDescriptionText = fullDescription;
        this.initialHistoricalDataText = historical_data;
        this.initialBibliographyText = bibliography;
        this.initialMembersText = members;
        this.initialSocietyText = society;
        this.initialCommunityText = community;
        this.initialTransmissionText = transmission;
        this.initialExistingText = existing;
        this.initialFutureText = future;
        this.initialLocationDescriptionText = locationDescription;

    }

    private String formatSet(Set<String> List) {
        StringBuilder sb = new StringBuilder();
        for (String item : List) {
            sb.append(item).append(";\n");

        }
        return sb.toString();
    }

}





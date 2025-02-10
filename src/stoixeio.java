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


public class stoixeio extends JFrame {
    private int id_user;
    private JTextField idStoixeioField;
    private JTextField nameField;
    private JTextField yearField;
    private JTextField otherNameInputField;
    private JTextArea otherNameField;
    private Set<String> otherNamesList;
    private JTextArea descriptionArea;
    private JComboBox<String> fieldComboBox;
    private JTextArea justification_of_fieldsField;
    private Set<String> fieldList;
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
    private Set<String> keywordsList;
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
    private Set<String> equipmentsList;
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
    private static final String DB_URL = "jdbc:mysql://localhost:3306/apk";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "apk2024";

    public stoixeio(int id_user) {

        this.id_user = id_user;

        int user_Id = Login.UserSession.getUserId();
        String userName = Login.UserSession.getFullName();

        setTitle("Εισαγωγή Στοιχείου ΑΠΚ");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Εφαρμογή θεμάτων
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
        idStoixeioField.setEnabled(false); // Το ID ανατίθεται από τη βάση δεδομένων
        formPanel.add(IdPanel);

        // Όνομα
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        namePanel.add(new JLabel("Όνομα:"), BorderLayout.NORTH);
        nameField = new JTextField();
        namePanel.add(nameField, BorderLayout.CENTER);
        formPanel.add(namePanel);

        // Έτος
        JPanel yearPanel = new JPanel(new BorderLayout());
        yearPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        yearPanel.add(new JLabel("Έτος:"), BorderLayout.NORTH);
        yearField = new JTextField();
        yearPanel.add(yearField, BorderLayout.CENTER);
        formPanel.add(yearPanel);

        // Άλλα Ονόματα
        otherNamesList = new HashSet<>();
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
        editOtherNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton editOtherNameButton = new JButton("Επεξεργασία Ονόματος");
        editOtherNameButton.setBackground(Color.GRAY);
        editOtherNameButton.setForeground(Color.WHITE);
        editOtherNamePanel.add(editOtherNameButton, BorderLayout.EAST);
        formPanel.add(editOtherNamePanel);

        // Κουμπί Διαγραφής
        JPanel removeOtherNamePanel = new JPanel(new BorderLayout());
        removeOtherNamePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton removeOtherNameButton = new JButton("Διαγραφή Ονόματος");
        removeOtherNameButton.setBackground(Color.DARK_GRAY);
        removeOtherNameButton.setForeground(Color.WHITE);
        removeOtherNamePanel.add(removeOtherNameButton, BorderLayout.EAST);
        formPanel.add(removeOtherNamePanel);

        addOtherNameButton.addActionListener(e -> {
            String otherName = otherNameInputField.getText().trim();
            if (!otherName.isEmpty()) {
                // Έλεγχος αν το όνομα υπάρχει ήδη
               if (otherNamesList.contains(otherName)) {
                    JOptionPane.showMessageDialog(this, "Το όνομα υπάρχει ήδη.");
                    return;
                }

                // Προσθήκη του ονόματος στη λίστα και στην TextArea
                otherNamesList.add(otherName);
                otherNameField.append(otherName + ";" + "\n");
                otherNameInputField.setText("");

            }
        });

        // Επεξεργασία του επιλεγμένου ονόματος
        editOtherNameButton.addActionListener(e -> {
            if (otherNamesList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν ονόματα για επεξεργασία.");
                return;
            }

            // Δημιουργία λίστας επιλογής για την επεξεργασία
            String[] namesArray = otherNamesList.toArray(new String[0]);
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
                // Εμφάνιση του ονόματος στο πεδίο εισαγωγής για επεξεργασία
                otherNameInputField.setText(selectedName);

                otherNamesList.remove(selectedName);
                updateOtherNameField();
            }
        });

        // Διαγραφή του επιλεγμένου ονόματος από τη λίστα
        removeOtherNameButton.addActionListener(e -> {
            if (otherNamesList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν ονόματα για διαγραφή.");
                return;
            }

            // Δημιουργία λίστας επιλογής για τη διαγραφή
            String[] namesArray = otherNamesList.toArray(new String[0]);
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
                otherNamesList.remove(selectedName);
                updateOtherNameField();
            }
        });

        // Περιγραφή
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        descriptionPanel.add(new JLabel("Σύντομη Περιγραφή (έως 100 λέξεις):"), BorderLayout.NORTH);
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        formPanel.add(descriptionPanel);

        JPanel removeDescriptionPanel = new JPanel(new BorderLayout());
        removeDescriptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton clearDescriptionButton = new JButton("Διαγραφή");
        clearDescriptionButton.setBackground(Color.DARK_GRAY);
        clearDescriptionButton.setForeground(Color.WHITE);
        removeDescriptionPanel.add(clearDescriptionButton, BorderLayout.EAST);
        clearDescriptionButton.addActionListener(e -> descriptionArea.setText(""));
        formPanel.add(removeDescriptionPanel);


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

        JPanel clearJustificationPanel = new JPanel(new BorderLayout());
        clearJustificationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearJustificationButton = new JButton("Διαγραφή Αιτιολόγησης");
        clearJustificationButton.addActionListener(e -> justification_of_fieldsField.setText(""));
        clearJustificationButton.setBackground(Color.DARK_GRAY);
        clearJustificationButton.setForeground(Color.WHITE);
        clearJustificationPanel.add(clearJustificationButton, BorderLayout.EAST);
        formPanel.add(clearJustificationPanel);

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

        // Κουμπί επεξεργασίας καταχώρησης από τη λίστα
        JPanel editEntryPanel = new JPanel(new BorderLayout());
        editEntryPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton editEntryButton = new JButton("Επεξεργασία Καταχώρησης");
        editEntryButton.setBackground(Color.GRAY);
        editEntryButton.setForeground(Color.WHITE);
        editEntryPanel.add(editEntryButton, BorderLayout.EAST);
        formPanel.add(editEntryPanel);

        // Κουμπί διαγραφής καταχώρησης από τη λίστα
        JPanel removeEntryPanel = new JPanel(new BorderLayout());
        removeEntryPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeEntryButton = new JButton("Διαγραφή Καταχώρησης");
        removeEntryButton.setBackground(Color.DARK_GRAY);
        removeEntryButton.setForeground(Color.WHITE);
        removeEntryPanel.add(removeEntryButton, BorderLayout.EAST);
        formPanel.add(removeEntryPanel);

        // Αρχικοποίηση της λίστας με επιλεγμένα πεδία
        Set<String> selectedFields = new HashSet<>();

        // Κουμπί προσθήκης
        addFieldButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String field_apk = (String) fieldComboBox.getSelectedItem();
                String justification = justification_of_fieldsField.getText();

                if (field_apk != null && !field_apk.isEmpty() && !"Επιλέξτε Πεδίο".equals(field_apk)) {
                    // Έλεγχος αν το πεδίο είναι "άλλο" και αν το justification είναι κενό
                    if ("άλλο".equals(field_apk) && justification.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Πρέπει να συμπληρώσετε το πεδίο 'Αιτιολόγηση' όταν επιλέξετε 'άλλο'.",
                                "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Έλεγχος αν το πεδίο έχει ήδη επιλεγεί
                    if (selectedFields.contains(field_apk)) {
                        JOptionPane.showMessageDialog(null, "Το πεδίο αυτό έχει ήδη επιλεγεί. Επιλέξτε άλλο πεδίο.",
                                "Προσοχή", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Προσθήκη του επιλεγμένου πεδίου στη λίστα
                    selectedFields.add(field_apk);

                    // Δημιουργία της καταχώρισης με βάση το πεδίο και την αιτιολόγηση (αν υπάρχει)
                    StringBuilder entryBuilder = new StringBuilder();
                    entryBuilder.append(field_apk);

                    if (!justification.isEmpty()) {
                        entryBuilder.append(": ").append(justification);
                    }

                    String entry = entryBuilder.toString();
                    fieldList.add(entry);

                    // Εμφάνιση των δεδομένων στο textArea
                    fieldField.setText(String.join(";\n", fieldList) + ";");

                    // Επαναφορά του ComboBox και καθαρισμός του πεδίου αιτιολόγησης
                    fieldComboBox.setSelectedIndex(0);
                    justification_of_fieldsField.setText("");
                }
            }
        });



        editEntryButton.addActionListener(e -> {
            String[] entriesArray = fieldList.toArray(new String[0]);
            String selectedEntry = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε καταχώρηση για επεξεργασία:",
                    "Επεξεργασία Καταχώρησης",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    entriesArray,
                    entriesArray[0]);

            if (selectedEntry == null) {
                return;
            }

            // Διάσπαση της επιλεγμένης καταχώρησης στα πεδία (διαχωρισμός αιτιολόγησης με ": ")
            String[] entryParts = selectedEntry.split(": ");
            String field = entryParts[0];
            String justification = entryParts.length > 1 ? entryParts[1] : "";

            // Φόρτωση των δεδομένων στο πεδίο για επεξεργασία
            fieldComboBox.setSelectedItem(field);
            justification_of_fieldsField.setText(justification);

            // Διαγραφή της παλιάς καταχώρησης από τη λίστα πριν την επεξεργασία
            fieldList.remove(selectedEntry);

            // Ενημέρωση του JTextArea μετά τη διαγραφή της παλιάς καταχώρησης
            fieldField.setText("");
            for (String fieldEntry : fieldList) {
                fieldField.append(fieldEntry + ";" + "\n");
            }

            // Ανανέωση της λίστας επιλεγμένων πεδίων
            selectedFields.remove(field);
        });


        removeEntryButton.addActionListener(e -> {
            // Παρουσιάζουμε τη λίστα των καταχωρήσεων στον χρήστη
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

            // Διαγραφή της επιλεγμένης καταχώρησης από τη λίστα
            fieldList.remove(selectedEntry);

            // Ανανέωση του JTextArea
            fieldField.setText("");
            for (String fieldEntry : fieldList) {
                fieldField.append(fieldEntry + ";" + "\n");
            }

            // Αφαίρεση του πεδίου από τη λίστα επιλεγμένων αν υπάρχει
            String field = selectedEntry.split(": ")[0];
            selectedFields.remove(field);

            // Απενεργοποίηση του κουμπιού αν η λίστα είναι πλέον κενή
            if (fieldList.isEmpty()) {
                removeEntryButton.setEnabled(false);
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
        descriptionLocationPanel.add(new JScrollPane(locationDescriptionArea), BorderLayout.CENTER);
        formPanel.add(descriptionLocationPanel);

        // Δημιουργία κουμπιού διαγραφής για την περιγραφή
        JPanel clearDescriptionLocationPanel = new JPanel(new BorderLayout());
        clearDescriptionLocationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton clearDescriptionLocationButton = new JButton("Διαγραφή Περιγραφής");
        clearDescriptionLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                locationDescriptionArea.setText("");
            }
        });
        clearDescriptionLocationButton.setBackground(Color.DARK_GRAY);
        clearDescriptionLocationButton.setForeground(Color.WHITE);
        clearDescriptionLocationPanel.add(clearDescriptionLocationButton, BorderLayout.EAST);
        formPanel.add(clearDescriptionLocationPanel);

        // Πεδία Καλικράτη
        JPanel kalikratisPanel = new JPanel(new GridLayout(6, 2));
        kalikratisPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Πεδίο Αποκεντρωμένης Διοίκησης
        kalikratisPanel.add(new JLabel("Αποκεντρωμένη Διοίκηση:"));
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Επιλέξτε Αποκεντρωμένη Διοίκηση");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM decentralized_administration")) {

            while (rs.next()) {
                model.addElement(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά τη φόρτωση των δεδομένων: " + e.getMessage());
        }

        // Ορισμός του μοντέλου στο JComboBox
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

        // Προσθήκη ActionListener για το municipalityComboBox
        municipalityComboBox.addActionListener(e -> {
            // Καθαρισμός επιλογών του municipalUnitComboBox
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
            // Καθαρισμός επιλογών του communitiesComboBox
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

        // Κουμπί για προσθήκη τεκμηρίων
        JPanel addLocationPanel = new JPanel(new BorderLayout());
        addLocationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton addLocationButton = new JButton("Προσθήκη Τοποθεσίας");
        addLocationPanel.add(addLocationButton, BorderLayout.EAST);
        formPanel.add(addLocationPanel);

        //Προσθήκη περιοχών Καλλικράτη με έλεγχο
        addLocationButton.addActionListener(e -> {
            String decentralizedAdmin = (String) decentralizedAdminComboBox.getSelectedItem();
            String region = (String) regionComboBox.getSelectedItem();
            String regionalUnit = (String) regionalUnitComboBox.getSelectedItem();
            String municipality = (String) municipalityComboBox.getSelectedItem();
            String municipalUnit = (String) municipalUnitComboBox.getSelectedItem();
            String community = (String) communitiesComboBox.getSelectedItem();

            // Λήψη περιγραφής τοποθεσίας
            String locationDescription = locationDescriptionArea.getText();

            // Έλεγχος αν είναι κενά τα πεδία Καλλικράτης και Περιγραφή Τοποθεσίας
            if (decentralizedAdmin.equals("Επιλέξτε Aποκεντρωμένη Διοίκηση") && locationDescription.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Πρέπει να συμπληρώσετε τουλάχιστον έναν από τους δύο: τον Καλλικράτη ή την Περιγραφή Τοποθεσίας.");
                return;
            }

            // Δημιουργία της περιοχής σε μορφή κειμένου με βάση τα επιλεγμένα πεδία
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
            if (!community.equals("Επιλέξτε Κοινότητα")) {
                if (locationEntry.length() > 0) locationEntry.append(", ");
                locationEntry.append(community);
            }

            // Έλεγχος αν η περιοχή έχει ήδη προστεθεί
            String locationString = locationEntry.toString();
            if (locationList.contains(locationString)) {
                JOptionPane.showMessageDialog(this, "Η περιοχή έχει ήδη προστεθεί.");
                return;
            }

            // Προσθήκη στην λίστα των περιοχών
            locationList.add(locationString);

            // Προσθήκη στην text area
            if (locationField.getText().isEmpty()) {
                locationField.setText(locationEntry + ";");
            } else {
                locationField.append("\n" + locationEntry + ";");
            }

            // Επαναφορά των πεδίων μετά την προσθήκη
            decentralizedAdminComboBox.setSelectedIndex(0);
            regionComboBox.setSelectedIndex(0);
            regionalUnitComboBox.setSelectedIndex(0);
            municipalityComboBox.setSelectedIndex(0);
            municipalUnitComboBox.setSelectedIndex(0);
            communitiesComboBox.setSelectedIndex(0);
        });

        // Κουμπί για διαγραφή περιοχής από τη λίστα
        JPanel removeLocationPanel = new JPanel(new BorderLayout());
        removeLocationPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JButton removeLocationButton = new JButton("Διαγραφή Τοποθεσίας");
        removeLocationButton.setBackground(Color.DARK_GRAY);
        removeLocationButton.setForeground(Color.WHITE);
        removeLocationPanel.add(removeLocationButton, BorderLayout.EAST);
        formPanel.add(removeLocationPanel);

        removeLocationButton.addActionListener(e -> {
            String[] locationsArray = locationList.toArray(new String[0]);
            String selectedLocation = (String) JOptionPane.showInputDialog(
                    this,
                    "Επιλέξτε περιοχή για διαγραφή:",
                    "Διαγραφή Τοποθεσίας",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    locationsArray,
                    locationsArray[0]);

            if (selectedLocation == null) {
                return;
            }

            // Διαγραφή της επιλεγμένης περιοχής από τη λίστα
            locationList.remove(selectedLocation);

            // Ενημέρωση του JTextArea
            updateLocationField();
        });


        // Λέξεις-Κλειδιά
        keywordsList = new HashSet<>();
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

        // Κουμπί Επεξεργασίας
        JPanel editKeywordPanel = new JPanel(new BorderLayout());
        editKeywordPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton editKeywordButton = new JButton("Επεξεργασία Λέξης-Κλειδί");
        editKeywordButton.setBackground(Color.GRAY);
        editKeywordButton.setForeground(Color.WHITE);
        editKeywordPanel.add(editKeywordButton, BorderLayout.EAST);
        formPanel.add(editKeywordPanel);

        //Κουμπί Διαγραφής
        JPanel removeKeywordPanel = new JPanel(new BorderLayout());
        removeKeywordPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeKeywordButton = new JButton("Διαγραφή Λέξης-Κλειδί");
        removeKeywordButton.setBackground(Color.DARK_GRAY);
        removeKeywordButton.setForeground(Color.WHITE);
        removeKeywordPanel.add(removeKeywordButton, BorderLayout.EAST);
        formPanel.add(removeKeywordPanel);

        addKeywordButton.addActionListener(e -> {
            String keyword = keywordInputField.getText();
            if (!keyword.isEmpty()) {
                if (keywordsList.size() >= 30) {
                    JOptionPane.showMessageDialog(this, "Δεν μπορείτε να προσθέσετε περισσότερες από 30 λέξεις-κλειδιά.");
                    return;
                }
                // Έλεγχος αν η λέξη-κλειδί υπάρχει ήδη
                if (keywordsList.contains(keyword)) {
                    JOptionPane.showMessageDialog(this, "Η λέξη-κλειδί υπάρχει ήδη.");
                    return;
                } else {
                    // Προσθήκη του ονόματος στη λίστα και στην TextArea
                    keywordsList.add(keyword);
                    keywordField.append(keyword + ";" + "\n");
                    keywordInputField.setText("");
                }
            }
        });

        editKeywordButton.addActionListener(e -> {
            if (keywordsList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν λέξεις-κλειδιά για επεξεργασία.");
                return;
            }

            String[] keywordsArray = keywordsList.toArray(new String[0]);
            String selectedKeyword = (String) JOptionPane.showInputDialog(
                    this,
                    "Επιλέξτε μία λέξη-κλειδί για επεξεργασία:",
                    "Επεξεργασία Λέξης-Κλειδί",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    keywordsArray,
                    keywordsArray[0]
            );

            if (selectedKeyword != null) {
                // Εμφάνιση της λέξης-κλειδί στο πεδίο εισαγωγής για επεξεργασία
                keywordInputField.setText(selectedKeyword);

                // Διαγραφή της επιλεγμένης λέξης-κλειδί από τη λίστα
                keywordsList.remove(selectedKeyword);
                updateKeywordField();
            }
        });

        // Διαγραφή της επιλεγμένης λέξης-κλειδί από τη λίστα
        removeKeywordButton.addActionListener(e -> {
            if (keywordsList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν λέξεις-κλειδιά για διαγραφή.");
                return;
            }

            // Δημιουργία λίστας επιλογής για τη διαγραφή
            String[] keywordsArray = keywordsList.toArray(new String[0]);
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
                keywordsList.remove(selectedKeyword);
                updateKeywordField();
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

        // Πεδίο εμφάνισης για φορέα
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

        // Προσθήκη κουμπιού διαγραφής
        JPanel removeForeasPanel = new JPanel(new BorderLayout());
        removeForeasPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeForeasButton = new JButton("Διαγραφή Φορέα");
        removeForeasButton.setBackground(Color.DARK_GRAY);
        removeForeasButton.setForeground(Color.WHITE);
        removeForeasPanel.add(removeForeasButton, BorderLayout.EAST);
        formPanel.add(removeForeasPanel);

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

                // Έλεγχος αν όλα τα πεδία είναι συμπληρωμένα
                if (foreasName.isEmpty() || foreasDescription.isEmpty() || foreasAddress.isEmpty() || foreasTel.isEmpty() ||
                        foreasEmail.isEmpty() || foreasTK.isEmpty() || foreasCity.isEmpty() || foreasWebsite.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Όλα τα πεδία είναι υποχρεωτικά!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Έλεγχος αν το όνομα του φορέα υπάρχει ήδη στη λίστα
                for (String existingEntry : foreasList) {
                    if (existingEntry.contains(foreasName)) {
                        JOptionPane.showMessageDialog(null, "Ο φορέας με αυτό το όνομα υπάρχει ήδη στη λίστα!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Έλεγχοι για τη μορφή των πεδίων
                if (foreasDescription.split("\\s+").length > 100) {
                    JOptionPane.showMessageDialog(null, "Η περιγραφή δεν πρέπει να υπερβαίνει τις 100 λέξεις.");
                    return;
                }

                if (!foreasTel.matches("^((\\+30)?[2-9][0-9]{9}|[2-9][0-9]{9})$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το τηλέφωνο", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return; // Επιστρέφει αν το τηλέφωνο δεν είναι έγκυρο
                }

                if (!foreasEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το email", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Έλεγχος αν το email υπάρχει ήδη στη λίστα
                for (String existingEntry : foreasList) {
                    if (existingEntry.contains(foreasEmail)) {
                        JOptionPane.showMessageDialog(null, "Το email υπάρχει ήδη στη λίστα!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
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

                // Δημιουργία καταχώρησης φορέα και προσθήκη στη λίστα
                String foreasEntry = "Όνομα: " + foreasName + "\n" +
                        "Περιγραφή: " + foreasDescription + "\n" +
                        "Email: " + foreasEmail + "\n" +
                        "Διεύθυνση: " + foreasAddress + "\n" +
                        "Πόλη: " + foreasCity + "\n" +
                        "TK: " + foreasTK + "\n" +
                        "Τηλέφωνο: " + foreasTel + "\n" +
                        "Website: " + foreasWebsite;

                foreasList.add(foreasEntry);

                // Εμφάνιση των φορέων στο JTextArea
                foreasField.setText(String.join(";\n", foreasList));

                // Καθαρισμός πεδίων για τον επόμενο φορέα
                foreasNameField.setText("");
                foreasDescriptionField.setText("");
                foreasAddressField.setText("");
                foreasCityField.setText("");
                foreasTelField.setText("");
                foreasEmailField.setText("");
                foreasTKField.setText("");
                foreasWebsiteField.setText("");
            }
        });

        // Προσθήκη κουμπιού για την επεξεργασία φορέα
        editForeasButton.addActionListener(e -> {
            // Παρουσίαση της λίστας των φορέων στον χρήστη για επιλογή
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

            // Διαγραφή της παλιάς καταχώρησης από τη λίστα πριν την επεξεργασία
            foreasList.remove(selectedForeas);

            // Ενημέρωση του JTextArea μετά τη διαγραφή της παλιάς καταχώρησης
            foreasField.setText("");
            for (String foreasEntry : foreasList) {
                foreasField.append(foreasEntry + ";\n");
            }
        });

        // Προσθήκη κουμπιού για τη διαγραφή φορέα
        removeForeasButton.addActionListener(e -> {
            // Παρουσίαση της λίστας των φορέων στον χρήστη για επιλογή
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

            foreasList.remove(selectedForeas);

            // Ενημέρωση του JTextArea μετά τη διαγραφή
            foreasField.setText("");
            for (String foreasEntry : foreasList) {
                foreasField.append(foreasEntry + ";\n");
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
        formPanel.add(fullDescriptionPanel);

        JPanel clearFullDescriptionPanel = new JPanel(new BorderLayout());
        clearFullDescriptionPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearFullDescriptionButton = new JButton("Διαγραφή");
        clearFullDescriptionButton.addActionListener(e -> full_descriptionArea.setText(""));
        clearFullDescriptionButton.setBackground(Color.DARK_GRAY);
        clearFullDescriptionButton.setForeground(Color.WHITE);
        clearFullDescriptionPanel.add(clearFullDescriptionButton, BorderLayout.EAST);
        formPanel.add(clearFullDescriptionPanel);

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

        //Κουμπί Διαγραφής
        JPanel removePerformanceAreaPanel = new JPanel(new BorderLayout());
        removePerformanceAreaPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removePerformanceAreaButton = new JButton("Διαγραφή Χώρου Επιτέλεσης");
        removePerformanceAreaButton.setBackground(Color.DARK_GRAY);
        removePerformanceAreaButton.setForeground(Color.WHITE);
        removePerformanceAreaPanel.add(removePerformanceAreaButton, BorderLayout.EAST);
        formPanel.add(removePerformanceAreaPanel);

        addPerformanceAreaButton.addActionListener(e -> {
            String performanceArea = performanceAreaInputField.getText();
            String justification = justificationPerformanceAreaField.getText();

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
                // Συνδυασμός Χώρου και Αιτιολόγησης
                String performanceAreaEntry = performanceArea + ": " + justification;

                // Έλεγχος αν υπάρχει ήδη ο χώρος σκέτος
                boolean areaExists = performanceAreasList.stream()
                        .anyMatch(entry -> entry.startsWith(performanceArea + ":") || entry.equals(performanceArea));

                if (areaExists) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Ο χώρος επιτέλεσης υπάρχει ήδη, είτε μόνος του είτε με διαφορετική αιτιολόγηση!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                // Έλεγχος αν υπάρχει ήδη ο συνδυασμός χώρου και αιτιολόγησης
                else if (performanceAreasList.contains(performanceAreaEntry)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Ο χώρος επιτέλεσης με την ίδια αιτιολόγηση υπάρχει ήδη!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    // Προσθήκη του χώρου και αιτιολόγησης στη λίστα
                    performanceAreasList.add(performanceAreaEntry);

                    performanceAreaField.setText(String.join(";\n", performanceAreasList));

                    performanceAreaInputField.setText("");
                    justificationPerformanceAreaField.setText("");
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
            String[] performanceAreasArray = performanceAreasList.toArray(new String[0]);
            String selectedPerformanceArea = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε χώρο επιτέλεσης για επεξεργασία:",
                    "Επεξεργασία Χώρου Επιτέλεσης",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    performanceAreasArray,
                    performanceAreasArray[0]);

            if (selectedPerformanceArea == null) {
                return;
            }

            // Διάσπαση του επιλεγμένου συνδυασμού (χώρος και αιτιολόγηση)
            String[] parts = selectedPerformanceArea.split(":", 2);
            String area = parts[0].trim();
            String justification = parts[1].trim();

            // Φόρτωση των δεδομένων στα πεδία επεξεργασίας
            performanceAreaInputField.setText(area);
            justificationPerformanceAreaField.setText(justification);

            // Διαγραφή του επιλεγμένου χώρου από τη λίστα πριν την επεξεργασία
            performanceAreasList.remove(selectedPerformanceArea);
            updatePerformanceAreaField();
        });


        removePerformanceAreaButton.addActionListener(e -> {
            if (performanceAreasList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν χώροι επιτέλεσης για διαγραφή.");
                return;
            }

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
                    performanceAreasList.remove(selectedPerformanceArea);
                    updatePerformanceAreaField();
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

        // Κουμπί για διαγραφή εγκατάστασης
        JPanel removeFacilitiesPanel = new JPanel(new BorderLayout());
        removeFacilitiesPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeFacilitiesButton = new JButton("Διαγραφή Εγκατάστασης");
        removeFacilitiesButton.setBackground(Color.DARK_GRAY);
        removeFacilitiesButton.setForeground(Color.WHITE);
        removeFacilitiesPanel.add(removeFacilitiesButton, BorderLayout.EAST);
        formPanel.add(removeFacilitiesPanel);

        addFacilitiesButton.addActionListener(e -> {
            String facility = facilitiesInputField.getText();
            String justification = justificationFacilityField.getText();

            // Έλεγχος αν έχει συμπληρωθεί η εγκατάσταση αλλά όχι η αιτιολόγηση
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
                // Συνδυασμός Εγκατάστασης και Αιτιολόγησης
                String facilityEntry = facility + ": " + justification;

                // Έλεγχος αν υπάρχει ήδη η εγκατάσταση
                boolean facilityExists = facilitiesList.stream()
                        .anyMatch(entry -> entry.startsWith(facility + ":") || entry.equals(facility));

                if (facilityExists) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Η εγκατάσταση υπάρχει ήδη, είτε μόνος της είτε με διαφορετική αιτιολόγηση!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else if (facilitiesList.contains(facilityEntry)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Η εγκατάσταση με την ίδια αιτιολόγηση υπάρχει ήδη!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    // Προσθήκη της εγκατάστασης και αιτιολόγησης στη λίστα
                    facilitiesList.add(facilityEntry);

                    // Εμφάνιση των εγκαταστάσεων στο JTextArea
                    facilitiesField.setText(String.join(";\n", facilitiesList));

                    // Καθαρισμός των πεδίων εισαγωγής
                    facilitiesInputField.setText("");
                    justificationFacilityField.setText("");
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

        // Λογική για επεξεργασία εγκατάστασης
        editFacilitiesButton.addActionListener(e -> {
            // Παρουσίαση της λίστας των εγκαταστάσεων για επιλογή
            String[] facilitiesArray = facilitiesList.toArray(new String[0]);
            String selectedFacility = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε εγκατάσταση για επεξεργασία:",
                    "Επεξεργασία Εγκατάστασης",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    facilitiesArray,
                    facilitiesArray[0]);

            if (selectedFacility == null) {
                return;
            }

            // Διάσπαση του επιλεγμένου συνδυασμού (εγκατάσταση και αιτιολόγηση)
            String[] parts = selectedFacility.split(":", 2);
            String facility = parts[0].trim();
            String justification = parts[1].trim();

            // Φόρτωση των δεδομένων στα πεδία επεξεργασίας
            facilitiesInputField.setText(facility);
            justificationFacilityField.setText(justification);

            // Διαγραφή της επιλεγμένης εγκατάστασης από τη λίστα πριν την επεξεργασία
            facilitiesList.remove(selectedFacility);
            updateFacilitiesField();
        });

        // Λογική για διαγραφή εγκατάστασης
        removeFacilitiesButton.addActionListener(e -> {
            if (facilitiesList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν εγκαταστάσεις για διαγραφή.");
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
                    facilitiesList.remove(selectedFacility);
                    updateFacilitiesField();
                }
            }
        });

        // Εξοπλισμός
        equipmentsList = new HashSet<>();
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

        // Κουμπί για διαγραφή εξοπλισμού
        JPanel removeEquipmentPanel = new JPanel(new BorderLayout());
        removeEquipmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeEquipmentsButton = new JButton("Διαγραφή Εξοπλισμού");
        removeEquipmentsButton.setBackground(Color.DARK_GRAY);
        removeEquipmentsButton.setForeground(Color.WHITE);
        removeEquipmentPanel.add(removeEquipmentsButton, BorderLayout.EAST);
        formPanel.add(removeEquipmentPanel);

        // Λογική για προσθήκη εξοπλισμού
        addEquipmentsButton.addActionListener(e -> {
            String equipment = equipmentInputField.getText();
            String justification = justificationEquipmentField.getText();

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
                // Συνδυασμός Εξοπλισμού και Αιτιολόγησης
                String equipmentEntry = equipment + ": " + justification;

                // Έλεγχος αν υπάρχει ήδη ο εξοπλισμός
                boolean equipmentExists = equipmentsList.stream()
                        .anyMatch(entry -> entry.startsWith(equipment + ":") || entry.equals(equipment));

                if (equipmentExists) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Ο εξοπλισμός υπάρχει ήδη, είτε μόνος του είτε με διαφορετική αιτιολόγηση!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else if (equipmentsList.contains(equipmentEntry)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Ο εξοπλισμός με την ίδια αιτιολόγηση υπάρχει ήδη!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    // Προσθήκη του εξοπλισμού και αιτιολόγησης στη λίστα
                    equipmentsList.add(equipmentEntry);

                    // Εμφάνιση των εξοπλισμών στο JTextArea
                    equipmentField.setText(String.join(";\n", equipmentsList));

                    // Καθαρισμός των πεδίων εισαγωγής
                    equipmentInputField.setText("");
                    justificationEquipmentField.setText("");
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

        // Λογική για επεξεργασία εξοπλισμού
        editEquipmentsButton.addActionListener(e -> {
            // Παρουσίαση της λίστας των εξοπλισμών για επιλογή
            String[] equipmentArray = equipmentsList.toArray(new String[0]);
            String selectedEquipment = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε εξοπλισμό για επεξεργασία:",
                    "Επεξεργασία Εξοπλισμού",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    equipmentArray,
                    equipmentArray[0]);

            if (selectedEquipment == null) {
                return;
            }

            // Διάσπαση του επιλεγμένου συνδυασμού (εξοπλισμός και αιτιολόγηση)
            String[] parts = selectedEquipment.split(":", 2);
            String equipment = parts[0].trim();
            String justification = parts[1].trim();

            // Φόρτωση των δεδομένων στα πεδία επεξεργασίας
            equipmentInputField.setText(equipment);
            justificationEquipmentField.setText(justification);

            // Διαγραφή του επιλεγμένου εξοπλισμού από τη λίστα πριν την επεξεργασία
            equipmentsList.remove(selectedEquipment);
            updateEquipmentField();
        });

        // Λογική για διαγραφή εξοπλισμού
        removeEquipmentsButton.addActionListener(e -> {
            if (equipmentsList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν εξοπλισμοί για διαγραφή.");
                return;
            }

            // Δημιουργία λίστας επιλογής για διαγραφή
            String[] equipmentArray = equipmentsList.toArray(new String[0]);
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
                    equipmentsList.remove(selectedEquipment);
                    updateEquipmentField();
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

        // Κουμπί για διαγραφή προϊόντος
        JPanel removeProductsPanel = new JPanel(new BorderLayout());
        removeProductsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removeProductsButton = new JButton("Διαγραφή Προϊόντος");
        removeProductsButton.setBackground(Color.DARK_GRAY);
        removeProductsButton.setForeground(Color.WHITE);
        removeProductsPanel.add(removeProductsButton, BorderLayout.EAST);
        formPanel.add(removeProductsPanel);

        // Λογική για προσθήκη προϊόντος
        addProductsButton.addActionListener(e -> {
            String product = productInputField.getText();
            String justification = justificationProductField.getText();

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
                // Συνδυασμός Προϊόντος και Αιτιολόγησης
                String productEntry = product + ": " + justification;

                // Έλεγχος αν υπάρχει ήδη το προϊόν
                boolean productExists = productsList.stream()
                        .anyMatch(entry -> entry.startsWith(product + ":") || entry.equals(product));

                if (productExists) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Το προϊόν υπάρχει ήδη, είτε μόνο του είτε με διαφορετική αιτιολόγηση!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else if (productsList.contains(productEntry)) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Το προϊόν με την ίδια αιτιολόγηση υπάρχει ήδη!",
                            "Σφάλμα",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    // Προσθήκη του προϊόντος και αιτιολόγησης στη λίστα
                    productsList.add(productEntry);

                    // Εμφάνιση των προϊόντων στο JTextArea
                    productField.setText(String.join(";\n", productsList));

                    // Καθαρισμός των πεδίων εισαγωγής
                    productInputField.setText("");
                    justificationProductField.setText("");
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

        // Λογική για επεξεργασία προϊόντος
        editProductsButton.addActionListener(e -> {
            String[] productArray = productsList.toArray(new String[0]);
            String selectedProduct = (String) JOptionPane.showInputDialog(
                    null,
                    "Επιλέξτε προϊόν για επεξεργασία:",
                    "Επεξεργασία Προϊόντος",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    productArray,
                    productArray[0]);

            if (selectedProduct == null) {
                return;
            }

            // Διάσπαση του επιλεγμένου συνδυασμού (προϊόν και αιτιολόγηση)
            String[] parts = selectedProduct.split(":", 2);
            String product = parts[0].trim();
            String justification = parts[1].trim();

            // Φόρτωση των δεδομένων στα πεδία επεξεργασίας
            productInputField.setText(product);
            justificationProductField.setText(justification);

            // Διαγραφή του επιλεγμένου προϊόντος από τη λίστα πριν την επεξεργασία
            productsList.remove(selectedProduct);
            updateProductField();
        });

        // Λογική για διαγραφή προϊόντος
        removeProductsButton.addActionListener(e -> {
            if (productsList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Δεν υπάρχουν προϊόντα για διαγραφή.");
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
                    productsList.remove(selectedProduct);
                    updateProductField();
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
        historicalDataPanel.add(new JScrollPane(historical_dataField), BorderLayout.CENTER);
        formPanel.add(historicalDataPanel);

        JPanel clearHistoricalDataPanel = new JPanel(new BorderLayout());
        clearHistoricalDataPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearHistoricalDataButton = new JButton("Διαγραφή");
        clearHistoricalDataButton.addActionListener(e -> historical_dataField.setText(""));
        clearHistoricalDataButton.setBackground(Color.DARK_GRAY);
        clearHistoricalDataButton.setForeground(Color.WHITE);
        clearHistoricalDataPanel.add(clearHistoricalDataButton, BorderLayout.EAST);
        formPanel.add(clearHistoricalDataPanel);


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
        membersPanel.add(new JScrollPane(membersField), BorderLayout.CENTER);
        formPanel.add(membersPanel);

        JPanel clearMembersPanel = new JPanel(new BorderLayout());
        clearMembersPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearMembersButton = new JButton("Διαγραφή");
        clearMembersButton.addActionListener(e -> membersField.setText(""));
        clearMembersButton.setBackground(Color.DARK_GRAY);
        clearMembersButton.setForeground(Color.WHITE);
        clearMembersPanel.add(clearMembersButton, BorderLayout.EAST);
        formPanel.add(clearMembersPanel);

        // Δημιουργία πεδίου για Society
        JPanel societyPanel = new JPanel(new BorderLayout());
        societyPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        societyPanel.add(new JLabel("Σημασία για την σύγχρονη Ελληνική κοινωνία (100 έως 200 λέξεις):"), BorderLayout.NORTH);
        societyField = new JTextArea(3,20);
        societyField.setLineWrap(true);
        societyField.setWrapStyleWord(true);
        societyPanel.add(new JScrollPane(societyField), BorderLayout.CENTER);
        formPanel.add(societyPanel);

        JPanel clearSocietyPanel = new JPanel(new BorderLayout());
        clearSocietyPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearSocietyButton = new JButton("Διαγραφή");
        clearSocietyButton.addActionListener(e -> societyField.setText(""));
        clearSocietyButton.setBackground(Color.DARK_GRAY);
        clearSocietyButton.setForeground(Color.WHITE);
        clearSocietyPanel.add(clearSocietyButton, BorderLayout.EAST);
        formPanel.add(clearSocietyPanel);

        // Δημιουργία πεδίου για Community
        JPanel communityPanel = new JPanel(new BorderLayout());
        communityPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        communityPanel.add(new JLabel("Συμμετοχή της κοινότητας στην προετοιμασία της εγγραφής του στο Εθνικό Ευρετήριο Άυλης Πολιτιστικής Κληρονομιάς της Ελλάδας (300 έως 500 λέξεις):"), BorderLayout.NORTH);
        communityField = new JTextArea(3,20);
        communityField.setLineWrap(true);
        communityField.setWrapStyleWord(true);
        communityPanel.add(new JScrollPane(communityField), BorderLayout.CENTER);
        formPanel.add(communityPanel);

        JPanel clearCommunityPanel = new JPanel(new BorderLayout());
        clearCommunityPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearCommunityButton = new JButton("Διαγραφή");
        clearCommunityButton.addActionListener(e -> communityField.setText(""));
        clearCommunityButton.setBackground(Color.DARK_GRAY);
        clearCommunityButton.setForeground(Color.WHITE);
        clearCommunityPanel.add(clearCommunityButton, BorderLayout.EAST);
        formPanel.add(clearCommunityPanel);

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
        transmissionPanel.add(new JScrollPane(transmissionField), BorderLayout.CENTER);
        formPanel.add(transmissionPanel);

        JPanel clearTransmissionPanel = new JPanel(new BorderLayout());
        clearTransmissionPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearTransmissionButton = new JButton("Διαγραφή");
        clearTransmissionButton.addActionListener(e -> transmissionField.setText(""));
        clearTransmissionButton.setBackground(Color.DARK_GRAY);
        clearTransmissionButton.setForeground(Color.WHITE);
        clearTransmissionPanel.add(clearTransmissionButton, BorderLayout.EAST);
        formPanel.add(clearTransmissionPanel);

        // Δημιουργία πεδίου για Existing
        JPanel existingPanel = new JPanel(new BorderLayout());
        existingPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        existingPanel.add(new JLabel("Μέτρα διαφύλαξης/ανάδειξης του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς που έχουν ληφθεί στο παρελθόν ή που εφαρμόζονται σήμερα (200 έως 300 λέξεις):"), BorderLayout.NORTH);
        existingField = new JTextArea(3,20);
        existingField.setLineWrap(true);
        existingField.setWrapStyleWord(true);
        existingPanel.add(new JScrollPane(existingField), BorderLayout.CENTER);
        formPanel.add(existingPanel);

        JPanel clearExistingPanel = new JPanel(new BorderLayout());
        clearExistingPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearExistingButton = new JButton("Διαγραφή");
        clearExistingButton.addActionListener(e -> existingField.setText(""));
        clearExistingButton.setBackground(Color.DARK_GRAY);
        clearExistingButton.setForeground(Color.WHITE);
        clearExistingPanel.add(clearExistingButton, BorderLayout.EAST);
        formPanel.add(clearExistingPanel);

        // Δημιουργία πεδίου για Future
        JPanel futurePanel = new JPanel(new BorderLayout());
        futurePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        futurePanel.add(new JLabel("Μέτρα διαφύλαξης/ανάδειξης που προτείνεται να εφαρμοστούν στο μέλλον (300 έως 500 λέξεις):"), BorderLayout.NORTH);
        futureField = new JTextArea(3,20);
        futureField.setLineWrap(true);
        futureField.setWrapStyleWord(true);
        futurePanel.add(new JScrollPane(futureField), BorderLayout.CENTER);
        formPanel.add(futurePanel);

        JPanel clearFuturePanel = new JPanel(new BorderLayout());
        clearFuturePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearFutureButton = new JButton("Διαγραφή");
        clearFutureButton.addActionListener(e -> futureField.setText(""));
        clearFutureButton.setBackground(Color.DARK_GRAY);
        clearFutureButton.setForeground(Color.WHITE);
        clearFuturePanel.add(clearFutureButton, BorderLayout.EAST);
        formPanel.add(clearFuturePanel);

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

        // Πεδίο για την ημερομηνία εισαγωγής
        JPanel datePanel = new JPanel( new BorderLayout());
        datePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        dateOfEntryField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd")));
        dateOfEntryField.setColumns(20);
        dateOfEntryField.setEnabled(false);
        datePanel.add(new JLabel("Ημερομηνία Δημιουργίας (yyyy-MM-dd):"), BorderLayout.NORTH);
        datePanel.add(dateOfEntryField);
        formPanel.add(datePanel);

        // Πεδίο για τον χρήστη που ανέβασε το αρχείο
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
            String filepath = filePathField.getText();
            String category = (String) categoryComboBox.getSelectedItem();
            String dateOfEntry = dateOfEntryField.getText();
            String whoUploadedIt = whoUploadedItField.getText();

            // Έλεγχος αν το category δεν είναι κενό
            if (category != null && !category.equals("Επιλέξτε κατηγορία")) {
                // Αν το category έχει επιλεγεί, τα υπόλοιπα πεδία είναι υποχρεωτικά
                if (filepath.isEmpty() || dateOfEntry.isEmpty() || whoUploadedIt.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Παρακαλώ συμπληρώστε όλα τα πεδία αν έχετε επιλέξει κατηγορία.");
                    return;
                }
            }

            if (category.equals("link")) {
                // Έλεγχος για link
                if (!filepath.startsWith("http://") && !filepath.startsWith("https://")) {
                    JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε έναν έγκυρο σύνδεσμο.");
                    return;
                }

                String linkHash = getLinkHash(filepath);

                // Έλεγχος αν το link hash υπάρχει ήδη στη λίστα
                for (String entry : evidenceList) {
                    if (entry.contains(linkHash)) {
                        JOptionPane.showMessageDialog(null, "Το link υπάρχει ήδη στη λίστα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Δημιουργία της καταχώρησης για το link
                String evidenceEntry = category + ", " + filepath + ", " + linkHash + ", " + dateOfEntry + ", " + whoUploadedIt;

                // Προσθήκη στη λίστα
                evidenceList.add(evidenceEntry);
                if (evidenceField.getText().isEmpty()) {
                    evidenceField.setText(evidenceEntry + ";");
                } else {
                    evidenceField.append("\n" + evidenceEntry + ";");
                }
            } else {
                // Έλεγχος για αρχείο
                File file = new File(filepath);
                if (!file.exists()) {
                    JOptionPane.showMessageDialog(this, "Το αρχείο δεν υπάρχει. Παρακαλώ επιλέξτε ένα έγκυρο αρχείο.");
                    return;
                }

                if (!isFileValidForCategory(file, category)) {
                    JOptionPane.showMessageDialog(this, "Το αρχείο δεν ταιριάζει με την επιλεγμένη κατηγορία.");
                    return;
                }

                String fileHash = getFileHash(file);

                // Έλεγχος αν το fileHash υπάρχει ήδη στη λίστα
                for (String entry : evidenceList) {
                    if (entry.contains(fileHash)) {
                        JOptionPane.showMessageDialog(null, "Το fileHash υπάρχει ήδη στη λίστα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Δημιουργία της καταχώρησης για το αρχείο
                String evidenceEntry = category + ", " + filepath + ", " + fileHash + ", " + dateOfEntry + ", " + whoUploadedIt;

                // Προσθήκη στη λίστα
                evidenceList.add(evidenceEntry);
                if (evidenceField.getText().isEmpty()) {
                    evidenceField.setText(evidenceEntry + ";");
                } else {
                    evidenceField.append("\n" + evidenceEntry + ";");
                }
            }

            // Καθαρισμός πεδίων
            filePathField.setText("");
            dateOfEntryField.setText("");
            whoUploadedItField.setText("");
            categoryComboBox.setSelectedIndex(0);
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
        editcompetentpersonsPanel.add(editcompetentpersonsButton, BorderLayout.EAST);
        formPanel.add(editcompetentpersonsPanel);

        // Προσθήκη κουμπιού διαγραφής
        JPanel removecompetentpersonsPanel = new JPanel(new BorderLayout());
        removecompetentpersonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton removecompetentpersonsButton = new JButton("Διαγραφή Αρμόδιου Προσώπου");
        removecompetentpersonsButton.setBackground(Color.DARK_GRAY);
        removecompetentpersonsButton.setForeground(Color.WHITE);
        removecompetentpersonsPanel.add(removecompetentpersonsButton, BorderLayout.EAST);
        formPanel.add(removecompetentpersonsPanel);

        addcompetentpersonsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Λήψη τιμών από τα πεδία
                String firstname = firstnameField.getText();
                String lastname = lastnameField.getText();
                String emailcp = emailcpField.getText();
                String telnumber = telnumberField.getText();
                String addresscp = addresscpField.getText();

                // Έλεγχος αν όλα τα πεδία είναι συμπληρωμένα
                if (firstname.isEmpty() || lastname.isEmpty() || emailcp.isEmpty() || telnumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Όλα τα πεδία είναι υποχρεωτικά!", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!emailcp.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το email", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!telnumber.matches("^((\\+30)?[2-9][0-9]{9}|[2-9][0-9]{9})$")) {
                    JOptionPane.showMessageDialog(null, "Δεν είναι έγκυρο το τηλέφωνο", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Έλεγχος αν το email υπάρχει ήδη στη λίστα
                for (String entry : competentpersonsList) {
                    if (entry.contains(emailcp)) {
                        JOptionPane.showMessageDialog(null, "Το email υπάρχει ήδη στη λίστα.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }


                String competentEntry = "Όνομα: " + firstname + "\n" +
                        "Επώνυμο: " + lastname + "\n" +
                        "Email: " + emailcp + "\n" +
                        "Τηλέφωνο: " + telnumber;

                // Έλεγχος αν έχει εισαχθεί διεύθυνση
                if (!addresscp.isEmpty()) {
                    competentEntry += "\nΔιεύθυνση: " + addresscp;
                }

                competentpersonsList.add(competentEntry);

                competentpersonsField.setText(String.join(";\n", competentpersonsList));

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

            // Διάσπαση της επιλεγμένης καταχώρησης στα πεδία
            String[] competentParts = selectedCompetent.split("\n");
            String firstname = competentParts[0].split(": ")[1];
            String lastname = competentParts[1].split(": ")[1];
            String emailcp = competentParts[2].split(": ")[1];
            String telnumber = competentParts[3].split(": ")[1];
            String addresscp = "";

            // Έλεγχος αν υπάρχει διεύθυνση στην καταχώρηση
            if (competentParts.length > 4 && competentParts[4].startsWith("Διεύθυνση: ")) {
                addresscp = competentParts[4].split(": ")[1];
            }
            // Φόρτωση των δεδομένων στα πεδία επεξεργασίας
            firstnameField.setText(firstname);
            lastnameField.setText(lastname);
            emailcpField.setText(emailcp);
            telnumberField.setText(telnumber);
            addresscpField.setText(addresscp);

            // Διαγραφή της παλιάς καταχώρησης από τη λίστα πριν την επεξεργασία
            competentpersonsList.remove(selectedCompetent);

            // Ενημέρωση του JTextArea μετά τη διαγραφή της παλιάς καταχώρησης
            competentpersonsField.setText("");
            for (String competentEntry : competentpersonsList) {
                competentpersonsField.append(competentEntry + ";\n");
            }
        });

        // Προσθήκη κουμπιού για τη διαγραφή αρμόδιου προσώπου
        removecompetentpersonsButton.addActionListener(e -> {
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

            competentpersonsList.remove(selectedCompetent);

            competentpersonsField.setText("");
            for (String competentEntry : competentpersonsList) {
                competentpersonsField.append(competentEntry + ";\n");
            }
        });


        //Βιβλιογραφία
        JPanel bibliographyPanel = new JPanel(new BorderLayout());
        bibliographyPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        bibliographyPanel.add(new JLabel("Βιβλιογραφία:"), BorderLayout.NORTH);
        bibliographyField = new JTextArea(5, 20);
        bibliographyField.setLineWrap(true);
        bibliographyField.setWrapStyleWord(true);
        bibliographyPanel.add(new JScrollPane(bibliographyField), BorderLayout.CENTER);
        formPanel.add(bibliographyPanel);

        JPanel clearBibliographyPanel = new JPanel(new BorderLayout());
        clearBibliographyPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        JButton clearBibliographyButton = new JButton("Διαγραφή");
        clearBibliographyButton.addActionListener(e -> bibliographyField.setText(""));
        clearBibliographyButton.setBackground(Color.DARK_GRAY);
        clearBibliographyButton.setForeground(Color.WHITE);
        clearBibliographyPanel.add(clearBibliographyButton, BorderLayout.EAST);
        formPanel.add(clearBibliographyPanel);

        // Υποβολή κουμπιού
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JButton submitButton = new JButton("Υποβολή");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });
        submitPanel.add(submitButton);
        formPanel.add(submitPanel);

        add(new JScrollPane(formPanel), BorderLayout.CENTER);
    }

    private void updateOtherNameField() {
        otherNameField.setText("");
        for (String name : otherNamesList) {
            otherNameField.append(name + ";" + "\n");
        }
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
        for (String keyword : keywordsList) {
            keywordField.append(keyword + ";" + "\n");
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
        for (String equipment : equipmentsList) {
            equipmentField.append(equipment + ";" + "\n");
        }
    }

    private void updateProductField() {
        productField.setText("");
        for (String product : productsList) {
            productField.append(product + ";" + "\n");
        }
    }

    // Μέθοδος για τον έλεγχο του αρχείου σύμφωνα με την κατηγορία
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

            // Επιστρέφει τον MIME τύπο αν είναι έγκυρος
            return mimeType;
        } catch (IOException e) {
            e.printStackTrace();
            return "unknown"; // Επιστρέφει "unknown" σε περίπτωση σφάλματος
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
            // Χρησιμοποιούμε τον αλγόριθμο SHA-256 για τη δημιουργία του hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(link.getBytes("UTF-8"));

            // Μετατρέπουμε τα bytes του hash σε δεκαεξαδική μορφή
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

    private void updateEvidenceDisplay() {
        evidenceField.setText("");
        for (String evidence : evidenceList) {
            evidenceField.append(evidence + ";" + "\n");
        }
    }

    private void submitForm() {
        String name = nameField.getText();
        String year = yearField.getText();
        String description = descriptionArea.getText();
        String full_description = full_descriptionArea.getText();

        // Έλεγχος για το υποχρεωτικό πεδίο 'name'
        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Το όνομα είναι υποχρεωτικό.");
            return;
        }

        // Έλεγχος αν το όνομα υπάρχει ήδη στη βάση δεδομένων
        if (doesNameExist(name)) {
            JOptionPane.showMessageDialog(this, "Το όνομα υπάρχει ήδη στη βάση δεδομένων. Παρακαλώ επιλέξτε ένα διαφορετικό όνομα.");
            return;
        }

        // Έλεγχος αν το year είναι έτος (4 ψηφία)
        if (!year.matches("\\d{4}")) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε ένα έγκυρο έτος (π.χ., 2024).");
            return;
        }

        String otherNames = String.join(";", otherNamesList);
        if (otherNamesList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον ένα όνομα.");
            return;
        }

        // Έλεγχος για το υποχρεωτικό πεδίο 'description'
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Η σύντομη περιγραφή είναι υποχρεωτική.");
            return;
        }
        // Έλεγχος λέξεων για description (μέγιστο 100 λέξεις)
        if (description.split("\\s+").length > 100) {
            JOptionPane.showMessageDialog(this, "Η σύντομη περιγραφή δεν πρέπει να υπερβαίνει τις 100 λέξεις.");
            return;
        }

        String fields_apk = String.join(";", fieldList);
        if (fieldList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον ένα πεδίο ΑΠΚ.");
            return;
        }

        String location = String.join(";",locationList);

        String locationDescription = locationDescriptionArea.getText();
        // Έλεγχος λέξεων για locationDescription (μέγιστο 100 λέξεις)
        if (locationDescription.split("\\s+").length > 100) {
            JOptionPane.showMessageDialog(this, "Η περιγραφή τοποθεσίας δεν πρέπει να υπερβαίνει τις 100 λέξεις.");
            return;
        }

        String keywords = String.join(";", keywordsList);
        if (keywordsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον μία λέξη-κλειδί.");
            return;
        }

        String foreas = String.join(";", foreasList);
        if (foreasList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον ένα φορέα.");
            return;
        }

        // Έλεγχος λέξεων για fullDescription (500 έως 1000 λέξεις)
        int full_descriptionWords = full_description.split("\\s+").length;
        if (full_descriptionWords < 500 || full_descriptionWords > 1000) {
            JOptionPane.showMessageDialog(this, "Η αναλυτική περιγραφή πρέπει να έχει από 500 έως 1000 λέξεις.");
            return;
        }

        String performanceAreas = String.join(";", performanceAreasList);
        if (performanceAreasList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον ένα χώρο επιτέλεσης.");
            return;
        }

        String facilities = String.join(";", facilitiesList);
        if (facilitiesList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον μία εγκατάσταση.");
            return;
        }

        String equipments = String.join(";", equipmentsList);
        if (equipmentsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον ένα εξοπλισμό.");
            return;
        }

        String products = String.join(";", productsList);
        if (productsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον ένα προϊόν.");
            return;
        }

        String historical_data = historical_dataField.getText();
        if (historical_data.split("\\s+").length > 700) {
            JOptionPane.showMessageDialog(this, "Τα Ιστορικά δεδομένα δεν πρέπει να υπερβαίνει τις 700 λέξεις.");
            return;
        }

        String members = membersField.getText();
        int membersWords = members.split("\\s+").length;
        if (membersWords < 100 || membersWords > 300) {
            JOptionPane.showMessageDialog(this, "Η Σημασία για τα μέλη της κοινότητας πρέπει να έχει από 100 έως 300 λέξεις.");
            return;
        }

        String society = societyField.getText();
        int societyWords = society.split("\\s+").length;
        if (societyWords < 100 || societyWords > 200) {
            JOptionPane.showMessageDialog(this, "Η Σημασία για την σύγχρονη Ελληνική κοινωνία πρέπει να έχει από 100 έως 200 λέξεις.");
            return;
        }

        String community = communityField.getText();
        int communityWords = community.split("\\s+").length;
        if (communityWords < 300 || communityWords > 500) {
            JOptionPane.showMessageDialog(this, "Η Συμμετοχή της κοινότητας στην προετοιμασία της εγγραφής του στο Εθνικό Ευρετήριο Άυλης Πολιτιστικής Κληρονομιάς της Ελλάδας πρέπει να έχει από 300 έως 500 λέξεις.");
            return;
        }

        String transmission = transmissionField.getText();
        int transmissionWords = transmission.split("\\s+").length;
        if (transmissionWords < 200 || transmissionWords > 300) {
            JOptionPane.showMessageDialog(this, "Οι Τρόποι μετάδοσης στις νεότερες γενιές πρέπει να είναι από 200 έως 300 λέξεις.");
            return;
        }

        String existing = existingField.getText();
        int existingWords = existing.split("\\s+").length;
        if (existingWords < 200 || existingWords > 300) {
            JOptionPane.showMessageDialog(this, "Τα Μέτρα διαφύλαξης/ανάδειξης που έχουν ληφθεί στο παρελθόν ή που εφαρμόζονται σήμερα πρέπει να είναι από 200 έως 300 λέξεις.");
            return;
        }

        String future = futureField.getText();
        int futureWords = future.split("\\s+").length;
        if (futureWords < 300 || futureWords > 500) {
            JOptionPane.showMessageDialog(this, "Τα Μέτρα διαφύλαξης/ανάδειξης που προτείνεται να εφαρμοστούν στο μέλλον πρέπει να είναι από 300 έως 500 λέξεις.");
            return;
        }

        System.out.println("Evidence List: " + evidenceList);
        String evidence = String.join(";", evidenceList);
        if (evidenceList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον ένα τεκμήριο.");
            return;
        }
        System.out.println("Evidence: " + evidence);

        String competent_persons = String.join(";", competentpersonsList);
        if (competentpersonsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Παρακαλώ προσθέστε τουλάχιστον ένα αρμόδιο πρόσωπο.");
            return;
        }

        String bibliography = bibliographyField.getText();
        if (bibliography.isEmpty()){
            JOptionPane.showMessageDialog(this, "Παρακαλώ εισάγετε βιβλιογραφία");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             CallableStatement stmt = conn.prepareCall("{CALL InsertStoixeio(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}")) {

            // Ρύθμιση του @full_name πριν την εκτέλεση της stored procedure
            String setSessionFullName = "SET @full_name = ?";
            try (PreparedStatement setSessionStmt = conn.prepareStatement(setSessionFullName)) {
                setSessionStmt.setString(1, Login.UserSession.getFullName());
                setSessionStmt.executeUpdate();
            }

            stmt.setString(1, name);
            stmt.setInt(2, Integer.parseInt(year));
            stmt.setString(3, description);
            stmt.setString(4, full_description);
            stmt.setString(5, historical_data);
            stmt.setString(6, bibliography);
            stmt.setString(7, otherNames);
            stmt.setString(8, fields_apk);
            stmt.setString(9,locationDescription);
            stmt.setString(10, keywords);
            stmt.setString(11, foreas);
            stmt.setString(12, performanceAreas);
            stmt.setString(13, facilities);
            stmt.setString(14, equipments);
            stmt.setString(15, products);
            stmt.setString(16, members);
            stmt.setString(17, society);
            stmt.setString(18, community);
            stmt.setString(19, transmission);
            stmt.setString(20, existing);
            stmt.setString(21, future);
            stmt.setString(22, competent_persons);

            stmt.registerOutParameter(23, Types.INTEGER);
            stmt.registerOutParameter(24, Types.INTEGER);

            stmt.execute();  // Εκτέλεση της stored procedure

            int stoixeioid = stmt.getInt(23);
            int location_id = stmt.getInt(24);

            // Διαχωρισμός και επεξεργασία του evidence
            String[] evidenceEntries = evidence.split(";");

            for (String entry : evidenceEntries) {
                // Διαχωρισμός κάθε καταχώρησης (entry) σε πεδία με βάση το ","
                String[] evidenceDetails = entry.split(",");

                // Εξασφάλιση ότι υπάρχουν αρκετά στοιχεία στο evidenceDetails
                if (evidenceDetails.length == 5) {
                    // Ανάλυση των πεδίων της απόδειξης
                    String category = evidenceDetails[0].trim();
                    String filePath = evidenceDetails[1].trim();
                    String fileHash = evidenceDetails[2].trim();
                    String dateOfEntry = evidenceDetails[3].trim();
                    String whoUploadedIt = evidenceDetails[4].trim();

                    if (category.equalsIgnoreCase("link")) {
                        // Επεξεργασία ως σύνδεσμος (URL)
                        String fileName = filePath;
                        String fileType = "URL";
                        String mimeType = "text/url";
                        byte[] fileBytes = null;

                        // Εισαγωγή στον πίνακα 'evidence'
                        try (PreparedStatement evidenceStmt = conn.prepareStatement(
                                "INSERT INTO evidence (stoixeio_idStoixeio, category, file_name, file_type, file_size, mime_type, file_hash, date_of_entry, who_uploaded_it, file_data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                            evidenceStmt.setInt(1, stoixeioid);
                            evidenceStmt.setString(2, category);
                            evidenceStmt.setString(3, fileName);
                            evidenceStmt.setString(4, fileType);
                            evidenceStmt.setLong(5, 0);
                            evidenceStmt.setString(6, mimeType);
                            evidenceStmt.setString(7, fileHash);
                            evidenceStmt.setString(8, dateOfEntry);
                            evidenceStmt.setString(9, whoUploadedIt);
                            evidenceStmt.setBytes(10, fileBytes);

                            evidenceStmt.executeUpdate();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την εισαγωγή συνδέσμου στο evidence: " + ex.getMessage());
                        }
                    } else {

                        File file = new File(filePath);

                        if (file.exists() && file.isFile()) {
                            String fileNameWithExtension = file.getName();
                            String fileName = fileNameWithExtension.contains(".")
                                    ? fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'))
                                    : fileNameWithExtension;
                            String fileType = getFileType(file);
                            long fileSize = file.length();
                            String mimeType = getMimeType(file);

                            // Ανάκτηση file_data ως InputStream
                            try (InputStream fileDataStream = new FileInputStream(file)) {
                                byte[] fileBytes = fileDataStream.readAllBytes();

                                // Εισαγωγή δεδομένων στον πίνακα 'evidence'
                                try (PreparedStatement evidenceStmt = conn.prepareStatement(
                                        "INSERT INTO evidence (stoixeio_idStoixeio, category, file_name, file_type, file_size, mime_type, file_hash, date_of_entry, who_uploaded_it, file_data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                                    evidenceStmt.setInt(1, stoixeioid);
                                    evidenceStmt.setString(2, category);
                                    evidenceStmt.setString(3, fileName);
                                    evidenceStmt.setString(4, fileType);
                                    evidenceStmt.setLong(5, fileSize);
                                    evidenceStmt.setString(6, mimeType);
                                    evidenceStmt.setString(7, fileHash);
                                    evidenceStmt.setString(8, dateOfEntry);
                                    evidenceStmt.setString(9, whoUploadedIt);
                                    evidenceStmt.setBytes(10, fileBytes);

                                    evidenceStmt.executeUpdate();
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(this, "Σφάλμα κατά την εισαγωγή στο evidence: " + ex.getMessage());
                                }
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(this, "Σφάλμα κατά την ανάγνωση του αρχείου: " + ex.getMessage());
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Σφάλμα: Το αρχείο δεν υπάρχει ή δεν είναι έγκυρο: " + filePath);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Σφάλμα: Η απόδειξη δεν περιέχει τα απαραίτητα δεδομένα.");
                }
            }

            // Διαχωρισμός και επεξεργασία του location
            String[] locationEntries = location.split(";");

            for (String locentry : locationEntries) {
                String[] locationDetails = locentry.split(",");

                String decentralizedAdmin = (locationDetails.length > 0) ? locationDetails[0].trim() : "";
                String region = (locationDetails.length > 1) ? locationDetails[1].trim() : "";
                String regionalUnit = (locationDetails.length > 2) ? locationDetails[2].trim() : "";
                String municipality = (locationDetails.length > 3) ? locationDetails[3].trim() : "";
                String municipalUnit = (locationDetails.length > 4) ? locationDetails[4].trim() : "";
                String communities = (locationDetails.length > 5) ? locationDetails[5].trim() : "";


                // Αρχικοποίηση των μεταβλητών
                Integer decentralizedAdminID = null;
                Integer regionID = null;
                Integer regionalUnitID = null;
                Integer municipalityID = null;
                Integer municipalUnitID = null;
                Integer communityID = null;


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

                try (PreparedStatement stmtLocation = conn.prepareStatement(
                        "INSERT INTO kalikratis (decentralized_administration_id, region_id, regional_unit_id, municipality_id, municipal_unit_id, community_id, location_id_location) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                    // Αν κάποιο πεδίο είναι null, θα εισάγεται ως NULL στη βάση
                    stmtLocation.setObject(1, decentralizedAdminID);
                    stmtLocation.setObject(2, regionID);
                    stmtLocation.setObject(3, regionalUnitID);
                    stmtLocation.setObject(4, municipalityID);
                    stmtLocation.setObject(5, municipalUnitID);
                    stmtLocation.setObject(6, communityID);
                    stmtLocation.setObject(7, location_id);

                    stmtLocation.executeUpdate();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Σφάλμα κατά την εισαγωγή στην τοποθεσία: " + ex.getMessage());
                }
            }


            resetForm();
            JOptionPane.showMessageDialog(this, "Η καταχώρηση ήταν επιτυχής με ID: " + stoixeioid);

            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την εισαγωγή στη βάση δεδομένων: " + ex.getMessage());
        }
    }

    private boolean doesNameExist(String name) {
        boolean exists = false;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM stoixeio WHERE name = ?")) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }


    private void resetForm() {
        idStoixeioField.setText("");
        nameField.setText("");
        yearField.setText("");
        otherNameField.setText("");
        otherNamesList.clear();
        descriptionArea.setText("");
        fieldComboBox.setSelectedItem(0);
        justification_of_fieldsField.setText("");
        fieldList.clear();
        fieldField.setText("");
        locationDescriptionArea.setText("");
        decentralizedAdminComboBox.setSelectedItem(0);
        regionComboBox.setSelectedItem(0);
        regionalUnitComboBox.setSelectedItem(0);
        municipalityComboBox.setSelectedItem(0);
        municipalUnitComboBox.setSelectedItem(0);
        communitiesComboBox.setSelectedItem(0);
        locationField.setText("");
        locationList.clear();
        full_descriptionArea.setText("");
        keywordField.setText("");
        keywordsList.clear();
        foreasField.setText("");
        foreasList.clear();
        performanceAreaField.setText("");
        performanceAreasList.clear();
        facilitiesField.setText("");
        facilitiesList.clear();
        equipmentField.setText("");
        equipmentsList.clear();
        productField.setText("");
        productsList.clear();
        historical_dataField.setText("");
        membersField.setText("");
        societyField.setText("");
        communityField.setText("");
        transmissionField.setText("");
        existingField.setText("");
        futureField.setText("");
        evidenceField.setText("");
        evidenceList.clear();
        competentpersonsField.setText("");
        competentpersonsList.clear();
        bibliographyField.setText("");
    }
}

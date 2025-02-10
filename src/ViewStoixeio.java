import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewStoixeio extends JFrame {
    private JTextField idStoixeioField, nameField, yearField;
    private JTextArea descriptionArea, full_descriptionArea, historical_dataField, bibliographyField, otherNameField, keywordField, foreasField,  performanceAreaField, facilitiesField, equipmentField, productField;
    private JTextArea membersField, societyField, communityField, transmissionField, existingField, futureField, fieldField, evidenceField, locationDescriptionArea, locationField, competentpersonsField;
    private int id_user;
    private String email;

    // Ρυθμίσεις για σύνδεση με τη βάση δεδομένων
    private static final String DB_URL = "jdbc:mysql://localhost:3306/apk";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "apk2024";
    public ViewStoixeio(String name, int id_user, String email) {
        this.id_user = id_user;
        this.email = email;

        int user_Id = Login.UserSession.getUserId();
        String userName = Login.UserSession.getFullName();

        setTitle("Προβολή Στοιχείου - " + name);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        UIManager.put("Panel.background", new Color(255, 255, 255));
        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 12));
        UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Κουμπί Επεξεργασίας
        JPanel editPanel = new JPanel(new BorderLayout());
        editPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        JButton editButton = new JButton("Επεξεργασία Στοιχείου");
        editButton.setBackground(Color.GRAY);
        editButton.setForeground(Color.WHITE);
        editPanel.add(editButton, BorderLayout.EAST);
        formPanel.add(editPanel);


        // ID (Μόνο για εμφάνιση, χωρίς δυνατότητα επεξεργασίας)
        JPanel IdPanel = new JPanel(new BorderLayout());
        IdPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        IdPanel.add(new JLabel("ID:"), BorderLayout.NORTH);
        idStoixeioField = new JTextField();
        IdPanel.add(idStoixeioField, BorderLayout.CENTER);
        idStoixeioField.setEditable(false);
        idStoixeioField.setForeground(Color.BLACK);
        formPanel.add(IdPanel);

        // Όνομα
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        namePanel.add(new JLabel("Όνομα:"), BorderLayout.NORTH);
        nameField = new JTextField();
        namePanel.add(nameField, BorderLayout.CENTER);
        nameField.setEditable(false);
        nameField.setForeground(Color.BLACK);
        formPanel.add(namePanel);

        // Έτος
        JPanel yearPanel = new JPanel(new BorderLayout());
        yearPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        yearPanel.add(new JLabel("Έτος εισαγωγής:"), BorderLayout.NORTH);
        yearField = new JTextField();
        yearPanel.add(yearField, BorderLayout.CENTER);
        yearField.setEditable(false);
        yearField.setForeground(Color.BLACK);
        formPanel.add(yearPanel);

        //Άλλα ονόματα
        JPanel otherNamePanel = new JPanel(new BorderLayout());
        otherNamePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        otherNamePanel.add(new JLabel("Άλλα Ονόματα:"), BorderLayout.NORTH);
        otherNameField = new JTextArea(3, 20);
        otherNameField.setEditable(false);
        otherNameField.setLineWrap(true);
        otherNameField.setWrapStyleWord(true);
        otherNamePanel.add(new JScrollPane(otherNameField));
        formPanel.add(otherNamePanel);

        // Περιγραφή
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        descriptionPanel.add(new JLabel("Σύντομη Περιγραφή:"), BorderLayout.NORTH);
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        descriptionArea.setEditable(false);
        descriptionArea.setForeground(Color.BLACK);
        formPanel.add(descriptionPanel);

        //Πεδία ΑΠΚ
        JPanel fieldApkPanel = new JPanel(new BorderLayout());
        fieldApkPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        fieldApkPanel.add(new JLabel("Πεδία ΑΠΚ:"), BorderLayout.NORTH);
        fieldField = new JTextArea(5, 20);
        fieldField.setEditable(false);
        fieldField.setLineWrap(true);
        fieldField.setWrapStyleWord(true);
        fieldApkPanel.add(new JScrollPane(fieldField));
        formPanel.add(fieldApkPanel);

        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        locationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        locationPanel.add(new JLabel("Περιοχή που απαντάται"));
        formPanel.add(locationPanel);

        // Περιγραφή Τοποθεσίας
        JPanel descriptionLocationPanel = new JPanel(new BorderLayout());
        descriptionLocationPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        descriptionLocationPanel.add(new JLabel("Περιγραφή Τοποθεσίας:"), BorderLayout.NORTH);
        locationDescriptionArea = new JTextArea(3, 20);
        locationDescriptionArea.setLineWrap(true);
        locationDescriptionArea.setWrapStyleWord(true);
        locationDescriptionArea.setEditable(false);
        descriptionLocationPanel.add(new JScrollPane(locationDescriptionArea), BorderLayout.CENTER);
        formPanel.add(descriptionLocationPanel);

        // Περιγραφή Τοποθεσίας
        JPanel kalikratisPanel = new JPanel(new BorderLayout());
        kalikratisPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        kalikratisPanel.add(new JLabel("Καλικράτης:"), BorderLayout.NORTH);
        locationField = new JTextArea(3, 20);
        locationField.setLineWrap(true);
        locationField.setWrapStyleWord(true);
        kalikratisPanel.add(new JScrollPane(locationField), BorderLayout.CENTER);
        formPanel.add(kalikratisPanel);

        //Λέξεις-Κλειδιά
        JPanel keywordsPanel = new JPanel(new BorderLayout());
        keywordsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        keywordsPanel.add(new JLabel("Λέξεις-Κλειδιά:"), BorderLayout.NORTH);
        keywordField = new JTextArea(3, 20);
        keywordField.setEditable(false);
        keywordField.setLineWrap(true);
        keywordField.setWrapStyleWord(true);
        keywordsPanel.add(new JScrollPane(keywordField));
        formPanel.add(keywordsPanel);

        //Φορεας
        JPanel foreasPanel = new JPanel(new BorderLayout());
        foreasPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        foreasPanel.add(new JLabel("Φορείς Μετάδοσης:"), BorderLayout.NORTH);
        foreasField = new JTextArea(6, 20);
        foreasField.setEditable(false);
        foreasField.setLineWrap(true);
        foreasField.setWrapStyleWord(true);
        foreasPanel.add(new JScrollPane(foreasField));
        formPanel.add(foreasPanel);

        // Αναλυτική Περιγραφή
        JPanel fullDescriptionPanel = new JPanel(new BorderLayout());
        fullDescriptionPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        fullDescriptionPanel.add(new JLabel("Αναλυτική Περιγραφή:"), BorderLayout.NORTH);
        full_descriptionArea = new JTextArea(5, 20);
        fullDescriptionPanel.add(new JScrollPane(full_descriptionArea), BorderLayout.CENTER);
        full_descriptionArea.setLineWrap(true);
        full_descriptionArea.setWrapStyleWord(true);
        full_descriptionArea.setEditable(false);
        full_descriptionArea.setForeground(Color.BLACK);
        formPanel.add(fullDescriptionPanel);

        //Χώροι Επιτέλεσης
        JPanel performance_areaPanel = new JPanel(new BorderLayout());
        performance_areaPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        performance_areaPanel.add(new JLabel("Χώροι Επιτέλεσης:"), BorderLayout.NORTH);
        performanceAreaField = new JTextArea(3, 20);
        performanceAreaField.setEditable(false);
        performanceAreaField.setLineWrap(true);
        performanceAreaField.setWrapStyleWord(true);
        performance_areaPanel.add(new JScrollPane(performanceAreaField));
        formPanel.add(performance_areaPanel);

        //Εγκαταστάσεις
        JPanel facilitiesPanel = new JPanel(new BorderLayout());
        facilitiesPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        facilitiesPanel.add(new JLabel("Εγκαταστάσεις:"), BorderLayout.NORTH);
        facilitiesField = new JTextArea(3, 20);
        facilitiesField.setEditable(false);
        facilitiesField.setLineWrap(true);
        facilitiesField.setWrapStyleWord(true);
        facilitiesPanel.add(new JScrollPane(facilitiesField));
        formPanel.add(facilitiesPanel);

        //Εξοπλισμός
        JPanel equipmentPanel = new JPanel(new BorderLayout());
        equipmentPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        equipmentPanel.add(new JLabel("Εξοπλισμός:"), BorderLayout.NORTH);
        equipmentField = new JTextArea(3, 20);
        equipmentField.setEditable(false);
        equipmentField.setLineWrap(true);
        equipmentField.setWrapStyleWord(true);
        equipmentPanel.add(new JScrollPane(equipmentField));
        formPanel.add(equipmentPanel);

        //Προϊόν
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        productPanel.add(new JLabel("Προϊόν:"), BorderLayout.NORTH);
        productField = new JTextArea(3, 20);
        productField.setEditable(false);
        productField.setLineWrap(true);
        productField.setWrapStyleWord(true);
        productPanel.add(new JScrollPane(productField));
        formPanel.add(productPanel);

        //Ιστορικά Δεδομένα
        JPanel historicalDataPanel = new JPanel(new BorderLayout());
        historicalDataPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        historicalDataPanel.add(new JLabel("Ιστορικά Δεδομένα:"), BorderLayout.NORTH);
        historical_dataField = new JTextArea(5, 20);
        historical_dataField.setLineWrap(true);
        historical_dataField.setWrapStyleWord(true);
        historicalDataPanel.add(new JScrollPane(historical_dataField), BorderLayout.CENTER);
        historical_dataField.setEditable(false);
        historical_dataField.setForeground(Color.BLACK);
        formPanel.add(historicalDataPanel);

        JPanel importancePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        importancePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        importancePanel.add(new JLabel("Η σημασία του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς σήμερα"));
        formPanel.add(importancePanel);

        // Δημιουργία πεδίου για Members
        JPanel membersPanel = new JPanel(new BorderLayout());
        membersPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        membersPanel.add(new JLabel("Σημασία για τα μέλη της κοινότητας (100 έως 300 λέξεις):"), BorderLayout.NORTH);
        membersField = new JTextArea(4,20);
        membersField.setLineWrap(true);
        membersField.setWrapStyleWord(true);
        membersPanel.add(new JScrollPane(membersField), BorderLayout.CENTER);
        formPanel.add(membersPanel);

        // Δημιουργία πεδίου για Society
        JPanel societyPanel = new JPanel(new BorderLayout());
        societyPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        societyPanel.add(new JLabel("Σημασία για την σύγχρονη Ελληνική κοινωνία (100 έως 200 λέξεις):"), BorderLayout.NORTH);
        societyField = new JTextArea(4,20);
        societyField.setLineWrap(true);
        societyField.setWrapStyleWord(true);
        societyPanel.add(new JScrollPane(societyField), BorderLayout.CENTER);
        formPanel.add(societyPanel);

        // Δημιουργία πεδίου για Community
        JPanel communityPanel = new JPanel(new BorderLayout());
        communityPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        communityPanel.add(new JLabel("Συμμετοχή της κοινότητας στην προετοιμασία της εγγραφής του στο Εθνικό Ευρετήριο Άυλης Πολιτιστικής Κληρονομιάς της Ελλάδας (300 έως 500 λέξεις):"), BorderLayout.NORTH);
        communityField = new JTextArea(6,20);
        communityField.setLineWrap(true);
        communityField.setWrapStyleWord(true);
        communityPanel.add(new JScrollPane(communityField), BorderLayout.CENTER);
        formPanel.add(communityPanel);

        JPanel preservationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        preservationPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        preservationPanel.add(new JLabel("Διαφύλαξη/ανάδειξη του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς"));
        formPanel.add(preservationPanel);

        // Δημιουργία πεδίου για Transmission
        JPanel transmissionPanel = new JPanel(new BorderLayout());
        transmissionPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        transmissionPanel.add(new JLabel("Τρόποι μετάδοσης στις νεότερες γενιές (200 έως 300 λέξεις):"), BorderLayout.NORTH);
        transmissionField = new JTextArea(4,20);
        transmissionField.setLineWrap(true);
        transmissionField.setWrapStyleWord(true);
        transmissionPanel.add(new JScrollPane(transmissionField), BorderLayout.CENTER);
        formPanel.add(transmissionPanel);

        // Δημιουργία πεδίου για Existing
        JPanel existingPanel = new JPanel(new BorderLayout());
        existingPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        existingPanel.add(new JLabel("Μέτρα διαφύλαξης/ανάδειξης του στοιχείου της Άυλης Πολιτιστικής Κληρονομιάς που έχουν ληφθεί στο παρελθόν ή που εφαρμόζονται σήμερα (200 έως 300 λέξεις):"), BorderLayout.NORTH);
        existingField = new JTextArea(4,20);
        existingField.setLineWrap(true);
        existingField.setWrapStyleWord(true);
        existingPanel.add(new JScrollPane(existingField), BorderLayout.CENTER);
        formPanel.add(existingPanel);

        // Δημιουργία πεδίου για Future
        JPanel futurePanel = new JPanel(new BorderLayout());
        futurePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        futurePanel.add(new JLabel("Μέτρα διαφύλαξης/ανάδειξης που προτείνεται να εφαρμοστούν στο μέλλον (300 έως 500 λέξεις):"), BorderLayout.NORTH);
        futureField = new JTextArea(6,20);
        futureField.setLineWrap(true);
        futureField.setWrapStyleWord(true);
        futurePanel.add(new JScrollPane(futureField), BorderLayout.CENTER);
        formPanel.add(futurePanel);

        JPanel evidencePanel = new JPanel(new BorderLayout());
        evidencePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        evidencePanel.add(new JLabel("Συμπληρωματικά Τεκμήρια:"), BorderLayout.NORTH);
        evidenceField = new JTextArea(6, 20);
        evidenceField.setEditable(false);
        evidenceField.setLineWrap(true);
        evidenceField.setWrapStyleWord(true);
        evidencePanel.add(new JScrollPane(evidenceField));
        formPanel.add(evidencePanel);

        evidenceField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    Point point = e.getPoint();
                    int caretPosition = evidenceField.viewToModel(point);

                    try {

                        int line = evidenceField.getLineOfOffset(caretPosition);
                        int start = evidenceField.getLineStartOffset(line);
                        int end = evidenceField.getLineEndOffset(line);

                        String selectedLine = evidenceField.getText(start, end - start).trim();

                        processEvidenceEntry(selectedLine);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Σφάλμα κατά την ανάγνωση της γραμμής: " + ex.getMessage());
                    }
                }
            }
        });


        //Αρμόδια Πρόσωπα
        JPanel competentpersonsPanel = new JPanel(new BorderLayout());
        competentpersonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        competentpersonsPanel.add(new JLabel("Αρμόδια Πρόσωπα:"), BorderLayout.NORTH);
        competentpersonsField = new JTextArea(6, 20);
        competentpersonsField.setEditable(false);
        competentpersonsField.setLineWrap(true);
        competentpersonsField.setWrapStyleWord(true);
        competentpersonsPanel.add(new JScrollPane(competentpersonsField));
        formPanel.add(competentpersonsPanel);



        //Βιβλιογραφία
        JPanel bibliographyPanel = new JPanel(new BorderLayout());
        bibliographyPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        bibliographyPanel.add(new JLabel("Βιβλιογραφία:"), BorderLayout.NORTH);
        bibliographyField = new JTextArea(5, 20);
        bibliographyField.setLineWrap(true);
        bibliographyField.setWrapStyleWord(true);
        bibliographyPanel.add(new JScrollPane(bibliographyField), BorderLayout.CENTER);
        bibliographyField.setEditable(false);
        bibliographyField.setForeground(Color.BLACK);
        formPanel.add(bibliographyPanel);

        add(new JScrollPane(formPanel), BorderLayout.CENTER);


        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            boolean hasAccess = false;

            // Έλεγχος αν ο χρήστης είναι admin
            try (PreparedStatement adminStmt = conn.prepareStatement(
                    "SELECT 1 FROM user WHERE id_user = ? AND userrole = 'admin'")) {
                adminStmt.setInt(1, id_user);
                try (ResultSet adminRs = adminStmt.executeQuery()) {
                    if (adminRs.next()) {
                        hasAccess = true;
                    }
                }
            }

            // Έλεγχος αν ο χρήστης σχετίζεται με το συγκεκριμένο στοιχείο
            if (!hasAccess) {
                try (PreparedStatement userStmt = conn.prepareStatement(
                        "SELECT 1 FROM user_stoixeio us " +
                                "JOIN stoixeio s ON us.stoixeio_id = s.idStoixeio " +
                                "WHERE us.user_id = ? AND s.name = ?")) {
                    userStmt.setInt(1, id_user);
                    userStmt.setString(2, name);
                    try (ResultSet userRs = userStmt.executeQuery()) {
                        if (userRs.next()) {
                            hasAccess = true;
                        }
                    }
                }
            }

            if (hasAccess) {
                editButton.setEnabled(true);
                editButton.setBackground(Color.DARK_GRAY);
                editButton.setToolTipText("Έχετε δικαίωμα επεξεργασίας.");
            } else {
                editButton.setEnabled(false);
                editButton.setBackground(Color.GRAY);
                editButton.setToolTipText("Δεν έχετε δικαίωμα επεξεργασίας.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά τη σύνδεση με τη βάση δεδομένων.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }




        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (editButton.isEnabled()) {
                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM stoixeio WHERE name = ?")) {

                        stmt.setString(1, name);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            int id = rs.getInt("idStoixeio");
                            int year = rs.getInt("year");

                            Set<String> otherName = loadRelatedData(conn, "SELECT name FROM other_names WHERE stoixeio_idStoixeio = ?", id);
                            String description = rs.getString("description");


                            Set<String> field = new HashSet<>();
                            Set<String> selectedFields = new HashSet<>();

                            try (PreparedStatement fieldStmt = conn.prepareStatement(
                                    "SELECT f.field_apk, sf.justification " +
                                            "FROM stoixeio_fields_apk sf " +
                                            "JOIN fields_apk f ON sf.fields_apk_idfields_apk = f.idfields_apk " +
                                            "WHERE sf.stoixeio_idStoixeio = ?"))  {
                                fieldStmt.setInt(1, id);
                                try (ResultSet fieldRs = fieldStmt.executeQuery()) {
                                    while (fieldRs.next()) {
                                        String fieldApk = fieldRs.getString("field_apk");
                                        String justification = fieldRs.getString("justification");

                                        // Αν το justification είναι κενό, προσθέτουμε μόνο το field_apk, αλλιώς τα συνδυάζουμε
                                        String fieldToAdd;
                                        if (justification == null || justification.isEmpty()) {
                                            fieldToAdd = fieldApk;
                                        } else {
                                            fieldToAdd = fieldApk + ": " + justification; // Συνδυασμός field_apk και justification
                                        }

                                        field.add(fieldToAdd);

                                        selectedFields.add(fieldApk);
                                    }
                                }
                            }

                            String locationDescription = null;

                            try (PreparedStatement locationDescStmt = conn.prepareStatement(
                                    "SELECT locationDescription FROM location WHERE stoixeio_idStoixeio = ?")) {
                                locationDescStmt.setInt(1, id);
                                ResultSet locationDescRs = locationDescStmt.executeQuery();

                                if (locationDescRs.next()) {
                                    locationDescription = locationDescRs.getString("locationDescription");
                                }
                            }


                            Set<String> location = new HashSet<>();

                            try (PreparedStatement locationStmt = conn.prepareStatement(
                                    "SELECT " +
                                            "    da.name AS decentralized_administration_name, " +
                                            "    r.name AS region_name, " +
                                            "    ru.name AS regional_unit_name, " +
                                            "    m.name AS municipality_name, " +
                                            "    mu.name AS municipal_unit_name, " +
                                            "    c.name AS community_name " +
                                            "FROM kalikratis k " +
                                            "LEFT JOIN decentralized_administration da ON k.decentralized_administration_id = da.id " +
                                            "LEFT JOIN region r ON k.region_id = r.id " +
                                            "LEFT JOIN regional_units ru ON k.regional_unit_id = ru.id " +
                                            "LEFT JOIN municipalities m ON k.municipality_id = m.id " +
                                            "LEFT JOIN municipal_units mu ON k.municipal_unit_id = mu.id " +
                                            "LEFT JOIN communities c ON k.community_id = c.id " +
                                            "WHERE k.location_id_location = (SELECT id_location FROM location WHERE stoixeio_idStoixeio = ?)")) {

                                locationStmt.setInt(1, id);
                                ResultSet locationRs = locationStmt.executeQuery();

                                while (locationRs.next()) {
                                    List<String> fields = new ArrayList<>();

                                    String decentralizedAdminName = locationRs.getString("decentralized_administration_name");
                                    if (decentralizedAdminName != null) {
                                        fields.add(decentralizedAdminName);
                                    }

                                    // Ελέγχουμε για την περιοχή (region_name)
                                    String regionName = locationRs.getString("region_name");
                                    if (regionName != null) {
                                        fields.add(regionName);
                                    }

                                    // Ελέγχουμε για την περιφερειακή μονάδα (regional_unit_name)
                                    String regionalUnitName = locationRs.getString("regional_unit_name");
                                    if (regionalUnitName != null) {
                                        fields.add(regionalUnitName);
                                    }

                                    // Ελέγχουμε για τον δήμο (municipality_name)
                                    String municipalityName = locationRs.getString("municipality_name");
                                    if (municipalityName != null) {
                                        fields.add(municipalityName);
                                    }

                                    // Ελέγχουμε για την δημοτική ενότητα (municipal_unit_name)
                                    String municipalUnitName = locationRs.getString("municipal_unit_name");
                                    if (municipalUnitName != null) {
                                        fields.add(municipalUnitName);
                                    }

                                    // Ελέγχουμε για την κοινότητα (community_name)
                                    String communityName = locationRs.getString("community_name");
                                    if (communityName != null) {
                                        fields.add(communityName);
                                    }

                                    // Αν υπάρχουν μη κενά πεδία, τα συνενώνουμε με κόμμα και τα προσθέτουμε στη λίστα location
                                    if (!fields.isEmpty()) {
                                        String locationEntry = String.join(", ", fields);
                                        location.add(locationEntry);
                                    }
                                }
                            }

                            Set<String> keyword = new HashSet<>();
                            try (PreparedStatement keywordsStmt = conn.prepareStatement(
                                    "SELECT k.keyword " +
                                            "FROM stoixeio_keywords sk " +
                                            "JOIN keywords k ON sk.keyword_id_keyword = k.id_keyword " +
                                            "WHERE sk.stoixeio_idStoixeio = ?")) {

                                keywordsStmt.setInt(1, id);
                                ResultSet keywordsRs = keywordsStmt.executeQuery();

                                while (keywordsRs.next()) {
                                    String keywords = keywordsRs.getString("keyword");
                                    if (keywords != null && !keywords.isEmpty()) {
                                        keyword.add(keywords);
                                    }
                                }
                            }

                            Set<String> foreas = new HashSet<>();
                            try (PreparedStatement instStmt = conn.prepareStatement(
                                    "SELECT f.namef, f.descriptionf, f.address, f.city, f.telephone, f.email, f.TK, f.website " +
                                            "FROM stoixeio_foreas sf " +
                                            "JOIN foreas f ON sf.foreas_id = f.idforeas " +
                                            "WHERE sf.stoixeio_id = ?")) {

                                instStmt.setInt(1, id);
                                ResultSet instRs = instStmt.executeQuery();

                                while (instRs.next()) {
                                    String namef = instRs.getString("namef");
                                    String descriptionf = instRs.getString("descriptionf");
                                    String emailf = instRs.getString("email");
                                    String address = instRs.getString("address");
                                    String city = instRs.getString("city");
                                    String TK = instRs.getString("TK");
                                    String telephone = instRs.getString("telephone");
                                    String website = instRs.getString("website");

                                    String foreasDetails = "Όνομα: " + namef + "\n" +
                                            "Περιγραφή: " + descriptionf + "\n" +
                                            "Email: " + emailf + "\n" +
                                            "Διεύθυνση: " + address + "\n" +
                                            "Πόλη: " + city + "\n" +
                                            "Τ.Κ.: " + TK + "\n" +
                                            "Τηλέφωνο: " + telephone + "\n" +
                                            "Ιστότοπος: " + website;

                                    foreas.add(foreasDetails);
                                }
                            }

                            String fullDescription = rs.getString("full_description");

                            Set<String> performanceArea = new HashSet<>();
                            try (PreparedStatement performanceAreaStmt = conn.prepareStatement("SELECT area, justification_performance_area FROM performance_area WHERE stoixeio_idStoixeio = ?")) {
                                performanceAreaStmt.setInt(1, id);
                                ResultSet performanceAreaRs = performanceAreaStmt.executeQuery();
                                while (performanceAreaRs.next()) {
                                    String area = performanceAreaRs.getString("area");
                                    String justification_performance_area = performanceAreaRs.getString("justification_performance_area");

                                    String performanceAreaEntry = area + ": " + justification_performance_area;
                                    performanceArea.add(performanceAreaEntry);
                                }
                            }

                            Set<String> facilities = new HashSet<>();
                            try (PreparedStatement facilitiesStmt = conn.prepareStatement("SELECT facility, justification_facility FROM facilities WHERE stoixeio_idStoixeio = ?")) {
                                facilitiesStmt.setInt(1, id);
                                ResultSet facilitiesRs = facilitiesStmt.executeQuery();
                                while (facilitiesRs.next()) {
                                    String facility = facilitiesRs.getString("facility");
                                    String justification_facility = facilitiesRs.getString("justification_facility");

                                    String facilitiesEntry = facility + ": " + justification_facility;
                                    facilities.add(facilitiesEntry);
                                }
                            }

                            Set<String> equipment = new HashSet<>();
                            try (PreparedStatement equipmentStmt = conn.prepareStatement("SELECT equipment, justification_equipment FROM equipment WHERE stoixeio_idStoixeio = ?")) {
                                equipmentStmt.setInt(1, id);
                                ResultSet equipmentRs = equipmentStmt.executeQuery();
                                while (equipmentRs.next()) {
                                    String equipments = equipmentRs.getString("equipment");
                                    String justification_equipment = equipmentRs.getString("justification_equipment");

                                    String equipmentEntry = equipments + ": " + justification_equipment;
                                    equipment.add(equipmentEntry);
                                }
                            }

                            Set<String> product = new HashSet<>();
                            try (PreparedStatement productStmt = conn.prepareStatement("SELECT product, justification_product FROM product WHERE stoixeio_idStoixeio = ?")) {
                                productStmt.setInt(1, id);
                                ResultSet productRs = productStmt.executeQuery();
                                while (productRs.next()) {
                                    String products = productRs.getString("product");
                                    String justification_product = productRs.getString("justification_product");

                                    String productEntry = products + ": " + justification_product;
                                    product.add(productEntry);
                                }
                            }

                            String historical_data = rs.getString("historical_data");

                            String members = loadSingleValue(conn, "SELECT members FROM importance WHERE stoixeio_idStoixeio = ?", id);
                            String society = loadSingleValue(conn, "SELECT society FROM importance WHERE stoixeio_idStoixeio = ?", id);
                            String community = loadSingleValue(conn, "SELECT community FROM importance WHERE stoixeio_idStoixeio = ?", id);

                            String transmission = loadSingleValue(conn, "SELECT transmission FROM preservation WHERE stoixeio_idStoixeio = ?", id);
                            String existing = loadSingleValue(conn, "SELECT existing FROM preservation WHERE stoixeio_idStoixeio = ?", id);
                            String future = loadSingleValue(conn, "SELECT future FROM preservation WHERE stoixeio_idStoixeio = ?", id);

                            Set<String> evidence = new HashSet<>();
                            try (PreparedStatement evidenceStmt = conn.prepareStatement("SELECT category, file_name, file_hash, date_of_entry, who_uploaded_it FROM evidence WHERE stoixeio_idStoixeio = ?")) {
                                evidenceStmt.setInt(1, id);
                                ResultSet evidenceRs = evidenceStmt.executeQuery();
                                while (evidenceRs.next()) {
                                    String category = evidenceRs.getString("category");
                                    String fileName = evidenceRs.getString("file_name");
                                    String fileHash = evidenceRs.getString("file_hash");
                                    String dateOfEntry = evidenceRs.getString("date_of_entry");
                                    String whoUploadedIt = evidenceRs.getString("who_uploaded_it");

                                    String evidenceEntry = category + ", " + fileName + ", " + fileHash + ", " + dateOfEntry + ", " + whoUploadedIt;
                                    evidence.add(evidenceEntry);
                                }
                            }

                            Set<String> competentpersons = new HashSet<>();
                            try (PreparedStatement instStmt = conn.prepareStatement(
                                    "SELECT u.first_name, u.last_name, u.email, u.telephone, " +
                                            "IFNULL(NULLIF(TRIM(u.address), ''), '') AS address " +
                                            "FROM apk.user u " +
                                            "JOIN apk.user_stoixeio us ON u.id_user = us.user_id " +
                                            "JOIN apk.stoixeio s ON us.stoixeio_id = s.idStoixeio " +
                                            "WHERE s.idStoixeio = ?")) {

                                instStmt.setInt(1, id);
                                ResultSet instRs = instStmt.executeQuery();

                                while (instRs.next()) {
                                    String firstname = instRs.getString("first_name");
                                    String lastname = instRs.getString("last_name");
                                    String emailcp = instRs.getString("email");
                                    String telnumber = instRs.getString("telephone");
                                    String addresscp = instRs.getString("address");

                                    String competentpersonsEntry = "Όνομα: " + firstname + "\n" +
                                            "Επώνυμο: " + lastname + "\n" +
                                            "Email: " + emailcp + "\n" +
                                            "Τηλέφωνο: " + telnumber + "\n" +
                                            "Διεύθυνση: " + addresscp;

                                    competentpersons.add(competentpersonsEntry);
                                }
                            }


                            String bibliography = rs.getString("bibliography");

                            EditStoixeio editForm = new EditStoixeio(id_user, name, email);
                            editForm.loadElement(id, name, year, otherName, description, field, selectedFields, locationDescription, location, keyword, foreas, fullDescription, performanceArea, facilities, equipment,
                                    product, historical_data, members, society, community, transmission, existing, future, evidence, competentpersons, bibliography);
                            editForm.setVisible(true);
                        }

                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        setVisible(true);
    }

    private void processEvidenceEntry(String entry) {
        try {
            String[] parts = entry.split(",");
            if (parts.length < 2) {
                JOptionPane.showMessageDialog(null, "Η καταχώριση δεν έχει έγκυρη μορφή.");
                return;
            }

            String part1 = parts[1].trim();

            if (isValidURL(part1)) {
                openURL(part1);
            } else {
                downloadFile(part1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά την επεξεργασία της καταχώρισης: " + ex.getMessage());
        }
    }
    public void downloadFile(String fileName) {

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT file_data, mime_type FROM evidence WHERE file_name = ? AND stoixeio_idStoixeio = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, fileName);
                stmt.setString(2, idStoixeioField.getText());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    byte[] fileData = rs.getBytes("file_data");
                    String mimeType = rs.getString("mime_type");

                    if (fileData != null && mimeType != null && fileName != null) {
                        // Εξαγωγή επέκτασης από το MIME type (π.χ. image/png -> .png)
                        String fileExtension = getFileExtensionFromMimeType(mimeType);
                        if (fileExtension == null) {
                            System.out.println("Αναγνωρίστηκε άγνωστος τύπος MIME.");
                            return;
                        }

                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Επιλέξτε φάκελο για αποθήκευση");

                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                        String defaultFileName = fileName + fileExtension;
                        fileChooser.setSelectedFile(new File(defaultFileName));

                        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

                        int result = fileChooser.showSaveDialog(null);

                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();

                            String filePath = selectedFile.getAbsolutePath();
                            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                                fos.write(fileData);
                                System.out.println("Αρχείο κατεβηκε με επιτυχία: " + filePath);
                            } catch (IOException e) {
                                System.out.println("Σφάλμα κατά την αποθήκευση του αρχείου.");
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("Η αποθήκευση του αρχείου ακυρώθηκε.");
                        }
                    } else {
                        System.out.println("Δεν βρέθηκαν δεδομένα ή MIME type για το αρχείο.");
                    }
                } else {
                    System.out.println("Δεν βρέθηκε το αρχείο με το όνομα: " + fileName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την προσθήκη στη βάση δεδομένων: " + ex.getMessage());
        }
    }
    // Μέθοδος για το άνοιγμα του URL
    private void openURL(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Σφάλμα κατά το άνοιγμα του URL: " + ex.getMessage());
        }
    }
    private boolean isValidURL(String url) {
        try {
            new java.net.URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private String getFileExtensionFromMimeType(String mimeType) {
        if (mimeType == null) {
            return null;
        }
        switch (mimeType) {
            // Εικόνες
            case "image/png": return ".png";
            case "image/jpeg": return ".jpg";
            case "image/gif": return ".gif";

            // Έγγραφα
            case "application/pdf": return ".pdf";
            case "text/plain": return ".txt";
            case "application/zip": return ".zip";
            case "application/msword": return ".doc";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document": return ".docx";

            // Βίντεο
            case "video/mp4": return ".mp4";
            case "video/x-msvideo": return ".avi";
            case "video/webm": return ".webm";
            case "video/mpeg": return ".mpeg";
            case "video/quicktime": return ".mov";

            // Ήχος
            case "audio/mpeg": return ".mp3";
            case "audio/wav": return ".wav";
            case "audio/aac": return ".aac";
            case "audio/ogg": return ".ogg";
            case "audio/flac": return ".flac";

            // Χάρτες
            case "application/vnd.google-earth.kml+xml": return ".kml"; // Google Earth KML
            case "application/vnd.google-earth.kmz": return ".kmz";   // Συμπιεσμένο KML
            case "application/gpx+xml": return ".gpx";               // GPS Exchange Format
            case "application/json": return ".geojson";              // GeoJSON
            case "image/tiff": return ".tif";                        // GeoTIFF

            // Άγνωστος τύπος MIME
            default:
                System.out.println("Άγνωστος τύπος MIME: " + mimeType);
                return null;  // Επιστρέφει null αν δεν αναγνωρίζει τον τύπο MIME
        }
    }

    private Set<String> loadRelatedData(Connection conn, String query, int id) throws SQLException {
        Set<String> data = new HashSet<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(rs.getString(1));
            }
        }
        return data;
    }

    private String loadSingleValue(Connection conn, String query, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString(1) : "";
        }
    }

    // Μέθοδος για τη φόρτωση των δεδομένων στα πεδία
    public void loadElementData(int id, String name, int year, Set<String> otherName, String description, Set<String> field, String locationDescription, Set<String> location, Set<String> keyword, Set<String> foreas, String fullDescription, Set<String> performanceArea,
                                Set<String> facilities, Set<String> equipment, Set<String> product, String historical_data, String members, String society, String community, String transmission,
                                String existing, String future, Set<String> evidence, Set<String> competentpersons, String bibliography) {
        idStoixeioField.setText(String.valueOf(id));
        nameField.setText(name);
        yearField.setText(String.valueOf(year));
        otherNameField.setText(String.join("\n", otherName));
        descriptionArea.setText(description);
        fieldField.setText(String.join("\n\n", field));
        locationDescriptionArea.setText(locationDescription);
        locationField.setText(String.join("\n\n", location));
        keywordField.setText(String.join("\n", keyword));
        foreasField.setText(String.join("\n\n", foreas));
        full_descriptionArea.setText(fullDescription);
        performanceAreaField.setText(String.join("\n\n", performanceArea));
        facilitiesField.setText(String.join("\n\n", facilities));
        equipmentField.setText(String.join("\n\n", equipment));
        productField.setText(String.join("\n\n", product));
        historical_dataField.setText(historical_data);
        membersField.setText(members);
        societyField.setText(society);
        communityField.setText(community);
        transmissionField.setText(transmission);
        existingField.setText(existing);
        futureField.setText(future);
        evidenceField.setText(String.join("\n", evidence));
        competentpersonsField.setText(String.join("\n\n", competentpersons));
        bibliographyField.setText(bibliography);

    }
}


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Login extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    // Ρυθμίσεις για σύνδεση με τη βάση δεδομένων
    private static final String DB_URL = "jdbc:mysql://localhost:3306/apk";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "apk2024";

    public Login() {
        setTitle("User Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        emailField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        statusLabel = new JLabel();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Έλεγχος των διαπιστευτηρίων με τη βάση
                int id_user = authenticate(email, password);
                if (id_user != -1) {
                    statusLabel.setText("Login successful!");
                    displayUserInfo(id_user, email);
                } else {
                    statusLabel.setText("Invalid username or password.");
                }
            }
        });

        add(panel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private int authenticate(String email, String password) {
        // Σύνδεση με τη βάση δεδομένων και έλεγχος διαπιστευτηρίων
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int user_Id =  rs.getInt("id_user");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String fullName = firstName + " " + lastName + ", " + email;
                // Αποθήκευση στη συνεδρία της εφαρμογής
                UserSession.setUserInfo(user_Id, fullName);

                // Ενημέρωση της βάσης
                try (PreparedStatement updateSessionStmt = conn.prepareStatement("SET @connected_user = ?")) {
                    updateSessionStmt.setString(1, fullName);
                    updateSessionStmt.executeUpdate();
                }

                return user_Id;
            } // Αν υπάρχει εγγραφή, τα διαπιστευτήρια είναι σωστά

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database connection error.");
        }
        return -1;
    }

    private void displayUserInfo(int id_user, String email) {
        // Νέο παράθυρο για τα στοιχεία του χρήστη
        JFrame userInfoFrame = new JFrame("User");
        userInfoFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        userInfoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInfoFrame.setLocationRelativeTo(null);

        // Δημιουργία του κεντρικού panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // Αναζήτηση στοιχείων του χρήστη από τη βάση
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement( "SELECT id_user, first_name, last_name, email, password, address, telephone FROM user  WHERE id_user = ?")){


            stmt.setInt(1, id_user);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Ανάκτηση δεδομένων χρήστη
                gbc.gridx = 0;
                gbc.gridy = 0;
                panel.add(new JLabel("User ID:"), gbc);
                gbc.gridx = 1;
                panel.add(new JLabel(String.valueOf(rs.getInt("id_user"))), gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Όνομα:"), gbc);
                gbc.gridx = 1;
                panel.add(new JLabel(rs.getString("first_name")), gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Επώνυμο:"), gbc);
                gbc.gridx = 1;
                panel.add(new JLabel(rs.getString("last_name")), gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Email:"), gbc);
                gbc.gridx = 1;
                panel.add(new JLabel(rs.getString("email")), gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Διεύθυνση:"), gbc);
                gbc.gridx = 1;
                panel.add(new JLabel(rs.getString("address")), gbc);

                gbc.gridx = 0;
                gbc.gridy++;
                panel.add(new JLabel("Τηλέφωνο:"), gbc);
                gbc.gridx = 1;
                panel.add(new JLabel(rs.getString("telephone")), gbc);

            }


        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving user data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                ((JLabel) component).setFont(new Font("Arial", Font.PLAIN, 18));
            }
        }

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.add(panel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField searchField = new JTextField(40);
        JButton searchButton = new JButton("Αναζήτηση");
        searchButton.setPreferredSize(new Dimension(120, 35));
        searchButton.setFont(new Font("Arial", Font.PLAIN, 18));
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            if (!searchTerm.isEmpty()) {
                searchElement(searchTerm, id_user, email);
            } else {
                JOptionPane.showMessageDialog(userInfoFrame, "Παρακαλώ εισάγετε το όνομα του στοιχείου.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Προσθήκη της μπάρας αναζήτησης και του κουμπιού στο searchPanel
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Δημιουργία panel για το κουμπί εισαγωγής
        JPanel insertPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //Δημιουργία κουμπιού εισαγωγής στοιχείου ΑΠΚ
        JButton insertButton = new JButton("Εισαγωγή Στοιχείου ΑΠΚ");
        insertButton.setPreferredSize(new Dimension(300, 35));
        insertButton.setFont(new Font("Arial", Font.PLAIN, 18));
        insertButton.addActionListener(e -> {
            // Δημιουργία νέας κλάσης για εισαγωγή στοιχείου ΑΠΚ
            stoixeio stoixeioForm = new stoixeio(id_user);
            stoixeioForm.setVisible(true);
        });

        // Προσθήκη του κουμπιού στο insertPanel
        insertPanel.add(insertButton);

        // Δημιουργία του αριστερού panel
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton viewAllButton = new JButton("Προβολή όλων των στοιχείων ΑΠΚ");
        viewAllButton.setPreferredSize(new Dimension(500, 35));
        viewAllButton.setFont(new Font("Arial", Font.PLAIN, 18));
        viewAllButton.addActionListener(e -> {
            // Κλήση της μεθόδου που ανοίγει τον πίνακα με όλα τα στοιχεία
            showAllElements(id_user, email);
        });

        leftPanel.add(viewAllButton);

        // Top panel για τοποθέτηση των κουμπιών αναζήτησης και εισαγωγής
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(userInfoFrame.getWidth(), 100));
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(insertPanel, BorderLayout.EAST);
        topPanel.add(leftPanel, BorderLayout.WEST);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(panel), BorderLayout.CENTER);

        userInfoFrame.add(mainPanel);
        userInfoFrame.setVisible(true);
    }

    // Μέθοδος αναζήτησης στοιχείου στη βάση
    private void searchElement(String searchTerm, int id_user, String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM stoixeio WHERE name LIKE ?")) {

            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("idStoixeio");
                String name = rs.getString("name");
                int year = rs.getInt("year");

                Set<String> otherNames = loadRelatedData(conn, "SELECT name FROM other_names WHERE stoixeio_idStoixeio = ?", id);

                String description = rs.getString("description");

                Set<String> field = new HashSet<>();
                try (PreparedStatement fieldStmt = conn.prepareStatement(
                        "SELECT f.field_apk, sf.justification " +
                                "FROM stoixeio_fields_apk sf " +
                                "JOIN fields_apk f ON sf.fields_apk_idfields_apk = f.idfields_apk " +
                                "WHERE sf.stoixeio_idStoixeio = ?")) {

                    fieldStmt.setInt(1, id);
                    ResultSet fieldRs = fieldStmt.executeQuery();

                    while (fieldRs.next()) {
                        String fieldApk = fieldRs.getString("field_apk");
                        String justification = fieldRs.getString("justification");

                        // Αν το justification είναι κενό, προσθέτουμε μόνο το field_apk, αλλιώς τα συνδυάζουμε
                        if (justification == null || justification.isEmpty()) {
                            field.add(fieldApk); // Προσθήκη μόνο του field_apk
                        } else {
                            field.add(fieldApk + ": " + justification); // Συνδυασμός field_apk με justification
                        }
                    }
                }


                // Φόρτωση της περιγραφής τοποθεσίας
                String locationDescription = null;

                try (PreparedStatement locationDescStmt = conn.prepareStatement(
                        "SELECT locationDescription FROM location WHERE stoixeio_idStoixeio = ?")) {
                    locationDescStmt.setInt(1, id);  // Βάζουμε το idStoixeio ως παράμετρο
                    ResultSet locationDescRs = locationDescStmt.executeQuery();

                    // Ελέγχουμε αν υπάρχει αποτελέσμα
                    if (locationDescRs.next()) {
                        locationDescription = locationDescRs.getString("locationDescription");  // Αποθηκεύουμε την περιγραφή τοποθεσίας
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
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
                        // Λίστα για αποθήκευση μη κενών πεδίων
                        List<String> fields = new ArrayList<>();

                        // Ελέγχουμε αν το πεδίο decentralized_administration_name έχει τιμή και προσθέτουμε το όνομα
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Set<String> keywords = new HashSet<>();
                try (PreparedStatement keywordsStmt = conn.prepareStatement(
                        "SELECT k.keyword " +
                                "FROM stoixeio_keywords sk " +
                                "JOIN keywords k ON sk.keyword_id_keyword = k.id_keyword " +
                                "WHERE sk.stoixeio_idStoixeio = ?")) {

                    keywordsStmt.setInt(1, id);
                    ResultSet keywordsRs = keywordsStmt.executeQuery();

                    while (keywordsRs.next()) {
                        String keyword = keywordsRs.getString("keyword");
                        if (keyword != null && !keyword.isEmpty()) {
                            keywords.add(keyword);  // Προσθήκη της λέξης-κλειδιού στο σύνολο
                        }
                    }
                }

                Set<String> foreas = new HashSet<>();
                try (PreparedStatement instStmt = conn.prepareStatement(
                        "SELECT f.namef, f.descriptionf, f.address, f.city, f.telephone, f.email, f.TK, f.website " +
                                "FROM stoixeio_foreas sf " +
                                "JOIN foreas f ON sf.foreas_id = f.idforeas " +
                                "WHERE sf.stoixeio_id = ?")) {

                    instStmt.setInt(1, id);  // Ορισμός του stoixeio_idStoixeio
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

                        // Δημιουργία της καταχώρησης του φορέα
                        String foreasDetails = "Όνομα: " + namef + "\n" +
                                "Περιγραφή: " + descriptionf + "\n" +
                                "Email: " + emailf + "\n" +
                                "Διεύθυνση: " + address + "\n" +
                                "Πόλη: " + city + "\n" +
                                "Τ.Κ.: " + TK + "\n" +
                                "Τηλέφωνο: " + telephone + "\n" +
                                "Ιστότοπος: " + website;

                        foreas.add(foreasDetails);  // Προσθήκη στο σύνολο
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
                try (PreparedStatement evidenceStmt = conn.prepareStatement("SELECT category, file_name, date_of_entry, who_uploaded_it FROM evidence WHERE stoixeio_idStoixeio = ?")) {
                    evidenceStmt.setInt(1, id);
                    ResultSet evidenceRs = evidenceStmt.executeQuery();
                    while (evidenceRs.next()) {
                        String category = evidenceRs.getString("category");
                        String fileName = evidenceRs.getString("file_name");
                        String dateOfEntry = evidenceRs.getString("date_of_entry");
                        String whoUploadedIt = evidenceRs.getString("who_uploaded_it");

                        // Συνένωση των πεδίων σε μία συμβολοσειρά με κόμμα ανάμεσα στα πεδία
                        String evidenceEntry = category + ", " + fileName + ", " + dateOfEntry + ", " + whoUploadedIt;
                        evidence.add(evidenceEntry);
                    }
                }

                Set<String> competentpersons = new HashSet<>();
                try (PreparedStatement instStmt = conn.prepareStatement(
                        "SELECT u.first_name, u.last_name, u.email, u.telephone " +
                                "FROM apk.user u " +
                                "JOIN apk.user_stoixeio us ON u.id_user = us.user_id " +
                                "JOIN apk.stoixeio s ON us.stoixeio_id = s.idStoixeio " +
                                "WHERE s.idStoixeio = ?")) {
                    instStmt.setInt(1, id);  // Ορίζουμε το id του στοιχείου ΑΠΚ
                    ResultSet instRs = instStmt.executeQuery();
                    while (instRs.next()) {
                        String firstname = instRs.getString("first_name");
                        String lastname = instRs.getString("last_name");
                        String emailcp = instRs.getString("email");
                        String telnumber = instRs.getString("telephone");


                        String competentpersonsEntry = "Όνομα: " + firstname + "\n" +
                                "Επώνυμο: " + lastname + "\n" +
                                "Email: " + emailcp + "\n" +
                                "Τηλέφωνο: " + telnumber;

                        competentpersons.add(competentpersonsEntry);
                    }
                }

                String bibliography = rs.getString("bibliography");


                ViewStoixeio viewForm = new ViewStoixeio(name, id_user, email);
                viewForm.loadElementData(id, name, year, otherNames, description, field, locationDescription, location, keywords, foreas, fullDescription, performanceArea, facilities, equipment,
                        product, historical_data, members, society, community, transmission, existing, future, evidence, competentpersons, bibliography);
                viewForm.setVisible(true);
            } else {
                // Εάν δεν βρέθηκαν αποτελέσματα, εμφανίζουμε μήνυμα
                JOptionPane.showMessageDialog(this, "Δεν βρέθηκαν αποτελέσματα.", "Αναζήτηση", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Σφάλμα κατά την αναζήτηση στοιχείου.", "Σφάλμα", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Μέθοδος για φόρτωση δεδομένων από πίνακα που σχετίζεται με το στοιχείο
    private Set<String> loadRelatedData(Connection conn, String query, int id) throws SQLException {
        Set<String> results = new HashSet<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(rs.getString(1));
            }
        }
        return results;
    }

    private String loadSingleValue(Connection conn, String query, int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString(1) : "";
        }
    }

    private void showAllElements(int id_user, String email) {
        // Δημιουργία ενός νέου παραθύρου για την προβολή όλων των στοιχείων ΑΠΚ
        JFrame allElementsFrame = new JFrame("Όλα τα Στοιχεία ΑΠΚ");
        allElementsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        allElementsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        allElementsFrame.setLocationRelativeTo(null);

        // Δημιουργία πίνακα για την εμφάνιση των στοιχείων
        String[] columnNames = {"Όνομα", "Έτος Εισαγωγής"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT name, year FROM stoixeio");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = { rs.getString("name"), rs.getInt("year") };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(allElementsFrame, "Error retrieving data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    String name = tableModel.getValueAt(row, 0).toString();
                    showElementDetails(name, id_user, email);
                }
            }
        });

        allElementsFrame.add(new JScrollPane(table));
        allElementsFrame.setVisible(true);
    }

    private void showElementDetails(String name, int id_user, String email) {
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
                try (PreparedStatement fieldStmt = conn.prepareStatement(
                        "SELECT f.field_apk, sf.justification " +
                                "FROM stoixeio_fields_apk sf " +
                                "JOIN fields_apk f ON sf.fields_apk_idfields_apk = f.idfields_apk " +
                                "WHERE sf.stoixeio_idStoixeio = ?")) {

                    fieldStmt.setInt(1, id);
                    ResultSet fieldRs = fieldStmt.executeQuery();

                    while (fieldRs.next()) {
                        String fieldApk = fieldRs.getString("field_apk");
                        String justification = fieldRs.getString("justification");

                        // Αν το justification είναι κενό, προσθέτουμε μόνο το field_apk, αλλιώς τα συνδυάζουμε
                        if (justification == null || justification.isEmpty()) {
                            field.add(fieldApk); // Προσθήκη μόνο του field_apk
                        } else {
                            field.add(fieldApk + ": " + justification); // Συνδυασμός field_apk με justification
                        }
                    }
                }

                // Φόρτωση της περιγραφής τοποθεσίας
                String locationDescription = null;

                try (PreparedStatement locationDescStmt = conn.prepareStatement(
                        "SELECT locationDescription FROM location WHERE stoixeio_idStoixeio = ?")) {
                    locationDescStmt.setInt(1, id);  // Βάζουμε το idStoixeio ως παράμετρο
                    ResultSet locationDescRs = locationDescStmt.executeQuery();

                    // Ελέγχουμε αν υπάρχει αποτελέσμα
                    if (locationDescRs.next()) {
                        locationDescription = locationDescRs.getString("locationDescription");  // Αποθηκεύουμε την περιγραφή τοποθεσίας
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
                        // Λίστα για αποθήκευση μη κενών πεδίων
                        List<String> fields = new ArrayList<>();

                        // Ελέγχουμε αν το πεδίο decentralized_administration_name έχει τιμή και προσθέτουμε το όνομα
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                Set<String> keyword = new HashSet<>();
                try (PreparedStatement keywordsStmt = conn.prepareStatement(
                        "SELECT k.keyword " +
                                "FROM stoixeio_keywords sk " +
                                "JOIN keywords k ON sk.keyword_id_keyword = k.id_keyword " +
                                "WHERE sk.stoixeio_idStoixeio = ?")) {

                    keywordsStmt.setInt(1, id);  // Ορισμός του stoixeio_idStoixeio
                    ResultSet keywordsRs = keywordsStmt.executeQuery();

                    while (keywordsRs.next()) {
                        String keywords = keywordsRs.getString("keyword");
                        if (keywords != null && !keywords.isEmpty()) {
                            keyword.add(keywords);  // Προσθήκη της λέξης-κλειδιού στο σύνολο
                        }
                    }
                }

                Set<String> foreas = new HashSet<>();
                try (PreparedStatement instStmt = conn.prepareStatement(
                        "SELECT f.namef, f.descriptionf, f.address, f.city, f.telephone, f.email, f.TK, f.website " +
                                "FROM stoixeio_foreas sf " +
                                "JOIN foreas f ON sf.foreas_id = f.idforeas " +
                                "WHERE sf.stoixeio_id = ?")) {

                    instStmt.setInt(1, id);  // Ορισμός του stoixeio_idStoixeio
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

                        // Δημιουργία της καταχώρησης του φορέα
                        String foreasDetails = "Όνομα: " + namef + "\n" +
                                "Περιγραφή: " + descriptionf + "\n" +
                                "Email: " + emailf + "\n" +
                                "Διεύθυνση: " + address + "\n" +
                                "Πόλη: " + city + "\n" +
                                "Τ.Κ.: " + TK + "\n" +
                                "Τηλέφωνο: " + telephone + "\n" +
                                "Ιστότοπος: " + website;

                        foreas.add(foreasDetails);  // Προσθήκη στο σύνολο
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
                try (PreparedStatement evidenceStmt = conn.prepareStatement("SELECT category, file_name, date_of_entry, who_uploaded_it FROM evidence WHERE stoixeio_idStoixeio = ?")) {
                    evidenceStmt.setInt(1, id);
                    ResultSet evidenceRs = evidenceStmt.executeQuery();
                    while (evidenceRs.next()) {
                        String category = evidenceRs.getString("category");
                        String fileName = evidenceRs.getString("file_name");
                        String dateOfEntry = evidenceRs.getString("date_of_entry");
                        String whoUploadedIt = evidenceRs.getString("who_uploaded_it");

                        // Συνένωση των πεδίων σε μία συμβολοσειρά με κόμμα ανάμεσα στα πεδία
                        String evidenceEntry = category + ", " + fileName + ", " + dateOfEntry + ", " + whoUploadedIt;
                        evidence.add(evidenceEntry);
                    }
                }

                Set<String> competentpersons = new HashSet<>();
                try (PreparedStatement instStmt = conn.prepareStatement(
                        "SELECT u.first_name, u.last_name, u.email, u.telephone " +
                                "FROM apk.user u " +
                                "JOIN apk.user_stoixeio us ON u.id_user = us.user_id " +
                                "JOIN apk.stoixeio s ON us.stoixeio_id = s.idStoixeio " +
                                "WHERE s.idStoixeio = ?")) {
                    instStmt.setInt(1, id);  // Ορίζουμε το id του στοιχείου ΑΠΚ
                    ResultSet instRs = instStmt.executeQuery();
                    while (instRs.next()) {
                        String firstname = instRs.getString("first_name");
                        String lastname = instRs.getString("last_name");
                        String emailcp = instRs.getString("email");
                        String telnumber = instRs.getString("telephone");


                        String competentpersonsEntry = "Όνομα: " + firstname + "\n" +
                                "Επώνυμο: " + lastname + "\n" +
                                "Email: " + emailcp + "\n" +
                                "Τηλέφωνο: " + telnumber;

                        competentpersons.add(competentpersonsEntry);
                    }
                }

                String bibliography = rs.getString("bibliography");

                ViewStoixeio viewForm = new ViewStoixeio(name, id_user, email);
                viewForm.loadElementData(id, name, year, otherName, description, field, locationDescription, location, keyword, foreas, fullDescription, performanceArea, facilities, equipment,
                        product, historical_data, members, society, community, transmission, existing, future, evidence, competentpersons, bibliography);
                viewForm.setVisible(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class UserSession {
        private static String fullName;
        private static int user_Id;

        // Ρύθμιση πληροφοριών χρήστη
        public static void setUserInfo(int id_user, String fullName) {
            UserSession.user_Id = id_user;
            UserSession.fullName = fullName; // Αποθήκευση του fullName
        }

        public static String getFullName() {
            return fullName;
        }

        public static int getUserId() {
            return user_Id;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}

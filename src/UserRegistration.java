import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Base64;

public class UserRegistration extends JFrame {
    private JTextField firstNameField, lastNameField, emailField, addressField, telephoneField;
    private JPasswordField passwordField;
    private JButton registerButton;


    // Ρυθμίσεις για σύνδεση με τη βάση δεδομένων
    private static final String DB_URL = "jdbc:mysql://localhost:3306/apk";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "apk2024";


    public UserRegistration() {
        setTitle("User Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 5, 5));

        // Labels and Text Fields
        add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Telephone:"));
        telephoneField = new JTextField();
        add(telephoneField);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        add(registerButton);

        setVisible(true);
    }

    // Action listener κουμπί εγγραφής
    private class RegisterButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String address = addressField.getText().trim();
            String telephone = telephoneField.getText().trim();

            // Validation checks
            if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() ||
                    email.isEmpty() || telephone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Όλα τα πεδία είναι υποχρεωτικά.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Το email δεν είναι έγκυρο", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidTelephone(telephone)) {
                JOptionPane.showMessageDialog(null, "Ο αριθμός τηλεφώνου δεν είναι έγκυρος", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(null, "Ο κωδικός πρέπει να έχει τουλάχιστον 8 χαρακτήρες, εκ των οποίων τουλάχιστον ένας κεφαλαίος χαρακτήρας, ένας πεζός χαρακτήρας, ένας αριθμός και ένας ειδικός χαρακτήρας.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Έλεγχος αν το email υπάρχει ήδη στη βάση δεδομένων
            if (isEmailTaken(email)) {
                JOptionPane.showMessageDialog(null, "Αυτό το email είναι ήδη καταχωρημένο.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 CallableStatement stmt = conn.prepareCall("{CALL InsertUser(?, ?, ?, ?, ?, ?)}")) {

                String salt = PasswordUtils.generateSalt();
                String hashedPassword = PasswordUtils.hashPassword(password, salt);

                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, hashedPassword);
                stmt.setString(4, email);
                stmt.setString(5, address);
                stmt.setString(6, telephone);

                stmt.execute();  // Εκτέλεση της stored procedure


                JOptionPane.showMessageDialog(null, "Η εγγραφή ήταν επιτυχής!");

                // Κλείσιμο του παραθύρου εγγραφής
                dispose();

                // Άνοιγμα του παραθύρου Login
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new Login().setVisible(true);
                    }
                });

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την εισαγωγή στη βάση δεδομένων: " + ex.getMessage());
            }
        }

        private boolean isValidEmail(String email) {
            return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        }

        private boolean isValidTelephone(String telephone) {
            return telephone.matches("^((\\+30)?[2-9][0-9]{9}|[2-9][0-9]{9})$");
        }

        private boolean isValidPassword(String password) {
            return password.length() >= 8 &&
                    password.matches(".*[A-Z].*") &&    // τουλάχιστον ένας κεφαλαίος χαρακτήρας
                    password.matches(".*[a-z].*") &&    // τουλάχιστον ένας πεζός χαρακτήρας
                    password.matches(".*\\d.*") &&      // τουλάχιστον ένας αριθμός
                    password.matches(".*[!@#$%^&*()].*"); // τουλάχιστον ένας ειδικός χαρακτήρας
        }

        private boolean isEmailTaken(String email) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "SELECT COUNT(*) FROM user WHERE email = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, email);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        return true;  // Το email υπάρχει ήδη
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την αναζήτηση email στη βάση δεδομένων: " + ex.getMessage());
            }
            return false;  // Το email δεν υπάρχει
        }
    }

    public static void main(String[] args) {
        new UserRegistration();
    }
}

class PasswordUtils {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hashedPassword = keyFactory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Σφάλμα κατακερματισμού κωδικού: " + e.getMessage(), e);
        }
    }
}

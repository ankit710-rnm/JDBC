import java.sql.*;
import java.util.*;

// ======================================================================
//   JAVA APPLICATIONS USING JDBC, CRUD OPERATIONS, AND MVC ARCHITECTURE
// ======================================================================

public class Main {

    // Database credentials (update as needed)
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    // ============================================================
    //   MAIN METHOD — Menu for All Three Parts (A, B, and C)
    // ============================================================
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n=== JDBC APPLICATION MENU ===");
                System.out.println("1. Part A: Fetch Data from Employees Table");
                System.out.println("2. Part B: CRUD on Product Table");
                System.out.println("3. Part C: Student Management (MVC Style)");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> fetchEmployees();
                    case 2 -> productCRUD();
                    case 3 -> studentManagement();
                    case 4 -> System.exit(0);
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    //   PART A: Connecting to MySQL and Fetching Data
    // ============================================================
    private static void fetchEmployees() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employees")) {

            System.out.println("\n=== Employee Data ===");
            System.out.println("ID | Name | Department | Salary");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("department") + " | " +
                                   rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
    }

    // ============================================================
    //   PART B: CRUD OPERATIONS ON PRODUCT TABLE
    // ============================================================
    private static void productCRUD() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n=== PRODUCT MENU ===");
                System.out.println("1. Add Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Back to Main Menu");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> addProduct(con, sc);
                    case 2 -> viewProducts(con);
                    case 3 -> updateProduct(con, sc);
                    case 4 -> deleteProduct(con, sc);
                    case 5 -> { return; }
                    default -> System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addProduct(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter price: ");
        double price = sc.nextDouble();
        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        String sql = "INSERT INTO product (name, price, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            ps.executeUpdate();
            System.out.println("✅ Product added successfully!");
        }
    }

    private static void viewProducts(Connection con) throws SQLException {
        String sql = "SELECT * FROM product";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nID | Name | Price | Quantity");
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " | " +
                                   rs.getString(2) + " | " +
                                   rs.getDouble(3) + " | " +
                                   rs.getInt(4));
            }
        }
    }

    private static void updateProduct(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter product ID to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new price: ");
        double price = sc.nextDouble();

        String sql = "UPDATE product SET price=? WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, price);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Product updated successfully!");
            else
                System.out.println("⚠ Product not found!");
        }
    }

    private static void deleteProduct(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter product ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM product WHERE id=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Product deleted successfully!");
            else
                System.out.println("⚠ Product not found!");
        }
    }

    // ============================================================
    //   PART C: STUDENT MANAGEMENT (SIMPLIFIED MVC)
    // ============================================================
    private static void studentManagement() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n=== STUDENT MANAGEMENT ===");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Back to Main Menu");
                System.out.print("Enter your choice: ");
                int ch = sc.nextInt();

                switch (ch) {
                    case 1 -> addStudent(con, sc);
                    case 2 -> viewStudents(con);
                    case 3 -> { return; }
                    default -> System.out.println("Invalid choice!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addStudent(Connection con, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Course: ");
        String course = sc.next();
        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        String sql = "INSERT INTO student (id, name, course, marks) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.setDouble(4, marks);
            ps.executeUpdate();
            System.out.println("✅ Student added successfully!");
        }
    }

    private static void viewStudents(Connection con) throws SQLException {
        String sql = "SELECT * FROM student";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nID | Name | Course | Marks");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getString("course") + " | " +
                                   rs.getDouble("marks"));
            }
        }
    }
}

import java.sql.*;
import java.util.Scanner;

public class LibraryManager {
    static Scanner sc = new Scanner(System.in);

    public static void addBook() throws SQLException {
        Connection conn = DBConnection.getConnection();
        System.out.print("Title: "); String title = sc.nextLine();
        System.out.print("Author: "); String author = sc.nextLine();
        System.out.print("Publisher: "); String publisher = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Books (title, author, publisher) VALUES (?, ?, ?)");
        ps.setString(1, title);
        ps.setString(2, author);
        ps.setString(3, publisher);
        ps.executeUpdate();
        System.out.println("Book added successfully!");
        conn.close();
    }

    public static void registerMember() throws SQLException {
        Connection conn = DBConnection.getConnection();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Phone: "); String phone = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Members (name, email, phone) VALUES (?, ?, ?)");
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, phone);
        ps.executeUpdate();
        System.out.println("Member registered successfully!");
        conn.close();
    }

    public static void issueBook() throws SQLException {
        Connection conn = DBConnection.getConnection();
        System.out.print("Book ID: "); int bookId = sc.nextInt();
        System.out.print("Member ID: "); int memberId = sc.nextInt();
        sc.nextLine();  // consume newline

        PreparedStatement ps1 = conn.prepareStatement("SELECT available FROM Books WHERE book_id=?");
        ps1.setInt(1, bookId);
        ResultSet rs = ps1.executeQuery();
        if (rs.next() && rs.getBoolean("available")) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO Transactions (book_id, member_id, issue_date) VALUES (?, ?, CURDATE())");
            ps.setInt(1, bookId);
            ps.setInt(2, memberId);
            ps.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement("UPDATE Books SET available = FALSE WHERE book_id=?");
            ps2.setInt(1, bookId);
            ps2.executeUpdate();

            System.out.println("Book issued successfully!");
        } else {
            System.out.println("Book is not available.");
        }
        conn.close();
    }

    public static void returnBook() throws SQLException {
        Connection conn = DBConnection.getConnection();
        System.out.print("Book ID to return: "); int bookId = sc.nextInt();
        sc.nextLine();

        PreparedStatement ps = conn.prepareStatement("UPDATE Transactions SET return_date = CURDATE() WHERE book_id = ? AND return_date IS NULL");
        ps.setInt(1, bookId);
        ps.executeUpdate();

        PreparedStatement ps2 = conn.prepareStatement("UPDATE Books SET available = TRUE WHERE book_id = ?");
        ps2.setInt(1, bookId);
        ps2.executeUpdate();

        System.out.println("Book returned successfully!");
        conn.close();
    }
}


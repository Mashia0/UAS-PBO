import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Interface untuk perawatan tanaman
interface PerawatanTanaman {
    void pantauTanaman(); // Memantau kondisi tanaman
    void rawatTanaman();  // Merawat tanaman sesuai kebutuhan
}
public class SistemHidroponik {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hidroponik_db";
    private static final String PENGGUNA = "root";
    private static final String KATA_SANDI = "";

    // Menambahkan tanaman baru ke database
    private static void tambahTanaman(int id, String nama, int levelAir, int levelNutrisi) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, PENGGUNA, KATA_SANDI)) {
            String query = "INSERT INTO tanaman (id, nama, level_air, level_nutrisi) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setString(2, nama);
            stmt.setInt(3, levelAir);
            stmt.setInt(4, levelNutrisi);
            stmt.executeUpdate();
            System.out.println("Tanaman berhasil ditambahkan.");
        } catch (SQLException e) {
            System.err.println("Kesalahan saat menambahkan tanaman: " + e.getMessage());
        }
    }

    

    // Memuat data dari database ke list tanaman
    private static void muatDataDariDatabase(List<TanamanHidroponik> tanamanList) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, PENGGUNA, KATA_SANDI)) {
            String query = "SELECT * FROM tanaman";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            tanamanList.clear(); // Membersihkan data lama
            while (rs.next()) {
                // Syarat 7: Menggunakan collection framework untuk menyimpan data dari database
                tanamanList.add(new TanamanHidroponik(
                        rs.getString("nama"),
                        rs.getInt("level_air"),
                        rs.getInt("level_nutrisi"),
                        70, // levelAirIdeal default
                        80  // levelNutrisiIdeal default
                ));
            }
        } catch (SQLException e) {
            System.err.println("Kesalahan saat memuat data dari database: " + e.getMessage());
        }
    }

     // Fungsi untuk memperbarui tanaman di database
     private static void perbaruiTanaman(int id, int levelAir, int levelNutrisi) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, PENGGUNA, KATA_SANDI)) {
            String query = "UPDATE tanaman SET level_air = ?, level_nutrisi = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, levelAir);
            stmt.setInt(2, levelNutrisi);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            System.out.println("Tanaman berhasil diperbarui.");
        } catch (SQLException e) {
            System.err.println("Kesalahan saat memperbarui tanaman: " + e.getMessage());
        }
    }

    // Fungsi untuk menghapus tanaman di database
    private static void hapusTanaman(int id) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, PENGGUNA, KATA_SANDI)) {
            String query = "DELETE FROM tanaman WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Tanaman berhasil dihapus.");
        } catch (SQLException e) {
            System.err.println("Kesalahan saat menghapus tanaman: " + e.getMessage());
        }
    }

    // Menampilkan tanaman di database
    private static void tampilkanTanaman() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, PENGGUNA, KATA_SANDI)) {
            String query = "SELECT * FROM tanaman";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            boolean adaData = false;

            while (rs.next()) {
                // Syarat 8: Mengakses dan membaca data dari database
                adaData = true;
                System.out.println("ID: " + rs.getInt("id") + ", Nama: " + rs.getString("nama") +
                        ", Level Air: " + rs.getInt("level_air") + "%" +
                        ", Level Nutrisi: " + rs.getInt("level_nutrisi") + "%");
            }

            if (!adaData) {
                System.out.println("Tidak ada tanaman dalam database.");
            }
        } catch (SQLException e) {
            System.err.println("Kesalahan saat menampilkan tanaman: " + e.getMessage());
        }
    }

    // Program utama
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<TanamanHidroponik> tanamanList = new ArrayList<>(); // Syarat 7: Menggunakan ArrayList

        muatDataDariDatabase(tanamanList); // Memuat data awal

        int pilihan;
        do {
            System.out.println("\n===== Sistem Hidroponik =====");
            System.out.println("1. Pantau Tanaman");
            System.out.println("2. Rawat Tanaman");
            System.out.println("3. Tampilkan Informasi Tanaman");
            System.out.println("4. Tambah Tanaman Baru (DB)");
            System.out.println("5. Lihat Semua Tanaman (DB)");
            System.out.println("6. Perbarui Tanaman (DB)");
            System.out.println("7. Hapus Tanaman (DB)");
            System.out.println("0. Keluar");
            System.out.print("Masukkan pilihan Anda: ");
            pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    // Syarat 4: Menggunakan perulangan untuk memproses semua tanaman
                    for (TanamanHidroponik tanaman : tanamanList) {
                        tanaman.pantauTanaman();
                    }
                    break;
                case 2:
                    // Melakukan perawatan untuk semua tanaman
                    for (TanamanHidroponik tanaman : tanamanList) {
                        tanaman.rawatTanaman();
                    }
                    // Memuat ulang data setelah perubahan
                    muatDataDariDatabase(tanamanList);
                    break;
                case 3:
                    for (TanamanHidroponik tanaman : tanamanList) {
                        tanaman.tampilkanInformasi();
                    }
                    break;
                case 4:
                    System.out.print("Masukkan ID tanaman: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Masukkan nama tanaman: ");
                    String nama = scanner.nextLine();
                    System.out.print("Masukkan level air: ");
                    int levelAir = scanner.nextInt();
                    System.out.print("Masukkan level nutrisi: ");
                    int levelNutrisi = scanner.nextInt();
                    tambahTanaman(id, nama, levelAir, levelNutrisi); // Syarat 8: Menambah data ke database
                    muatDataDariDatabase(tanamanList); // Memperbarui data lokal
                    break;
                case 5:
                    tampilkanTanaman();
                    break;
                case 6:
                    System.out.print("Masukkan ID tanaman yang ingin diperbarui: ");
                    int idUpdate = scanner.nextInt();
                    System.out.print("Masukkan level air baru: ");
                    int airBaru = scanner.nextInt();
                    System.out.print("Masukkan level nutrisi baru: ");
                    int nutrisiBaru = scanner.nextInt();
                    perbaruiTanaman(idUpdate, airBaru, nutrisiBaru);
                    // Memuat ulang data tanaman sCD    Wetelah pembaruan
                    muatDataDariDatabase(tanamanList);
                    break;
                case 7:
                    System.out.print("Masukkan ID tanaman yang ingin dihapus: ");
                    int idDelete = scanner.nextInt();
                    hapusTanaman(idDelete);
                    // Memuat ulang data tanaman setelah penghapusan
                    muatDataDariDatabase(tanamanList);
                    break;
                case 0:
                    System.out.println("Keluar dari program...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 0);

        scanner.close();
    }
}

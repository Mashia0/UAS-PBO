import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date; // Impor untuk Date



// Subclass TanamanHidroponik yang mewarisi dari Tanaman
class TanamanHidroponik extends Tanaman implements PerawatanTanaman {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hidroponik_db";
    private static final String PENGGUNA = "root";
    private static final String KATA_SANDI = "";
    private int levelAirIdeal;   // Syarat 3: Penambahan atribut di subclass
    private int levelNutrisiIdeal;

    public TanamanHidroponik(String nama, int levelAir, int levelNutrisi, int levelAirIdeal, int levelNutrisiIdeal) {
        super(nama, levelAir, levelNutrisi); // Syarat 3: Pemanggilan konstruktor superclass
        this.levelAirIdeal = levelAirIdeal;
        this.levelNutrisiIdeal = levelNutrisiIdeal;
    }

    @Override
    public void pantauTanaman() { 
        // Syarat 4: Percabangan untuk memantau kondisi tanaman
        System.out.println("\nMemantau tanaman: " + nama);
        if (levelAir < levelAirIdeal) {
            System.out.println("Peringatan: Level air di bawah ideal! Saat ini: " + levelAir + "%, Ideal: " + levelAirIdeal + "%");
        } else {
            System.out.println("Level air optimal.");
        }
        if (levelNutrisi < levelNutrisiIdeal) {
            System.out.println("Peringatan: Level nutrisi di bawah ideal! Saat ini: " + levelNutrisi + "%, Ideal: " + levelNutrisiIdeal + "%");
        } else {
            System.out.println("Level nutrisi optimal.");
        }
    }

    @Override
    public void rawatTanaman() {
    System.out.println("\nMerawat tanaman: " + nama);
    this.levelAir = Math.min(100, levelAir + 10);
    this.levelNutrisi = Math.min(100, levelNutrisi + 15);
    this.terakhirDiperiksa = new Date(); // Memperbarui waktu terakhir diperiksa
    System.out.println("Level air dan nutrisi telah disesuaikan.");

    // Memperbarui data di database
    try (Connection conn = DriverManager.getConnection(JDBC_URL, PENGGUNA, KATA_SANDI)){
        String query = "UPDATE tanaman SET level_air = ?, level_nutrisi = ?, nama = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, this.levelAir);
        stmt.setInt(2, this.levelNutrisi);
        stmt.setString(3, this.nama);

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Data tanaman berhasil diperbarui di database.");
        } else {
            System.out.println("Tanaman dengan nama '" + nama + "' tidak ditemukan di database.");
        }
    } catch (SQLException e) {
        System.err.println("Kesalahan saat memperbarui data di database: " + e.getMessage());
    }
    }
}

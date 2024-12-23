import java.text.SimpleDateFormat;
import java.util.Date; // Mengimpor hanya pustaka yang dibutuhkan

// Superclass Tanaman
class Tanaman {
    protected String nama; // Syarat 3: Inheritance - Atribut diwarisi oleh subclass
    protected int levelAir;
    protected int levelNutrisi;
    protected Date terakhirDiperiksa;

    // Konstruktor superclass
    public Tanaman(String nama, int levelAir, int levelNutrisi) {
        this.nama = nama; // Syarat 3: Konstruktor superclass dipanggil oleh subclass
        this.levelAir = levelAir;
        this.levelNutrisi = levelNutrisi;
        this.terakhirDiperiksa = new Date(); // Syarat 5: Manipulasi Date
    }

    // Menampilkan informasi tanaman
    public void tampilkanInformasi() { 
        // Syarat 5: Format tanggal menggunakan SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Nama Tanaman: " + nama);
        System.out.println("Level Air: " + levelAir + "%");
        System.out.println("Level Nutrisi: " + levelNutrisi + "%");
        System.out.println("Terakhir Diperiksa: " + sdf.format(terakhirDiperiksa));
    }
}

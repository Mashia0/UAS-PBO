import java.text.SimpleDateFormat;
import java.util.Date; // Mengimpor hanya pustaka yang dibutuhkan

class Tanaman {
    protected String nama;
    protected int levelAir;
    protected int levelNutrisi;
    protected Date terakhirDiperiksa;

    // Konstruktor
    public Tanaman(String nama, int levelAir, int levelNutrisi) {
        this.nama = nama;
        this.levelAir = levelAir;
        this.levelNutrisi = levelNutrisi;
        this.terakhirDiperiksa = new Date(); // Atur waktu saat ini
    }

    // Metode untuk menampilkan informasi tanaman
    public void tampilkanInformasi() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Nama Tanaman: " + nama);
        System.out.println("Level Air: " + levelAir + "%");
        System.out.println("Level Nutrisi: " + levelNutrisi + "%");
        System.out.println("Terakhir Diperiksa: " + sdf.format(terakhirDiperiksa));
    }
}

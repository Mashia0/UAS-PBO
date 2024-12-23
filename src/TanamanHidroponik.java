import java.util.Date; // Impor untuk Date

class TanamanHidroponik extends Tanaman implements PerawatanTanaman {
    private int levelAirIdeal;
    private int levelNutrisiIdeal;

    public TanamanHidroponik(String nama, int levelAir, int levelNutrisi, int levelAirIdeal, int levelNutrisiIdeal) {
        super(nama, levelAir, levelNutrisi);
        this.levelAirIdeal = levelAirIdeal;
        this.levelNutrisiIdeal = levelNutrisiIdeal;
    }

    @Override
    public void pantauTanaman() {
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
        this.terakhirDiperiksa = new Date(); // Menggunakan java.util.Date
        System.out.println("Level air dan nutrisi telah disesuaikan.");
    }
}

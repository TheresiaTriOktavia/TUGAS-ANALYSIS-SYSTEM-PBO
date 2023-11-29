package tugasanalysis;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

class AnggotaPerpustakaan {
    final String nama;
    private final String alamat;
    private final String nomorTelepon;
    private final String email;
    List<TransaksiPeminjaman> riwayatPeminjaman;

    public AnggotaPerpustakaan(String nama, String alamat, String nomorTelepon, String email) {
        this.nama = nama;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.riwayatPeminjaman = new ArrayList<>();
    }

    public void tambahRiwayatPeminjaman(TransaksiPeminjaman transaksi) {
        riwayatPeminjaman.add(transaksi);
    }

    @Override
    public String toString() {
        return "ID Anggota: " + nama +
               "\nAlamat: " + alamat +
               "\nNomor Telepon: " + nomorTelepon +
               "\nEmail: " + email;
    }
}

class Buku {
    String judul;
    private String pengarang;
    private int nomorISBN;
    String statusKetersediaan;

    public Buku(String judul, String pengarang, int nomorISBN, String statusKetersediaan) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.nomorISBN = nomorISBN;
        this.statusKetersediaan = statusKetersediaan;
    }

    public String toString() {
        return "Judul: " + judul +
               "\nPengarang: " + pengarang +
               "\nNomor ISBN: " + nomorISBN +
               "\nStatus Ketersediaan: " + statusKetersediaan;
    }
}

class TabelBukuTersedia {
    public List<Buku> daftarBukuTersedia;

    public TabelBukuTersedia() {
        this.daftarBukuTersedia = new ArrayList<>();
    }

    public void tambahBuku(Buku buku) {
        daftarBukuTersedia.add(buku);
    }

    public void tampilkanTabel() {
        StringBuilder tabelInfo = new StringBuilder("Tabel Buku Tersedia:\n");
        for (Buku buku : daftarBukuTersedia) {
            tabelInfo.append(buku.toString()).append("\n------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, tabelInfo.toString());
    }
}

class TransaksiPeminjaman {
    private static int counter = 1;
    int idTransaksi;
    AnggotaPerpustakaan anggota;
    Buku buku;
    String tanggal;
    String waktu;
    int durasiPeminjaman;
    Object judulBuku;

    public TransaksiPeminjaman(AnggotaPerpustakaan anggota, Buku buku, String tanggal, String waktu, int durasiPeminjaman) {
        this.idTransaksi = counter++;
        this.anggota = anggota;
        this.buku = buku;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.durasiPeminjaman = durasiPeminjaman;
    }

    public int hitungSisaHariPengembalian() {
        LocalDate tanggalPengembalian = LocalDate.parse(tanggal).plusDays(durasiPeminjaman);
        LocalDate sekarang = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(sekarang, tanggalPengembalian);
    }

    @Override
    public String toString() {
        return "ID Transaksi: " + idTransaksi +
               "\nAnggota: " + anggota.nama +
               "\nBuku: " + buku.judul +
               "\nTanggal: " + tanggal +
               "\nWaktu: " + waktu +
               "\nDurasi Peminjaman: " + durasiPeminjaman + " hari";
    }

    void tampilkanNotifikasi() {
        String pesan = null;
        JOptionPane.showMessageDialog(null, "Notifikasi untuk " + anggota.nama + ":\nBuku '" + judulBuku + "': " + pesan);
    }

    void cekWaktuPengembalian() {
         
    }
}
class TransaksiPengembalian {
    private static int counter = 1;
    int idTransaksi;
    AnggotaPerpustakaan anggota;
    Buku buku;
    String tanggal;
    String waktu;

    public TransaksiPengembalian(AnggotaPerpustakaan anggota, Buku buku, String tanggal, String waktu) {
        this.idTransaksi = counter++;
        this.anggota = anggota;
        this.buku = buku;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public int hitungSisaHariPengembalian() {
        LocalDate tanggalPengembalian = LocalDate.parse(tanggal);
        LocalDate sekarang = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(sekarang, tanggalPengembalian);
    }

    @Override
    public String toString() {
        return "ID Transaksi Pengembalian: " + idTransaksi +
                "\nAnggota: " + anggota.nama +
                "\nBuku: " + buku.judul +
                "\nTanggal: " + tanggal +
                "\nWaktu: " + waktu;
    }

}

class Notifikasi {
    private AnggotaPerpustakaan anggota;
    String judulBuku;
    private String namaAdmin;
    private String tanggal;
    private String waktu;
    private String pesan;

    public Notifikasi(AnggotaPerpustakaan anggota, String judulBuku, String namaAdmin, String tanggal, String waktu, String pesan) {
        this.anggota = anggota;
        this.judulBuku = judulBuku;
        this.namaAdmin = namaAdmin;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.pesan = pesan;
    }

    public void tampilkanNotifikasi() {
        JOptionPane.showMessageDialog(null, "Notifikasi untuk " + anggota.nama + ":\nBuku '" + judulBuku + "': " + pesan);
    }
}

class Admin {
    public void tambahBuku(TabelBukuTersedia tabelBukuTersedia, String judul, String pengarang, int nomorISBN, String statusKetersediaan) {
        Buku bukuBaru = new Buku(judul, pengarang, nomorISBN, statusKetersediaan);
        tabelBukuTersedia.tambahBuku(bukuBaru);
        JOptionPane.showMessageDialog(null, "Buku baru berhasil ditambahkan.");
    }
}

public class Tugasanalysis {
    
    private static TransaksiPeminjaman cariTransaksiById(int idTransaksi, List<TransaksiPeminjaman> daftarTransaksi) {
    for (TransaksiPeminjaman transaksi : daftarTransaksi) {
        if (transaksi.idTransaksi == idTransaksi) {
            return transaksi;
        }
    }
    return null;
}
    public static void main(String[] args) {
        List<AnggotaPerpustakaan> daftarAnggota = new ArrayList<>();
        List<TransaksiPeminjaman> daftarTransaksiPeminjaman = new ArrayList<>();
        List<TransaksiPengembalian> daftarTransaksiPengembalian = new ArrayList<>();
        TabelBukuTersedia tabelBukuTersedia = new TabelBukuTersedia();

        Buku buku1 = new Buku("Harry Potter", "J.K. Rowlings", 123456789, "Tersedia");
        Buku buku2 = new Buku("Hujan", "Tere Liye", 987654321, "Tersedia");
        Buku buku3 = new Buku("Sebuah Seni Untuk Bersikap Bodoamat", "Mark Manson", 56789721, "Tersedia");
        Buku buku4 = new Buku("Butterfly", "Abel Khaizure", 1256789023, "Tersedia");
        tabelBukuTersedia.tambahBuku(buku1);
        tabelBukuTersedia.tambahBuku(buku2);
        tabelBukuTersedia.tambahBuku(buku3);
        tabelBukuTersedia.tambahBuku(buku4);

        boolean isAdmin = false;
        boolean isLoggedIn = false;

        while (true) {
            if (!isLoggedIn) {
                String[] options = {"Admin", "Anggota"};
                int masukSebagai = JOptionPane.showOptionDialog(null, "Masuk sebagai:", "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (masukSebagai == 0) {
                    isAdmin = true;
                    isLoggedIn = true;
                    JOptionPane.showMessageDialog(null, "Masuk sebagai Admin.");
                } else if (masukSebagai == 1) {
                    isAdmin = false;
                    isLoggedIn = true;
                    JOptionPane.showMessageDialog(null, "Masuk sebagai Anggota.");
                } else {
                    JOptionPane.showMessageDialog(null, "Pilihan tidak valid. Silakan coba lagi.");
                }
            } else {
                if (isAdmin) {
                    String[] adminOptions = {"Melihat daftar anggota", "Menambah buku ke dalam tabel koleksi", "Memverifikasi peminjaman yang telah diajukan", "Memverifikasi pengembalian buku", "Keluar"};
                    int pilihanAdmin = JOptionPane.showOptionDialog(null, "Menu Admin:", "Admin Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, adminOptions, adminOptions[0]);

                    switch (pilihanAdmin) {
                        case 0:
                            // Melihat daftar anggota
                            StringBuilder anggotaInfo = new StringBuilder("Daftar Anggota:\n");
                            for (AnggotaPerpustakaan anggota : daftarAnggota) {
                                anggotaInfo.append(anggota.toString()).append("\n------------------------------\n");
                            }
                            JOptionPane.showMessageDialog(null, anggotaInfo.toString());
                            break;

                        case 1:
                            // Menambah buku ke dalam tabel koleksi
                            String judulBuku = JOptionPane.showInputDialog("Masukkan judul buku:");
                            String pengarangBuku = JOptionPane.showInputDialog("Masukkan pengarang buku:");
                            int nomorISBNBuku = Integer.parseInt(JOptionPane.showInputDialog("Masukkan nomor ISBN buku:"));
                            String statusKetersediaanBuku = JOptionPane.showInputDialog("Masukkan status ketersediaan buku:");

                            Admin admin = new Admin();
                            admin.tambahBuku(tabelBukuTersedia, judulBuku, pengarangBuku, nomorISBNBuku, statusKetersediaanBuku);
                            break;

                        case 2:
                            // Memverifikasi peminjaman yang telah diajukan
                            StringBuilder verifikasiPeminjamanInfo = new StringBuilder("Daftar Peminjaman yang Menunggu Verifikasi:\n");
                            for (TransaksiPeminjaman transaksi : daftarTransaksiPeminjaman) {
                                verifikasiPeminjamanInfo.append("ID Transaksi: ").append(transaksi.idTransaksi).append("\n");
                                verifikasiPeminjamanInfo.append("Anggota: ").append(transaksi.anggota.nama).append("\n");
                                verifikasiPeminjamanInfo.append("Buku: ").append(transaksi.buku.judul).append("\n");
                                verifikasiPeminjamanInfo.append("Tanggal: ").append(transaksi.tanggal).append("\n");
                                verifikasiPeminjamanInfo.append("Waktu: ").append(transaksi.waktu).append("\n");
                                verifikasiPeminjamanInfo.append("Durasi Peminjaman: ").append(transaksi.durasiPeminjaman).append(" hari\n");
                                verifikasiPeminjamanInfo.append("Status: ").append(transaksi.buku.statusKetersediaan).append("\n");
                                verifikasiPeminjamanInfo.append("------------------------------\n");
                            }
                            JOptionPane.showMessageDialog(null, verifikasiPeminjamanInfo.toString());

                            // Meminta input admin untuk memverifikasi peminjaman
                            int idTransaksiDiverifikasi = Integer.parseInt(JOptionPane.showInputDialog("Masukkan ID Transaksi yang ingin diverifikasi:"));
                            TransaksiPeminjaman transaksiDiverifikasi = cariTransaksiById(idTransaksiDiverifikasi, daftarTransaksiPeminjaman);

                            if (transaksiDiverifikasi != null) {
                                String verifikasiPeminjaman = JOptionPane.showInputDialog("Verifikasi peminjaman?\n1. Setujui\n2. Tolak");

                                switch (verifikasiPeminjaman) {
                                    case "1":
                                        // Admin menyetujui peminjaman
                                        transaksiDiverifikasi.buku.statusKetersediaan = "Dipinjam";
                                        JOptionPane.showMessageDialog(null, "Peminjaman berhasil disetujui. Buku " + transaksiDiverifikasi.buku.judul + " telah dipinjam.");
                                        break;

                                    case "2":
                                        // Admin menolak peminjaman
                                        JOptionPane.showMessageDialog(null, "Peminjaman ditolak.");
                                        break;

                                    default:
                                        JOptionPane.showMessageDialog(null, "Pilihan tidak valid.");
                                        break;
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "ID Transaksi tidak valid.");
                            }
                            break;

                        case 3:
                            // Memverifikasi Pengembalian Buku
                            JOptionPane.showMessageDialog(null, "Daftar Pengembalian yang Menunggu Verifikasi:");

                            for (TransaksiPeminjaman transaksi : daftarTransaksiPeminjaman) {
                                // Memeriksa apakah transaksi sudah berakhir
                                if (transaksi.hitungSisaHariPengembalian() <= 0) {
                                    String konfirmasiPengembalian = JOptionPane.showInputDialog("Transaksi ID " + transaksi.idTransaksi +
                                            "\nBuku '" + transaksi.buku.judul +
                                            "' oleh " + transaksi.anggota.nama +
                                            "\nSudah dikembalikan? (Y/N)");

                                    if (konfirmasiPengembalian.equalsIgnoreCase("Y")) {
                                        // Verifikasi pengembalian
                                        transaksi.buku.statusKetersediaan = "Tersedia";
                                        JOptionPane.showMessageDialog(null, "Pengembalian buku berhasil diverifikasi.");
                                        TransaksiPengembalian transaksiPengembalian = new TransaksiPengembalian(
                                        transaksi.anggota, transaksi.buku, transaksi.tanggal, transaksi.waktu);
                                        // Tambahkan ke daftar transaksi pengembalian
                                        daftarTransaksiPengembalian.add(transaksiPengembalian);
                                    } else {
                                        // Jika tidak dikembalikan, tampilkan peringatan
                                        JOptionPane.showMessageDialog(null, "Pengembalian buku belum diverifikasi.");
                                    }
                                }
                            }
                            break;
                            
                        case 4:
                          
                            isAdmin = false;
                            isLoggedIn = false;
                            JOptionPane.showMessageDialog(null, "Anda keluar.");
                            break;

                        default:
                            JOptionPane.showMessageDialog(null, "Pilihan tidak valid. Silakan pilih kembali.");
                            break;
                    }
                } else {
                    String[] anggotaOptions = {"Mendaftar sebagai Anggota", "Menampilkan Tabel Buku yang Tersedia", "Tambahkan Transaksi Meminjam Buku", "Mengembalikkan Buku", "Riwayat Peminjaman Buku", "Keluar"};
                    int pilihanAnggota = JOptionPane.showOptionDialog(null, "Menu Anggota:", "Anggota Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, anggotaOptions, anggotaOptions[0]);

                    switch (pilihanAnggota) {
                        case 0:
                            // Mendaftar sebagai Anggota
                            String namaAnggota = JOptionPane.showInputDialog("Masukkan nama anggota:");
                            String alamatAnggota = JOptionPane.showInputDialog("Masukkan alamat anggota:");
                            String nomorTeleponAnggota = JOptionPane.showInputDialog("Masukkan nomor telepon anggota:");
                            String emailAnggota = JOptionPane.showInputDialog("Masukkan email anggota:");

                            AnggotaPerpustakaan anggotaBaru = new AnggotaPerpustakaan(namaAnggota, alamatAnggota, nomorTeleponAnggota, emailAnggota);
                            daftarAnggota.add(anggotaBaru);
                            JOptionPane.showMessageDialog(null, "Anggota baru berhasil ditambahkan.");
                            break;

                        case 1:
                            // Menampilkan Tabel Buku yang Tersedia
                            tabelBukuTersedia.tampilkanTabel();
                            break;

                        case 2:
                            // Tambahkan Transaksi Meminjam Buku
                            String namaAnggotaTransaksi = JOptionPane.showInputDialog("Masukkan nama anggota:");
                            String judulBukuTransaksi = JOptionPane.showInputDialog("Masukkan judul buku yang dipinjam:");
                            String tanggalTransaksi = JOptionPane.showInputDialog("Masukkan tanggal transaksi (yyyy-mm-dd) :");
                            String waktuTransaksi = JOptionPane.showInputDialog("Masukkan waktu transaksi:");
                            int durasiPeminjaman = Integer.parseInt(JOptionPane.showInputDialog("Masukkan durasi peminjaman (dalam hari):"));

                            AnggotaPerpustakaan anggotaTransaksi = null;
                            for (AnggotaPerpustakaan anggota : daftarAnggota) {
                                if (anggota.nama.equals(namaAnggotaTransaksi)) {
                                    anggotaTransaksi = anggota;
                                    break;
                                }
                            }

                            Buku bukuTransaksi = null;
                            for (Buku buku : tabelBukuTersedia.daftarBukuTersedia) {
                                if (buku.judul.equals(judulBukuTransaksi)) {
                                    bukuTransaksi = buku;
                                    break;
                                }
                            }

                            if (anggotaTransaksi != null && bukuTransaksi != null) {
                                TransaksiPeminjaman transaksiPeminjaman = new TransaksiPeminjaman(anggotaTransaksi, bukuTransaksi, tanggalTransaksi, waktuTransaksi, durasiPeminjaman);
                                daftarTransaksiPeminjaman.add(transaksiPeminjaman);
                                anggotaTransaksi.tambahRiwayatPeminjaman(transaksiPeminjaman);
                                transaksiPeminjaman.cekWaktuPengembalian();
                                JOptionPane.showMessageDialog(null, "Transaksi peminjaman berhasil ditambahkan.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal menambahkan transaksi peminjaman. Anggota atau buku tidak ditemukan.");
                            }
                            break;  // tambahkan break agar keluar dari switch case

                        case 3:
                            // Mengembalikkan Buku
                            String namaAnggotaPengembalian = JOptionPane.showInputDialog("Masukkan nama anggota:");
                            String judulBukuPengembalian = JOptionPane.showInputDialog("Masukkan judul buku yang dikembalikan:");

                            AnggotaPerpustakaan anggotaPengembalian = null;
                            for (AnggotaPerpustakaan anggota : daftarAnggota) {
                                if (anggota.nama.equals(namaAnggotaPengembalian)) {
                                    anggotaPengembalian = anggota;
                                    break;
                                }
                            }

                            TransaksiPeminjaman transaksiPengembalian = null;
                            for (TransaksiPeminjaman transaksi : anggotaPengembalian.riwayatPeminjaman) {
                                if (transaksi.buku.judul.equals(judulBukuPengembalian)) {
                                    transaksiPengembalian = transaksi;
                                    break;
                                }
                            }

                            if (anggotaPengembalian != null && transaksiPengembalian != null) {
                                int sisaHari = transaksiPengembalian.hitungSisaHariPengembalian();
                                String pesanPengembalian = "Batas pengembalian buku '" + transaksiPengembalian.buku.judul + "' adalah dalam " + sisaHari + " hari.";

                                // Tampilkan peringatan batas hari pengembalian
                                int option = JOptionPane.showConfirmDialog(null, pesanPengembalian + "\nApakah Anda yakin ingin mengembalikan buku ini?", "Konfirmasi Pengembalian", JOptionPane.YES_NO_OPTION);

                                if (option == JOptionPane.YES_OPTION) {
                                    // Tampilkan pesan bahwa data akan diperiksa oleh admin
                                    JOptionPane.showMessageDialog(null, "Silahkan tunggu sebentar, data akan diperiksa oleh admin.");
                                } else {
                                    JOptionPane.showMessageDialog(null, "Pengembalian buku dibatalkan.");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal mengembalikan buku. Anggota atau buku tidak ditemukan.");
                            }
                            break;

                        case 4:
                            JOptionPane.showMessageDialog(null, "Riwayat Peminjaman untuk " + daftarAnggota.get(0).nama);
                            for (TransaksiPeminjaman transaksi : daftarAnggota.get(0).riwayatPeminjaman) {
                                JOptionPane.showMessageDialog(null, "ID Transaksi: " + transaksi.idTransaksi +
                                                "\nBuku: " + transaksi.buku.judul +
                                                "\nTanggal: " + transaksi.tanggal +
                                                "\nWaktu: " + transaksi.waktu +
                                                "\nDurasi Peminjaman: " + transaksi.durasiPeminjaman + " hari");
                            }
                            break;

                        case 5:
                            // Keluar
                            isLoggedIn = false;
                            JOptionPane.showMessageDialog(null, "Anda keluar.");
                            break;

                        default:
                            JOptionPane.showMessageDialog(null, "Pilihan tidak valid. Silakan pilih kembali.");
                            break;
                    }
                }
}
}
}
}
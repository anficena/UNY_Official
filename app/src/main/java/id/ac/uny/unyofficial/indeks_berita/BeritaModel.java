package id.ac.uny.unyofficial.indeks_berita;

/**
 * Created by WIN 8 on 10/10/2017.
 */

public class BeritaModel {
    String title;
    String date;
    String image;
    String artikel;
    String detail;


    public BeritaModel(String title,String image, String date, String artikel, String detail) {
        this.title = title;
        this.image = image;
        this.date = date;
        this.artikel = artikel;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getImage() {
        return image;
    }
    public String getArtikel() {
        return artikel;
    }
    public String getDetail() {
        return detail;
    }

}

package id.ac.uny.unyofficial.indeks_pengumuman;

/**
 * Created by WIN 8 on 10/10/2017.
 */

public class PengumumanModel {
    String title;
    String date;
    String detail_link;


    public PengumumanModel(String title, String date, String detail_link) {
        this.title = title;
        this.date = date;
        this.detail_link = detail_link;
    }

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getDetail_link() {
        return detail_link;
    }

}

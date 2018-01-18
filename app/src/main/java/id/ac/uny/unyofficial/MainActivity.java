package id.ac.uny.unyofficial;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import id.ac.uny.unyofficial.indeks_berita.BeritaUny;
import id.ac.uny.unyofficial.indeks_pengumuman.IndexPengumuman;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button indexPengumuman,indexBerita,home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                android.support.v4.app.Fragment fragment = null;
                if (tabId == R.id.tab_home) {
                    fragment = new BerandaUny();
                } else if(tabId == R.id.tab_berita){
                    fragment = new BeritaUny();
                } else if (tabId == R.id.tab_pengumuman){
                    fragment = new PengumumanUny();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentContainer, fragment)
                        .commit();
            }
        });

//        indexPengumuman.setOnClickListener(this);
//        indexBerita.setOnClickListener(this);
//        home.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        Intent i;
//        switch (view.getId()){
//            case R.id.bthome:
//                Intent iH = new Intent(MainActivity.this,HomeActivity.class);
//                startActivity(iH);
//                break;
//
//            case R.id.btindexberita:
//                Intent iB = new Intent(MainActivity.this,IndexBerita.class);
//                startActivity(iB);
//                break;
//
//            case R.id.btindexpeng:
//                Intent iP = new Intent(MainActivity.this,IndexPengumuman.class);
//                startActivity(iP);
//                break;
//        }

    }
}

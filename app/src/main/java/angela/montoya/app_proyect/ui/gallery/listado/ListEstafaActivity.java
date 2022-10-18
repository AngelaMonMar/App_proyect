package angela.montoya.app_proyect.ui.gallery.listado;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import angela.montoya.app_proyect.R;
import angela.montoya.app_proyect.utils.Interface_comunica;

import static angela.montoya.app_proyect.MainActivity.KEY;

public class ListEstafaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_estafa);

   /*     Fragment newFragment = new ListEstafaFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, newFragment).commit();*/


      /*  FragmentTransaction transaction = newFragment.beginTransaction();
        transaction.add(R.id.container,YOUR_FRAGMENT_NAME,YOUR_FRAGMENT_STRING_TAG);
        transaction.addToBackStack(null);
        transaction.commit();*/
/*
        Bundle bundle = getIntent().getExtras();

        if (bundle != null){

            String msng = bundle.getString(KEY);
            Fragment fragment = new ListEstafaFragment();
            boolean fragmentTransaction = true;
            getSupportActionBar().setTitle(getText(R.string.menu_lista_estafas));

            if (fragmentTransaction) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_list_frag, fragment)
                        .commit();

              //  menuItem.setChecked(true);
                //getSupportActionBar().setTitle(menuItem.getTitle());
            }*/

        // }
    }


}
package net.ivanvega.soportediferentespantallasb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.contenedor_pequeno) != null &&
                getSupportFragmentManager().findFragmentById(R.id.contenedor_pequeno) ==null
           ){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.contenedor_pequeno,
                            SelectorFragment.class, null)
            .commit();
        }

        /*
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==R.id.menu_preferencias){
            Toast.makeText(this, "Preferencias", Toast.LENGTH_LONG).show();
            return true;
        } else if(id==R.id.menu_acerca){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Mensaje de Acerca de");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void irUltimoVisitado(){
        SharedPreferences pref = getSharedPreferences("com.example.audiolibros_internal", MODE_PRIVATE);
        int id = pref.getInt("ultimo", -1);
        if(id>=0){
            mostrarDetalle(id);
        }else{
            Toast.makeText(this, "Sin última visita", Toast.LENGTH_LONG).show();
        }
    }

    public void mostrarDetalle(int posLibroSelectd) {
        DetalleFragment detalleFragment;

        detalleFragment =(DetalleFragment)
                getSupportFragmentManager().findFragmentById(R.id.detalle_fragment);

        if (detalleFragment!=null){
            detalleFragment.setInfoLibro(posLibroSelectd)   ;
        }else{

            detalleFragment = new DetalleFragment();

            Bundle param = new Bundle();
            param.putInt(DetalleFragment.ARG_LIBRO_POS, posLibroSelectd );

            detalleFragment.setArguments(param);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.contenedor_pequeno, detalleFragment, null )
                    .addToBackStack(null)
                    .commit();
        }
        SharedPreferences pref = getSharedPreferences("com.example.audiolibros_internal", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("último", posLibroSelectd);
        editor.commit();

    }
}
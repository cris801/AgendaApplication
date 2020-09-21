package com.example.agendaapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendaapplication.ClassesDB.SQLiteHelper;
import com.example.agendaapplication.ClassesDB.Usuario;
import com.example.agendaapplication.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilName;
    private TextInputLayout tilPhone;
    private TextInputLayout tilAddress;

    private Button btnSave;
    private Button btnConsult;

    private ListView lvConsult;
    private List<String> names;

    private String name;
    private String phone;
    private String address;

    private final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z ]+$");

    SQLiteHelper sqlh = null;
    SQLiteDatabase db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // conectamos los componentes visuales con su logica de programacion
        tilName = (TextInputLayout) findViewById(R.id.tilName);
        tilPhone = (TextInputLayout) findViewById(R.id.tilPhone);
        tilAddress = (TextInputLayout) findViewById(R.id.tilAddress);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnConsult = (Button) findViewById(R.id.btnConsult);

        lvConsult = (ListView) findViewById(R.id.lvConsult);

        //name = etName.getText().toString();




        sqlh = new SQLiteHelper(getApplicationContext(),"agenda.db",null,1);

        // Datos a mostrar
        names = new ArrayList<String>();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validations(v);
            }
        });

        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Consultar", Toast.LENGTH_SHORT).show();
                ConsultarUsuarios(v);
                verLista();
            }
        });

    }

    public void NuevoUsuario(View v){
        phone = tilPhone.getEditText().getText().toString().trim();
        int phonee = Integer.parseInt(phone);

        // crear o utilizar la bd
        db = sqlh.getWritableDatabase();

        // instaciamos a usuario
        Usuario usuario = new Usuario(getApplicationContext(),db);
        usuario.Nuevo(name,address,phonee);

        db.close();
    }

    public void ConsultarUsuarios(View v){
        db = sqlh.getWritableDatabase(); // conecto con la bd

        Usuario usuario = new Usuario(getApplicationContext(),db); //instancio

        Cursor cursor1 = usuario.Consulta(); // consulto todos los usuarios

        // recorremos el cursor
        String nombre, direccion;
        int idU, telefono;

        int filas = cursor1.getCount();
        cursor1.moveToFirst();
        for(int i = 0; i<filas ; i++){
            idU = cursor1.getInt(0);
            nombre =cursor1.getString(1);
            direccion =cursor1.getString(2);
            telefono =cursor1.getInt(3);
            cursor1.moveToNext();

            names.add("\n" +idU+", "+nombre+", "+direccion+", "+telefono);
        }
        db.close();
    }


    private Boolean isValidName() {
        name = tilName.getEditText().getText().toString().trim();

        if (name.isEmpty()){
            //Toast.makeText(this, "no hay naida inil name", Toast.LENGTH_SHORT).show();
            tilName.setError("El campo no puede estar vacío.");
            return false;
        } else if (!NAME_PATTERN.matcher(name).matches()) {
            tilName.setError("nombre invalido");
            return false;
        } else {
            tilName.setError(null);
            return true;
        }
    }
    private Boolean isValidPhone() {
        phone = tilPhone.getEditText().getText().toString().trim();

        if (phone.isEmpty()){
            tilPhone.setError("El campo no puede estar vacío.");
            return false;
        } else if ( phone.length() < 10 || phone.length() > 12) {
            tilPhone.setError("introduce un numero valido de entre 10 y 12 digitos");
            return false;
        } else {
            tilPhone.setError(null);
            return true;
        }
    }
    private Boolean isValidAddress() {
        address = tilAddress.getEditText().getText().toString().trim();
        if (address.isEmpty()){
            tilAddress.setError("El campo no puede estar vacío.");
            return false;
        } else if (!NAME_PATTERN.matcher(address).matches()) {
            tilAddress.setError("direccion no valida");
            return false;
        } else {
            tilPhone.setError(null);
            return true;
        }
    }

    private void validations(View v) {
        if (!isValidName() | !isValidPhone() | !isValidAddress()) {
            Toast.makeText(this, "NO pasas", Toast.LENGTH_SHORT).show();
            return;
        }
        NuevoUsuario(v);
    }

    private void verLista() {

        // Adaptador, la forma visual en la que mostraremos nuestros datos
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);

        // Enlazamos nuestros componentes visuales
        lvConsult.setAdapter(adapter);

        lvConsult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Clicked: " + names.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
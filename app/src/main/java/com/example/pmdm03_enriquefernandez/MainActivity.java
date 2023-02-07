package com.example.pmdm03_enriquefernandez;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int READ_CONTACTS_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermisos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Menu del ActionBar
        menu.findItem(R.id.menu_importar);
        menu.findItem(R.id.menu_exportar);
        menu.findItem(R.id.menu_eliminar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_importar:
                importarContactos();
                break;
            case R.id.menu_exportar:
                exportarContactos();
                Toast.makeText(this, "Exportar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_eliminar:
                eliminarContactos();
                Toast.makeText(this, "Eliminar", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public boolean getPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSIONS_REQUEST);
        }
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private void importarContactos() {
        if(getPermisos()) {
            ContentResolver contentResolver = getContentResolver();

            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,null,null, null);
            if(cursor.getCount() > 0) {
                while (cursor.moveToNext())
                {
                    int id_id = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
                    int id_nombre = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    int id_email;
                    int id_telefono;
                    int id_has_number = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

                    String id = Integer.toString(id_id);
                    String nombre = cursor.getString(id_nombre);
                    String telefono = "";
                    String email = "";
                    if(cursor.getInt(id_has_number) > 0) {
                        Cursor cursorTelefono = contentResolver.query(
                                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            null,
                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                                    + " = ?", new String[] { id }, null);
                        if (cursorTelefono.moveToNext()) {
                            id_telefono = cursorTelefono.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            telefono = cursorTelefono.getString(id_telefono);
                        }
                        cursorTelefono.close();
                    }

                    Cursor cursorEmail = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
                            new String[]{id}, null);

                    id_email = cursorEmail.getColumnIndex(
                            ContactsContract.CommonDataKinds.Email.DATA);

                    if (cursorEmail.moveToFirst()) {
                        email = cursor.getString(id_email);

                        if(email!=null) {
                            email = "Sin correo";
                        }
                    }

                    /*Cursor cursorEmail = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{id},
                            null);
                    if (nombre.equals("Fran Autoescuela")) {
                        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
                    }
                    if (cursorEmail.moveToNext()) {
                        if (nombre.equals("Fran Autoescuela")) {
                            Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
                        }
                        id_email = cursorEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                        email = cursorEmail.getString(id_email);
                    }
                    cursorEmail.close();
                    if (nombre.equals("Fran Autoescuela")) {
                        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
                    }*/

                    if (nombre.equals("ABC")) {
                        Toast.makeText(this, nombre + ", " + telefono + ", " + email, Toast.LENGTH_SHORT).show();
                    }

                }
                cursor.close();
            } else {
                Toast.makeText(this, "No hay contactos", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "No tiene permisos para leer los contactos", Toast.LENGTH_SHORT).show();
        }

    }

    private void exportarContactos() {

    }

    private void eliminarContactos() {

    }
}
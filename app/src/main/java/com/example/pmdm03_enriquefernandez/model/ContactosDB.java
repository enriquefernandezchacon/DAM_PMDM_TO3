package com.example.pmdm03_enriquefernandez.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ContactosDB extends SQLiteOpenHelper{

    private SQLiteDatabase db = null;
    private final List<Contacto> listaContactos;

    public List<Contacto> getListaContactos() { return listaContactos; }

    public ContactosDB(@Nullable Context context) {
        super(context, DbUtils.DATABASE_NAME, null, DbUtils.DATABASE_VERSION);
        listaContactos = new ArrayList<>();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbUtils.CREAR_TABLA_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DbUtils.DROP_TABLA_CONTACTOS);
        onCreate(sqLiteDatabase);
    }

    public boolean leer() {
        listaContactos.clear();
        abrirBD();

        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(DbUtils.SELECT_CONTACTOS, null);
            if (cursor.moveToFirst()) {
                do {
                    Contacto contacto = new Contacto(
                      cursor.getInt(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_ID)),
                      cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_NOMBRE)),
                      cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_TELEFONO)),
                      cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_EMAIL)),
                      cursor.getInt(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_IMAGENID))
                    );
                    listaContactos.add(contacto);
                } while (cursor.moveToNext());
            }
            cerrarBD(cursor);
        }

        return !listaContactos.isEmpty();
    }

    public Contacto leer(int id) {
        Contacto contacto = null;
        abrirBD();

        if (db.isOpen()) {
            String[] campos = new String[]{DbUtils.CAMPO_ID, DbUtils.CAMPO_NOMBRE, DbUtils.CAMPO_TELEFONO, DbUtils.CAMPO_EMAIL, DbUtils.CAMPO_IMAGENID};
            String where = DbUtils.CAMPO_ID + "=?";
            String[] whereArgs = new String[]{String.valueOf(id)};

            Cursor cursor = db.query(DbUtils.TABLA_CONTACTOS,
                    campos, where, whereArgs, null, null, null);

            if (cursor.moveToFirst()) {
                 contacto = new Contacto(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_NOMBRE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_TELEFONO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_EMAIL)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_IMAGENID))
                );
            }
            cerrarBD(cursor);
        }
        return contacto;
    }

    public long insertar(Contacto contacto) {
        ContentValues values = new ContentValues();
        values.put(DbUtils.CAMPO_NOMBRE, contacto.getNombre());
        values.put(DbUtils.CAMPO_TELEFONO, contacto.getTelefono());
        values.put(DbUtils.CAMPO_EMAIL, contacto.getEmail());
        values.put(DbUtils.CAMPO_IMAGENID, contacto.getImagenId());

        abrirBD();
        long resp = db.insert(DbUtils.TABLA_CONTACTOS, null, values);
        cerrarBD(null);

        return resp;
    }

    public int actualizar(Contacto contacto) {
        ContentValues values = new ContentValues();
        values.put(DbUtils.CAMPO_NOMBRE, contacto.getNombre());
        values.put(DbUtils.CAMPO_TELEFONO, contacto.getTelefono());
        values.put(DbUtils.CAMPO_EMAIL, contacto.getEmail());
        values.put(DbUtils.CAMPO_IMAGENID, contacto.getImagenId());

        String where = DbUtils.CAMPO_ID + "=?";
        String[] whereArgs = new String[] {String.valueOf(contacto.getId())};

        abrirBD();
        int resp = db.update(DbUtils.TABLA_CONTACTOS, values, where, whereArgs);
        cerrarBD(null);

        return resp;
    }

    private int eliminar(int id) {
        String where = DbUtils.CAMPO_ID + "=?";
        String[] whereArgs = new String[] {String.valueOf(id)};

        abrirBD();
        int resp = db.delete(DbUtils.TABLA_CONTACTOS, where, whereArgs);
        cerrarBD(null);

        return resp;
    }

    private void eliminar() {
        abrirBD();
        db.execSQL(DbUtils.DELETE_CONTACTOS);
        cerrarBD(null);
    }

    private void abrirBD() {
        SQLiteDatabase db = this.getReadableDatabase();
    }

    private void cerrarBD(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }
}

package com.example.pmdm03_enriquefernandez.model;

public class DbUtils {

    public static final String DATABASE_NAME = "contactor.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLA_CONTACTOS = "contactor";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_TELEFONO = "telefono";
    public static final String CAMPO_EMAIL = "email";
    public static final String CAMPO_IMAGENID = "imagenid";

    public static final String CREAR_TABLA_CONTACTOS = "CREATE TABLE " + TABLA_CONTACTOS + " (" +
            CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            "," + CAMPO_NOMBRE + " TEXT NOT NULL" +
            "," + CAMPO_TELEFONO + " TEXT NOT NULL" +
            "," + CAMPO_EMAIL + " TEXT" +
            "," + CAMPO_IMAGENID + " INT)";

    public static final String DROP_TABLA_CONTACTOS = "DROP TABLE IF EXISTS " + TABLA_CONTACTOS;
    public static final String SELECT_CONTACTOS = "SELECT * FROM " + TABLA_CONTACTOS;
    public static final String DELETE_CONTACTOS = "delete from " + TABLA_CONTACTOS;
}

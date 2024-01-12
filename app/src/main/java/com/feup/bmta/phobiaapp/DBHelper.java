package com.feup.bmta.phobiaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Bio.Library.namespace.BioLib;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "0ECGData.db";
    private static final int DATABASE_VERSION = 47;
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String TABLE_ECG_DATA = "ecg_data";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_ECG = "ecg_data";

    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_ID_CARD_NUMBER = "id_card_number";

    private SQLiteDatabase mDatabase;

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a single table for both users and their ECG data
        String createDataTableQuery = "CREATE TABLE " + TABLE_ECG_DATA +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_DATE_OF_BIRTH + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_ID_CARD_NUMBER + " TEXT, " +
                COLUMN_ECG + " TEXT);";

        // Execute the SQL query to create the table
        db.execSQL(createDataTableQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed and create fresh tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ECG_DATA);
        onCreate(db);
    }

    // Method to add user to the database
    public long addUser(String username, String password, String fullName, String dateOfBirth, String gender, String idCardNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_DATE_OF_BIRTH, dateOfBirth);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_ID_CARD_NUMBER, idCardNumber);

        long userId = db.insert(TABLE_ECG_DATA, null, values);
        db.close();

        return userId;
    }

    // Method to check if user exists and return user ID
    public long checkCredentialsAndGetUserId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = -1;

        // Columns you want to retrieve
        String[] projection = {COLUMN_ID};

        // WHERE clause to check credentials
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        // Query the database
        Cursor cursor = db.query(TABLE_ECG_DATA, projection, selection, selectionArgs, null, null, null);

        // Check if there is a matching row
        if (cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return userId;
    }

    // Método para adicionar dados QRS à tabela ECG
    public void addQRSData(BioLib.QRS qrs) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_ECG, String.valueOf(qrs.rr)); // Armazena o valor R-R (qrs.rr)
        db.insert(TABLE_ECG_DATA, null, values);
        db.close();
    }


    // Método para converter QRS para uma representação de string
    private String convertQRSToString(BioLib.QRS qrs) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Position: ").append(qrs.position).append("\n");
        stringBuilder.append("BPMi: ").append(qrs.bpmi).append("\n");
        stringBuilder.append("BPM: ").append(qrs.bpm).append("\n");
        stringBuilder.append("RR: ").append(qrs.rr).append("\n");

        // Convertendo o StringBuilder para uma String
        return stringBuilder.toString();
    }

    // Método para obter dados ECG com base no utilizador
    public String getECGData(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String ecgData = null;

        String[] projection = {COLUMN_ECG};

        // Cláusula WHERE para filtrar pelos dados do utilizador
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        // Consulta ao banco de dados
        Cursor cursor = db.query(TABLE_ECG_DATA, projection, selection, selectionArgs, null, null, null);

        // Verifica se há uma linha correspondente
        if (cursor.moveToFirst()) {
            ecgData = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ECG));
        }

        // Fecha o cursor e o banco de dados
        cursor.close();
        db.close();

        return ecgData;
    }

    // Open the database connection
    public void openDatabase() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = getWritableDatabase();
        }
    }

    // Método para obter detalhes do utilizador pelo ID
    public User getUserById(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;


        String[] projection = {COLUMN_FULL_NAME, COLUMN_DATE_OF_BIRTH, COLUMN_GENDER, COLUMN_ID_CARD_NUMBER, COLUMN_USERNAME};

        // Cláusula WHERE para filtrar pelos dados do utilizador
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        // Consulta ao banco de dados
        Cursor cursor = db.query(TABLE_ECG_DATA, projection, selection, selectionArgs, null, null, null);

        // Verifica se há uma linha correspondente
        if (cursor.moveToFirst()) {
            // Obtém os valores das colunas
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULL_NAME));
            String dateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_OF_BIRTH));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
            String idCardNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID_CARD_NUMBER));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));

            // Cria uma instância de User usando o construtor apropriado
            user = new User(fullName, dateOfBirth, gender, idCardNumber, username);
        }

        // Fecha o cursor e o banco de dados
        cursor.close();
        db.close();

        return user;
    }




}

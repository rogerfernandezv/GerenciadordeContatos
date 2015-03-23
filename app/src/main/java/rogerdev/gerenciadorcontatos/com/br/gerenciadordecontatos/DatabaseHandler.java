package rogerdev.gerenciadorcontatos.com.br.gerenciadordecontatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rogerfernandez on 22/03/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String LOGCAT = null;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contatosManager",
    TABLE_CONTATOS = "contatos",
    KEY_ID = "id",
    KEY_NAME = "nome",
    KEY_NUMERO = "numero",
    KEY_EMAIL = "email",
    KEY_ENDERECO = "endereco";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String query = "CREATE TABLE contatos (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, endereco TEXT, numero TEXT, email TEXT);";
        db.execSQL(query);
        //db.execSQL("CREATE TABLE " + TABLE_CONTATOS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_ENDERECO + " TEXT," + KEY_NUMERO + " TEXT," + KEY_EMAIL + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTATOS);
        onCreate(db);
    }

    public void criarContato (Contato contato){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        /*values.put(KEY_ID, contato.getId());
        values.put(KEY_NAME, contato.getNome());
        values.put(KEY_ENDERECO, contato.getEndereco());
        values.put(KEY_NUMERO, contato.getNumero());
        values.put(KEY_EMAIL, contato.getEmail());*/

        //values.put("id", 123);
        values.put("nome", "roger");
        values.put("endereco", "rua do nunca");
        values.put("numero", "nada");
        values.put("email", "jfdijfisjijfd");

        String query = "INSERT INTO contatos(nome, email, endereco, numero) VALUES ('roger','rgr','rua','123')";
        db.execSQL(query);
        //db.insert(TABLE_CONTATOS, null, values);
        db.close();

    }

    public Contato getContato(int id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTATOS, new String[]{KEY_ID, KEY_NAME, KEY_ENDERECO, KEY_NUMERO, KEY_EMAIL}, KEY_ID + "=?", new String[] { String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Contato contato = new Contato(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) );

        db.close();
        cursor.close();

        return contato;

    }

    public void apagarContato(Contato contato){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTATOS, KEY_ID + "=?", new String[] { String.valueOf(contato.getId()) });
        db.close();
    }

    public int getContatoCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTATOS, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

    public int atualizarContato(Contato contato){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contato.getNome());
        values.put(KEY_ENDERECO, contato.getEndereco());
        values.put(KEY_NUMERO, contato.getNumero());
        values.put(KEY_EMAIL, contato.getEmail());

        int rowsAffected = db.update(TABLE_CONTATOS, values, KEY_ID + "=?", new String[] { String.valueOf(contato.getId()) });

        db.close();

        return rowsAffected;

    }

    public List<Contato> getAllContatos(){
        List<Contato> contatos = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTATOS, null);

        if(cursor.moveToFirst()){
            do{
                contatos.add(new Contato(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) ));

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return contatos;

    }


}

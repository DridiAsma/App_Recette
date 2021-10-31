package esen.tn.recipeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import esen.tn.recipeapp.Model.Recipe;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Recipe.db";
    public static final String TABLE_NAME = "recipe_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "PHOTO";
    public static final String COL_4 = "INGREDIENT";
    public static final String COL_5 = "HOW";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PHOTO BLOB, INGREDIENT TEXT, HOW TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,byte[] image, String ingredient,String how) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,image);
        contentValues.put(COL_4,ingredient);
        contentValues.put(COL_5,how);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public ArrayList<Recipe>getAllImg(){
        Recipe imgrecipe = null;
        ArrayList<Recipe> imgList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select NAME,PHOTO from "+TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            imgrecipe = new Recipe(cursor.getBlob(1), cursor.getString(0));
            imgList.add(imgrecipe);
            cursor.moveToNext();
        }
        return imgList;
    }

    public Recipe getRowData(int id){

        Cursor res=null;
        Recipe recipe = null;
        SQLiteDatabase db = this.getWritableDatabase();
        res =  db.rawQuery("select * from " + TABLE_NAME + " where " + COL_1 + "='" + id + "'" , null);
        if(res.moveToFirst()){
            do{
                recipe = new Recipe();
                recipe.setId(res.getInt(res.getColumnIndex("ID")));
                recipe.setTitle(res.getString(res.getColumnIndex("NAME")));
                recipe.setImage(res.getBlob(res.getColumnIndex("PHOTO")));
                recipe.setIngredient(res.getString(res.getColumnIndex("INGREDIENT")));
                recipe.setDescription(res.getString(res.getColumnIndex("HOW")));

            }while (res.moveToNext());
        }
        return recipe;
    }

    public Recipe getIDbyName(String name){

        Cursor res=null;
        Recipe recipe = null;

        SQLiteDatabase db = this.getWritableDatabase();
        res =  db.rawQuery("select "+COL_1+" from " + TABLE_NAME + " where " + COL_2 + "='" + name + "'" , null);
        if(res.moveToFirst()){
            do{
                recipe = new Recipe();
                recipe.setId(res.getInt(res.getColumnIndex("ID")));
            }while (res.moveToNext());
        }
        return recipe;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public boolean updateData(String id,String name, byte[] image, String ingredient,String how) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,image);
        contentValues.put(COL_4,ingredient);
        contentValues.put(COL_5,how);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[]{id});
        return true;
    }

    public ArrayList<Recipe> getRecipeByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from "+TABLE_NAME+" WHERE "+COL_2+" LIKE '%"+name+"%'";
        Cursor res = db.rawQuery(query, null);
        Recipe recipe = null;
        ArrayList<Recipe> result = new ArrayList<>();
        res.moveToFirst();
        while (!res.isAfterLast()){
            recipe = new Recipe(res.getBlob(2), res.getString(1));
            result.add(recipe);
            res.moveToNext();
        }
        return result;
    }

    /*public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }*/

    /*public List<String> getNameOfRecipe(){

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"NAME"};
        String TableName = "Recipe";

        qb.setTables(TableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<String> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(cursor.getColumnIndex("NAME")));
            }while (cursor.moveToNext());
        }
        return result;
    }*/

      /*  public List<String> getNameOfRecipe(){

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"NAME"};
        String TableName = "Recipe";

        qb.setTables(TableName);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);
        List<String> result = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                result.add(cursor.getString(cursor.getColumnIndex("NAME")));
            }while (cursor.moveToNext());
        }
        return result;
    }*/

    /*public List<Recipe> getRecipeByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from "+TABLE_NAME+" WHERE "+COL_2+" LIKE '%"+name+"%'";
        Cursor res = db.rawQuery(query, null);
        List<Recipe> result = new ArrayList<>();
        if(res.moveToFirst()){
            do{
                Recipe recipe = new Recipe();
                recipe.setId(res.getInt(res.getColumnIndex("ID")));
                recipe.setTitle(res.getString(res.getColumnIndex("NAME")));
                recipe.setImage(res.getBlob(res.getColumnIndex("PHOTO")));
                recipe.setIngredient(res.getString(res.getColumnIndex("INGREDIENT")));
                recipe.setDescription(res.getString(res.getColumnIndex("HOW")));

                result.add(recipe);
            }while (res.moveToNext());
        }
        return result;
    }*/
}

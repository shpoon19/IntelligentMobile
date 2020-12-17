package com.bawa.babyneedsapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bawa.babyneedsapp.model.Item;
import com.bawa.babyneedsapp.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseOpenHandler extends SQLiteOpenHelper {

    private Context context;

    public DatabaseOpenHandler(Context context) {
        super( context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION );
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BABY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.KEY_BABY_ITEM  + " INTEGER, "
                + Constants.KEY_COLOR + " TEXT, "
                + Constants.KEY_QTY_NUMBER + " INTEGER, "
                + Constants.KEY_ITEM_SIZE + " INTEGER, "
                + Constants.KEY_DATE_NAME +" LONG );";

        db.execSQL(CREATE_BABY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // delete the old table
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);
    }

    // Add Needs
    public void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put( Constants.KEY_BABY_ITEM, item.getProductName());
        values.put( Constants.KEY_COLOR, item.getProductColor());
        values.put( Constants.KEY_QTY_NUMBER, item.getProductQuantity());
        values.put( Constants.KEY_ITEM_SIZE, item.getProductSize());
        values.put( Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis()); //time stamp  of the system

        db.insert( Constants.TABLE_NAME, null, values);
        //db.close();
        Log.d("DBHandler", "added Item: ");
    }

    // Get Item
    public Item getItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Item item;
        try (Cursor cursor = db.query( Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_BABY_ITEM,
                        Constants.KEY_COLOR,
                        Constants.KEY_QTY_NUMBER,
                        Constants.KEY_ITEM_SIZE,
                        Constants.KEY_DATE_NAME},
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf( id )}, null, null, null, null )) {


            if (cursor != null)
                cursor.moveToFirst();

            item = new Item();
            if (cursor != null) {
                item.setId( Integer.parseInt( cursor.getString( cursor.getColumnIndex( Constants.KEY_ID ) ) ) );
                item.setProductName( cursor.getString( cursor.getColumnIndex( Constants.KEY_BABY_ITEM ) ) );
                item.setProductColor( cursor.getString( cursor.getColumnIndex( Constants.KEY_COLOR ) ) );
                item.setProductQuantity( cursor.getInt( cursor.getColumnIndex( Constants.KEY_QTY_NUMBER ) ) );
                item.setProductSize( cursor.getString( cursor.getColumnIndex( Constants.KEY_ITEM_SIZE ) ) );

                //convert Timestamp to something readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format( new Date( cursor.getLong( cursor.getColumnIndex( Constants.KEY_DATE_NAME ) ) )
                        .getTime() ); // Feb 23, 2020

                item.setDateAdded( formattedDate );

            }
        }
        return item;
    }

    //Get all Items
    public List<Item> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemList = new ArrayList<>();

        try (Cursor cursor = db.query( Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_BABY_ITEM,
                        Constants.KEY_COLOR,
                        Constants.KEY_QTY_NUMBER,
                        Constants.KEY_ITEM_SIZE,
                        Constants.KEY_DATE_NAME},
                null, null, null, null,
                Constants.KEY_DATE_NAME + " DESC" )) {


            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setId( Integer.parseInt( cursor.getString( cursor.getColumnIndex( Constants.KEY_ID ) ) ) );
                    item.setProductName( cursor.getString( cursor.getColumnIndex( Constants.KEY_BABY_ITEM ) ) );
                    item.setProductColor( cursor.getString( cursor.getColumnIndex( Constants.KEY_COLOR ) ) );
                    item.setProductQuantity( cursor.getInt( cursor.getColumnIndex( Constants.KEY_QTY_NUMBER ) ) );
                    item.setProductSize( cursor.getString( cursor.getColumnIndex( Constants.KEY_ITEM_SIZE ) ) );

                    //convert Timestamp to something readable
                    DateFormat dateFormat = DateFormat.getDateInstance();
                    String formattedDate = dateFormat.format( new Date( cursor.getLong( cursor.getColumnIndex( Constants.KEY_DATE_NAME ) ) )
                            .getTime() ); // Feb 23, 2020
                    item.setDateAdded( formattedDate );

                    //Add to arraylist
                    itemList.add( item );
                } while (cursor.moveToNext());
            }
        }
        return itemList;

    }

    //Todo: Add updateItem
    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_BABY_ITEM, item.getProductName());
        values.put(Constants.KEY_COLOR, item.getProductColor());
        values.put(Constants.KEY_QTY_NUMBER, item.getProductQuantity());
        values.put(Constants.KEY_ITEM_SIZE, item.getProductSize());
        values.put(Constants.KEY_DATE_NAME, java.lang.System.currentTimeMillis());//timestamp of the system

        //update row
        return db.update(Constants.TABLE_NAME, values,
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});

    }

    //Todo: Add Delete Item
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,
                Constants.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        //close
        db.close();

    }

    //Todo: getItemCount
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery( countQuery, null )) {

            return cursor.getCount();
        }

    }
}



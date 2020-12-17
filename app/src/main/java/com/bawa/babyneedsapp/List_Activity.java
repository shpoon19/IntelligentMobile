package com.bawa.babyneedsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bawa.babyneedsapp.data.DatabaseOpenHandler;
import com.bawa.babyneedsapp.model.Item;
import com.bawa.babyneedsapp.ui.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class List_Activity extends AppCompatActivity {

    private static final String TAG = "ListAcivity";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DatabaseOpenHandler databaseOpenHandler;
    private FloatingActionButton fab;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;



    private Button saveButton;
    private EditText productName;
    private EditText prodQuantity;
    private EditText prodColor;
    private EditText prodSize;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list );

        recyclerView = findViewById(R.id.recycler);
        fab = findViewById(R.id.fab);


        databaseOpenHandler = new DatabaseOpenHandler(this);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();

        // Get items from db
        itemList = databaseOpenHandler.getAllItems();
        for (Item item: itemList){
            Log.d(TAG, "onCreate: " + item.getProductName());
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        fab.setOnClickListener( v -> createPopDialog() );
    }

    public void createPopDialog(){

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);

        productName = view.findViewById(R.id.babyItem);
        prodQuantity =  view.findViewById(R.id.itemQty);
        prodColor = view.findViewById(R.id.itemColor);
        prodSize = view.findViewById(R.id.itemSize);

        saveButton = view.findViewById(R.id.btnSave);
        saveButton.setOnClickListener( v -> {

            if (!productName.getText().toString().isEmpty()
                    && !prodColor.getText().toString().isEmpty()
                    && !prodQuantity.getText().toString().isEmpty()
                    && !prodSize.getText().toString().isEmpty()){

                saveItem(v);
            } else {
                Snackbar.make(v, "Empty Fields not allowed", Snackbar.LENGTH_LONG)
                        .show();
            }
        } );


        builder.setView( view );
        alertDialog = builder.create();
        alertDialog.show();

    }


    public void saveItem(View view){

        //Todo: save each baby item to db
        Item item = new Item();
        String newItem = productName.getText().toString().trim();
        String newColor = prodColor.getText().toString().trim();
        int newQuantity = Integer.parseInt( prodQuantity.getText().toString().trim());
        String newSze = prodSize.getText().toString().trim();

        item.setProductName(newItem);
        item.setProductColor(newColor);
        item.setProductQuantity(newQuantity);
        item.setProductSize(newSze);

        databaseOpenHandler.addItem(item);
        Snackbar.make(view, "Product successfully saved", Snackbar.LENGTH_SHORT)
                .show();

        //Todo: move to next screen - details screen

        new Handler().postDelayed( () -> {
            alertDialog.dismiss();

            Intent intent = new Intent( List_Activity.this, List_Activity.class);
            startActivity(intent);
            finish();
        }, 1200);

    }
}
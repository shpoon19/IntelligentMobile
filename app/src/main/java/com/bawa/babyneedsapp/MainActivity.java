package com.bawa.babyneedsapp;

import android.content.Intent;
import android.os.Bundle;

import com.bawa.babyneedsapp.data.DatabaseOpenHandler;
import com.bawa.babyneedsapp.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private Button saveButton;
    private EditText prodName;
    private EditText prodQuantity;
    private EditText prodColor;
    private EditText prodSize;
    private DatabaseOpenHandler databaseOpenHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );


        databaseOpenHandler = new DatabaseOpenHandler(this);

        //Todo: Move to next activity if there is data in the datatbase
        byPassActivity();


        List<Item>items = databaseOpenHandler.getAllItems();

        //Todo: Check if item was saved
        for (Item item: items){
            Log.d("Main", "onCreate: " + item.getProductName());
        }

        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( view -> {

            createPopUpDialog();
//             Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
//                     .setAction( "Action", null ).show();
        } );
    }

    private void byPassActivity() {

        if (databaseOpenHandler.getItemsCount()>0){
            startActivity(new Intent(MainActivity.this, List_Activity.class));
            finish();
        }
    }

    public void saveItem(View view){

        //Todo: save each baby item to db
        Item item = new Item();
        String newItem = prodName.getText().toString().trim();
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
            dialog.dismiss();

            Intent intent = new Intent( MainActivity.this, List_Activity.class);
            startActivity(intent);
        }, 1200);

    }
    private void createPopUpDialog(){

        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_up, null);

        prodName = view.findViewById(R.id.babyItem);
        prodQuantity =  view.findViewById(R.id.itemQty);
        prodColor = view.findViewById(R.id.itemColor);
        prodSize = view.findViewById(R.id.itemSize);

        saveButton = view.findViewById(R.id.btnSave);
        saveButton.setOnClickListener( v -> {

            if (!prodName.getText().toString().isEmpty()
                    && !prodColor.getText().toString().isEmpty()
                    && !prodQuantity.getText().toString().isEmpty()
                    && !prodSize.getText().toString().isEmpty()){

                saveItem(v);
            } else {
                Snackbar.make(v, "Empty Fields not allowed", Snackbar.LENGTH_LONG)
                        .show();
            }
        } );
        builder.setView(view);
        //builder.setTitle("Save Baby Item");
        dialog = builder.create(); // Creating dialog object
        dialog.show(); // This is an important step

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }
}
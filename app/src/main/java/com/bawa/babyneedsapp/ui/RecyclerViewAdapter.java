package com.bawa.babyneedsapp.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawa.babyneedsapp.R;
import com.bawa.babyneedsapp.data.DatabaseOpenHandler;
import com.bawa.babyneedsapp.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item>itemList;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;


    public RecyclerViewAdapter(Context context,List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
            Item item = itemList.get(position); // item object
            holder.itemName.setText(MessageFormat.format("Item: {0}", item.getProductName()));
            holder.itemColor.setText(MessageFormat.format("Color: {0}", item.getProductColor()));
            holder.itemQuantity.setText(MessageFormat.format("Quantity: {0}", String.valueOf( item.getProductQuantity())));
            holder.itemSize.setText(MessageFormat.format("Size: {0}", String.valueOf( item.getProductSize())));
            holder.dateItemAdded.setText(MessageFormat.format("Added on: {0}", item.getDateAdded()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView itemName;
        public TextView itemColor;
        public TextView itemQuantity;
        public TextView itemSize;
        public TextView dateItemAdded;
        public Button editButton;
        public Button deleteButton;
        public int id;



        public ViewHolder(@NonNull View itemView, Context ctx) {
            super( itemView );
            context = ctx;

            itemName = itemView.findViewById(R.id.item_name);
            itemColor = itemView.findViewById(R.id.item_color);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            itemSize = itemView.findViewById(R.id.item_size);
            dateItemAdded = itemView.findViewById(R.id.item_date);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {

            int position;
            position = getAdapterPosition();
            Item item =itemList.get(position);
            switch (v.getId()){
                case R.id.editButton:
                    //Todo:  edit item
                    editItem(item);
                    break;
                case R.id.deleteButton:
                    //Todo: delete item
                    deleteItem(item.getId());
                    break;
            }
        }

        private void editItem(Item newItem) {

            // Todo: populate the popup with current object data
            // Item item = itemList.get(getAdapterPosition());

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.pop_up, null);
            Button saveButton;
            EditText babyItem;
            EditText itemQuantity;
            EditText itemColor;
            EditText itemSize;
            TextView title;


            babyItem = view.findViewById(R.id.babyItem);
            itemQuantity =  view.findViewById(R.id.itemQty);
            itemColor = view.findViewById(R.id.itemColor);
            itemSize = view.findViewById(R.id.itemSize);
            saveButton = view.findViewById(R.id.btnSave);
            title = view.findViewById(R.id.title);

            title.setText( R.string.edit_item);
            babyItem.setText(newItem.getProductName());
            itemQuantity.setText(String.valueOf(newItem.getProductQuantity()));
            itemColor.setText(newItem.getProductColor());
            itemSize.setText(newItem.getProductSize());
            saveButton.setText( R.string.update_item);

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();

            saveButton.setOnClickListener( v -> {
                DatabaseOpenHandler db = new DatabaseOpenHandler(context);

                //Todo: Update Items
                newItem.setProductName(babyItem.getText().toString());
                newItem.setProductQuantity(Integer.parseInt(itemQuantity.getText().toString()));
                newItem.setProductColor(itemColor.getText().toString());
                newItem.setProductSize(itemSize.getText().toString());

                if (!babyItem.getText().toString().isEmpty()
                && !itemColor.getText().toString().isEmpty()
                && !itemQuantity.getText().toString().isEmpty()
                && !itemSize.getText().toString().isEmpty()){

                    //Todo: Refresh the adapter position
                    notifyItemChanged(getAdapterPosition(), newItem);
                    db.updateItem(newItem);
                } else {
                    Snackbar.make(view, "Fields empty",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }

                alertDialog.dismiss();
            } );
        }

        private void deleteItem(int id) {

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_pop,null);
            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();

            yesButton.setOnClickListener( v -> {
                DatabaseOpenHandler db = new DatabaseOpenHandler(context);
                db.deleteItem(id);
                itemList.remove(getAdapterPosition());

                //notifyDataSetChanged();
                notifyItemRemoved(getAdapterPosition());

                // Todo: dismiss the dialog button;
                alertDialog.dismiss();
            } );
            noButton.setOnClickListener( v -> alertDialog.dismiss() );




        }
    }
}

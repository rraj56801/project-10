package com.example.android.myinventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.myinventoryapp.DataFile.ItemContract.ItemEntry;

/**
 * Created by RajBaba on 23-08-2017.
 */
public class ItemCursorAdapter extends CursorAdapter {

    public ItemCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView tvName = (TextView) view.findViewById(R.id.item_name);
        TextView tvPrice = (TextView) view.findViewById(R.id.item_price);
        TextView tvQuantity = (TextView) view.findViewById(R.id.item_quantity);
        Button sellBtn = (Button) view.findViewById(R.id.item_sell_btn);
        Button buyBtn = (Button) view.findViewById(R.id.item_buy_btn);
        int idColumnIndex = cursor.getColumnIndex(ItemEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
        int imageColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE);
        int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);
        String itemName = cursor.getString(nameColumnIndex);
        String itemPrice = cursor.getString(priceColumnIndex);
        final int itemQuantity = cursor.getInt(quantityColumnIndex);
        final int itemId = cursor.getInt(idColumnIndex);
        tvName.setText(itemName);
        tvPrice.setText(itemPrice);
        tvQuantity.setText(context.getString(R.string.quantity_setting_text) + itemQuantity);
        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemQuantity > 0) {
                    int tempQuantity = itemQuantity;
                    tempQuantity--;
                    ContentValues values = new ContentValues();
                    values.put(ItemEntry.COLUMN_ITEM_QUANTITY, tempQuantity);
                    Uri currentItemUri = ContentUris.withAppendedId(ItemEntry.CONTENT_URI, itemId);
                    context.getContentResolver().update(currentItemUri, values, null, null);
                }
            }
        });
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempQuantity = itemQuantity;
                tempQuantity++;
                ContentValues values = new ContentValues();
                values.put(ItemEntry.COLUMN_ITEM_QUANTITY, tempQuantity);
                Uri currentItemUri = ContentUris.withAppendedId(ItemEntry.CONTENT_URI, itemId);
                context.getContentResolver().update(currentItemUri, values, null, null);
            }
        });
    }
}

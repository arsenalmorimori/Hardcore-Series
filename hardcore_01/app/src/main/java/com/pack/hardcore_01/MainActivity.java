package com.pack.hardcore_01;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_item,et_price;
    Button btn_addList, btn_add, btn_delete, btn_update, btn_back;
    LinearLayout addPage;
    ListView listView;
    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
    TextView tv_noData;
    ArrayAdapter adapterArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addPage = findViewById(R.id.addPage);

        et_item = findViewById(R.id.et_item);
        et_price = findViewById(R.id.et_price);

        btn_addList = findViewById(R.id.btn_addList);
        btn_add = findViewById(R.id.btn_add);
        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);
        btn_back = findViewById(R.id.btn_back);

        tv_noData = findViewById(R.id.tv_noData);
        listView = findViewById(R.id.listView);


        refresh();

//        --------------- NAVIGATION ---------------

        btn_addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPage.setVisibility(View.VISIBLE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPage.setVisibility(View.GONE);
            }
        });

//        --------------- ADD ---------------
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryModel groceryModel;

                try{
                    groceryModel = new GroceryModel(-1, et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                }catch (Exception e){
                    groceryModel = new GroceryModel(-1, "erorr", 0);

                }

                databaseHelper = new DatabaseHelper(MainActivity.this);
                boolean b = databaseHelper.addOne(groceryModel);
                Toast.makeText(MainActivity.this, "ADD SUCCESS", Toast.LENGTH_SHORT).show();
                refresh();
                addPage.setVisibility(View.GONE);

            }
        });

//        --------------- DELETE ---------------
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroceryModel deleteItem = (GroceryModel) adapterView.getItemAtPosition(i);
                databaseHelper.deleteOne(deleteItem);

                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                refresh();
                return true;
            }
        });

//        --------------- UPDATE ---------------
        final int[] idSelected = new int[1];
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GroceryModel clickItem = (GroceryModel) adapterView.getItemAtPosition(i);
                et_item.setText(clickItem.getName());
                et_price.setText(String.valueOf(clickItem.getPrice()));

                idSelected[0] = clickItem.getId();
                addPage.setVisibility(View.VISIBLE);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryModel updateItem = new GroceryModel(idSelected[0],et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                boolean b = databaseHelper.updateOne(updateItem);

                refresh();
                addPage.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "UPDATED", Toast.LENGTH_SHORT).show();
            }
        });



    }

//        --------------- VIEW ---------------
    private void refresh(){
        try{
            adapterArr = new ArrayAdapter<GroceryModel>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, databaseHelper.getEveryone());
            listView.setAdapter(adapterArr);
            if (adapterArr.isEmpty()){
                tv_noData.setVisibility(View.VISIBLE);
            }else{
                tv_noData.setVisibility(View.GONE);
            }
        }catch (Exception e){
            Toast.makeText(this, "Theres an ERROR", Toast.LENGTH_SHORT).show();
        }
    }


}
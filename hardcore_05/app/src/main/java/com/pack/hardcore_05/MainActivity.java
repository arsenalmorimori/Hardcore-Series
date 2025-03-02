package com.pack.hardcore_05;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et_item, et_price;
    TextView tv_noData;
    Button addList, btn_add, btn_back, btn_update;

    //    SORT
    EditText et_search;
    Button btn_sort,btn_sortAsc, btn_sortDesc, btn_sortId, btn_sortItem,btn_sortPrice;

    LinearLayout addPage, sortPage;
    ListView listView;

    ArrayAdapter arrayAdapter;

    String arrange = "ASC";
    String sortBy = "a";


    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

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

        et_item = findViewById(R.id.et_item);
        et_price = findViewById(R.id.et_price);
        addList = findViewById(R.id.addList);
        btn_add = findViewById(R.id.btn_add);
        btn_back = findViewById(R.id.btn_back);
        btn_update = findViewById(R.id.btn_update);

        addPage = findViewById(R.id.addPage);
        listView = findViewById(R.id.listView);
        tv_noData = findViewById(R.id.tv_noData);

        et_search = findViewById(R.id.et_search);
        sortPage = findViewById(R.id.sortPage);
        btn_sort = findViewById(R.id.btn_sort);
        btn_sortAsc = findViewById(R.id.btn_sortAsc);
        btn_sortDesc = findViewById(R.id.btn_sortDesc);
        btn_sortId = findViewById(R.id.btn_sortId);
        btn_sortItem = findViewById(R.id.btn_sortItem);
        btn_sortPrice = findViewById(R.id.btn_sortPrice);
        refresh(arrange);

//        ----------------- NAVIGATIONS -----------------
        addList.setOnClickListener(new View.OnClickListener() {
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

//        ----------------- ADD -----------------
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreModel addItem;
                try {
                    addItem = new StoreModel(-1, et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                }catch (Exception e){
                    addItem = new StoreModel(-1, "errorrr", 404);
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                databaseHelper.addOne(addItem);

                refresh(arrange);
                addPage.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "ADDED", Toast.LENGTH_SHORT).show();
            }
        });

//        ----------------- DELETE -----------------
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreModel deleteItem = (StoreModel) adapterView.getItemAtPosition(i);
                databaseHelper.deleteOne(deleteItem);

                refresh(arrange);
                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

//        ----------------- UPDATE -----------------
        final int[] id = new int[1];
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreModel updateItem = (StoreModel) adapterView.getItemAtPosition(i);

                et_item.setText(String.valueOf(updateItem.getItem()));
                et_price.setText(String.valueOf(updateItem.getPrice()));

                id[0] = updateItem.getId();
                addPage.setVisibility(View.VISIBLE);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreModel updateList = new StoreModel(id[0], et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                boolean b = databaseHelper.updateOne(updateList);

                refresh(arrange);
                addPage.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "UPDATED", Toast.LENGTH_SHORT).show();
            }
        });

//        ----------------- SORT -----------------
        String sort;
        btn_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortPage.setVisibility(View.VISIBLE);
            }
        });
        btn_sortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrange = "ASC";
                refresh(arrange);
                sortPage.setVisibility(View.GONE);
            }
        });
        btn_sortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrange = "DESC";
                refresh(arrange);
                sortPage.setVisibility(View.GONE);
            }
        });
        btn_sortId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy= "a";
                btn_sortId.setBackgroundColor(getResources().getColor(R.color.green));
                btn_sortItem.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sortPrice.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });
        btn_sortItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy= "b";
                btn_sortId.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sortItem.setBackgroundColor(getResources().getColor(R.color.green));
                btn_sortPrice.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });
        btn_sortPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy= "c";
                btn_sortId.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sortItem.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sortPrice.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

//        ----------------- SEARCH -----------------
 et_search.addTextChangedListener(new TextWatcher() {
     @Override
     public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

     }

     @Override
     public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        arrayAdapter.getFilter().filter(charSequence);
     }

     @Override
     public void afterTextChanged(Editable editable) {

     }
 });
    }

    //        ----------------- VIEW -----------------
    private void refresh(String arrange){
        if(sortBy.equals("a")) {
            arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, databaseHelper.getEveryone(arrange));
        } else if (sortBy.equals("b")) {
            arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, databaseHelper.getItemm(arrange));
        }else{
            arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, databaseHelper.getPrice(arrange));

        }
        listView.setAdapter(arrayAdapter);

        tv_noData.setVisibility(arrayAdapter.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
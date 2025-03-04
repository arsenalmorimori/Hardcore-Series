package com.pack.hardcorelaststraw_08;

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

public class MainActivity extends AppCompatActivity {
// ADD and PROFILE
    EditText et_search, et_item, et_price;
    Button btn_profilePage,btn_sortPage,btn_addPage;
    Button btn_add, btn_update;
    TextView tv_noData,tv_back;
    ListView listView;

//    SORT
    Button btn_sortNameAsc,btn_sortNameDesc,btn_sortPriceAsc, btn_sortPriceDesc,btn_sortIdAsc,btn_sortIdDesc;

//    SELECT
    TextView tv_selectDelete, tv_selectEdit, tv_selectBack,tv_select,tv_add;
//    PAGE
    LinearLayout sortPage, addPage,selectPage;



    String sortBy = "ID";
    String arrange = "ASC";
    ArrayAdapter arrayAdapter;
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

        et_search = findViewById(R.id.et_search );
        et_item = findViewById(R.id.et_item );
        et_price = findViewById(R.id.et_price );
        btn_profilePage = findViewById(R.id.btn_profilePage );
        btn_sortPage = findViewById(R.id.btn_sortPage );
        btn_addPage = findViewById(R.id.btn_addPage );
        btn_add = findViewById(R.id.btn_add );
        btn_update = findViewById(R.id.btn_update );
        tv_back = findViewById(R.id.tv_back );
        tv_add = findViewById(R.id.tv_add );
        tv_noData = findViewById(R.id.tv_noData );
        listView = findViewById(R.id.listView );

        btn_sortNameAsc = findViewById(R.id.btn_sortNameAsc);
        btn_sortNameDesc = findViewById(R.id.btn_sortNameDesc);
        btn_sortPriceAsc = findViewById(R.id.btn_sortPriceAsc);
        btn_sortPriceDesc = findViewById(R.id.btn_sortPriceDesc);
        btn_sortIdAsc = findViewById(R.id.btn_sortIdAsc);
        btn_sortIdDesc = findViewById(R.id.btn_sortIdDesc);
        tv_select = findViewById(R.id.tv_select);
        tv_selectDelete = findViewById(R.id.tv_selectDelete);
        tv_selectEdit = findViewById(R.id.tv_selectEdit);
        tv_selectBack = findViewById(R.id.tv_selectBack);
        sortPage = findViewById(R.id.sortPage);
        addPage = findViewById(R.id.addPage);
        selectPage = findViewById(R.id.selectPage);
        refresh(sortBy,arrange);





//        ------------------ NAVIGATION ------------------
        btn_addPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_add.setText("ADD LIST");
                btn_add.setVisibility(View.VISIBLE);
                btn_update.setVisibility(View.GONE);
                addPage.setVisibility(View.VISIBLE);
            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPage.setVisibility(View.GONE);
            }
        });
        final String[] item = new String[1];
        final int[] price = new int[1];
        final int[] id = new int[1];
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreModel selected = (StoreModel) adapterView.getItemAtPosition(i);
                selectPage.setVisibility(View.VISIBLE);
                tv_select.setText(selected.getItem());

                item[0] = selected.getItem();
                price[0] = selected.getPrice();
                id[0] = selected.getId();
            }
        });
        tv_selectEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_add.setText("EDIT");
                et_item.setText(String.valueOf(item[0]));
                et_price.setText(String.valueOf(price[0]));
                addPage.setVisibility(View.VISIBLE);
                btn_add.setVisibility(View.GONE);
                btn_update.setVisibility(View.VISIBLE);
            }
        });
        tv_selectBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPage.setVisibility(View.GONE);
            }
        });



//        ------------------ ADD ------------------
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                StoreModel addItem;

                try{
                    addItem = new StoreModel(-1, et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                }catch (Exception e){
                    addItem = new StoreModel(-1,"ERRRR", 404);
                }

                databaseHelper.addOne(addItem);
                refresh(sortBy,arrange);
                addPage.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "ADDED : " + addItem.toString(), Toast.LENGTH_SHORT).show();
            }
        });


//        ------------------ DELETE ------------------
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreModel deleteItem = (StoreModel) adapterView.getItemAtPosition(i);
                databaseHelper.deleteOne(deleteItem);

                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                refresh(sortBy,arrange);
                return true;
            }
        });
        tv_selectDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreModel deleteItem = new StoreModel(id[0],null,0);
                databaseHelper.deleteOne(deleteItem);

                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                refresh(sortBy,arrange);
                selectPage.setVisibility(View.GONE);

            }
        });

//        ------------------ UPDATE ------------------
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreModel update = new StoreModel(id[0], et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                databaseHelper.updateOne(update);


                Toast.makeText(MainActivity.this, "UPDATE", Toast.LENGTH_SHORT).show();
                refresh(sortBy,arrange);
                addPage.setVisibility(View.GONE);
                selectPage.setVisibility(View.GONE);
            }
        });

//        ------------------ SEARCH ------------------
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

//        ------------------ SORT ------------------
        btn_sortPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortPage.setVisibility(View.VISIBLE);
            }
        });

        btn_sortIdAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = "ID";
                arrange = "ASC";
                refresh(sortBy,arrange);
                sortPage.setVisibility(View.GONE);
            }
        });
        btn_sortIdDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = "ID";
                arrange = "DESC";
                refresh(sortBy,arrange);
                sortPage.setVisibility(View.GONE);
            }
        });
        btn_sortNameAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = "ITEM";
                arrange = "ASC";
                refresh(sortBy,arrange);
                sortPage.setVisibility(View.GONE);
            }
        });
        btn_sortNameDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = "ITEM";
                arrange = "DESC";
                refresh(sortBy,arrange);
                sortPage.setVisibility(View.GONE);
            }
        });
        btn_sortPriceAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = "PRICE";
                arrange = "ASC";
                refresh(sortBy,arrange);
                sortPage.setVisibility(View.GONE);
            }
        });
        btn_sortPriceDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = "PRICE";
                arrange = "DESC";
                refresh(sortBy,arrange);
                sortPage.setVisibility(View.GONE);
            }
        });
    }


//        ------------------ VIEW  ------------------
        private void refresh(String sortBy, String arrange){
            arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, databaseHelper.getEveryone(sortBy,arrange));
            listView.setAdapter(arrayAdapter);

            tv_noData.setVisibility(arrayAdapter.isEmpty() ? View.VISIBLE : View.GONE);
        }
}
package com.pack.hardcore_06;

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

    EditText et_search, et_item, et_quantity,et_username;
    TextView tv_noData, tv_user;
    Button addList, btn_add,btn_update,btn_back,sortBtn;

    Button  btn_username,btn_sortId,btn_sortItem,btn_sortQuantity, btn_sortAsc, btn_sortDesc,userBtn;

    ListView listView;
    LinearLayout addPage, sortPage,usernamePage;

    ArrayAdapter username;
    String arrange = "ASC";
    String sortBy = "a";
    ArrayAdapter arrayAdapter;
    DatabaseHelper dbhelper = new DatabaseHelper(MainActivity.this);

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

        et_search = findViewById(R.id.et_search);
        et_item = findViewById(R.id.et_item);
        et_quantity = findViewById(R.id.et_quantity);
        et_username = findViewById(R.id.et_username);
        tv_user = findViewById(R.id.tv_user);

        addList = findViewById(R.id.addList);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_back = findViewById(R.id.btn_back);
        sortBtn = findViewById(R.id.sortBtn);
        userBtn = findViewById(R.id.userBtn);
        usernamePage = findViewById(R.id.usernamePage);

        btn_username = findViewById(R.id.btn_username);
        btn_sortId = findViewById(R.id.btn_sortId);
        btn_sortItem = findViewById(R.id.btn_sortItem);
        btn_sortQuantity = findViewById(R.id.btn_sortQuantity);
        btn_sortAsc = findViewById(R.id.btn_sortAsc);
        btn_sortDesc = findViewById(R.id.btn_sortDesc);

        tv_noData = findViewById(R.id.tv_noData);

        listView = findViewById(R.id.listView);
        addPage = findViewById(R.id.addPage);
        sortPage = findViewById(R.id.sortPage);
        refresh();
        refreshUsername();

//        ----------------------------- USERNAME -----------------------------
        btn_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.isEmpty()){
                    DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                    Username username;
                    try {
                        username = new Username(-1, et_username.getText().toString());
                    }catch (Exception e){
                        username = new Username(-1,"error");
                    }
                    db.userAdd(username);
//                    refreshUsername();
                    Toast.makeText(MainActivity.this, "USERNAME ADDED", Toast.LENGTH_SHORT).show();
                    usernamePage.setVisibility(View.GONE);
                }else{
                    Username updateUser = new Username(0,et_username.getText().toString());
                    dbhelper.userUpdate(updateUser);

                    usernamePage.setVisibility(View.GONE);
                    refreshUsername();
                }
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tv_user.setText("HELLO " + String.valueOf(dbhelper.getUsername()));
                et_username.setText( String.valueOf(dbhelper.getUsername()));
                sortPage.setVisibility(View.VISIBLE);
//                refreshUsername();

            }
        });


//        ----------------------------- NAVIGATION -----------------------------
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
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernamePage.setVisibility(View.VISIBLE);
            }
        });

//        ----------------------------- ADD -----------------------------
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbhelper = new DatabaseHelper(MainActivity.this);
                StoreModel storeModel;


                try{
                    storeModel = new StoreModel(-1, et_item.getText().toString(), Integer.parseInt(et_quantity.getText().toString()));
                } catch (Exception e) {
                    storeModel = new StoreModel(-1, "error", 404);
                }

                dbhelper.addOne(storeModel);
                refresh();
                Toast.makeText(MainActivity.this, "ADDED", Toast.LENGTH_SHORT).show();
                addPage.setVisibility(View.GONE);
            }
        });

//        ----------------------------- SEARCH -----------------------------
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

//        ----------------------------- SEARCH -----------------------------
        btn_sortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrange = "ASC";
                refresh();
                sortPage.setVisibility(View.GONE);
            }
        });
        btn_sortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrange = "DESC";
                refresh();
                sortPage.setVisibility(View.GONE);
            }
        });
        btn_sortId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = "a";
                refresh();
                btn_sortId.setBackgroundColor(getResources().getColor(R.color.green));
                btn_sortItem.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sortQuantity.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });
        btn_sortItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = "b";
                refresh();
                btn_sortId.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sortItem.setBackgroundColor(getResources().getColor(R.color.green));
                btn_sortQuantity.setBackgroundColor(getResources().getColor(R.color.gray));
            }
        });
        btn_sortQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortBy = "c";
                refresh();
                btn_sortId.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sortItem.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_sortQuantity.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

//        ----------------------------- UPDATE -----------------------------
        final int[] id = new int[1];
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            StoreModel updateItem = (StoreModel) adapterView.getItemAtPosition(i);
            et_item.setText(String.valueOf(updateItem.getItem()));
            et_quantity.setText(String.valueOf(updateItem.getQuantity()));

            addPage.setVisibility(View.VISIBLE);
            id[0] = updateItem.getId();
        }
    });
    btn_update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            StoreModel updatedItem = new StoreModel(id[0], et_item.getText().toString(), Integer.parseInt(et_quantity.getText().toString()));
            dbhelper.update(updatedItem);
            addPage.setVisibility(View.GONE);
            refresh();
        }
    });

//        ----------------------------- DELETE -----------------------------
    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            StoreModel deleteItem = (StoreModel) adapterView.getItemAtPosition(i);
            dbhelper.deleteOne(deleteItem);

            refresh();
            return true;
        }
    });

    }
//        ----------------------------- VIEW -----------------------------
    private   void refresh(){
        if (sortBy.equals("a")) {
            arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dbhelper.getEveryone(arrange));
        }else if (sortBy.equals("b")){
            arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dbhelper.getItem(arrange));
        }else{
            arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dbhelper.getQuantity(arrange));
        }

        listView.setAdapter(arrayAdapter);
        tv_noData.setVisibility(arrayAdapter.isEmpty() ? View.VISIBLE : View.GONE );
    }

    private  void refreshUsername() {
        try {
            username = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dbhelper.getUsername());
            Toast.makeText(this, "HELOO NULL", Toast.LENGTH_SHORT).show();
            usernamePage.setVisibility(View.GONE);
        }catch (Exception e){
            usernamePage.setVisibility(View.VISIBLE);
            Toast.makeText(this, "EMPTYYY : ", Toast.LENGTH_SHORT).show();

        }
    }
}
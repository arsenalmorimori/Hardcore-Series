package com.pack.hardcore_02;

import android.database.sqlite.SQLiteDatabase;
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

public class MainActivity extends AppCompatActivity {

    EditText et_item, et_price;
    Button addList, btn_add, btn_delete, btn_update, btn_back;
    TextView tv_noData;

    ListView listView;

    LinearLayout addPage;

    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
    ArrayAdapter arrayAdapter;

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

        addList = findViewById(R.id.addList);
        btn_add = findViewById(R.id.btn_add);
        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);
        btn_back = findViewById(R.id.btn_back);

        listView = findViewById(R.id.listView);
        tv_noData = findViewById(R.id.tv_noData);
        refresh();

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
          addPage.setVisibility(View.VISIBLE);
          StoreModel storeModel;

          try {
              storeModel = new StoreModel(-1, et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
          }catch (Exception e){
              storeModel = new StoreModel(-1, "errorr", 404);
          }

          DatabaseHelper databaseHelper= new DatabaseHelper(MainActivity.this);
          boolean b = databaseHelper.addOne(storeModel);
          Toast.makeText(MainActivity.this, "ADD SUCCESS", Toast.LENGTH_SHORT).show();
          refresh();

          addPage.setVisibility(View.GONE);
      }
  });

//        ----------------- DELETE -----------------
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreModel deleteitem = (StoreModel) adapterView.getItemAtPosition(i);
                databaseHelper.deleteOne(deleteitem);

                refresh();
                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

//        ----------------- UPDATE -----------------
        final int[] idSelected = new int[1];
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreModel updateItem = (StoreModel) adapterView.getItemAtPosition(i);
                et_item.setText(String.valueOf(updateItem.getItem()));
                et_price.setText(String.valueOf(updateItem.getPrice()));

                idSelected[0] = updateItem.getId();
                addPage.setVisibility(View.VISIBLE);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreModel updateList = new StoreModel(idSelected[0], et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                boolean b = databaseHelper.updateOne(updateList);

                Toast.makeText(MainActivity.this, "UPDATED", Toast.LENGTH_SHORT).show();
                refresh();
                addPage.setVisibility(View.GONE);
            }
        });



    }

    //        ----------------- VIEW -----------------
    public void refresh(){
        arrayAdapter = new ArrayAdapter<StoreModel>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, databaseHelper.getEveryone());
        listView.setAdapter(arrayAdapter);
        tv_noData.setVisibility(arrayAdapter.isEmpty() ? View.VISIBLE: View.GONE);
    }
}
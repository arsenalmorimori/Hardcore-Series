package com.pack.hardcore_03_2;

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
    Button addList, btn_back, btn_add, btn_update;
    TextView tv_noData;

    ListView listView;
    LinearLayout addPage;

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

        btn_add=findViewById(R.id.btn_add);
        btn_back=findViewById(R.id.btn_back);
        btn_update=findViewById(R.id.btn_update);

        et_item=findViewById(R.id.et_item);
        et_price=findViewById(R.id.et_price);
        addList=findViewById(R.id.addList);
        tv_noData=findViewById(R.id.tv_noData);

        listView=findViewById(R.id.listView);
        addPage=findViewById(R.id.addPage);
        refresh();

//        ------------------ NAVIGATION ------------------
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

//        ------------------ ADD ------------------
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreModel storeModel;

                try {
                    storeModel = new StoreModel(-1, et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                }catch (Exception e){
                    storeModel = new StoreModel(-1,"error",404);
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                boolean b = databaseHelper.addOne(storeModel);
                Toast.makeText(MainActivity.this, "ADD SUCCESS", Toast.LENGTH_SHORT).show();
                addPage.setVisibility(View.GONE);

                refresh();
            }
        });

//        ------------------ DELETE ------------------
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreModel storeModel = (StoreModel) adapterView.getItemAtPosition(i);
                databaseHelper.deleteOne(storeModel);

                refresh();
                Toast.makeText(MainActivity.this, "DELETED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

//        ------------------ UPDATE ------------------
        final int[] id = new int[1];
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreModel updateItem = (StoreModel) adapterView.getItemAtPosition(i);

                et_item.setText(updateItem.getItem().toString());
                et_price.setText(String.valueOf(updateItem.getPrice()));

                id[0] = updateItem.getId();
                addPage.setVisibility(View.VISIBLE);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreModel updateItem = new StoreModel(id[0], et_item.getText().toString(), Integer.parseInt(et_price.getText().toString()));
                databaseHelper.updateOne(updateItem);

                addPage.setVisibility(View.GONE);
                refresh();
                Toast.makeText(MainActivity.this, "UPDATED", Toast.LENGTH_SHORT).show();
            }
        });


    }

//        ------------------ VIEW ------------------
    private void refresh(){
        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, databaseHelper.getEveryone());
        listView.setAdapter(arrayAdapter);

            tv_noData.setVisibility(arrayAdapter.isEmpty() ? View.VISIBLE: View.GONE);
    }

}
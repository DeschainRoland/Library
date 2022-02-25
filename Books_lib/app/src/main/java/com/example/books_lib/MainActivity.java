package com.example.books_lib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    ListView bookList;
    Button add,del;
    EditText bookName,bookAuthor, bookYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = findViewById(R.id.book_list);
        add=findViewById(R.id.add);
        del=findViewById(R.id.del);
        bookName =findViewById(R.id.name);
        bookAuthor=findViewById(R.id.author);
        bookYear =findViewById(R.id.year);

        //Подготовка данных
        LinkedList<Book> bookLinkedList=new LinkedList<>();
        bookLinkedList.add(new Book("Гарри Поттер","Роулинг",
                1892, R.drawable.book_one));
        bookLinkedList.add(new Book("Идиот","Достоевский",
                2365, R.drawable.book_two));
        bookLinkedList.add(new Book("Гиперболоид инженера Гарина",
                "А.Толстой", 5222, R.drawable.book_three));
        bookLinkedList.add(new Book("Роковые яйца","М.Булгаков",
                1231,R.drawable.book_four));
        bookLinkedList.add(new Book("Колобок","Народ",
                424, R.drawable.just_book));


        //Массив с ключами и идентификаторами
        String[]keyArray={"title","author","year","cover"};
        int [] idArray={R.id.book_title,R.id.author,R.id.year,R.id.cover};

        //Создание списка map для адаптеров
        LinkedList<HashMap<String,Object>> listForAdapter=new LinkedList<>();
        for (int i = 0; i < bookLinkedList.size(); i++) {
            HashMap<String,Object>bookMap=new HashMap<>();
            bookMap.put(keyArray[0],bookLinkedList.get(i).title);
            bookMap.put(keyArray[1],bookLinkedList.get(i).author);
            bookMap.put(keyArray[2],bookLinkedList.get(i).year);
            bookMap.put(keyArray[3],bookLinkedList.get(i).coverId);
            listForAdapter.add(bookMap);

        }
        //Создание адаптера
        //ArrayAdapter<Book>adapter=new ArrayAdapter<>(this,R.layout.list_item,bookLinkedList);

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,listForAdapter,R.layout.list_item,keyArray,idArray);

        bookList.setAdapter(simpleAdapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), bookLinkedList.get(i).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_name = bookName.getText().toString();
                String new_author = bookAuthor.getText().toString();
                int new_year = 0;
                new_year = Integer.parseInt(bookYear.getText().toString());
                if (new_year != 0) {
                    bookLinkedList.add(new Book(new_name, new_author, new_year, R.drawable.just_book));
                    HashMap<String, Object> bookMap = new HashMap<>();
                    bookMap.put(keyArray[0], new_name);
                    bookMap.put(keyArray[1], new_author);
                    bookMap.put(keyArray[2], new_year);
                    bookMap.put(keyArray[3], R.drawable.just_book);
                    listForAdapter.add(bookMap);

                    simpleAdapter.notifyDataSetChanged();
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1= bookName.getText().toString();
                String author1=bookAuthor.getText().toString();
                for (int i = 0; i < listForAdapter.size(); i++) {
                    if((name1+" "+author1).equals(bookLinkedList.get(i).toString())){
                        listForAdapter.remove(i);
                        break;
                    }
                }
                simpleAdapter.notifyDataSetChanged();
            }
        });

        simpleAdapter.notifyDataSetChanged();
    }
}
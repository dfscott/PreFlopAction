package com.poker.dscott.pfatrainer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.poker.dscott.pfatrainer.R;
import com.poker.dscott.pfatrainer.entity.Table;
import com.poker.dscott.pfatrainer.service.TableService;
import com.poker.dscott.pfatrainer.service.TableServiceImpl;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private Table table;
    private TableService tableService;

    public TableService getTableService() {
        if (tableService == null) {
            tableService = new TableServiceImpl();
        }
        return tableService;
    }

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createNewHand(View view) {

        table = getTableService().initializeTable();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(table.getTableStatus());
    }
}

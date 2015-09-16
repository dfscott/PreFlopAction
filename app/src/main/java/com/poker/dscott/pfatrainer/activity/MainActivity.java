package com.poker.dscott.pfatrainer.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.poker.dscott.pfatrainer.App;
import com.poker.dscott.pfatrainer.R;
import com.poker.dscott.pfatrainer.entity.AleoMagusStrategy;
import com.poker.dscott.pfatrainer.entity.FullTable;
import com.poker.dscott.pfatrainer.entity.Strategy;
import com.poker.dscott.pfatrainer.entity.Table;
import com.poker.dscott.pfatrainer.service.TableService;
import com.poker.dscott.pfatrainer.service.TableServiceImpl;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private Table table;
    private TableService tableService;
    private Strategy strategy;

    public TableService getTableService() {
        if (tableService == null) {
            tableService = new TableServiceImpl();
        }
        return tableService;
    }

    public static Context getContext() {
        return context;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
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

        Table table = new FullTable();
        getTableService().initializeTable(table);
        TextView textView = (TextView) findViewById(R.id.textView);
        getTableService().generateAction(table);
        String tableStatus = tableService.generateTableStatusMessage(table);
        tableStatus += tableService.generateActionMessage(table);

        Typeface font= Typeface.createFromAsset(getAssets(), "DejaVuSans.ttf");
        textView.setTypeface(font);

        textView.setText(Html.fromHtml(tableStatus));

        // move this to a settable area
        Strategy strategy = new AleoMagusStrategy(table);
        setStrategy(strategy);

    }

    public void foldClicked(View view) {

        String msg = App.getContext().getString(R.string.fold_clicked_message);
        DialogFragment newFragment = ActionResultDialog.newInstance(msg);
        newFragment.show(getFragmentManager(), "dialog");

    }

    public void callClicked(View view) {

        Strategy strategy = getStrategy();


        Context context = getApplicationContext();
        CharSequence text = "You should " + strategy.correctAction().toString();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void raiseClicked(View view) {

    }

    public void allInClicked(View view) {

    }


}

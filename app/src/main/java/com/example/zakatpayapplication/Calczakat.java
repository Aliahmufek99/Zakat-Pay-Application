package com.example.zakatpayapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;
import java.text.DecimalFormat;

public class Calczakat extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText gram, value;
    Button ButtonCalc, ButtonCancel;
    TextView result1, result2, result3;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    float goldWeight;
    float goldValue;

    SharedPreferences shared1;
    SharedPreferences shared2;
    private Menu menu;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calczakat);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        gram = (EditText) findViewById(R.id.WeightGold);
        value = (EditText) findViewById(R.id.CurrentGold);
        result1 = (TextView) findViewById(R.id.TotalGoldValue);
        result2 = (TextView) findViewById(R.id.ZakatPay);
        result3 = (TextView) findViewById(R.id.TotalZakat);
        ButtonCalc = (Button) findViewById(R.id.ButtonCalc);
        ButtonCancel = (Button) findViewById(R.id.ButtonCancel);

        ButtonCalc.setOnClickListener(this);
        ButtonCancel.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        shared1 = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        goldWeight = shared1.getFloat("weight", 0.0F);

        shared2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        goldValue = shared2.getFloat("value", 0.0F);

        gram.setText("" + goldWeight);
        value.setText("" + goldValue);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.menu, menu);

        return true;
    }

    //public boolean onOptionsItemSelected(MenuItem item) {
    //    switch (item.getItemId()) {
    //        case R.id.aboutpage:
    //            Intent intent = new Intent(this,aboutpage.class);
    //            startActivity(intent);
    //            break;
    //    }
    //   return super.onOptionsItemSelected(item);
    //}

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {

                case R.id.ButtonCalc:
                    calc();
                    break;


                case R.id.ButtonCancel:
                    gram.setText("");
                    value.setText("");
                    result1.setText("Total Gold Value: RM");
                    result2.setText("");
                    result3.setText("");
                    break;

            }
        } catch (java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Input Missing!", Toast.LENGTH_SHORT).show();

        } catch (Exception exp) {
            Toast.makeText(this, "Unknown Exception" + exp, Toast.LENGTH_SHORT).show();
        }
    }

    public void calc() {
        DecimalFormat df = new DecimalFormat("##.00");
        float goldWeight = Float.parseFloat(gram.getText().toString());
        float goldValue = Float.parseFloat(value.getText().toString());
        String stat = spinner.getSelectedItem().toString();
        double value, ruff, zakatpay, tZakat;

        SharedPreferences.Editor editor = shared1.edit();
        editor.putFloat("weight",goldWeight);
        editor.apply() ;

        SharedPreferences.Editor editor1 = shared2.edit();
        editor1.putFloat("value", goldValue);
        editor1.apply();


        if (stat.equals("Keep")) {
            value = goldWeight * goldValue;
            ruff = goldWeight - 85;

            if (ruff <= 0.0) {
                zakatpay = ruff + goldValue;
                tZakat = zakatpay * 0.025;
            } else {
                zakatpay = 0.0;
                tZakat = zakatpay * 0.025;
            }
            result1.setText("Total Gold Value: RM" + df.format(value));
            result2.setText("Zakat payable : RM" + df.format(zakatpay));
            result3.setText("Total Zakat : RM" + df.format(tZakat));
        }
        else {
            value = goldWeight * goldValue;
            ruff = goldWeight - 200;

            if (ruff >= 0.0) {
                zakatpay = ruff * goldValue;
                tZakat = zakatpay * 0.025;
            }
            else {
                zakatpay = 0.0;
                tZakat = zakatpay * 0.025;
            }
            result1.setText("Total Gold Value: RM" + df.format(value));
            result2.setText("Zakat payable : RM" + df.format(zakatpay));
            result3.setText("Total Zakat : RM" + df.format(tZakat));

        }
    }

}
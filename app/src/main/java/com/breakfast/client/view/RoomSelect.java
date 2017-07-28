package com.breakfast.client.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.ImageButton;
import android.widget.TextView;

import  java.io.Console;


import com.breakfast.client.R;

import rx.internal.schedulers.NewThreadScheduler;

/**
 * Created by admin on 2017/7/24.
 */

public class RoomSelect extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_consume);
        TextView txtRoomNumber =(TextView)findViewById(R.id.txtInput);
        TextView btnOne =(TextView)findViewById(R.id.one);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"1");
            }
        });
        TextView btnTwo =(TextView)findViewById(R.id.two);
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"2");
            }
        });
        TextView btnThree =(TextView)findViewById(R.id.three);
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"3");
            }
        });
        TextView btnFour =(TextView)findViewById(R.id.four);
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"4");
            }
        });
        TextView btnFive =(TextView)findViewById(R.id.five);
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"5");
            }
        });
        TextView btnSix =(TextView)findViewById(R.id.six);
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"6");
            }
        });
        TextView btnSeven =(TextView)findViewById(R.id.seven);
        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"7");
            }
        });
        TextView btnEight =(TextView)findViewById(R.id.eight);
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"8");
            }
        });
        TextView btnNine =(TextView)findViewById(R.id.nine);
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"9");
            }
        });
        TextView btnZero =(TextView)findViewById(R.id.zero);
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRoomNumber.setText(txtRoomNumber.getText()+"0");
            }
        });

        ImageButton btnDel=(ImageButton)findViewById(R.id.de);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (  txtRoomNumber.getText().length()>0  )
                {
                    txtRoomNumber.setText(txtRoomNumber.getText().subSequence(0,txtRoomNumber.getText().length()-1));
                }
            }
        });

        txtRoomNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 2) {
                    //do get
                }
            }
        });
       /* Toolbar toolbar = (Toolbar) findViewById(0);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_room_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public  void  onClick(View v)
    {


    }
}

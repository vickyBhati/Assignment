package com.example.assignment.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.R;
import com.example.assignment.Utility;


public class NoInternet extends AppCompatActivity implements View.OnClickListener {
    TextView retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        //overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

        retry = (TextView) findViewById(R.id.retry);
        retry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retry) {
            if (Utility.hasConnection(NoInternet.this)) {
                Intent intent = new Intent();
                setResult(2, intent);
//                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                finish();
            } else {
                Utility.showErrorToast(this, getString(R.string.still_not));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (Utility.hasConnection(NoInternet.this)) {
            Intent intent = new Intent();
            setResult(2, intent);
            //overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            finish();
            //finishing activity
        } else {
            Utility.showErrorToast(getApplicationContext(), getString(R.string.still_not));
        }
    }


}

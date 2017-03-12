package com.vinctor.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.Vinctor.VBind;
import com.Vinctor.VOnClick;

public class MainActivity extends AppCompatActivity {

    @VBind(R.id.text)
    TextView text;

    @VBind(R.id.text2)
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainActivity_VV_binder().bind(this);
        text.setText("success");
        text2.setText("success2");
    }

    @VOnClick(R.id.text)
    public void onClick() {
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
    }

    @VOnClick(R.id.text2)
    public void onClick2() {
        Toast.makeText(this, "test2", Toast.LENGTH_LONG).show();
    }
}

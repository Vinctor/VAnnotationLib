package com.vinctor.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.Vinctor.VBind;

//@VBind(1)
public class MainActivity extends AppCompatActivity {

    @VBind(R.id.text)
    TextView text;

    //    @VBind(0)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

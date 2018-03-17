package com.gugu.biom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class AddLocationActivity extends AppCompatActivity {

    EditText edtInputLocation;
    Button btnInputLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        edtInputLocation=(EditText)findViewById(R.id.edtInputLocation);
        btnInputLocation=(Button)findViewById(R.id.btnInputLocation);
    }
}

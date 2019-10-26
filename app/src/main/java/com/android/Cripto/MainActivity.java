package com.android.Cripto;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button create = findViewById(R.id.btn_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = findViewById(R.id.pt_user);
                String user = text.getText().toString();
                EditText text1 = findViewById(R.id.pt_password);
                String pwd = text1.getText().toString();
                boolean good = creaFile(user,pwd);
                TextView resul = findViewById(R.id.resultado);
                if (good) {
                    resul.setText("Guardado OK");
                } else {
                    resul.setText("No Guardado");
                }
            }
        });
    }

    public boolean creaFile(String txt1, String txt2) {
        String contentFile = creaContentFile(txt1,txt2);
        try
        {
            OutputStreamWriter fout= new OutputStreamWriter(
                    openFileOutput("users.xml", Context.MODE_PRIVATE));

            fout.write(contentFile);
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String creaContentFile(String user, String pwd) {
        String xmlContent = "";
        xmlContent = xmlContent + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xmlContent = xmlContent + "<content_file>\n";
        xmlContent = xmlContent + "    <data>\n";
        xmlContent = xmlContent + "        <time>" + dateActual() + "</time>\n";
        xmlContent = xmlContent + "        <usertext>" + user + "</usertext>\n";
        xmlContent = xmlContent + "        <pwdtext>" + pwd + "</pwdtext>\n";
        //  xmlContent = xmlContent + "        <cifredpwd>" + cifredPwd + "</cidredpwd>\n";
        xmlContent = xmlContent + "    </data>\n";
        xmlContent = xmlContent + "</content_file>";
        return xmlContent;
    }

    public String dateActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        return fecha;
    }
}

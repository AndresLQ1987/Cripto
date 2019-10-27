package com.android.Cripto;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
                String cifredPwd = CifrarPwd(pwd);
                boolean good = creaFile(user,pwd,cifredPwd);
                TextView resul = findViewById(R.id.resultado);
                if (good) {
                    File dir = getFilesDir();
                    resul.setText(dir.toString());
                } else {
                    resul.setText("No Guardado");
                }
            }
        });
    }

    private String CifrarPwd(String pwd) {
        String cifred = "";
        try {
            RSA rsa = new RSA();
            rsa.setContext(getBaseContext());
            rsa.genKeyPair(1024);
            rsa.saveToDiskPrivateKey("rsa.pi");
            rsa.saveToDiskPublicKey("rsa.pb");
            cifred = rsa.Encrypt(pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return cifred;
    }

    public boolean creaFile(String txt1, String txt2, String txt3) {
        String contentFile = creaContentFile(txt1,txt2,txt3);
        FileOutputStream archivo;
        try
        {
            archivo = openFileOutput("user.txt",MODE_PRIVATE);
            archivo.write(contentFile.getBytes());
            archivo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String creaContentFile(String user, String pwd, String cifredPwd) {
        String xmlContent = "";
        xmlContent = xmlContent + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        xmlContent = xmlContent + "<content_file>\n";
        xmlContent = xmlContent + "    <data>\n";
        xmlContent = xmlContent + "        <time>" + dateActual() + "</time>\n";
        xmlContent = xmlContent + "        <usertext>" + user + "</usertext>\n";
        xmlContent = xmlContent + "        <pwdtext>" + pwd + "</pwdtext>\n";
        xmlContent = xmlContent + "        <cifredpwd>" + cifredPwd + "</cidredpwd>\n";
        xmlContent = xmlContent + "    </data>\n";
        xmlContent = xmlContent + "</content_file>";
        return xmlContent;
    }

    public String dateActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}

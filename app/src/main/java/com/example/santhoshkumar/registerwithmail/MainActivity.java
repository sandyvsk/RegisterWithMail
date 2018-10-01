package com.example.santhoshkumar.registerwithmail;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText tnid, usrname, addrs, num, bkname;
    Button submit;
    String TempTxnid, TempUsername, TempAddr, TempNum, TempBkname;

    String ServerURL = "server_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tnid = (EditText)findViewById(R.id.tnsid);
        usrname = (EditText)findViewById(R.id.uname);
        addrs = (EditText)findViewById(R.id.addr);
        num = (EditText)findViewById(R.id.no);
        bkname = (EditText)findViewById(R.id.bname);

        submit = (Button)findViewById(R.id.button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();

                InsertData(TempTxnid, TempUsername, TempAddr, TempNum, TempBkname);
            }
        });
    }


    private void GetData() {


        TempTxnid = tnid.getText().toString();
        TempUsername = usrname.getText().toString();
        TempAddr = addrs.getText().toString();
        TempNum = num.getText().toString();
        TempBkname = bkname.getText().toString();


    }

    private void InsertData(final String transactionid, final String username, final String address, final String phonenum, final String bookname) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String TranscationHolder = transactionid ;

                String UsernameHolder = username;

                String AddrHolder = address;

                String PhoneHolder = phonenum;

                String BkHolder = bookname;

                Log.d("tnxid", TranscationHolder);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("transactionid", TranscationHolder));

                nameValuePairs.add(new BasicNameValuePair("username", UsernameHolder));

                nameValuePairs.add(new BasicNameValuePair("address", AddrHolder));

                nameValuePairs.add(new BasicNameValuePair("phonenum", PhoneHolder));

                nameValuePairs.add(new BasicNameValuePair("bookname", BkHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Ordered Sucessfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(MainActivity.this, "Ordered Successfully", Toast.LENGTH_LONG).show();


            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(transactionid);
    }

}

package com.san47.asynctask;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView text;
    ProgressBar pb;
    Button mulai;
    private TextView mTextView;
    private static final String TEXT_STATE = "currentText";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView1);

        text =(TextView)findViewById(R.id.textView);
        mulai=(Button)findViewById(R.id.btn_mulai);
        pb=(ProgressBar)findViewById(R.id.progressBar);
        if(savedInstanceState!=null) {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
        mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText("napping");
                new testAsync().execute("");
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE,
                mTextView.getText().toString());
    }
    public class testAsync extends AsyncTask<String, Integer, String> {
        private int SLEEP_TIME;
        @Override
        protected void onPreExecute(){
            //menjalankan perintah sebelum eksekusi
            pb.setVisibility(View.VISIBLE);
            text.setTextColor(Color.WHITE);
            text.setText("Memulai sesuai perintah...");
            super.onPreExecute();
            Random r = new Random();
            int n = r.nextInt(11);
            SLEEP_TIME = n * 100;
            pb.setMax(SLEEP_TIME);
            pb.setProgress(0);
        }
        @Override
        protected String doInBackground(String... params) {
            int n = SLEEP_TIME/100;
            try {
                for (int i = 1; i <= n; i++){
                    Thread.sleep(100);
                    publishProgress(i*100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Awake at last after sleeping for " + SLEEP_TIME + " milliseconds!";
        }
        @Override
        protected void onProgressUpdate(Integer... Values){
            //monitoring progress eksekusi untuk dikirim ke UI
            super.onProgressUpdate(Values);
            mTextView.setText("Sleeping for " + Values[0] + " miliseconds...");
            text.setText(String.valueOf(Values[0]));
            pb.incrementProgressBy(100);
        }
        @Override
        protected void onPostExecute(String s){
            //hasil eksekusi ditampilkan di sini
            super.onPostExecute(s);
            if(s!=null && s!=""){
                text.setText(s);
            }
        }
    }
}



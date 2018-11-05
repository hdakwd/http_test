package pkg.json.http_test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {
    URL url;
    URLConnection connection; //add test
    TextView textView;
    String line;
    StringBuilder html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btClick = findViewById(R.id.btClick);
        Clicklistener listener = new Clicklistener();
        btClick.setOnClickListener(listener);
    }


    private class Clicklistener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EditText input = findViewById(R.id.url);
                        textView = findViewById(R.id.result);
                        String name = input.getText().toString();
                        url = new URL(name);
                        connection = url.openConnection();

                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                        html = new StringBuilder();

                        while ((line = in.readLine()) != null) {
                            html.append(line);
                        }

                        //textView.setText(html.toString());
                        Log.d("HTTP", html.toString());
                    }catch (MalformedURLException e) {
                        e.printStackTrace();
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

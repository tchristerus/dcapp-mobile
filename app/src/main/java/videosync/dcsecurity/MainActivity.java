package videosync.dcsecurity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import videosync.dcsecurity.Utils.RestHandler;
import videosync.dcsecurity.Utils.ToastUtil;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText staffnr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.staffnr = (EditText)findViewById(R.id.staffnr);

        this.button = (Button)findViewById(R.id.btn_login);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText(getString(R.string.btn_login_active));

                if (staffnr.getText().length() > 0) {
                    String staffName = RestHandler.getLocationName(Integer.parseInt(String.valueOf(staffnr.getText())));
                    if (staffName != null) {
                        ToastUtil.shortToast(getApplicationContext(), staffName);

                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(intent);
                    } else {
                        ToastUtil.shortToast(getApplicationContext(), getString(R.string.error_unknown_pers_nr));
                    }
                }else{
                    ToastUtil.shortToast(getApplicationContext(), getString(R.string.error_no_pers_nr));
                }

                button.setText(getString(R.string.btn_login));
            }
        });
    }
}

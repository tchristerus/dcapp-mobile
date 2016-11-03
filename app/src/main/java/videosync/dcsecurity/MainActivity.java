package videosync.dcsecurity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                    int staffNumber = Integer.parseInt(staffnr.getText().toString());
                    boolean staffName = RestHandler.staffExists(staffNumber);
                    if (staffName) {
                        ToastUtil.shortToast(getApplicationContext(), getString(R.string.login_success));

                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        intent.putExtra("staffNr", staffNumber);
                        startActivity(intent);
                        finish();
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

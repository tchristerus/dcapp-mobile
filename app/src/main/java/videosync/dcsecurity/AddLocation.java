package videosync.dcsecurity;

import android.content.Intent;
import android.support.test.internal.runner.TestExecutor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import videosync.dcsecurity.Utils.RestHandler;
import videosync.dcsecurity.Utils.ToastUtil;

public class AddLocation extends AppCompatActivity {
    TextView lblLocation;
    EditText txtParticularities;
    Button btnsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        this.lblLocation = (TextView)findViewById(R.id.txt_location);
        this.btnsend = (Button)findViewById(R.id.btn_send);
        this.txtParticularities = (EditText)findViewById(R.id.txt_particularities);
        this.lblLocation.setText(getIntent().getExtras().getString("locationName"));

        this.btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int locationId = getIntent().getExtras().getInt("locationId");
                int staffId = getIntent().getExtras().getInt("staffNr");
                String particularities = txtParticularities.getText().toString();
                if(RestHandler.addEntry(locationId, staffId, particularities)){
                    ToastUtil.longToast(getApplicationContext(), getString(R.string.new_record_success));
                    Intent newIntent = new Intent(getApplicationContext(), MainMenu.class);
                    newIntent.putExtra("staffNr", staffId);
                    startActivity(newIntent);
                }else{
                    ToastUtil.longToast(getApplicationContext(), getString(R.string.new_record_fail));
                }
            }
        });
    }
}

package videosync.dcsecurity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import videosync.dcsecurity.Utils.RestHandler;
import videosync.dcsecurity.Utils.ToastUtil;

public class MainMenu extends AppCompatActivity {

    Button btnScan;
    Button btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.btnScan = (Button)findViewById(R.id.btn_scan);
        this.btnlogout = (Button)findViewById(R.id.btn_logout);
        this.btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        this.btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                boolean activityExists = intent.resolveActivityInfo(getPackageManager(), 0) != null;

                if(activityExists) {
                    intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                }else{
                    String appPackageName = "com.google.zxing.client.android";
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    ToastUtil.shortToast(getApplicationContext(),"Barcode app not installed");
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                try {
                    JSONObject jsonObject = new JSONObject((contents));
                    System.out.println("LocationID: " + jsonObject.getInt("loc_id"));
                    String locName = RestHandler.getLocationName(jsonObject.getInt("loc_id"));
                    if(locName != null) {
                        Intent newIntent = new Intent(getApplicationContext(), AddLocation.class);
                        newIntent.putExtra("locationId", jsonObject.getInt("loc_id"));
                        newIntent.putExtra("locationName", locName);
                        newIntent.putExtra("staffNr", getIntent().getExtras().getInt("staffNr"));
                        startActivity(newIntent);
                        ToastUtil.shortToast(getApplicationContext(), locName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else
            if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
}

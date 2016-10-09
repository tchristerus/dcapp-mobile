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

import videosync.dcsecurity.Utils.ToastUtil;

public class MainMenu extends AppCompatActivity {

    Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.btnScan = (Button)findViewById(R.id.btn_scan);
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

                ToastUtil.shortToast(getApplicationContext(),contents);
            }
            else
            if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
    }
}

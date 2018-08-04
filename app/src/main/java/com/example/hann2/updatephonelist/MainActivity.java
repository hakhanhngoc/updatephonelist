package com.example.hann2.updatephonelist;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import adapter.CustomAdapter;
import module.CommonFunction;
import module.Contact;

public class MainActivity extends AppCompatActivity {
    private ListView lv_contact;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private final String TAG=getClass().getSimpleName();
    CustomAdapter customAdapter;
    CommonFunction commonFunction;
    final Context context =this;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_contact= (ListView) findViewById(R.id.lv_contact);
        commonFunction=new CommonFunction();
        //Lấy thông tin khách hàng.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            Log.e(TAG,"Không Có quyền truy cập danh bạ");
            //After this point you wait for calback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
        else {

            ArrayList<Contact> arrayList = commonFunction.getInfoContact(this);
            customAdapter=new CustomAdapter(this, R.layout.row_item_contact,arrayList);
            lv_contact.setAdapter(customAdapter);
        }

        Button btn_2_NewNumber = (Button) findViewById(R.id.btn_2_NewNumber);
        btn_2_NewNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonFunction.ShowAlertDialogNewNumber(context,lv_contact);
            }
        });

        Button btn_2_OldNumber = (Button) findViewById(R.id.btn_2_OldNumber);
        btn_2_OldNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonFunction.ShowAlertDialogOldNumber(context,lv_contact);
            }
        });




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                Log.e(TAG,"Có quyền");
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Không Có quyền aaa");
            }
        }
    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */

    public void download(){
        progress=new ProgressDialog(this);
        progress.setMessage("Cập nhật danh bạ ...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    }
                    catch (InterruptedException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
}

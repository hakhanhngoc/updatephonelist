package module;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hann2.updatephonelist.MainActivity;
import com.example.hann2.updatephonelist.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.CustomAdapter;

public class CommonFunction {
    public CommonFunction(){

    }

    public String Convert2NewNumber(String strNumber) {
        //Loại bỏ kí tự khoảng trắng nếu có
        strNumber= strNumber.replaceAll(" ","");
        //Toast.makeText(MainActivity.this,"Số thuê bao cần chuẩn hóa là: " +strNumber, Toast.LENGTH_LONG).show();
        String frefixNum="";
        //Biểu thức chính quy cho các đầu số di động trong nước cần phải đổi mã mạng
        String strRgxMobileAll = "^(01|\\+841|00841)[0-9]{8,9}$";
        Pattern patternMobileAll = Pattern.compile(strRgxMobileAll);
        Matcher matcherMobileAll = patternMobileAll.matcher(strNumber);

        //Nếu không phải là tập số cần thay đổi thì trả về luôn
        if (!matcherMobileAll.matches()) {
            //  Toast.makeText(MainActivity.this,"Đầu số không cần chuyển đổi: " +strNumber, Toast.LENGTH_LONG).show();
            return   strNumber;
        }
        //Chuyển toàn bộ số thuê bao theo định dạng 1xxxxxxxxx
        if (strNumber.substring(0, 4).equals("+841")) {
            frefixNum=strNumber.substring(0, 3);
            strNumber = strNumber.substring(3);

            //Toast.makeText(MainActivity.this,"Số bị cắt +841: " +strNumber, Toast.LENGTH_LONG).show();
        }
        if (strNumber.substring(0, 5).equals("00841")) {
            frefixNum=strNumber.substring(0, 4);
            strNumber = strNumber.substring(4);

            //Toast.makeText(MainActivity.this,"Số bị cắt 00841: " +strNumber, Toast.LENGTH_LONG).show();
        }

        if (strNumber.substring(0, 2).equals("01")) {
            frefixNum=strNumber.substring(0, 1);
            strNumber = strNumber.substring(1);

            //Toast.makeText(MainActivity.this,"Số bị cắt 01: " +strNumber, Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(MainActivity.this,"Kết quả chuẩn hóa lần 1: " +strNumber, Toast.LENGTH_LONG).show();

        //Xử lý chuyển đầu số cho Viettel 16xxxxxxxx => 3xxxxxxxx

        if (strNumber.substring(0,2).equals("16")) {
            //Thêm đầu số 3 sau đó nối 8 kí tự cuối cùng vào thành số mới để trả về
            //Log.e(TAG, "Đầu số Viettel được chuẩn hóa: "+"3" + strNumber.substring(strNumber.length() - 8, strNumber.length()));
            return   frefixNum + "3" + strNumber.substring(strNumber.length() - 8, strNumber.length());

        }
        //Toast.makeText(MainActivity.this,"Không phải số Viettel" +strNumber, Toast.LENGTH_LONG).show();
        //Xử lý chuyển đầu số cho các mạng còn lại
        /*
         * */
        switch (strNumber.substring(0, 3)) {
            //Mobifone
            case "120":
                strNumber = "70" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "121":
                strNumber = "79" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "122":
                strNumber = "77" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "126":
                strNumber = "76" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "128":
                strNumber = "78" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            //Vina
            case "123":
                strNumber = "83" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "124":
                strNumber = "84" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "125":
                strNumber = "85" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "127":
                strNumber = "81" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "129":
                strNumber = "82" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            //VietNamobile
            case "186":
                strNumber = "56" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "188":
                strNumber = "58" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            //Gmobile
            case "199":
                strNumber = "59" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            default:
                strNumber = frefixNum+strNumber;
        }
        //Log.e(TAG,"Đầu số sau khi được chuẩn hóa");
        return   frefixNum+strNumber;
    }

    public String Convert2OldNumber(String strNumber) {
        //Loại bỏ kí tự khoảng trắng nếu có
        strNumber= strNumber.replaceAll(" ","");
        //Toast.makeText(MainActivity.this,"Số thuê bao cần chuẩn hóa là: " +strNumber, Toast.LENGTH_LONG).show();
        String frefixNum="";
        //Biểu thức chính quy cho các đầu số di động trong nước cần phải đổi mã mạng
        String strRgxMobileAll = "^(03|\\+843|00843||05|\\+845|00845||07|\\+847|00847||08|\\+848|00848)[0-9]{8,9}$";
        Pattern patternMobileAll = Pattern.compile(strRgxMobileAll);
        Matcher matcherMobileAll = patternMobileAll.matcher(strNumber);

        //Nếu không phải là tập số cần thay đổi thì trả về luôn
        if (!matcherMobileAll.matches()) {
            //  Toast.makeText(MainActivity.this,"Đầu số không cần chuyển đổi: " +strNumber, Toast.LENGTH_LONG).show();
            return   strNumber;
        }
        //Chuyển toàn bộ số thuê bao theo định dạng 1xxxxxxxxx
        if (strNumber.substring(0, 3).equals("+84")) {
            frefixNum=strNumber.substring(0, 3);
            strNumber = strNumber.substring(3);

            //Toast.makeText(MainActivity.this,"Số bị cắt +841: " +strNumber, Toast.LENGTH_LONG).show();
        }
        if (strNumber.substring(0, 4).equals("0084")) {
            frefixNum=strNumber.substring(0, 4);
            strNumber = strNumber.substring(4);

            //Toast.makeText(MainActivity.this,"Số bị cắt 00841: " +strNumber, Toast.LENGTH_LONG).show();
        }

        if (strNumber.substring(0, 1).equals("0")) {
            frefixNum=strNumber.substring(0, 1);
            strNumber = strNumber.substring(1);

            //Toast.makeText(MainActivity.this,"Số bị cắt 01: " +strNumber, Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(MainActivity.this,"Kết quả chuẩn hóa lần 1: " +strNumber, Toast.LENGTH_LONG).show();

        //Xử lý chuyển đầu số cho Viettel 16xxxxxxxx => 3xxxxxxxx

        if (strNumber.substring(0,1).equals("3")) {
            //Thêm đầu số 3 sau đó nối 8 kí tự cuối cùng vào thành số mới để trả về
            //Log.e(TAG, "Đầu số Viettel được chuẩn hóa: "+"3" + strNumber.substring(strNumber.length() - 8, strNumber.length()));
            return   frefixNum + "16" + strNumber.substring(strNumber.length() - 8, strNumber.length());

        }
        //Toast.makeText(MainActivity.this,"Không phải số Viettel" +strNumber, Toast.LENGTH_LONG).show();
        //Xử lý chuyển đầu số cho các mạng còn lại
        /*
         * */
        switch (strNumber.substring(0, 2)) {
            //Mobifone
            case "70":
                strNumber = "120" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "79":
                strNumber = "121" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "77":
                strNumber = "122" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "76":
                strNumber = "126" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "78":
                strNumber = "128" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            //Vina
            case "83":
                strNumber = "123" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "84":
                strNumber = "124" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "85":
                strNumber = "125" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "81":
                strNumber = "127" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "82":
                strNumber = "129" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            //VietNamobile
            case "56":
                strNumber = "186" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            case "58":
                strNumber = "188" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            //Gmobile
            case "59":
                strNumber = "199" + strNumber.substring(strNumber.length() - 7, strNumber.length());
                break;
            default:
                strNumber = frefixNum+strNumber;
        }
        //Log.e(TAG,"Đầu số sau khi được chuẩn hóa");
        return   frefixNum+strNumber;
    }

    public String TelcoName(String strNumber) {
        //Loại bỏ kí tự khoảng trắng nếu có
        strNumber= strNumber.replaceAll(" ","");
        strNumber= strNumber.replaceAll("\\(","");
        strNumber= strNumber.replaceAll("\\)","");
        strNumber= strNumber.replaceAll("-","");
        String frefixNum="";
        String telco="";
        //Biểu thức chính quy cho các đầu số di động trong nước
        String strRgxMobileAll = "^(01|09|\\+841|00841|\\+849|00849|03|05|07|08|\\+843|\\+845|\\+847|\\+848|00843|00845|00847||00848)[0-9]{8,9}$";
        Pattern patternMobileAll = Pattern.compile(strRgxMobileAll);
        Matcher matcherMobileAll = patternMobileAll.matcher(strNumber);

        //Nếu không phải là tập số cần thay đổi thì trả về luôn
        if (!matcherMobileAll.matches()) {
            //Toast.makeText(MainActivity.this,"Đầu số không cần chuyển đổi: " +strNumber, Toast.LENGTH_LONG).show();
            //Log.d(TAG,"Trả lại luôn telco không xác định:" +strNumber);
            return   "Other";
        }
        //Chuyển toàn bộ số thuê bao theo định dạng 1xxxxxxxxx hoặc 9xxxxxxxx hoặc 8xxxxxxxx
        if (strNumber.substring(0, 4).equals("+841")) {
            frefixNum=strNumber.substring(0, 3);
            strNumber = strNumber.substring(3);

            //Toast.makeText(MainActivity.this,"Số bị cắt +841: " +strNumber, Toast.LENGTH_LONG).show();
        }
        if (strNumber.substring(0, 5).equals("00841")) {
            frefixNum=strNumber.substring(0, 4);
            strNumber = strNumber.substring(4);

            //Toast.makeText(MainActivity.this,"Số bị cắt 00841: " +strNumber, Toast.LENGTH_LONG).show();
        }

        if (strNumber.substring(0, 2).equals("01")||strNumber.substring(0, 2).equals("09")) {
            frefixNum=strNumber.substring(0, 1);
            strNumber = strNumber.substring(1);

            //Toast.makeText(MainActivity.this,"Số bị cắt 01: " +strNumber, Toast.LENGTH_LONG).show();
        }
        /* Đầu số 9, 8*/
        //Log.d(TAG,"Đầu 2 số cũ:"+strNumber+" Cắt đầu số:" +strNumber.substring(0, 2));
        switch (strNumber.substring(0, 2)) {
            //Mobifone
            case "86":
                telco="Viettel";
                break;
            case "96":
                telco="Viettel";
                break;
            case "97":
                telco="Viettel";
                break;
            case "98":
                telco="Viettel";
                break;
            case "16":
                telco="Viettel";
                break;
            case "88":
                telco="VinaPhone";
                break;
            //Vina
            case "91":
                telco="VinaPhone";
                break;
            case "94":
                telco="VinaPhone";
                break;
            case "89":
                telco="Mobifone";
                break;
            case "90":
                telco="Mobifone";
                break;
            case "93":
                telco="Mobifone";
                break;
            //VietNamobile
            case "92":
                telco="Vietnammobie ";
                break;
            case "99":
                telco="GMobile";
                break;
        }
        if (!telco.equals("")){
            return telco;
        }

        //Log.d(TAG,"Đầu 3 số cũ:"+strNumber+" Cắt đầu số:"  +strNumber.substring(0, 3));
        switch (strNumber.substring(0, 3)) {
            //Mobifone
            case "120":
                telco="Mobifone";
                break;
            case "121":
                telco="Mobifone";
                break;
            case "122":
                telco="Mobifone";
                break;
            case "126":
                telco="Mobifone";
                break;
            case "128":
                telco="Mobifone";
                break;
            //Vina
            case "123":
                telco="VinaPhone";
                break;
            case "124":
                telco="VinaPhone";
                break;
            case "125":
                telco="VinaPhone";
                break;
            case "127":
                telco="VinaPhone";
                break;
            case "129":
                telco="VinaPhone";
                break;
            //VietNamobile
            case "186":
                telco="VietNamobile";
                break;
            case "188":
                telco="VietNamobile";
                break;
            //Gmobile
            case "199":
                telco="Gmobile";
                break;

        }
        if (!telco.equals("")){
            return telco;
        }

        //Xét đầu số nhà mạng nếu đã chuyển sang đầu số mới
        //Log.d(TAG,"Đầu 2 số mới:"+strNumber+" Cắt đầu số:"  +strNumber.substring(0, 2));
        switch (strNumber.substring(0, 2)) {
            //Mobifone
            case "70":
                telco="Mobifone";
                break;
            case "79":
                telco="Mobifone";
                break;
            case "77":
                telco="Mobifone";
                break;
            case "76":
                telco="Mobifone";
                break;
            case "78":
                telco="Mobifone";
                break;
            //Vina
            case "83":
                telco="VinaPhone";
                break;
            case "84":
                telco="VinaPhone";
                break;
            case "85":
                telco="VinaPhone";
                break;
            case "81":
                telco="VinaPhone";
                break;
            case "82":
                telco="VinaPhone";
                break;
            //VietNamobile
            case "56":
                telco="VietNamobile";
                break;
            case "58":
                telco="VietNamobile";
                break;
            //Gmobile
            case "59":
                telco="Gmobile";
                break;
        }

        //Log.e(TAG,"Đầu số sau khi được chuẩn hóa");
        if (telco.equals("")){
            //Log.d(TAG,"Other cuối cùng");
            return "Other";
        }
        return   telco;
    }
    public ArrayList<Contact> getInfoContact (Context context) {
        ArrayList<Contact> arrayList = new ArrayList<>();
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            //Log.d(TAG,"Show:"+ phoneNumber);
            Contact contactModel = new Contact(Color.BLUE,name,phoneNumber);
            arrayList.add(contactModel);
        }
        phones.close();
        // Get the ContentResolver

        return arrayList;
    }

    public static boolean updateContact(Context context, String name, String newPhoneNumber){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        String where = ContactsContract.Data.DISPLAY_NAME + " = ? AND " +
                ContactsContract.Data.MIMETYPE + " = ? AND " +
                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE) + " = ? ";
        //Toast.makeText(context,where, Toast.LENGTH_LONG).show();

        String[] params = new String[] {name,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)};
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, params)
                .withValue(ContactsContract.CommonDataKinds.Phone.DATA, newPhoneNumber)
                .build());
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            //Toast.makeText(context,"Cập nhật thành công", Toast.LENGTH_LONG).show();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateContactList2New(Context context){
//        ArrayList<Contact> arrayList = new ArrayList<>();
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        final int tmp = 0;
        final int counter = phones.getCount();

        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            CommonFunction commonFunction =new CommonFunction();
            updateContact(context,name,commonFunction.Convert2NewNumber(phoneNumber));
            //Toast.makeText(context, "Khách hàng "+name+" .Chuyển đổi số " +phoneNumber +" sang số "+commonFunction.Convert2NewNumber(phoneNumber), Toast.LENGTH_SHORT).show();
            //Log.d(TAG,"Show:"+ phoneNumber);
        }
        phones.close();
        //download();
    }
    public void updateContactList2Old(Context context, ListView lv ){
        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");

        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            CommonFunction commonFunction =new CommonFunction();
            updateContact(context,name,commonFunction.Convert2OldNumber(phoneNumber));
            //Toast.makeText(context, "Khách hàng "+name+" .Chuyển đổi số " +phoneNumber +" sang số "+commonFunction.Convert2OldNumber(phoneNumber), Toast.LENGTH_SHORT).show();
            //Log.d(TAG,"Show:"+ phoneNumber);
        }
        phones.close();
    }

    public void ShowAlertDialogNewNumber(final Context context,final ListView lv){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chuyển đổi 11 số về 10 số");
        builder.setMessage("Bạn có khồng ý chuyển đổi không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateContactList2New(context);
                ArrayList<Contact> arrayList1 = getInfoContact(context);
                CustomAdapter customAdapter;
                customAdapter = new CustomAdapter(context, R.layout.row_item_contact,arrayList1);
                lv.setAdapter(customAdapter);
                Toast.makeText(context,"Chuyển sang số mới thành công", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
    public void ShowAlertDialogOldNumber(final Context context,final ListView lv){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chuyển đổi 11 sang 10 số");
        builder.setMessage("Bạn có khồng ý chuyển đổi không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                updateContactList2Old(context,lv);
                ArrayList<Contact> arrayList1 = getInfoContact(context);
                CustomAdapter customAdapter;
                customAdapter = new CustomAdapter(context, R.layout.row_item_contact,arrayList1);
                lv.setAdapter(customAdapter);
                Toast.makeText(context,"Chuyển sang số mới thành công", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}

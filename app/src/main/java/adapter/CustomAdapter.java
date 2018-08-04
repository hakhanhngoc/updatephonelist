package adapter;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hann2.updatephonelist.MainActivity;
import com.example.hann2.updatephonelist.R;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import module.CommonFunction;
import module.Contact;

public class CustomAdapter extends ArrayAdapter <Contact>   {
    private final String TAG=getClass().getSimpleName();
    private Context context;
    private int resoure;
    private ArrayList<Contact> arrayContact;

    public CustomAdapter(Context context,int resouce, ArrayList<Contact> objects){
        super(context,resouce,objects);
        this.context=context;
        this.resoure=resouce;
        this.arrayContact=objects;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHorder viewHorder;
        if (convertView==null) {
            viewHorder =new ViewHorder();
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item_contact, parent, false);
            viewHorder.tv_color = convertView.findViewById(R.id.tv_color);
            viewHorder.tv_number = convertView.findViewById(R.id.tv_number);
            viewHorder.tv_name = convertView.findViewById(R.id.tv_name);
            viewHorder.tv_telco = convertView.findViewById(R.id.tv_telco);
            //TextView tv_number_new=convertView.findViewById(R.id.tv_number_new);
            convertView.setTag(viewHorder);
        }else {
            viewHorder=(ViewHorder) convertView.getTag();
        }

        Contact contact =arrayContact.get(position);
        viewHorder.tv_color.setBackgroundColor(contact.getmColor());
        viewHorder.tv_color.setText(contact.getmName().toString().substring(0,1).toUpperCase());

        CommonFunction commonFunction = new CommonFunction();
        viewHorder.tv_telco.setText(commonFunction.TelcoName(contact.getmNumber()));
        viewHorder.tv_name.setText(contact.getmName());

        if (!contact.getmNumber().replaceAll(" ","").equals(commonFunction.Convert2NewNumber(contact.getmNumber()))) {
            viewHorder.tv_number.setText("(Số cũ):   " + contact.getmNumber() +
                    "\n(Số mới): " + commonFunction.Convert2NewNumber(contact.getmNumber().toString())+
                    "\n(chuyển lại): " + commonFunction.Convert2OldNumber(commonFunction.Convert2NewNumber(contact.getmNumber().toString()))
            );
            //Log.d(TAG,"(Số cũ):   " + contact.getmNumber().replaceAll(" ","") + "\n(Số mới): " + Convert2NewNumber(contact.getmNumber().toString()));
        } else {

            viewHorder.tv_number.setText("(Số cũ):   " + contact.getmNumber() + "\n(Số mới): Không thay đổi" );
        }
        return convertView;
    }

    public class ViewHorder {
        TextView tv_color;
        TextView tv_number;
        TextView tv_name;
        TextView tv_telco;
    }
}

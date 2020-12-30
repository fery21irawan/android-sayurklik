package com.sayurklik.app.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class SayurKlikHelper {
    Context context;
    public SayurKlikHelper(Context context){
        this.context = context;
    }
    public void chatToWhatsapp(){
        PackageManager pm=context.getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "YOUR TEXT HERE";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }
    public void callToWhatsApp(String number) {
        String url = "https://api.whatsapp.com/send?phone=" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
    public String rupiah(int money){
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat df = (DecimalFormat)nf;
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        String duit = df.format(money);
        return "Rp. " + duit;
    }
    public String rupiah(String money){
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        DecimalFormat df = (DecimalFormat)nf;
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        String duit;
        if(money.contains("null")){
            duit = df.format(Integer.parseInt("0"));
        }else{
            duit = df.format(Integer.parseInt(money));
        }
        return "Rp. " + duit;
    }
}

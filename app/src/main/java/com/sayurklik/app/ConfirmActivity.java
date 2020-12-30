package com.sayurklik.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.sayurklik.app.helpers.AuthHelper;
import com.sayurklik.app.items.OrderItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class ConfirmActivity extends AppCompatActivity {
    ImageView imageConfirm;
    TextView tvOrderId;
    OrderItem orderItem;
    File fileConfirm;
    Button btnUploadPembayaran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        orderItem = getIntent().getParcelableExtra(consts.PARCELABLE);
        imageConfirm = findViewById(R.id.image_confirm);
        tvOrderId = findViewById(R.id.tv_order_id);
        tvOrderId.setText("Order id : " + orderItem.getOrderId());
        btnUploadPembayaran = findViewById(R.id.btn_upload_pembayaran);
        btnUploadPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        openCamera();
    }
    private void openCamera(){
        Pix.start(this, 100, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        if(requestCode == 100){
            if (resultCode == Activity.RESULT_OK) {
                String imagePath = data.getStringArrayListExtra(Pix.IMAGE_RESULTS).get(0);
                File file = new File(imagePath);
                Bitmap bitmap = new BitmapDrawable(this.getResources(), file.getAbsolutePath()).getBitmap();
                imageConfirm.setImageBitmap(bitmap);
                fileConfirm = file;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(this, 100, 1);
                } else {
                    Toast.makeText(this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    public String documentFiles(File file){
        String imagedata = null;
        String filePath = file.toString();
        try {
            JSONObject document = new JSONObject();
            String ext = filePath.substring(filePath.lastIndexOf("."));
            String base64 = encodeFileToBase64Binary(filePath);
            document.put("filename", generateFileName());
            document.put("document", base64);
            document.put("extension", ext);
            imagedata = document.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return imagedata;
    }
    private static String generateFileName(){
        int MAX_LENGTH = 25;
        String ALLOWED_CHARACTERS ="0123456789abcdefghijklmnopqrstuvwxyz";
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(MAX_LENGTH);
        for(int i=0;i<MAX_LENGTH;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
    public String encodeFileToBase64Binary(String path){
        File file = new File(path);
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedfile;
    }

    private void upload(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
        AndroidNetworking.post(consts.CONFIRM)
                .setPriority(Priority.MEDIUM)
                .addHeaders(new AuthHelper(this).basicAuth())
                .addUrlEncodeFormBodyParameter(consts.DATA, documentFiles(fileConfirm))
                .addUrlEncodeFormBodyParameter(consts.ORDER_ID, orderItem.getOrderId())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = Boolean.parseBoolean(response.getString(consts.STATUS));
                            if(status){
                                Intent intent = new Intent(ConfirmActivity.this, ProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Toast.makeText(ConfirmActivity.this, response.getString(consts.MESSAGE), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
    }
}

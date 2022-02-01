package com.tellioglu.asrandroid;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.encore.eftpos.D10Pos;
import com.tellioglu.asrandroid.databinding.ActivityMainBinding;

import java.math.BigInteger;
import java.net.InetAddress;

import apiresources.D10Response;

public class MainActivity extends AppCompatActivity {

    D10Pos d10Pos = new D10Pos();
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        try {
            Context context = getApplicationContext();
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            byte[] ipAddress = BigInteger.valueOf(wm.getConnectionInfo().getIpAddress()).toByteArray();

            InetAddress inetAddress = InetAddress.getByAddress(ipAddress);
            String ip = inetAddress.getHostAddress();
           // String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            binding.ipTextView.setText(ip);
            d10Pos.init();
        }
        catch (Exception e){
            Toast.makeText(this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void pay(View view){
      D10Response response =  d10Pos.messagges.StartAdvancedPayment("1000");
      if(response!=null)
          Toast.makeText(this,"Cevap kodu" +response.cevapKodu,Toast.LENGTH_LONG).show();
      else
          Toast.makeText(this,"Response is null",Toast.LENGTH_LONG).show();


    }
}
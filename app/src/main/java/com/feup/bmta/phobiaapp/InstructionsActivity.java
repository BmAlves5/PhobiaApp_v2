package com.feup.bmta.phobiaapp;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.feup.bmta.phobiaapp.BluetoothService;
import com.feup.bmta.phobiaapp.R;
import com.feup.bmta.phobiaapp.SpiderGameActivity;

import Bio.Library.namespace.BioLib;

public class InstructionsActivity extends AppCompatActivity {

    private BluetoothService bluetoothService;

    private BluetoothDevice deviceToConnect;

    private String address = "";
    private BioLib lib = null;

    private TextView text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        address = "00:23:FE:00:0B:33";

        // Inicialize sua instância de BluetoothService aqui (substitua com sua lógica de inicialização)
        bluetoothService = new BluetoothService();

        Button startTestButton = (Button) findViewById(R.id.connectVitalJacket);
        startTestButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Connect();
            }

            /***
             * Connect to device.
             */
            public void Connect()
            {
                try
                {
                    deviceToConnect =  lib.mBluetoothAdapter.getRemoteDevice(address);



                    text.setText("");
                    lib.Connect(address, 5);
                } catch (Exception e)
                {
                    text.setText("");
                    e.printStackTrace();
                }
            }

        });
    }




}
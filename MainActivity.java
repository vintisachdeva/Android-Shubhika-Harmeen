package com.bmpl.bluetoothapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{

    Button visibleButton, pairedDevices;
    ListView listView;
    Switch aSwitch;
    ArrayAdapter arrayAdapter;

    // bluetooth turn on or off--> if on then set default functionality
    // initialize device discovery,
    BluetoothAdapter bluetoothAdapter;

    Set<BluetoothDevice> bluetoothDeviceSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initialize();

    }


    public void initialize()
    {
        visibleButton = (Button)findViewById(R.id.visibleButton);
        pairedDevices = (Button)findViewById(R.id.pairedButton);
        listView = (ListView) findViewById(R.id.listView);
        aSwitch = (Switch) findViewById(R.id.bluetoothOn);

        aSwitch.setOnCheckedChangeListener(this);
        visibleButton.setOnClickListener(this);
        pairedDevices.setOnClickListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
    {
        if(isChecked){
            if(bluetoothAdapter == null){
                Toast.makeText(MainActivity.this, "Bluetooth not supported", Toast.LENGTH_LONG).show();
            }
            else if(!bluetoothAdapter.isEnabled())
            {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Turned On", Toast.LENGTH_LONG).show();

            }
        }

        else {
            if(bluetoothAdapter.isEnabled())
            {
                bluetoothAdapter.disable();
                Toast.makeText(MainActivity.this, "Turned Off", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.visibleButton:

                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3000);
                startActivity(intent);

                break;

            case R.id.pairedButton:

                bluetoothDeviceSet = bluetoothAdapter.getBondedDevices();
                ArrayList arrayList = new ArrayList();

                for(BluetoothDevice device: bluetoothDeviceSet)
                {
                    arrayList.add(device.getName());
                }

                arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayList);
                listView.setAdapter(arrayAdapter);

                break;

        }

    }
}

package net.iotinn.batterylevelindicator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView batteryLevel;
    private TextView voltageLevel;
    private TextView temperatureLevel;
    private TextView chargingStatus;
    private TextView chargingType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        batteryLevel = (TextView)findViewById(R.id.battery_level);
        voltageLevel = (TextView)findViewById(R.id.voltage_level);
        temperatureLevel = (TextView)findViewById(R.id.temperature_level);
        chargingStatus = (TextView)findViewById(R.id.chargingStatus);
        chargingType = (TextView)findViewById(R.id.chargingType);

        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }


    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context c, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = intent.getIntExtra("temperature", 0);

            // Are we charging / charged?
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;


            // How are we charging?
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;



            batteryLevel.setText("Battery Status: " + String.valueOf(level) + "%");
            voltageLevel.setText("Battery Voltage: " + String.valueOf(voltage));
            double temps = (double)temperature / 10;
            temperatureLevel.setText("Battery Temperature: " + String.valueOf(temps));
            chargingStatus.setText("Charging Status: " + String.valueOf(isCharging));

            if (usbCharge) {
                chargingType.setText("Charging Status: " + "USB");
            }else if(acCharge){
                chargingType.setText("Charging Status: " + "AC");
            }else{
                chargingType.setText("Charging Status: No Charging");
            }

        }
    };

}

package com.betterwifisignal.betterwifisignal;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class SearchWifi {

    private WifiManager wifiManager;
    private List<WifiElementModel> nets; //create a network to maintain the parameters to obtain
    private List<ScanResult> wifiList;
    private Context context;

    public SearchWifi(Context applicationContext) {
        this.context = applicationContext;
    }

    private boolean isExistsSSDI(String SSDI) {
        boolean isExists = false;
        if (!nets.isEmpty()) {
            for (WifiElementModel wifiElement : nets) {
                if (wifiElement.getSSDI().equals(SSDI)) {
                    isExists = true;
                }
            }
        }
        return isExists;
    }

    List<WifiElementModel> scanWifi() {
        wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        wifiList = wifiManager.getScanResults();

        this.nets = new ArrayList<WifiElementModel>();//khoi tao mang nets voi so phan tu = so phan tu cua mang wifiList.
        for (ScanResult itemList : wifiList) {
            String item = itemList.toString();
            int level = WifiManager.calculateSignalLevel(itemList.level, 10);
            Log.d("fffffffff", item.toString());

            String[] mang_item = item.split(",");//Corta la cadena recibida y la guarda en la matriz de elementos.
            String item_ssid = mang_item[0];// Get the string "SSID: wifi name"
            String item_capabilities = mang_item[3]; // Get the string "SSID: wifi name"
            item_ssid = item_ssid.replace("SSID: ", "");

            int strengthint;
            if (itemList.level >= -50) {
                strengthint = 4;
            } else if (itemList.level >= -60) {
                strengthint = 3;
            } else if (itemList.level >= -75) {
                strengthint = 2;
            } else if (itemList.level >= -70) {
                strengthint = 1;
            } else {
                strengthint = 0;
            }

            if (!item_ssid.trim().isEmpty()) {
                if (isExistsSSDI(item_ssid) == false) {
                    nets.add(new WifiElementModel(itemList.SSID, itemList.frequency, itemList.level, strengthint));
                }
            }
        }

        return nets;

    }

    public String getConnectionSSID() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if ((wifiInfo != null) && (wifiInfo.getSSID() != null)) {
            return wifiInfo.getSSID();
        } else {
            return null;
        }
    }

    public int getConnectionStrength() {
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if ((wifiInfo != null) && (wifiInfo.getSSID() != null)) return wifiInfo.getRssi();
        return 0;
    }

    void connectWifi(WifiElementModel wifiElement) {
        wifiManager.disconnect();
        String networkSSID = wifiElement.getSSDI();
        String networkPass = wifiElement.getPass();
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", networkSSID);
        wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);

        wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        //remember id
        int netId = wifiManager.addNetwork(wifiConfig);

        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
    }

    public boolean isRegisteredWifi(String SSID) {
        boolean isRegistered = false;
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (isExistsSSDI(SSID)) {
            for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                if (SSID.equals(wifiConfiguration.SSID.replace("\"", ""))) {
                    isRegistered = true;
                }

            }
        }
        return isRegistered;
    }

}

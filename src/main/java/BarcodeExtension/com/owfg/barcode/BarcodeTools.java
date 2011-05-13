package com.owfg.barcode;

import net.rim.device.api.script.Scriptable;

public final class BarcodeTools extends Scriptable {
    public static final String FIELD_SCAN = "scan";

    private Scan _callScan;

    public BarcodeTools() {
        _callScan = new Scan();
    }

    public Object getField(String name) throws Exception {
        if(name.equals(FIELD_SCAN)) {
            return this._callScan;
        }

        return super.getField(name);
    }
}

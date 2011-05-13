package sample;

import net.rim.device.api.script.Scriptable;
import net.rim.device.api.system.Alert;
public final class AlertSample extends Scriptable {
    public static final String FIELD_VIBRATE_SUPPORTED = "vibrateSupported";
    public static final String FIELD_VIBRATE = "vibrate";
    private Vibrate _callVibrate;
    public AlertSample() {
        _callVibrate = new Vibrate();
    }
    public Object getField(String name) throws Exception {
        if (name.equals(FIELD_VIBRATE_SUPPORTED)) {
            return new Boolean(Alert.isVibrateSupported());
        } else if (name.equals(FIELD_VIBRATE)) {
            return this._callVibrate;
        }
        return super.getField(name);
    }
}

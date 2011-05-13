package sample;

import net.rim.device.api.script.ScriptableFunction;
import net.rim.device.api.system.Alert;
public final class Vibrate extends ScriptableFunction {
    public Object invoke(Object obj, Object[] args) throws Exception {
        if (!Alert.isVibrateSupported()) {
            return UNDEFINED;
        } if (args.length == 1) {
            int duration = ((Integer)args[0]).intValue();
            Alert.startVibrate(duration);
        }
        return UNDEFINED;
    }
}


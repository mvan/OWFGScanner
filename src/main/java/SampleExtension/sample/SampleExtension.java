package sample;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.script.ScriptEngine;
import net.rim.device.api.web.WidgetConfig;
import net.rim.device.api.web.WidgetExtension;
import org.w3c.dom.Document;

public final class SampleExtension implements WidgetExtension {
    String _widgetNameForFutureUse;
    BrowserField _browserFieldForFutureUse;
    public String[] getFeatureList() {
        String[] result = new String[1];
        result[0] = "sample.alert";
        return result;
    }
    public void loadFeature(String feature, String version,
            Document doc, ScriptEngine scriptEngine) throws Exception {
        if (feature.equals("sample.alert")) {
            scriptEngine.addExtension("sample.alert", new AlertSample());
        }
    }
    public void register(WidgetConfig widgetConfig,
            BrowserField browserField) {
        _widgetNameForFutureUse = widgetConfig.getName();
        _browserFieldForFutureUse = browserField;
    }
    public void unloadFeatures(Document doc) {
    }
}


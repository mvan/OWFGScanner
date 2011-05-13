package com.owfg.barcode;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.script.ScriptEngine;
import net.rim.device.api.web.WidgetConfig;
import net.rim.device.api.web.WidgetExtension;
import org.w3c.dom.Document;

public final class BarcodeExtension implements WidgetExtension {
    String _widgetName;
    BrowserField _browserField;

    /**
     * The application calls the getFeatureList() when the JavaScript® extension is created. Define the
     * getFeatureList() method to provide a full list of feature IDs that the BlackBerry WebWorks application
     * can provide extensions for. This method is called when the BlackBerry WebWorks Packager parses the
     * config.xml file that requires the feature
     */
    public String[] getFeatureList() {
        String[] result = new String[1];

        result[0] = "barcode.scanner";

        return result;
    }

    /**
     * The loadFeature() method is called when the BlackBerry WebWorks application loads a resource that
     * requires a feature ID that you supplied in getFeatureList(). Define this method to allow your application to
     * add your extension to the script engine.
     */
    public void loadFeature(String feature, String version, Document doc, ScriptEngine scriptEngine) throws Exception {
        if (feature.equals("barcode.scanner")) {
            scriptEngine.addExtension("barcode.scanner", new BarcodeTools());
        }
    }

    /**
     * The register() method provides access to the configuration document or BrowserField object, if required.
     * Define this method to be called when the BrowserField object is initialized.
     */
    public void register(WidgetConfig widgetConfig, BrowserField browserField) {
        _widgetName = widgetConfig.getName();
        _browserField = browserField;
    }

    /**
     * Define theunloadFeatures() method to perform any clean up when your extension is unloaded.
     */
    public void unloadFeatures(Document doc) {
        
    }
}

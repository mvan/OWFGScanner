package com.owfg.webservice;

import com.owfg.stub.Banner;
import com.owfg.stub.Store;
import com.owfg.stub.StoreManagementInfo;
import com.owfg.stub.ItemSalesHistoryInfo;
import com.owfg.stub.StoreManagement_Stub;


import net.rim.device.api.script.ScriptableFunction;


/**
* Class which manages the web service.
* <p>
* The WebService uses the generated stub files then sets the
* target server address in the stub classes from the properties file
* (to be implemented)   
*
* @author Warren Voelkl
**/
public class WebService  {
	private static StoreManagement_Stub stub = null;
	private static long storeId = 0;
    private static String address;
    private static String user;
    private static String pass;
	public WebService(String address, String user, String password) {
		try {
			if (stub == null) {
				stub = new StoreManagement_Stub();
                setAddress(address);
                setUser(user);
                setPass(password);
			}
		} catch (Exception ex) {
			Logger.logSevereErrorEvent("WebService(): " + ex);
		}
	}
	
	/**
	* Gets a list of the stores from server
	* <p>
	* Currently this function uses the stub to contact a server
	* on a failure it will throw an exception currently this is
	* disabled for testing purposes.
	*
    * @author Warren Voelkl
	* @return a list of stores containing name, id and bannerId
	**/
	public Result getStores(ScriptableFunction success, ScriptableFunction error) throws Exception {
		Store[] storesResponse = null;
		String[] str = null;
		if (stub == null) {
			str = new String[1];
			str[0] = new String("Stub is null");
            return new Result(error, str);
        }
		try {
            Logger.logDebugEvent("WebService() stub.GetActiveStores()");
			storesResponse = stub.getActiveStores();
		} catch (Exception e) {
			Logger.logSevereErrorEvent("WebService.getStores(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrive Stores from: " + address);
            return new Result(error, str);
		}

		str = new String[storesResponse.length];
		for (int i = 0; i != storesResponse.length; i++) {
			str[i] = new String(storesResponse[i].getStoreId() + " " 
					+ ((storesResponse[i].getStoreName() == null) 
							? "" : storesResponse[i].getStoreName()));
		}
        String[][] arrayOfStrings = new String[1][storesResponse.length];
        arrayOfStrings[0] = str;
        return new Result(success, arrayOfStrings);
	}
	
	/**
	* Gets a list of the Banners from server
	* <p>
	* Currently this function uses the stub to contact a server
	* on a failure it will throw an exception currently this is
	* disabled for testing purposes.
	*
	* @return a list of store banners;
	* @author Warren Voelkl
	**/
	public Result getBanners(ScriptableFunction success, ScriptableFunction error) throws Exception {
        Banner[] banners = null;
		String[] str = null;
		if (stub == null) {
			str = new String[1];
			str[0] = new String("Stub is null");
            return new Result(error, str);
		}
		try {
            Logger.logDebugEvent("WebService.getBanners stub.getBanners(): ");
			banners = stub.getBanners();
		} catch (Exception e) {
			Logger.logSevereErrorEvent("WebService.getBanners(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrieve Banners");
            return new Result(error, str);
		}
		str = new String[banners.length];
		for (int i = 0; i != banners.length; i++) {
			str[i] = new String(banners[i].getBannerId() + " " 
					+ ((banners[i].getBannerName() == null) 
							? "" : banners[i].getBannerName()));
		}
        String[][] arrayOfStrings = new String[1][banners.length];
        arrayOfStrings[0] = str;
        return new Result(success, arrayOfStrings);
	}
	
	/**
	* Gets a store info object from server
	* <p>
	* Currently this function uses the stub to contact a server
	* on a failure it will throw an exception currently this exception
	* returns bogus data for testing purposes.
	* 
	* @return the info object containing all information of an object
	* @author Warren Voelkl
	**/	
	public Result getInfo(ScriptableFunction success, ScriptableFunction error, String string) throws Exception {
        Logger.logDebugEvent("Info");
		StoreManagementInfo pi = null;
		String[] str = null;
		ProductInfo info = null;
		if (stub == null) {
			str = new String[1];
			str[0] = new String("Stub is null");
            return new Result(error, str);
		}
		try {
            Logger.logDebugEvent("getInfo() stub.getStoreManagementInfo()");
			pi = stub.getStoreManagementInfo(storeId, string);
		} catch (Exception e) {
			Logger.logSevereErrorEvent("WebService.getInfo(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrive product info");
            return new Result(error, str);
		}
        info = new ProductInfo(pi);
        return new Result(success, info.toArray());
	}

    /**
	* Gets a store info object from server
	* <p>
	* Currently this function uses the stub to contact a server
	* on a failure it will throw an exception currently this exception
	* returns bogus data for testing purposes.
	*
	* @return the info object containing all information of an object
	* @author Warren Voelkl
	**/
	public Result getHistory(ScriptableFunction success, ScriptableFunction error, String string) throws Exception {
        Logger.logDebugEvent("history");
		ItemSalesHistoryInfo[] history = null;
		String[] str = null;
		ProductHistory hInfo = null;
		if (stub == null) {
			str = new String[1];
			str[0] = new String("Stub is null");
            return new Result(error, str);
		}
		try {
            Logger.logDebugEvent("getHistory() stub.getItemSalesHistoryInfo()");
			history = stub.getItemSalesHistory(storeId, string);
		} catch (Exception e) {
			Logger.logSevereErrorEvent("WebService.getHistory(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrive product info");
            return new Result(error, str);
		}
        hInfo = new ProductHistory(history);
        return new Result(success, hInfo.toArray());
	}

    public void setStore (ScriptableFunction success, ScriptableFunction error, Long id) throws Exception {
        storeId = id.longValue();
        try {
        success.invoke(success, null);
        } catch (Exception e) {
            Logger.logSevereErrorEvent("WebService.getInfo.Invoke success: " + e);
        }
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        stub._setProperty(StoreManagement_Stub.ENDPOINT_ADDRESS_PROPERTY, address);
        this.address = address;
        Logger.logDebugEvent("WebService.setAddress: " + address);
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        stub._setProperty(StoreManagement_Stub.USERNAME_PROPERTY, user);
        this.user = user;
        Logger.logDebugEvent("WebService.setAddress: " + user);
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        stub._setProperty(StoreManagement_Stub.PASSWORD_PROPERTY, pass);
        this.pass = pass;
        Logger.logDebugEvent("WebService.setAddress: " + pass);
    }




     protected int dayOfWeek;
    protected Double forecast;
    protected Boolean onPromo;
    protected Double promoSales;
    protected Double regularSales;
}


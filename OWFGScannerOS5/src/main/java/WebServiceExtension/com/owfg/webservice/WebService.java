package com.owfg.webservice;

import com.owfg.stub.Banner;
import com.owfg.stub.Store;
import com.owfg.stub.StoreManagement_Stub;
import com.owfg.stub.StoreManagementInfo;

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
				//stub._setProperty(StoreManagementImpl_Stub.ENDPOINT_ADDRESS_PROPERTY, address);
                //this.address = address;
                //this.user = user;
                //this.pass = password;
				//TODO uncomment when server side is setup
				stub._setProperty(StoreManagement_Stub.USERNAME_PROPERTY, user);
				stub._setProperty(StoreManagement_Stub.PASSWORD_PROPERTY, password);
			}
		} catch (Exception ex) {
			Logger.logSevereErrorEvent("WebService(): " + ex);
			return;
		}
	}
	
	/**
	* Gets a list of the stores from server
	* <p>
	* Currently this function uses the stub to contact a server
	* on a failure it will throw an exception currently this is
	* disabled for testing purposes.
	*
	* @return a list of stores containing name, id and bannerId
	* @author Warren Voelkl
	**/
	public void getStores(ScriptableFunction success, ScriptableFunction error) throws Exception {

		Store[] storesResponse = null;
		String[] str = null;
		if (stub == null) {
			str = new String[1];
			str[0] = new String("Stub is null");
            try {
			    error.invoke(error, str);
            } catch (Exception e) {
                //TODO: Handle this somehow
            }
			throw new Exception("Null Pointer Exception");
		}		
		try {
            Logger.logDebugEvent("WebService() stub.GetActiveStores()");
			storesResponse = stub.getActiveStores();

		} catch (Exception e) {
			//TODO
			Logger.logSevereErrorEvent("WebService.getStores(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrive Stores from: " + address);
            try {
			    error.invoke(error, str);
            } catch (Exception er) {
                Logger.logSevereErrorEvent("WebService.getStores.Invoke error: " + e);
            }
		}
        
		str = new String[storesResponse.length];
		for (int i = 0; i != storesResponse.length; i++) {
			str[i] = new String(storesResponse[i].getStoreId() + " " 
					+ ((storesResponse[i].getStoreName() == null) 
							? "" : storesResponse[i].getStoreName()));
		}
        String[][] arrayOfStrings = new String[1][storesResponse.length];
        arrayOfStrings[0] = str;
        try {
		    success.invoke(success, arrayOfStrings);
        } catch (Exception e) {
            Logger.logSevereErrorEvent("WebService.getStores.Invoke success: " + e);
        }
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
	public void getBanners(ScriptableFunction success, ScriptableFunction error) throws Exception {
		Banner[] banners = null;
		String[] str = null;
		if (stub == null) {
			str = new String[1];
			str[0] = new String("Stub is null");
            try {
			    error.invoke(error, str);
            } catch (Exception e) {
                Logger.logSevereErrorEvent("WebService.getBanners.Invoke error: " + e);
            }
			throw new Exception("Null Pointer Exception");
		}
		try {
            Logger.logDebugEvent("WebService.getBanners stub.getBanners(): ");
			banners = stub.getBanners();
		} catch (Exception e) {
			Logger.logSevereErrorEvent("WebService.getBanners(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrieve Banners");
            try {
			    error.invoke(error, str);
            } catch (Exception er) {
                Logger.logSevereErrorEvent("WebService.getBanners.Invoke error: " + e);
            }
			throw e;
		}
		str = new String[banners.length];
		for (int i = 0; i != banners.length; i++) {
			str[i] = new String(banners[i].getBannerId() + " " 
					+ ((banners[i].getBannerName() == null) 
							? "" : banners[i].getBannerName()));
		}
        String[][] arrayOfStrings = new String[1][banners.length];
        arrayOfStrings[0] = str;
        try {
		    success.invoke(success, arrayOfStrings);
        } catch (Exception e) {
            Logger.logSevereErrorEvent("WebService.getBanners.Invoke error: " + e);
        }
	}
	
	/**
	* Gets a list of the Banners from server
	* <p>
	* Currently this function uses the stub to contact a server
	* on a failure it will throw an exception currently this exception
	* returns bogus data for testing purposes.
	* 
	* @return the info object containing all information of an object
	* @author Warren Voelkl
	**/	
	public void getInfo(ScriptableFunction success, ScriptableFunction error, String string) throws Exception {
		Logger.logDebugEvent("Info");
		StoreManagementInfo pi = null;
		String[] str = null;
		ProductInfo info = null;
		if (stub == null) {
			str = new String[1];
			str[0] = new String("Stub is null");
            try {
			    error.invoke(error, str);
            } catch (Exception e) {
                //TODO: Handle this somehow
            }
			throw new Exception("Null Pointer Exception");
		}
		try {
            Logger.logDebugEvent("getInfo() stub.getStoreManagementInfo()");
			pi = stub.getStoreManagementInfo(storeId, string);
		} catch (Exception e) {
			//TODO
			//Logger.logSevereErrorEvent("WebService.getStores(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrive product info");
			try {
                error.invoke(error, str);
            } catch (Exception er) {
                Logger.logSevereErrorEvent("WebService.getInfo.Invoke error: " + e);
            }
			throw e;
		}
          info = new ProductInfo(pi);
        try {
		    success.invoke(success, info.toArray());
        } catch (Exception e) {
            Logger.logSevereErrorEvent("WebService.getInfo.Invoke success: " + e);
        }
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
}


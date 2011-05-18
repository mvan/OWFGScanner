package com.owfg.webservice;

import com.owfg.stub.Banner;
import com.owfg.stub.Store;
import com.owfg.stub.StoreManagementImpl_Stub;
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
	private static StoreManagementImpl_Stub stub = null;
	
	public WebService(String address, String user, String password) {
		Logger.logDebugEvent("WebService(): constructor");
		try {
			if (stub == null) {
				stub = new StoreManagementImpl_Stub();
				stub._setProperty(StoreManagementImpl_Stub.ENDPOINT_ADDRESS_PROPERTY, address);
				//TODO uncomment when server side is setup
				//stub._setProperty(StoreManagementImpl_Stub.USERNAME_PROPERTY, user);
				//stub._setProperty(StoreManagementImpl_Stub.PASSWORD_PROPERTY, password);
			}
		} catch (Exception ex) {
			//TODO
			//Logger.logSevereErrorEvent("WebService(): " + ex);
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
		Logger.logDebugEvent("WebService(): getStores");
		Store[] storesResponse = null;
		String[] str = null;
		if (stub == null) {
			Logger.logSevereErrorEvent("WebService.getStores(): stub is null");
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
			storesResponse = stub.getActiveStores();
		} catch (Exception e) {
			//TODO
			Logger.logSevereErrorEvent("WebService.getStores(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrive Stores");
            try {
			    error.invoke(error, str);
            } catch (Exception er) {
                //TODO: Handle this somehow
            }
		}
        
		str = new String[storesResponse.length];
		for (int i = 0; i != storesResponse.length; i++) {
			str[i] = new String(storesResponse[i].getStoreId() + " " 
					+ ((storesResponse[i].getStoreName() == null) 
							? "" : storesResponse[i].getStoreName()));
		}
         
        try {
		    success.invoke(success, str);
        } catch (Exception e) {
            //TODO: Handle this somehow...
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
                //TODO: Handle this somehow
            }
			throw new Exception("Null Pointer Exception");
		}
		try {
			banners = stub.getBanners();
		} catch (Exception e) {
			//TODO
			//Logger.logSevereErrorEvent("WebService.getStores(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrive Banners");
            try {
			    error.invoke(error, str);
            } catch (Exception er) {
                //TODO: Handle this somehow...
            }
			throw e;
		}
		str = new String[banners.length];
		for (int i = 0; i != banners.length; i++) {
			str[i] = new String(banners[i].getBannerId() + " " 
					+ ((banners[i].getBannerName() == null) 
							? "" : banners[i].getBannerName()));
		}	
        try {
		    success.invoke(success, str);
        } catch (Exception e) {
            //TODO: Handle this somehow...
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
		Logger.logDebugEvent("blah");
		StoreManagementInfo info = null;
		String[] str = null;
		ProductInfo[] pi = new ProductInfo[1];
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
			info = stub.getStoreManagementInfo(1,string);
		} catch (Exception e) {
			//TODO
			//Logger.logSevereErrorEvent("WebService.getStores(): " + e);
			str = new String[1];
			str[0] = new String("Unable to retrive product info");
			try {
                error.invoke(error, str);
            } catch (Exception er) {
                //TODO: Handle this somehow...
            }
			throw e;
		}
		pi[0] = new ProductInfo(info);
        try {
		    success.invoke(success, pi);
        } catch (Exception e) {
            //TODO: Handle this somehow...
        }
	}	
}


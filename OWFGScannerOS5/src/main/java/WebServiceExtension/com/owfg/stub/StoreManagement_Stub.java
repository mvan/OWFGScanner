// This class was generated by 172 StubGenerator.
// Contents subject to change without notice.
// @generated

package WebServiceExtension.com.owfg.stub;

import javax.xml.rpc.JAXRPCException;
import javax.xml.namespace.QName;
import javax.microedition.xml.rpc.Operation;
import javax.microedition.xml.rpc.Type;
import javax.microedition.xml.rpc.ComplexType;
import javax.microedition.xml.rpc.Element;

public class StoreManagement_Stub implements WebServiceExtension.com.owfg.stub.StoreManagement, javax.xml.rpc.Stub {
	private String[] _propertyNames;
	private Object[] _propertyValues;

	public StoreManagement_Stub() {
		_propertyNames = new String[] {ENDPOINT_ADDRESS_PROPERTY};
		_propertyValues = new Object[] {"https://simdv1.owfg.com:8443/caos/StoreManagement"};
	}

	public void _setProperty(String name, Object value) {
		int size = _propertyNames.length;
		for (int i = 0; i < size; ++i) {
			if (_propertyNames[i].equals(name)) {
				_propertyValues[i] = value;
				return;
			}
		}
		// Need to expand our array for a new property
		String[] newPropNames = new String[size + 1];
		System.arraycopy(_propertyNames, 0, newPropNames, 0, size);
		_propertyNames = newPropNames;
		Object[] newPropValues = new Object[size + 1];
		System.arraycopy(_propertyValues, 0, newPropValues, 0, size);
		_propertyValues = newPropValues;

		_propertyNames[size] = name;
		_propertyValues[size] = value;
	}

	public Object _getProperty(String name) {
		for (int i = 0; i < _propertyNames.length; ++i) {
			if (_propertyNames[i].equals(name)) {
				return _propertyValues[i];
			}
		}
		if (ENDPOINT_ADDRESS_PROPERTY.equals(name) || USERNAME_PROPERTY.equals(name) || PASSWORD_PROPERTY.equals(name)) {
			return null;
		}
		if (SESSION_MAINTAIN_PROPERTY.equals(name)) {
			return new java.lang.Boolean(false);
		}
		throw new JAXRPCException("Stub does not recognize property: "+name);
	}

	protected void _prepOperation(Operation op) {
		for (int i = 0; i < _propertyNames.length; ++i) {
			op.setProperty(_propertyNames[i], _propertyValues[i].toString());
		}
	}

	// 
	//  Begin user methods
	// 

	public WebServiceExtension.com.owfg.stub.Store[] getActiveStores() throws java.rmi.RemoteException {
		// Copy the incoming values into an Object array if needed.
		Object[] inputObject = new Object[0];

		Operation op = Operation.newInstance(_qname_getActiveStores, _type_getActiveStores, _type_getActiveStoresResponse);
		_prepOperation(op);
		op.setProperty(Operation.SOAPACTION_URI_PROPERTY, "");
		Object resultObj;
		try {
			resultObj = op.invoke(inputObject);
		} catch (JAXRPCException e) {
			Throwable cause = e.getLinkedCause();
			if (cause instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) cause;
			}
			throw e;
		}
		WebServiceExtension.com.owfg.stub.Store[] result;
		// Convert the result into the right Java type.
		// Unwrapped return value
		Object[] storesObj = (Object[]) ((Object[])resultObj)[0];
		if (storesObj == null) {
			result = null;
		} else {
			int storeArraySize = storesObj.length;
			result = new WebServiceExtension.com.owfg.stub.Store[storeArraySize];
			for (int storeArrayIndex = 0; 
				storeArrayIndex < storeArraySize; ++storeArrayIndex) {
				if (storesObj[storeArrayIndex] == null) {
					result[storeArrayIndex] = null;
				} else {
					result[storeArrayIndex] = new WebServiceExtension.com.owfg.stub.Store();
					long a_long;
					Object bannerIdObj = ((Object[])storesObj[storeArrayIndex])[0];
					a_long = ((java.lang.Long)bannerIdObj).longValue();
					result[storeArrayIndex].setBannerId(a_long);
					long a_long2;
					Object storeIdObj = ((Object[])storesObj[storeArrayIndex])[1];
					a_long2 = ((java.lang.Long)storeIdObj).longValue();
					result[storeArrayIndex].setStoreId(a_long2);
					java.lang.String string;
					Object storeNameObj = ((Object[])storesObj[storeArrayIndex])[2];
					string = (java.lang.String)storeNameObj;
					result[storeArrayIndex].setStoreName(string);
				}
			}
		}
		return result;
	}

	public WebServiceExtension.com.owfg.stub.Banner[] getBanners() throws java.rmi.RemoteException {
		// Copy the incoming values into an Object array if needed.
		Object[] inputObject = new Object[0];

		Operation op = Operation.newInstance(_qname_getBanners, _type_getBanners, _type_getBannersResponse);
		_prepOperation(op);
		op.setProperty(Operation.SOAPACTION_URI_PROPERTY, "");
		Object resultObj;
		try {
			resultObj = op.invoke(inputObject);
		} catch (JAXRPCException e) {
			Throwable cause = e.getLinkedCause();
			if (cause instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) cause;
			}
			throw e;
		}
		WebServiceExtension.com.owfg.stub.Banner[] result;
		// Convert the result into the right Java type.
		// Unwrapped return value
		Object[] bannersObj = (Object[]) ((Object[])resultObj)[0];
		if (bannersObj == null) {
			result = null;
		} else {
			int bannerArraySize = bannersObj.length;
			result = new WebServiceExtension.com.owfg.stub.Banner[bannerArraySize];
			for (int bannerArrayIndex = 0; 
				bannerArrayIndex < bannerArraySize; ++bannerArrayIndex) {
				if (bannersObj[bannerArrayIndex] == null) {
					result[bannerArrayIndex] = null;
				} else {
					result[bannerArrayIndex] = new WebServiceExtension.com.owfg.stub.Banner();
					long a_long;
					Object bannerIdObj = ((Object[])bannersObj[bannerArrayIndex])[0];
					a_long = ((java.lang.Long)bannerIdObj).longValue();
					result[bannerArrayIndex].setBannerId(a_long);
					java.lang.String string;
					Object bannerNameObj = ((Object[])bannersObj[bannerArrayIndex])[1];
					string = (java.lang.String)bannerNameObj;
					result[bannerArrayIndex].setBannerName(string);
				}
			}
		}
		return result;
	}

	public WebServiceExtension.com.owfg.stub.ItemSalesHistoryInfo[] getItemSalesHistory(long storeId, java.lang.String upc) throws java.rmi.RemoteException {
		// Copy the incoming values into an Object array if needed.
		Object[] inputObject = new Object[2];
		inputObject[0] = new java.lang.Long(storeId);
		inputObject[1] = upc;

		Operation op = Operation.newInstance(_qname_getItemSalesHistory, _type_getItemSalesHistory, _type_getItemSalesHistoryResponse);
		_prepOperation(op);
		op.setProperty(Operation.SOAPACTION_URI_PROPERTY, "");
		Object resultObj;
		try {
			resultObj = op.invoke(inputObject);
		} catch (JAXRPCException e) {
			Throwable cause = e.getLinkedCause();
			if (cause instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) cause;
			}
			throw e;
		}
		WebServiceExtension.com.owfg.stub.ItemSalesHistoryInfo[] result;
		// Convert the result into the right Java type.
		// Unwrapped return value
		Object[] itemSalesHistoryInfoObj = (Object[]) ((Object[])resultObj)[0];
		if (itemSalesHistoryInfoObj == null) {
			result = null;
		} else {
			int itemSalesHistoryInfoArraySize = itemSalesHistoryInfoObj.length;
			result = new WebServiceExtension.com.owfg.stub.ItemSalesHistoryInfo[itemSalesHistoryInfoArraySize];
			for (int itemSalesHistoryInfoArrayIndex = 0; 
				itemSalesHistoryInfoArrayIndex < itemSalesHistoryInfoArraySize; 
				++itemSalesHistoryInfoArrayIndex) {
				if (itemSalesHistoryInfoObj[itemSalesHistoryInfoArrayIndex] == null) {
					result[itemSalesHistoryInfoArrayIndex] = null;
				} else {
					result[itemSalesHistoryInfoArrayIndex] = new WebServiceExtension.com.owfg.stub.ItemSalesHistoryInfo();
					int a_int;
					Object dayOfWeekObj = ((Object[])itemSalesHistoryInfoObj[itemSalesHistoryInfoArrayIndex])[0];
					a_int = ((java.lang.Integer)dayOfWeekObj).intValue();
					result[itemSalesHistoryInfoArrayIndex].setDayOfWeek(a_int);
					java.lang.Double _double;
					Object forecastObj = ((Object[])itemSalesHistoryInfoObj[itemSalesHistoryInfoArrayIndex])[1];
					_double = (java.lang.Double)forecastObj;
					result[itemSalesHistoryInfoArrayIndex].setForecast(_double);
					java.lang.Boolean _boolean;
					Object onPromoObj = ((Object[])itemSalesHistoryInfoObj[itemSalesHistoryInfoArrayIndex])[2];
					_boolean = (java.lang.Boolean)onPromoObj;
					result[itemSalesHistoryInfoArrayIndex].setOnPromo(_boolean);
					java.lang.Double _double2;
					Object promoSalesObj = ((Object[])itemSalesHistoryInfoObj[itemSalesHistoryInfoArrayIndex])[3];
					_double2 = (java.lang.Double)promoSalesObj;
					result[itemSalesHistoryInfoArrayIndex].setPromoSales(_double2);
					java.lang.Double _double3;
					Object regularSalesObj = ((Object[])itemSalesHistoryInfoObj[itemSalesHistoryInfoArrayIndex])[4];
					_double3 = (java.lang.Double)regularSalesObj;
					result[itemSalesHistoryInfoArrayIndex].setRegularSales(_double3);
				}
			}
		}
		return result;
	}

	public WebServiceExtension.com.owfg.stub.StoreManagementInfo getStoreManagementInfo(long storeId, java.lang.String upc) throws java.rmi.RemoteException {
		// Copy the incoming values into an Object array if needed.
		Object[] inputObject = new Object[2];
		inputObject[0] = new java.lang.Long(storeId);
		inputObject[1] = upc;

		Operation op = Operation.newInstance(_qname_getStoreManagementInfo, _type_getStoreManagementInfo, _type_getStoreManagementInfoResponse);
		_prepOperation(op);
		op.setProperty(Operation.SOAPACTION_URI_PROPERTY, "");
		Object resultObj;
		try {
			resultObj = op.invoke(inputObject);
		} catch (JAXRPCException e) {
			Throwable cause = e.getLinkedCause();
			if (cause instanceof java.rmi.RemoteException) {
				throw (java.rmi.RemoteException) cause;
			}
			throw e;
		}
		WebServiceExtension.com.owfg.stub.StoreManagementInfo result;
		// Convert the result into the right Java type.
		// Unwrapped return value
		Object[] storeManagementInfoObj = (Object[]) ((Object[])resultObj)[0];
		if (storeManagementInfoObj == null) {
			result = null;
		} else {
			result = new WebServiceExtension.com.owfg.stub.StoreManagementInfo();
			java.lang.Double _double;
			Object balanceOnHandObj = storeManagementInfoObj[0];
			_double = (java.lang.Double)balanceOnHandObj;
			result.setBalanceOnHand(_double);
			java.lang.Double _double2;
			Object forecastObj = storeManagementInfoObj[1];
			_double2 = (java.lang.Double)forecastObj;
			result.setForecast(_double2);
			java.lang.Double _double3;
			Object inTransitObj = storeManagementInfoObj[2];
			_double3 = (java.lang.Double)inTransitObj;
			result.setInTransit(_double3);
			java.lang.String string;
			Object itemDescriptionObj = storeManagementInfoObj[3];
			string = (java.lang.String)itemDescriptionObj;
			result.setItemDescription(string);
			java.lang.Long _long;
			Object minimumObj = storeManagementInfoObj[4];
			_long = (java.lang.Long)minimumObj;
			result.setMinimum(_long);
			java.lang.Double _double4;
			Object onOrderObj = storeManagementInfoObj[5];
			_double4 = (java.lang.Double)onOrderObj;
			result.setOnOrder(_double4);
			java.lang.Integer integer;
			Object packObj = storeManagementInfoObj[6];
			integer = (java.lang.Integer)packObj;
			result.setPack(integer);
			java.lang.String string2;
			Object promotionObj = storeManagementInfoObj[7];
			string2 = (java.lang.String)promotionObj;
			result.setPromotion(string2);
			java.lang.Double _double5;
			Object regularPriceObj = storeManagementInfoObj[8];
			_double5 = (java.lang.Double)regularPriceObj;
			result.setRegularPrice(_double5);
			java.lang.String string3;
			Object sourceObj = storeManagementInfoObj[9];
			string3 = (java.lang.String)sourceObj;
			result.setSource(string3);
			long a_long;
			Object storeIdObj = storeManagementInfoObj[10];
			a_long = ((java.lang.Long)storeIdObj).longValue();
			result.setStoreId(a_long);
			java.lang.String string4;
			Object upcObj = storeManagementInfoObj[11];
			string4 = (java.lang.String)upcObj;
			result.setUpc(string4);
		}
		return result;
	}
	// 
	//  End user methods
	// 

	protected static final QName _qname_balanceOnHand = new QName("", "balanceOnHand");
	protected static final QName _qname_bannerId = new QName("", "bannerId");
	protected static final QName _qname_bannerName = new QName("", "bannerName");
	protected static final QName _qname_banners = new QName("", "banners");
	protected static final QName _qname_dayOfWeek = new QName("", "dayOfWeek");
	protected static final QName _qname_forecast = new QName("", "forecast");
	protected static final QName _qname_inTransit = new QName("", "inTransit");
	protected static final QName _qname_itemDescription = new QName("", "itemDescription");
	protected static final QName _qname_itemSalesHistoryInfo = new QName("", "itemSalesHistoryInfo");
	protected static final QName _qname_minimum = new QName("", "minimum");
	protected static final QName _qname_onOrder = new QName("", "onOrder");
	protected static final QName _qname_onPromo = new QName("", "onPromo");
	protected static final QName _qname_pack = new QName("", "pack");
	protected static final QName _qname_promoSales = new QName("", "promoSales");
	protected static final QName _qname_promotion = new QName("", "promotion");
	protected static final QName _qname_regularPrice = new QName("", "regularPrice");
	protected static final QName _qname_regularSales = new QName("", "regularSales");
	protected static final QName _qname_source = new QName("", "source");
	protected static final QName _qname_storeId = new QName("", "storeId");
	protected static final QName _qname_storeManagementInfo = new QName("", "storeManagementInfo");
	protected static final QName _qname_storeName = new QName("", "storeName");
	protected static final QName _qname_stores = new QName("", "stores");
	protected static final QName _qname_upc = new QName("", "upc");
	protected static final QName _qname_getActiveStores = new QName("http://ws.facade.owfg.com/", "getActiveStores");
	protected static final QName _qname_getActiveStoresResponse = new QName("http://ws.facade.owfg.com/", "getActiveStoresResponse");
	protected static final QName _qname_getBanners = new QName("http://ws.facade.owfg.com/", "getBanners");
	protected static final QName _qname_getBannersResponse = new QName("http://ws.facade.owfg.com/", "getBannersResponse");
	protected static final QName _qname_getItemSalesHistory = new QName("http://ws.facade.owfg.com/", "getItemSalesHistory");
	protected static final QName _qname_getItemSalesHistoryResponse = new QName("http://ws.facade.owfg.com/", "getItemSalesHistoryResponse");
	protected static final QName _qname_getStoreManagementInfo = new QName("http://ws.facade.owfg.com/", "getStoreManagementInfo");
	protected static final QName _qname_getStoreManagementInfoResponse = new QName("http://ws.facade.owfg.com/", "getStoreManagementInfoResponse");
	protected static final Element _type_getActiveStores;
	protected static final Element _type_getActiveStoresResponse;
	protected static final Element _type_getBanners;
	protected static final Element _type_getBannersResponse;
	protected static final Element _type_getItemSalesHistory;
	protected static final Element _type_getItemSalesHistoryResponse;
	protected static final Element _type_getStoreManagementInfo;
	protected static final Element _type_getStoreManagementInfoResponse;
	static {
		// Create all of the Type's that this stub uses, once.
		ComplexType _complexType_getActiveStores;
		_complexType_getActiveStores = new ComplexType();
		_complexType_getActiveStores.elements = new Element[0];
		_type_getActiveStores = new Element(_qname_getActiveStores, _complexType_getActiveStores);
		Element _type_bannerId;
		_type_bannerId = new Element(_qname_bannerId, Type.LONG);
		Element _type_storeId;
		_type_storeId = new Element(_qname_storeId, Type.LONG);
		Element _type_storeName;
		_type_storeName = new Element(_qname_storeName, Type.STRING, 0, 1, false);
		ComplexType _complexType_store;
		_complexType_store = new ComplexType();
		_complexType_store.elements = new Element[3];
		_complexType_store.elements[0] = _type_bannerId;
		_complexType_store.elements[1] = _type_storeId;
		_complexType_store.elements[2] = _type_storeName;
		Element _type_stores;
		_type_stores = new Element(_qname_stores, _complexType_store, 0, -1, false);
		ComplexType _complexType_getActiveStoresResponse;
		_complexType_getActiveStoresResponse = new ComplexType();
		_complexType_getActiveStoresResponse.elements = new Element[1];
		_complexType_getActiveStoresResponse.elements[0] = _type_stores;
		_type_getActiveStoresResponse = new Element(_qname_getActiveStoresResponse, _complexType_getActiveStoresResponse);
		_type_getBanners = new Element(_qname_getBanners, _complexType_getActiveStores);
		Element _type_bannerName;
		_type_bannerName = new Element(_qname_bannerName, Type.STRING, 0, 1, false);
		ComplexType _complexType_banner;
		_complexType_banner = new ComplexType();
		_complexType_banner.elements = new Element[2];
		_complexType_banner.elements[0] = _type_bannerId;
		_complexType_banner.elements[1] = _type_bannerName;
		Element _type_banners;
		_type_banners = new Element(_qname_banners, _complexType_banner, 0, -1, false);
		ComplexType _complexType_getBannersResponse;
		_complexType_getBannersResponse = new ComplexType();
		_complexType_getBannersResponse.elements = new Element[1];
		_complexType_getBannersResponse.elements[0] = _type_banners;
		_type_getBannersResponse = new Element(_qname_getBannersResponse, _complexType_getBannersResponse);
		Element _type_upc;
		_type_upc = new Element(_qname_upc, Type.STRING, 0, 1, false);
		ComplexType _complexType_getItemSalesHistory;
		_complexType_getItemSalesHistory = new ComplexType();
		_complexType_getItemSalesHistory.elements = new Element[2];
		_complexType_getItemSalesHistory.elements[0] = _type_storeId;
		_complexType_getItemSalesHistory.elements[1] = _type_upc;
		_type_getItemSalesHistory = new Element(_qname_getItemSalesHistory, _complexType_getItemSalesHistory);
		Element _type_dayOfWeek;
		_type_dayOfWeek = new Element(_qname_dayOfWeek, Type.INT);
		Element _type_forecast;
		_type_forecast = new Element(_qname_forecast, Type.DOUBLE, 0, 1, false);
		Element _type_onPromo;
		_type_onPromo = new Element(_qname_onPromo, Type.BOOLEAN, 0, 1, false);
		Element _type_promoSales;
		_type_promoSales = new Element(_qname_promoSales, Type.DOUBLE, 0, 1, false);
		Element _type_regularSales;
		_type_regularSales = new Element(_qname_regularSales, Type.DOUBLE, 0, 1, false);
		ComplexType _complexType_itemSalesHistoryInfo;
		_complexType_itemSalesHistoryInfo = new ComplexType();
		_complexType_itemSalesHistoryInfo.elements = new Element[5];
		_complexType_itemSalesHistoryInfo.elements[0] = _type_dayOfWeek;
		_complexType_itemSalesHistoryInfo.elements[1] = _type_forecast;
		_complexType_itemSalesHistoryInfo.elements[2] = _type_onPromo;
		_complexType_itemSalesHistoryInfo.elements[3] = _type_promoSales;
		_complexType_itemSalesHistoryInfo.elements[4] = _type_regularSales;
		Element _type_itemSalesHistoryInfo;
		_type_itemSalesHistoryInfo = new Element(_qname_itemSalesHistoryInfo, _complexType_itemSalesHistoryInfo, 0, -1, false);
		ComplexType _complexType_getItemSalesHistoryResponse;
		_complexType_getItemSalesHistoryResponse = new ComplexType();
		_complexType_getItemSalesHistoryResponse.elements = new Element[1];
		_complexType_getItemSalesHistoryResponse.elements[0] = _type_itemSalesHistoryInfo;
		_type_getItemSalesHistoryResponse = new Element(_qname_getItemSalesHistoryResponse, _complexType_getItemSalesHistoryResponse);
		_type_getStoreManagementInfo = new Element(_qname_getStoreManagementInfo, _complexType_getItemSalesHistory);
		Element _type_balanceOnHand;
		_type_balanceOnHand = new Element(_qname_balanceOnHand, Type.DOUBLE, 0, 1, false);
		Element _type_inTransit;
		_type_inTransit = new Element(_qname_inTransit, Type.DOUBLE, 0, 1, false);
		Element _type_itemDescription;
		_type_itemDescription = new Element(_qname_itemDescription, Type.STRING, 0, 1, false);
		Element _type_minimum;
		_type_minimum = new Element(_qname_minimum, Type.LONG, 0, 1, false);
		Element _type_onOrder;
		_type_onOrder = new Element(_qname_onOrder, Type.DOUBLE, 0, 1, false);
		Element _type_pack;
		_type_pack = new Element(_qname_pack, Type.INT, 0, 1, false);
		Element _type_promotion;
		_type_promotion = new Element(_qname_promotion, Type.STRING, 0, 1, false);
		Element _type_regularPrice;
		_type_regularPrice = new Element(_qname_regularPrice, Type.DOUBLE, 0, 1, false);
		Element _type_source;
		_type_source = new Element(_qname_source, Type.STRING, 0, 1, false);
		ComplexType _complexType_storeManagementInfo;
		_complexType_storeManagementInfo = new ComplexType();
		_complexType_storeManagementInfo.elements = new Element[12];
		_complexType_storeManagementInfo.elements[0] = _type_balanceOnHand;
		_complexType_storeManagementInfo.elements[1] = _type_forecast;
		_complexType_storeManagementInfo.elements[2] = _type_inTransit;
		_complexType_storeManagementInfo.elements[3] = _type_itemDescription;
		_complexType_storeManagementInfo.elements[4] = _type_minimum;
		_complexType_storeManagementInfo.elements[5] = _type_onOrder;
		_complexType_storeManagementInfo.elements[6] = _type_pack;
		_complexType_storeManagementInfo.elements[7] = _type_promotion;
		_complexType_storeManagementInfo.elements[8] = _type_regularPrice;
		_complexType_storeManagementInfo.elements[9] = _type_source;
		_complexType_storeManagementInfo.elements[10] = _type_storeId;
		_complexType_storeManagementInfo.elements[11] = _type_upc;
		Element _type_storeManagementInfo;
		_type_storeManagementInfo = new Element(_qname_storeManagementInfo, _complexType_storeManagementInfo, 0, 1, false);
		ComplexType _complexType_getStoreManagementInfoResponse;
		_complexType_getStoreManagementInfoResponse = new ComplexType();
		_complexType_getStoreManagementInfoResponse.elements = new Element[1];
		_complexType_getStoreManagementInfoResponse.elements[0] = _type_storeManagementInfo;
		_type_getStoreManagementInfoResponse = new Element(_qname_getStoreManagementInfoResponse, _complexType_getStoreManagementInfoResponse);
	}

}

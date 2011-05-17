package com.owfg.webservice;

import com.owfg.stub.StoreManagementInfo;

public class ProductInfo {
	public String boh;
	public String forcast;
	public String inTransit;
	public String itemDesc;
	public String min;
	public String onOrder;
	public String pack;
	public String promotion;
	public String regularPrice;
	public String source;
	public String storeId;
	public String upc;
	public ProductInfo(StoreManagementInfo info) {
		if (info == null) {
			return;
		}
		boh = (info.getBalanceOnHand() == null) ? "" : info.getBalanceOnHand().toString();
		forcast = (info.getForecast() == null) ? "" : info.getForecast().toString();
		inTransit = (info.getInTransit() == null) ? "" : info.getInTransit().toString();
		itemDesc = (info.getItemDescription() == null) ? "" : info.getItemDescription().toString();
		min = (info.getMinimum() == null) ? "" : info.getMinimum().toString();
		onOrder = (info.getOnOrder() == null) ? "" : info.getOnOrder().toString();
		pack = (info.getPack() == null) ? "" : info.getPack().toString();
		promotion = (info.getPromotion() == null) ? "" : info.getPromotion().toString();
		regularPrice = (info.getRegularPrice() == null) ? "" : info.getRegularPrice().toString();
		source = (info.getSource() == null) ? "" : info.getSource().toString();
		storeId = Long.toString(info.getStoreId());
		upc = (info.getUpc() == null) ? "" : info.getUpc().toString();
	}
}

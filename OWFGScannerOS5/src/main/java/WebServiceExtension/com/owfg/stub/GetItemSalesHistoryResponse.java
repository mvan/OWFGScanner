// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JSR-172 Reference Implementation wscompile 1.0, using: JAX-RPC Standard Implementation (1.1, build R59)

package com.owfg.stub;


public class GetItemSalesHistoryResponse {
    protected com.owfg.stub.ItemSalesHistoryInfo[] itemSalesHistoryInfo;
    
    public GetItemSalesHistoryResponse() {
    }
    
    public GetItemSalesHistoryResponse(com.owfg.stub.ItemSalesHistoryInfo[] itemSalesHistoryInfo) {
        this.itemSalesHistoryInfo = itemSalesHistoryInfo;
    }
    
    public com.owfg.stub.ItemSalesHistoryInfo[] getItemSalesHistoryInfo() {
        return itemSalesHistoryInfo;
    }
    
    public void setItemSalesHistoryInfo(com.owfg.stub.ItemSalesHistoryInfo[] itemSalesHistoryInfo) {
        this.itemSalesHistoryInfo = itemSalesHistoryInfo;
    }
}

// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JSR-172 Reference Implementation wscompile 1.0, using: JAX-RPC Standard Implementation (1.1, build R59)

package WebServiceExtension.com.owfg.stub;


public class ItemSalesHistoryInfo {
    protected int dayOfWeek;
    protected java.lang.Double forecast;
    protected java.lang.Boolean onPromo;
    protected java.lang.Double promoSales;
    protected java.lang.Double regularSales;
    
    public ItemSalesHistoryInfo() {
    }
    
    public ItemSalesHistoryInfo(int dayOfWeek, java.lang.Double forecast, java.lang.Boolean onPromo, java.lang.Double promoSales, java.lang.Double regularSales) {
        this.dayOfWeek = dayOfWeek;
        this.forecast = forecast;
        this.onPromo = onPromo;
        this.promoSales = promoSales;
        this.regularSales = regularSales;
    }
    
    public int getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public java.lang.Double getForecast() {
        return forecast;
    }
    
    public void setForecast(java.lang.Double forecast) {
        this.forecast = forecast;
    }
    
    public java.lang.Boolean getOnPromo() {
        return onPromo;
    }
    
    public void setOnPromo(java.lang.Boolean onPromo) {
        this.onPromo = onPromo;
    }
    
    public java.lang.Double getPromoSales() {
        return promoSales;
    }
    
    public void setPromoSales(java.lang.Double promoSales) {
        this.promoSales = promoSales;
    }
    
    public java.lang.Double getRegularSales() {
        return regularSales;
    }
    
    public void setRegularSales(java.lang.Double regularSales) {
        this.regularSales = regularSales;
    }
}

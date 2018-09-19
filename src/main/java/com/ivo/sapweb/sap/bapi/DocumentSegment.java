package com.ivo.sapweb.sap.bapi;

/**
 * @author wangjian
 * @date 2018/9/18
 */
public class DocumentSegment {

    private String MBLNR;	//	物料凭证编号
    private String MJAHR;	//	物料凭证年度
    private String ZEILE;	//	物料凭证中的项目
    private String BWART;	//	移动类型（库存管理）
    private String XAUTO;	//	项目自动创建
    //private String TRVOG;	//	事务组
    private String MATNR;	//	物料号
    private String WERKS;	//	工厂
    private String LGORT;	//	库存地点
    private String CHARG;	//	批号
    private String SOBKZ;	//	特殊库存标识
    private String LIFNR;	//	供应商帐户号
    private String KUNNR;	//	客户的帐户号

    public String getMBLNR() {
        return MBLNR;
    }

    public void setMBLNR(String MBLNR) {
        this.MBLNR = MBLNR;
    }

    public String getMJAHR() {
        return MJAHR;
    }

    public void setMJAHR(String MJAHR) {
        this.MJAHR = MJAHR;
    }

    public String getZEILE() {
        return ZEILE;
    }

    public void setZEILE(String ZEILE) {
        this.ZEILE = ZEILE;
    }

    public String getBWART() {
        return BWART;
    }

    public void setBWART(String BWART) {
        this.BWART = BWART;
    }

    public String getXAUTO() {
        return XAUTO;
    }

    public void setXAUTO(String XAUTO) {
        this.XAUTO = XAUTO;
    }

    public String getMATNR() {
        return MATNR;
    }

    public void setMATNR(String MATNR) {
        this.MATNR = MATNR;
    }

    public String getWERKS() {
        return WERKS;
    }

    public void setWERKS(String WERKS) {
        this.WERKS = WERKS;
    }

    public String getLGORT() {
        return LGORT;
    }

    public void setLGORT(String LGORT) {
        this.LGORT = LGORT;
    }

    public String getCHARG() {
        return CHARG;
    }

    public void setCHARG(String CHARG) {
        this.CHARG = CHARG;
    }

    public String getSOBKZ() {
        return SOBKZ;
    }

    public void setSOBKZ(String SOBKZ) {
        this.SOBKZ = SOBKZ;
    }

    public String getLIFNR() {
        return LIFNR;
    }

    public void setLIFNR(String LIFNR) {
        this.LIFNR = LIFNR;
    }

    public String getKUNNR() {
        return KUNNR;
    }

    public void setKUNNR(String KUNNR) {
        this.KUNNR = KUNNR;
    }
}

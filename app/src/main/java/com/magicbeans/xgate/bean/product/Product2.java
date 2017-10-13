package com.magicbeans.xgate.bean.product;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 * 本来只使用{@Product}一个实体就可以，但是商品详情中返回的商品列表和商品列表接口返回的数据实体不一致，只有新建一个Product2
 * 在详情中返回的Product2中ProductImages字段是一个集合，而列表中是一个对象。。。日
 */

public class Product2 implements Serializable {

    private String ProdID;
    private String ProdName;
    private String Size;
    private String ColorText;
    private String ColorImg;
    private String OptionDisplay;
    private String OptionTypeId;
    private boolean IsUnboxed;
    private String RRP;
    private String WasPrice;
    private String ShopPrice;
    private String Save;
    private String SaveTextWithDesc;
    private String SizeText;
    private boolean isSale;
    private List<ProductImages> ProductImages;
    private List<String> Description;

    public String getProdID() {
        return ProdID;
    }

    public void setProdID(String prodID) {
        ProdID = prodID;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getColorText() {
        return ColorText;
    }

    public void setColorText(String colorText) {
        ColorText = colorText;
    }

    public String getColorImg() {
        return ColorImg;
    }

    public void setColorImg(String colorImg) {
        ColorImg = colorImg;
    }

    public String getOptionDisplay() {
        return OptionDisplay;
    }

    public void setOptionDisplay(String optionDisplay) {
        OptionDisplay = optionDisplay;
    }

    public String getOptionTypeId() {
        return OptionTypeId;
    }

    public void setOptionTypeId(String optionTypeId) {
        OptionTypeId = optionTypeId;
    }

    public boolean isUnboxed() {
        return IsUnboxed;
    }

    public void setUnboxed(boolean unboxed) {
        IsUnboxed = unboxed;
    }

    public String getRRP() {
        return RRP;
    }

    public void setRRP(String RRP) {
        this.RRP = RRP;
    }

    public String getWasPrice() {
        return WasPrice;
    }

    public void setWasPrice(String wasPrice) {
        WasPrice = wasPrice;
    }

    public String getShopPrice() {
        return ShopPrice;
    }

    public void setShopPrice(String shopPrice) {
        ShopPrice = shopPrice;
    }

    public String getSave() {
        return Save;
    }

    public void setSave(String save) {
        Save = save;
    }

    public String getSaveTextWithDesc() {
        return SaveTextWithDesc;
    }

    public void setSaveTextWithDesc(String saveTextWithDesc) {
        SaveTextWithDesc = saveTextWithDesc;
    }

    public String getSizeText() {
        return SizeText;
    }

    public void setSizeText(String sizeText) {
        SizeText = sizeText;
    }

    public boolean isSale() {
        return isSale;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    public List<com.magicbeans.xgate.bean.product.ProductImages> getProductImages() {
        return ProductImages;
    }

    public void setProductImages(List<com.magicbeans.xgate.bean.product.ProductImages> productImages) {
        ProductImages = productImages;
    }

    public List<String> getDescription() {
        return Description;
    }

    public void setDescription(List<String> description) {
        Description = description;
    }
}
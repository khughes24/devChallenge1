package com.khughes.devchallenge1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "productList", propOrder = {
        "Result",
        "Net",
        "VAT",
        "Gross"
})

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
        "Result",
        "Net",
        "VAT",
        "Gross"
})

public class productList {

    @XmlElement(name = "Result")
    @JsonProperty("Result")
    private List<productItem> item;

    @XmlElement(name = "Net")
    @JsonProperty("Net")
    public double net;

    @XmlElement(name = "VAT")
    @JsonProperty("VAT")
    public double vat;

    @XmlElement(name = "Gross")
    @JsonProperty("Gross")
    public double gross;


    public List<productItem> getItem() {
        return item;
    }
    public void setItem(List<productItem> item) {
        this.item = item;
    }
}

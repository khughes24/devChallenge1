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
        "Item"
})

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
        "Item"
})

public class productList {

    @XmlElement(name = "Item")
    @JsonProperty("Item")
    public List<productItem> item;

    public List<productItem> getItem() {
        return item;
    }

    public void setItem(List<productItem> item) {
        this.item = item;
    }
}

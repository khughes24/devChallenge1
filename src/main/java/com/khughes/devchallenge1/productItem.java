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
@XmlType(name = "productItem", propOrder = {
        "Name",
        "Description",
        "Price",
        "Kals"
})

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
        "Name",
        "Description",
        "Price",
        "Kals"
})

public class productItem {

    @XmlElement(name = "Name")
    @JsonProperty("Name")
    public String name;

    @XmlElement(name = "Description")
    @JsonProperty("Description")
    public String description;

    @XmlElement(name = "Price")
    @JsonProperty("Price")
    public String price;

    @XmlElement(name = "Kals")
    @JsonProperty("Kals")
    public String kals;




    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getKals() {
        return kals;
    }
    public void setKals(String kals) {
        this.kals = kals;
    }
}

package com.khughes.devchallenge1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

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
    @Getter
    @Setter private String name;

    @XmlElement(name = "Description")
    @JsonProperty("Description")
    @Getter
    @Setter private String description;

    @XmlElement(name = "Price")
    @JsonProperty("Price")
    @Getter
    @Setter private String price;

    @XmlElement(name = "Kals")
    @JsonProperty("Kals")
    @Getter
    @Setter private String kals;
}

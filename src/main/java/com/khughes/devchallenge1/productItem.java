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
        "Title",
        "Code",
        "Kcal_per_100g",
        "Unit_price",
        "Description"
})

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
        "Title",
        "Code",
        "Kcal_per_100g",
        "Unit_price",
        "Description"

})

public class  productItem {

    @XmlElement(name = "Title")
    @JsonProperty("Title")
    @Getter
    @Setter private String name;

    @XmlElement(name = "Code")
    @JsonProperty("Code")
    @Getter
    @Setter private String code;

    @XmlElement(name = "Kcal_per_100g")
    @JsonProperty("Kcal_per_100g")
    @Getter
    @Setter private String kals;

    @XmlElement(name = "Unit_price")
    @JsonProperty("Unit_price")
    @Getter
    @Setter private String price;

    @XmlElement(name = "Description")
    @JsonProperty("Description")
    @Getter
    @Setter private String description;


}

package com.mcdonald.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mcdonald.model.Item;
import com.mcdonald.model.Price;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@ToString
public class MenuItemsResponse {

	@JsonProperty("Items")
	private Map<Price, List<Item>> items = new HashMap<>();
}

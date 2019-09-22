package com.mcdonald.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
@ToString
public class MenuListResponse {
	
	@JsonProperty("MenuList")
	private List<Menu> menuList;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Getter
	@AllArgsConstructor
	@ToString
	public static class Menu {
		private int menuid;
		private String menuName;
	}
}

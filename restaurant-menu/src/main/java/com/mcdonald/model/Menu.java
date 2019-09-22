package com.mcdonald.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Menu {

	private int menuId;
	private String name;
	private List<Item> items;// = new ArrayList<>();
	private List<Menu> subMenus;// = new ArrayList<>();
	private String description;
	private boolean isActive;
	
}

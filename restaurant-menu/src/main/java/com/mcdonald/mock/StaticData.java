package com.mcdonald.mock;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import com.mcdonald.model.Item;
import com.mcdonald.model.Menu;
import com.mcdonald.model.Price;

public class StaticData {

	private static List<Menu> menuList = new ArrayList<>();
	
	static {
		Menu menu1 = new Menu();
		menu1.setActive(true);
		menu1.setDescription("menu 1");
		menu1.setMenuId(1);
		menu1.setName("menu1");
		List<Item> items1 = new ArrayList<>();
		Item item1 = new Item();
		item1.setDescription("item1 of menu1");
		item1.setName("item1");
		item1.setPicUrl("https://media.gettyimages.com/photos/make-the-most-out-of-your-device-picture-id618982164?s=2048x2048");
		item1.setEndTime(LocalTime.of(23, 00));
		item1.setEnddate(LocalDate.of(2019, 4, 1));
		item1.setOpenDays(Arrays.<DayOfWeek>asList(DayOfWeek.FRIDAY,DayOfWeek.SATURDAY,DayOfWeek.SUNDAY));
		item1.setPrice(new Price(new BigDecimal(34.99), Currency.getInstance("USD")));
		item1.setRank(1);
		item1.setStartdate(LocalDate.of(2019, 3, 1));
		item1.setStartTime(LocalTime.of(20, 00));
		items1.add(item1);
		menu1.setItems(items1);
		List<Menu> subMenus = new ArrayList<>();
		Menu subMenu11 = new Menu();
		subMenu11.setActive(true);
		subMenu11.setDescription("sub-menu 1");
		subMenu11.setMenuId(101);
		subMenu11.setName("sub-menu1");
		List<Item> subitems1 = new ArrayList<>();
		Item subitem1 = new Item();
		subitem1.setDescription("sub-item1 of sub-menu1");
		subitem1.setName("sub-item1");
		subitem1.setPicUrl("https://media.gettyimages.com/photos/make-the-most-out-of-your-device-picture-id618982164?s=2048x2048");
		subitem1.setEndTime(LocalTime.of(23, 00));
		subitem1.setEnddate(LocalDate.of(2019, 4, 1));
		subitem1.setOpenDays(Arrays.<DayOfWeek>asList(DayOfWeek.FRIDAY,DayOfWeek.SATURDAY,DayOfWeek.SUNDAY));
		subitem1.setPrice(new Price(new BigDecimal(34.99), Currency.getInstance("USD")));
		subitem1.setRank(1);
		subitem1.setStartdate(LocalDate.of(2019, 3, 1));
		subitem1.setStartTime(LocalTime.of(20, 00));
		subitems1.add(subitem1);
		subMenu11.setItems(subitems1);
		subMenus.add(subMenu11);
		menu1.setSubMenus(subMenus);
	}
	
	public static  List<Menu> getStaticMenuList() {
		return menuList;
	}
}

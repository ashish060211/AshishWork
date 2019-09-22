package com.mcdonald.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class Item {

	private String name;
	private String description;
	private String picUrl;
	private Price price;
	private int rank;
	private List<DayOfWeek> openDays;
	private LocalDate startdate;
	private LocalDate enddate;
	private LocalTime startTime;
	private LocalTime endTime;
	
}

package com.mcdonald.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mcdonald.dto.MenuItemsResponse;
import com.mcdonald.dto.MenuListResponse;
import com.mcdonald.dto.Response;
import com.mcdonald.service.MenuService;
import com.mcdonald.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MenuController {

	@Autowired
	private MenuService service;
	
	@GetMapping("/menu")
	public ResponseEntity<Response<MenuListResponse>> getMenuList() {
		Response<MenuListResponse> response = null;
		org.springframework.http.HttpStatus httpstatus;
		try {
			log.info("getEventTypeList function called ...");
			MenuListResponse responseBody = service.getMenuList();
			response = Response.success(Constants.SUCCESS_MSG, responseBody);
			httpstatus = org.springframework.http.HttpStatus.OK;
			return new ResponseEntity<>(response, httpstatus);
		} catch (Exception ex) {
			log.error("Error occur while executing getEventTypeList : ", ex);
			response = Response.error(Constants.SERVER_ERROR_MSG);
			httpstatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<>(response, httpstatus);
		}
	}
	
	@GetMapping("/menu/{menuid}/items")
	public ResponseEntity<Response<MenuItemsResponse>> getMenuItems(@PathVariable("id") Integer menuid) {
		Response<MenuItemsResponse> response = null;
		org.springframework.http.HttpStatus httpstatus;
		try {
			log.info("getEventTypeList function called ...");
			MenuItemsResponse responseBody = service.getMenuItems(menuid);
			response = Response.success(Constants.SUCCESS_MSG, responseBody);
			httpstatus = org.springframework.http.HttpStatus.OK;
			return new ResponseEntity<>(response, httpstatus);
		} catch (Exception ex) {
			log.error("Error occur while executing getEventTypeList : ", ex);
			response = Response.error(Constants.SERVER_ERROR_MSG);
			httpstatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<>(response, httpstatus);
		}
	}
	
	@GetMapping("/menu/{menuid}/items/total")
	public ResponseEntity<Response<BigDecimal>> getMenuItemTotal(@PathVariable("id") Integer menuid) {
		Response<BigDecimal> response = null;
		org.springframework.http.HttpStatus httpstatus;
		try {
			log.info("getEventTypeList function called ...");
			BigDecimal responseBody = service.getMenuItemTotal(menuid);
			response = Response.success(Constants.SUCCESS_MSG, responseBody);
			httpstatus = org.springframework.http.HttpStatus.OK;
			return new ResponseEntity<>(response, httpstatus);
		} catch (Exception ex) {
			log.error("Error occur while executing getEventTypeList : ", ex);
			response = Response.error(Constants.SERVER_ERROR_MSG);
			httpstatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<>(response, httpstatus);
		}
	}
	
	@GetMapping("/menu/{menuid}/activesubmenucount")
	public ResponseEntity<Response<Integer>> getActiveSubmenuCount(@PathVariable("id") Integer menuid) {
		Response<Integer> response = null;
		org.springframework.http.HttpStatus httpstatus;
		try {
			log.info("getEventTypeList function called ...");
			Integer responseBody = service.getActiveSubmenuCount(menuid);
			response = Response.success(Constants.SUCCESS_MSG, responseBody);
			httpstatus = org.springframework.http.HttpStatus.OK;
			return new ResponseEntity<>(response, httpstatus);
		} catch (Exception ex) {
			log.error("Error occur while executing getEventTypeList : ", ex);
			response = Response.error(Constants.SERVER_ERROR_MSG);
			httpstatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<>(response, httpstatus);
		}
	}
}

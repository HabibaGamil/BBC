package com.springboot.firstapp.DataClasses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Request {
	String command;
	Map<String, Object> requestParameters;
	Map<String, Object> body;
	Map<String,String> header;
	
	public Request(String command,Map<String,String> header )
	{
	    this.command = command;
	    this.header = header;
	}
}

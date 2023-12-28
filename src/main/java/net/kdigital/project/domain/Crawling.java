package net.kdigital.project.domain;

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
public class Crawling {
	
	private String metal;
	private String unit;	
	private String price;
	private String day;
	private String percent;
	private String weekly;
	private String monthly;
	private String YoY;

}

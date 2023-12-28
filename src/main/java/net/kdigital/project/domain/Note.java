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
public class Note {
	
	private String regdate;
	private String name;
	private String email;
	private String phone;
	private String text;
	
}

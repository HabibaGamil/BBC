package com.springboot.firstapp.DataClasses;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrefs {

	private Long id;
	
	private Set<Integer> prefSet;
}

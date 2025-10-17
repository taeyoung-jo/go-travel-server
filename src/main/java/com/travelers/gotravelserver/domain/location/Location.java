package com.travelers.gotravelserver.domain.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

	private Long id;
	private String continent;
	private String city;
}

package com.tecsup.petclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * @author jgomezm
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialityTO {

	private Integer id;
	
	private String name;
	
	private int typeId;

	private int specialityId;


}

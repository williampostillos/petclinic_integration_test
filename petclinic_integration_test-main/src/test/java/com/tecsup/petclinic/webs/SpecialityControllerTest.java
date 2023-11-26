package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.PetTO;
import com.tecsup.petclinic.domain.SpecialityTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class SpecialityControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllSpeciality() throws Exception {

		int ID_FIRST_RECORD = 1;

		this.mockMvc.perform(get("/speciality"))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				//		    .andExpect(jsonPath("$", hasSize(NRO_RECORD)))
				.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}
	

	/**
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testFindPetOK() throws Exception {

		String NAME_Speciality = "POO";
		int TYPE_ID = 1;
		int Speciality_ID = 1;

		mockMvc.perform(get("/speciality/1"))  // Object must be BASIL
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(NAME_Speciality)))
				.andExpect(jsonPath("$.typeId", is(TYPE_ID)))
				.andExpect(jsonPath("$.specialityId", is(Speciality_ID)));
	}
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindPetKO() throws Exception {

		mockMvc.perform(get("/speciality/666"))
				.andExpect(status().isNotFound());

	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testCreatePet() throws Exception {

		String NAME_Speciality = "Base de datos";
		int TYPE_ID = 1;
		int Speciality_ID = 1;

		SpecialityTO newSpecialityTO = new SpecialityTO();
		newSpecialityTO.setName(NAME_Speciality);
		newSpecialityTO.setTypeId(TYPE_ID);
		newSpecialityTO.setSpecialityId(Speciality_ID);

		mockMvc.perform(post("/speciality")
						.content(om.writeValueAsString(newSpecialityTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				//.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(NAME_Speciality)))
				.andExpect(jsonPath("$.typeId", is(TYPE_ID)))
				.andExpect(jsonPath("$.specialityId", is(Speciality_ID)));

	}


	/**
     * 
     * @throws Exception
     */
	@Test
	public void testDeletePet() throws Exception {

		String NAME_Speciality = "Innovacion";
		int TYPE_ID = 1;
		int Speciality_ID = 1;

		SpecialityTO newSpecialityTO = new SpecialityTO();
		newSpecialityTO.setName(NAME_Speciality);
		newSpecialityTO.setTypeId(TYPE_ID);
		newSpecialityTO.setSpecialityId(Speciality_ID);

		ResultActions mvcActions = mockMvc.perform(post("/speciality")
						.content(om.writeValueAsString(newSpecialityTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Integer id = JsonPath.parse(response).read("$.id");

		mockMvc.perform(delete("/speciality/" + id ))
				/*.andDo(print())*/
				.andExpect(status().isOk());
	}

}
    
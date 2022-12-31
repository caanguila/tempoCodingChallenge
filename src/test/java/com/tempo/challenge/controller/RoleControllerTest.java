package com.tempo.challenge.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tempo.challenge.dto.RoleRequest;
import com.tempo.challenge.model.Role;
import com.tempo.challenge.repo.RoleRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RoleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private RoleRepository roleRepository;

	private static List<Role> listRoles;

	private static Role role;
	
	private static RoleRequest roleRequest;

	@BeforeAll
	static void setup() {
		listRoles = new ArrayList<>();
		listRoles.add(new Role(UUID.randomUUID(), "Developer", "test"));
		listRoles.add(new Role(UUID.randomUUID(), "Tester", "test"));
		listRoles.add(new Role(UUID.randomUUID(), "PM", "test"));

		role = new Role(UUID.randomUUID(), "Designer", "test");
		
		roleRequest = new RoleRequest("UX", "test");
		
	}

	@Test
	void givenListOfRoles_whenGetAllRoles_thenReturnRolesList() throws Exception {

		Mockito.when(roleRepository.findAll()).thenReturn(listRoles);

		mockMvc.perform(MockMvcRequestBuilders.get("/roles").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(3)))
				.andExpect(jsonPath("$[2].name", Matchers.is("PM")));
	}

	@Test
	void givenOneRole_whenGetRole_thenReturnRole() throws Exception {
		Mockito.when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

		mockMvc.perform(MockMvcRequestBuilders.get("/roles/" + role.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.notNullValue()))
				.andExpect(jsonPath("$.name", Matchers.is("Designer")));
	}

	@Test
	void givenOneRole_whenSaveRole_thenReturnRole() throws Exception {
		Mockito.when(roleRepository.save(any())).thenReturn(role);
		Mockito.when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/roles")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(roleRequest));

		mockMvc.perform(mockRequest)
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$", Matchers.notNullValue()))
			   .andExpect(jsonPath("$.name", Matchers.is("Designer")));
	}

}

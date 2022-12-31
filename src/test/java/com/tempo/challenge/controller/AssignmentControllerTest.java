package com.tempo.challenge.controller;

import static org.mockito.ArgumentMatchers.any;
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
import com.tempo.challenge.dto.AssignmentRequest;
import com.tempo.challenge.integration.TempoRestClient;
import com.tempo.challenge.model.Assignment;
import com.tempo.challenge.model.Role;
import com.tempo.challenge.model.Team;
import com.tempo.challenge.model.User;
import com.tempo.challenge.repo.AssignmentRepository;
import com.tempo.challenge.repo.RoleRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AssignmentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private TempoRestClient tempoRestClient;	
	
	@MockBean
	private AssignmentRepository assignmentRepository;
	
	@MockBean
	private RoleRepository roleRepository;
	
	private static List<Assignment> lAssignment;
	
	private static AssignmentRequest assignmentRequest;
	
	private static Assignment assignmentResponse;
	
	private static Role role;
	
	private static User user;
	
	private static Team team;
	
	@BeforeAll
	static void setup() {
		lAssignment = new ArrayList<>();
		lAssignment.add(new Assignment(new Role(UUID.randomUUID(), "Developer", "test"),UUID.randomUUID(),UUID.randomUUID()));
		lAssignment.add(new Assignment(new Role(UUID.randomUUID(), "Tester", "test"),UUID.randomUUID(),UUID.randomUUID()));
		lAssignment.add(new Assignment(new Role(UUID.randomUUID(), "PM", "test"),UUID.randomUUID(),UUID.randomUUID()));
		
		assignmentRequest = new AssignmentRequest(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID());
		
		assignmentResponse=new Assignment(new Role(UUID.randomUUID(), "Developer", "test"),UUID.randomUUID(),UUID.randomUUID());
		
		role=new Role();
		role.setId(UUID.randomUUID());
		role.setName("Tester");
		
		user= new User();
		team= new Team();
		user.setId(UUID.randomUUID());
		user.setDisplayName("Test User 1");
		team.setId(UUID.randomUUID());
		team.setName("test team");
		
	}
	
	@Test
	void givenListOfAssignments_whenGetAllAssignments_thenReturnAssignmentsList() throws Exception {
		Mockito.when(assignmentRepository.findAll()).thenReturn(lAssignment);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/roles/assignments").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(3)))
		.andExpect(jsonPath("$[2].role.name", Matchers.is("PM")));
	}
	
	@Test
	void givenOneAssignment_whenSaveAssignment_thenReturnAssignment() throws Exception {
			
		Mockito.when(tempoRestClient.findUser(any())).thenReturn(user);
		Mockito.when(tempoRestClient.findTeam(any())).thenReturn(team);
		Mockito.when(roleRepository.findById(any())).thenReturn(Optional.of(role));
		Mockito.when(assignmentRepository.getByRoleAndUserIdAndTeamId(any(),any(),any())).thenReturn(Optional.empty());
		Mockito.when(assignmentRepository.save(any())).thenReturn(assignmentResponse);
		
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/roles/assignments")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(assignmentRequest));
		
		mockMvc.perform(mockRequest)
	       .andExpect(status().isOk())
	       .andExpect(jsonPath("$", Matchers.notNullValue()))
		   .andExpect(jsonPath("$.role.name", Matchers.is("Developer")));
	}
	
	@Test
	void givenOneRole_thenReturnMemberships() throws Exception {
		
		
		Mockito.when(roleRepository.findById(any())).thenReturn(Optional.of(role));
		Mockito.when(assignmentRepository.findMembershipsByRole(any())).thenReturn(lAssignment);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/roles/findMemberships/"+role.getId()).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(3)))
		.andExpect(jsonPath("$[0].userId", Matchers.is(lAssignment.get(0).getUserId().toString())));
		
		
	}
	
	@Test
	void givenOneMembership_thenReturnRole() throws Exception {
				
		Mockito.when(assignmentRepository.findByTeamIdAndUserId(any(), any())).thenReturn(Optional.of(assignmentResponse));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/roles/findRole/?teamId="+team.getId()+"&userId="+user.getId()).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.notNullValue()))
		.andExpect(jsonPath("$.name", Matchers.is("Developer")));
	}

}

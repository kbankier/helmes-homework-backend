package com.helmes.homework_backend.test.integration;

import com.helmes.homework_backend.dto.UserDataRequestDTO;
import com.helmes.homework_backend.dto.UserDataResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${frontend.username}")
    private String frontendUser;

    @Value("${frontend.password}")
    private String frontendPassword;

    @Test
    public void getSectors_authenticatedUser_returnsOk() {
        ResponseEntity<Object[]> response = restTemplate.withBasicAuth(frontendUser, frontendPassword)
                .getForEntity("/api/sectors", Object[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Object[] sectors = response.getBody();
        assertThat(sectors).isNotNull();
        assertThat(sectors.length).isGreaterThan(0);
    }

    @Test
    public void createUserData_invalidRequest_returnsBadRequest() {
        UserDataRequestDTO request = new UserDataRequestDTO();
        request.setName("");
        request.setAgree(null);
        request.setSectorIds(Set.of());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDataRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.withBasicAuth(frontendUser, frontendPassword)
                .postForEntity("/api/user-data", entity, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Map<String, String> errors = response.getBody();
        assertThat(errors).containsEntry("name", "Name is required!");
        assertThat(errors).containsEntry("agree", "Agreeing the terms is required!");
        assertThat(errors).containsEntry("sectorIds", "At least one sector must be selected!");
    }

    @Test
    public void createAndUpdateUserData_validRequest_returnsUpdatedData() {
        UserDataRequestDTO request = new UserDataRequestDTO();
        request.setName("Some Name");
        request.setAgree(true);
        request.setSectorIds(Set.of(5));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserDataRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<UserDataResponseDTO> createResponse = restTemplate.withBasicAuth(frontendUser, frontendPassword)
                .postForEntity("/api/user-data", entity, UserDataResponseDTO.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserDataResponseDTO created = createResponse.getBody();
        assertThat(created).isNotNull();
        Long id = created.getId();
        assertThat(id).isNotNull();
        assertThat(created.getName()).isEqualTo("Some Name");

        request.setName("Other Name");
        HttpEntity<UserDataRequestDTO> updateEntity = new HttpEntity<>(request, headers);
        ResponseEntity<UserDataResponseDTO> updateResponse = restTemplate.withBasicAuth(frontendUser, frontendPassword)
                .exchange("/api/user-data/" + id, HttpMethod.PUT, updateEntity, UserDataResponseDTO.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserDataResponseDTO updated = updateResponse.getBody();
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("Other Name");
    }

    @Test
    public void getSectors_unauthenticatedUser_returnsUnauthorized() {
        ResponseEntity<Object[]> response = restTemplate.getForEntity("/api/sectors", Object[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}

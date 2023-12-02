package app.moz.userservice;


import app.moz.dto.UserDto;
import app.moz.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class UserServiceApplicationTests {

    @Container
    protected static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("user")
                    .withUsername("mozz")
                    .withPassword("1234");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  //objects to JSON

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    private static void registerDatasourceProperties (DynamicPropertyRegistry registry)
    {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void shouldCreateUser () throws Exception {

        UserDto userDto = new UserDto();
        userDto.setName("mozza");
        userDto.setPassword("1234");
        userDto.setEmail("mozza@io.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());


        //getAll Customers

        String responseContent = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<UserDto> userDtoList = objectMapper.readValue(responseContent,
                objectMapper.getTypeFactory().constructCollectionType(List.class, UserDto.class));

        Assertions.assertEquals(1, userDtoList.size());
        Assertions.assertEquals("mozza", userDtoList.get(0).getName());
        Assertions.assertEquals("mozza@io.com", userDtoList.get(0).getEmail());


    }

}

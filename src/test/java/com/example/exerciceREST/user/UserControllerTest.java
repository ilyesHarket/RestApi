package com.example.exerciceREST.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    private User mockUser1;
    private User mockUser2;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Setup mock users
        mockUser1 = new User();
        mockUser1.setId(1);
        mockUser1.setName("Test User 1");
        mockUser1.setEmail("testuser1@example.com");

        mockUser2 = new User();
        mockUser2.setId(2);
        mockUser2.setName("Test User 2");
        mockUser2.setEmail("testuser2@example.com");
    }

    @Test
    void testCreateUser_Success() throws Exception {

        User newUser = new User();
        newUser.setName("New User");
        newUser.setEmail("newuser@example.com");

        when(userService.newUser(any(User.class))).thenReturn(new ResponseEntity<>(newUser, HttpStatus.CREATED));


        mockMvc.perform(post("/api/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New User\", \"email\":\"newuser@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New User"))
                .andExpect(jsonPath("$.email").value("newuser@example.com"));
        verify(userService, times(1)).newUser(any(User.class));
    }
}

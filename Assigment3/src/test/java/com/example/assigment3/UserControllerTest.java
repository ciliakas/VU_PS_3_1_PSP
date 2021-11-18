package com.example.assigment3;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private final User existingUser = new User((long) 1,"Vardenis", "Pavardenis",
            "+37062527965", "email@prov.com", "an address", "A!abc1324");

    @Autowired
    private MockMvc mvc;

    @Test
    public void getUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                .content(existingUser.toStringWithoutId()))
                .andExpect(status().isOk());
    }

    @Test
    public void postUserBadRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
                .content("nonsense"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNonExistingUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/99999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void getNonExistingUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/99999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void putNonExistingUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/users/99999").contentType(MediaType.APPLICATION_JSON)
                .content(existingUser.toStringWithoutId()))
                .andExpect(status().isNotFound());
    }
}
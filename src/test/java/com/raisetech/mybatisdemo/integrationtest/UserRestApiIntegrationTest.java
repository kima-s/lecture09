package com.raisetech.mybatisdemo.integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.raisetech.mybatisdemo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRestApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void ユーザーが全件取得できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/names"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                [{
                   "name": "清⽔"
                },
                {
                   "name": "⼩⼭"
                },
                {
                   "name": "⽥中"
                }]
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void IDに紐づくユーザーが1件取得できること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/names/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        JSONAssert.assertEquals("""
                {
                   "id": 1,
                   "name": "清⽔",
                   "address": "Aichi",
                   "age": 13
                }
                """, response, JSONCompareMode.STRICT);
    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void 存在しないユーザーのIDを指定したときに例外が返されること() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/names/99"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        Exception exception = result.getResolvedException();
        assertThat(exception.getClass()).isEqualTo(ResourceNotFoundException.class);
    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @ExpectedDataSet(value = "datasets/expectedUsers.yml", ignoreCols = "id")
    @Transactional
    void ユーザーを１件登録できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                   "id": 4,
                                   "name": "山中",
                                   "address": "Mie",
                                   "age": 38
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @ExpectedDataSet(value = "datasets/expectedUsers2.yml")
    @Transactional
    void ユーザーを１件更新できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/3")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                   "id": 3,
                                   "name": "山中",
                                   "address": "Mie",
                                   "age": 38
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @ExpectedDataSet(value = "datasets/expectedUsers3.yml")
    @Transactional
    void ユーザーを１件削除できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/3"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

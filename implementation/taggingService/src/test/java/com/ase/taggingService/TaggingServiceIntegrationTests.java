package com.ase.taggingService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ase.common.taggingEvent.ETags;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaggingController.class)
@AutoConfigureTestDatabase
public class TaggingServiceIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaggingService taggingService;

    @Test
    public void testaddEventTagEndpoint_createBookmarkEvent() throws Exception{

        TaggingEvent taggingEvent = new TaggingEvent("testevent1", "testuser1", ETags.EDUCATION);
        ArrayList tags = new ArrayList<>();
        tags.add(ETags.EDUCATION);
        when(taggingService.addNewTaggingEvent("testevent1", "testuser1", tags)).thenReturn(taggingEvent);

        mockMvc.perform(post("/api/v1/tag/add/testevent1/testuser1/EDUCATION")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());;

    }

    @Test
    public void testgetEvent_getEventById() throws Exception{

        TaggingEvent taggingEvent = new TaggingEvent("testevent2", "testuser2", ETags.EDUCATION);
        ArrayList tags = new ArrayList<>();
        tags.add(ETags.HEALTH);
        when(taggingService.addNewTaggingEvent("testevent2", "testuser2", tags)).thenReturn(taggingEvent);

        mockMvc.perform(post("/api/v1/tag/add/testevent2/testuser2/EDUCATION")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());;


        mockMvc.perform(get("/api/v1/tag/event/testevent2/testuser2")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testremoveEventTag_removeTag() throws Exception{

        TaggingEvent taggingEvent = new TaggingEvent("testevent2", "testuser2", ETags.EDUCATION);
        ArrayList tags = new ArrayList<>();
        tags.add(ETags.HEALTH);
        when(taggingService.addNewTaggingEvent("testevent2", "testuser2", tags)).thenReturn(taggingEvent);

        mockMvc.perform(post("/api/v1/tag/add/testevent2/testuser2/EDUCATION")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


        mockMvc.perform(post("/api/v1/tag/add/testevent2/testuser2/HEALTH")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());


        mockMvc.perform(put("/api/v1/tag/removeTag/testevent2/testuser2/EDUCATION")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    @Test
    public void testremoveEvent_removeEvent() throws Exception{

        TaggingEvent taggingEvent = new TaggingEvent("testevent2", "testuser2", ETags.EDUCATION);
        ArrayList tags = new ArrayList<>();
        tags.add(ETags.HEALTH);
        when(taggingService.addNewTaggingEvent("testevent2", "testuser2", tags)).thenReturn(taggingEvent);

        mockMvc.perform(post("/api/v1/tag/add/testevent2/testuser2/EDUCATION")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        mockMvc.perform(delete("/api/v1/tag/removeEvent/testevent2/testuser2")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

}

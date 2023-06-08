package com.ase.bookmarkService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookmarkController.class)
@AutoConfigureTestDatabase
public class BookmarkServiceIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookmarkService bookmarkService;

    @Test
    public void testBookmarkEventEndpoint_createBookmark() throws Exception {
        BookmarkEvent bookmarkEvent = new BookmarkEvent("testevent1", "testuser1");
        when(bookmarkService.addBookmarkEvent("testevent1", "testuser1")).thenReturn(bookmarkEvent);

        mockMvc.perform(post("/api/v1/bookmark/add/testuser1/testuser1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnbookmarkEventEndpoint_deleteBookmarkEvent() throws Exception {

        mockMvc.perform(delete("/api/v1/bookmark/unbookmarkEvent/testevent1/testuser1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookmarkedEventuserEndpoint_bookmarksForUser() throws Exception {
        List<BookmarkEvent> bookmarkEvents = new ArrayList<>();
        bookmarkEvents.add(new BookmarkEvent("testevent1", "testuser1"));
        bookmarkEvents.add(new BookmarkEvent("testevent2", "testuser1"));

        when(bookmarkService.getBookmarkedEventsForUser("testuser1")).thenReturn(bookmarkEvents);

        mockMvc.perform(post("/api/v1/bookmark/add/testevent1/testuser1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/bookmark/add/testevent2/testuser1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/bookmark/bookmarkedEvents/testuser1"))
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].event").value("testevent1"))
                .andExpect(jsonPath("$[1].event").value("testevent2"));
    }
}

package szu.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import szu.dto.HisQueryDto;
import szu.dto.HistoryDto;
import szu.service.HistoryService;

import java.sql.Time;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.video.service
 * @Author: Origami
 * @Date: 2023/11/25 15:12
 */
@SpringBootTest
@AutoConfigureMockMvc
public class HistoryTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private HistoryService historyService;

    @Test
    public void testAddHistory() throws Exception {
        HistoryDto historyDto = new HistoryDto();
        // 设置historyDto的属性
        historyDto.setMediaId(2);
        historyDto.setMediaType(1);
        historyDto.setWatchedAt(new Time(123112112L));
        historyDto.setUserId(2);
        mockMvc.perform(post("/his")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(historyDto)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(historyService, times(1)).addHistory(historyDto);
    }

    @Test
    public void testUpdateHistory() throws Exception {
        HistoryDto historyDto = new HistoryDto();
        // 设置historyDto的属性
        historyDto.setMediaId(2);
        historyDto.setMediaType(1);
        historyDto.setWatchedAt(new Time(1231112L));
        historyDto.setUserId(2);
        mockMvc.perform(put("/his")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(historyDto)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(historyService, times(1)).updateHistory(historyDto);
    }

    @Test
    public void testGetRecentHistory() throws Exception {
        HisQueryDto hisQueryDto = new HisQueryDto();
        // 设置hisQueryDto的属性

        mockMvc.perform(get("/his")
                        .param("cntStart", "1")
                        .param("cntEnd", "20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(hisQueryDto)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(historyService, times(1)).getRecentHistory(hisQueryDto, 1, 20);
    }
}

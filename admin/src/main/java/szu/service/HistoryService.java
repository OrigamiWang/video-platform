package szu.service;

import szu.dto.HisQueryDto;
import szu.dto.HistoryDto;
import szu.model.History;

import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service
 * @Author: Origami
 * @Date: 2023/11/25 10:57
 */
public interface HistoryService {
    void addHistory(HistoryDto historyDto);

    void updateHistory(HistoryDto historyDto);

    List<History> getRecentHistory(HisQueryDto hisQueryDto, int cntStart, int cntEnd);
}

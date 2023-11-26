package szu.service.impl;

import org.springframework.stereotype.Service;
import szu.dao.HistoryDao;
import szu.dto.HisQueryDto;
import szu.dto.HistoryDto;
import szu.model.History;
import szu.service.HistoryService;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.List;

/**
 * @BelongsProject: video-platform
 * @BelongsPackage: szu.service.impl
 * @Author: Origami
 * @Date: 2023/11/25 10:57
 */
@Service
public class HistoryServiceImpl implements HistoryService {
    @Resource
    private HistoryDao historyDao;

    @Override
    public void addHistory(HistoryDto historyDto) {
        Integer userId = historyDto.getUserId();
        Integer mediaId = historyDto.getMediaId();
        Integer mediaType = historyDto.getMediaType();
        Time watchedAt = historyDto.getWatchedAt();
        historyDao.insertHis(userId, mediaId, mediaType, watchedAt);
    }

    @Override
    public void updateHistory(HistoryDto historyDto) {
        Integer userId = historyDto.getUserId();
        Integer mediaId = historyDto.getMediaId();
        Integer mediaType = historyDto.getMediaType();
        Time watchedAt = historyDto.getWatchedAt();
        historyDao.updateHis(userId, mediaId, mediaType, watchedAt);
    }

    @Override
    public List<History> getRecentHistory(HisQueryDto hisQueryDto, int cntStart, int cntEnd) {
        int offset = cntStart - 1;
        int count = cntEnd - cntStart + 1;
        return historyDao.selectRecentHis(hisQueryDto.getUserId(), hisQueryDto.getMediaType(), offset, count);
    }


}

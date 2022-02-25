package com.twogenesis.shoppingmall_service.mapper;

import com.twogenesis.shoppingmall_service.data.MemberProductHistroyVO;
import com.twogenesis.shoppingmall_service.data.PageConnectHistoryVO;
import com.twogenesis.shoppingmall_service.data.ReviewReportHistoryVO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryMapper {
    void insertReviewReportHistory(ReviewReportHistoryVO data);
    void insertPageConnectHistory(PageConnectHistoryVO data);
    void insertMemberProductHistory(MemberProductHistroyVO data);
}

package moviegoods.movie.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moviegoods.movie.domain.dto.Manager.ManagerResponseDto;
import moviegoods.movie.domain.entity.Content_Detail.Content_Detail;
import moviegoods.movie.domain.entity.Report.Report;
import moviegoods.movie.domain.entity.Report.ReportRepository;
import moviegoods.movie.domain.entity.Transaction.Transaction;
import moviegoods.movie.domain.entity.User.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ReportRepository reportRepository;

    public List<ManagerResponseDto> show() {
        List<ManagerResponseDto> reportsList = new ArrayList<>();
        List<Report> reports = reportRepository.findAll();
        for (Report report : reports) {
            Content_Detail content_detail = report.getContent_detail();
            String content = content_detail.getContent();
            LocalDateTime written_date = content_detail.getWritten_date();

            Transaction transaction = report.getTransaction();
            User user = transaction.getUser();
            String nickname = user.getNickname();

            reportsList.add(new ManagerResponseDto(content, written_date, nickname));
        }

        Comp comp = new Comp();
        Collections.sort(reportsList, comp);

        return reportsList;
    }

    private static class Comp implements Comparator<ManagerResponseDto> {

        @Override
        public int compare(ManagerResponseDto responseDto1, ManagerResponseDto responseDto2) {

            LocalDateTime date1 = responseDto1.getWritten_date();
            LocalDateTime date2 = responseDto2.getWritten_date();

            int result = date1.compareTo(date2);
            return result;
        }
    }
}

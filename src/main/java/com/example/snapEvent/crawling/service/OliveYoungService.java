package com.example.snapEvent.crawling.service;

import com.example.snapEvent.crawling.dto.OliveYoungDto;
import com.example.snapEvent.crawling.embedded.DateRange;
import com.example.snapEvent.crawling.entity.OliveYoung;
import com.example.snapEvent.crawling.repository.OliveYoungRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class OliveYoungService {

    private final OliveYoungRepository oliveYoungRepository;


    public List<OliveYoungDto> getOliveYoungDatas() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        List<OliveYoungDto> oliveYoungDtoList = new ArrayList<>();

        String url = "https://www.oliveyoung.co.kr/store/main/getEventList.do?evtType=20&pageIdx=1&t_page=%EC%9D%B4%EB%B2%A4%ED%8A%B8&t_click=%EB%AA%A8%EB%93%A0%ED%9A%8C%EC%9B%90";

        setSSL();
        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select("ul.event_thumb_list li");

        // 10개 이벤트 목록까지만 가져오기
        List<Element> limitedContents = contents.subList(0, 10);

        for (Element content : limitedContents) {
            String title = content.select(".evt_tit").text();
            DateRange dateRange = getDateRange(content);
            String image = content.select("a img").attr("abs:data-original");
            String description = content.select(".evt_desc").text();
            String href = extractHref(content);

            // 만약 제목이 DB에 존재한다면, 기존의 저장된 DB 크롤링 데이터를 뷰로 보여줌
            if (isTitleExist(title)) {
                log.debug("Skipping duplicate title: {}", title);
                OliveYoung existingOliveYoung = oliveYoungRepository.findByTitle(title);
                if (existingOliveYoung != null) {
                    oliveYoungDtoList.add(new OliveYoungDto(existingOliveYoung));
                }
                continue;
            }

            OliveYoung oliveYoung = OliveYoung.builder()
                    .title(title)
                    .dateRange(dateRange)
                    .image(image)
                    .description(description)
                    .href(href)
                    .build();
            oliveYoungRepository.save(oliveYoung);
            oliveYoungDtoList.add(new OliveYoungDto(oliveYoung));
        }

        return oliveYoungDtoList;
    }

    public static void setSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {

                        return null;
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {


                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {

                    }
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    private boolean isTitleExist(String title) {
        return oliveYoungRepository.existsByTitle(title);
    }

    private DateRange getDateRange(Element content) {
        String dateText = content.select(".evt_date").text();
        LocalDate startDate = null;
        LocalDate endDate = null;

        // '-'를 기준으로 시작 날짜, 종료 날짜를 나눔
        String[] dateParts = dateText.split("-");

        if (dateParts.length == 2) {

            String startDateStr = dateParts[0].trim();
            startDate = parseDate(startDateStr);

            // Extract end date
            String endDateStr = dateParts[1].trim();
            endDate = parseDate(endDateStr);
        } else {
            // Handle invalid date format
            log.error("Invalid date format={}", dateText);
        }

        return new DateRange(startDate, endDate);

    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
        return LocalDate.parse(dateStr, formatter);
    }

    private String extractHref(Element content) {
        Element inputElement = content.selectFirst("input[name=urlInfo]");
        assert inputElement != null;
        String urlInfoValue = inputElement.attr("value");
        String baseUrl = "https://www.oliveyoung.co.kr/store/";

        return baseUrl.concat(urlInfoValue);
    }

}
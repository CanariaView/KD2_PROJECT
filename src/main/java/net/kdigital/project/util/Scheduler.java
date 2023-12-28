package net.kdigital.project.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Scheduler {
	
	@Value("${wordcloud.server}")
	String url;

	@Scheduled(cron = "30 39 14 * * ?")  // 매일 14시 39분 30초에 실행
    public void sendRequestToFastAPI() {
		
		log.info("크롤링 시작");
        // FastAPI 요청 보내는 로직 작성
        RestTemplate restTemplate = new RestTemplate();
        
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 바디 설정
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        // POST 요청 보내기
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        restTemplate.exchange(url+"/crawl", HttpMethod.POST, requestEntity, Object.class);

        log.info("크롤링 끝");
    }
    
}
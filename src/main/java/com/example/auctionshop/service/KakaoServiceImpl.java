package com.example.auctionshop.service;

/*import com.google.gson.JsonObject;
import com.google.gson.JsonParser;*/
import com.example.auctionshop.dto.KakaoDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class KakaoServiceImpl implements KakaoService {


    public String getToken(String code) throws IOException {
        String host = "https://kauth.kakao.com/oauth/token";
        HttpURLConnection urlConnection = null;
        String token = "";

        try {
            URL url = new URL(host);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()))) {
                StringBuilder sb = new StringBuilder();
                sb.append("grant_type=authorization_code");  // 수정된 부분
                sb.append("&client_id=4db3e0af6e1d6916f03390586d47359a");
                sb.append("&redirect_uri=http://localhost:8080/auth/kakao/callback");  // 고정된 값 사용
                sb.append("&code=").append(code);

                bw.write(sb.toString());
                bw.flush();
            }
;

            int responseCode = urlConnection.getResponseCode();
            log.info("responseCode = " + responseCode);

            if (responseCode == 200) { // 성공한 경우만 처리
                StringBuilder result = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }
                }

                log.info("result = " + result.toString());

             /*   JsonParser parser = new JsonParser();
                JsonObject elem = parser.parse(result.toString()).getAsJsonObject();

                token = elem.get("access_token").getAsString();*/

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(result.toString());

                // "access_token" 값을 가져오기
                String accessToken = jsonNode.get("access_token").asText();
                token = accessToken;




            } else {
                log.error("Failed to get token. Response code: " + responseCode);
            }

        } catch (IOException e) {
            log.error("Error occurred: " + e.getMessage(), e);
            throw e;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return token;
    }

    @Override
    public KakaoDTO userInfo(String access_Token) throws IOException {
        log.info("사용자 정보 가져오기 시작------------------------");
        KakaoDTO userInfo = new KakaoDTO();

        String reqURl = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqURl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            log.info("응답 코드: " + responseCode);

            if (responseCode == 200) {  // 성공한 경우에만 처리
                StringBuilder result = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        result.append(line);
                    }
                }

                log.info("카카오 사용자 정보 응답 내용: " + result.toString());  // 전체 응답 로그 출력

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(result.toString());

                // "access_token" 값을 가져오기
                String id = jsonNode.get("id").asText();

                log.info("카카오 사용자 아이디 " + id);


                String nickName = jsonNode.get("properties").get("nickname").asText();

                log.info("카카오 닉네임 " + nickName);

                userInfo.setNickname(nickName);
                userInfo.setKakaoId(id);
                Map<String, Object> attributes = new HashMap<>();

                attributes.put("id", id);
                attributes.put("nickname", nickName);

                userInfo.setAttributes(attributes);


            } else {
                log.error("사용자 정보를 가져오는 데 실패했습니다. 응답 코드: " + responseCode);
            }

        } catch (Exception e) {
            log.error("사용자 정보 가져오는 중 예외 발생: " + e.getMessage(), e);
        }

        return userInfo;
    }


}

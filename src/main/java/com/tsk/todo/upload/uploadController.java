package com.tsk.todo.upload;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * @author Tsk
 * @date 2024/7/10 0010
 */
@RestController
@RequestMapping("/upload")
public class uploadController {
    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://picui.cn/api/v1";

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("file", file.getResource()); // 将MultipartFile转换为FileResource添加到MultiValueMap

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(MULTIPART_FORM_DATA_VALUE));
        httpHeaders.set(HttpHeaders.AUTHORIZATION,"Bearer 348|NQlBff1Zx3wWi2C3gUCPP0ZofNPRlmsNgnwAbXDN");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        String responseBody = responseEntity.getBody();
        int statusCode = responseEntity.getStatusCode().value();
        HttpHeaders responseHeaders = responseEntity.getHeaders();

        System.out.println("Response body: " + responseBody);
        System.out.println("Status code: " + statusCode);
        System.out.println("Response headers: " + responseHeaders);
        return "上传成功";
    }
}

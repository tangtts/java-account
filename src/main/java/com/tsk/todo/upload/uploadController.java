package com.tsk.todo.upload;

import com.tsk.todo.exception.ResultResponse;
import com.tsk.todo.exception.StatusEnum;
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

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * @author Tsk
 * @date 2024/7/10 0010
 */
@RestController
@RequestMapping("/upload")
public class uploadController {
    @PostMapping
    public ResultResponse upload(@RequestParam("file") MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://picui.cn/api/v1/upload";

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("file", file.getResource());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(MULTIPART_FORM_DATA_VALUE));

        httpHeaders.setAccept((List.of(APPLICATION_JSON)));

        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer 465|ITEG4ZZSDvryY1Swj6uejkpQg4mLs2VwPFdVFo6c");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        int statusCode = responseEntity.getStatusCode().value();

        if (statusCode == 200) {
            return ResultResponse.success(responseBody);
        } else {
            return ResultResponse.error(StatusEnum.SERVICE_ERROR, "上传失败");
        }
    }
}

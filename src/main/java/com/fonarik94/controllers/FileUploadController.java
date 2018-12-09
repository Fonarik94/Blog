package com.fonarik94.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping(value = "/administration")
public class FileUploadController {
    @Value("${images.path}")
    private String path;

    @PostMapping(value = "/postwriter/upload")
    @ResponseBody
    public String[] uploader(@RequestParam("file") MultipartFile[] files) throws IOException {
        List<String> fileNames = new ArrayList<>();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (MultipartFile file : files) {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                fileNames.add(resultFilename);
                String resultPath = path + "/" + resultFilename;
                file.transferTo(new File(resultPath));
            }
        }
        return fileNames.toArray(new String[0]);
    }
}

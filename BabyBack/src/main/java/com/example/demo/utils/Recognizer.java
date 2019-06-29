package com.example.demo.utils;

import com.example.demo.entity.ResponseBase;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.RectangularShape;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Recognizer {

    FileManager fileManager = new FileManager();

    public ResponseBase recognition(MultipartFile file, String fileName) {
        ResponseBase responseBase;
        ArrayList<String> matches = new ArrayList<>();
        try {
            file.transferTo(fileManager.generateFile(FileManager.Path.TEMP, fileName));
            String[] cmd = {"docker", "exec", "fr_3", "face_recognition", "/photo/lost", "/photo/temp/" + fileName};
            Process p = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String tempStr = input.readLine();
            while (tempStr != null) {
                String id = tempStr.split(",")[1];
                matches.add(id);
                tempStr = input.readLine();
            }
            input.close();
            ir.close();

            responseBase = new ResponseBase(200, "待识别照片上传成功", matches);
        } catch (Exception e) {
            e.printStackTrace();
            responseBase = new ResponseBase(40005, "待识别照片上传异常", null);
        }

        return responseBase;
    }

}

package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.reposity.LostBabyRepository;
import com.example.demo.reposity.MatchBabyRepository;
import com.example.demo.reposity.UserRepository;
import com.example.demo.utils.FileManager;
import com.example.demo.utils.Recognizer;
import com.example.demo.utils.UserInformer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping(value = "file")
public class FileUploadController {

    private static final String FILE_NAME_TEMPLATE = "%s.%s";
    private static final String FILE_PATH_TEMPLATE = "%s%s%s.%s";

    String photoBaseUrl = "/resource/photo/";

    @Value("${server.port}")
    private String port;

    @Value("${eureka.instance.ip-address}")
    private String IP;

    enum Action {
        AS_PROFILE,
        AS_LOST_PICS,
        AS_MATCH_PICS,
        RECOGNITION
    }

    private Recognizer recognizer = new Recognizer();
    private FileManager fileManager = new FileManager();

    @Autowired
    UserRepository userRepository;
    @Autowired
    LostBabyRepository lostBabyRepository;
    @Autowired
    MatchBabyRepository matchBabyRepository;

    UserInformer userInformer = new UserInformer();

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public ResponseBase uploadPic(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "id") String id, Action action) {
        ResponseBase responseBase;
        String postfix = "";
        ArrayList<String> matches = null;

        String fileName;
        try {
            file.getOriginalFilename();
            file.getName();
            postfix = file.getOriginalFilename().split("\\.")[1];
            fileName = String.format("%s.%s", id, postfix);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBase(40001, "上传文件名异常", null);
        }

        switch (action) {
            case AS_LOST_PICS:
                responseBase = fileManager.saveLostPic(file, fileName);
                matchOnInsertAndInfo(id, file);
                break;
            case AS_MATCH_PICS:
                responseBase = fileManager.saveMatchPic(file, fileName);
                break;
            case AS_PROFILE:
                responseBase = fileManager.saveProfile(file, fileName);
                break;
            case RECOGNITION:
                responseBase = recognizer.recognition(file, String.format(FILE_NAME_TEMPLATE, generateRandomFilename(), postfix), Recognizer.MatchTarget.LOST_BABY);
                break;
            default:
                responseBase = new ResponseBase(40006, "未知图片上传行为", null);
        }

        updateEntity(id, action, postfix);

        return responseBase;
    }

    @ApiOperation("自然语言分析接口")
    @PostMapping("/analyze-txt")
    public ResponseBase getTextAndRecog(@RequestParam String txt){
        return recognizer.analyze(txt, Recognizer.MatchTarget.LOST_BABY);
    }

    public String generateRandomFilename() {
        String RandomFilename;
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();

        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        String now = String.valueOf(intYear) + "_" + String.valueOf(intMonth) + "_" +
                String.valueOf(intDay) + "_";

        RandomFilename = now + String.valueOf(random > 0 ? random : (-1) * random);

        return RandomFilename;
    }

    private void updateEntity(String id, Action action, String suffix){

        int nId = Integer.valueOf(id);
        photoBaseUrl = "http://"+IP + ":"+ port+"/resource/photo/";

        switch (action){
            case AS_PROFILE:
                User user = userRepository.findById(nId).get();
                user.setProfileUrl(String.format(FILE_PATH_TEMPLATE, photoBaseUrl, "profile/", id, suffix));
                userRepository.save(user);
                break;
            case AS_LOST_PICS:
                LostBaby lostBaby = lostBabyRepository.findById(nId).get();
                lostBaby.setPicUrl(String.format(FILE_PATH_TEMPLATE, photoBaseUrl, "lost/", id, suffix));
                lostBabyRepository.save(lostBaby);
                break;
            case AS_MATCH_PICS:
                MatchBaby matchBaby = matchBabyRepository.findById(nId).get();
                matchBaby.setPicUrl(String.format(FILE_PATH_TEMPLATE, photoBaseUrl, "match/", id, suffix));
                matchBabyRepository.save(matchBaby);
                break;
            default:
                break;
        }
    }

    void matchOnInsertAndInfo(String lostBabyId, MultipartFile picFile){
        LostBaby lostBaby = lostBabyRepository.findById(Integer.valueOf(lostBabyId)).get();
        ArrayList<Integer> matchedIDs = matchSynthetically(lostBaby, picFile);

        User user = lostBaby.getUser();
        ArrayList<Integer> userIDs = new ArrayList<>();
        userIDs.add(user.getId());

        String matchResult = "";
        for(Integer id : matchedIDs){
            matchResult += (id + ",");
        }

        userInformer.infoUser(userIDs, PendingMessage.MessageType.MATCH_NOTIFICATION, matchResult);
    }

    private ArrayList<Integer> matchSynthetically(LostBaby lostBaby, MultipartFile pic){

        ArrayList<Integer> matchedIDs = matchOnInsertInfo(lostBaby);

        String postfix = "";
        try {
            pic.getOriginalFilename();
            pic.getName();
            postfix = pic.getOriginalFilename().split("\\.")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseBase responseBase = recognizer.recognition(pic, String.format(FILE_NAME_TEMPLATE, generateRandomFilename(), postfix), Recognizer.MatchTarget.MATCH_BABY);
        ArrayList<MatchBaby> matchBabies = (ArrayList<MatchBaby>) responseBase.getData();
        if(matchBabies != null) {
            for (MatchBaby matchBaby : matchBabies) {
                if (!matchedIDs.contains(matchBaby.getId())) {
                    matchedIDs.add(matchBaby.getId());
                }
            }
        }

        return matchedIDs;
    }

    ArrayList<Integer> matchOnInsertInfo(LostBaby lostBaby){

        String info = lostBaby.toString();
        ResponseBase responseBase = recognizer.analyze(info, Recognizer.MatchTarget.MATCH_BABY);
        ArrayList<MatchBaby> matchBabies = (ArrayList<MatchBaby>)responseBase.getData();
        ArrayList<Integer> matchIDs = new ArrayList<>();
        if(matchBabies == null){
            return new ArrayList<>();
        }
        for(MatchBaby matchBaby : matchBabies){
            matchIDs.add(matchBaby.getId());
        }

        return matchIDs;
    }

}

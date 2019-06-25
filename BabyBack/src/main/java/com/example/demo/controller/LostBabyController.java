package com.example.demo.controller;

import com.example.demo.entity.KeyWord;
import com.example.demo.entity.LostBaby;
import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.KeyWordRepository;
import com.example.demo.reposity.LostBabyRepository;
import com.example.demo.service.ApiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "LostBaby")
public class LostBabyController {
    @Autowired
    ApiService apiService;
    @Autowired
    LostBabyRepository lostBabyRepository;
    @Autowired
    KeyWordRepository keyWordRepository;
    @ApiOperation(value="新增一个丢失儿童信息")
    @PutMapping("")
    public ResponseBase insertLostBaby(@RequestBody LostBaby lostBaby){
         lostBabyRepository.save(lostBaby);
         lostBaby=lostBabyRepository.findById(lostBaby.getId()).get();

        for(String str: apiService.initKeyWord(apiService.initDescription(lostBaby))){
            if(keyWordRepository.existsByName(str)){
                KeyWord existK=keyWordRepository.findByName(str);
                existK.getLostBabies().add(lostBaby);
                keyWordRepository.save(existK);
            }else{
                KeyWord kw=new KeyWord(str);
                kw.getLostBabies().add(lostBaby);
                keyWordRepository.save(kw);
            }
        }


         return new ResponseBase(200,"插入成功",lostBaby);
    }
    @ApiOperation(value="查找功能")
    @PostMapping("")
    public ResponseBase findLostBaby(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息")
                                       Pageable pageable,
                                        @RequestParam(value = "id",required = false,defaultValue ="") String id,
                                        @RequestParam(value = "place",required = false,defaultValue ="") String place,
                                        @RequestParam(value = "name",required = false,defaultValue ="") String name,
                                        @RequestParam(value = "height",required = false,defaultValue ="") String height,
                                        @RequestParam(value = "nativePlace",required = false,defaultValue ="") String nativePlace,
                                        @RequestParam(value = "date",required = false,defaultValue ="") String date
    ){
        Specification<LostBaby> lostBabySpecification =apiService.createLostBabySpecification(id,place,name,height,nativePlace,date);
        return new ResponseBase(200,"查找成功",lostBabyRepository.findAll(lostBabySpecification,pageable));
    }
    @ApiOperation(value="新增一个用户")
    @DeleteMapping("")
    public ResponseBase deleteUser(@RequestParam(value = "id") Integer id){
        lostBabyRepository.deleteById(id);
        return new ResponseBase().succes("删除成功");
    }
    @ApiOperation(value="根据关键字查找用户")
    @GetMapping("/{key}")
    public ResponseBase findLostBabyByKey(@PathVariable String key){
        Set<LostBaby> set=new HashSet<>();
        for (String str:apiService.initKeyWord(key)) {
            set.addAll(keyWordRepository.findByName(str).getLostBabies());
        }
        return new ResponseBase(200,"查询成功",set);
    }

}
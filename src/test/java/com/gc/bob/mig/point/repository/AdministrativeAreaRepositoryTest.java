package com.gc.bob.mig.point.repository;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gc.bob.mig.point.entity.AdministrativeArea;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.StringUtils.isEmpty;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdministrativeAreaRepositoryTest {

    @Autowired
    private AdministrativeAreaRepository administrativeAreaRepository;

    @Test
    public void test_findByLocationFalse() {
        List<AdministrativeArea> byLocationFalse = administrativeAreaRepository.findByLocationExists(false);
        for (AdministrativeArea area: byLocationFalse) {

            System.out.println(area.getName());
            System.out.println(modifyExceptionQuery(area.getSdName(), area.getSggName(), area.getName()));
        }
    }

    @Test
    public void test_findByType() {
        List<AdministrativeArea> sggs = administrativeAreaRepository.findByType("sgg");
        Assertions.assertNotNull(sggs);
        List<AdministrativeArea> umds = administrativeAreaRepository.findByType("umd");
        Assertions.assertNotNull(umds);
    }

    private String modifyExceptionQuery(String sdName, String sggName, String name) {

        if (StringUtils.contains(name, "·")) {
            name = name.replace("·", "");
        } else if (StringUtils.equals(sggName, "금정구") && StringUtils.equals(name, "장전3동")) {
            name = "장전동";
        } else if (StringUtils.equals(sdName, "인천광역시")
            && (StringUtils.equals(name, "검단1동") || StringUtils.equals(name, "검단2동")
            || StringUtils.equals(name, "검단3동") || StringUtils.equals(name, "검단4동")
            || StringUtils.equals(name, "검단5동"))) {
            name = "검단동";
        } else if (StringUtils.equals(sggName, "수원시 영통구") && StringUtils.equals(name, "태장동")) {
            name = "망포2동";
        } else if (StringUtils.equals(sggName, "의정부시") && (
            StringUtils.equals(name, "가능1동") || StringUtils.equals(name, "가능2동")
                || StringUtils.equals(name, "가능3동"))) {
            name = "가능동";
        } else if (StringUtils.equals(sggName, "안산시 상록구")
            && (StringUtils.equals(name, "사1동") || StringUtils.equals(name, "사3동"))) {
            name = "사동";
        } else if (StringUtils.equals(sggName, "안산시 단원구") && StringUtils.equals(name, "원곡본동")) {
            name = "신길동";
        } else if (StringUtils.equals(sggName, "안산시 단원구")
            && (StringUtils.equals(name, "원곡1동") || StringUtils.equals(name, "원곡2동"))) {
            name = "백운동";
        } else if (StringUtils.equals(sggName, "고양시 덕양구") && StringUtils.equals(name, "신도동")) {
            name = "삼송동";
        } else if (StringUtils.equals(sggName, "김포시") && StringUtils.equals(name, "김포1동")) {
            name = "김포본동";
        } else if (StringUtils.equals(sggName, "김포시") && StringUtils.equals(name, "김포2동")) {
            name = "장기본동";
        } else if (StringUtils.equals(sggName, "화성시") && StringUtils.equals(name, "동탄면")) {
            name = "동탄6동";
        } else if (StringUtils.equals(sggName, "제천시") && StringUtils.equals(name, "의암동")) {
            name = "의림지동";
        } else if (StringUtils.equals(sggName, "제천시") && StringUtils.equals(name, "인성동")) {
            name = "중앙동";
        } else if (StringUtils.equals(sggName, "진주시")
            && (StringUtils.equals(name, "상대1동") || StringUtils.equals(name, "상대2동"))) {
            name = "상대동";
        } else if (StringUtils.equals(sggName, "진주시")
            && (StringUtils.equals(name, "하대1동") || StringUtils.equals(name, "하대2동"))) {
            name = "상대동";
        } else if (StringUtils.equals(sggName, "창원시 마산합포구") && StringUtils.equals(name, "동서동")) {
            name = "오동동";
        } else if (StringUtils.equals(sggName, "창원시 마산합포구") && StringUtils.equals(name, "노산동")) {
            name = "교방동";
        } else if (StringUtils.equals(sggName, "창원시 마산회원구")
            && (StringUtils.equals(name, "석전1동") || StringUtils.equals(name, "석전2동"))) {
            name = "석전동";
        }
        if (StringUtils.equals(sdName, "세종특별자치시")) {
            return sdName + " " + name;
        }
        if (!isEmpty(sdName) && !isEmpty(sggName)) {
            return sdName + " " + sggName + " " + name;
        } else if (isEmpty(sdName) && !isEmpty(sggName)) {
            return sggName + " " + name;
        } else {
            return name;
        }
    }

}
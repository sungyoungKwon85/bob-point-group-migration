package com.gc.bob.mig.point.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gc.bob.mig.point.entity.AdministrativeArea;
import com.gc.bob.mig.point.entity.Location;
import com.gc.bob.mig.point.model.NaverGeocodeResponse;
import com.gc.bob.mig.point.model.NaverGeocodeResponse.Address;
import com.gc.bob.mig.point.repository.AdministrativeAreaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdministrativeCenterMigrationService {

    private final AdministrativeAreaRepository administrativeAreaRepository;
    private final NaverCloudPlatformService naverCloudPlatformService;

    @Transactional
    public void migrateCenterOfAdministrativeArea_testOne() {
        List<AdministrativeArea> all = new ArrayList<>();
        AdministrativeArea administrativeArea = administrativeAreaRepository.findById("11").orElse(null);
        if (administrativeArea != null) {
            all.add(administrativeArea);
        }
        migrateCenter(all);
    }

    @Transactional
    public void migrateCenterOfAdministrativeArea() {
        List<AdministrativeArea> all = administrativeAreaRepository.findAll();
        migrateCenter(all);
    }

    @Transactional
    public void migrateCenterOfAdministrativeArea_missing() {
        List<AdministrativeArea> all = administrativeAreaRepository.findByLocationExists(false);
        migrateCenter(all);
    }

    private void migrateCenter(List<AdministrativeArea> all) {
        log.debug("========list for migration's size: {}", all.size());
        for (AdministrativeArea it : all) {
            String query = "";
            String sdName = it.getSdName();
            String sggName = it.getSggName();
            String name = it.getName();
            query = modifyExceptionQuery(sdName, sggName, name);

            NaverGeocodeResponse geocode = naverCloudPlatformService.geocode(query);
            if (geocode.getAddresses() != null && geocode.getAddresses().size() > 0) {
                Address address = geocode.getAddresses().get(0);
                it.setLocation(Location.builder()
                    .type("Point")
                    .coordinates(Arrays.asList(
                        Double.parseDouble(address.getX()),
                        Double.parseDouble(address.getY())))
                    .build());
            }
        }
        administrativeAreaRepository.saveAll(all);
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

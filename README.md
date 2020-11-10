# 식당, 행정구역 + geo, reversegeo Migration

## AdministrativeCenterMigrationService
행정구역 + geo -> center 정보 추가  



## RestaurantUmdMigrationService
식당 + reversegeo -> umd 정보 추가  
- 112건에 대해 좌표가 비정상이라 추가못함 

## PointGroupService
시군구/읍면동 별 축적에 따른 그룹을 미리 생성하여 db화 



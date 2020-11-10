# 식당, 행정구역 + geo, reversegeo Migration

## AdministrativeCenterMigrationService
행정구역 + geo -> center 정보 추가  
- 63건에 대해 query 검색 결과에 안나와서 추가 못함
- 경기도 수원시 영통구 태장동은 2018년부터 분할 편입되었음 ㅠ -> 망포2동 


## RestaurantUmdMigrationService
식당 + reversegeo -> umd 정보 추가  
- 112건에 대해 좌표가 비정상이라 추가못함 





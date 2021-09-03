create table ACCOUNT
(
    ID        VARCHAR2(45) not null
        primary key,
    PWD       VARCHAR2(50) not null,
    USER_TYPE NUMBER(1)    not null
)
/

comment on column ACCOUNT.USER_TYPE is '1:개인
2:기업'
/

create table LOCATION
(
    LOC_CODE NUMBER(5)    not null
        constraint LOCATION_PK
            primary key,
    LOC_NAME VARCHAR2(15) not null
)
/

comment on column LOCATION.LOC_CODE is '지역코드'
/

comment on column LOCATION.LOC_NAME is '시군구'
/

create unique index LOCATION_LOC_CITY_UINDEX
    on LOCATION (LOC_CITY)
/

create table COMPANY
(
    COM_ID      VARCHAR2(45)  not null
        constraint COMPANY_PK
            primary key
        constraint COMPANY_ACCOUNT_ID_FK
            references ACCOUNT,
    COM_NAME    VARCHAR2(20)  not null,
    COM_PHONE   VARCHAR2(30)  not null,
    COM_EMAIL   VARCHAR2(40)  not null,
    COM_ZIPCODE NUMBER(5)     not null,
    COM_ADDR    VARCHAR2(255) not null,
    COM_RM      VARCHAR2(10)  not null,
    COM_BT      NUMBER        not null
)
/

comment on column COMPANY.COM_PHONE is '전화번호'
/

comment on column COMPANY.COM_EMAIL is '회사이메일'
/

comment on column COMPANY.COM_ZIPCODE is '우편번호'
/

comment on column COMPANY.COM_ADDR is '상세주소'
/

comment on column COMPANY.COM_RM is '사업자 등록번호'
/

comment on column COMPANY.COM_BT is '업종코드'
/

create table ADVERTISEMENT
(
    ADV_NO         NUMBER       not null
        constraint ADVERTISEMENT_PK
            primary key,
    ADV_COM_ID     VARCHAR2(45) not null
        constraint ADV_COMPANY_COM_ID_FK
            references COMPANY,
    ADV_LOCATION1  NUMBER       not null
        constraint ADV_LOCATION_LOC_CODE_FK1
            references LOCATION,
    ADV_LOCATION2  NUMBER
        constraint ADV_LOCATION_LOC_CODE_FK2
            references LOCATION,
    ADV_LOCATION3  NUMBER
        constraint ADV_LOCATION_LOC_CODE_FK3
            references LOCATION,
    ADV_FEE        NUMBER       not null,
    ADV_CARTYPE    NUMBER       not null,
    ADV_STARTMONTH DATE         not null,
    ADV_ENDMONTH   DATE         not null,
    ADV_DATE       DATE default SYSDATE,
    ADV_TOTAL      NUMBER       not null
)
/

comment on column ADVERTISEMENT.ADV_NO is '광고코드'
/

comment on column ADVERTISEMENT.ADV_CARTYPE is '0 : 차종무관
1 : 경차
2 : 승용차
3 : SUV
4 : 대형'
/

comment on column ADVERTISEMENT.ADV_DATE is 'default  : 현재시각'
/

comment on column ADVERTISEMENT.ADV_TOTAL is '모집인원'
/

create unique index COMPANY_COM_EMAIL_UINDEX
    on COMPANY (COM_EMAIL)
/

create unique index COMPANY_COM_PHONE_UINDEX
    on COMPANY (COM_PHONE)
/

create unique index COMPANY_COM_RM_UINDEX
    on COMPANY (COM_RM)
/

create table INDIVIDUAL
(
    IN_ID       VARCHAR2(45)  not null
        constraint INDIVIDUAL_PK
            primary key
        constraint INDIVIDUAL_ACCOUNT_ID_FK
            references ACCOUNT,
    IN_NAME     VARCHAR2(20)  not null,
    IN_PHONE    VARCHAR2(30)  not null,
    IN_EMAIL    VARCHAR2(40)  not null,
    IN_ZIPCODE  NUMBER(5)     not null,
    IN_ADDRESS  VARCHAR2(255) not null,
    IN_BIRTHDAY DATE          not null,
    IN_CAR      NUMBER(1)     not null
)
/

comment on column INDIVIDUAL.IN_ZIPCODE is '우편번호'
/

comment on column INDIVIDUAL.IN_ADDRESS is '상세주소'
/

comment on column INDIVIDUAL.IN_CAR is '차종
1 : 경차
2 : 승용차
3 : SUV
4 : 대형'
/

create table APPLY
(
    APP_NO     NUMBER                    not null
        constraint APPLY_PK
            primary key,
    APP_IN_ID  VARCHAR2(45)              not null
        constraint APPLY_INDIVIDUAL_IN_ID_FK
            references INDIVIDUAL,
    APP_ADV_NO NUMBER                    not null
        constraint APPLY_ADVERTISEMENT_ADV_NO_FK
            references ADVERTISEMENT,
    ADD_DATE   DATE      default SYSDATE not null,
    APP_STATE  NUMBER(1) default 0       not null,
    APP_BANK   VARCHAR2(20)              not null,
    APP_ACNO   VARCHAR2(30)              not null
)
/

comment on table APPLY is '광고 지원정보 테이블'
/

comment on column APPLY.APP_IN_ID is '아이디 (개인)'
/

comment on column APPLY.APP_ADV_NO is '광고번호'
/

comment on column APPLY.ADD_DATE is '광고 신청일'
/

comment on column APPLY.APP_STATE is '신청상태
-1 : 거절
0 : 대기
1 : 수락'
/

comment on column APPLY.APP_BANK is '입금받을 은행'
/

comment on column APPLY.APP_ACNO is '계좌번호'
/

create table PAYMENT
(
    PAY_NO     NUMBER               not null
        constraint PAYMENT_PK
            primary key,
    PAY_APP_NO NUMBER               not null
        constraint PAYMENT_APPLY_APP_NO_FK
            references APPLY,
    PAY_DATE   DATE default SYSDATE not null,
    PAY_MONTH  NUMBER(2)            not null,
    PAY_FEE    NUMBER               not null
)
/

comment on column PAYMENT.PAY_APP_NO is '어떤 광고에 대한 지불인지 알기위해'
/

comment on column PAYMENT.PAY_MONTH is '8월 임금 => 11월에 지급
=> 그럼 몇월달 돈을 준건지 알수가 없어
	이를 기록하기 위해 만듦'
/

comment on column PAYMENT.PAY_FEE is '1인당 월 광고지급 금액'
/

create table VERIFICATION
(
    VERI_APP_NO NUMBER              not null
        constraint VERIFICATION_APPLY_APP_NO_FK
            references APPLY,
    VERI_MONTH  DATE                not null,
    VERI_METER  NUMBER    default 0 not null,
    VERI_STATE  NUMBER(1) default 0 not null,
    constraint VERIFICATION_PK_2
        primary key (VERI_APP_NO, VERI_MONTH)
)
/

comment on column VERIFICATION.VERI_APP_NO is '신청번호
복합키'
/

comment on column VERIFICATION.VERI_MONTH is '복합키'
/

comment on column VERIFICATION.VERI_METER is '인증거리 (GPS 난수입력)'
/

comment on column VERIFICATION.VERI_STATE is '-1 : 반려(거절)
0 : 대기
1 : 승인'
/

create unique index INDIVIDUAL_IN_EMAIL_UINDEX
    on INDIVIDUAL (IN_EMAIL)
/

create unique index INDIVIDUAL_IN_PHONE_UINDEX
    on INDIVIDUAL (IN_PHONE)
/

select * from advertisement;

SELECT adv.adv_no, com.com_name, com.com_bt, adv.adv_location1, adv.adv_location2, adv.adv_location3,
 adv.adv_fee, adv.adv_total, adv.adv_cartype, adv.adv_startmonth, adv.adv_endmonth, adv.adv_date
FROM advertisement adv JOIN company com ON(adv.adv_com_id = com.com_id);
create table ACCOUNT
(
    ID        VARCHAR2(45) not null
        primary key,
    PWD       VARCHAR2(50) not null,
    USER_TYPE NUMBER(1)    not null
)
/

comment on column ACCOUNT.USER_TYPE is '1:����
2:���'
/

create table LOCATION
(
    LOC_CODE NUMBER(5)    not null
        constraint LOCATION_PK
            primary key,
    LOC_NAME VARCHAR2(15) not null
)
/

comment on column LOCATION.LOC_CODE is '�����ڵ�'
/

comment on column LOCATION.LOC_NAME is '�ñ���'
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

comment on column COMPANY.COM_PHONE is '��ȭ��ȣ'
/

comment on column COMPANY.COM_EMAIL is 'ȸ���̸���'
/

comment on column COMPANY.COM_ZIPCODE is '�����ȣ'
/

comment on column COMPANY.COM_ADDR is '���ּ�'
/

comment on column COMPANY.COM_RM is '����� ��Ϲ�ȣ'
/

comment on column COMPANY.COM_BT is '�����ڵ�'
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

comment on column ADVERTISEMENT.ADV_NO is '�����ڵ�'
/

comment on column ADVERTISEMENT.ADV_CARTYPE is '0 : ��������
1 : ����
2 : �¿���
3 : SUV
4 : ����'
/

comment on column ADVERTISEMENT.ADV_DATE is 'default  : ����ð�'
/

comment on column ADVERTISEMENT.ADV_TOTAL is '�����ο�'
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

comment on column INDIVIDUAL.IN_ZIPCODE is '�����ȣ'
/

comment on column INDIVIDUAL.IN_ADDRESS is '���ּ�'
/

comment on column INDIVIDUAL.IN_CAR is '����
1 : ����
2 : �¿���
3 : SUV
4 : ����'
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

comment on table APPLY is '���� �������� ���̺�'
/

comment on column APPLY.APP_IN_ID is '���̵� (����)'
/

comment on column APPLY.APP_ADV_NO is '�����ȣ'
/

comment on column APPLY.ADD_DATE is '���� ��û��'
/

comment on column APPLY.APP_STATE is '��û����
-1 : ����
0 : ���
1 : ����'
/

comment on column APPLY.APP_BANK is '�Աݹ��� ����'
/

comment on column APPLY.APP_ACNO is '���¹�ȣ'
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

comment on column PAYMENT.PAY_APP_NO is '� ���� ���� �������� �˱�����'
/

comment on column PAYMENT.PAY_MONTH is '8�� �ӱ� => 11���� ����
=> �׷� ����� ���� �ذ��� �˼��� ����
	�̸� ����ϱ� ���� ����'
/

comment on column PAYMENT.PAY_FEE is '1�δ� �� �������� �ݾ�'
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

comment on column VERIFICATION.VERI_APP_NO is '��û��ȣ
����Ű'
/

comment on column VERIFICATION.VERI_MONTH is '����Ű'
/

comment on column VERIFICATION.VERI_METER is '�����Ÿ� (GPS �����Է�)'
/

comment on column VERIFICATION.VERI_STATE is '-1 : �ݷ�(����)
0 : ���
1 : ����'
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
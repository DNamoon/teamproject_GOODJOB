create table memberdiv(memdiv_code varchar(45) primary key);
insert into Memberdiv(memdivCode)
values ('COMPANY');
insert into Memberdiv(memdivCode)
values ('USER');

#number 타입이라 앞에 0이 안들어감. varchar2로 전환 고려
insert into Region(regCode, regName) VALUE (02,'서울특별시');
insert into Region(regCode, regName) VALUE (031,'경기도');
insert into Region(regCode, regName) VALUE (032,'인천광역시');
insert into Region(regCode, regName) VALUE (033,'강원도');
insert into Region(regCode, regName) VALUE (041,'충청남도');
insert into Region(regCode, regName) VALUE (042,'대전광역시');
insert into Region(regCode, regName) VALUE (043,'충청북도');
insert into Region(regCode, regName) VALUE (044,'세종특별시');
insert into Region(regCode, regName) VALUE (051,'부산광역시');
insert into Region(regCode, regName) VALUE (052,'울산광역시');
insert into Region(regCode, regName) VALUE (053,'대구광역시');
insert into Region(regCode, regName) VALUE (054,'경상북도');
insert into Region(regCode, regName) VALUE (055,'경상남도');
insert into Region(regCode, regName) VALUE (061,'전라남도');
insert into Region(regCode, regName) VALUE (062,'광주광역시');
insert into Region(regCode, regName) VALUE (063,'전라북도');
insert into Region(regCode, regName) VALUE (064,'제주도');

insert into Comdiv(ComdivCode, comdivName) VALUE ('기업분류테스트1','기업분류테스트1');
insert into Comdiv(ComdivCode, comdivName) VALUE ('기업분류테스트2','기업분류테스트2');
insert into Comdiv(ComdivCode, comdivName) VALUE ('기업분류테스트3','기업분류테스트3');

#약어 사용의 애매함.
insert into Occupation(occCode, occName) VALUE ('01','관리직(임원·부서장)');
insert into Occupation(occCode, occName) VALUE ('02','경영·행정·사무직');
insert into Occupation(occCode, occName) VALUE ('03','금융·보험직');
insert into Occupation(occCode, occName) VALUE ('11','인문·사회과학');
insert into Occupation(occCode, occName) VALUE ('12','자연·생명과학');
insert into Occupation(occCode, occName) VALUE ('13','정보통신');
insert into Occupation(occCode, occName) VALUE ('14','건설·채굴');
insert into Occupation(occCode, occName) VALUE ('15','제조');
insert into Occupation(occCode, occName) VALUE ('21','교육직');
insert into Occupation(occCode, occName) VALUE ('22','법률직');
insert into Occupation(occCode, occName) VALUE ('23','사회복지·종교직');
insert into Occupation(occCode, occName) VALUE ('24','경찰·소방·교도직');
insert into Occupation(occCode, occName) VALUE ('25','군인');
insert into Occupation(occCode, occName) VALUE ('30','보건·의료직');
insert into Occupation(occCode, occName) VALUE ('41','예술·디자인·방송직');
insert into Occupation(occCode, occName) VALUE ('42','스포츠·레크리에이션직');
insert into Occupation(occCode, occName) VALUE ('51','미용·예식');
insert into Occupation(occCode, occName) VALUE ('52','여행·숙박·오락');
insert into Occupation(occCode, occName) VALUE ('53','요식업');
insert into Occupation(occCode, occName) VALUE ('54','경호·경비직');
insert into Occupation(occCode, occName) VALUE ('55','돌봄(간병·육아)');
insert into Occupation(occCode, occName) VALUE ('56','청소 및 기타');
insert into Occupation(occCode, occName) VALUE ('61','영업·판매직');
insert into Occupation(occCode, occName) VALUE ('62','운전·운송직');
insert into Occupation(occCode, occName) VALUE ('70','건설·채굴직');
insert into Occupation(occCode, occName) VALUE ('81','기계 설치·정비·생산직');
insert into Occupation(occCode, occName) VALUE ('82','금속·재료 설치·정비·생산직');
insert into Occupation(occCode, occName) VALUE ('83','전기·전자 설치·정비·생산직');
insert into Occupation(occCode, occName) VALUE ('84','정보통신 설치·정비직');
insert into Occupation(occCode, occName) VALUE ('85','화학·환경 설치·정비·생산직');
insert into Occupation(occCode, occName) VALUE ('86','섬유·의복 생산직');
insert into Occupation(occCode, occName) VALUE ('87','식품가공·생산직');
insert into Occupation(occCode, occName) VALUE ('88','인쇄·목재·공예 및 기타');
insert into Occupation(occCode, occName) VALUE ('89','제조 단순직');
insert into Occupation(occCode, occName) VALUE ('90','농림어업직');

insert into SchoolName(schName) value ('sch_name_test1');
insert into SchoolName(schName) value ('sch_name_test2');
insert into SchoolName(schName) value ('sch_name_test3');

insert into MajorName(majorName) value ('major_name_test1');
insert into MajorName(majorName) value ('major_name_test2');
insert into MajorName(majorName) value ('major_name_test3');

insert into CertificateName(certiName, certiInst) VALUE ('certificate_name1','certi_inst1');
insert into CertificateName(certiName, certiInst) VALUE ('certificate_name2','certi_inst2');
insert into CertificateName(certiName, certiInst) VALUE ('certificate_name3','certi_inst3');
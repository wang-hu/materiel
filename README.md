1.导出mysql数据库
mysqldump -uroot -p123456 materiel>C:\materiel.sql
2.导入数据库
	2.1登录数据库 mysql -uroot -ppassword
	2.2创建数据库 CREATE DATABASE materiel;
	2.3导入数据 use materiel; set names utf8;  source c:\Users\\Desktop\materiel.sql

3.tomcat 设置 
	3.1 CATALINA_HOME=D:\java\apache-tomcat-7.0.73-windows-x64\apache-tomcat-7.0.73
	3.2 setclasspath.bat修改
		set JAVA_HOME=D:\java\jdk1.8.0_111
		rem Make sure prerequisite environment variables are set
	3.2 service install
		修改 service.bat
		新增 set "JRE_HOME=D:\java\jdk1.8.0_111\jre"

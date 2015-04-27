构造spring-rest-extjs4-api

启动环境：
gradle jettyRun
测试：spring-mvc

extjs4 sort filter参数封装；
excel 导出封装；
构造数据，未连接数据库；

http://localhost:8080/spring_web_template
http://localhost:8080/spring_web_template/home
http://localhost:8080/spring_web_template/stocks?page=2&limit=2
http://127.0.0.1:8080/spring_web_template/stocks/13



mvn clean         		--> cleans the project
mvn clean test    		--> cleans the project and runs all tests
mvn clean package 		--> cleans the project and builds the WAR
mvn tomcat:run	 		--> web run
mvn eclipse:eclipse		--> gen eclipse .project and .classpath config file
mvn jar:jar				--> only build jar file.
mvn javadoc:javadoc     --> gen api files;

mvn dependency:copy-dependencies  拷贝jar
mvn dependency:tree

mvn test -Dtest={测试文件名称}


    
        

    
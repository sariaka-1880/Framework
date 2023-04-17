rm "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps\TestFramework"
cd "C:\Users\finar\OneDrive\Documents\Framework\Framework\src"
jar cvf framework.jar *
copy "./framework.jar" "C:\Users\finar\OneDrive\Documents\Framework\TestFramework\WEB-INF\lib"
cd "C:\Users\finar\OneDrive\Documents\Framework\TestFramework"
jar cvf "TestFramework.war" *
copy "TestFramework.war" "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps"

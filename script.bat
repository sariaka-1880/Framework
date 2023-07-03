rm "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps\TestFramework"
cd "C:\Users\finar\OneDrive\Documents\framework\Framework\Framework\src"
jar cvf framework.jar *
copy "./framework.jar" "C:\Users\finar\OneDrive\Documents\framework\Framework\TestFramework\WEB-INF\lib"
cd "C:\Users\finar\OneDrive\Documents\framework\Framework\TestFramework\"
jar cvf "TestFramework.war" *
copy "TestFramework.war" "C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps"

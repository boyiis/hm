### 打包方式
1.  通过maven profile + filtering, 隔离不同发布环境配置,例如:
```
pom.xml:
    <profiles>
        <profile>
            <id>dev</id>
            <properties>
                <env>dev</env>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>test</id>
            <properties>
                <env>test</env>
            </properties>
        </profile>
    </profiles>

    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>

        <filters>
            <filter>conf/${env}/GlobalConfig.properties</filter>
            <filter>conf/${env}/log4j2.properties</filter>
        </filters>
    </build>

maven:
   mvn clean install -DskipTests -Ptest
   即用conf/test目录下的properties文件中的属性值来替换'src/main/resources'所有文件的占位符

```

### 本地测试
1.依次进入game-ws-gate game-ws-charge game-ws-debug目录敲入命令 mvn tomcat7:run 启动项目
2.http://localhost:8080/debug列举所有api


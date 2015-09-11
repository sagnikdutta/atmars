  * [Environment Requirement](#Environment_Requirement.md)
  * [Building the source code](#Building_the_source_code.md)
  * [Run Android Client](#Run_Android_Client.md)

# Environment Requirement #

  * Windows 7
  * MyEclipse 10.1
  * MySQL 5.5
  * Apache ActiveMQ 5.6.0
  * Squid 3.1.20

# Building the source code #

  * Create a new directory named **atmars**
```

mkdir atmars
```
  * Checkout atmars source code into **atmars**
```

svn checkout http://atmars.googlecode.com/svn/trunk/ atmars
```
  * Import database into MySQL use tables.sql
  * Open MyEclipse and click File -> Import -> General -> Existing Projects into Workspace, click next and choose the directory /atmars, then click Finish.
  * Change the configuration file in /WebRoot/WEB-INF/applicationContext.xml, change the code accroding to your own database.
```

<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
<property name="driverClassName" value="com.mysql.jdbc.Driver">


Unknown end tag for &lt;/property&gt;


<property name="url" value="jdbc:mysql://localhost:3306/marsdb?charset=utf-8">


Unknown end tag for &lt;/property&gt;


<property name="username" value="root">

Unknown end tag for &lt;/property&gt;

<property name="password" value="1234">

Unknown end tag for &lt;/property&gt;




Unknown end tag for &lt;/bean&gt;


```
  * Unzip the /third-party-tools/apache-activemq-5.6.0-bin.zip, run the script /bin/activemq.bat
  * Run the project in MyEclipse.
  * In Web Exprorer, type in http://localhost:8080/atmars/ and visit the web site.

# Run Android Client #
  * Open Eclipse with ADT 18.0 and Android Emulator 2.3.3
  * In Eclipse, click File -> Import -> General -> Existing Projects into Workspace, click next and choose the directory checked out right now, then click Finish.
  * Run the project in Eclipse.
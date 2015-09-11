

&lt;hr&gt;


<font color='blue' size='4'>
<b>@Mars 2.0</b>
</font>
<br>
<br>
<br>
<br>
<hr><br>
<br>
<br>
<font color='green' size='3'>
<p>This project is an open source project that focuses on the implementation of a Micro Blog called AtMars. Using Struts, Spring and Hibernate Architecture and HTML5 page render technology.</p>
</font>

<ul><li><a href='#Introduction.md'>Introduction</a>
</li><li><a href='#Functions.md'>Functions</a>
</li><li><a href='#System_Topology.md'>System Topology</a>
</li><li><a href='#System_Architecture.md'>System Architecture</a>
</li><li><a href='#Third-party_Technology.md'>Third-party Technology</a>
</li><li><a href='#Installation_Guide.md'>Installation Guide</a>
</li><li><a href='#Screenshot.md'>Screenshot</a>
<br></li></ul>

<h1>Introduction</h1>

<p>This is an open source implementation of micro blog. It can also be named <b>@Mars</b>, which is a symbol of micro blog. Mars, as the fourth planet in the Solar System, is the first planet that human begin to study and research except earth. @Mars, means that the user group of the micro blog is focus on the foresight and pioneer of some new technology and design idea.</p>

<h1>Functions</h1>

<p>AtMars, as a implementation of micro blog, has several functions as following:</p>
<ul><li>Standard <b>Publish</b> of micro blog<br>
</li><li><b>Forward</b> and <b>Comment</b> submission of micro blog<br>
</li><li><b>Followers</b> and <b>Followings</b> function<br>
</li><li><b>Face</b> emotion of micro blog<br>
</li><li><b>Photo</b> submission of micro blog<br>
</li><li><b>Location</b> of HTML5 Navigator<br>
</li><li><b>Mail</b> Validation</li></ul>


<h1>System Topology</h1>

<p>The system contains <b>proxy servers</b>, <b>web servers</b> and <b>back servers</b>. Users can not only use PC client but also use mobile device, such as PDA, cellphones, to visit @Mars Micro Blog.</p>
<p>When a user visit the website of @Mars Micro Blog, the <b>proxy server</b> is in use of load balance to balance the load of two or more <b>web servers</b>. Each of <b>web server</b> will deal with the sessions assigned for it. The data sharing server will be used in individual servers, including picture server, mail server and database server. <b>Picture server</b> is responsible for storing the pictures uploaded by users. <b>Mail server</b> is responsible for sending authentication e-mail to new users. <b>Database server</b> is responsible for storing structural data in relational tables.</p>

<img src='http://atmars.googlecode.com/svn/wiki/images/topology.png' />


<h1>System Architecture</h1>

<p>The architecture of the system can be separated into three parts: <b>UI Layer</b>, <b>SSH Layer</b> and <b>DB Layer</b>.</p>
<ul><li><p><b>UI Layer</b>, means User Interface Layer, is implemented by Java Server Pages (JSP). Using Hypertext Markup Language (HTML) as the page-render technology, we choose HTML5 as the main page development standard. In addition, we use Cascading Style Sheets version 3 (CSS3) to decorate the pages and JavaScript to control the page functions. Asynchronous JavaScript and XML (AJAX) is used to deal with the communication between client and server.</p>
</li><li><p><b>SSH Layer</b>, means the Layer of Struts, Spring and Hibernate. It is the integration of Open Source Architecture. Struts is responsible for the MVC Layer. Spring is responsible for the Service Layer. Hibernate is responsible for the ORM Mapping.</p>
</li><li><p><b>DB Layer</b>, means Database Layer. MySQL is used as the database for this project to store structural data.</p></li></ul>

<img src='http://atmars.googlecode.com/svn/wiki/images/architecture.png' />


<h1>Third-party Technology</h1>

<ul><li>JavaMail API 1.4.5<br>
</li><li>Jasypt 1.9.0<br>
</li><li>ActiveMQ 5.6.0<br>
</li><li>Java Message Service (JMS)<br>
</li><li>Hypertext Markup Language Version 5 (HTML5)<br>
</li><li>Cascading Style Sheets Version 3 (CSS3)<br>
</li><li>JQuery 1.7.2<br>
</li><li>Asynchronous JavaScript and XML (AJAX)<br>
</li><li>JavaScript Object Notiation (JSON)<br>
</li><li>Squid 3.1.20<br>
</li><li>Android 2.3.3<br>
</li><li>Struts Model-View-Controller (Struts MVC) 2<br>
</li><li>Spring Inversion of Control (Spring IoC) 3.0<br>
</li><li>Hibernate Object/Relation Mapping (Hibernate ORM) 3.3<br>
</li><li>MySQL 5.5</li></ul>

<h1>Installation Guide</h1>

<a href='http://code.google.com/p/atmars/wiki/Installation_Guide'>http://code.google.com/p/atmars/wiki/Installation_Guide</a>

<h1>Screenshot</h1>
<ul><li>Index<br>
<img src='http://atmars.googlecode.com/svn/wiki/Screenshot/Screenshot-1.jpg' width='700'>
</li><li>Homepage<br>
<img src='http://atmars.googlecode.com/svn/wiki/Screenshot/Screenshot-2.jpg' width='700'>
</li><li>Search Users<br>
<img src='http://atmars.googlecode.com/svn/wiki/Screenshot/Screenshot-3.jpg' width='700'>
</li><li>Publish a Message<br>
<img src='http://atmars.googlecode.com/svn/wiki/Screenshot/Screenshot-4.jpg' width='700'>
</li><li>Forward a Message<br>
<img src='http://atmars.googlecode.com/svn/wiki/Screenshot/Screenshot-5.jpg' width='700'>
</li><li>Comment a Message<br>
<img src='http://atmars.googlecode.com/svn/wiki/Screenshot/Screenshot-6.jpg' width='700'>
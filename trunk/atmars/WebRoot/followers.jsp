<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="org.atmars.dao.User"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	java.util.List<User> list_followers=(java.util.List<User>) session.getAttribute("followers");
%>
<%
    org.atmars.dao.User user=(org.atmars.dao.User) session.getAttribute("user");
%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Followers</title>
<style type="text/css">
<!--
body {
	font-family: Arial, Helvetica, sans-serif;
	background: #ddf3f7;
	background-repeat: repeat-y;
	margin: 0;
	padding: 0;
	color: #000;
	height: 100%;
}

/* ~~ Element/tag selectors ~~ */
ul,ol,dl {
	/* Due to variations between browsers, it's best practices to zero padding and margin on lists. For consistency, you can either specify the amounts you want here, or on the list items (LI, DT, DD) they contain. Remember that what you do here will cascade to the .nav list unless you write a more specific selector. */
	padding: 0;
	margin: 0;
}

h1,h2,h3,h4,h5,h6,p {
	margin-top: 0;
	/* removing the top margin gets around an issue where margins can escape from their containing div. The remaining bottom margin will hold it away from any elements that follow. */
	padding-right: 15px;
	padding-left: 15px;
	/* adding the padding to the sides of the elements within the divs, instead of the divs themselves, gets rid of any box model math. A nested div with side padding can also be used as an alternate method. */
}

a img {
	/* this selector removes the default blue border displayed in some browsers around an image when it is surrounded by a link */
	border: none;
}

/* ~~ Styling for your site's links must remain in this order - including the group of selectors that create the hover effect. ~~ */
a:link {
	text-decoration: underline;
	/* unless you style your links to look extremely unique, it's best to provide underlines for quick visual identification */
}

a:visited {
	text-decoration: underline;
}

a:hover,a:active,a:focus {
	/* this group of selectors will give a keyboard navigator the same hover experience as the person using a mouse. */
	text-decoration: none;
}

/* ~~ this container surrounds all other divs giving them their percentage-based width ~~ */
.container {
	height: auto;
	background: url(homepage-img/mm_body_bg.png) );
	background-repeat: repeat-y;
	background-position: center 0;
	margin: 0 auto;
	/* the auto value on the sides, coupled with the width, centers the layout. It is not needed if you set the .container's width to 100%. */
}

.container .header {
	background-image: url(homepage-img/global_nav_bg.png);
	background-repeat: repeat-x;
}

.header {
	z-index: 9998;
	height: 35px;
	width: 100%;
	position: fixed;
	left: 0;
	top: 0;
}

.header .nav {
	width: 950px;
	height: 35px;
	margin: auto;
	position: relative;
	display: block;
}

.header .logo {
	width: 100px;
	height: 34px;
	position: absolute;
	margin: 0 0 0 10px;
	left: 0;
}

.header .list {
	float: left;
	display: block;
	margin: 0px 0 0px 160px;
}

.header .right {
	float: right;
	display: block;
	margin: 0 0px 0 0;
}

.header .list li {
	display: inline-block;
	height: 100%;
	width: 100px;
}

.header .right li {
	padding: 0 7px 0 5px;
	display: inline-block;
	height: 100%;
}

li {
	list-style: none;
}

.gbgt {
	height: 28px;
	cursor: pointer;
	display: block;
	text-decoration: none !important;
	font-size: 18px;
	padding-top: 6px;
}

.header a:hover {
	background: #4380a2;
}

/* ~~ This is the layout information. ~~ 

1) Padding is only placed on the top and/or bottom of the div. The elements within this div have padding on their sides. This saves you from any "box model math". Keep in mind, if you add any side padding or border to the div itself, it will be added to the width you define to create the *total* width. You may also choose to remove the padding on the element in the div and place a second div within it with no width and the padding necessary for your design.

*/
.content {
	padding: 50px 0 0 0;
	width: 840px;
	margin: 0 auto;
	height: auto;
}

.content .conl {
	float: left;
	overflow: hidden;
	background-color: rgba(255,255,255,0.5);
	width: 160px;

	display: block;
}

.conl .left_nav {
	padding-top: 20px;
}

.conl .left_nav .left_nav_title {
	padding: 15px 5px 8px 5px;
	font-size: 12px;
}

.conl .left_nav .left_nav_item {
	float: left;
	display: block;
	height: 26px;
	width: 100%;
	line-height: 16px;
	padding: 5px 15px 0px 20px;
	overflow: hidden;
	white-space: nowrap;
	text-decoration: none !important;
	color: #124A96;
	margin-top: 2px;
}

.conl .left_nav .left_nav_item .left_nav_icon {
	float: left;
	width: 20px;
	height: 18px;
	display: inline-block;
	margin-top: 3px;
}

.conl .left_nav .left_nav_item .left_nav_content {
	font-size: 14px;
	margin-top: 3px
}

.left_nav a:hover {
	background-color: #79c5e9;
}

.content .conr {
	float: right;
	overflow: hidden;
	width: 680px;
	background: #fff;
	display: block;
}


.content .conr .search_box {
	float: left;
	overflow: hidden;
	margin-top: 38px;
	padding-left: 60px;
	width: 620px;
}

.search_more {
	float: left;
	margin-top: 2px;
	padding: 0 9px;
	padding-bottom: 20px;
	line-height: 21px;
	width: 530px;
	border-bottom: 4px #79c5e9 solid;
}

.search_more .search_result {
	float: left;
	color: #A4A4A4;
	display: inline;
}

#person_list {
	float: left;
	width: 78%;
	padding: 46px 90px 50px 60px;
}

.item {
	float: left;
	margin-top: 15px;
	padding-bottom: 20px;
	width: 70%;
	border-bottom: 1px dotted #e8e8e8;
}

.item .item_l {
	float: left;
	width: 100%;
}

.item .item_img {
	float: left;
	width: 50px;
	height: 50px;
}

.item dd {
	margin-left: 55px;
}

.item .person_name {
	font-size: 20px;
	line-height: 22px;
	overflow: hidden;
	margin-top: -3px;
	color: #79c5e9;
}

.item .item_content {
	font-size: 12px;
	margin-top: 10px;
	color: ##79c5e9;
}

.item .item_content ul {
	float: left;
	display: inline;
}

.item .item_content li {
	float: left;
	line-height: 18px;
	margin: 0 3px 0 0;
	padding: 0 3px 0 3px;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #4380a2;
	color: #79c5e9;
}

/* ~~ This grouped selector gives the lists in the .content area space ~~ */

/* ~~ miscellaneous float/clear classes ~~ */
.fltrt {
	/* this class can be used to float an element right in your page. The floated element must precede the element it should be next to on the page. */
	float: right;
	margin-left: 8px;
}

.fltlft {
	/* this class can be used to float an element left in your page. The floated element must precede the element it should be next to on the page. */
	float: left;
	margin-right: 8px;
}

.clearfloat {
	/* this class can be placed on a <br /> or empty div as the final element following the last floated div (within the #container) if the overflow:hidden on the .container is removed */
	clear: both;
	height: 0;
	font-size: 1px;
	line-height: 0px;
}
-->
</style>
</head>
<script>
function Sync() {
		var bheight= window.screen.availHeight-160;
		var conrheight= document.all.conr.offsetHeight;
		if(bheight>conrheight){
			document.all.conl.style.height = bheight+ "px";
			document.all.conr.style.height = bheight+ "px";
		}
		else{
			document.all.conl.style.height=conrheight+"px";
		}
	}
</script>
<body onLoad="Sync()">

	<div class="container">
		<header class="header">
			<div class="nav">
				<div class="logo">
					<img src="homepage-img/homelogo.png" height="34" width="100" />
				</div>
				<ul class="list">
					<li><a href="homepage"
						class="gbgt current" style="color:#fff; padding-left:5px">
							Homepage </a></li>
					<li><a href="search"
						class="gbgt" style="color:#fff; padding-left:15px"> Search </a>
					</li>
				</ul>
				<ul class="right">
					<li><a href="homepage"
						class="gbgt"
						style="color:#fff; padding-left:6px; padding-right:6px">
							<%=user.getNickname() %> </a>
					</li>
					<li style=" width:90px;"><a
						href="logout" class="gbgt"
						style="color:#fff; padding-left:14px"> Logout </a>
					</li class="current">
				</ul>
			</div>
		</header>
		<div class="content">
			<div id="conl" class="conl">
				<div class="left_nav">
					<div class="left_nav_title">FOLLOWING/FOLLOWERS</div>
					<a href="myFollowings" class="left_nav_item"> <img
						src="template-img/follow.png" class="left_nav_icon">
						<p class="left_nav_content">Following</p> </a>
					<div class="left_nav_item" style="background-color:#79c5e9">
						<img src="template-img/follower.png" class="left_nav_icon">
						<p class="left_nav_content">Followers</p>
					</div>
					<a href="search" class="left_nav_item"> <img
						src="template-img/search.png" class="left_nav_icon">
						<p class="left_nav_content">Search</p> </a>
				</div>
			</div>
			<div id="conr" class="conr">
			    <div class="search_box">
					<p class="search_more">
						<span class="search_result">Find <%=list_followers.size()%> results</span>
					</p>
				</div>
				<div style="clear:both"></div>
				<div id="person_list">
					
					<%
						int i = 0;
						while (i < list_followers.size()) {
					%>
					<div class="item">
						<div class="item_l">
							<dl>
								<dt>
									<img src=<%=((User) list_followers.get(i)).getImage()%>
										class="item_img" />
								</dt>
								<dd>
									<div class="person_name"><a href="userpage?hisId=<%=((User) list_followers.get(i)).getUserId()%>"><%=((User) list_followers.get(i)).getNickname()%></a></div>
									<div class="item_content">
										<ul>
											<li><strong>Following(<%=((User) list_followers.get(i)).getFollowingCount() %>)</strong>
											</li>
											<li><strong>Followers(<%=((User) list_followers.get(i)).getFollowerCount()%>)</strong>
											</li>
											<li style="border:none"><strong>Posts(<%=((User) list_followers.get(i)).getPostCount() %>)</strong>
											</li>
										</ul>
									</div>
								</dd>
							</dl>
						</div>
					</div>

					<%
						i++;
						}
					%>








				</div>
				<div style="clear:both"></div>
			</div>
		</div>
</body>
</html>

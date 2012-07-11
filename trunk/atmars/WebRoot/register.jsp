<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<!-- TemplateBeginEditable name="doctitle" -->
<title>Mars Register</title>
<!-- TemplateEndEditable -->
<!-- TemplateBeginEditable name="head" -->
<!-- TemplateEndEditable -->
<style type="text/css">
<!--
body {
	font: 100%/1.4 Verdana, Arial, Helvetica, sans-serif;
	background: #A1E0E9;
	padding: 0;
	color: #000;
}

/* ~~ Element/tag selectors ~~ */
ul, ol, dl { /* Due to variations between browsers, it's best practices to zero padding and margin on lists. For consistency, you can either specify the amounts you want here, or on the list items (LI, DT, DD) they contain. Remember that what you do here will cascade to the .nav list unless you write a more specific selector. */
	padding: 0;
	margin: 0;
}
h1, h2, h3, h4, h5, h6, p {
	margin-top: 0;	 /* removing the top margin gets around an issue where margins can escape from their containing div. The remaining bottom margin will hold it away from any elements that follow. */
	padding-right: 15px;
	padding-left: 15px; /* adding the padding to the sides of the elements within the divs, instead of the divs themselves, gets rid of any box model math. A nested div with side padding can also be used as an alternate method. */
}
a img { /* this selector removes the default blue border displayed in some browsers around an image when it is surrounded by a link */
	border: none;
}

/* ~~ Styling for your site's links must remain in this order - including the group of selectors that create the hover effect. ~~ */
a:link {
	color:#414958;
	text-decoration: underline; /* unless you style your links to look extremely unique, it's best to provide underlines for quick visual identification */
}
a:visited {
	color: #4E5869;
	text-decoration: underline;
}
a:hover, a:active, a:focus { /* this group of selectors will give a keyboard navigator the same hover experience as the person using a mouse. */
	text-decoration: none;
}

/* ~~ this container surrounds all other divs giving them their percentage-based width ~~ */
.container {
	width: 60%;
	max-width: 1260px;/* a max-width may be desirable to keep this layout from getting too wide on a large monitor. This keeps line length more readable. IE6 does not respect this declaration. */
	min-width: 780px;/* a min-width may be desirable to keep this layout from getting too narrow. This keeps line length more readable in the side columns. IE6 does not respect this declaration. */
	background:#FFF ;
	margin: 0 auto; /* the auto value on the sides, coupled with the width, centers the layout. It is not needed if you set the .container's width to 100%. */
	background: #A1E0E9;
	position:relative;
	
}

/* ~~the header is not given a width. It will extend the full width of your layout. It contains an image placeholder that should be replaced with your own linked logo~~ */
.header {

}


/* ~~ This is the layout information. ~~ 

1) Padding is only placed on the top and/or bottom of the div. The elements within this div have padding on their sides. This saves you from any "box model math". Keep in mind, if you add any side padding or border to the div itself, it will be added to the width you define to create the *total* width. You may also choose to remove the padding on the element in the div and place a second div within it with no width and the padding necessary for your design.

*/
.content {
	padding: 10px 0;
	border-top-left-radius: 8px;
    border-top-right-radius: 8px;
    box-shadow: 0 1px 3px rgba(0,0,0,.35);
	background:#fff;
}

/* ~~ This grouped selector gives the lists in the .content area space ~~ */
.content ul, .content ol { 
	padding: 0 15px 15px 40px; /* this padding mirrors the right padding in the headings and paragraph rule above. Padding was placed on the bottom for space between other elements on the lists and on the left to create the indention. These may be adjusted as you wish. */
}

/* ~~ The footer ~~ */
.footer {
	padding: 10px 0;
	background: #D2F1F6;
    border-bottom-right-radius: 8px;
    border-bottom-left-radius: 8px;
    box-shadow: 0 1px 3px rgba(0,0,0,.35);
}

/* ~~ miscellaneous float/clear classes ~~ */
.fltrt {  /* this class can be used to float an element right in your page. The floated element must precede the element it should be next to on the page. */
	float: right;
	margin-left: 8px;
}
.fltlft { /* this class can be used to float an element left in your page. The floated element must precede the element it should be next to on the page. */
	float: left;
	margin-right: 8px;
}
.clearfloat { /* this class can be placed on a <br /> or empty div as the final element following the last floated div (within the #container) if the #footer is removed or taken out of the #container */
	clear:both;
	height:0;
	font-size: 1px;
	line-height: 0px;
}

.submitbt{
	width:150px;
	height:50px;
	margin:30px 0px 0px 140px;
	font-family:Arial, Helvetica, sans-serif;
	font-size:28px;
	background:#80c50c;
	background-position:center;
	color:#fff;
	border:0;
	border-radius: 8px;
    box-shadow: 0 1px 5px rgba(0,0,0,.45);
}
.submitbt:active{
	color:#999999;
	margin:31px 0px 0px 141px;
}
.textinp{
	color:#000;
	border:medium solid #ddd;
	border-width:thin;
	margin:5px 0 0 5px;
	height:36px;
	line-height:28px;
	background:#FFF;
	width:256px;
	font-size:16px;
	font-weight:100;
	padding-left:10px;
}

.prompt{
	padding-left:10px;
	color:#333333;
	font-size:13px; 
	border:0px;
	word-wrap:break-word;
	padding-right:10px;
	width:200px;
	display:none;
	margin-top:3px;
	box-shadow:0 1px 3px rgba(0,0,0,0.45);
	background-color:#DCFDCC;
}
.prompt_right{
	border:0px;
	background-color:#FFFFFF;
	box-shadow:none;
}

.prompt_wrong{
	padding-left:10px;
	color:#333333;
	font-size:13px; 
	border:0px;
	word-wrap:break-word;
	padding-right:10px;
	width:200px;
	display:none;
	margin-top:3px;
	box-shadow:0 1px 3px rgba(0,0,0,0.45);
	background-color:#FFECEC;
}

.table{
	margin-left:50px;
	margin-top:30px;
	background:#fff;
}
.container .content form .table tr td label {
	font-family: Arial, Helvetica, sans-serif;
}
.container .register{
	position:absolute;
	top:0px;
	left:-42px;
	width: 920px;
}

.cloud {
	width: 345px;
	height: 171px;
	position: absolute;
	right: 0;
	top: 0;
	background-image:url(register-img/cloud.png);
	background-repeat-x: no-repeat;
	background-repeat-y: no-repeat;
	background-attachment: initial;
	background-position-x: 100%;
	background-position-y: 50%;
	background-origin: initial;
	background-clip: initial;
	background-color: initial;
	_background: none;
    z-index: 2;
}

.fragm {
	float: left;
	height: 26px;
	padding-top: 12px;
	padding-right: 0px;
	padding-bottom: 12px;
	margin-right: 7px;
	padding-left: 4px;
}

.fragm .ra {
	margin-right: 2px;
	vertical-align: -2px;
	border: none;
	color:style="color:#666666";
}
.container .filter{
	position:absolute;
	top:117px;
	height:30px;
	background:-webkit-gradient(linear, left top, left bottom, from(#ebfbfb), to(#fff));
	width: 100%;
	border-top-left-radius: 8px;
    border-top-right-radius: 8px;
}

.upimg{
	height:100px;
	width:100px;
	border:medium solid #ddd;
	border-width:thin;
	margin-left:5px;

}
.successimg{
	width:30px;
	height:30px;
}
-->
</style>

<script type="text/javascript" src="js/register.js"></script>
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
</head>

<body>
<div class="cloud"></div>
<div class="container">
  <div class="filter"></div>
  <div class="header"> <img src="register-img/register.png" width="917" class="register"/>
    <!-- end .header --></div>
    
  <div style="top:0; height:117px; background-color:#A1E0E9"></div>
  <div class="content">
    
    <table width="580" height="90" border="0" background="register-img/email.png" style="margin-top:50px; margin-left:30px">
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>

    <s:form name="form1" method="post" action="performRegister" enctype="multipart/form-data">
      
      <table width="650" height="190" border="0" class="table" >
        <tr>
          <td width="97" height="60" align="left" valign="middle" style="color:#666666">Email</td>
          <td width="321">
            <input name="email" type="text" class="textinp" id="email" size="30" maxlength="20" onBlur="email_text_onblur()" onFocus="email_text_onfocus()" style="color:#333333">
          </td>
          <td width="218"><div id="proemail" class="prompt" ></div> </td>
        </tr>
        <tr>
          <td height="60" align="left" valign="middle" style="color:#666666">Password</td>
          <td><input name="password" type="password" class="textinp" id="password" value="" onBlur="password_text_onblur()" onFocus="password_text_onfocus()" size="30"maxlength="20" style="color:#333333"></td>
          <td><div id="propassword" class="prompt" ></div></td>
        </tr>
        <tr>
          <td height="60" align="left" valign="middle" style="color:#666666">Password Confirm</td>
          <td><input name="passwordconfirm" type="password" class="textinp" id="passwordc" value="" onBlur="passwordc_text_onblur()" onFocus="passwordc_text_onfocus()" size="30" maxlength="20" style="color:#333333"></td>
          <td><div id="propasswordc" class="prompt" ></div></td>
        </tr>
        <tr>
          <td height="60" align="left" valign="middle" style="color:#666666">Nickname</td>
          <td><input name="nickname" type="text" class="textinp" id="nickname" onBlur="nickname_text_onblur()" onFocus="nickname_text_onfocus()" size="30"maxlength="20" style="color:#333333"></td>
          <td><div id="pronickname" class="prompt" ></div></td>
        </tr>
        <tr>
          <td height="60" align="left" valign="middle" style="color:#666666">Gender</td>
          <td>
          <div class="fragm">
               <label><input type="radio" value="1" name="gender" id="rdoboy" class="ra" /> Male</label>
          </div>
          <div class="fragm">
               <label><input type="radio" value="2" name="gender" id="rdogirl" class="ra" />Female</label>
          </div>
          </td>
        </tr>
         <tr>
          <td height="90" align="left" valign="middle" style="color:#666666">Photo</td>
          <td>
          <img id="image" src="register-img/uploadphoto.png" onClick="image_file.click()" class="upimg"/>
          </td>
          <td>
           <s:file id="image_file" name="upload" type="file" style="visibility:collapse" onchange="handleFiles(this.files)" />
          </td>
        </tr>
      </table>
      <p>
        <input type="submit" name="submit" id="submit" value="Submit" class="submitbt" ">
      </p>

    </s:form>
    <table width="780" height="200" border="0" background="register-img/image.png">
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table>
  </div>
  <div class="footer">
    <p>&nbsp;</p>
  <!-- end .footer --></div>
<!-- end .container --></div>
</body>
</html>


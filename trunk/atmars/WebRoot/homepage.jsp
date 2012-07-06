<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="org.atmars.dao.User"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
    org.atmars.dao.User user=(org.atmars.dao.User) session.getAttribute("user");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
<title>Atmars Homepage</title>

<link rel="stylesheet" type="text/css" href="css/homepage.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/jquery.insertContent.js"></script>
<script type="text/javascript" src="js/maxlength.js"></script>

<script>
function Sync(){
	document.all.conr.style.height=document.all.conl.offsetHeight+"px";
	pageRefresh();
}
</script>
<script type="text/javascript">
$(function () {
	$("#publish_text").maxlength({
		'feedback': '#letter_num',
		'useInput': true
	});
	$("#forward_text").maxlength({
		'feedback': '#forward_letter_num',
		'useInput':true
	});
	$("#comment_text").maxlength({
		'feedback': '#comment_letter_num',
		'useInput':true
	});
});
</script>
</head>
<body onLoad="Sync()">

<div class="container">
  <header class="header">
    <div class="nav">
      <div class="logo">
        <img src="homepage-img/homelogo.png" height="34" width="100" />
      </div>
       <ul class="list">
           <li><a href="C:\Users\quanshuo\Desktop\dream\homepage.html" class="gbgt current" style="color:#fff; padding-left:5px" > Homepage </a></li>
           <li>
              <a href="search?queryString=cloudzfy" class="gbgt" style="color:#fff; padding-left:15px" >
              Search
              </a>
           </li>
       </ul>
       <ul class="right">
          <li>
              <a href="C:\Users\quanshuo\Desktop\dream\homepage.html" class="gbgt" style="color:#fff; padding-left:6px; padding-right:6px" > 
              <%=user.getNickname() %>
            </a>
          </li>
          <li  style=" width:90px;">
              <a href="C:\Users\quanshuo\Desktop\dream\homepage.html" class="gbgt" style="color:#fff; padding-left:14px" > 
              Logout
              </a>
          </li class="current">
       </ul>
    </div>
  </header>
  <div class="content">
     <div id="conl" class="conl">
       <div class="input">
     <form>
       <div>
       	 <div style="float:left; margin-left:30px; margin-top:25px;"><img src="homepage-img/whatsup.png"></div>
         <div style="float:right; margin-right:50px; margin-top:30px;">&nbsp;letters left.</div>
         <div id="letter_num" style="float:right; margin-top:20px; font-family:'Arial Black', Gadget, sans-serif; font-size:24px;">140</div>
         <textarea id="publish_text"></textarea>
         <input type="hidden" name="maxlength" value="140" />
       </div>
       <div>
         <div style="float:left; margin-left:20px; margin-top:10px;">
           <a href="javascript:void(0);" id="emotion_button">
             <img id="emotion_img" src="homepage-img/emotion_0.png">
           </a>
         </div>
         <div style="float:left; margin-left:10px; margin-top:10px;">
           <a href="javascript:void(0);" id="image_button" onClick="image_file.click()">
             <img id="image_img" src="homepage-img/image_0.png">
           </a>
         </div>
         <div style="float:left; margin-left:10px; margin-top:10px;">
           <a href="javascript:void(0);" id="location_button">
             <img id="location_img" src="homepage-img/location_0.png">
           </a>
         </div>
         <div style="float:left;">
           <input id="image_file" type="file" accept="image/*" style="visibility:collapse" onchange="handleFiles(this.files)" />
           <script type="text/javascript">
		   var upload_img = "null";
		   var upload_filename = "null";
		    function handleFiles(files){
				var file = files[0];
				upload_filename = files[0].name;
	    		var reader = new FileReader();  
	    		reader.onload = function(e){
					$("#preview_image").attr("src",null);
					upload_img = e.target.result;
					$("#preview_image").attr("src",e.target.result).load(function() {
						var a =$("#preview_image").width();
                        if(this.width<400){
							$("#publish_image").width(this.width+10);
							$("#publish_image").height(this.height+10);
						} else {
							$("#publish_image").width(400);
							$("#publish_image").height(400*this.height/this.width);
							$("#preview_image").width(390);
							$("#preview_image").height($("#publish_image").height()-10);
						}
                    });
    			}
    			reader.readAsDataURL(file);  
    		}
		</script>
         </div>
         <div style="float:right">
           <input type="button" id="submit" value="Publish" onClick="publish()" />
         </div>
       </div>
     </form>
      <script type="text/javascript">
	  function publish(){
		  $.post("fasong.action",{"messageid":"-1","text":$("#publish_text").val(),"position":$("#positionInfo").text(),"upload":upload_img,"uploadFileName":upload_filename},function(result){
			  var str = '<dt class="face"><a href="javascript:void(0);"><img src="';
			  if(result.lastPost.user.image!=null){
					str = str + result.lastPost.user.image;
				}else{
					 str = str + 'homepage-img/upimg.png';
				 }
					   str = str + '" width="50" height="50"></a></dt><dd class="flcontent"><p class="fltext"><a href="javascript:void(0);" class="author_name">' + result.lastPost.user.nickname + '</a>:&nbsp;' + result.lastPost.text + '</p>';
					   if(result.lastPost.image!=null){
						   str = str + '<p><div style="margin-left:10px; margin-top:5px;"><img src="' + result.lastPost.image + '" style="width:300px"></div></p>';
					   }
					   str = str + '<p class="fltime"><span><a href="javascript:void(0);" name="' + result.lastPost.messageId + '" onClick="forward(this.name)">Forward</a><i class="W_vline">&nbsp;|&nbsp;</i><a href="javascript:void(0);" name="' + result.lastPost.messageId + '" onClick="comment(this.name)">Comment</a></span>' + result.lastPost.timeDescription + '&nbsp;&nbsp;&nbsp;';
					   if(result.lastPost.position!=null && result.lastPost.position!=""){
						   str = str + 'From ' + result.lastPost.position;
					   }
					   str = str + '</p></dd>';
					   var i = document.createElement("dl");
					   i.className="feed_list";
					   i.innerHTML=str;
					   var mainlist = document.getElementById("mainlist");
					   mainlist.insertBefore(i,mainlist.firstChild);
					   $("#publish_text").val("");
		  });
	  }
	  </script>
       </div>
       <div style="width:553px; height:3px; background-color:#EAEDEE; float:left; margin-left:15px; margin-top:20px; margin-bottom:20px"></div>
          <div id="mainlist">
          </div>
          <div id="loading_div" class="loading_div">
          	<img src="homepage-img/loading.gif">&nbsp;&nbsp;Loading&nbsp;...
          </div>
   <script type="text/javascript">
   function forward(id){
	   $("#back_div").height($(document.body).height());
	   $("#back_div").width($(document.body).width());
	   $("#back_div").fadeIn(700);
	   $("#forward_div").fadeIn(700);
	   $("#forward_div").css("left",document.body.scrollLeft+(window.screen.availWidth-442)/2);
	   $("#forward_div").css("top",document.body.scrollTop+(window.screen.availHeight-290)/2);
	   $("#forward_message_id").val(id);
	   $("#forward_text").val("Fw: ");
   }
   
   function closing(){
	   $("#back_div").fadeOut(700);
	   $("#forward_div").fadeOut(700);
   }
   $(window).scroll(function(e) {
	   if(document.body.scrollTop+window.screen.availHeight>=document.body.scrollHeight){
		   pageRefresh();	   
	   }
   });
   var oldest_message_id = 9999999;
   function pageRefresh(){
	   $.get("getMyMessages.action?oldest_message_id=" + oldest_message_id,null,function(response){
				   //var response = JSON.parse(result);
				   var myMsg = response.myMessages;
				   var not_original = response.not_original;
				   for(var i = 0; i<myMsg.length; i++){
					   oldest_message_id = myMsg[i].messageId;
					   var str = '<dl class="feed_list"><dt class="face"><a href="javascript:void(0);"><img src="';
					   if(myMsg[i].user.image!=null){
						   str = str + myMsg[i].user.image;
					   }else{
						   str = str + 'homepage-img/upimg.png';
					   }
					   str = str + '" width="50" height="50"></a></dt><dd class="flcontent"><p class="fltext"><a href="javascript:void(0);" class="author_name">' + myMsg[i].user.nickname + '</a>:&nbsp;' + myMsg[i].text + '</p>';
					   if(myMsg[i].image!=null){
						   str = str + '<p><div style="margin-left:10px; margin-top:5px;"><img src="' + myMsg[i].image + '" style="width:300px"></div></p>';
					   }
					   if(myMsg[i].sourceId!=-1){
						   var j=myMsg[i].sourceId;
						   str = str + '<p><div class="previous_div"><p><a href="javascript:void(0);" class="author_name">@' + not_original[j].user.nickname + '</a>:&nbsp;' + not_original[j].text + '</p>';
						   if(not_original[j].image!=null){
							   str = str + '<p><div style="margin-left:20px; margin-top:5px"><img src="' + not_original[j].image + '" width="300"></div></p>';
						   }
						   str = str + '<p class="fltime"><span><a href="javascript:void(0);" name="' + not_original[j].messageId + '"  onClick="forward(this.name)">Forward</a><i class="W_vline">&nbsp;|&nbsp;</i><a href="javascript:void(0);" name="' + not_original[j].messageId + '" onClick="comment(this.name)">Comment</a></span>' + not_original[j].timeDescription + '&nbsp;&nbsp;&nbsp;';
						   if(not_original[j].position!=null){
							   str = str + 'From ' + not_original[j].position;
						   }
						   str = str + '</p></div></p>';
					   }
					   str = str + '<p class="fltime"><span><a href="javascript:void(0);" name="' + myMsg[i].messageId + '" onClick="forward(this.name)">Forward</a><i class="W_vline">&nbsp;|&nbsp;</i><a href="javascript:void(0);" name="' + myMsg[i].messageId + '" onClick="comment(this.name)">Comment</a></span>' + myMsg[i].timeDescription + '&nbsp;&nbsp;&nbsp;';
					   if(myMsg[i].position!=null && myMsg[i].position!=""){
						   str = str + 'From ' + myMsg[i].position;
					   }
					   str = str + '</p></dd></dl>';
					   var mainlist = document.getElementById("mainlist");
					   mainlist.innerHTML = mainlist.innerHTML + str;
					   str = null;
					   document.all.conr.style.height=document.all.conl.offsetHeight+"px";
				   }
		   });
   }
   </script>
   
     </div>
       
     <div id="conr" class="conr">
       <div class="person_info">
          <dl>
             <dt>
                <img src="<%=user.getImage() %>" height="50" width="50"/>
             </dt>
             <dd ><a href="javascript:void(0);" class="name" ><%=user.getNickname() %></a></dd>
          </dl>
       </div>
       <div class="attention">
          <ul>
             <li>
               <a href="chaFensi" style="text-decoration: none !important; ">
               <strong>
               2
               </strong>
               <span>
               FOLLOWING
               </span>
               </a>
             </li>
             <li>
                <a href="wodeguanzhu" style="text-decoration: none !important">
                <strong>
                5
                </strong>
                <span>
                FOLLOWERS
                </span>
                </a>
             </li>
             <li>
                <a href="javascript:void(0);" style="text-decoration: none !important">
                <strong>
                12
                </strong>
                <span>
                POSTS
                </span>
                </a>
             </li>
          </ul>
       </div>
     </div>
    <div style="clear:both">
    </div>
  </div>
</div>
<div id="publish_emotion">
              <table width="200" border="0" align="center">
                <tr>
                  <td width="40" height="40"><a href="javascript:void(0);" title="smile"><img src="Face/smile.gif" width="24" height="24" alt="smile"></a></td>
                  <td width="40"><a href="javascript:void(0);" title="naughty"><img src="Face/naughty.gif" width="24" height="24" alt="naughty"></td>
                  <td width="40"><a href="javascript:void(0);" title="cry"><img src="Face/cry.gif" width="24" height="24" alt="cry"></td>
                  <td width="40"><a href="javascript:void(0);" title="angry"><img src="Face/angry.gif" width="24" height="24" alt="angry"></td>
                  <td width="40"><a href="javascript:void(0);" title="embarrass"><img src="Face/embarrass.gif" width="24" height="24" alt="embarrass"></td>
                </tr>
                <tr>
                  <td height="40"><a href="javascript:void(0);" title="crazy"><img src="Face/crazy.gif" width="24" height="24" alt="crazy"></td>
                  <td><a href="javascript:void(0);" title="effort"><img src="Face/effort.gif" width="24" height="24" alt="effort"></td>
                  <td><a href="javascript:void(0);" title="despise"><img src="Face/despise.gif" width="24" height="24" alt="despise"></td>
                  <td><a href="javascript:void(0);" title="lovely"><img src="Face/lovely.gif" width="24" height="24" alt="lovely"></td>
                  <td><a href="javascript:void(0);" title="laugh"><img src="Face/laugh.gif" width="24" height="24" alt="laugh"></td>
                </tr>
                <tr>
                  <td height="40"><a href="javascript:void(0);" title="titter"><img src="Face/titter.gif" width="24" height="24" alt="titter"></td>
                  <td><a href="javascript:void(0);" title="surprise"><img src="Face/surprise.gif" width="24" height="24" alt="surprise"></td>
                  <td><a href="javascript:void(0);" title="orz"><img src="Face/orz.gif" width="24" height="24" alt="orz"></td>
                  <td><a href="javascript:void(0);" title="unhappy"><img src="Face/unhappy.gif" width="24" height="24" alt="unhappy"></td>
                  <td><a href="javascript:void(0);" title="wronged"><img src="Face/wronged.gif" width="24" height="24" alt="wronged"></td>
                </tr>
              </table>
				<script type="text/javascript">
				var isClick=false;
				$("body").click(function(event){
					if($("#publish_emotion").css("display")=="block"
					&&(event.clientX<$("#publish_emotion").offset().left||event.clientX>$("#publish_emotion").offset().left+$("#emotion_button").width()
					||event.clientY<$("#publish_emotion").offset().top||event.clientY>$("#emotion_button").offset().top+$("#emotion_button").height())){
						if(!isClick){
							$("#emotion_img").attr("src","homepage-img/emotion_0.png");
							$("#publish_emotion").fadeOut(700);
						} else {
							isClick = false;
						}
					}
				});
				$("#emotion_button").click(function(event){
					$("#publish_emotion").css("left",$("#emotion_button").offset().left);
					$("#publish_emotion").css("top",$("#emotion_button").offset().top+$("#emotion_button").height());
					$("#emotion_img").attr("src","homepage-img/emotion_1.png");
					$("#publish_emotion").fadeIn(700);
					isClick=true;
				});
				$("#publish_emotion").find("a").click(function( event){
				    var insertCon=$(this).attr("title");
				    $("#publish_text").insertContent("["+insertCon+"]");
					$("#emotion_img").attr("src","homepage-img/emotion_0.png");
					$("#publish_emotion").fadeOut(700);
				});
				</script>
       </div>
       <div id="publish_image">
       <img id="preview_image" />
       	<script type="text/javascript">
		var isClick = false;
		var isExisted = false;
		$("#image_file").change(function(event){
			$("#publish_image").css("left",$("#image_button").offset().left);
			$("#publish_image").css("top",$("#image_button").offset().top+$("#image_button").height());
			$("#image_img").attr("src","homepage-img/image_1.png");
			$("#publish_image").fadeIn(700);
			isClick = true;
			isExisted = true;
		});
		$("body").click(function(event){
					if($("#publish_image").css("display")=="block"
					&&(event.clientX<$("#publish_image").offset().left||event.clientX>$("#publish_image").offset().left+$("#publish_image").width()
					||event.clientY<$("#publish_image").offset().top||event.clientY>$("#image_button").offset().top+$("#image_button").height())){
						if(!isClick){
							$("#publish_image").fadeOut(700);
						} else {
							isClick = false;
						}
					}
				});
		</script>
       </div>
       <div id="publish_location">
       <div style="float:left; margin-left:15px; margin-top:5px;"><img src="homepage-img/mark.png"></div>
       <div style="float:left; margin-top:15px;"><p style="font-size:24px">Location</p></div>
       <div style="float:left; margin-left:5px; margin-right:5px;"><p style="font-size:16px;" id="positionInfo"></p></div>
       	<script type="text/javascript">
		var isClick = false;
		var isLocation = false;
		$("#location_button").click(function(){
			isClick=true;
			if(isLocation){
				$("#location_img").attr("src","homepage-img/location_0.png");
				isLocation = false;
			} else {
				$("#publish_location").css("left",$("#location_button").offset().left);
				$("#publish_location").css("top",$("#location_button").offset().top+$("#location_button").height());
				$("#location_img").attr("src","homepage-img/location_1.png");
				$("#publish_location").fadeIn(700);
				isLocation = true;
			}
		});
		$("body").click(function(event){
					if($("#publish_location").css("display")=="block"
					&&(event.clientX<$("#publish_location").offset().left||event.clientX>$("#publish_location").offset().left+$("#publish_location").width()
					||event.clientY<$("#publish_location").offset().top||event.clientY>$("#location_button").offset().top+$("#location_button").height())){
						if(!isClick){
							$("#publish_location").fadeOut(700);
						} else {
							isClick = false;
						}
					}
				});
			document.getElementById("location_button").onclick=function(){
				if(navigator.geolocation){
					navigator.geolocation.getCurrentPosition(show_map,handle_error ,null);
				}
			}
			function handle_error(){
			}
			function show_map(position) {
				var coords = position.coords;
				$.get("googlePosition.action?latitude="+coords.latitude+"&longitude="+coords.longitude,null,function(result){
					var response = JSON.parse(result);
					document.getElementById("positionInfo").textContent = response.results[0].address_components[1].long_name + ", " + response.results[0].address_components[2].long_name;
				});
			}
		</script>
       </div>
       <div id="back_div">
       
       </div>
       <div id="forward_div">
       <form>
       <div style="width:100%; height:36px; background-color:#EAFFD7">
       <div style="float:left; font-size:20px; margin-left:8px; margin-top:8px; color:#008000">Forward</div>
       <div style="float:right; width:20px; height:20px; margin-right:8px; margin-top:8px;"><a href="javascript:void(0);" onClick="closing()"><img src="homepage-img/close.png"></a></div>
       </div>
       <div style="float:right; margin-top:15px;">
       <div style="float:right; margin-right:50px; margin-top:30px; color:#999999">&nbsp;letters left.</div>
       <div id="forward_letter_num" style="float:right; margin-top:26px; font-family:'Arial Black', Gadget, sans-serif; font-size:18px; color:#999999">140</div>
       </div>
       <div style="float:left; width:412px;; margin-left:15px; margin-right:15px; margin-top:15px;">
       <textarea class="forward_text" id="forward_text"></textarea>
       <input type="hidden" name="maxlength" value="140" />
       <input type="hidden" id="forward_message_id" />
       <div style="float:right; margin-right:15px"><input value="Forward" type="button" class="forward_button" onClick="forward_send()" /></div>
       </div>
        </form>
        <script type="text/javascript">
			function forward_send(){
				var a=$("#forward_message_id").val();
				var b=$("#forward_text").val();
				$.post("zhuanfa.action",{"messageid":$("#forward_message_id").val(),"text":$("#forward_text").val()},function(result){
					closing();
				});
				$("#forward_text").val("");
			}
		</script>
       </div>
</body>
</html>

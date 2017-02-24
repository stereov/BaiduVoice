<%--
  Created by IntelliJ IDEA.
  User: lihui
  Date: 2017-02-24
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>百度语音合成Demo</title>

    <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.form.min.js"></script>

    <script language="javascript">
        var filesrc;
        $(document).ready(function() {
            $("#getvalue").click(function() {
                $.ajax({
                    url : 'Access_tokenServlet',
                    data : {apiid:$("#client_id").val(), apisecret:$("#client_secret").val()},
                    type : 'post',
                    datatype : 'json',
                    success : function(data) {
                        var obj = eval("(" + data + ")");
                        //alert(data);
                        if(obj.result == 'succ')	{
                            $("#message").val("token: " + obj.content);
                        }
                        else
                        {
                            if(obj.result == 'fail') {
                                $("#message").val("获取令牌错误，错误信息： " + obj.content);
                            }
                        }
                    }
                });
            });

            $("#getvoice").click(function(){
                $.ajax({
                    url : 'Voice_generationServlet',
                    data : {token:$("#token").val(), appid:$("#appid").val(),content_text:$("#content_text").val()},
                    type : 'post',
                    datatype : 'json',
                    success : function(data) {
                        var obj = eval("(" + data + ")");
                        //alert(data);
                        if(obj.result == 'succ')	{
                            $("#message").val("语音文件生成成功");
                            filesrc = obj.content;

                            PlayVoice(filesrc);
                        }
                        else
                        {
                            if(obj.result == 'fail') {
                                $("#message").val("错误代码： " + obj.content);
                            }
                        }
                    }
                });
            });
        });

        function PlayVoice(url) {
            var video = $('audio');
            //video[0].src = "E:\\DEV\\server\\mp3file\\test.mp3";
            video[0].src = url;
            video[0].play();
            //alert(url);
        }
    </script>
  </head>
  <body>
  <b>获取令牌</b>
  <table>
    <tr>
      <td>API ID:</td>
      <td><input type='text' name='client_id' id='client_id' value="hEo70cn1IHiw2tvya7AxMsOO" style='width:300px' /></td>
    </tr>
    <tr>
      <td>API SECRET:</td>
      <td><input type='text' name='client_secret' id='client_secret' value="8995e8c7c672c022ce0bc047f46b3be0" style='width:300px' /></td>
    </tr>
    <tr>
      <td><input type="button" id='getvalue' value='提交' style='width:120px; height:30px; font-family: "Times New Roman", Serif'/></td>
    </tr>
  </table>
  <br>
  <b>语音生成</b>
  <table>
    <tr>
      <td>TOKEN:</td>
      <td><input type='text' name='token' id='token' value="24.34dcf95a0aa2e1a1003d4bc4c6ced288.2592000.1489672013.282335-9278368" style='width:650px' /></td>
    </tr>
    <tr>
      <td>APP ID:</td>
      <td><input type='text' name='appid' id='appid' value="9278368" style='width:650px' /></td>
    </tr>
    <tr>
      <td>文字内容:</td>
      <td><input type='text' name='content_text' id='content_text' value="" style='width:650px' /></td>
    </tr>
    <tr>
      <td><input type="button" id='getvoice' value='提交' style='width:120px; height:30px; font-family: "Times New Roman", Serif'/></td>
    </tr>
  </table>
  <form>
    <audio id="audio"  controls="controls" type="audio/mpeg" style="height: 30px; width: 30%" >
      <source src="" type="audio/mp3">
    </audio>
  </form>
  <b>消息输出</b>
  <input type='text' id="message" style="width: 700px;height: 50px;border: 1px solid red ; text-align: left; padding: 5px; overflow-y:auto; font-size:15px;" />
  <br>
  <br>
  <b>注意：该应用目前只支持firefox chrome,不支持IE。如果要保存文件，请在嵌入式播放器上点击右键，firefox需要填写完整文件名（包括扩展名 .mp3）</b>
  </body>
</html>

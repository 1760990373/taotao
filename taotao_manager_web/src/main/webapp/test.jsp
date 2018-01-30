<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>淘淘商城后台管理系统</title>
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
    <link rel="stylesheet" type="text/css" href="css/taotao.css" />
    <script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <style type="text/css">
        .content {
            padding: 10px 10px 10px 10px;
        }
    </style>
    <script>
            function start(){
                var value = $('#p').progressbar('getValue');
                if (value < 100){
                    value += Math.floor(Math.random() * 2);
                    $('#p').progressbar('setValue', value);
                    if(value<=30){
                        $(".progressbar-value .progressbar-text").css("background-color","#53CA22");
                    }else if (value<=70){
                        $(".progressbar-value .progressbar-text").css("background-color","#EABA0A");
                    }else if (value>70){
                        $(".progressbar-value .progressbar-text").css("background-color","#DF4134");
                    }
                    setTimeout(arguments.callee, 200);
                }
            };


    </script>
</head>
<body>
<div style="margin:20px 0;">
    <a href="#" class="easyui-linkbutton" onclick="start()">Start</a>
</div>
<div id="p" class="easyui-progressbar" style="width:400px;height:15px"></div>



</body>
</html>
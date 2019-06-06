$(function () {
    // 查询用户信息，用户名填充到欢迎Xxx
    $.get("user/findOne", {}, function (data) {
        //{uid:1,name:'李四'}
        if (data) {
            var msg = "欢迎回来，" + data.name;
            $("#span_username").html(msg);
        } else {
            $("#span_username").html("登录查看详细信息");
        }
    });


    //从数据库（tab_category）里面查询分类信息，填充到页面
    $.get("category/findAll", {}, function (data) {
        console.log(data);
        //[{"cid":8,"cname":"全球自由行"},{"cid":5,"cname":"国内游"},{"cid":4,"cname":"处境游"},{"cid":7,"cname":"抱团定制"},{"cid":6,"cname":"港澳游"},{"cid":2,"cname":"酒店"},{"cid":1,"cname":"门票"},{"cid":3,"cname":"香港车票"}]
        //收尾的两个 li 是固定的
        var first = '<li class="nav-active"><a href="index.html">首页</a></li>';
        var end = '<li><a href="favoriterank.html">收藏排行榜</a></li>';

        var lis = first;
        //遍历 数组
        for (var i = 0; i < data.length; i++) {
            var li = '<li><a href="route_list.html?cid=' + data[i].cid + '">' + data[i].cname + '</a></li>';
            lis += li;
        }
        lis += end;
        //将lis字符串，设置到ul的html内容中
        $("#category").html(lis);
    })


    //给搜索框绑定单击事件，获取搜索输入框的内容
    $("#search-button").click(function () {
        //线路名称
        var rname = $("#search_input").val();
        //alert(rname);
        var cid = getParameter("cid");
        //alert(cid);
        //跳转路径 http://localhost:8080/travel_ssm/route_list.html?cid=5，拼接上 rname=xxx
        location.href = "http://localhost:8080/travel_ssm/route_list.html?cid=" + cid + "&rname=" + rname;
    })

});
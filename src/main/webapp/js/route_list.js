// 请求 http://localhost:8080/travel_ssm/route/pageQuery?cid=5
// http://www.bejson.com/
/*
{
    "totalCount": 513,
    "totalPage": 513,
    "currentPage": 5,
    "pageSize": 1,
    "list": [{
    "rid": 5,
    "rname": "梅州 双飞3天2晚 自由行套票【含广州直飞梅州早对晚含税往返机票+2晚梅州市区酒店】",
    "price": 999.0,
    "routeIntroduce": "含广州直飞梅州早对晚含税往返机票+2晚梅州市区酒店！",
    "rflag": "1",
    "rdate": "2018-02-09 01:13:17",
    "isThemeTour": "0",
    "count": 0,
    "cid": 5,
    "rimage": "img/product/small/m3ac1aa10b493b4b22221e19ba39f7e6a1.jpg",
    "sid": 1,
    "sourceId": "21917",
    "category": null,
    "seller": null,
    "routeImgList": null
}]
}

*/

$(function () {
    //获取 cid 的参数值
    var cid = getParameter("cid");
    //获取rname的参数值
    var rname = getParameter("rname");

    //alert(rname);
    if (rname) {
        //url 解码 %E5%91%B5%E5%91%B5
        rname = window.decodeURIComponent(rname);
    }

    //当页面加载完成后，调用 load 方法，发送ajax请求加载数据
    load(cid, null, rname);
});


//加载函数 用户异步发送AJAx 请求，并且把数据填充到当前页面
function load(cid, currentPage, rname) {

    //发送 ajax 请求，请求  route/pageQuery , 传递 cid
    $.get("route/pageQuery?pageSize=10", {cid: cid, currentPage: currentPage, rname: rname}, function (pb) {
        //解析 pagebean 数据，展示到页面上
        // 1. 分页工具条数据展示
        // 1.1 展示总页码和种记录数
        $("#totalPage").html(pb.totalPage);
        $("#totalCount").html(pb.totalCount);
        //alert("总页数："+pb.totalPage+" 总页码："+pb.totalCount);

        /**
         <div class="pageNum">
         <ul id="pageNum">
         <li><a href="">首页</a></li>
         <li class="threeword"><a href="#">上一页</a></li>
         <li><a href="#">1</a></li>
         <li><a href="#">2</a></li>
         <li><a href="#">10</a></li>
         <li class="threeword"><a href="javascript:;">下一页</a></li>
         <li class="threeword"><a href="javascript:;">末页</a></li>
         </ul>
         </div>
         */
            // 1.2 展示分页页码
            // 首页 可以不传 第二个参数，默认为1
        var firstPage = '<li onclick="javascript:load('+cid+','+1+',\''+rname+'\')"><a href="javascript:void(0)">首页</a></li>';

        // 上一页
        //计算上一页的页码
        var beforeNum = pb.currentPage - 1;
        if (beforeNum <= 0) {
            beforeNum = 1;
        }
        var beforePage = '<li onclick="javascript:load('+cid+','+beforeNum+',\''+rname+'\')" class="threeword" onclick=""><a href="javascript:void(0)">上一页</a></li>';

        // 下一页
        //计算下一页的页码
        var nextNum = pb.currentPage + 1;
        if (nextNum > pb.totalPage) {
            nextNum = pb.totalPage;
        }
        var nextPage = '<li onclick="javascript:load('+cid+','+nextNum+',\''+rname+'\')" class="threeword"><a href="javascript:void(0)">下一页</a></li>';

        // 尾页
        var lastPage = '<li onclick="javascript:load('+cid+','+pb.totalPage+',\''+rname+'\') "class="threeword"><a href="javascript:void(0)">末页</a></li>';

        var lis = firstPage + beforePage;

        /*
            1.一共展示10个页码，能够达到前5后4的效果
            2.如果前边不够5个，后边补齐10个
            3.如果后边不足4个，前边补齐10个
            理解百度的分页实现
         */

        //定义开始位置begin，结束位置end
        var begin;//开始位置
        var end;//结束位置

        //1. 要显示10个页码
        if (pb.totalPage < 10) {//有几个显示几个
            //种页码不够10页
            begin = 1;
            end = pb.totalPage;
        } else {//足够多
            //总页码超过10页
            begin = pb.currentPage - 5;
            end = pb.currentPage + 4;

            // 2.如果前边不够5个，后边补齐10个
            if (begin <= 0) {
                begin = 1;
                end = begin + 9;
            }
            // 3.如果后边不足4个，前边补齐10个
            if (end > pb.totalPage) {
                end = pb.totalPage;
                begin = pb.totalPage - 9;
            }

        }


        for (var i = begin; i <= end; i++) {
            var li;
            //当前页码需要加上css 修饰
            // 判断当前页码是否等于 i
            if (pb.currentPage == i) {//如果是当前页码创建出来的 li 带有是样式的
                // class="curPage"
                li = '<li class="curPage"  ="javascript:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">' + i + '</a></li>';
            } else {
                //创建页码的li
                li = '<li onclick="javascript:load('+cid+','+i+',\''+rname+'\')"><a href="javascript:void(0)">' + i + '</a></li>';
            }
            //拼接字符串
            lis += li;
        }


        //这种方式达不到前5后4的效果
        // for (var i = 1; i <= pb.totalPage; i++) {
        //     var li;
        //     //当前页码需要加上css 修饰
        //     // 判断当前页码是否等于 i
        //     if (pb.currentPage == i) {//如果是当前页码创建出来的 li 带有是样式的
        //         // class="curPage"
        //         li = '<li class="curPage" onclick="javascript:load(' + cid + ',' + i + ')"><a href="javascript:void(0)">' + i + '</a></li>';
        //     } else {
        //         //创建页码的li
        //         li = '<li onclick="javascript:load(' + cid + ',' + i + ')"><a href="javascript:void(0)">' + i + '</a></li>';
        //     }
        //     //拼接字符串
        //     lis += li;
        // }


        lis += nextPage;
        lis += lastPage;
        //将类容设置到 ul
        $("#pageNum").html(lis);


        //解析 pagebaen 数据，展示到页面上
        /**
         {
        "rid": 5,
        "rname": "梅州 双飞3天2晚 自由行套票【含广州直飞梅州早对晚含税往返机票+2晚梅州市区酒店】",
        "price": 999.0,
         ......
        "seller": null,
        "routeImgList": null
        }
         */
            //图片原来路径  images/04-search_03.jpg
            // 现在图片有点宽
            // 2. 列表数据展示  route
        var route_lis = "";
        //获取{rid: 5, rname:"Xxx"}
        for (var i = 0; i < pb.list.length; i++) {
            var route = pb.list[i];//的到没一个元素
            var li =
                '<li>\n' +
                '    <div class="img"><img src="' + route.rimage + '" style="width: 299px;" alt=""></div>\n' +
                '    <div class="text1">\n' +
                '        <p>' + route.rname + '</p>\n' +
                '        <br/>\n' +
                '        <p>' + route.routeIntroduce + '</p>\n' +
                '    </div>\n' +
                '    <div class="price">\n' +
                '        <p class="price_num">\n' +
                '            <span>&yen;</span>\n' +
                '            <span>' + route.price + '</span>\n' +
                '            <span>起</span>\n' +
                '        </p>\n' +
                '        <p><a href="route_detail.html?rid='+route.rid+'">查看详情</a></p>\n' +
                '    </div>\n' +
                '</li>';

            route_lis += li;

        }
        $("#route").html(route_lis);
        //定位到页面的顶部
        window.scrollTo(0, 0);//每次点击都回到最上面

    });


}
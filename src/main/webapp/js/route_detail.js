/**
 {
	"rid": 1,
	"rname": "【旅展 半价特惠 重走丝路•漫游宁夏 双飞4天】银川西部影视城 穆民新村 中卫沙坡头【品美酒 回族学唱花儿 感悟民俗】",
	"price": 999.0,
	"routeIntroduce": "走进【宁夏沙坡头】，感受西北大漠风情、体会“大漠孤烟直，长河落日圆”的塞上风光！",
	"rflag": "1",
	"rdate": "2018-02-09 01:13:16",
	"isThemeTour": "0",
	"count": 0,
	"cid": 5,
	"rimage": "img/product/small/m304b69a4c8328f7d6b8d5dadef020fe07.jpg",
	"sid": 1,
	"sourceId": "23677",
	"category": null,
	"seller": {
		"sid": 1,
		"sname": "Xxx",
		"consphone": "12345678901",
		"address": "重庆邮电大学移通学院"
	},
	"routeImgList": [{
		"rgid": 1,
		"rid": 1,
		"bigPic": "img/product/size4/m421d66d4446940b1c80a0092c91e75184.jpg",
		"smallPic": "img/product/size2/m221d66d4446940b1c80a0092c91e75184.jpg"
	}, {
		"rgid": 4,
		"rid": 1,
		"bigPic": "img/product/size4/m47fe3031a3df75ab5e6711ae2338c18f7.jpg",
		"smallPic": "img/product/size2/m27fe3031a3df75ab5e6711ae2338c18f7.jpg"
	}]
}

 */

//页面加载完成
$(document).ready(function () {
    goImg();
});

$(function () {
    // 1. 获取rid
    var rid = getParameter("rid");
    // 2. 发送请求 route/findOne?rid=1
    $.get("route/findOne", {rid: rid}, function (route) {
        //alert(route.rname);
        // 3. 解析数据，填充 html

        $("#rname").html(route.rname);
        $("#routeIntroduce").html(route.routeIntroduce);
        $("#price").html("¥" + route.price);

        //卖家信息
        $("#sname").html(route.seller.sname);
        $("#consphone").html(route.seller.consphone);
        $("#address").html(route.seller.address);
        $("#favoriteCount").html("已收藏"+route.count+"次");


        //替换图片
        //两个上下移动的按钮
        var startStr = "<a class=\"up_img up_img_disable\"></a>";
        var endStr = "<a class=\"down_img down_img_disable\" style=\"margin-bottom: 0;\"></a>";
        var ddStr = startStr;

        //大图默认为第0个图片 打扰可以用随机数
        $("#big_img").attr('src', route.routeImgList[0].bigPic);


        for (var i = 0; i < route.routeImgList.length; i++) {
            var aStr;
            if (i >= 4) {//隐藏掉
                aStr =
                    '<a title="" class="little_img"\n' +
                    '   data-bigpic="' + route.routeImgList[i].bigPic + '"\n' +
                    '   style="display:none;">\n' +
                    '    <img src="' + route.routeImgList[i].smallPic + '">\n' +
                    '</a>';
            } else {
                aStr =
                    '<a title="" class="little_img"\n' +
                    '   data-bigpic="' + route.routeImgList[i].bigPic + '"\n' +
                    '   ">\n' +
                    '    <img src="' + route.routeImgList[i].smallPic + '">\n' +
                    '</a>';
            }
            ddStr += aStr;
        }

        ddStr += endStr;
        $("#dd").html(ddStr);

        //图片展示和切换代码的调用
        goImg();

    });


});


$(function () {
    //发送请求，判断用户是否收藏过该路线
    var rid = getParameter("rid");
    $.get("route/isFavorite", {rid: rid}, function (flag) {
        if (flag) {
            console.log(flag)
            //用户已经收藏过
            //设置收藏样式
            //<a id="favorite" class="btn"><i class="glyphicon glyphicon-heart-empty"></i>点击收藏</a>
            //<a class="btn already" disabled="disabled"><i class="glyphicon glyphicon-heart-empty"></i>点击收藏</a>
            $("#favorite").addClass("already");
            $("#favorite").attr("disabled","disabled");
            // $("#favorite").prop("disabled",disabled);

            //删除按钮的点击事件
            $("#favorite").removeAttr("onclick");

        } else {
            //过户没有收藏

        }
    });
});


function goImg() {
    //焦点图效果
    //点击图片切换图片
    $('.little_img').on('mousemove', function () {
        $('.little_img').removeClass('cur_img');
        var big_pic = $(this).data('bigpic');
        $('.big_img').attr('src', big_pic);
        $(this).addClass('cur_img');
    });
    //上下切换
    var picindex = 0;
    var nextindex = 4;
    $('.down_img').on('click', function () {
        var num = $('.little_img').length;
        if ((nextindex + 1) <= num) {
            $('.little_img:eq(' + picindex + ')').hide();
            $('.little_img:eq(' + nextindex + ')').show();
            picindex = picindex + 1;
            nextindex = nextindex + 1;
        }
    });
    $('.up_img').on('click', function () {
        var num = $('.little_img').length;
        if (picindex > 0) {
            $('.little_img:eq(' + (nextindex - 1) + ')').hide();
            $('.little_img:eq(' + (picindex - 1) + ')').show();
            picindex = picindex - 1;
            nextindex = nextindex - 1;
        }
    });
    //自动播放
    // var timer = setInterval("auto_play()", 5000);
}

//自动轮播方法
function auto_play() {
    var cur_index = $('.prosum_left dd').find('a.cur_img').index();
    cur_index = cur_index - 1;
    var num = $('.little_img').length;
    var max_index = 3;
    if ((num - 1) < 3) {
        max_index = num - 1;
    }
    if (cur_index < max_index) {
        var next_index = cur_index + 1;
        var big_pic = $('.little_img:eq(' + next_index + ')').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(' + next_index + ')').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    } else {
        var big_pic = $('.little_img:eq(0)').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(0)').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    }
}


//点击收藏按钮触发的方法
function addFavorite() {
    alert("我被点击了");
    var rid = getParameter("rid");
    //判断用户是否登录
    $.get("user/findOne",{},function (user) {
        if(user){
            //用户登录了
            $.get("route/addFavorite",{rid:rid},function () {
                // alert("收藏成功");
                // console.log("收藏成功");
                //代码刷新页面
                location.reload();
            });
        }else {
            alert("请登录在收藏瓜皮");
            //location.href="http://localhost:8080/travel_ssm/login.html";
        }


    });
}
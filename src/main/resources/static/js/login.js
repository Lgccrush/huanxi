/////**login.js封装登录和注册处理**/
////登录处理
function userLogin() {
    // alert("测试登录绑定事件");//测试绑定事件
    // 获取参数 通过id选择器获取并去掉空格
    var name = $("#count").val().trim(); // 获取账号
    var pwd = $("#password").val().trim(); // 获取密码
    // alert(name+","+pwd);//测试账号密码
    // 格式检查
    // 先清空之前的提示消息
    $("#count_span").html("");
    $("#password_span").html("");
    var ok = true;
    if (name == "") {
        $("#count_span").html("用户名不能为空");
        ok = false;
    }
    if (pwd == "") {
        $("#password_span").html("密码不能为空");
        ok = false;
    }
    // 调用ajax发送请求
    if (ok) { // 检测格式通过
        $.ajax({ // 发送ajax请求
            url: "/login/cnUser", // 地址
            type: "post",// 请求方法
            data: {
                "name": name,
                "password": pwd
            }, // 发送的参数
            dataType: "json",// 返回的数据类型
            success: function (result) {// result是服务器返回的json结果
                if (result.type == 0) {
                    // 登录成功的请况 将用户信息保存到cookie中
                    var user = result.data;//返回的user对象
                    // alert(result.data);
                    addCookie("token", user.token, 2); // 保存用户id到cookie中
                    // addCookie("username", user.cn_user_name, 2); // 保存用户名到cookie中
                    window.location.href = "/"; // 转发到主页
                    // window.open("/");
                } else if (result.type == 1) {// 用户名错误的情况
                    $("#count_span").html(result.message);// 给出用户名错误提示
                } else if (result.type == 2) {// 密码错误的情况
                    $("#password_span").html(result.message);// 给出密码错误提示
                }
            },
            error: function () {
                debugger;
                alert("登录失败");

            }
        });
    }
}

//注册处理
function regist() {
//		alert("aa");
//		获取用户名密码昵称
    var name = $("#regist_username").val().trim();
    var password = $("#regist_password").val().trim();
    var final_password = $("#final_password").val().trim();
//		alert(name+""+nick+""+password+""+final_password);
//		检查数据格式
//		检查用户名
    var ok = true; //表示参数状态
    $("#warning_1 span").html("");
    if (name == "") {
        ok = false;
        $("#warning_1 span").html("用户名不能为空");
        $("#warning_1").show();
    }
//		检查密码:1 非空 2不能小于6位
    if (password == "") {
        ok = false;
        $("#warning_2").show();
        $("#warning_2 span").html("密码不能为空");
    } else if (password.length > 0 && password.length < 6) {
        ok = false;
        $("#warning_2").show();
        $("#warning_2 span").html("密码不能小于6位");
    }
//		检查确认密码:1非空 2是否与密码一致
    if (final_password != password) {
        ok = false;
//			alert("aa");
        $("#warning_3").show();
    }
    if (ok) {//校验通过 发送ajax请求
        $.ajax({
            url: "/login/register",
            type: "post",
            data: {"name": name, "password": password},
            dataType: "json",
            success: function (result) {
                if (result.type == 0) {//注册成功
                    alert(result.message);
                    $("#back").click();//返回到登录页面
                } else if (result.type == 1) {
                    $("#warning_1 span").html(result.msg);
                    $("#warning_1").show();
                }
            },
            error: function () {
                alert("注册异常");
            }
        })
    }
}

//function userLogin() {
//	// 获取请求参数
//	var name = $("#count").val().trim();
//	var password = $("#password").val().trim();
//	// 检测参数格式
//	// 先清空以前提示信息
//	$("#count_span").html("");
//	$("#password_span").html("");
//	var ok = true;
//	if (name == "") {
//		ok = false;
//		$("#count_span").html("用户名为空");
//	}
//	if (password == "") {
//		ok = false;
//		$("#password_span").html("密码为空");
//	}
//	// 发送Ajax请求
//	if (ok) {
//		$.ajax({
//			url : path + "/user/login.do",
//			type : "post",
//			data : {
//				"name" : name,
//				"password" : password
//			},
//			dataType : "json",
//			success : function(result) {
//				/*
//				 * result是服务器返回的JSON结果{"status":xx,"msg":xx,"data":xx}
//				 */
//				alert(result);
//				if (result.status == 0) { // 成功
//					/*
//					 * 将用户信息写入Cookie
//					 */
//					var user = result.data; // 获取返回的user信息
//					// 写入Cookie
//					addCookie("uid", user.cn_user_id, 2);
//					addCookie("uname", user.cn_user_name, 2);
//					window.location.href = "edit.html";
//				} else if (result.status == 1) { // 用户名错
//					$("#count_span").html(result.msg);
//				} else if (result.status == 2) { // 密码错
//					$("#password_span").html(result.msg);
//				}
//			},
//			error : function() {
//				alert("登录异常");
//			}
//		});
//	}
//}

// 注册处理
//function registUser() {
//	// 获取请求参数
//	var name = $("#regist_username").val().trim();
//	var nick = $("#nickname").val().trim();
//	var password = $("#regist_password").val().trim();
//	var f_password = $("#final_password").val().trim();
//	// 格式检查
//	// 清空消息
//	$("#warning_1 span").html("");
//	$("#warning_2 span").html("");
//	$("#warning_3 span").html("");
//	var ok = true; // 是否通过验证
//	if (name == "") {
//		ok = false;
//		$("#warning_1").show();
//		$("#warning_1 span").html("用户名为空");
//	}
//	if (password == "") {
//		ok = false;
//		$("#warning_2").show();
//		$("#warning_2 span").html("密码为空");
//	} else if (password.length < 6) {
//		ok = false;
//		$("#warning_2").show();
//		$("#warning_2 span").html("密码长度太短");
//	} else if (password.length > 18) {
//		ok = false;
//		$("#warning_2").show();
//		$("#warning_2 span").html("密码长度太长");
//	}
//	if (f_password == "") {
//		ok = false;
//		$("#warning_3").show();
//		$("#warning_3 span").html("确认密码为空");
//	} else if (f_password != password) {
//		ok = false;
//		$("#warning_3").show();
//		$("#warning_3 span").html("确认密码与密码不一致");
//	}
//	// 发送Ajax请求
//	if (ok) {
//		$.ajax({
//			url : base_path + "/user/add.do",
//			type : "post",
//			data : {
//				"name" : name,
//				"password" : password,
//				"nick" : nick
//			},
//			dataType : "json",
//			success : function(result) {
//				if (result.status == 0) { // 成功
//					alert(result.msg); // 登录成功提示框
//					$("#back").click(); // 转向登录界面
//				} else if (result.status == 1) { // 用户名被占用
//					$("#warning_1").show();
//					$("#warning_1 span").html(result.msg);
//				}
//			},
//			error : function() {
//				alert("注册异常");
//			}
//		});
//	}
//}
//
//// $(function(){ //页面载入完毕
//// //给登录按钮绑定单击处理，加()是函数调用，不加()是函数绑定
//// $("#login").click(checkLogin);
//// //给注册按钮绑定单击处理
//// $("#regist_button").click(registUser);
//// });

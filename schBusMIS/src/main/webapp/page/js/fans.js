// 获取url的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}

function getUrl() {
    var openid = GetQueryString("Open");
        window.location.href = "memberlist.html?Open=" + openid;
}
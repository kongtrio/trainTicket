function initPages(divId, currPage, pageCount, url) {
    currPage = currPage + 1;
    pageCount = pageCount;
    var pageSize = 10;
    if (pageSize > pageCount) {
        pageSize = pageCount;
    }
    if (pageSize > 1) {
        $("#" + divId).myPagination({
            currPage: currPage,
            pageCount: pageCount,
            pageSize: pageSize,
            cssStyle: 'sabrosus',
            url: url
        });
    }
}

function initPagesWithClick(divId, currPage, pageCount, clickEvent) {
    currPage = currPage + 1;
    pageCount = pageCount;
    var pageSize = 10;
    if (pageSize > pageCount) {
        pageSize = pageCount;
    }
    if (pageSize > 1) {
        $("#" + divId).myPagination({
            currPage: currPage,
            pageCount: pageCount,
            pageSize: pageSize,
            cssStyle: 'sabrosus',
            clickEvent: clickEvent
        });
    }
}

function pageClickEvent(page) {
    $("#pageNo").val(page);
    $("#query_form").submit();
}

function isBlank(value) {
    if (value == undefined) {
        return true;
    }
    if (value.trim() == "") {
        return true;
    }
    return false;
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                       //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

function contains(array, str) {
    if (str == undefined || array == undefined) {
        return false;
    }
    for (var i = 0; i < array.length; i++) {
        if (str.trim() == array[i]) {
            return true;
        }
    }
    return false;
};

//接受 xx:xx:xx 的时间格式
function parseStr2Time(timeStr) {
    if (timeStr == undefined) {
        return 0;
    }
    var split = timeStr.split(":");
    if (split.length != 3) {
        return 0;
    }
    return split[0] * 60 * 60 + split[1] * 60 + split[2] * 1;
}
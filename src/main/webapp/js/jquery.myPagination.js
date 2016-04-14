(function ($) {
    $.fn.myPagination = function (param) {
        if (param && param instanceof Object) {
            var options;
            var currPage;
            var pageCount;
            var pageSize;
            var tempPage;
            var url;
            var clickEvent = function (page) {
            };
            var obj = $(this);
            var defaults = new Object({
                currPage: 1,
                pageCount: 10,
                pageSize: 5,
                cssStyle: 'badoo',
                ajax: {on: false, hiddenId: 'pageCount', param: {on: false, page: 1}},
                info: {
                    first: '首页',
                    last: '尾页',
                    next: '下一页',
                    prev: '上一页',
                    first_on: true,
                    last_on: true,
                    next_on: true,
                    prev_on: true,
                    msg_on: true,
                    link: '#',
                    msg: '<span>&nbsp;&nbsp;{curr}/{sum}页</span>',
                    text: {width: '30px'}
                }
            });

            function getCurrPage() {
                if (options.currPage) {
                    return options.currPage
                } else {
                    return defaults.currPage
                }
            }

            function getPageCount() {
                if (options.pageCount) {
                    return options.pageCount
                } else {
                    return defaults.pageCount
                }
            }

            function getPageSize() {
                if (options.pageSize) {
                    return options.pageSize
                } else {
                    return defaults.pageSize
                }
            }

            function getCssStyle() {
                if (options.cssStyle) {
                    return options.cssStyle
                } else {
                    return defaults.cssStyle
                }
            }

            function getUrl() {
                if (options.url) {
                    return options.url
                } else {
                    return ''
                }
            }

            function getAjax() {
                if (options.ajax && options.ajax.on) {
                    return options.ajax
                } else {
                    return defaults.ajax
                }
            }

            function getParam() {
                if (options.ajax.param && options.ajax.param.on) {
                    options.ajax.param.page = currPage;
                    return options.ajax.param
                } else {
                    defaults.ajax.param.page = currPage;
                    return defaults.ajax.param
                }
            }

            function getFirst() {
                if (options.info && options.info.first_on == false) {
                    return ""
                }
                if (options.info && options.info.first_on && options.info.first) {
                    var str = "<a hef='" + getLink() + "' title='1'>" + options.info.first + "</a>";
                    return str
                } else {
                    var str = "<a hef='" + getLink() + "' title='1'>" + defaults.info.first + "</a>";
                    return str
                }
            }

            function getLast(pageCount) {
                if (options.info && options.info.last_on == false) {
                    return ""
                }
                if (options.info && options.info.last_on && options.info.last) {
                    var str = "<a hef='" + getLink() + "' title='" + pageCount + "'>" + options.info.last + "</a>";
                    return str
                } else {
                    var str = "<a hef='" + getLink() + "' title='" + pageCount + "'>" + defaults.info.last + "</a>";
                    return str
                }
            }

            function getPrev() {
                if (options.info && options.info.prev_on == false) {
                    return ""
                }
                if (options.info && options.info.prev) {
                    return options.info.prev
                } else {
                    return defaults.info.prev
                }
            }

            function getNext() {
                if (options.info && options.info.next_on == false) {
                    return ""
                }
                if (options.info && options.info.next) {
                    return options.info.next
                } else {
                    return defaults.info.next
                }
            }

            function getLink() {
                if (options.info && options.info.link) {
                    return options.info.link
                } else {
                    return defaults.info.link
                }
            }

            function getMsg() {
                var input = "<input type='text' value='" + currPage + "' >";
                if (options.info && options.info.msg_on == false) {
                    return false
                }
                if (options.info && options.info.msg) {
                    var str = options.info.msg;
                    str = str.replace("{curr}", input);
                    str = str.replace("{sum}", pageCount);
                    return str
                } else {
                    var str = defaults.info.msg;
                    str = str.replace("{curr}", input);
                    str = str.replace("{sum}", pageCount);
                    return str
                }
            }

            function getText() {
                var msg = getMsg();
                if (msg) {
                    msg = $(msg)
                } else {
                    return ""
                }
                var input = msg.children(":text");
                if (options.info && options.info.text) {
                    var css = options.info.text;
                    for (temp in css) {
                        var val = eval("css." + temp);
                        input.css(temp, val)
                    }
                    return msg.html()
                } else {
                    var css = defaults.info.text;
                    for (temp in css) {
                        var val = eval("css." + temp);
                        input.css(temp, val)
                    }
                    return msg.html()
                }
            }

            function getHiddendId() {
                if (options.ajax && options.ajax.hiddendId) {
                    return options.ajax.hiddendId
                } else {
                    return defaults.ajax.hiddendId
                }
            }

            function getInt(val) {
                return parseInt(val)
            }

            function isCode(val) {
                if (val < 1) {
                    alert("输入值不能小于1");
                    return false
                }
                var patrn = /^[0-9]{1,8}$/;
                if (!patrn.exec(val)) {
                    alert("请输入正确的数字");
                    return false
                }
                if (val > pageCount) {
                    alert("输入值不能大于总页数");
                    return false
                }
                return true
            }

            function updateView() {
                currPage = getInt(currPage);
                pageCount = getInt(pageCount);
                var link = getLink();
                var firstPage = lastPage = 1;
                if (currPage - tempPage > 0) {
                    firstPage = currPage - tempPage
                } else {
                    firstPage = 1
                }
                if (firstPage + pageSize > pageCount) {
                    lastPage = pageCount + 1;
                    firstPage = lastPage - pageSize
                } else {
                    lastPage = firstPage + pageSize
                }
                var content = "";
                content += getFirst();
                if (currPage == 1) {
                    content += "<span class=\"disabled\" title=\"" + getPrev() + "\">" + getPrev() + " </span>"
                } else {
                    content += "<a href='" + link + "' title='" + (currPage - 1) + "'>" + getPrev() + " </a>"
                }
                for (firstPage; firstPage < lastPage; firstPage++) {
                    if (firstPage == currPage) {
                        content += "<span class=\"current\" title=\"" + firstPage + "\">" + firstPage + "</span>"
                    } else {
                        content += "<a href='" + link + "' title='" + firstPage + "'>" + firstPage + "</a>"
                    }
                }
                if (currPage == pageCount) {
                    content += "<span class=\"disabled\" title=\"" + getNext() + "\">" + getNext() + " </span>"
                } else {
                    content += "<a href='" + link + "' title='" + (currPage + 1) + "'>" + getNext() + " </a>"
                }
                content += getLast(pageCount);
                content += getText();
                obj.html(content);
                obj.children(":text").keypress(function (event) {
                    var keycode = event.which;
                    if (keycode == 13) {
                        var page = $(this).val();
                        if (isCode(page)) {
                            obj.children("a").unbind("click");
                            if (clickEvent != undefined) {
                                clickEvent(page);
                                return;
                            }
                            if (getUrl() != '') {
                                jump2page(page);
                            } else {
                                createView(page);
                            }
                        }
                    }
                });
                obj.children("a").each(function (i) {
                    var page = this.title;
                    $(this).click(function () {
                        obj.children("a").unbind("click");
                        if (clickEvent != undefined) {
                            clickEvent(page);
                            return;
                        }
                        if (getUrl() != '') {
                            jump2page(page);
                        } else {
                            createView(page);
                            $(this).focus();
                            return false
                        }
                    })
                })
            };

            function jump2page(page) {
                window.location.href = getUrl() + "?pageNo=" + page;
            }

            function createView(page) {
                currPage = page;
                var ajax = getAjax();
                if (ajax.on) {
                    var varUrl = ajax.url;
                    var param = getParam();
                    $.ajax({
                        url: varUrl,
                        type: 'GET',
                        data: param,
                        contentType: "application/x-www-form-urlencoded;utf-8",
                        async: true,
                        cache: false,
                        timeout: 60000,
                        error: function () {
                            //alert("访问服务器超时，请重试，谢谢！")
                        },
                        success: function (data) {
                            loadPageCount({dataType: ajax.dataType, callback: ajax.callback, data: data});
                            updateView()
                        }
                    })
                } else {
                    updateView()
                }
            }

            function checkParam() {
                if (currPage < 1) {
                    alert("配置参数错误\n错误代码:-1");
                    return false
                }
                if (currPage > pageCount) {
                    alert("配置参数错误\n错误代码:-2");
                    return false
                }
                if (pageSize < 2) {
                    alert("配置参数错误\n错误代码:-3");
                    return false
                }
                return true
            }

            function loadPageCount(options) {
                if (options.dataType) {
                    var data = options.data;
                    var resultPageCount = false;
                    var isB = true;
                    switch (options.dataType) {
                        case"json":
                            data = eval("(" + data + ")");
                            resultPageCount = data.pageCount;
                            break;
                        case"xml":
                            resultPageCount = $(data).find("pageCount").text();
                            break;
                        default:
                            isB = false;
                            var callback = options.callback + "(data)";
                            eval(callback);
                            resultPageCount = $("#pageCount").val();
                            break
                    }
                    if (resultPageCount) {
                        pageCount = resultPageCount
                    }
                    if (isB) {
                        var callback = options.callback + "(data)";
                        eval(callback)
                    }
                }
            }

            options = param;
            currPage = getCurrPage();
            pageCount = getPageCount();
            pageSize = getPageSize();
            tempPage = getInt(pageSize / 2);
            clickEvent = options.clickEvent;
            var cssStyle = getCssStyle();
            obj.addClass(cssStyle);
            if (checkParam()) {
                updateView();
                createView(currPage)
            }
        }
        return $(this)
    }
})(jQuery);
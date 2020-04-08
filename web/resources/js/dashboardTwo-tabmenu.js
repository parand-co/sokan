// $(document).ready(function () {
//     var e = [];
//     var t = 0;
//     $("#two-tabs>div>span.dynamic-tabs").each(function (t) {
//         e[t] = $(this).text();
//         $(this).parent().addClass("dynamic-tabs-pane")
//     });
//     $("#two-tabs").wrapInner('<div id="panes2" />');
//     $('<div id="tabs-butt2"><ul></ul></div>').prependTo("#two-tabs");
//     $.each(e, function (n) {
//         t = e.length + 1 - n;
//         if (n == 0) {
//             $('<li class="first current" style="z-index:' + t + ";right:" + n * -15 + 'px"><a class="tab-before">&nbsp;</a><a>' + e[n] + '</a><a class="tab-after">&nbsp;</a></li>').appendTo("#tabs-butt2 ul")
//         } else {
//             if (n == e.length - 1) {
//                 $('<li class="last" style="z-index:' + t + ";right:" + n * -15 + 'px"><a class="tab-before">&nbsp;</a><a>' + e[n] + '</a><a class="tab-after">&nbsp;</a></li>').appendTo("#tabs-butt2 ul")
//             } else {
//                 $('<li style="z-index:' + t + ";right:" + n * -15 + 'px"><a class="tab-before">&nbsp;</a><a>' + e[n] + '</a><a class="tab-after">&nbsp;</a></li>').appendTo("#tabs-butt2 ul")
//             }
//         }
//     });
//     $("#tabs-butt2 ul li a").eq(0).addClass("current");
//     $("#tabs-butt2 ul li").eq(0).addClass("current");
//     $("#panes2>div").eq(0).css("display", "block");
//     $("#tabs-butt2 li").click(function () {
//         var n = $("#tabs-butt2 li").index(this);
//         t = e.length + t + n;
//         $(this).parent().find(".current").toggleClass("current");
//         $(this).toggleClass("current");
//         $(this).css("z-index", t);
//         $("#panes2>div").css("display", "none");
//         $("#panes2>div").eq(n).css("display", "block")
//     })
// });


$(document).ready(function () {
    var e = [];
    var t = 0;
    var slider = "";
    $("#two-tabs>div>span.dynamic-tabs").each(function (t) {
        e[t] = $(this).text();
        $(this).parent().addClass("dynamic-tabs-pane")
    });
    $("#two-tabs").wrapInner('<div id="panes2" />');
    $('<div id="tabs-butt2"><ul></ul></div>').prependTo("#two-tabs");
    $.each(e, function (n) {
        t = e.length + 1 - n;
        if (n == 0) {
            $('<li class="first" style="z-index:' + t + '"><a class="tab-before">&nbsp;</a><a>' + e[n] + '</a><a class="tab-after">&nbsp;</a></li>').appendTo("#tabs-butt2 ul")
        } else {
            if (n == e.length - 1) {
                $('<li class="last" style="z-index:' + t + '"><a class="tab-before">&nbsp;</a><a>' + e[n] + '</a><a class="tab-after">&nbsp;</a></li>').appendTo("#tabs-butt2 ul")
            } else {
                $('<li style="z-index:' + t + '"><a class="tab-before">&nbsp;</a><a>' + e[n] + '</a><a class="tab-after">&nbsp;</a></li>').appendTo("#tabs-butt2 ul")
            }
        }
    });
    // $("#tabs-butt2 ul li a").eq(0).addClass("current");
    // $("#tabs-butt2 ul li").eq(0).addClass("current");
    // $("#panes2>div").eq(0).css("display", "block");
    $("#tabs-butt2 li").click(function () {
        var n = $("#tabs-butt2 li").index(this);
        t = e.length + t + n;
        $(this).parent().find(".current").toggleClass("current");
        $(this).toggleClass("current");
        $(this).css("z-index", t);

        if(slider === ""){
            slider = $("#sliderContainer").html();
        }

        if($("#panes2>div>div").eq(n).html() !== "\n"){
            $("#sliderContainer>div").css("display", "none");
            $("#sliderContainer").html($("#panes2>div").eq(n).html());
            $("#sliderContainer>span.dynamic-tabs").html("");
        } else {
            $("#sliderContainer>div").css("display", "none");
            $("#sliderContainer").html(slider);
        }


        // $("#panes2>div").css("display", "none");
        // $("#panes2>div").eq(n).css("display", "block")

        $("#tabs-butt22").find(".current").removeClass("current");
    });


    var r = [];
    var y = 0;
    $("#two-tabs1>div>span.dynamic-tabs").each(function (y) {
        r[y] = $(this).text();
        $(this).parent().addClass("dynamic-tabs-pane")
    });
    $("#two-tabs1").wrapInner('<div id="panes22" />');
    $('<div id="tabs-butt22"><ul></ul></div>').prependTo("#two-tabs1");
    $.each(r, function (n) {
        y = r.length + 1 - n;
        if (n == 0) {
            $('<li class="first" style="z-index:' + y + '"><a class="tab-before">&nbsp;</a><a>' + r[n] + '</a><a class="tab-after">&nbsp;</a></li>').appendTo("#tabs-butt22 ul")
        } else {
            if (n == r.length - 1) {
                $('<li class="last" style="z-index:' + y + '"><a class="tab-before">&nbsp;</a><a>' + r[n] + '</a><a class="tab-after">&nbsp;</a></li>').appendTo("#tabs-butt22 ul")
            } else {
                $('<li style="z-index:' + y + '"><a class="tab-before">&nbsp;</a><a>' + r[n] + '</a><a class="tab-after">&nbsp;</a></li>').appendTo("#tabs-butt22 ul")
            }
        }
    });
    $("#tabs-butt22 li").click(function () {
        var n = $("#tabs-butt22 li").index(this);
        y = r.length + y + n;
        $(this).parent().find(".current").toggleClass("current");
        $(this).toggleClass("current");
        $(this).css("z-index", y);

        if(slider === ""){
            slider = $("#sliderContainer").html();
        }
        if($("#panes2>div>div").eq(n).html() !== "\n"){
            $("#sliderContainer>div").css("display", "none");
            $("#sliderContainer").html($("#panes22>div").eq(n).html());
            $("#sliderContainer>span.dynamic-tabs").html("");
        } else {
            $("#sliderContainer>div").css("display", "none");
            $("#sliderContainer").html(slider);
        }

        $("#tabs-butt2").find(".current").removeClass("current");
    });
});

function modalTabDisable(tab) {
    if(tab == 1){
        document.getElementById("basic-tab1").style.display = "block";
        document.getElementById("basic-tab2").style.display = "none";
        document.getElementById("basic-tab3").style.display = "none";
    } else if (tab == 2){
        document.getElementById("basic-tab1").style.display = "none";
        document.getElementById("basic-tab2").style.display = "block";
        document.getElementById("basic-tab3").style.display = "none";
    } else if (tab == 3){
        document.getElementById("basic-tab1").style.display = "none";
        document.getElementById("basic-tab2").style.display = "none";
        document.getElementById("basic-tab3").style.display = "block";
    }
}

function timeRefresh(timeoutPeriod) {
    setTimeout("location.reload(true);", timeoutPeriod);
}
(function($) {
    $.fn.setTab = function() {
        var getTab = $(this), //this指向调用函数的ID
            panels = getTab.children("div.div-content").children(".tabs-item-div"),
            tabs = getTab.find("li");

        return this.each(function() {
            $(tabs).click(function(e) {
                var index = $.inArray(this, $(this).parent().find("li")); //this指向li
                if (panels.eq(index)[0]) {
                    $(tabs).removeClass("active");
                    $(this).addClass("active");
                    panels.css("display", "none").eq(index).css("display", "block");
                }
            });

        });
    }
})(jQuery);
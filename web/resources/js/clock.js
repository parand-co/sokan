var objDate = new Date();
var serverDateTime = objDate.toDateString() + " " + document.getElementById("WebPart_lblTime").innerHTML;
objDate = new Date(serverDateTime);
window.setInterval(RefreshTimer, 1000);

function RefreshTimer() {
    var clientSeconds = objDate.getSeconds();
    var lblTime = document.getElementById("WebPart_lblTime");
    clientSeconds += 1;
    objDate.setSeconds(clientSeconds);
    if (lblTime != null)
        lblTime.innerHTML = objDate.toTimeString().slice(0, 8);
}

$(document).ready(function () {

    $('.iosSlider').iosSlider({
        desktopClickDrag: true,
        snapToChildren: true,
        navSlideSelector: '.sliderContainer .slideSelectors .item',
        onSlideComplete: slideComplete,
        onSliderLoaded: sliderLoaded,
        onSlideChange: slideChange,
        autoSlide: true,
        scrollbar: true,
        scrollbarContainer: '.sliderContainer .scrollbarContainer',
        scrollbarMargin: '0',
        scrollbarBorderRadius: '0'
    });

});

function slideChange(args) {

    $('.sliderContainer .slideSelectors .item').removeClass('selected');
    $('.sliderContainer .slideSelectors .item:eq(' + (args.currentSlideNumber - 1) + ')').addClass('selected');

}

function slideComplete(args) {

    if (!args.slideChanged) return false;

    $(args.sliderObject).find('.text1, .text2').attr('style', '');

    $(args.currentSlideObject).find('.text1').animate({
        right: '100px',
        opacity: '0.5'
    }, 400, 'easeOutQuint');

    $(args.currentSlideObject).find('.text2').delay(200).animate({
        right: '50px',
        opacity: '0.5'
    }, 400, 'easeOutQuint');


}

function sliderLoaded(args) {

    $(args.sliderObject).find('.text1, .text2').attr('style', '');

    $(args.currentSlideObject).find('.text1').animate({
        right: '100px',
        opacity: '0.5'
    }, 400, 'easeOutQuint');

    $(args.currentSlideObject).find('.text2').delay(200).animate({
        right: '50px',
        opacity: '0.5'
    }, 400, 'easeOutQuint');

    slideChange(args);

}

(function ($) {

  "use strict";

  // MENU
  $('.navbar-collapse a').on('click', function () {
    $(".navbar-collapse").collapse('hide');
  });

  // CUSTOM LINK
  $('.smoothscroll').click(function () {
    var el = $(this).attr('href');
    var elWrapped = $(el);
    var header_height = $('.navbar').height();

    scrollToDiv(elWrapped, header_height);
    return false;

    function scrollToDiv(element, navheight) {
      var offset = element.offset();
      var offsetTop = offset.top;
      var totalScroll = offsetTop - navheight;

      $('body,html').animate({
        scrollTop: totalScroll
      }, 300);
    }
  });

})(window.jQuery);

const boxes = document.querySelectorAll('.box');

boxes.forEach(function (box) {
  box.addEventListener('mouseover', function () {
    box.style.backgroundColor = '#007bff';
    box.style.color = '#fff';
  });

  box.addEventListener('mouseout', function () {
    box.style.backgroundColor = '#f2f2f2';
    box.style.color = 'initial';
  });
});


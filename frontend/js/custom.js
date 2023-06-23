
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
  const h3 = box.querySelector('h3'); // Select the h3 element within the box

  box.addEventListener('mouseover', function () {
    box.style.backgroundColor = '#007bff';
    box.style.color = '#fff';
    h3.style.color = '#fff'; // Apply style to the h3 element
  });

  box.addEventListener('mouseout', function () {
    box.style.backgroundColor = '#fff';
    box.style.color = 'initial';
    h3.style.color = 'initial'; // Reset the style of the h3 element
  });
});





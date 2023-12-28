var wordcloudBtn = document.getElementById("wordcloud");

var newsReportBtn = document.getElementById("newsReport");

var wordcloud = document.getElementById("wordcloudSection");

var newsReport = document.getElementById("newsReportSection");

wordcloudBtn.addEventListener("click", function() {
	wordcloud.classList.remove("section_hide");
	newsReport.classList.add("section_hide");

});

newsReportBtn.addEventListener("click", function() {
	newsReport.classList.remove("section_hide");
	wordcloud.classList.add("section_hide");

});
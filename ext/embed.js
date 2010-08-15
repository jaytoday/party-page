var host = "http://localhost:8080/";
// var host = "http://party-page.appspot.com/";
var party_wrapper = document.createElement("div");
party_wrapper.class = "party_wrapper";
var party_script = document.createElement("script");
party_script.src = host + "party-page-js";
party_wrapper.appendChild(party_script);
document.getElementsByTagName("body")[0].appendChild(party_wrapper);
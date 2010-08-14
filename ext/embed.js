var party_wrapper = document.createElement("div");
party_wrapper.class = "party_wrapper";
var party_script = document.createElement("script");
party_script.src = "http://party-page.appspot.com/party-page-js";
party_wrapper.appendChild(party_script);
document.getElementsByTagName("body")[0].appendChild(party_wrapper);
{% include "jquery.js" %}

var jQueryScriptOutputted = false;
//santosh
$pageParty = {
  init: function(){
    var this_url = encodeURIComponent(location.href.split('?')[0]);
    $pageParty.loadStyles();
    $pageParty.chatTab = jQuery('<div id="chat_tab"><span class="inner">PARTY<span>!</span></span></div>');
    $pageParty.chatWrapper = jQuery('<div id="chat_wrapper"></div>');
    if ('{{ login_url }}'){
      $pageParty.chatWrapper.html('<div id="login_required"><a href="{{ login_url }}">Login With Your Google Account</a></div>');
    }else {
      // soon we will want to replace this with a valid constructor building the iframe contents
      // directly into the page.
      $pageParty.chatWrapper.html('<div id="party_page_wrapper"><iframe id="chat_iframe" src="{{ SERVER_HOST }}/iframe?url='+ this_url + '"></iframe></div>');
    }

    jQuery("body").append($pageParty.chatWrapper);
    jQuery("body").append($pageParty.chatTab);
    $pageParty.chatIframe = jQuery("#chat_iframe");
    console.log("chat tab injected");
    $pageParty.chatIframe.hide();
    $pageParty.chatTab.toggle(expand,collapse);

    function expand(){
      jQuery(this).animate({marginRight:"300px"}, 200);
      $pageParty.chatWrapper.animate({width: "300px"}, 200);
      $pageParty.chatIframe.show();		
    };
    function collapse(){
      jQuery(this).animate({marginRight:"0px"}, 200);
      $pageParty.chatWrapper.animate({width:"0px"}, 200);
      $pageParty.chatIframe.hide();
    };
  },
// inject chat css into the document head
  loadStyles: function(){
    var widgetStyles = '{{ css }}';
    var style = document.createElement('style');
    style.rel = "stylesheet"; style.type = "text/css";
    jQuery(style).html(widgetStyles);
    jQuery('head').append(style);
  },
};

$pageParty.init();

function getQueryParams( val ) {
  function querystringToDictionary(x){
    var y = x.split('=');
    retval[decodeURIComponent(y[0])] = decodeURIComponent(y[1]);
  }
  //Use the window.location.search if we don't have a val.
  var query = (val != null)?val : window.location.search;
  if(query.indexOf('&') < 0 && query.indexOf('&')<0 ){
    return {};
  }
  query = query.split('?')[1]
  console.log(query);
  var pairs = query.split('&');
  var retval = {};
  var check = [];
  pairs.map(querystringToDictionary);
  return retval;
}

var this_url = getQueryParams(window.location.href)['url'];
function callContentScript(msg){
$.ajax({
url: 'http://party-page.appspot.com/getchats',
type: "POST",
dataType: "html",	
data: {
content: msg,  
url: this_url
},
success: function(x){console.log(x);}
});
}
function rpc(function_name, args, callback, context) {
if(!window.console){console.log = function(msg){return false;}}
// Wrapper for jQuery's .ajax dor the sake of DRY
if (!args) {args = new Array();}
if (!callback) {var callback = function(response) {console.log(response)};}
var params = new Array(function_name);
for (var i = 0; i < args.length; i++) {params.push(args[i]);}
var body = JSON.stringify(params);
$.ajax({
url: "/rpc",
type: "POST",
data: body,
context: context,
dataType: "json",
success: function(response, textStatus) {callback(response, textStatus);},
error: function(response, textStatus) {console.log(response, textStatus);}
});
}
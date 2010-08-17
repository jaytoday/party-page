{% include "jquery.js" %}

var jQueryScriptOutputted = false;

$pageParty = {

    
    init: function(){
        
        var this_url = encodeURIComponent(window.location.href.split('?')[0]);
        $pageParty.loadStyles();
        $pageParty.chatTab = jQuery('<div id="chat_tab"><span class="inner">PARTY<span>!</span></span></div>');
        $pageParty.chatWrapper = jQuery('<div id="chat_wrapper"></div>');
        if ('{{ login_url }}'){
            $pageParty.chatWrapper.html('<div id="login_required"><a href="{{ login_url }}">Login With Your Google Account</a></div>');
        }else 
            $pageParty.chatWrapper.html('<div id="party_page_wrapper"><iframe id="chat_iframe" src="{{ SERVER_HOST }}/iframe?url='+ this_url + '"></iframe></div>');
        
        jQuery("body").append($pageParty.chatWrapper);
        jQuery("body").append($pageParty.chatTab);
	$pageParty.chatIframe = jQuery("#chat_iframe");
	console.log("chat tab injected");
 	var chat_tab = document.getElementById("chat_tab");
	chat_tab.ondragover = DONOTHING;
	chat_tab.ondragenter = DONOTHING;
	chat_tab.ondrop = urlDrop;
	
	$pageParty.chatIframe.hide();
	$pageParty.chatTab.toggle(
	    expand,collapse);   
	function expand(){
		jQuery(this).animate({marginRight:"400px"}, 200);
		$pageParty.chatWrapper.animate({ 
			width: "400px"
		}, 200);
		$pageParty.chatIframe.show();		
	}
	function collapse(){
  	        jQuery(this).animate({marginRight:"0px"}, 200);
		$pageParty.chatWrapper.animate({
			width:"0px"
		}, 200);
		$pageParty.chatIframe.hide();
	}

},

// inject quiz css into the document head
    loadStyles: function(){
        var widgetStyles = '{{ css }}';
        var style = document.createElement('style');
        style.rel = "stylesheet"; style.type = "text/css";
        jQuery(style).html(widgetStyles);
        jQuery('head').append(style);
    },

};
 function DONOTHING(e){
	e.preventDefault();
}

function urlDrop(e){
	var urlDropText = e.dataTransfer.getData("text/plain");
	console.log(urlDropText);
	callContentScript(urlDropText);
}
$pageParty.init();

function getQueryParams( val ) {
        //Use the window.location.search if we don't have a val.
        var query = val || window.location.search;
        query = query.split('?')[1]
        var pairs = query.split('&');
        var retval = {};
        var check = [];
        for( var i = 0; i < pairs.length; i++ ) {
                check = pairs[i].split('=');
                retval[decodeURIComponent(check[0])] = decodeURIComponent(check[1]);
        }
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
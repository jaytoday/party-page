{% include "jquery.js" %}

var jQueryScriptOutputted = false;

$partyPage = {

    init: function(){

        var this_url = encodeURIComponent(window.location.href.split('?')[0]);
        $partyPage.loadStyles();
        
        $partyPage.chatTab = jQuery('<div id="chat_tab"><span class="inner">PARTY<span>!</span></span></div>');
        $partyPage.chatWrapper = jQuery('<div id="chat_wrapper"></div>');
        if ('{{ login_url }}'){
            $partyPage.chatWrapper.html('<div><a href="{{ login_url }}">Click Here to Login</a></div>');
        }else 
            $partyPage.chatWrapper.html('<div id="party_page_wrapper"><iframe id="chat_iframe" src="{{ SERVER_HOST }}/iframe?url='
            + this_url + '"</div>');
             
        jQuery("body").append($partyPage.chatWrapper);
        jQuery("body").append($partyPage.chatTab);
	$partyPage.chatIframe = jQuery("#chat_iframe");
	$partyPage.chatIframe.hide();
	$partyPage.chatTab.toggle(
	    expand,collapse);   
	function expand(){
		jQuery(this).animate({marginRight:"400px"}, 200);
		$partyPage.chatWrapper.animate({ 
			width: "400px"
		}, 200);
		$partyPage.chatIframe.show();		
	}
	function collapse(){
  	        jQuery(this).animate({marginRight:"0px"}, 200);
		$partyPage.chatWrapper.animate({
			width:"0px"
		}, 200);
		$partyPage.chatIframe.hide();
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

$partyPage.init();



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
    
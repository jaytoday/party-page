
var jQueryScriptOutputted = false;

$partyPage = {
    
    initJQuery: function (){

        //if the jQuery object isn't available
        if (typeof(jQuery) == 'undefined') {
    
            if (!jQueryScriptOutputted) {
                //only output the script once..
                jQueryScriptOutputted = true;
            
                //output the script (load it from google api)
                document.write('<script type="text/javascript"'
                + ' src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js">'
                + '</script>');
            }
            setTimeout("$partyPage.initJQuery()", 50);
        } else {
            jQuery(function() { 
                    $partyPage.init(); 
            });
                               
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

    init: function(){

        var this_url = encodeURIComponent(window.location.href.split('?')[0]);
        $partyPage.loadStyles();
        
        $partyPage.chatTab = jQuery('<div id="chat_tab"><span class="inner">PARTY<span>!</span></span></div>');
        $partyPage.chatWrapper = jQuery('<div id="chat_wrapper"></div>');
        if ('{{ login_url }}')
            $partyPage.chatWrapper.html('<div><a href="{{ login_url }}continue=' + window.location.href + '">Click Here to Login</a></div>');
        else 
            $partyPage.chatWrapper.html('<div id="party_page_wrapper"><iframe id="chat_iframe" src="{{ SERVER_HOST }}/iframe?url='
            + this_url + '"</div>');
             
        jQuery("body").append($partyPage.chatWrapper);
        jQuery("body").append($partyPage.chatTab);
	$partyPage.chatIframe = jQuery("#chat_iframe");
	$partyPage.chatIframe.hide();
	$partyPage.chatTab.toggle(
	    expand,collapse);   
	function expand(){
		$(this).animate({marginRight:"400px"}, 200);
		$partyPage.chatWrapper.animate({ 
			width: "400px"
		}, 200);
		$partyPage.chatIframe.show();		
	}
	function collapse(){
  	        $(this).animate({marginRight:"0px"}, 200);
		$partyPage.chatWrapper.animate({
			width:"0px"
		}, 200);
		$partyPage.chatIframe.hide();
	}
}

};

$partyPage.initJQuery();

    
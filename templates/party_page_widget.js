
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

        $partyPage.loadStyles();
        
        $partyPage.chatTab = jQuery('<div id="chat_tab"></div>');
        $partyPage.chatWrapper = jQuery('<div id="chat_wrapper"></div>');
    
        /*if ('{{ login_url }}')
            insertEl = '<div><a href="{{ login_url }}continue=' + window.location.href + '">Click Here to Login</a></div>';
        else 
            insertEl = '<div id="party_page_wrapper"><iframe src="{{ SERVER_HOST }}/iframe"</div>';

         jQuery("script", jQuery("body")).each(function() {
            if (this.src.indexOf('party-page-js') > -1) 
            jQuery(this).after(insertEl); 
         } );
         */
         

             
         jQuery("body").append($partyPage.chatTab);
         jQuery("body").append($partyPage.chatWrapper);
  
           $partyPage.chatTab.live('click', function(){ 
             alert('click');
            $partyPage.chatWrapper.animate({ 
                width: 400
              }, 200 );

        });   
        
        $partyPage.chatTab.live('collapse', function(){ 
            $partyPage.chatWrapper.animate({ 
                width: 0
              }, 200 );
        });   
          
}

};

$partyPage.initJQuery();

    
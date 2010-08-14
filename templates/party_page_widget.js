
var jQueryScriptOutputted = false;

function initJQuery() {

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
        setTimeout("initJQuery()", 50);
    } else {
                        
        $(function() {  
            
                if ('{{ login_url }}')
                    insertEl = '<div><a href="{{ login_url }}continue=' + window.location.href + '">Click Here to Login</a></div>';
                else 
                    insertEl = '<div id="party_page_wrapper"><iframe src="{{ SERVER_HOST }}/iframe"</div>';

                $("script", $("body")).each(function() {
                    if (this.src.indexOf('party-page-js') > -1) 
                    $(this).after(insertEl); 
                 } );
            
        });
    }
            
}

initJQuery();

    
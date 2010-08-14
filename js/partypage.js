function get(el) {  							//getElementById
  return document.getElementById(el);
}

Palette_Factory = {

makeWidget: function () {
	var widget = {};
  with (DomGenerator) {
	  	widget.chat_icon = DIV ({id:"chat_icon" ,  onclick: (this.expand)}
	),
	  
		widget.chat_box = DIV ({id:"chat_box"}
	);
    }
  return widget;
  },

expand: function () {
	 var bubble_status = window.getComputedStyle(get('chat_box'),null).getPropertyValue("width");
	 if (bubble_status=="0px")
	{
	$("#chat_box").animate({ 
    width: "400px"
  }, 200 );
	$("#chat_icon").animate({ 
    marginRight: "400px"
  }, 200 );
	}
	
	else
	{
	$("#chat_box").animate({ 
    width: "0px"
  }, 200 );
	$("#chat_icon").animate({ 
    marginRight: "0px"
  }, 200 );
	}
	
  }
};
g_widget = Palette_Factory.makeWidget();

document.body.appendChild(g_widget.chat_box);

document.body.appendChild(g_widget.chat_icon);

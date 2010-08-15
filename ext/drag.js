function makeDrag() {
	console.log('drag')
	var anchors = document.getElementsByTagName("a");
	for(var i = 0, a; a = anchors[i]; i++)
	{
		if(a.href.indexOf("http")==0||a.href.indexOf("/")==0)
		{
			a.setAttribute("draggable","true");
			a.ondragstart = dragGift;
		}
	}
	function dragGift(e){
		console.log(e.target.parentElement.href);
		e.dataTransfer.setData("text/plain",e.target.parentElement.href);
	};
	function callContentScript(message){
		msg = {content: message};
		chrome.extension.sendRequest(msg, function(response) {
			console.log(response);
		});
	};
	console.log('ssss')
		var chat_tab = document.getElementById("chat_tab");
		console.log(chat_tab)
		chat_tab.ondragover = DONOTHING;
		chat_tab.ondragenter = DONOTHING;
		chat_tab.ondrop = urlDrop;
		function DONOTHING(e){
			e.preventDefault();
		}

		function urlDrop(e){
			var urlDropText = e.dataTransfer.getData("text/plain");
			console.log(urlDropText);
			callContentScript(urlDropText);
		}	
}
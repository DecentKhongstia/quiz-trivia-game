<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%-- <!DOCTYPE html>
<html>
	<head>
        <title>Spring Secured Sockets</title>
        <link href="<c:url value="/resources/styles/app.css"/>" rel="stylesheet">
        <link href="<c:url value="/resources/styles/index.css"/>" rel="stylesheet">

        <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
        <script src="<c:url value="/resources/vendor/angular/angular.min.js" />"></script>
        <script src="<c:url value="/resources/vendor/angular/angular-route.min.js" />"></script>

        <script src="<c:url value="/resources/scripts/app.js" />"></script>
        <script src="<c:url value="/resources/scripts/services/SocketService.js" />"></script>
        <script src="<c:url value="/resources/scripts/controllers/indexController.js" />"></script>
        <script src="<c:url value="/resources/scripts/controllers/socketController.js" />"></script>
        <script src="<c:url value="/resources/scripts/controllers/successController.js" />"></script>
        <script src="<c:url value="/resources/scripts/routes/router.js" />"></script>
    </head>

    <body ng-controller="indexController">
        <main>
            <div class="wrapper">
                <h1>Welcome!</h1>
                <span>{{greeting}}</span>
                <a href="${pageContext.request.contextPath}/login">Click to login.</a>
            </div>
        </main>
    </body>
</html> --%>

<html>
	<head>
	    <title>Chat WebSocket</title>
	    
	    <script src="resources/vendor/sockjs/sockjs.min.js"></script>
	    <script src="resources/vendor/stomp/stomp.min.js"></script>
	    
	    <script type="text/javascript">

	        var stompClient = null;

	        function setConnected(connected) {

	            document.getElementById('connect').disabled = connected;
	            document.getElementById('disconnect').disabled = !connected;
	            document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
	            /* document.getElementById('subscriptionDiv').style.visibility = connected ? 'visible' : 'hidden'; */
	            document.getElementById('response').innerHTML = '';
	        }

	        function connect() {

	            var socket = new SockJS('chat2');
	            stompClient = Stomp.over(socket);

	            stompClient.connect({}, function(frame) {

	            	setConnected(true);
	                console.log('Connected: ' + frame);
	                /* stompClient.subscribe('/topic/messages', function(messageOutput) {
	                    showMessageOutput(JSON.parse(messageOutput.body));
	                }); */ 
	                var from = document.getElementById('from').value;
	                stompClient.subscribe('/user/'+from+'/queue/reply', function(messageOutput) {
	                    showMessageOutput(JSON.parse(messageOutput.body));
	                });
	            });
	        }
			
	        function disconnect() {
	        	document.getElementById('conversationDiv').style.visibility = 'hidden';
	            if(stompClient != null) {
	                stompClient.disconnect();
	            }

	            setConnected(false);
	            console.log("Disconnected");
	        }

	        function sendMessage() {
			
	        	var from = document.getElementById('from').value;
	            var text = document.getElementById('text').value;
	            var to = document.getElementById('to').value;

	            /* stompClient.send("/app/chat", {}, JSON.stringify({'from':from, 'text':text})); */
            	stompClient.send('/app/message', {}, JSON.stringify({'from':from, 'text':text, 'to': to}));
	        }

	        function showMessageOutput(messageOutput) {

	            var response = document.getElementById('response');
	            var p = document.createElement('p');
	            p.style.wordWrap = 'break-word';
	            p.appendChild(document.createTextNode(messageOutput.from + ": " + messageOutput.text + " (" + messageOutput.time + ")"));
	            response.appendChild(p);
	        }
	    </script>
	    
	</head>
	
	<body onload="disconnect()">

		<div>
		
		
			<div>
				<input type="text" id="from" placeholder="Choose a nickname"/>
			</div>
			<br />
		    <div>
		        <button id="connect" onclick="connect();">Connect ME</button>
		        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
		    </div>
		    <br />
		    <div id="conversationDiv">
		        <input type="text" id="text" placeholder="Write a message..."/>
		    	<input type="text" id="to" placeholder="To..."/>
		        <button id="sendMessage" onclick="sendMessage();">Send Message</button>
		        <p id="response"></p>
		    </div>
		</div>

	</body>
</html>
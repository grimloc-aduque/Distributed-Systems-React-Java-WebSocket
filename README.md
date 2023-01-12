# Distributed-Systems-React-Java-WebSocket

# Technologies
Web Client
* React 
    * React functional components
    * React Hooks
        * useState
        * useEffect
        * useMemo
        * useTable
        * useWebSocket
    
WebSocket Server
* WildFly
* Java
   * java.util
   * javax.websocket

# Web Client
Reactive table that keeps track of items on stock among all users.

<img src="https://github.com/grimloc-aduque/Distributed-Systems-React-Java-WebSocket/blob/main/git_images/web_client.png" style="width:600px;"/>


# WebSocket Server
Accepts websocket connections. Responds to messages from users requesting the list of items in stock and sending stock updates. Keeps the information of all web clients updated.

# Distributed-Systems-React-Java-WebSocket

# Technologies
Web Client: React
* React Hooks
    * useState
    * useEffect
    * useMemo
    * useTable
    * useWebSocket
    
WebSocket Server:
* WildFly
* Java
   * java.util
   * javax.websocket

# Web Client
Reactive table that keeps track of items on stock among all users

# WebSocket Server
Accepts websocket connections. Responds to messages from users requesting the list of items in stock and sending stock updates. Keeps the information of all web clients updated.

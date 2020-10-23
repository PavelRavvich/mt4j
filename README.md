# bridge
Bridge between Java and Meta Trader via http. Tomcat on port `80`.

Bridge works as http service with further enpoints:

POST `/api/config` - set advisor config (Advisor bootstrap sinc with server side)
GET `/api/signal` - resolve indicator's time series and return signal corresponding strategy.

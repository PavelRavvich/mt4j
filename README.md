# Bridge
Http server based on Tomcat is the bridge between Java and Meta Trader via http. 

Port `80`. Port can't be changed by specific Meta Trader reason, see more in MQL5 documentation: [https://www.mql5.com/en/docs/network/webrequest][mt5doc].

Endpoints:

POST `/api/config` - set advisor config (Advisor bootstrap sync with server side)

GET `/api/signal` - resolve indicator's time series and return signal corresponding strategy.


[mt5doc]: https://www.mql5.com/en/docs/network/webrequest
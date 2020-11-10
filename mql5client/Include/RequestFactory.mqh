#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <MarketProvider.mqh>

struct Position
  {
   string            type;
   string            advisorid;
   string            positionId;
   double            lot;
   int               stopLoss;
   int               takeProfit;
   long              openAt;
   long              closeAt;
   double            profit;
  };

class RequestFactory
  {

   MarketProvider *  _market_provider;

   long              _magic;

   string            _position_formatter;

public:
                     RequestFactory(long magic)
     {
      _market_provider = new MarketProvider();
      _position_formatter = "{ \"type\": \"%s\", \"advisorId\": \"%s\", \"positionId\": %s, \"lot\": %.2f, \"stopLoss\": %.0f, \"takeProfit\": %.0f, \"openAt\": %.0f, \"closeAt\": %.0f, \"profit\": %.2f }";
     }
                    ~RequestFactory() { delete _market_provider; }

public:

   string            GetAddAdvisorRequestBody(string inputs)
     {
      return "{ \"magic\": " + (string) _magic + ", \"inputs\": " + inputs + " }";
     }

   // todo add open positions to signal request
   string            GetSignalRequestBody(string advisor_id, string strategy_name, string symbol)
     {
      return "{ \"advisorId\": \"" +  advisor_id + "\", \"strategyName\": \"" + strategy_name
             + "\", \"rates\": " + _market_provider.GetRates(symbol) + ", \"positions\": " + "[]" + " }";
     }

   string            GetPositionRequestBody(Position &position)
     {
      return StringFormat(_position_formatter, position.type, position.advisor_id, position.position_id, position.lot,
                          position.stop_loss, position.take_profit, position.open_at, position.close_at, position.profit);
     }

  };
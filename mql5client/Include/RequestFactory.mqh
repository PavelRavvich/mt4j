#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <MarketProvider.mqh>


struct Position
  {
   string type;
   string advisor_id;
   string position_id;
   double lot;
   int stop_loss;
   int take_profit;
   long open_at;
   long close_at;
   double profit;
  };

class RequestFactory
  {

   int               _to_copy;

   MarketProvider *  _market_provider;

   string _position_formatter;

public:
                     RequestFactory(int to_copy = 100)
     {
      _to_copy = to_copy;
      _position_formatter = "{ \"type\": %s, \"advisorId\": %s, \"positionId\": %s, \"lot\": %.2f, \"stopLoss\": %.0f, \"takeProfit\": %.0f, \"openAt\": %.0f, \"closeAt\": %.0f, \"profit\": %.2f }";
      _market_provider = new MarketProvider();
     }
                    ~RequestFactory() { delete _market_provider; }

public:

   string            GetAddAdvisorRequestBody(long magic, string inputs)
     {
      return "{ \"magic\": " + magic + ", \"inputs\": " + inputs + " }";
     }

   string            GetSignalRequestBody(string advisor_id, string strategy_name, string symbol)
     {
      return "{ \"advisorId\": " +  advisor_id + ", \"strategyName\": " + strategy_name
                + ", \"rates\": " + _market_provider.GetRates(symbol, _to_copy) + " }";
     }

   string            GetPositionRequestBody(Position &position)
     {
      return StringFormat(_position_formatter, position.type, position.advisor_id, position.position_id, position.lot,
                          position.stop_loss, position.take_profit, position.open_at, position.close_at, position.profit);
     }

  };
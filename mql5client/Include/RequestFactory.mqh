#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <MarketProvider.mqh>
#include <PositionProvider.mqh>


class RequestFactory
  {

   long                _magic;

   string              _symbol;

   MarketProvider *    _market_provider;

   PositionProvider *  _position_provider;

public:
                     RequestFactory(long magic, string symbol)
     {
      _magic = magic;
      _symbol = symbol;
      _market_provider = new MarketProvider(symbol);
      _position_provider = new PositionProvider(magic);
     }

                    ~RequestFactory() { delete _market_provider; delete _position_provider; }

public:

   string            GetAddAdvisorRequestBody(string inputs)
     {
      return "{ \"magic\": " + (string) _magic + ", \"inputs\": " + inputs + " }";
     }

   string            GetSignalRequestBody(string advisor_id, string strategy_name)
     {
      return "{ \"advisorId\": \"" +  advisor_id + "\", \"strategyName\": \"" + strategy_name
             + "\", \"rates\": " + _market_provider.GetRates() +
             ", \"positions\": " + _position_provider.GetPositions() + " }";
     }

  };
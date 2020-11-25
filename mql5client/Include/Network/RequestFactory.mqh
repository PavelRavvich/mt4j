#property strict

#include <Provider\MarketProvider.mqh>
#include <Provider\AccountProvider.mqh>
#include <Provider\PositionProvider.mqh>


class RequestFactory
  {

   long                _magic;

   string              _symbol;

   MarketProvider *    _market_provider;

   AccountProvider *   _account_provider;

   PositionProvider *  _position_provider;

public:
                     RequestFactory(long magic, string symbol)
     {
      _magic = magic;
      _symbol = symbol;
      _market_provider = new MarketProvider(symbol);
      _position_provider = new PositionProvider(magic);
     }

                    ~RequestFactory() { delete _market_provider; delete _position_provider; delete _account_provider; }

public:

   string            GetAddAdvisorRequestBody(string inputs)
     {
      return "{ \"magic\": " + (string) _magic + ", \"inputs\": " + inputs + " }";
     }

   string            GetSignalRequestBody(string advisor_id, string strategy_name)
     {
      return "{ \"advisorId\": \"" +  advisor_id + "\", \"strategyName\": \"" + strategy_name +
             "\", \"rates\": " + _market_provider.GetRates() +
             ", \"positions\": " + _position_provider.GetPositions() +
             ", \"account\": " + _account_provider.GetAccount() +
             " }";
     }

  };
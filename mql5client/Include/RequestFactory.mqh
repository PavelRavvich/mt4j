#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <MqlRatesProvider.mqh>


class RequestFactory
  {

   int               _to_copy;

   RatesProvider *   _rates_provider;

public:
                     RequestFactory(int to_copy = 100)
     {
      _to_copy = to_copy;
      _rates_provider = new RatesProvider();
     }
                    ~RequestFactory() { delete _rates_provider; }

public:

   string            GetAddAdvisorRequestBody(long magic, string inputs)
     {
      return "{ \"magic\": " + magic + ", \"inputs\": " + inputs + " }";
     }

   string            GetSignalRequestBody(string advisor_id, string strategy_name, string symbol)
     {
      return "{ \"advisorId\": " +  advisor_id + ", \"strategyName\": " + strategy_name
                + ", \"rates\": " + _rates_provider.GetRates(symbol, _to_copy) + " }";
     }

  };
#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <MqlRatesProvider.mqh>


class RequestFactory
  {

   int               _to_copy;

   string            _signal_request_formatter;

   string            _add_advisor_request_formatter;

   RatesProvider *   _rates_provider;

public:
                     RequestFactory(int to_copy = 100)
     {
      _to_copy = to_copy;
      _rates_provider = new RatesProvider();
      _add_advisor_request_formatter = "{ \"magic\": %s, \"inputs\": %s }";
      _signal_request_formatter = "{ \"advisorId\": %s, \"strategyName\": %s, \"rates\": %s }";
     }
                    ~RequestFactory() { delete _rates_provider; }

public:

   string            GetAddAdvisorRequestBody(long magic, string inputsJson)
     {
      return StringFormat(_add_advisor_request_formatter, magic, inputsJson);
     }

   string            GetSignalRequestBody(string advisor_id, string strategy_name, string symbol)
     {
      string rates = _rates_provider.GetRates(symbol, _to_copy);
      return StringFormat(_signal_request_formatter, advisor_id, strategy_name, rates);
     }

  };
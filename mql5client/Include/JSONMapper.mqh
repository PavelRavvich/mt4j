#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <JAson.mqh>

// TODO: 11/4/2020 make multi timeframe protocol
class Mapper
  {

   CJAVal *          _json;

   string            _signal_request_formatter;

   string            _rates_formatter;

public:
                     Mapper()
     {
      _json = new CJAVal(NULL, jtUNDEF);
      _signal_request_formatter = "{ \"advisorId\": %s, \"strategyName\": %s, \"rates\": %s }";
      _rates_formatter = "{ \"open\": %G, \"high\": %G, \"low\": %G, \"close\": %G, \"tickVolume\": %d, \"time\": %d, \"spread\": %d, \"realVolume\": %d }";
     }
                    ~Mapper() { delete _json; }

public:
   string            GetSignalRequestBody(string advisor_id, string strategy_name, string symbol, ENUM_TIMEFRAMES  timeframe, int to_copy = 100)
     {
      string rates = MqlRatesToJson(symbol, timeframe, to_copy);
      return StringFormat(_signal_request_formatter, advisor_id, strategy_name, rates);
     }

private:
   string            MqlRatesToJson(string symbol, ENUM_TIMEFRAMES  timeframe, int to_copy)
     {
      string items = "";
      MqlRates rates[];
      ArraySetAsSeries(rates, true);
      int copied = CopyRates(symbol, timeframe, 0, to_copy, rates);
      if(copied > 0)
        {
         int size = fmin(copied, 10);
         for(int i = 0; i < size; i++)
           {
            string item = StringFormat(_rates_formatter, rates[i].open, rates[i].high, rates[i].low,
                                       rates[i].close, rates[i].tick_volume, rates[i].time,
                                       rates[i].spread, rates[i].real_volume);
            items += item;
            if(i != copied - 1)
               items += ",";
           }
        }
      else
         Print("Failed to get history data for the symbol ", symbol);

      return StringFormat("[%s]", items);
     }
  };

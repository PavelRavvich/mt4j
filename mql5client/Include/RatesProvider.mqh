#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

class RatesProvider
  {

   string            _rates_butch_formatter;

   string            _rates_formatter;

public:
                     RatesProvider()
     {
      _rates_formatter = "{ \"open\": %G, \"high\": %G, \"low\": %G, \"close\": %G, \"tickVolume\": %d, \"time\": %d, \"spread\": %d, \"realVolume\": %d }";
      _rates_butch_formatter = "{ \"M_1\": %s, \"M_2\": %s, \"M_3\": %s, \"M_4\": %s, \"M_5\": %s, \"M_6\": %s, \"M_10\": %s, \"M_12\": %s, \"M_15\": %s, \"M_20\": %s, \"M_30\": %s, \"H_1\": %s, \"H_2\": %s, \"H_3\": %s, \"H_4\": %s, \"H_6\": %s, \"H_8\": %s, \"H_12\": %s, \"D_1\": %s, \"W_1\": %s, \"MN_1\": %s }";
     }
                    ~RatesProvider() {}


public:
   string            GetRates(string symbol, int to_copy)
     {
      return StringFormat(_rates_butch_formatter,
                          CopyMqlRates(symbol, PERIOD_M1, to_copy),
                          CopyMqlRates(symbol, PERIOD_M2, to_copy),
                          CopyMqlRates(symbol, PERIOD_M3, to_copy),
                          CopyMqlRates(symbol, PERIOD_M4, to_copy),
                          CopyMqlRates(symbol, PERIOD_M5, to_copy),
                          CopyMqlRates(symbol, PERIOD_M6, to_copy),
                          CopyMqlRates(symbol, PERIOD_M10, to_copy),
                          CopyMqlRates(symbol, PERIOD_M12, to_copy),
                          CopyMqlRates(symbol, PERIOD_M15, to_copy),
                          CopyMqlRates(symbol, PERIOD_M20, to_copy),
                          CopyMqlRates(symbol, PERIOD_M30, to_copy),
                          CopyMqlRates(symbol, PERIOD_H1, to_copy),
                          CopyMqlRates(symbol, PERIOD_H2, to_copy),
                          CopyMqlRates(symbol, PERIOD_H3, to_copy),
                          CopyMqlRates(symbol, PERIOD_H4, to_copy),
                          CopyMqlRates(symbol, PERIOD_H6, to_copy),
                          CopyMqlRates(symbol, PERIOD_H8, to_copy),
                          CopyMqlRates(symbol, PERIOD_H12, to_copy),
                          CopyMqlRates(symbol, PERIOD_D1, to_copy),
                          CopyMqlRates(symbol, PERIOD_W1, to_copy),
                          CopyMqlRates(symbol, PERIOD_MN1, to_copy));

     }

private:
   string            CopyMqlRates(string symbol, ENUM_TIMEFRAMES  timeframe, int to_copy)
     {
      string items = "";
      MqlRates rates[];
      ArraySetAsSeries(rates, true);
      int copied = CopyRates(symbol, timeframe, 0, to_copy, rates);
      if(copied == to_copy)
        {
         for(int i = 0; i < copied; i++)
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

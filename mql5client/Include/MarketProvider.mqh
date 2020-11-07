#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

class MarketProvider
  {

   string            _rates_formatter;

public:
                     MarketProvider()
     { _rates_formatter = "{ \"open\": %G, \"high\": %G, \"low\": %G, \"close\": %G, \"tickVolume\": %d, \"time\": %d, \"spread\": %d, \"realVolume\": %d }"; }
                    ~MarketProvider() {}


public:
   string            GetRates(string symbol, int to_copy)
     {
      string m1 = "\"M_1\": " + CopyMqlRates(symbol, PERIOD_M1, to_copy) + ",";
      string m2 = "\"M_2\": " + CopyMqlRates(symbol, PERIOD_M2, to_copy) + ",";
      string m3 = "\"M_3\": " + CopyMqlRates(symbol, PERIOD_M3, to_copy) + ",";
      string m4 = "\"M_4\": " + CopyMqlRates(symbol, PERIOD_M4, to_copy) + ",";
      string m5 = "\"M_5\": " + CopyMqlRates(symbol, PERIOD_M5, to_copy) + ",";
      string m6 = "\"M_6\": " + CopyMqlRates(symbol, PERIOD_M6, to_copy) + ",";
      string m10 = "\"M_10\": " + CopyMqlRates(symbol, PERIOD_M10, to_copy) + ",";
      string m12 = "\"M_12\": " + CopyMqlRates(symbol, PERIOD_M12, to_copy) + ",";
      string m15 = "\"M_15\": " + CopyMqlRates(symbol, PERIOD_M15, to_copy) + ",";
      string m20 = "\"M_20\": " + CopyMqlRates(symbol, PERIOD_M20, to_copy) + ",";
      string m30 = "\"M_30\": " + CopyMqlRates(symbol, PERIOD_M30, to_copy) + ",";
      string h1 = "\"H_1\": " + CopyMqlRates(symbol, PERIOD_H1, to_copy) + ",";
      string h2 = "\"H_2\": " + CopyMqlRates(symbol, PERIOD_H2, to_copy) + ",";
      string h3 = "\"H_3\": " + CopyMqlRates(symbol, PERIOD_H3, to_copy) + ",";
      string h4 = "\"H_4\": " + CopyMqlRates(symbol, PERIOD_H4, to_copy) + ",";
      string h6 = "\"H_6\": " + CopyMqlRates(symbol, PERIOD_H6, to_copy) + ",";
      string h8 = "\"H_8\": " + CopyMqlRates(symbol, PERIOD_H8, to_copy) + ",";
      string h12 = "\"H_12\": " + CopyMqlRates(symbol, PERIOD_H12, to_copy) + ",";
      string d1 = "\"D_1\": " + CopyMqlRates(symbol, PERIOD_D1, to_copy) + ",";
      string w1 = "\"W_1\": " + CopyMqlRates(symbol, PERIOD_W1, to_copy) + ",";
      string mn1 = "\"MN_1\": " + CopyMqlRates(symbol, PERIOD_MN1, to_copy);
      return "{" + m1 + m2 + m3 + m4 + m5 + m6 + m10 + m12 + m15 + m20 + m30
                 + h1 + h2 + h3 + h4 + h6 + h8 + h12 + d1 + w1 + mn1 + "}";
     }

private:
   string            CopyMqlRates(string symbol, ENUM_TIMEFRAMES  timeframe, int to_copy)
     {
      Bars(symbol, timeframe);
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
               items += ", ";
           }
        }
      else
         Print("Failed to get history data for the symbol ", symbol);

      return "[" + items + "]";
     }
  };

#property strict

#include <ApplicationContext\ApplicationContext.mqh>

//+------------------------------------------------------------------+
//| Provide rates from MT                                            |
//+------------------------------------------------------------------+
class MarketProvider
  {
   int               buffer_size;
   string            rates_formatter;
public:
                     MarketProvider(int _buffer_size = 20)
     {
      buffer_size = _buffer_size;
      rates_formatter = "{ \"open\": %G, \"high\": %G, \"low\": %G, \"close\": %G, \"tickVolume\": %d, \"time\": %d, \"spread\": %d, \"realVolume\": %d }";
     }
                    ~MarketProvider() {}
public:
   string            GetRates();
private:
   string            CopyMqlRates(ENUM_TIMEFRAMES  timeframe);
  };

//+------------------------------------------------------------------+
//| Build JSON of all existed timeframes                             |
//+------------------------------------------------------------------+
string::MarketProvider            GetRates()
  {
   string m1 = "\"M_1\": " + CopyMqlRates(PERIOD_M1) + ",";
   string m2 = "\"M_2\": " + CopyMqlRates(PERIOD_M2) + ",";
   string m3 = "\"M_3\": " + CopyMqlRates(PERIOD_M3) + ",";
   string m4 = "\"M_4\": " + CopyMqlRates(PERIOD_M4) + ",";
   string m5 = "\"M_5\": " + CopyMqlRates(PERIOD_M5) + ",";
   string m6 = "\"M_6\": " + CopyMqlRates(PERIOD_M6) + ",";
   string m10 = "\"M_10\": " + CopyMqlRates(PERIOD_M10) + ",";
   string m12 = "\"M_12\": " + CopyMqlRates(PERIOD_M12) + ",";
   string m15 = "\"M_15\": " + CopyMqlRates(PERIOD_M15) + ",";
   string m20 = "\"M_20\": " + CopyMqlRates(PERIOD_M20) + ",";
   string m30 = "\"M_30\": " + CopyMqlRates(PERIOD_M30) + ",";
   string h1 = "\"H_1\": " + CopyMqlRates(PERIOD_H1) + ",";
   string h2 = "\"H_2\": " + CopyMqlRates(PERIOD_H2) + ",";
   string h3 = "\"H_3\": " + CopyMqlRates(PERIOD_H3) + ",";
   string h4 = "\"H_4\": " + CopyMqlRates(PERIOD_H4) + ",";
   string h6 = "\"H_6\": " + CopyMqlRates(PERIOD_H6) + ",";
   string h8 = "\"H_8\": " + CopyMqlRates(PERIOD_H8) + ",";
   string h12 = "\"H_12\": " + CopyMqlRates(PERIOD_H12) + ",";
   string d1 = "\"D_1\": " + CopyMqlRates(PERIOD_D1) + ",";
   string w1 = "\"W_1\": " + CopyMqlRates(PERIOD_W1) + ",";
   string mn1 = "\"MN_1\": " + CopyMqlRates(PERIOD_MN1);
   return "{" + m1 + m2 + m3 + m4 + m5 + m6 + m10 + m12 + m15 + m20 + m30
          + h1 + h2 + h3 + h4 + h6 + h8 + h12 + d1 + w1 + mn1 + "}";
  }


//+------------------------------------------------------------------+
//| Copy rates of timeframe as JSON                                  |
//+------------------------------------------------------------------+
string::MarketProvider            CopyMqlRates(ENUM_TIMEFRAMES  timeframe)
  {
   Bars(Symbol(), timeframe);
   string items = "";
   MqlRates rates[];
   ArraySetAsSeries(rates, true);
   int copied = CopyRates(Symbol(), timeframe, 0, buffer_size, rates);
   if(copied == buffer_size)
     {
      for(int i = 0; i < copied; i++)
        {
         string item = StringFormat(rates_formatter, rates[i].open, rates[i].high, rates[i].low,
                                    rates[i].close, rates[i].tick_volume, rates[i].time,
                                    rates[i].spread, rates[i].real_volume);
         items += item;
         if(i != copied - 1)
            items += ", ";
        }
     }
   else
      Print("Failed to get history data for the symbol ", Symbol());

   return "[" + items + "]";
  }
//+------------------------------------------------------------------+

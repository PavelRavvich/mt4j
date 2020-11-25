#property strict


class MarketProvider
  {

   string            _symbol;

   int               _buffer_size;

   string            _rates_formatter;

public:
                     MarketProvider(string symbol, int buffer_size = 20)
     {
      _buffer_size = buffer_size;
      _rates_formatter = "{ \"open\": %G, \"high\": %G, \"low\": %G, \"close\": %G, \"tickVolume\": %d, \"time\": %d, \"spread\": %d, \"realVolume\": %d }";
     }
                    ~MarketProvider() {}


public:
   string            GetRates()
     {
      string m1 = "\"M_1\": " + CopyMqlRates(_symbol, PERIOD_M1, _buffer_size) + ",";
      string m2 = "\"M_2\": " + CopyMqlRates(_symbol, PERIOD_M2, _buffer_size) + ",";
      string m3 = "\"M_3\": " + CopyMqlRates(_symbol, PERIOD_M3, _buffer_size) + ",";
      string m4 = "\"M_4\": " + CopyMqlRates(_symbol, PERIOD_M4, _buffer_size) + ",";
      string m5 = "\"M_5\": " + CopyMqlRates(_symbol, PERIOD_M5, _buffer_size) + ",";
      string m6 = "\"M_6\": " + CopyMqlRates(_symbol, PERIOD_M6, _buffer_size) + ",";
      string m10 = "\"M_10\": " + CopyMqlRates(_symbol, PERIOD_M10, _buffer_size) + ",";
      string m12 = "\"M_12\": " + CopyMqlRates(_symbol, PERIOD_M12, _buffer_size) + ",";
      string m15 = "\"M_15\": " + CopyMqlRates(_symbol, PERIOD_M15, _buffer_size) + ",";
      string m20 = "\"M_20\": " + CopyMqlRates(_symbol, PERIOD_M20, _buffer_size) + ",";
      string m30 = "\"M_30\": " + CopyMqlRates(_symbol, PERIOD_M30, _buffer_size) + ",";
      string h1 = "\"H_1\": " + CopyMqlRates(_symbol, PERIOD_H1, _buffer_size) + ",";
      string h2 = "\"H_2\": " + CopyMqlRates(_symbol, PERIOD_H2, _buffer_size) + ",";
      string h3 = "\"H_3\": " + CopyMqlRates(_symbol, PERIOD_H3, _buffer_size) + ",";
      string h4 = "\"H_4\": " + CopyMqlRates(_symbol, PERIOD_H4, _buffer_size) + ",";
      string h6 = "\"H_6\": " + CopyMqlRates(_symbol, PERIOD_H6, _buffer_size) + ",";
      string h8 = "\"H_8\": " + CopyMqlRates(_symbol, PERIOD_H8, _buffer_size) + ",";
      string h12 = "\"H_12\": " + CopyMqlRates(_symbol, PERIOD_H12, _buffer_size) + ",";
      string d1 = "\"D_1\": " + CopyMqlRates(_symbol, PERIOD_D1, _buffer_size) + ",";
      string w1 = "\"W_1\": " + CopyMqlRates(_symbol, PERIOD_W1, _buffer_size) + ",";
      string mn1 = "\"MN_1\": " + CopyMqlRates(_symbol, PERIOD_MN1, _buffer_size);
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

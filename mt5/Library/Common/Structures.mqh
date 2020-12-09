#property strict

#include <Library\Common\Enums.mqh>

//+------------------------------------------------------------------+
//| Http responce                                                    |
//+------------------------------------------------------------------+
struct HttpResponse
  {
   int               status;
   string            body;
  };
//+------------------------------------------------------------------+
//| Http request                                                     |
//+------------------------------------------------------------------+
struct HttpRequest
  {
   string            url;
   string            body;
   string            headers;
  };
//+------------------------------------------------------------------+
//| REST configuration                                               |
//+------------------------------------------------------------------+
struct RestConfig
  {
   string            host;
   int               port;
   string            headers;
   int               timeout;
  };
//+------------------------------------------------------------------+
//| Signal dto                                                       |
//+------------------------------------------------------------------+
struct Signal
  {
   long              positionId;
   string            advisorId;
   string            type;
   double            lot;
   int               stopLoss;
   int               takeProfit;
  };
//+------------------------------------------------------------------+
//| Position dto                                                     |
//+------------------------------------------------------------------+
struct Position
  {
   bool              isHistory;
   PositionType      type;
   long              magic;
   ulong             positionId;
   double            lot;
   int               stopLoss;
   int               takeProfit;
   ulong             openAt;
   ulong             closeAt;
   double            openPrice;
   double            closePrice;
   double            profit;
   double            commission;
   double            swap;
  };
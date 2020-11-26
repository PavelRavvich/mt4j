#property strict

#include <Common\Enums.mqh>

struct HttpResponse
  {
   int               status;
   string            body;
  };

struct HttpRequest
  {
   string            url;
   string            body;
   string            headers;
  };

struct RestConfig
  {
   string            host;
   int               port;
   string            headers;
   int               timeout;
  };

struct Signal
  {
   long              positionId;
   string            advisorId;
   SignalType        type;
   double            lot;
   int               stopLoss;
   int               takeProfit;
  };

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
   double            profit;
   double            swap;
  };
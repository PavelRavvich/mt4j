#property strict

#include <Library\Common\Enums.mqh>
#include <Library\Common\Structures.mqh>

string PositionTypeToString(PositionType type)
  {
   switch(type)
     {
      case LONG:
         return "BUY";
      case SHORT:
         return "SELL";
     }
   Alert("Unexpected enum value PositionType can't be: ", type, "Expert was stopped!");
   ExpertRemove();
   return "ERROR";
  }

string BoolToString(bool val)
  {
   return val ? "true" : "false";
  }

int TakeProfitToPoint(double takeProfit, double price, ENUM_POSITION_TYPE type)
  {
   return (int) (type == POSITION_TYPE_BUY ? (takeProfit - price) / Point() : (price - takeProfit) / Point());
  }

double TakeProfitToPrice(int takeProfit, double price, ENUM_POSITION_TYPE type)
  {
   return type == POSITION_TYPE_BUY ? price + takeProfit * Point() : price - takeProfit * Point();
  }

int StopLossToPoint(double stopLoss, double price, ENUM_POSITION_TYPE type)
  {
   return (int) (type == POSITION_TYPE_BUY ? (price - stopLoss) / Point() : (stopLoss - price) / Point());
  }

double StopLossToPrice(double stopLoss, double price, ENUM_POSITION_TYPE type)
  {
   return type == POSITION_TYPE_BUY ? price - stopLoss * Point() : price + stopLoss * Point();
  }

double Ask()
  {
   return NormalizeDouble(SymbolInfoDouble(Symbol(), SYMBOL_ASK), Digits());
  }

double Bid()
  {
   return NormalizeDouble(SymbolInfoDouble(Symbol(), SYMBOL_BID), Digits());
  }
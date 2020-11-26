#property strict

#include <Common\Enums.mqh>
#include <Common\Structures.mqh>

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

int TakeProfitToPoint(double takeProfit, ENUM_POSITION_TYPE type)
  {
   return 100;
  }

double TakeProfitToPrice(int takeProfit, ENUM_POSITION_TYPE type)
  {
   return (type == BUY ? Ask() + takeProfit * Point() : Bid() - takeProfit * Point());
  }
int StopLossToPoint(double stopLoss, ENUM_POSITION_TYPE type)
  {
   // todo
   return 100;
  }

double StopLossToPrice(double stopLoss, ENUM_POSITION_TYPE type)
  {
   return (type == BUY ? Ask() - stopLoss * Point() : Bid() + stopLoss * Point());
  }

double Ask()
  {
   return NormalizeDouble(SymbolInfoDouble(_symbolInfo.Name(), SYMBOL_ASK), Digits());
  }

double Bid()
  {
   return NormalizeDouble(SymbolInfoDouble(_symbolInfo.Name(), SYMBOL_BID), Digits());
  }
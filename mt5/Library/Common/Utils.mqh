#property strict

#include <Library\Common\Enums.mqh>
#include <Library\Common\Structures.mqh>

//+------------------------------------------------------------------+
//| Cast PositionType enum to string                                 |
//+------------------------------------------------------------------+
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
//+------------------------------------------------------------------+
//| Cast bool to string                                              |
//+------------------------------------------------------------------+
string BoolToString(bool val)
  {
   return val ? "true" : "false";
  }
//+------------------------------------------------------------------+
//| Cast price take profit to point take profit                      |
//+------------------------------------------------------------------+
int TakeProfitToPoint(double takeProfit, double price, ENUM_POSITION_TYPE type)
  {
   return (int) (type == POSITION_TYPE_BUY ? (takeProfit - price) / Point() : (price - takeProfit) / Point());
  }
//+------------------------------------------------------------------+
//| Cast point take profit to price take profit                      |
//+------------------------------------------------------------------+
double TakeProfitToPrice(int takeProfit, double price, ENUM_POSITION_TYPE type)
  {
   return type == POSITION_TYPE_BUY ? price + takeProfit * Point() : price - takeProfit * Point();
  }
//+------------------------------------------------------------------+
//| Cast price stop loss to point stop loss                          |
//+------------------------------------------------------------------+
int StopLossToPoint(double stopLoss, double price, ENUM_POSITION_TYPE type)
  {
   return (int) (type == POSITION_TYPE_BUY ? (price - stopLoss) / Point() : (stopLoss - price) / Point());
  }
//+------------------------------------------------------------------+
//| Cast stop loss profit to price stop loss                         |
//+------------------------------------------------------------------+
double StopLossToPrice(double stopLoss, double price, ENUM_POSITION_TYPE type)
  {
   return type == POSITION_TYPE_BUY ? price - stopLoss * Point() : price + stopLoss * Point();
  }
//+------------------------------------------------------------------+
//| Current ASK price                                                |
//+------------------------------------------------------------------+
double Ask()
  {
   return NormalizeDouble(SymbolInfoDouble(Symbol(), SYMBOL_ASK), Digits());
  }
//+------------------------------------------------------------------+
//| Current BID price                                                |
//+------------------------------------------------------------------+
double Bid()
  {
   return NormalizeDouble(SymbolInfoDouble(Symbol(), SYMBOL_BID), Digits());
  }
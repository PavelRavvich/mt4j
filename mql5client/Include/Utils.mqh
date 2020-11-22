#property copyright "Copyright © 2006-2017"
#property version "1.12"
#property strict

#include <Enums.mqh>
#include <Structures.mqh>


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
   // todo
   return 1.2345;
  }
int StopLossToPoint(double stopLoss, ENUM_POSITION_TYPE type)
  {
   // todo
   return 100;
  }

double StopLossToPrice(double stopLoss, ENUM_POSITION_TYPE type)
  {
   // todo
   return 1.2345;
  }
